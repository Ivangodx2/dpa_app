package com.example.dpa_v6;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class sintomas_oprime extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;



    String Nombre_paciente, puntuacionJ,IDPaciente;


    TextView Mipuntuacion, nombre,puntuacion;
    Button jugar_btn, puntaje;
    private String idPaciente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas_oprime);

        //Variables
        auth= FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        Mipuntuacion = findViewById(R.id.Mipuntuacion);
        nombre = findViewById(R.id.nombre_p);
        puntuacion = findViewById(R.id.puntuacion);

        jugar_btn = findViewById(R.id.jugar_btn);
        puntaje = findViewById(R.id.button_Puntaje);
        idPaciente = auth.getCurrentUser().getUid();

        //Recibir datos de usuario a esta pantalla
        Bundle presiona_s = getIntent().getExtras();
        Nombre_paciente = presiona_s.getString("Nombre_paciente");
        puntuacionJ = presiona_s.getString("puntuacion_J");
        IDPaciente= presiona_s.getString("IDPaciente");


        puntuacion.setText(puntuacionJ);
        nombre.setText(Nombre_paciente);

        jugar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Iniciando juego", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(sintomas_oprime.this, sintomas_oprime_juego.class);
                intent.putExtra("puntuacion_J",puntuacionJ);
                intent.putExtra("IDPaciente",IDPaciente);
                startActivity(intent);
            }
        });


        puntaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Iniciando juego", Toast.LENGTH_SHORT).show();
            }
        });

    }


}