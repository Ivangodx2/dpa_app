package com.example.dpa_v6;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class sintomas_oprime_juego extends AppCompatActivity {

    String puntuacionPaciente,IDPacietne;
    TextView P_contador,P_tiempo;
    ImageView P_huevo;

    int APantalla;
    int LPantalla;
    int contador;

    Random mvAleatorio;

    FirebaseFirestore db;

    boolean GameRunning= false;
    Dialog aviso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas_oprime_juego);

        P_huevo = findViewById(R.id.P_huevo);

        P_contador = findViewById(R.id.P_contador);
        P_tiempo = findViewById(R.id.P_tiempo);

        aviso= new Dialog(sintomas_oprime_juego.this);

        //Recibir datos
        db = FirebaseFirestore.getInstance();

        Bundle intent = getIntent().getExtras();
        puntuacionPaciente = intent.getString("puntuacion_J");
        IDPacietne = getIntent().getStringExtra("IDPaciente");
        P_contador.setText(puntuacionPaciente);

        Pantalla();
        cuentaatras();


        P_huevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!GameRunning) {
                    contador++;
                    P_contador.setText(String.valueOf(contador));

                    P_huevo.setImageResource(R.drawable.huevo_roto_game_sf);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            P_huevo.setImageResource(R.drawable.huevo_game_sf);
                            movimiento();
                        }
                    }, 300);
                }
            }
        });

    }

    private void Pantalla(){
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        APantalla= point.x;
        LPantalla= point.y;

        mvAleatorio = new Random();
    }

    private void movimiento(){
        int min = 0;
        //Movimiento de imagen
        int maxiX  = APantalla - P_huevo.getWidth()-5;
        int maxiY  = LPantalla - P_huevo.getHeight()-35;

        int randomX = mvAleatorio.nextInt(((maxiX-min)+1)+min);
        int randomY = mvAleatorio.nextInt(((maxiY-min)+1)+min);


        P_huevo.setX(randomX);
        P_huevo.setY(randomY);
    }

    private void cuentaatras(){
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                long seg_rest=millisUntilFinished/1000;
                P_tiempo.setText(seg_rest+" Seg");

            }

            public void onFinish() {
                P_tiempo.setText("0 Seg");
                GameRunning = true;
                avisoJuegoT();
                GuardarDatosBD();
            }
        }.start();

    }
    private void avisoJuegoT(){
        TextView titulo, titulo_dos,puntaje_obtenido;
        Button Jugar_otv,Volver_menu;

        aviso.setContentView(R.layout.avisojuegoterminado);
        titulo = aviso.findViewById(R.id.titulo);
        titulo_dos = aviso.findViewById(R.id.titulo_dos);
        puntaje_obtenido = aviso.findViewById(R.id.puntaje_obtenido);

        Jugar_otv = aviso.findViewById(R.id.Jugar_otv);
        Volver_menu = aviso.findViewById(R.id.Volver_menu);

        String pnt_huevos = String.valueOf(contador);
        puntaje_obtenido.setText(pnt_huevos);

        Jugar_otv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador=0;
                aviso.dismiss();
                P_contador.setText("0");
                GameRunning = false;
                cuentaatras();
                movimiento();
                Toast.makeText(sintomas_oprime_juego.this, "Iniciando juego...", Toast.LENGTH_SHORT).show();
            }
        });

        Volver_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(sintomas_oprime_juego.this,Home_pacientes.class));

            }
        });
        aviso.show();

    }


    //No se guarda el puntaje mas alto-corregir
    private void GuardarDatosBD(){
        //Actualizar base de datos
        String puntuacionPaciente= P_contador.getText().toString();

        Map<String,Object> map = new HashMap<>();
        map.put("puntuacion_J",puntuacionPaciente);

        db.collection("reg_paciente").document(IDPacietne).update(map);
    }
}