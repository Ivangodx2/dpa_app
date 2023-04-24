package com.example.dpa_v6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class home_especialista extends AppCompatActivity {
    Home_fragment_e home_fragment_e = new Home_fragment_e();
    Pacientes_fragment_e pacientes_fragment_e = new Pacientes_fragment_e();
    //iniciar_sesion_especialista salir_sesion = new iniciar_sesion_especialista();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_especialista);

        BottomNavigationView navigation= findViewById(R.id.buttom_navi);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(home_fragment_e);

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home_fragment_esp:
                    loadFragment(home_fragment_e);
                    return true;
                case R.id.pacientes_fragment_es:
                    loadFragment(pacientes_fragment_e);
                    return true;
                case R.id.salirsesion_e:
                    //loadFragment(salir_sesion);
                    return true;
            }
            return false;
        }


    };

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,fragment);
        transaction.commit();
    }
}