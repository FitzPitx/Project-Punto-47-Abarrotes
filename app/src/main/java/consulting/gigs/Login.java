package consulting.gigs;

import static consulting.gigs.api.ValuesAPI.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import consulting.gigs.api.ServiceLogin;
import consulting.gigs.model.Credentials;
import consulting.gigs.model.Loger;
import consulting.gigs.model.ReponseCredentials;
import consulting.gigs.remoto.ClienteRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {
    private Retrofit retrofit;
    private EditText user;
    private EditText pass;
    private Button logear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        parInit();
        logear.setOnClickListener(this::processLogin);
    }
    private void alertView(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage(mensaje);
        builder.setNegativeButton("ACEPTAR", null);
        builder.create();
        builder.show();
    }


    private void processLogin(View view) {
        if (!validEmail(user.getText().toString())&& pass.getText().length()<=3){
            Toast.makeText(this, "Error en credenciales", Toast.LENGTH_LONG).show();
        }else{
            String password = md5(pass.getText().toString());
            System.out.println(password);
            Loger loger = new Loger();
            loger.setUsu_mail(user.getText().toString());
            loger.setUsu_contra(password);
            retrofit = ClienteRetrofit.getClient(BASE_URL,"");
            ServiceLogin serviceLogin =retrofit.create(ServiceLogin.class);
            Call<ReponseCredentials> call= serviceLogin.accessLogin(loger);
            call.enqueue(new Callback<ReponseCredentials>() {
                @Override
                public void onResponse(Call<ReponseCredentials> call, retrofit2.Response<ReponseCredentials> response) {
                    if(response.isSuccessful()){
                        ReponseCredentials body =response.body();
                        String mensaje = body.getMensaje();
                        ArrayList<Credentials> list = body.getCredentials();
                        Toast.makeText(Login.this, "Ingresado:"+mensaje, Toast.LENGTH_SHORT).show();
                        if(mensaje.equals("OK") && !isNullOrEmpty(list)){
                            for(Credentials c:list){
                                SharedPreferences shared = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=shared.edit();
                                editor.putString("key",c.getUsu_key());
                                editor.putString("identificador",c.getUsu_identifier());
                                editor.putString("id",c.getUsu_id());
                                editor.commit();
                                irTo();
                            }
                        }else{
                            alertView("Usuario no existe o Contraseña Inválida!, Intentelo de nuevo");
                        }
                    }else{
                        alertView("Usuario no existe!, Intentelo de nuevo");
                    }

                }

                @Override
                public void onFailure(Call<ReponseCredentials> call, Throwable t) {
                    Log.i("Reponse2",t.getMessage());
                    alertView("Error en Servicio comuniquese con el Programador");
                }
            });

        }
    }

    public static boolean isNullOrEmpty(Object obj){
        if(obj==null)return true;
        if(obj instanceof String) return ((String)obj).trim().equals("") || ((String)obj).equalsIgnoreCase("NULL");
        if(obj instanceof Integer) return ((Integer)obj)==0;
        if(obj instanceof Long) return ((Long)obj).equals(new Long(0));
        if(obj instanceof Double) return ((Double)obj).equals(0.0);
        if(obj instanceof Collection) return (((Collection)obj).isEmpty());
        return false;
    }
    private void irTo() {
        try {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    private void parInit(){
        user = findViewById(R.id.etUsu);
        pass = findViewById(R.id.etPass);
        logear = findViewById(R.id.btnLogin);
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