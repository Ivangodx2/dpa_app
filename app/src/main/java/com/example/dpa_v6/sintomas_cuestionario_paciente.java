package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class sintomas_cuestionario_paciente extends AppCompatActivity {

    RadioButton rP1_1, rP1_2, rP1_3, rP1_4,rP2_1, rP2_2, rP2_3, rP2_4,rP3_1, rP3_2, rP3_3, rP3_4,rP4_1, rP4_2, rP4_3, rP4_4;
    RadioButton rP5_1, rP5_2, rP5_3, rP5_4,rP6_1, rP6_2, rP6_3, rP6_4,rP7_1, rP7_2, rP7_3, rP7_4,rP8_1, rP8_2, rP8_3, rP8_4, rP9_1, rP9_2, rP9_3, rP9_4,rP10_1, rP10_2, rP10_3, rP10_4;
    TextView npregunta, txtpregunta;
    Button siguiente, btnsalir;
    FirebaseFirestore db;

    Button info_cuest;

    int resultado_encu= 0;
    int Npregunta_p=1;
    String result_cuestionario, IDPacietne;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas_cuestionario_paciente);

        rP1_1 =(RadioButton)findViewById(R.id.rBP1_1);
        rP1_2 = (RadioButton)findViewById(R.id.rBP1_2);
        rP1_3 = (RadioButton)findViewById(R.id.rBP1_3);
        rP1_4= (RadioButton)findViewById(R.id.rBP1_4);
        rP2_1 =(RadioButton)findViewById(R.id.rBP2_1);
        rP2_2 = (RadioButton)findViewById(R.id.rBP2_2);
        rP2_3 = (RadioButton)findViewById(R.id.rBP2_3);
        rP2_4= (RadioButton)findViewById(R.id.rBP2_4);
        rP3_1 =(RadioButton)findViewById(R.id.rBP3_1);
        rP3_2 = (RadioButton)findViewById(R.id.rBP3_2);
        rP3_3 = (RadioButton)findViewById(R.id.rBP3_3);
        rP3_4= (RadioButton)findViewById(R.id.rBP3_4);
        rP4_1 =(RadioButton)findViewById(R.id.rBP4_1);
        rP4_2 = (RadioButton)findViewById(R.id.rBP4_2);
        rP4_3 = (RadioButton)findViewById(R.id.rBP4_3);
        rP4_4= (RadioButton)findViewById(R.id.rBP4_4);
        rP5_1 =(RadioButton)findViewById(R.id.rBP5_1);
        rP5_2 = (RadioButton)findViewById(R.id.rBP5_2);
        rP5_3 = (RadioButton)findViewById(R.id.rBP5_3);
        rP5_4= (RadioButton)findViewById(R.id.rBP5_4);
        rP6_1 =(RadioButton)findViewById(R.id.rBP6_1);
        rP6_2 = (RadioButton)findViewById(R.id.rBP6_2);
        rP6_3 = (RadioButton)findViewById(R.id.rBP6_3);
        rP6_4= (RadioButton)findViewById(R.id.rBP6_4);
        rP7_1 =(RadioButton)findViewById(R.id.rBP7_1);
        rP7_2 = (RadioButton)findViewById(R.id.rBP7_2);
        rP7_3 = (RadioButton)findViewById(R.id.rBP7_3);
        rP7_4= (RadioButton)findViewById(R.id.rBP7_4);
        rP8_1 =(RadioButton)findViewById(R.id.rBP8_1);
        rP8_2 = (RadioButton)findViewById(R.id.rBP8_2);
        rP8_3 = (RadioButton)findViewById(R.id.rBP8_3);
        rP8_4= (RadioButton)findViewById(R.id.rBP8_4);
        rP9_1 =(RadioButton)findViewById(R.id.rBP9_1);
        rP9_2 = (RadioButton)findViewById(R.id.rBP9_2);
        rP9_3 = (RadioButton)findViewById(R.id.rBP9_3);
        rP9_4= (RadioButton)findViewById(R.id.rBP9_4);
        rP10_1 =(RadioButton)findViewById(R.id.rBP10_1);
        rP10_2 = (RadioButton)findViewById(R.id.rBP10_2);
        rP10_3 = (RadioButton)findViewById(R.id.rBP10_3);
        rP10_4= (RadioButton)findViewById(R.id.rBP10_4);
        npregunta = (TextView) findViewById(R.id.Npregunta);
        txtpregunta=(TextView) findViewById(R.id.pregunta_sintomas);
        siguiente=(Button) findViewById(R.id.siguiente_preg);
        btnsalir=(Button) findViewById(R.id.salir);
        info_cuest = findViewById(R.id.icn_cuestionario);

        //Recibir datos
        db = FirebaseFirestore.getInstance();
        Bundle intent = getIntent().getExtras();
        result_cuestionario = intent.getString("puntaje_cuesti");
        IDPacietne = getIntent().getStringExtra("IDPaciente");

        info_cuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sintomas_cuestionario_paciente.this, Popup_cuestionario.class));
            }
        });




    }

    public void enviar(View view){
        if (rP1_1.isChecked() == false && rP1_2.isChecked() == false && rP1_3.isChecked() == false && rP1_4.isChecked() == false || rP2_1.isChecked() == false && rP2_2.isChecked() == false && rP2_3.isChecked() == false && rP2_4.isChecked() == false || rP3_1.isChecked() == false && rP3_2.isChecked() == false && rP3_3.isChecked() == false && rP3_4.isChecked() == false || rP3_4.isChecked() == false && rP4_1.isChecked() == false && rP4_2.isChecked() == false && rP4_3.isChecked() == false && rP4_4.isChecked() == false || rP5_1.isChecked() == false && rP5_2.isChecked() == false && rP5_3.isChecked() == false && rP5_4.isChecked() == false || rP6_1.isChecked() == false && rP6_2.isChecked() == false && rP6_3.isChecked() == false && rP6_4.isChecked() == false || rP7_1.isChecked() == false && rP7_2.isChecked() == false && rP7_3.isChecked() == false && rP7_4.isChecked() == false || rP8_1.isChecked() == false && rP8_2.isChecked() == false && rP8_3.isChecked() == false && rP8_4.isChecked() == false  || rP9_1.isChecked() == false && rP9_2.isChecked() == false && rP9_3.isChecked() == false && rP9_4.isChecked() == false || rP10_1.isChecked() == false && rP10_2.isChecked() == false && rP10_3.isChecked() == false && rP10_4.isChecked() == false){
            Toast.makeText(getApplicationContext(), "Responda todas las pregutas", Toast.LENGTH_SHORT).show();
        }else {
            //Pregunta 1
            if (rP1_1.isChecked()){
                resultado_encu= resultado_encu+1;
            }
            if (rP1_2.isChecked()){
                resultado_encu= resultado_encu+2;
            }
            if (rP1_3.isChecked() ){
                resultado_encu= resultado_encu+3;
            }
            if (rP1_4.isChecked()){
                resultado_encu= resultado_encu+4;
            }
            //Pregunta 2
            if (rP2_1.isChecked()){
                resultado_encu= resultado_encu+1;
            }
            if (rP2_2.isChecked()){
                resultado_encu= resultado_encu+2;
            }
            if (rP2_3.isChecked()){
                resultado_encu= resultado_encu+3;
            }
            if (rP2_4.isChecked()){
                resultado_encu= resultado_encu+4;
            }

            //Pregunta 3
            if (rP3_1.isChecked()){
                resultado_encu= resultado_encu+1;
            }
            if (rP3_2.isChecked()){
                resultado_encu= resultado_encu+2;
            }
            if (rP3_3.isChecked()){
                resultado_encu= resultado_encu+3;
            }
            if (rP3_4.isChecked()){
                resultado_encu= resultado_encu+4;
            }
            //Pregunta 4
            if (rP4_1.isChecked()){
                resultado_encu= resultado_encu+1;
            }
            if (rP4_2.isChecked()){
                resultado_encu= resultado_encu+2;
            }
            if (rP4_3.isChecked()){
                resultado_encu= resultado_encu+3;
            }
            if (rP4_4.isChecked()){
                resultado_encu= resultado_encu+4;
            }

            //Pregunta 5
            if (rP5_1.isChecked()){
                resultado_encu= resultado_encu+1;
            }
            if (rP5_2.isChecked()){
                resultado_encu= resultado_encu+2;
            }
            if (rP5_3.isChecked() ){
                resultado_encu= resultado_encu+3;
            }
            if (rP5_4.isChecked()){
                resultado_encu= resultado_encu+4;
            }
            //Pregunta 6
            if (rP6_1.isChecked()){
                resultado_encu= resultado_encu+1;
            }
            if (rP6_2.isChecked()){
                resultado_encu= resultado_encu+2;
            }
            if (rP6_3.isChecked()){
                resultado_encu= resultado_encu+3;
            }
            if (rP6_4.isChecked()){
                resultado_encu= resultado_encu+4;
            }

            //Pregunta 7
            if (rP7_1.isChecked()){
                resultado_encu= resultado_encu+1;
            }
            if (rP7_2.isChecked()){
                resultado_encu= resultado_encu+2;
            }
            if (rP7_3.isChecked()){
                resultado_encu= resultado_encu+3;
            }
            if (rP7_4.isChecked()){
                resultado_encu= resultado_encu+4;
            }
            //Pregunta 8
            if (rP8_1.isChecked()){
                resultado_encu= resultado_encu+1;
            }
            if (rP8_2.isChecked()){
                resultado_encu= resultado_encu+2;
            }
            if (rP8_3.isChecked()){
                resultado_encu= resultado_encu+3;
            }
            if (rP8_4.isChecked()){
                resultado_encu= resultado_encu+4;
            }

            //Pregunta 9
            if (rP9_1.isChecked()){
                resultado_encu= resultado_encu+1;
            }
            if (rP9_2.isChecked()){
                resultado_encu= resultado_encu+2;
            }
            if (rP9_3.isChecked()){
                resultado_encu= resultado_encu+3;
            }
            if (rP9_4.isChecked()){
                resultado_encu= resultado_encu+4;
            }
            //Pregunta 10
            if (rP10_1.isChecked()){
                resultado_encu= resultado_encu+1;
            }
            if (rP10_2.isChecked()){
                resultado_encu= resultado_encu+2;
            }
            if (rP10_3.isChecked()){
                resultado_encu= resultado_encu+3;
            }
            if (rP10_4.isChecked()){
                resultado_encu= resultado_encu+4;
            }

            GuardarDatosBD();
            finish();
            Toast.makeText(getApplicationContext(), "Respuestas guardadas", Toast.LENGTH_SHORT).show();
            }



        }


        public void salir_p(View view){
        finish();
        }

    private void GuardarDatosBD(){
        //Actualizar base de datos
        String puntuacionPaciente = Integer.toString(resultado_encu);

        Map<String,Object> map = new HashMap<>();
        map.put("puntaje_cuesti",puntuacionPaciente);
        db.collection("reg_paciente").document(IDPacietne).update(map);
    }



}
