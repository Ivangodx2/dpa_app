package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;

public class popup_cuestionario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_cuestionario);

        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int Ancho = medidasVentana.widthPixels;
        int Alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(Ancho*0.87), (int)(Alto*0.46));
    }
}