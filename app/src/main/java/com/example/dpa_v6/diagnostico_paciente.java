package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class diagnostico_paciente extends AppCompatActivity {

    Button info_diag;
    private XYPlot myplot;
    FirebaseFirestore db;
    private TextView nombre_paciente1;
    TextView DCuestionario,DEscucha,DVisualiza,DOprime,DIdentifica;
    String IDPacietne,R_cuestionario,R_escucha,R_identifica,R_oprime,R_visualiza,NombrePaciente2;
    int Numbr_Cuest,Numbr_Escucha,Numbr_Visuali,Numbr_Oprime,Numbr_Ident;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico_paciente);

        myplot= findViewById(R.id.xyzplot);
        nombre_paciente1= findViewById(R.id.textNombre_paciente);
        DCuestionario =findViewById(R.id.Diag_Cuestionario);
        DEscucha =findViewById(R.id.Diag_Escucha);
        DVisualiza = findViewById(R.id.Diag_Visualiza);
        DOprime = findViewById(R.id.Diag_Oprime);
        DIdentifica = findViewById(R.id.Diag_Identifica);
        info_diag = findViewById(R.id.info_diagnostico);

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

        info_diag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(diagnostico_paciente.this, popup_diagnostico.class));
            }
        });


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



        Number[] values1 = {Numbr_Cuest};
        Number[] values2 = {Numbr_Escucha};
        Number[] values3 = {Numbr_Visuali};
        Number[] values4 = {Numbr_Oprime};
        Number[] values5 = {Numbr_Ident};


        XYSeries series1 = new SimpleXYSeries(Arrays.asList(values1),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Sintomas");
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(values2),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Escucha");
        XYSeries series3 = new SimpleXYSeries(Arrays.asList(values3),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Visualiza");
        XYSeries series4 = new SimpleXYSeries(Arrays.asList(values4),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Oprime");
        XYSeries series5 = new SimpleXYSeries(Arrays.asList(values5),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Identifica");

        myplot.getRangeTitle().getLabelPaint().setTextSize(50f);

        if(Numbr_Cuest>30){
            DCuestionario.setText("Resultados de Test Sintomas: Ansiedad alta");
            MyBarFormatter barFormatter = new MyBarFormatter(Color.RED, Color.LTGRAY);
            myplot.addSeries(series1,barFormatter);
        } else if (Numbr_Cuest>=20) {
            DCuestionario.setText("Resultados de Test Sintomas: Ansiedad media alta");
            MyBarFormatter barFormatter = new MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
            myplot.addSeries(series1,barFormatter);
        } else if (Numbr_Cuest>=17) {
            DCuestionario.setText("Resultados de Test Sintomas: Ansiedad media");
            MyBarFormatter barFormatter = new MyBarFormatter(Color.YELLOW, Color.LTGRAY);
            myplot.addSeries(series1,barFormatter);
        } else if (Numbr_Cuest>=1) {
            DCuestionario.setText("Resultados de Test Sintomas: Ansiedad baja");
            MyBarFormatter barFormatter = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot.addSeries(series1,barFormatter);
        }

        if(Numbr_Escucha>30){
            DEscucha.setText("Resultados de test escucha: Ansiedad alta");
            MyBarFormatter barFormatter2 = new MyBarFormatter(Color.RED, Color.LTGRAY);
            myplot.addSeries(series2,barFormatter2);
        } else if (Numbr_Escucha>=20) {
            DEscucha.setText("Resultados de test escucha: Ansiedad media alta");
            MyBarFormatter barFormatter2 = new MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
            myplot.addSeries(series2,barFormatter2);
        } else if (Numbr_Escucha>=10) {
            DEscucha.setText("Resultados de test escucha: Ansiedad media");
            MyBarFormatter barFormatter2 = new MyBarFormatter(Color.YELLOW, Color.LTGRAY);
            myplot.addSeries(series2,barFormatter2);
        } else if (Numbr_Escucha>=1) {
            DEscucha.setText("Resultados de test escucha: Ansiedad baja");
            MyBarFormatter barFormatter2 = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot.addSeries(series2,barFormatter2);
        }

        if(Numbr_Visuali>27){
            DVisualiza.setText("Resultados de test visualiza: Ansiedad alta");
            MyBarFormatter barFormatter3 = new MyBarFormatter(Color.RED, Color.LTGRAY);
            myplot.addSeries(series3,barFormatter3);
        } else if (Numbr_Visuali>=16) {
            DVisualiza.setText("Resultados de test visualiza: Ansiedad media alta");
            MyBarFormatter barFormatter3 = new MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
            myplot.addSeries(series3,barFormatter3);
        } else if (Numbr_Visuali>=8) {
            DVisualiza.setText("Resultados de test visualiza: Ansiedad media");
            MyBarFormatter barFormatter3 = new MyBarFormatter(Color.YELLOW, Color.LTGRAY);
            myplot.addSeries(series3,barFormatter3);
        } else if (Numbr_Visuali>=1) {
            DVisualiza.setText("Resultados de test visualiza: Ansiedad baja");
            MyBarFormatter barFormatter3 = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot.addSeries(series3,barFormatter3);
        }

        if(Numbr_Oprime>10){
            DOprime.setText("Resultados de test oprime: Ansiedad baja");
            MyBarFormatter barFormatter4 = new MyBarFormatter(Color.RED, Color.LTGRAY);
            myplot.addSeries(series4,barFormatter4);
        } else if (Numbr_Oprime>=7) {
            DOprime.setText("Resultados de test oprime: Ansiedad media");
            MyBarFormatter barFormatter4 = new MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
            myplot.addSeries(series4,barFormatter4);
        } else if (Numbr_Oprime>=3) {
            DOprime.setText("Resultados de test oprime: Ansiedad media alta");
            MyBarFormatter barFormatter4 = new MyBarFormatter(Color.YELLOW, Color.LTGRAY);
            myplot.addSeries(series4,barFormatter4);
        } else if (Numbr_Oprime>=1) {
            DOprime.setText("Resultados de test oprime: Ansiedad alta");
            MyBarFormatter barFormatter4 = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot.addSeries(series4,barFormatter4);
        }

        if(Numbr_Ident>=35){
            DIdentifica.setText("Resultados de test escucha: Ansiedad alta");
            MyBarFormatter barFormatter5 = new MyBarFormatter(Color.RED, Color.LTGRAY);
            myplot.addSeries(series5,barFormatter5);
        } else if (Numbr_Ident>=30) {
            DIdentifica.setText("Resultados de test escucha: Ansiedad media alta");
            MyBarFormatter barFormatter5 = new MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
            myplot.addSeries(series5,barFormatter5);
        } else if (Numbr_Ident>=25) {
            DIdentifica.setText("Resultados de test escucha: Ansiedad media");
            MyBarFormatter barFormatter5 = new MyBarFormatter(Color.YELLOW, Color.LTGRAY);
            myplot.addSeries(series5,barFormatter5);
        } else if (Numbr_Ident>=5) {
            DIdentifica.setText("Resultados de test escucha: Ansiedad baja");
            MyBarFormatter barFormatter5 = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot.addSeries(series5,barFormatter5);
        }



        myplot.centerOnDomainOrigin(0);
        PanZoom.attach(myplot);

        myplot.setDomainBoundaries(0,3, BoundaryMode.FIXED);
        myplot.setRangeBoundaries(0,2000, BoundaryMode.FIXED);

        MyBarRenderer renderer = (MyBarRenderer)myplot.getRenderer(MyBarRenderer.class);
        renderer.setBarOrientation(BarRenderer.BarOrientation.SIDE_BY_SIDE);
        renderer.setBarGroupWidth(BarRenderer.BarGroupWidthMode.FIXED_GAP, 100);



    }

    class MyBarFormatter extends BarFormatter{
        public MyBarFormatter(int fillColor, int borderColor){
            super(fillColor,borderColor);
        }

        @Override
        public Class<? extends SeriesRenderer> getRendererClass(){
            return MyBarRenderer.class;
        }

        @Override
        public SeriesRenderer doGetRendererInstance(XYPlot plot){
            return new MyBarRenderer(plot);
        }
    }

    class MyBarRenderer extends BarRenderer<MyBarFormatter>{
        public MyBarRenderer(XYPlot plot){
            super(plot);
        }
    }


    public void salir_p(View view){
        Intent salir_a_home_paciente = new Intent( this, Home_pacientes.class);
        startActivity(salir_a_home_paciente);
        finish();
    }

}