package consulting.gigs.fragments;


import static consulting.gigs.migrationsDatabase.DatabaseMigrations.MIGRATION_1_2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import consulting.gigs.CartManager;
import consulting.gigs.Product;
import consulting.gigs.R;
import consulting.gigs.adapter.CartAdapter;
import consulting.gigs.database.AppDatabase;
import consulting.gigs.entities.OrderEntity;
import consulting.gigs.entities.ProductOrderEntity;


public class ShoppingCart extends Fragment implements CartAdapter.OnRemoveItemClickListener {
    private EditText addressEditText;
    private RecyclerView recyclerView;
    private CartManager cartManager;
    private TextView cartTotalAmount;
    private TextView cartEmptyMessage;
    private ImageView cartEmptyImage;
    private Button buyButton;
    private TextInputLayout addressInputLayout;

    public ShoppingCart() {
        // Required empty public constructor
    }

    public static ShoppingCart newInstance() {
        return new ShoppingCart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartManager = CartManager.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        addressEditText = view.findViewById(R.id.address_edit_text);
        addressInputLayout = view.findViewById(R.id.address_text_input_layout);
        recyclerView = view.findViewById(R.id.recycler_view_cart_products);
        cartTotalAmount = view.findViewById(R.id.cart_total_amount);
        buyButton = view.findViewById(R.id.buy_button);
        cartEmptyMessage = view.findViewById(R.id.cart_empty_message);
        cartEmptyImage = view.findViewById(R.id.cart_empty_image);

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ShoppingCart", "Buy button clicked.");
                saveOrder();
            }
        });

        updateCartView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateCartView();
    }

    private void updateCartView() {
        List<Product> cartProducts = cartManager.getProducts();
        if (cartProducts.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            cartEmptyMessage.setVisibility(View.VISIBLE);
            cartEmptyImage.setVisibility(View.VISIBLE);
            cartTotalAmount.setVisibility(View.GONE);
            buyButton.setVisibility(View.GONE);
            addressEditText.setVisibility(View.GONE);
            addressInputLayout.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            cartEmptyMessage.setVisibility(View.GONE);
            cartEmptyImage.setVisibility(View.GONE);
            cartTotalAmount.setVisibility(View.VISIBLE);
            buyButton.setVisibility(View.VISIBLE);
            addressEditText.setVisibility(View.VISIBLE);
            addressInputLayout.setVisibility(View.VISIBLE);

            CartAdapter adapter = new CartAdapter(cartProducts, this, new CartAdapter.OnQuantityChangeListener() {
                @Override
                public void onQuantityChange() {
                    updateCartTotal();
                }
            });
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            updateCartTotal();
        }
    }


    // Implementa el método de eliminación de productos
    @Override
    public void onRemoveItemClick(int position) {
        // Obtén el producto a eliminar
        Product productToRemove = cartManager.getProducts().get(position);

        // Elimina el producto del carrito
        cartManager.removeProduct(productToRemove);

        // Actualiza la vista del carrito
        updateCartView();
    }

    private void updateCartTotal() {
        double total = 0.0;
        for (Product product : cartManager.getProducts()) {
            total += product.getPrice() * product.getQuantity();
        }
        cartTotalAmount.setText(String.format("$%.2f", total));
    }

    private void saveOrder() {
        Log.d("ShoppingCart", "Saving order...");
        AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "shoppingCart")
                .addMigrations(MIGRATION_1_2) // Agregar la migración
                .build();

        // Verificar si la dirección no está vacía
        String address = addressEditText.getText().toString().trim();
        if (address.isEmpty()) {
            // Mostrar un mensaje de error o notificar al usuario que la dirección es obligatoria.
            addressInputLayout.setError("La dirección es obligatoria");
            return; // No continuar con la compra si la dirección está vacía
        } else {
            addressInputLayout.setError(null); // Limpiar el mensaje de error si la dirección no está vacía
        }

        // Verificar que todas las cantidades de producto sean mayores a 0
        for (Product product : cartManager.getProducts()) {
            if (product.getQuantity() <= 0) {
                // Mostrar un mensaje de error o notificar al usuario que las cantidades deben ser mayores a 0.
                Toast.makeText(getContext(), "La cantidad de " + product.getName() + " debe ser mayor que 0", Toast.LENGTH_SHORT).show();
                return; // No continuar con la compra si una cantidad es menor o igual a 0
            }
        }

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.address = address;
        double total = 0.0;
        for (Product product : cartManager.getProducts()) {
            total += product.getPrice() * product.getQuantity();
        }
        orderEntity.total = total;

        new Thread(() -> {
            long orderId = db.shopDao().insertOrder(orderEntity);
            Log.d("ShoppingCart", "Order saved with ID: " + orderId);

            for (Product product : cartManager.getProducts()) {
                ProductOrderEntity productOrder = new ProductOrderEntity();
                productOrder.orderId = orderId;
                productOrder.productName = product.getName();
                productOrder.productPrice = product.getPrice();
                productOrder.productQuantity = product.getQuantity();
                db.shopDao().insertOrderProducts(productOrder);
                Log.d("ShoppingCart", "Saved product: " + productOrder.productName + " for order ID: " + orderId);
            }

            getActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), "Order saved successfully!", Toast.LENGTH_SHORT).show();

                // Clear the cart
                cartManager.clearCart();

                // Update the UI to reflect the empty cart
                updateCartView();

                // Get a reference to the BottomNavigationView
                BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation);

                // Select the "order" menu item
                bottomNav.setSelectedItemId(R.id.orderFragment);
            });
        }).start();
    }
}