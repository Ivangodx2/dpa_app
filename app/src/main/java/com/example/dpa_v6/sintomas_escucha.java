package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class sintomas_escucha extends AppCompatActivity {
    Button btnplay, btnrty;
    MediaPlayer mp;
    ImageView iv;

    int repetir =2, posicion=0;
    MediaPlayer vectormp[] = new MediaPlayer[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas_escucha);

        btnplay = findViewById(R.id.btn_play);
        btnrty = findViewById(R.id.btn_repetir);
        iv= findViewById(R.id.Num_sonido);

        vectormp[0]= MediaPlayer.create(this,R.raw.sound_two);
        vectormp[1] = MediaPlayer.create(this,R.raw.sound_one);
        vectormp[2] = MediaPlayer.create(this,R.raw.sound_three);


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

            vectormp[0]= MediaPlayer.create(this,R.raw.sound_two);
            vectormp[1] = MediaPlayer.create(this,R.raw.sound_one);
            vectormp[2] = MediaPlayer.create(this,R.raw.sound_three);
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
                }


            }else {
                posicion++;
                if (posicion==0){
                    iv.setImageResource(R.drawable.icon_uno);
                }else if(posicion == 1){
                    iv.setImageResource(R.drawable.icon_dos);
                }else if(posicion == 2){
                    iv.setImageResource(R.drawable.icon_tres);
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
                vectormp[0]= MediaPlayer.create(this,R.raw.sound_two);
                vectormp[1] = MediaPlayer.create(this,R.raw.sound_one);
                vectormp[2] = MediaPlayer.create(this,R.raw.sound_three);
                posicion--;

                if (posicion==0){
                    iv.setImageResource(R.drawable.icon_uno);
                }else if(posicion == 1){
                    iv.setImageResource(R.drawable.icon_dos);
                }else if(posicion == 2){
                    iv.setImageResource(R.drawable.icon_tres);
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
                }
            }
        }else {
            Toast.makeText(this, "No hay mas canciones", Toast.LENGTH_SHORT).show();
        }
    }

    public void enviar_dts_escucha(View view){
        Intent regresar_home_paciente = new Intent( this, Home_pacientes.class);
        vectormp[posicion].stop();
        finish();
        startActivity(regresar_home_paciente);

    }


    public void salir_p(View view){
        Intent salir_a_home_paciente = new Intent( this, Home_pacientes.class);
        vectormp[posicion].stop();
        startActivity(salir_a_home_paciente);
        finish();
    }
}