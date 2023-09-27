package consulting.gigs;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.*;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import consulting.gigs.api.RetrofitClient;
import consulting.gigs.model.response.LoginResponse;
import consulting.gigs.model.response.RegisterResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class registerActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etNombre, etApellido, etNombreUsuario, etCorreo;
    private EditText etPassword;
    private TextView tvIngresarLink;
    private Button btnRegistrarse;
    private static String URL_REGIST = "http://";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();

        //hide actionbar
        //getSupportActionBar().hide();

        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (tvIngresarLink != null) {
            tvIngresarLink.setOnClickListener(this::onClick);
        }

        if (btnRegistrarse != null) {
            btnRegistrarse.setOnClickListener(this::onClick);
        }

    }

    public void initialize() {
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etNombreUsuario = findViewById(R.id.etNombreUsuario);
        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        tvIngresarLink = findViewById(R.id.tvIngresarLink);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.tvIngresarLink):
                switchOnLogin();
                break;
            case (R.id.btnRegistrarse):
                validateUser();
                break;
        }
    }

    private void validateUser() {

        String user_nombre = etNombre.getText().toString();
        String user_apellido = etApellido.getText().toString();
        String user_usuario = etNombreUsuario.getText().toString();
        String user_mail = etCorreo.getText().toString();
        String user_contra = etPassword.getText().toString();

        if (user_nombre.isEmpty()) {
            etNombre.setError("Nombre requerido");
            etNombre.requestFocus();

        } else if (user_nombre.length() < 3) {
            etNombre.setError("El nombre debe tener al menos 3 caracteres");
            etNombre.requestFocus();
            return;
        }
        if (user_apellido.isEmpty()) {
            etApellido.setError("Apellido requerido");
            etApellido.requestFocus();
            return;
        }
        if (user_usuario.isEmpty()) {
            etNombreUsuario.setError("Nombre de usuario requerido");
            etNombreUsuario.requestFocus();

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(user_mail).matches()) {
            etCorreo.setError("Correo requerido");
            etCorreo.requestFocus();
            return;
        }
        if (user_contra.isEmpty()) {
            etPassword.setError("Contraseña requerida");
            etPassword.requestFocus();
            return;
        }

        if (user_contra.length() < 8) {
            etPassword.setError("La contraseña debe tener al menos 8 caracteres");
            etPassword.requestFocus();
            return;
        }

        Call<RegisterResponse> call = RetrofitClient.getInstance().getApi().register(user_nombre, user_apellido, user_usuario, user_contra, user_mail, 1);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.isSuccessful()){
                    RegisterResponse registerResponse = response.body();
                    if (registerResponse != null){
                        String errorMessage = registerResponse.getMessage();
                        if (registerResponse.getError().equals("000")){
                            Toast.makeText(registerActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            switchOnLogin();
                        } else {
                            Toast.makeText(registerActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(registerActivity.this, "Error en la respuesta de la API", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(registerActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


        private void switchOnLogin() {
            try {
                Intent loginIntent;
                loginIntent = new Intent(registerActivity.this, loginActivity.class);
                startActivity(loginIntent);
                finish();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

        }
    }

