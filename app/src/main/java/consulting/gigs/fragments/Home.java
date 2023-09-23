package consulting.gigs.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import consulting.gigs.Product;
import consulting.gigs.R;
import consulting.gigs.adapter.ProductAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    private TextView tvFecha;
    private RecyclerView recyclerViewProducts;
    private RecyclerView recyclerViewProducts2;
    ShoppingCart shoppingCart;

    public Home() {
        // Required empty public constructor
    }
    public static Home newInstance() {
        Home fragment = new Home();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        frInit(view);
        takeDate();

        // Configuración del RecyclerView
        setupRecyclerView();
        setupRecyclerView2();

        return view;
    }

    public void loadFragment(Fragment fr){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container,fr).addToBackStack(null).commit();
    }

    private void takeDate(){
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        String salida = df.format(date);
        tvFecha.setText(salida);
    }

    private void frInit(View v){
        tvFecha = v.findViewById(R.id.tvFecha);
        recyclerViewProducts = v.findViewById(R.id.recycler_view_products);
        recyclerViewProducts2 = v.findViewById(R.id.recycler_view_products2);
    }

    // Método para configurar el primer RecyclerView
    private void setupRecyclerView() {
        List<Product> products = new ArrayList<>();
        // Agrega algunos productos a la lista...
        products.add(new Product(R.drawable.boleto, "Producto 1", 25.50, 10));
        products.add(new Product(R.drawable.boleto, "Producto 1", 25.50, 10));
        products.add(new Product(R.drawable.boleto, "Producto 1", 25.50, 10));
        products.add(new Product(R.drawable.boleto, "Producto 1", 25.50, 10));
        products.add(new Product(R.drawable.boleto, "Producto 1", 25.50, 10));

        ProductAdapter adapter = new ProductAdapter(products);
        recyclerViewProducts.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewProducts.setLayoutManager(layoutManager);
    }

    // Método para configurar el segundo RecyclerView
    private void setupRecyclerView2() {
        List<Product> products2 = new ArrayList<>();
        // Agrega algunos productos a la lista...
        products2.add(new Product(R.drawable.boleto, "Producto 1", 25.50, 10));
        products2.add(new Product(R.drawable.boleto, "Producto 1", 25.50, 10));
        products2.add(new Product(R.drawable.boleto, "Producto 1", 25.50, 10));
        products2.add(new Product(R.drawable.boleto, "Producto 1", 25.50, 10));
        products2.add(new Product(R.drawable.boleto, "Producto 1", 25.50, 10));

        ProductAdapter adapter2 = new ProductAdapter(products2);
        recyclerViewProducts2.setAdapter(adapter2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewProducts2.setLayoutManager(layoutManager2);
    }
}