package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ir_inicio_S_espe(View view){
        Intent inicios_s_es = new Intent( this, iniciar_sesion_especialista.class);
        startActivity(inicios_s_es);
    }
}