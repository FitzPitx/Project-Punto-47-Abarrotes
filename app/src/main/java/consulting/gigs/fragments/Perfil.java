package consulting.gigs.fragments;

import static android.util.Base64.NO_WRAP;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import consulting.gigs.R;
import consulting.gigs.adapter.SharedPrefManager;
import consulting.gigs.api.RetrofitClient;
import consulting.gigs.model.response.LoginResponse;
import consulting.gigs.model.response.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Perfil extends Fragment implements View.OnClickListener{

    private EditText etNombreUsuario, etApellidoUsuario, etUsername, etUsermail;
    private Button btnActualizar;
    int user_id;
    SharedPrefManager sharedPrefManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        user_id = sharedPrefManager.;
        etNombreUsuario = view.findViewById(R.id.etNombreUsuario);
        etApellidoUsuario = view.findViewById(R.id.etApellidoUsuario);
        etUsername = view.findViewById(R.id.etUsername);
        etUsermail = view.findViewById(R.id.etUsermail);
        btnActualizar = view.findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(this::onClick);

        return view;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.btnActualizar):
                updateUser();
                break;
        }
    }


    private void updateUser() {
        String user_nombre = etNombreUsuario.getText().toString().trim();
        String user_apellido = etApellidoUsuario.getText().toString().trim();
        String user_usuario = etUsername.getText().toString().trim();
        String user_mail = etUsermail.getText().toString().trim();

        if (user_nombre.isEmpty()){
            etNombreUsuario.setError("Nombre requerido");
            etNombreUsuario.requestFocus();
            return;
        }

        if (user_apellido.isEmpty()){
            etApellidoUsuario.setError("Apellido requerido");
            etApellidoUsuario.requestFocus();
            return;
        }

        if (user_usuario.isEmpty()){
            etUsername.setError("Nombre de usuario requerido");
            etUsername.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(user_mail).matches()){
            etUsermail.setError("Correo requerido");
            etUsermail.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().updateUser(user_id,user_nombre,user_apellido, user_usuario, user_mail);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse updateResponse = response.body();
                    if(updateResponse.getError().equals("200")){
                        sharedPrefManager.saveUser(updateResponse.getUser());
                        Toast.makeText(getContext(), updateResponse.getMessage(), Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onResponse: " + updateResponse.getMessage());
                    } else {
                        Toast.makeText(getContext(), updateResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                        Toast.makeText(getContext(), "Error al actualizar", Toast.LENGTH_LONG).show();
                    }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}