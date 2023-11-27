package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.ui.SeriesRenderer;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class especialista_ver_diagnostico_paciente extends AppCompatActivity {

    TextView Nombre_paciente, DCuestionario_e,DEscucha_e,DVisualiza_e,DOprime_e,DIdentifica_e,Infor_grafica;
    private XYPlot myplot_E;
    ProgressBar PBNansiedad_e,PBGrafica;
    String idpaciente;
    FirebaseFirestore db;
    Button enviar_correo, info_diagPaciente_VE;

    int V_cuest, V_escucha,V_visuali,V_oprime,V_ident, count_PB_e,ResPB_e;
    public String S_cuest,S_escucha,S_visuali,S_oprime,S_ident;
    private static final int TIEMPO_CARGA = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especialista_ver_paciente);
        Nombre_paciente = findViewById(R.id.textNombre_paciente_ie);
        DCuestionario_e = findViewById(R.id.Diag_Cuestionario_ie);
        DEscucha_e = findViewById(R.id.Diag_Escucha_ie);
        DVisualiza_e = findViewById(R.id.Diag_Visualiza_ie);
        DOprime_e = findViewById(R.id.Diag_Oprime_ie);
        Infor_grafica=findViewById(R.id.Info_grafica);
        DIdentifica_e = findViewById(R.id.Diag_Identifica_ie);
        myplot_E = findViewById(R.id.xyzplot_Vis_E);
        PBNansiedad_e = findViewById(R.id.progressBar_ansiedad_ie);
        enviar_correo = findViewById(R.id.enviar_mcp);
        info_diagPaciente_VE = findViewById(R.id.info_diagnosticoPaciente_VE);
        PBGrafica = findViewById(R.id.PB_Grafica);


        PBGrafica.setVisibility(View.VISIBLE);
        myplot_E.setVisibility(View.GONE);
        PBNansiedad_e.setVisibility(View.GONE);
        Nombre_paciente.setVisibility(View.GONE);
        Infor_grafica.setVisibility(View.GONE);
        DCuestionario_e.setVisibility(View.GONE);
        DEscucha_e.setVisibility(View.GONE);
        DVisualiza_e.setVisibility(View.GONE);
        DOprime_e.setVisibility(View.GONE);
        DIdentifica_e.setVisibility(View.GONE);


        //Recibir id
        db = FirebaseFirestore.getInstance();
        idpaciente = getIntent().getStringExtra("Idpaciente");


        info_diagPaciente_VE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(especialista_ver_diagnostico_paciente.this, popup_ver_diagnosticoPaciente_VE.class));
            }
        });
        db.collection("reg_paciente").document(idpaciente).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                        // Obtén los valores de documentSnapshot
                        String nombre_p = documentSnapshot.getString("nombre");
                        S_cuest = documentSnapshot.getString("puntaje_cuesti");
                        S_escucha = documentSnapshot.getString("puntaje_escuch");
                        S_visuali = documentSnapshot.getString("puntaje_vsual");
                        S_oprime = documentSnapshot.getString("puntuacion_J");
                        S_ident = documentSnapshot.getString("puntaje_identifica");
                        Nombre_paciente.setText("P. " + nombre_p);

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            PBGrafica.setVisibility(View.GONE);

                            // Muestra la gráfica
                            myplot_E.setVisibility(View.VISIBLE);
                            PBNansiedad_e.setVisibility(View.VISIBLE);
                            Nombre_paciente.setVisibility(View.VISIBLE);
                            Infor_grafica.setVisibility(View.VISIBLE);
                            DCuestionario_e.setVisibility(View.VISIBLE);
                            DEscucha_e.setVisibility(View.VISIBLE);
                            DVisualiza_e.setVisibility(View.VISIBLE);
                            DOprime_e.setVisibility(View.VISIBLE);
                            DIdentifica_e.setVisibility(View.VISIBLE);

                            GenerarGrafica(S_cuest, S_escucha, S_oprime, S_visuali, S_ident);
                        }
                    }, TIEMPO_CARGA);


                }
            }

        });

        enviar_correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),especialista_generar_correo.class);
                intent.putExtra("Idpaciente",idpaciente);
                startActivity(intent);
            }
        });




    }


    private void GenerarGrafica(String S_cuest, String S_escucha, String S_oprime,String S_visuali,String S_ident) {

        // Realizar la conversión y procesamiento aquí
        Integer numeroCuest_e = Integer.valueOf(S_cuest);
        V_cuest = numeroCuest_e.intValue();

        Integer numeroEscucha_e = Integer.valueOf(S_escucha);
        V_escucha = numeroEscucha_e.intValue();
        V_escucha = V_escucha * 2;

        Integer numeroOprime_e = Integer.valueOf(S_oprime);
        V_oprime = numeroOprime_e.intValue();

        Integer numeroVisuali_e = Integer.valueOf(S_visuali);
        V_visuali = numeroVisuali_e.intValue();
        V_visuali = V_visuali * 2;

        Integer numeroIdent_e = Integer.valueOf(S_ident);
        V_ident = numeroIdent_e.intValue();
        V_ident = V_ident * 5;


        Number[] values1 = {V_cuest};
        Number[] values2 = {V_escucha};
        Number[] values3 = {V_visuali};
        Number[] values4 = {V_oprime};
        Number[] values5 = {V_ident};


        XYSeries series1 = new SimpleXYSeries(Arrays.asList(values1),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "S");
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(values2),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "E");
        XYSeries series3 = new SimpleXYSeries(Arrays.asList(values3),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "V");
        XYSeries series4 = new SimpleXYSeries(Arrays.asList(values4),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "O");
        XYSeries series5 = new SimpleXYSeries(Arrays.asList(values5),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "I");

        myplot_E.getRangeTitle().getLabelPaint().setTextSize(50f);

        if(V_cuest>=30){
            DCuestionario_e.setText("-Resultados de Test Sintomas: Ansiedad alta");
            MyBarFormatter barFormatter = new MyBarFormatter(Color.RED, Color.LTGRAY);
            myplot_E.addSeries(series1,barFormatter);
            ResPB_e = ResPB_e+V_cuest;
        } else if (V_cuest>=20) {
            DCuestionario_e.setText("-Resultados de Test Sintomas: Ansiedad media alta");
            MyBarFormatter barFormatter = new MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
            myplot_E.addSeries(series1,barFormatter);
            ResPB_e = ResPB_e+V_cuest;
        } else if (V_cuest>=17) {
            DCuestionario_e.setText("-Resultados de Test Sintomas: Ansiedad media");
            MyBarFormatter barFormatter = new MyBarFormatter(Color.YELLOW, Color.LTGRAY);
            myplot_E.addSeries(series1,barFormatter);
            ResPB_e = ResPB_e+V_cuest;
        } else if (V_cuest>0) {
            DCuestionario_e.setText("-Resultados de Test Sintomas: Ansiedad baja");
            MyBarFormatter barFormatter = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot_E.addSeries(series1,barFormatter);
            ResPB_e = ResPB_e+V_cuest;
        }else if (V_cuest==0) {
            DCuestionario_e.setText("-Resultados de Test Sintomas: Sin resultados");
            MyBarFormatter barFormatter = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot_E.addSeries(series1,barFormatter);
        }


        if(V_escucha>=30){
            DEscucha_e.setText("-Resultados de Test Escucha: Ansiedad alta");
            MyBarFormatter barFormatter2 = new MyBarFormatter(Color.RED, Color.LTGRAY);
            myplot_E.addSeries(series2,barFormatter2);
            ResPB_e = ResPB_e+V_escucha;
        } else if (V_escucha>=20) {
            DEscucha_e.setText("-Resultados de Test Escucha: Ansiedad media alta");
            MyBarFormatter barFormatter2 = new MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
            myplot_E.addSeries(series2,barFormatter2);
            ResPB_e = ResPB_e+V_escucha;
        } else if (V_escucha>=10) {
            DEscucha_e.setText("-Resultados de Test Escucha: Ansiedad media");
            MyBarFormatter barFormatter2 = new MyBarFormatter(Color.YELLOW, Color.LTGRAY);
            myplot_E.addSeries(series2,barFormatter2);
            ResPB_e = ResPB_e+V_escucha;
        } else if (V_escucha>0) {
            DEscucha_e.setText("-Resultados de Test Escucha: Ansiedad baja");
            MyBarFormatter barFormatter2 = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot_E.addSeries(series2,barFormatter2);
            ResPB_e = ResPB_e+V_escucha;
        }else if (V_escucha==0) {
            DEscucha_e.setText("-Resultados de Test Escucha: Sin resultados");
            MyBarFormatter barFormatter2 = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot_E.addSeries(series2,barFormatter2);}

        if(V_visuali>=27){
            DVisualiza_e.setText("-Resultados de Test Visualiza: Ansiedad alta");
            MyBarFormatter barFormatter3 = new MyBarFormatter(Color.RED, Color.LTGRAY);
            myplot_E.addSeries(series3,barFormatter3);
            ResPB_e = ResPB_e+V_visuali;
        } else if (V_visuali>=16) {
            DVisualiza_e.setText("-Resultados de Test Visualiza: Ansiedad media alta");
            MyBarFormatter barFormatter3 = new MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
            myplot_E.addSeries(series3,barFormatter3);
            ResPB_e = ResPB_e+V_visuali;
        } else if (V_visuali>=8) {
            DVisualiza_e.setText("-Resultados de Test Visualiza: Ansiedad media");
            MyBarFormatter barFormatter3 = new MyBarFormatter(Color.YELLOW, Color.LTGRAY);
            myplot_E.addSeries(series3,barFormatter3);
            ResPB_e = ResPB_e+V_visuali;
        } else if (V_visuali>0) {
            DVisualiza_e.setText("-Resultados de Test Visualiza: Ansiedad baja");
            MyBarFormatter barFormatter3 = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot_E.addSeries(series3,barFormatter3);
            ResPB_e = ResPB_e+V_visuali;
        }else if (V_visuali==0) {
            DVisualiza_e.setText("-Resultados de Test Visualiza: Sin resultados");
            MyBarFormatter barFormatter3 = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot_E.addSeries(series3,barFormatter3);
        }

        if(V_oprime>10){
            DOprime_e.setText("-Resultados de Test Oprime: Ansiedad baja");
            MyBarFormatter barFormatter4 = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot_E.addSeries(series4,barFormatter4);
        } else if (V_oprime>=7) {
            DOprime_e.setText("-Resultados de Test Oprime: Ansiedad media");
            MyBarFormatter barFormatter4 = new MyBarFormatter(Color.YELLOW, Color.LTGRAY);
            myplot_E.addSeries(series4,barFormatter4);
        } else if (V_oprime>=5) {
            DOprime_e.setText("-Resultados de Test Oprime: Ansiedad media alta");
            MyBarFormatter barFormatter4 = new MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
            myplot_E.addSeries(series4,barFormatter4);
        } else if (V_oprime>=1) {
            DOprime_e.setText("-Resultados de Test Oprime: Ansiedad alta");
            MyBarFormatter barFormatter4 = new MyBarFormatter(Color.RED, Color.LTGRAY);
            myplot_E.addSeries(series4,barFormatter4);
        }else if (V_oprime==0){
            DOprime_e.setText("-Resultados de Test Oprime: Sin resultados");
            MyBarFormatter barFormatter4 = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot_E.addSeries(series4,barFormatter4);
        }


        if(V_ident>=35){
            DIdentifica_e.setText("-Resultados de Test Identifica: Ansiedad alta");
            MyBarFormatter barFormatter5 = new MyBarFormatter(Color.RED, Color.LTGRAY);
            myplot_E.addSeries(series5,barFormatter5);
            ResPB_e = ResPB_e+V_ident;
        } else if (V_ident>=30) {
            DIdentifica_e.setText("-Resultados de Test Identifica: Ansiedad media alta");
            MyBarFormatter barFormatter5 = new MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
            myplot_E.addSeries(series5,barFormatter5);
            ResPB_e = ResPB_e+V_ident;
        } else if (V_ident>=20) {
            DIdentifica_e.setText("-Resultados de Test Identifica: Ansiedad media");
            MyBarFormatter barFormatter5 = new MyBarFormatter(Color.YELLOW, Color.LTGRAY);
            myplot_E.addSeries(series5,barFormatter5);
            ResPB_e = ResPB_e+V_ident;
        } else if (V_ident>0) {
            DIdentifica_e.setText("-Resultados de Test Identifica: Ansiedad baja");
            MyBarFormatter barFormatter5 = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot_E.addSeries(series5,barFormatter5);
            ResPB_e = ResPB_e+V_ident;
        }
        else if (V_ident==0) {
            DIdentifica_e.setText("-Resultados de Test Identifica: Sin resultados");
            MyBarFormatter barFormatter5 = new MyBarFormatter(Color.GREEN, Color.LTGRAY);
            myplot_E.addSeries(series5,barFormatter5);
        }



        myplot_E.centerOnDomainOrigin(0);
        PanZoom.attach(myplot_E);

        myplot_E.setDomainBoundaries(-0.9,1, BoundaryMode.FIXED);
        myplot_E.setRangeBoundaries(0,40, BoundaryMode.FIXED);


        MyBarRenderer renderer = (MyBarRenderer)myplot_E.getRenderer(MyBarRenderer.class);
        renderer.setBarOrientation(BarRenderer.BarOrientation.SIDE_BY_SIDE);
        renderer.setBarGroupWidth(BarRenderer.BarGroupWidthMode.FIXED_GAP, 100);

        Pbar_ansiedad_e();
    }


    class MyBarFormatter extends BarFormatter{
        public MyBarFormatter(int fillColor, int borderColor){
            super(fillColor,borderColor);
        }

        @Override
        public Class<? extends SeriesRenderer> getRendererClass(){
            return especialista_ver_diagnostico_paciente.MyBarRenderer.class;
        }

        @Override
        public SeriesRenderer doGetRendererInstance(XYPlot plot){
            return new especialista_ver_diagnostico_paciente.MyBarRenderer(plot);
        }
    }

    class MyBarRenderer extends BarRenderer<MyBarFormatter>{
        public MyBarRenderer(XYPlot plot){
            super(plot);
        }
    }


    public void Pbar_ansiedad_e(){

        if(ResPB_e==0){
            ResPB_e=1;
        }else {
            ResPB_e = ((ResPB_e*100)/152);
        }
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                PBNansiedad_e.setProgress(count_PB_e);
                if (count_PB_e == ResPB_e){
                    timer.cancel();
                }
                count_PB_e++;

            }
        };
        timer.schedule(timerTask,0,100);
    }

    public void salir_e(View view){
        finish();
    }





}