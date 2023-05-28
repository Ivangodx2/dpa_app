package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class sintomas_escucha extends AppCompatActivity {
    Button btnplay, btnrty, info_esuch;
    MediaPlayer mp;
    String IDPacietne,result_escucha;
    ImageView iv;
    FirebaseFirestore db;
    RadioButton raP1_1, raP1_2, raP1_3, raP1_4,raP2_1, raP2_2, raP2_3, raP2_4,raP3_1, raP3_2, raP3_3, raP3_4,raP4_1, raP4_2, raP4_3, raP4_4,raP5_1, raP5_2, raP5_3, raP5_4;

    int repetir =2, posicion=0, resultado_escu;
    MediaPlayer vectormp[] = new MediaPlayer[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas_escucha);

        btnplay = findViewById(R.id.btn_play);
        btnrty = findViewById(R.id.btn_repetir);
        iv= findViewById(R.id.Num_sonido);
        info_esuch = findViewById(R.id.info_escucha);

        vectormp[0]= MediaPlayer.create(this,R.raw.ans_a1);
        vectormp[1] = MediaPlayer.create(this,R.raw.ans_a2);
        vectormp[2] = MediaPlayer.create(this,R.raw.ans_a3);
        vectormp[3] = MediaPlayer.create(this,R.raw.ans_a4);
        vectormp[4] = MediaPlayer.create(this,R.raw.ans_a5);


        raP1_1 =(RadioButton)findViewById(R.id.rAP1_1);
        raP1_2 = (RadioButton)findViewById(R.id.rAP1_2);
        raP1_3 = (RadioButton)findViewById(R.id.rAP1_3);
        raP1_4= (RadioButton)findViewById(R.id.rAP1_4);
        raP2_1 =(RadioButton)findViewById(R.id.rAP2_1);
        raP2_2 = (RadioButton)findViewById(R.id.rAP2_2);
        raP2_3 = (RadioButton)findViewById(R.id.rAP2_3);
        raP2_4= (RadioButton)findViewById(R.id.rAP2_4);
        raP3_1 =(RadioButton)findViewById(R.id.rAP3_1);
        raP3_2 = (RadioButton)findViewById(R.id.rAP3_2);
        raP3_3 = (RadioButton)findViewById(R.id.rAP3_3);
        raP3_4= (RadioButton)findViewById(R.id.rAP3_4);
        raP4_1 =(RadioButton)findViewById(R.id.rAP4_1);
        raP4_2 = (RadioButton)findViewById(R.id.rAP4_2);
        raP4_3 = (RadioButton)findViewById(R.id.rAP4_3);
        raP4_4= (RadioButton)findViewById(R.id.rAP4_4);
        raP5_1 =(RadioButton)findViewById(R.id.rAP5_1);
        raP5_2 = (RadioButton)findViewById(R.id.rAP5_2);
        raP5_3 = (RadioButton)findViewById(R.id.rAP5_3);
        raP5_4= (RadioButton)findViewById(R.id.rAP5_4);

        //Recibir datos
        db = FirebaseFirestore.getInstance();
        Bundle intent = getIntent().getExtras();
        result_escucha = intent.getString("puntaje_escuch");
        IDPacietne = getIntent().getStringExtra("IDPaciente");

        info_esuch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sintomas_escucha.this, popup_escucha.class));
            }
        });

    }

    public void Playpause(View view){
        if (vectormp[posicion].isPlaying()){
            vectormp[posicion].pause();
            btnplay.setBackgroundResource(R.drawable.boton_de_play);
        } else {
            vectormp[posicion].start();
            btnplay.setBackgroundResource(R.drawable.boton_pausa);

        }
    }

    public void Stop(View view){
        if(vectormp[posicion] !=null){
            vectormp[posicion].stop();

            vectormp[0]= MediaPlayer.create(this,R.raw.ans_a1);
            vectormp[1] = MediaPlayer.create(this,R.raw.ans_a2);
            vectormp[2] = MediaPlayer.create(this,R.raw.ans_a3);
            vectormp[3] = MediaPlayer.create(this,R.raw.ans_a4);
            vectormp[4] = MediaPlayer.create(this,R.raw.ans_a5);
            posicion = 0;
            btnplay.setBackgroundResource(R.drawable.boton_de_play);


        }
    }

    public void Repetir(View view){
        if(repetir==1){
            btnrty.setBackgroundResource(R.drawable.reiniciar);
            vectormp[posicion].setLooping(false);
            repetir=2;
        }else {
            btnrty.setBackgroundResource(R.drawable.reinicia_rjo);
            vectormp[posicion].setLooping(true);
            repetir = 1;
        }
    }


    public void siguiente(View view){
        if(posicion<vectormp.length-1){

            if(vectormp[posicion].isPlaying()){
                vectormp[posicion].stop();
                posicion++;
                vectormp[posicion].start();

                if (posicion==0){
                    iv.setImageResource(R.drawable.icon_uno);
                }else if(posicion == 1){
                    iv.setImageResource(R.drawable.icon_dos);
                }else if(posicion == 2){
                    iv.setImageResource(R.drawable.icon_tres);
                }else if(posicion == 3){
                    iv.setImageResource(R.drawable.icon_cuatro);
                }else if(posicion == 4){
                    iv.setImageResource(R.drawable.icon_cinco);
                }


            }else {
                posicion++;
                if (posicion==0){
                    iv.setImageResource(R.drawable.icon_uno);
                }else if(posicion == 1){
                    iv.setImageResource(R.drawable.icon_dos);
                }else if(posicion == 2){
                    iv.setImageResource(R.drawable.icon_tres);
                }else if(posicion == 3){
                    iv.setImageResource(R.drawable.icon_cuatro);
                }else if(posicion == 4){
                    iv.setImageResource(R.drawable.icon_cinco);
                }
            }
        }else {
            Toast.makeText(this, "No hay mas canciones", Toast.LENGTH_SHORT).show();
        }
    }

    public void anterior(View view){
        if (posicion>=1){
            if(vectormp[posicion].isPlaying()){
                vectormp[posicion].stop();
                vectormp[0]= MediaPlayer.create(this,R.raw.ans_a1);
                vectormp[1] = MediaPlayer.create(this,R.raw.ans_a2);
                vectormp[2] = MediaPlayer.create(this,R.raw.ans_a3);
                vectormp[3] = MediaPlayer.create(this,R.raw.ans_a4);
                vectormp[4] = MediaPlayer.create(this,R.raw.ans_a5);
                posicion--;

                if (posicion==0){
                    iv.setImageResource(R.drawable.icon_uno);
                }else if(posicion == 1){
                    iv.setImageResource(R.drawable.icon_dos);
                }else if(posicion == 2){
                    iv.setImageResource(R.drawable.icon_tres);
                }else if(posicion == 3){
                    iv.setImageResource(R.drawable.icon_cuatro);
                }else if(posicion == 4){
                    iv.setImageResource(R.drawable.icon_cinco);
                }

                vectormp[posicion].start();
            }else{
                posicion--;

                if (posicion==0){
                    iv.setImageResource(R.drawable.icon_uno);
                }else if(posicion == 1){
                    iv.setImageResource(R.drawable.icon_dos);
                }else if(posicion == 2){
                    iv.setImageResource(R.drawable.icon_tres);
                }else if(posicion == 3){
                    iv.setImageResource(R.drawable.icon_cuatro);
                }else if(posicion == 4){
                    iv.setImageResource(R.drawable.icon_cinco);
                }
            }
        }else {
            Toast.makeText(this, "No hay mas canciones", Toast.LENGTH_SHORT).show();
        }
    }

    public void enviar_dts_escucha(View view){
        if (raP1_1.isChecked() == false && raP1_2.isChecked() == false && raP1_3.isChecked() == false && raP1_4.isChecked() == false || raP2_1.isChecked() == false && raP2_2.isChecked() == false && raP2_3.isChecked() == false && raP2_4.isChecked() == false || raP3_1.isChecked() == false && raP3_2.isChecked() == false && raP3_3.isChecked() == false && raP3_4.isChecked() == false || raP3_4.isChecked() == false && raP4_1.isChecked() == false && raP4_2.isChecked() == false && raP4_3.isChecked() == false && raP4_4.isChecked() == false || raP5_1.isChecked() == false && raP5_2.isChecked() == false && raP5_3.isChecked() == false && raP5_4.isChecked() == false){
            Toast.makeText(getApplicationContext(), "Responda todas las pregutas", Toast.LENGTH_SHORT).show();
        }else {
            //Pregunta 1
            if (raP1_1.isChecked()){
                resultado_escu= resultado_escu+1;
            }
            if (raP1_2.isChecked()){
                resultado_escu= resultado_escu+2;
            }
            if (raP1_3.isChecked() ){
                resultado_escu= resultado_escu+3;
            }
            if (raP1_4.isChecked()){
                resultado_escu= resultado_escu+4;
            }
            //Pregunta 2
            if (raP2_1.isChecked()){
                resultado_escu= resultado_escu+1;
            }
            if (raP2_2.isChecked()){
                resultado_escu= resultado_escu+2;
            }
            if (raP2_3.isChecked()){
                resultado_escu= resultado_escu+3;
            }
            if (raP2_4.isChecked()){
                resultado_escu= resultado_escu+4;
            }

            //Pregunta 3
            if (raP3_1.isChecked()){
                resultado_escu= resultado_escu+1;
            }
            if (raP3_2.isChecked()){
                resultado_escu= resultado_escu+2;
            }
            if (raP3_3.isChecked()){
                resultado_escu= resultado_escu+3;
            }
            if (raP3_4.isChecked()){
                resultado_escu= resultado_escu+4;
            }
            //Pregunta 4
            if (raP4_1.isChecked()){
                resultado_escu= resultado_escu+1;
            }
            if (raP4_2.isChecked()){
                resultado_escu= resultado_escu+2;
            }
            if (raP4_3.isChecked()){
                resultado_escu= resultado_escu+3;
            }
            if (raP4_4.isChecked()){
                resultado_escu= resultado_escu+4;
            }

            //Pregunta 5
            if (raP5_1.isChecked()){
                resultado_escu= resultado_escu+1;
            }
            if (raP5_2.isChecked()){
                resultado_escu= resultado_escu+2;
            }
            if (raP5_3.isChecked() ){
                resultado_escu= resultado_escu+3;
            }
            if (raP5_4.isChecked()){
                resultado_escu= resultado_escu+4;
            }
        }
        GuardarDatosBD();
        Intent regresar_home_paciente = new Intent( this, Home_pacientes.class);
        vectormp[posicion].stop();
        finish();
        Toast.makeText(getApplicationContext(), "Respuestas guardadas", Toast.LENGTH_SHORT).show();
        startActivity(regresar_home_paciente);

    }


    public void salir_p(View view){
        Intent salir_a_home_paciente = new Intent( this, Home_pacientes.class);
        vectormp[posicion].stop();
        startActivity(salir_a_home_paciente);
        finish();
    }

    private void GuardarDatosBD(){
        //Actualizar base de datos
        String puntuacionPaciente_escucha = Integer.toString(resultado_escu);

        Map<String,Object> map = new HashMap<>();
        map.put("puntaje_escuch",puntuacionPaciente_escucha);
        db.collection("reg_paciente").document(IDPacietne).update(map);
    }


}