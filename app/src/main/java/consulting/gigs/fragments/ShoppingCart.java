package consulting.gigs.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button buyButton;

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
        recyclerView = view.findViewById(R.id.recycler_view_cart_products);
        cartTotalAmount = view.findViewById(R.id.cart_total_amount);
        buyButton = view.findViewById(R.id.buy_button);

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
        if (recyclerView != null) {
            List<Product> cartProducts = cartManager.getProducts();
            CartAdapter adapter = new CartAdapter(cartProducts, this, new CartAdapter.OnQuantityChangeListener() {
                @Override
                public void onQuantityChange() {
                    updateCartTotal();
                }
            });
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        updateCartTotal();
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
        cartTotalAmount.setText(String.format("Total: $%.2f", total));
    }

    private void saveOrder() {
        Log.d("ShoppingCart", "Saving order...");
        AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "shoppingCart").build();

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.address = addressEditText.getText().toString();
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
            });
        }).start();
    }
}