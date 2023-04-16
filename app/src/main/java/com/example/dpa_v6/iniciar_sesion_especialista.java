package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class iniciar_sesion_especialista extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion_especialista);
    }

    public void ir_registro_especialista(View view){
        Intent ir_registro_especialista = new Intent( this, registrarse_especialista.class);
        startActivity(ir_registro_especialista);
    }
}