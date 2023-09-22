package consulting.gigs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import consulting.gigs.fragments.Eventos;
import consulting.gigs.fragments.Home;
import consulting.gigs.fragments.Perfil;
import consulting.gigs.fragments.Salir;

public class MainActivity extends AppCompatActivity {
    Home home = new Home();
    Eventos eventos = new Eventos();
    Perfil perfil = new Perfil();
    Salir salir = new Salir();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        selecc(navigation);
        loadFragment(home);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return;
    }
    public void loadFragment(Fragment fr){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,fr);
        transaction.commit();

    }
    private void selecc(BottomNavigationView navigation){
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch(id){
                    //check id
                    case R.id.homeFragment:
                        loadFragment(home);
                        return true;
                    case R.id.consultasFragment:
                        loadFragment(eventos);
                        return true;
                    case R.id.perfilFragment:
                        loadFragment(perfil);
                        return true;
                    case R.id.salirFragment:
                        loadFragment(salir);
                        return true;
                }
                return true;
            }
        });
    }
}