package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class sintomas_cuestionario_paciente extends AppCompatActivity {

    RadioButton opc1, opc2, opc3, opc4;
    TextView npregunta, txtpregunta;
    Button siguiente, btnsalir;

    int resultado_encu= 0;
    int Npregunta_p=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas_cuestionario_paciente);

        opc1 =(RadioButton)findViewById(R.id.radioButton);
        opc2 = (RadioButton)findViewById(R.id.radioButton2);
        opc3 = (RadioButton)findViewById(R.id.radioButton3);
        opc4= (RadioButton)findViewById(R.id.radioButton4);

        npregunta = (TextView) findViewById(R.id.Npregunta);
        txtpregunta=(TextView) findViewById(R.id.pregunta_sintomas);

        siguiente=(Button) findViewById(R.id.siguiente_preg);
        btnsalir=(Button) findViewById(R.id.salir);

    }

    public void siguientes(View view){
        if (opc1.isChecked() == false && opc2.isChecked() == false && opc3.isChecked() == false && opc4.isChecked() == false){
            Toast.makeText(getApplicationContext(), "Elija una opcion", Toast.LENGTH_SHORT).show();
        }else if(Npregunta_p == 1){
            if (opc1.isChecked()){
                resultado_encu= resultado_encu+1;
            }
            if (opc2.isChecked()){
                resultado_encu= resultado_encu+2;
            }
            if (opc3.isChecked()){
                resultado_encu= resultado_encu+3;
            }
            if (opc4.isChecked()){
                resultado_encu= resultado_encu+4;
            }
            opc1.setChecked(false);
            opc2.setChecked(false);
            opc3.setChecked(false);
            opc4.setChecked(false);

            Npregunta_p = 1 + Npregunta_p;
            //Reinicio de prguntas
            npregunta.setText("Prgunta 2");
            txtpregunta.setText("¿Te tiemblan la piernas?");
            opc1.setText("No");
            opc2.setText("Leve");
            opc3.setText("Moderado");
            opc4.setText("Bastante");



            //Se reinician las opciones
        }else if(Npregunta_p == 2){
            if (opc1.isChecked()){
                resultado_encu= resultado_encu+1;
            }
            if (opc2.isChecked()){
                resultado_encu= resultado_encu+2;
            }
            if (opc3.isChecked()){
                resultado_encu= resultado_encu+3;
            }
            if (opc4.isChecked()){
                resultado_encu= resultado_encu+4;
            }

            opc1.setChecked(false);
            opc2.setChecked(false);
            opc3.setChecked(false);
            opc4.setChecked(false);


            Npregunta_p = 1 + Npregunta_p;
            //Reinicio de prguntas
            npregunta.setText("Prgunta 3");
            txtpregunta.setText("¿Eres capaz de relajarte?");
            opc1.setText("No");
            opc2.setText("Leve");
            opc3.setText("Moderado");
            opc4.setText("Bastante");
            //Se reinician las opciones
        }else if(Npregunta_p == 3){
            if (opc1.isChecked()){
                resultado_encu= resultado_encu+1;
            }
            if (opc2.isChecked()){
                resultado_encu= resultado_encu+2;
            }
            if (opc3.isChecked()){
                resultado_encu= resultado_encu+3;
            }
            if (opc4.isChecked()){
                resultado_encu= resultado_encu+4;
            }
            opc1.setChecked(false);
            opc2.setChecked(false);
            opc3.setChecked(false);
            opc4.setChecked(false);
            Npregunta_p = 1 + Npregunta_p;
            //Reinicio de prguntas
            npregunta.setText("Prgunta 4");
            txtpregunta.setText("¿Tiendes a temer a que ocurra lo peor?");
            opc1.setText("No");
            opc2.setText("Leve");
            opc3.setText("Moderado");
            opc4.setText("Bastante");
            //Se reinician las opciones
        }else if(Npregunta_p == 4){
            if (opc1.isChecked()){
                resultado_encu= resultado_encu+1;
            }
            if (opc2.isChecked()){
                resultado_encu= resultado_encu+2;
            }
            if (opc3.isChecked()){
                resultado_encu= resultado_encu+3;
            }
            if (opc4.isChecked()){
                resultado_encu= resultado_encu+4;
            }
            opc1.setChecked(false);
            opc2.setChecked(false);
            opc3.setChecked(false);
            opc4.setChecked(false);
            Npregunta_p = 1 + Npregunta_p;
            //Reinicio de prguntas
            npregunta.setText("Prgunta 5");
            txtpregunta.setText("¿Tiendes atener latidos fuertes en tu corazón?");
            opc1.setText("No");
            opc2.setText("Leve");
            opc3.setText("Moderado");
            opc4.setText("Bastante");
            //Se reinician las opciones
        }else if(Npregunta_p == 5){
            if (opc1.isChecked()){
                resultado_encu= resultado_encu+1;
            }
            if (opc2.isChecked()){
                resultado_encu= resultado_encu+2;
            }
            if (opc3.isChecked()){
                resultado_encu= resultado_encu+3;
            }
            if (opc4.isChecked()){
                resultado_encu= resultado_encu+4;
            }
            opc1.setChecked(false);
            opc2.setChecked(false);
            opc3.setChecked(false);
            opc4.setChecked(false);
            Npregunta_p = 1 + Npregunta_p;
            //Reinicio de prguntas
            npregunta.setText("Prgunta 6");
            txtpregunta.setText("¿Tiendes ponerte nervioso?");
            opc1.setText("No");
            opc2.setText("Leve");
            opc3.setText("Moderado");
            opc4.setText("Bastante");
            //Se reinician las opciones
        }else if(Npregunta_p == 6){
            if (opc1.isChecked()){
                resultado_encu= resultado_encu+1;
            }
            if (opc2.isChecked()){
                resultado_encu= resultado_encu+2;
            }
            if (opc3.isChecked()){
                resultado_encu= resultado_encu+3;
            }
            if (opc4.isChecked()){
                resultado_encu= resultado_encu+4;
            }

            npregunta.setText("nota obtenida "+resultado_encu);
            siguiente.setVisibility(View.GONE);
            opc1.setVisibility(View.GONE);
            opc2.setVisibility(View.GONE);
            opc3.setVisibility(View.GONE);
            opc4.setVisibility(View.GONE);


        }

        }

        public void Exite(View view){
        finish();
        }

}
