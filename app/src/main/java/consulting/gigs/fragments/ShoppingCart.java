package consulting.gigs.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import consulting.gigs.CartManager;
import consulting.gigs.Product;
import consulting.gigs.R;
import consulting.gigs.adapter.CartAdapter;


public class ShoppingCart extends Fragment implements CartAdapter.OnRemoveItemClickListener {
    private RecyclerView recyclerView;
    private CartManager cartManager;
    private TextView cartTotalAmount;

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

        recyclerView = view.findViewById(R.id.recycler_view_cart_products);
        cartTotalAmount = view.findViewById(R.id.cart_total_amount);
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
}