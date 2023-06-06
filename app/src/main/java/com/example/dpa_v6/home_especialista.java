package com.example.dpa_v6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarView;

public class home_especialista extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_especialista);

        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navi);
        bottomNavigationView.setSelectedItemId(R.id.home_esp);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_esp:
                    return true;

                case R.id.pacientes_es:
                    Intent intent2 = new Intent(getApplicationContext(), especialista_pacientes.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivityForResult(intent2, 0);
                    overridePendingTransition(0,0);
                    finish();
                    return true;

                case R.id.comprobar_es:
                    Intent intent3 = new Intent(getApplicationContext(), especialista_comprobar.class);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivityForResult(intent3, 0);
                    overridePendingTransition(0,0);
                    finish();
                    return true;
            }
            return false;
        });
    }


}