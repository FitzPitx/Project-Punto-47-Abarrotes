package consulting.gigs.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import consulting.gigs.Product;
import consulting.gigs.R;
import consulting.gigs.adapter.ProductAdapter;
import consulting.gigs.adapter.SharedPrefManager;
import consulting.gigs.loginActivity;

public class Home extends Fragment {

    private TextView tvFecha;
    private RecyclerView recyclerViewProducts;
    private RecyclerView recyclerViewProducts2;
    private RecyclerView recyclerViewProducts3;
    ShoppingCart shoppingCart;

    private TextView tvNombreUsuario, tvCorreoUsuario;
    private Button btnSalirSesion;
    SharedPrefManager sharedPrefManager;

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

        //Boton salir de la sesion
        btnSalirSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefManager.logout();
                Intent intent = new Intent(getActivity(), consulting.gigs.loginActivity.class);
                startActivity(intent);
            }
        });

        //Configuracion del shared preferences para el nombre y correo del usuario
        sharedPrefManager = new SharedPrefManager(getActivity());

        /*String userName = "Bienvenido a Punto Abarrotes 47";
        tvNombreUsuario.setText(userName);*/
        loginActivity loginActivity = new loginActivity();
        String userName = "Bienvenido a Punto Abarrotes 47 \n" + loginActivity.NOMBRE + " " + loginActivity.APELLIDO;
        tvNombreUsuario.setText(userName);
        String userEmail = loginActivity.MAIL;
        tvCorreoUsuario.setText(userEmail);
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
        tvNombreUsuario = v.findViewById(R.id.tvNombreUsuario);
        tvCorreoUsuario = v.findViewById(R.id.tvCorreoUsuarioHome);
        btnSalirSesion = v.findViewById(R.id.btnCerrarSesion);

    }

    // Método para configurar el primer RecyclerView
    private void setupRecyclerView() {
        List<Product> products = new ArrayList<>();
        // Frutas
        products.add(new Product(R.drawable.naranja, "Naranja", 1000.00, 1));   // Aprox. 1,000 COP por naranja.
        products.add(new Product(R.drawable.banana, "Banana", 700.00, 1));      // Aprox. 700 COP por banana.
        products.add(new Product(R.drawable.uvas, "Uvas", 8000.00, 1));         // Aprox. 8,000 COP por racimo.
        products.add(new Product(R.drawable.manzana, "Manzana", 1500.00, 1));   // Aprox. 1,500 COP por manzana.
        products.add(new Product(R.drawable.fresa, "Fresa (12)", 5000.00, 1));       // Aprox. 5,000 COP por paquete o bandeja.

        ProductAdapter adapter = new ProductAdapter(products);
        recyclerViewProducts.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewProducts.setLayoutManager(layoutManager);
    }

    // Método para configurar el segundo RecyclerView
    private void setupRecyclerView2() {
        List<Product> products2 = new ArrayList<>();
        // Verduras
        products2.add(new Product(R.drawable.tomate, "Tomate", 2000.00, 1));       // Aprox. 2,000 COP
        products2.add(new Product(R.drawable.cebolla, "Cebolla", 1500.00, 1));     // Aprox. 1,500 COP
        products2.add(new Product(R.drawable.zanahoria, "Zanahoria", 1000.00, 1)); // Aprox. 1,000 COP
        products2.add(new Product(R.drawable.esparrago, "Esparrago", 5000.00, 1)); // Aprox. 5,000 COP (los espárragos suelen ser más caros)
        products2.add(new Product(R.drawable.pimiento, "Pimiento", 2500.00, 1));   // Aprox. 2,500 COP

        ProductAdapter adapter2 = new ProductAdapter(products2);
        recyclerViewProducts2.setAdapter(adapter2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewProducts2.setLayoutManager(layoutManager2);
    }


    // Metodo para configurar el tercer RecyclerView
    private void setupRecyclerView3() {
        List<Product> products3 = new ArrayList<>();
        // Snacks
        products3.add(new Product(R.drawable.choclitos, "Choclitos", 1500.00, 1));
        products3.add(new Product(R.drawable.uvaspasas, "Uvas pasas", 3100.00, 1));
        products3.add(new Product(R.drawable.cheetos, "Cheetos", 1700.00, 1));
        products3.add(new Product(R.drawable.margaritamayonesa, "Margarita", 2000.00, 1));
        products3.add(new Product(R.drawable.doritos, "Doritos", 2500.00, 1));

        ProductAdapter adapter3 = new ProductAdapter(products3);
        recyclerViewProducts3.setAdapter(adapter3);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewProducts3.setLayoutManager(layoutManager3);
    }
}