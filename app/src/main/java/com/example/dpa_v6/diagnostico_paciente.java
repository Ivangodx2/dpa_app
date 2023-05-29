package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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

import java.util.Arrays;


public class diagnostico_paciente extends AppCompatActivity {

    private XYPlot myplot;
    FirebaseFirestore db;
    private TextView nombre_paciente1;
    String IDPacietne,R_cuestionario,R_escucha,R_identifica,R_oprime,R_visualiza,NombrePaciente2;
    int Numbr_Cuest,Numbr_Escucha,Numbr_Visuali,Numbr_Oprime,Numbr_Ident;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico_paciente);

        myplot= findViewById(R.id.xyzplot);
        nombre_paciente1= findViewById(R.id.textNombre_paciente);

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

        Integer numeroOprime = Integer.valueOf(R_oprime);
        Numbr_Oprime = numeroOprime.intValue();

        Integer numeroVisuali = Integer.valueOf(R_visualiza);
        Numbr_Visuali = numeroVisuali.intValue();

        Integer numeroIdent = Integer.valueOf(R_identifica);
        Numbr_Ident = numeroIdent.intValue();


        Number[] values1 = {Numbr_Cuest};
        Number[] values2 = {Numbr_Escucha*2};
        Number[] values3 = {Numbr_Visuali*2};
        Number[] values4 = {Numbr_Oprime};
        Number[] values5 = {Numbr_Ident*5};



        XYSeries series1 = new SimpleXYSeries(Arrays.asList(values1),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Sintomas");
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(values2),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Escucha");
        XYSeries series3 = new SimpleXYSeries(Arrays.asList(values3),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Visualiza");
        XYSeries series4 = new SimpleXYSeries(Arrays.asList(values4),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Oprime");
        XYSeries series5 = new SimpleXYSeries(Arrays.asList(values5),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Identifica");

        MyBarFormatter barFormatter = new MyBarFormatter(Color.RED, Color.LTGRAY);
        MyBarFormatter barFormatter2 = new MyBarFormatter(Color.BLUE, Color.GRAY);

        myplot.getRangeTitle().getLabelPaint().setTextSize(50f);

        myplot.addSeries(series1,barFormatter);
        myplot.addSeries(series2,barFormatter2);
        myplot.addSeries(series3,barFormatter);
        myplot.addSeries(series4,barFormatter2);
        myplot.addSeries(series5,barFormatter);

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