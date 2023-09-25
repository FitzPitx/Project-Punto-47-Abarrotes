package consulting.gigs.fragments;

import static android.util.Base64.NO_WRAP;
import static consulting.gigs.loginActivity.isNullOrEmpty;
import static consulting.gigs.api.ValuesAPI.BASE_URL;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import consulting.gigs.R;
import consulting.gigs.api.ServiceVisit;
import consulting.gigs.model.visita.ResponseVisit;
import consulting.gigs.model.visita.Visit;
import consulting.gigs.remoto.ClienteRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Perfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Perfil extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private ListView lista;
    private Retrofit retrofit;
    private TextView tv;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Perfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Perfil.
     */
    // TODO: Rename and change types and number of parameters
    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        frInit(view);
        getCredencials(view);
        return view;
    }

    private void listaEventos(String identificador,String key,String id, View view){
        String token = identificador+":"+key;
        final String AUTH = "Basic "+ Base64.encodeToString((token).getBytes(), NO_WRAP);

        retrofit = ClienteRetrofit.getClient(BASE_URL,AUTH);
        ServiceVisit serviceVisit = retrofit.create(ServiceVisit.class);
        Call<ResponseVisit> call = serviceVisit.visitList(id,AUTH);
        call.enqueue(new Callback<ResponseVisit>() {
            @Override
            public void onResponse(Call<ResponseVisit> call, Response<ResponseVisit> response) {
                Toast.makeText(getActivity(), "COdigo:"+response.code(), Toast.LENGTH_SHORT).show();
                Log.i("RAPTOR1", "onResponse: "+response.body().getMensaje());
                if (response.isSuccessful()){
                    ResponseVisit body = response.body();
                    String mensaje = body.getMensaje();
                    ArrayList<Visit> list = body.getVisita();

                    if(mensaje.equals("OK") && !isNullOrEmpty(list)){
                        ArrayAdapter<Visit> adapter = new ArrayAdapter<>
                                (getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,list);
                        lista.setAdapter(adapter);
                    }
                }else{
                    Toast.makeText(getActivity(), "NO error", Toast.LENGTH_SHORT).show();
                    Log.i("RAPTOR2", "onResponse: "+response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseVisit> call, Throwable t) {
                Log.i("RAPTOR3", "onFailure: "+t.getMessage());
            }
        });
    }

    private void frInit(View view){
        lista = view.findViewById(R.id.lvEventos);

    }
    private void getCredencials(View view){
        SharedPreferences preferences = getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String key = preferences.getString("key","No");
        String identificador = preferences.getString("identificador","No");
        String id = preferences.getString("id","NoID");
        Toast.makeText(getActivity(), ""+id+identificador+key, Toast.LENGTH_SHORT).show();
        listaEventos(identificador,key,id, view);
    }
}