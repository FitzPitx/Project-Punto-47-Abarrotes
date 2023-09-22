package consulting.gigs.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import consulting.gigs.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    private TextView tvFecha;
    private Button btnVisita;
    Eventos eventos;

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
        btnVisita.setOnClickListener(this::visita);
        return view;
    }

    private void visita(View view) {
        Fragment visita = new Eventos();
        loadFragment(visita);
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
        btnVisita = v.findViewById(R.id.btnVisitante);
    }
}