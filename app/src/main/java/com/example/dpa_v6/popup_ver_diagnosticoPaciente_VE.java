package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;

public class popup_ver_diagnosticoPaciente_VE extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_ver_diagnostico_paciente_ve);

        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int Ancho = medidasVentana.widthPixels;
        int Alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(Ancho*0.98), (int)(Alto*0.46));
    }
}