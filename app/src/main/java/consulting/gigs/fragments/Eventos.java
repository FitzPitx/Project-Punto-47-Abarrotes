package consulting.gigs.fragments;

import static android.util.Base64.NO_WRAP;
import static consulting.gigs.Login.isNullOrEmpty;
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
import consulting.gigs.api.ServiceLogin;
import consulting.gigs.api.ServiceVisit;
import consulting.gigs.model.Credentials;
import consulting.gigs.model.ReponseCredentials;
import consulting.gigs.model.ResponseVisit;
import consulting.gigs.model.Visit;
import consulting.gigs.remoto.ClienteRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Eventos extends Fragment {

    private ListView lista;
    private Retrofit retrofit;
    private TextView tv;


    public Eventos() {
        // Required empty public constructor

    }
    public static Eventos newInstance(String param1, String param2) {
        Eventos fragment = new Eventos();

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
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);
        frInit(view);
        getCredencials(view);
        return view;
    }

    private void listaEventos(String identificador,String key,String id, View view){
        String token = identificador+":"+key;
        final String AUTH = "Basic "+Base64.encodeToString((token).getBytes(), NO_WRAP);

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