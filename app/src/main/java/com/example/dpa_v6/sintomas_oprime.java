package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class sintomas_oprime extends AppCompatActivity {

    Button jugar_btn, puntaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas_oprime);

        jugar_btn = findViewById(R.id.jugar_btn);
        puntaje = findViewById(R.id.button_Puntaje);

        jugar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Iniciando juego", Toast.LENGTH_SHORT).show();
            }
        });


        puntaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Iniciando juego", Toast.LENGTH_SHORT).show();
            }
        });

    }


}