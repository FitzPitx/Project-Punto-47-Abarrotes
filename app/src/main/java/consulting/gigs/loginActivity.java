package consulting.gigs;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import consulting.gigs.adapter.SharedPrefManager;
import consulting.gigs.api.RetrofitClient;
import consulting.gigs.model.response.LoginResponse;
import consulting.gigs.model.response.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class loginActivity extends AppCompatActivity implements View.OnClickListener{
    private Retrofit retrofit;
    private EditText etCorreo;
    private EditText etPassword;
    private Button btnLogin;
    private TextView etCrearLink;
    SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        parInit();
        btnLogin.setOnClickListener(this::onClick);
        if (etCrearLink != null){
            etCrearLink.setOnClickListener(this::onClick);
        }

        sharedPrefManager = new SharedPrefManager(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case (R.id.btnLogin):
                    userLogin();
                    break;
                case (R.id.etCrearLink):
                    switchOnRegister();
                    break;
            }
    }

    public void switchOnRegister(){
        try {
            Intent userRegister = new Intent(this, registerActivity.class);
            startActivity(userRegister);
            finish();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void userLogin() {

        String user_mail = etCorreo.getText().toString();
        String user_contra = etPassword.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(user_mail).matches()){
            etCorreo.setError("Correo requerido");
            etCorreo.requestFocus();
            return;
        }
        if (user_contra.isEmpty()){
            etPassword.setError("Contraseña requerida");
            etPassword.requestFocus();
            return;
        }

        if (user_contra.length() < 8){
            etPassword.setError("La contraseña debe tener al menos 8 caracteres");
            etPassword.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().login(user_mail, user_contra);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + loginResponse);
                    if (loginResponse != null) {
                        String errorMessage = loginResponse.getMessage();
                        if (loginResponse.getError().equals("000")) {
                            sharedPrefManager.saveUser(loginResponse.getUser());
                            Toast.makeText(loginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(loginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (loginResponse.getError().equals("400")){
                            Toast.makeText(loginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(loginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(loginActivity.this, "Error en la respuesta de la API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(loginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    /*@Override
    protected void onStart(){
        super.onStart();
        if (sharedPrefManager.isLoggedIn()){
            Intent intent = new Intent(loginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }*/

    private void parInit(){
        etCorreo = findViewById(R.id.etUsu);
        etPassword = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);
        etCrearLink = findViewById(R.id.etCrearLink);
    }
    public boolean validEmail(String data){
        Pattern pattern =
                Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~\\-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
        Matcher mather = pattern.matcher(data);
        if (mather.find() == true) {
            System.out.println("El email ingresado es válido.");
            return true;
        } else {
            System.out.println("El email ingresado es inválido.");
        }
        return false;
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}