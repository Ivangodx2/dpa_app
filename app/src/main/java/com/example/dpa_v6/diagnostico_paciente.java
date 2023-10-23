package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidplot.ui.SeriesRenderer;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class diagnostico_paciente extends AppCompatActivity {

    ProgressBar PBNansiedad;
    FirebaseFirestore db;
    private TextView nombre_paciente1;
    TextView DCuestionario,DEscucha,DVisualiza,DOprime,DIdentifica;
    String IDPacietne,R_cuestionario,R_escucha,R_identifica,R_oprime,R_visualiza,NombrePaciente2;
    int Numbr_Cuest,Numbr_Escucha,Numbr_Visuali,Numbr_Oprime,Numbr_Ident,count_PB,ResPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico_paciente);

        nombre_paciente1= findViewById(R.id.textNombre_paciente);
        DCuestionario =findViewById(R.id.Diag_Cuestionario);
        DEscucha =findViewById(R.id.Diag_Escucha);
        DVisualiza = findViewById(R.id.Diag_Visualiza);
        DOprime = findViewById(R.id.Diag_Oprime);
        DIdentifica = findViewById(R.id.Diag_Identifica);
        PBNansiedad = findViewById(R.id.progressBar_ansiedad);

        //Recibir datos
        db = FirebaseFirestore.getInstance();
        Bundle intent = getIntent().getExtras();
        IDPacietne = getIntent().getStringExtra("IDPaciente");
        R_cuestionario= intent.getString("puntaje_cuesti");
        R_escucha = intent.getString("puntaje_escuch");
        R_oprime = intent.getString("puntuacion_J");
        R_visualiza = intent.getString("puntaje_vsual");
        R_identifica = intent.getString("puntaje_identifica");
        NombrePaciente2= intent.getString("nombre");
        nombre_paciente1.setText(NombrePaciente2);


        Integer numeroCuest = Integer.valueOf(R_cuestionario);
        Numbr_Cuest = numeroCuest.intValue();

        Integer numeroEscucha = Integer.valueOf(R_escucha);
        Numbr_Escucha = numeroEscucha.intValue();
        Numbr_Escucha= Numbr_Escucha*2;

        Integer numeroOprime = Integer.valueOf(R_oprime);
        Numbr_Oprime = numeroOprime.intValue();

        Integer numeroVisuali = Integer.valueOf(R_visualiza);
        Numbr_Visuali = numeroVisuali.intValue();
        Numbr_Visuali=Numbr_Visuali*2;

        Integer numeroIdent = Integer.valueOf(R_identifica);
        Numbr_Ident = numeroIdent.intValue();
        Numbr_Ident= Numbr_Ident*5;


        if(Numbr_Cuest>=30){
            DCuestionario.setText("-Resultados de Test Sintomas: Ansiedad alta");

            ResPB = ResPB+Numbr_Cuest;
        } else if (Numbr_Cuest>=20) {
            DCuestionario.setText("-Resultados de Test Sintomas: Ansiedad media alta");

            ResPB = ResPB+Numbr_Cuest;
        } else if (Numbr_Cuest>=17) {
            DCuestionario.setText("-Resultados de Test Sintomas: Ansiedad media");

            ResPB = ResPB+Numbr_Cuest;
        } else if (Numbr_Cuest>=0) {
            DCuestionario.setText("-Resultados de Test Sintomas: Ansiedad baja");

            ResPB = ResPB+Numbr_Cuest;
        }

        if(Numbr_Escucha>=30){
            DEscucha.setText("-Resultados de test escucha: Ansiedad alta");

            ResPB = ResPB+Numbr_Escucha;
        } else if (Numbr_Escucha>=20) {
            DEscucha.setText("-Resultados de test escucha: Ansiedad media alta");

            ResPB = ResPB+Numbr_Escucha;
        } else if (Numbr_Escucha>=10) {
            DEscucha.setText("-Resultados de test escucha: Ansiedad media");

            ResPB = ResPB+Numbr_Escucha;
        } else if (Numbr_Escucha>=0) {
            DEscucha.setText("-Resultados de test escucha: Ansiedad baja");

            ResPB = ResPB+Numbr_Escucha;
        }

        if(Numbr_Visuali>=27){
            DVisualiza.setText("-Resultados de test visualiza: Ansiedad alta");

            ResPB = ResPB+Numbr_Visuali;
        } else if (Numbr_Visuali>=16) {
            DVisualiza.setText("-Resultados de test visualiza: Ansiedad media alta");

            ResPB = ResPB+Numbr_Visuali;
        } else if (Numbr_Visuali>=8) {
            DVisualiza.setText("-Resultados de test visualiza: Ansiedad media");

            ResPB = ResPB+Numbr_Visuali;
        } else if (Numbr_Visuali>=0) {
            DVisualiza.setText("-Resultados de test visualiza: Ansiedad baja");

            ResPB = ResPB+Numbr_Visuali;
        }

        if(Numbr_Oprime>10){
            DOprime.setText("-Resultados de test oprime: Ansiedad baja");

        } else if (Numbr_Oprime>=7) {
            DOprime.setText("-Resultados de test oprime: Ansiedad media");

        } else if (Numbr_Oprime>=5) {
            DOprime.setText("-Resultados de test oprime: Ansiedad media alta");

        } else if (Numbr_Oprime>=2) {
            DOprime.setText("-Resultados de test oprime: Ansiedad alta");

        }

        if(Numbr_Ident>=35){
            DIdentifica.setText("-Resultados de test identifica: Ansiedad alta");

            ResPB = ResPB+Numbr_Ident;
        } else if (Numbr_Ident>=30) {
            DIdentifica.setText("-Resultados de test identifica: Ansiedad media alta");

            ResPB = ResPB+Numbr_Ident;
        } else if (Numbr_Ident>=20) {
            DIdentifica.setText("-Resultados de test identifica: Ansiedad media");

            ResPB = ResPB+Numbr_Ident;
        } else if (Numbr_Ident>=0) {
            DIdentifica.setText("-Resultados de test identifica: Ansiedad baja");

            ResPB = ResPB+Numbr_Ident;
        }
        Pbar_ansiedad();
        GuardarDatosPA();

    }


    public void Pbar_ansiedad(){

        if(ResPB==0){
            ResPB=0;
        }else {
            ResPB = ((ResPB*100)/152);
        }
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                PBNansiedad.setProgress(count_PB);
                if (count_PB == ResPB){
                    timer.cancel();
                }
                count_PB++;
            }
        };
        timer.schedule(timerTask,0,100);
    }

    private void GuardarDatosPA(){
        //Actualizar base de datos
        String puntuacionPaciente = Integer.toString(ResPB);

        Map<String,Object> map = new HashMap<>();
        map.put("porcentaje_A",puntuacionPaciente);
        db.collection("reg_paciente").document(IDPacietne).update(map);
    }

    public void salir_p(View view){
        this.finish();
    }


}