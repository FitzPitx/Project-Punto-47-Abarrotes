package consulting.gigs.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import consulting.gigs.R;
import consulting.gigs.adapter.OrdersAdapter;
import consulting.gigs.database.AppDatabase;
import consulting.gigs.entities.OrderEntity;

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
            loadOrderDetails(order.orderId);
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

    private void loadOrderDetails(long orderId) {
        AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "shoppingCart").build();

        db.shopDao().loadOrderDetails(orderId).observe(getViewLifecycleOwner(), order -> {
            // Actualizar la UI con los detalles de la orden
        });
    }
}