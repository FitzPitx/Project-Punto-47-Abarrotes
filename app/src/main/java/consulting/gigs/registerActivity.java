package consulting.gigs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class registerActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etNombre, etApellido, etNombreUsuario, etCorreo;
    private EditText etPassword;
    private TextView tvIngresarLink;
    private Button btnRegistrarse;

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

        if (tvIngresarLink != null ){
            tvIngresarLink.setOnClickListener(this::onClick);
        }

        if (btnRegistrarse != null ) {
            btnRegistrarse.setOnClickListener(this::onClick);
        }

    }

    public void initialize(){
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
        switch (view.getId()){
            case (R.id.btnRegistrarse):
                Toast.makeText(this, "Registrado", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.tvIngresarLink):
                switchOnLogin();
                break;
        }
    }

    private void switchOnLogin(){
        try {
            Intent loginIntent;
            loginIntent = new Intent(registerActivity.this, loginActivity.class);
            startActivity(loginIntent);
            finish();

        } catch (Exception e){
            System.err.println(e.getMessage());
        }

    }
}
