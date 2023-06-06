package com.example.dpa_v6;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidplot.ui.SeriesRenderer;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class especialista_ver_paciente extends AppCompatActivity {

    TextView Nombre_paciente, DCuestionario_e,DEscucha_e,DVisualiza_e,DOprime_e,DIdentifica_e;
    private XYPlot myplot_e;
    ProgressBar PBNansiedad_e;
    String idpaciente;
    FirebaseFirestore db;

    int V_cuest, V_escucha,V_visuali,V_oprime,V_ident, count_PB_e,ResPB_e;
    public String S_cuest,S_escucha,S_visuali,S_oprime,S_ident;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especialista_ver_paciente);
        Nombre_paciente = findViewById(R.id.textNombre_paciente_ie);
        DCuestionario_e = findViewById(R.id.Diag_Cuestionario_ie);
        DEscucha_e = findViewById(R.id.Diag_Escucha_ie);
        DVisualiza_e = findViewById(R.id.Diag_Visualiza_ie);
        DOprime_e = findViewById(R.id.Diag_Oprime_ie);
        DIdentifica_e = findViewById(R.id.Diag_Identifica_ie);
        myplot_e = findViewById(R.id.xyzplot_ie);
        PBNansiedad_e = findViewById(R.id.progressBar_ansiedad_ie);

        //Recibir id
        db = FirebaseFirestore.getInstance();
        idpaciente = getIntent().getStringExtra("Idpaciente");
        db.collection("reg_paciente").document(idpaciente).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String nombre_p= documentSnapshot.getString("nombre");
                    Nombre_paciente.setText(nombre_p);

                    S_cuest = documentSnapshot.getString("puntaje_cuesti");
                    S_escucha = documentSnapshot.getString("puntaje_escuch");
                    S_visuali = documentSnapshot.getString("puntaje_vsual");
                    S_oprime = documentSnapshot.getString("puntuacion_J");
                    S_ident = documentSnapshot.getString("puntaje_identifica");

                    Integer numeroCuest_e = Integer.valueOf(S_cuest);
                    V_cuest = numeroCuest_e.intValue();

                    Integer numeroEscucha_e = Integer.valueOf(S_escucha);
                    V_escucha = numeroEscucha_e.intValue();
                    V_escucha= V_escucha*2;

                    Integer numeroOprime_e = Integer.valueOf(S_oprime);
                    V_oprime = 10;numeroOprime_e.intValue();

                    Integer numeroVisuali_e = Integer.valueOf(S_visuali);
                    V_visuali = 16;numeroVisuali_e.intValue();
                    V_visuali=V_visuali*2;

                    Integer numeroIdent_e = Integer.valueOf(S_ident);
                    V_ident = 5;numeroIdent_e.intValue();
                    V_ident= V_ident*5;

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

                    myplot_e.getRangeTitle().getLabelPaint().setTextSize(50f);

                    if(V_cuest>=30){
                        DCuestionario_e.setText("-Resultados de Test Sintomas: Ansiedad alta");
                        especialista_ver_paciente.MyBarFormatter barFormatter = new especialista_ver_paciente.MyBarFormatter(Color.RED, Color.LTGRAY);
                        myplot_e.addSeries(series1,barFormatter);
                        ResPB_e = ResPB_e+V_cuest;
                    } else if (V_cuest>=20) {
                        DCuestionario_e.setText("-Resultados de Test Sintomas: Ansiedad media alta");
                        especialista_ver_paciente.MyBarFormatter barFormatter = new especialista_ver_paciente.MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
                        myplot_e.addSeries(series1,barFormatter);
                        ResPB_e = ResPB_e+V_cuest;
                    } else if (V_cuest>=17) {
                        DCuestionario_e.setText("-Resultados de Test Sintomas: Ansiedad media");
                        especialista_ver_paciente.MyBarFormatter barFormatter = new especialista_ver_paciente.MyBarFormatter(Color.YELLOW, Color.LTGRAY);
                        myplot_e.addSeries(series1,barFormatter);
                        ResPB_e = ResPB_e+V_cuest;
                    } else if (V_cuest>=0) {
                        DCuestionario_e.setText("-Resultados de Test Sintomas: Ansiedad baja");
                        especialista_ver_paciente.MyBarFormatter barFormatter = new especialista_ver_paciente.MyBarFormatter(Color.GREEN, Color.LTGRAY);
                        myplot_e.addSeries(series1,barFormatter);
                        ResPB_e = ResPB_e+V_cuest;
                    }


                    if(V_escucha>=30){
                        DEscucha_e.setText("-Resultados de test escucha: Ansiedad alta");
                        especialista_ver_paciente.MyBarFormatter barFormatter2 = new especialista_ver_paciente.MyBarFormatter(Color.RED, Color.LTGRAY);
                        myplot_e.addSeries(series2,barFormatter2);
                        ResPB_e = ResPB_e+V_escucha;
                    } else if (V_escucha>=20) {
                        DEscucha_e.setText("-Resultados de test escucha: Ansiedad media alta");
                        especialista_ver_paciente.MyBarFormatter barFormatter2 = new especialista_ver_paciente.MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
                        myplot_e.addSeries(series2,barFormatter2);
                        ResPB_e = ResPB_e+V_escucha;
                    } else if (V_escucha>=10) {
                        DEscucha_e.setText("-Resultados de test escucha: Ansiedad media");
                        especialista_ver_paciente.MyBarFormatter barFormatter2 = new especialista_ver_paciente.MyBarFormatter(Color.YELLOW, Color.LTGRAY);
                        myplot_e.addSeries(series2,barFormatter2);
                        ResPB_e = ResPB_e+V_escucha;
                    } else if (V_escucha>=0) {
                        DEscucha_e.setText("-Resultados de test escucha: Ansiedad baja");
                        especialista_ver_paciente.MyBarFormatter barFormatter2 = new especialista_ver_paciente.MyBarFormatter(Color.GREEN, Color.LTGRAY);
                        myplot_e.addSeries(series2,barFormatter2);
                        ResPB_e = ResPB_e+V_escucha;
                    }

                    if(V_visuali>=27){
                        DVisualiza_e.setText("-Resultados de test visualiza: Ansiedad alta");
                        especialista_ver_paciente.MyBarFormatter barFormatter3 = new especialista_ver_paciente.MyBarFormatter(Color.RED, Color.LTGRAY);
                        myplot_e.addSeries(series3,barFormatter3);
                        ResPB_e = ResPB_e+V_visuali;
                    } else if (V_visuali>=16) {
                        DVisualiza_e.setText("-Resultados de test visualiza: Ansiedad media alta");
                        especialista_ver_paciente.MyBarFormatter barFormatter3 = new especialista_ver_paciente.MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
                        myplot_e.addSeries(series3,barFormatter3);
                        ResPB_e = ResPB_e+V_visuali;
                    } else if (V_visuali>=8) {
                        DVisualiza_e.setText("-Resultados de test visualiza: Ansiedad media");
                        especialista_ver_paciente.MyBarFormatter barFormatter3 = new especialista_ver_paciente.MyBarFormatter(Color.YELLOW, Color.LTGRAY);
                        myplot_e.addSeries(series3,barFormatter3);
                        ResPB_e = ResPB_e+V_visuali;
                    } else if (V_visuali>=0) {
                        DVisualiza_e.setText("-Resultados de test visualiza: Ansiedad baja");
                        especialista_ver_paciente.MyBarFormatter barFormatter3 = new especialista_ver_paciente.MyBarFormatter(Color.GREEN, Color.LTGRAY);
                        myplot_e.addSeries(series3,barFormatter3);
                        ResPB_e = ResPB_e+V_visuali;
                    }

                    if(V_oprime>10){
                        DOprime_e.setText("-Resultados de test oprime: Ansiedad baja");
                        especialista_ver_paciente.MyBarFormatter barFormatter4 = new especialista_ver_paciente.MyBarFormatter(Color.GREEN, Color.LTGRAY);
                        myplot_e.addSeries(series4,barFormatter4);
                    } else if (V_oprime>=7) {
                        DOprime_e.setText("-Resultados de test oprime: Ansiedad media");
                        especialista_ver_paciente.MyBarFormatter barFormatter4 = new especialista_ver_paciente.MyBarFormatter(Color.YELLOW, Color.LTGRAY);
                        myplot_e.addSeries(series4,barFormatter4);
                    } else if (V_oprime>=5) {
                        DOprime_e.setText("-Resultados de test oprime: Ansiedad media alta");
                        especialista_ver_paciente.MyBarFormatter barFormatter4 = new especialista_ver_paciente.MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
                        myplot_e.addSeries(series4,barFormatter4);
                    } else if (V_oprime>=2) {
                        DOprime_e.setText("-Resultados de test oprime: Ansiedad alta");
                        especialista_ver_paciente.MyBarFormatter barFormatter4 = new especialista_ver_paciente.MyBarFormatter(Color.RED, Color.LTGRAY);
                        myplot_e.addSeries(series4,barFormatter4);
                    }


                    if(V_ident>=35){
                        DIdentifica_e.setText("-Resultados de test identifica: Ansiedad alta");
                        especialista_ver_paciente.MyBarFormatter barFormatter5 = new especialista_ver_paciente.MyBarFormatter(Color.RED, Color.LTGRAY);
                        myplot_e.addSeries(series5,barFormatter5);
                        ResPB_e = ResPB_e+V_ident;
                    } else if (V_ident>=30) {
                        DIdentifica_e.setText("-Resultados de test identifica: Ansiedad media alta");
                        especialista_ver_paciente.MyBarFormatter barFormatter5 = new especialista_ver_paciente.MyBarFormatter(Color.rgb(255,100,0), Color.LTGRAY);
                        myplot_e.addSeries(series5,barFormatter5);
                        ResPB_e = ResPB_e+V_ident;
                    } else if (V_ident>=20) {
                        DIdentifica_e.setText("-Resultados de test identifica: Ansiedad media");
                        especialista_ver_paciente.MyBarFormatter barFormatter5 = new especialista_ver_paciente.MyBarFormatter(Color.YELLOW, Color.LTGRAY);
                        myplot_e.addSeries(series5,barFormatter5);
                        ResPB_e = ResPB_e+V_ident;
                    } else if (V_ident>=0) {
                        DIdentifica_e.setText("-Resultados de test identifica: Ansiedad baja");
                        especialista_ver_paciente.MyBarFormatter barFormatter5 = new especialista_ver_paciente.MyBarFormatter(Color.GREEN, Color.LTGRAY);
                        myplot_e.addSeries(series5,barFormatter5);
                        ResPB_e = ResPB_e+V_ident;
                    }



                    myplot_e.centerOnDomainOrigin(0);
                    PanZoom.attach(myplot_e);

                    myplot_e.setDomainBoundaries(-0.9,1, BoundaryMode.FIXED);
                    myplot_e.setRangeBoundaries(0,40, BoundaryMode.FIXED);

                    MyBarRenderer renderer = (MyBarRenderer)myplot_e.getRenderer(MyBarRenderer.class);
                    renderer.setBarOrientation(BarRenderer.BarOrientation.SIDE_BY_SIDE);
                    renderer.setBarGroupWidth(BarRenderer.BarGroupWidthMode.FIXED_GAP, 100);

                    Pbar_ansiedad_e();




                }
            }
        });




    }


    class MyBarFormatter extends BarFormatter {
        public MyBarFormatter(int fillColor, int borderColor){
            super(fillColor,borderColor);
        }

        @Override
        public Class<? extends SeriesRenderer> getRendererClass(){
            return especialista_ver_paciente.MyBarRenderer.class;
        }

        @Override
        public SeriesRenderer doGetRendererInstance(XYPlot plot){
            return new especialista_ver_paciente.MyBarRenderer(plot);
        }
    }

    class MyBarRenderer extends BarRenderer<especialista_ver_paciente.MyBarFormatter>{
        public MyBarRenderer(XYPlot plot){
            super(plot);
        }
    }



    public void Pbar_ansiedad_e(){
        ResPB_e = ((ResPB_e*100)/152);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                count_PB_e++;
                PBNansiedad_e.setProgress(count_PB_e);

                if (count_PB_e == ResPB_e){
                    timer.cancel();
                }

            }
        };
        timer.schedule(timerTask,0,100);
    }

}