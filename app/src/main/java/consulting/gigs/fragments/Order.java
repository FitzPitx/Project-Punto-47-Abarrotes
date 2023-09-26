package consulting.gigs.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import consulting.gigs.R;
import consulting.gigs.database.AppDatabase;

public class Order extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orden, container, false);
    }

    private void loadOrderDetails(long orderId) {
        AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "shoppingCart").build();

        db.shopDao().loadOrderDetails(orderId).observe(getViewLifecycleOwner(), order -> {
            // Actualizar la UI con los detalles de la orden
        });
    }
}