package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class diagnostico_paciente_grafica extends AppCompatActivity {


    Button info_diag;
    private XYPlot myplot;
    FirebaseFirestore db;
    private TextView nombre_paciente_grafica;
    String IDPacietne,R_cuestionario,R_escucha,R_identifica,R_oprime,R_visualiza,NombrePaciente2;
    int Numbr_Cuest,Numbr_Escucha,Numbr_Visuali,Numbr_Oprime,Numbr_Ident,count_PB,ResPB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico_paciente_grafica);

        myplot= findViewById(R.id.xyzplot_DF);
        nombre_paciente_grafica= findViewById(R.id.textNombre_paciente_GF);
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
        nombre_paciente_grafica.setText(NombrePaciente2);

        info_diag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(diagnostico_paciente_grafica.this, popup_diagnostico.class));
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


        XYSeries series1 = new SimpleXYSeries(Arrays.asList(values1),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "S");
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(values2),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "E");
        XYSeries series3 = new SimpleXYSeries(Arrays.asList(values3),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "V");
        XYSeries series4 = new SimpleXYSeries(Arrays.asList(values4),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "O");
        XYSeries series5 = new SimpleXYSeries(Arrays.asList(values5),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "I");

        myplot.getRangeTitle().getLabelPaint().setTextSize(50f);

        if(Numbr_Cuest>=30){

            MyBarFormatter barFormatter = new MyBarFormatter(Color.RED, Color.LTGRAY);
            myplot.addSeries(series1,barFormatter);
            ResPB = ResPB+Numbr_Cuest;
        } else if (Numbr_Cuest>=20) {

            MyBarFormatter barFormatter = new MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
            myplot.addSeries(series1,barFormatter);
            ResPB = ResPB+Numbr_Cuest;
        } else if (Numbr_Cuest>=17) {

            MyBarFormatter barFormatter = new MyBarFormatter(Color.YELLOW, Color.LTGRAY);
            myplot.addSeries(series1,barFormatter);
            ResPB = ResPB+Numbr_Cuest;
        } else if (Numbr_Cuest>=0) {

            MyBarFormatter barFormatter = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot.addSeries(series1,barFormatter);
            ResPB = ResPB+Numbr_Cuest;
        }

        if(Numbr_Escucha>=30){

            MyBarFormatter barFormatter2 = new MyBarFormatter(Color.RED, Color.LTGRAY);
            myplot.addSeries(series2,barFormatter2);
            ResPB = ResPB+Numbr_Escucha;
        } else if (Numbr_Escucha>=20) {

            MyBarFormatter barFormatter2 = new MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
            myplot.addSeries(series2,barFormatter2);
            ResPB = ResPB+Numbr_Escucha;
        } else if (Numbr_Escucha>=10) {

            MyBarFormatter barFormatter2 = new MyBarFormatter(Color.YELLOW, Color.LTGRAY);
            myplot.addSeries(series2,barFormatter2);
            ResPB = ResPB+Numbr_Escucha;
        } else if (Numbr_Escucha>=0) {

            MyBarFormatter barFormatter2 = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot.addSeries(series2,barFormatter2);
            ResPB = ResPB+Numbr_Escucha;
        }

        if(Numbr_Visuali>=27){

            MyBarFormatter barFormatter3 = new MyBarFormatter(Color.RED, Color.LTGRAY);
            myplot.addSeries(series3,barFormatter3);
            ResPB = ResPB+Numbr_Visuali;
        } else if (Numbr_Visuali>=16) {

            MyBarFormatter barFormatter3 = new MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
            myplot.addSeries(series3,barFormatter3);
            ResPB = ResPB+Numbr_Visuali;
        } else if (Numbr_Visuali>=8) {

            MyBarFormatter barFormatter3 = new MyBarFormatter(Color.YELLOW, Color.LTGRAY);
            myplot.addSeries(series3,barFormatter3);
            ResPB = ResPB+Numbr_Visuali;
        } else if (Numbr_Visuali>=0) {

            MyBarFormatter barFormatter3 = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot.addSeries(series3,barFormatter3);
            ResPB = ResPB+Numbr_Visuali;
        }

        if(Numbr_Oprime>10){
            MyBarFormatter barFormatter4 = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot.addSeries(series4,barFormatter4);
        } else if (Numbr_Oprime>=7) {

            MyBarFormatter barFormatter4 = new MyBarFormatter(Color.YELLOW, Color.LTGRAY);
            myplot.addSeries(series4,barFormatter4);
        } else if (Numbr_Oprime>=5) {

            MyBarFormatter barFormatter4 = new MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
            myplot.addSeries(series4,barFormatter4);
        } else if (Numbr_Oprime>=2) {

            MyBarFormatter barFormatter4 = new MyBarFormatter(Color.RED, Color.LTGRAY);
            myplot.addSeries(series4,barFormatter4);
        }

        if(Numbr_Ident>=35){

            MyBarFormatter barFormatter5 = new MyBarFormatter(Color.RED, Color.LTGRAY);
            myplot.addSeries(series5,barFormatter5);
            ResPB = ResPB+Numbr_Ident;
        } else if (Numbr_Ident>=30) {

            MyBarFormatter barFormatter5 = new MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
            myplot.addSeries(series5,barFormatter5);
            ResPB = ResPB+Numbr_Ident;
        } else if (Numbr_Ident>=20) {

            MyBarFormatter barFormatter5 = new MyBarFormatter(Color.YELLOW, Color.LTGRAY);
            myplot.addSeries(series5,barFormatter5);
            ResPB = ResPB+Numbr_Ident;
        } else if (Numbr_Ident>=0) {

            MyBarFormatter barFormatter5 = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot.addSeries(series5,barFormatter5);
            ResPB = ResPB+Numbr_Ident;
        }

        myplot.centerOnDomainOrigin(0);
        PanZoom.attach(myplot);

        myplot.setDomainBoundaries(-0.9,1, BoundaryMode.FIXED);
        myplot.setRangeBoundaries(0,40, BoundaryMode.FIXED);

        MyBarRenderer renderer = (MyBarRenderer)myplot.getRenderer(MyBarRenderer.class);
        renderer.setBarOrientation(BarRenderer.BarOrientation.SIDE_BY_SIDE);
        renderer.setBarGroupWidth(BarRenderer.BarGroupWidthMode.FIXED_GAP, 100);
        ResPB = ((ResPB*100)/152);
        GuardarDatosPA();

    }

    class MyBarFormatter extends BarFormatter{
        public MyBarFormatter(int fillColor, int borderColor){
            super(fillColor,borderColor);
        }

        @Override
        public Class<? extends SeriesRenderer> getRendererClass(){
            return diagnostico_paciente_grafica.MyBarRenderer.class;
        }

        @Override
        public SeriesRenderer doGetRendererInstance(XYPlot plot){
            return new diagnostico_paciente_grafica.MyBarRenderer(plot);
        }
    }

    class MyBarRenderer extends BarRenderer<MyBarFormatter>{
        public MyBarRenderer(XYPlot plot){
            super(plot);
        }
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