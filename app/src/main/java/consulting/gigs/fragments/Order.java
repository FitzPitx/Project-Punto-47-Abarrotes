package consulting.gigs.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import consulting.gigs.R;
import consulting.gigs.adapter.OrdersAdapter;
import consulting.gigs.database.AppDatabase;
import consulting.gigs.entities.OrderEntity;
import consulting.gigs.entities.ProductOrderEntity;

public class Order extends Fragment {

    // Define el RecyclerView y el adaptador
    private RecyclerView recyclerView;
    private OrdersAdapter adapter;

    public Order() {
        // Required empty public constructor
    }

    public static Order newInstance() {
        Order fragment = new Order();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orden, container, false);

        // Inicializar el RecyclerView y el adaptador
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new OrdersAdapter(position -> {
            OrderEntity order = adapter.getOrders().get(position);
            loadProductsForOrder(order.orderId);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Cargar todas las órdenes
        loadAllOrders();

        return view;
    }

    private void loadAllOrders() {
        AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "shoppingCart").build();
        db.shopDao().loadAllOrders().observe(getViewLifecycleOwner(), orders -> {
            adapter.setOrders(orders);
        });
    }

    private void loadProductsForOrder(long orderId) {
        AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "shoppingCart").build();

        db.shopDao().loadProductsForOrder(orderId).observe(getViewLifecycleOwner(), products -> {
            // Aquí tienes la lista de productos de la orden. Puedes mostrarlos como prefieras.
            displayProducts(products);
        });
    }

    private void displayProducts(List<ProductOrderEntity> products) {
        // Aquí puedes mostrar los productos, ya sea usando un Dialog, un nuevo Fragment, etc.
        // Por ejemplo, si quieres mostrarlos en un Dialog simple, puedes hacerlo así:

        StringBuilder productsList = new StringBuilder();
        for (ProductOrderEntity product : products) {
            productsList.append(product.productName).append(" - ").append(product.productPrice).append(" x ").append(product.productQuantity).append("\n");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Productos de la Orden")
                .setMessage(productsList.toString())
                .setPositiveButton("Cerrar", null)
                .show();
    }
}