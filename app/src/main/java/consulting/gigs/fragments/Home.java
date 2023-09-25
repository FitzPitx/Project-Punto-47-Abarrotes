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
    private RecyclerView recyclerViewProducts3;
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
        setupRecyclerView3();

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
        recyclerViewProducts3 = v.findViewById(R.id.recycler_view_products3);
    }

    // Método para configurar el primer RecyclerView
    private void setupRecyclerView() {
        List<Product> products = new ArrayList<>();
        // Frutas
        products.add(new Product(R.drawable.fresa, "Fresa", 25.50, 1));
        products.add(new Product(R.drawable.naranja, "Naranja", 25.50, 1));
        products.add(new Product(R.drawable.banana, "Banana", 25.50, 1));
        products.add(new Product(R.drawable.uvas, "Uvas", 25.50, 1));
        products.add(new Product(R.drawable.manzana, "Manzana", 25.50, 1));

        ProductAdapter adapter = new ProductAdapter(products);
        recyclerViewProducts.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewProducts.setLayoutManager(layoutManager);
    }

    // Método para configurar el segundo RecyclerView
    private void setupRecyclerView2() {
        List<Product> products2 = new ArrayList<>();
        // Verduras
        products2.add(new Product(R.drawable.tomate, "Tomate", 25.50, 1));
        products2.add(new Product(R.drawable.cebolla, "Cebolla", 25.50, 1));
        products2.add(new Product(R.drawable.zanahoria, "Zanahoria", 25.50, 1));
        products2.add(new Product(R.drawable.esparrago, "Esparrago", 25.50, 1));
        products2.add(new Product(R.drawable.pimiento, "Pimiento", 25.50, 1));

        ProductAdapter adapter2 = new ProductAdapter(products2);
        recyclerViewProducts2.setAdapter(adapter2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewProducts2.setLayoutManager(layoutManager2);
    }


    // Metodo para configurar el tercer RecyclerView
    private void setupRecyclerView3() {
        List<Product> products3 = new ArrayList<>();
        // Snacks
        products3.add(new Product(R.drawable.boleto, "", 25.50, 1));
        products3.add(new Product(R.drawable.boleto, "", 25.50, 1));
        products3.add(new Product(R.drawable.boleto, "", 25.50, 1));
        products3.add(new Product(R.drawable.boleto, "", 25.50, 1));
        products3.add(new Product(R.drawable.boleto, "", 25.50, 1));

        ProductAdapter adapter3 = new ProductAdapter(products3);
        recyclerViewProducts3.setAdapter(adapter3);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewProducts3.setLayoutManager(layoutManager3);
    }
}