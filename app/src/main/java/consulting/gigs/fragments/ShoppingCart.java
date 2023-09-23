package consulting.gigs.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import consulting.gigs.CartManager;
import consulting.gigs.Product;
import consulting.gigs.R;
import consulting.gigs.adapter.CartAdapter;
import consulting.gigs.adapter.ProductAdapter;


public class ShoppingCart extends Fragment implements CartAdapter.OnRemoveItemClickListener {
    private RecyclerView recyclerView;
    private CartManager cartManager;

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
            CartAdapter adapter = new CartAdapter(cartProducts, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
}