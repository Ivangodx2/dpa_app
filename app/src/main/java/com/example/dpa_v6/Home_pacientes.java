package com.example.dpa_v6;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Home_pacientes extends AppCompatActivity {

    private TextView nombre_paciente;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private String idPaciente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pacientes);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        nombre_paciente = findViewById(R.id.textNombrePaciente);
        db = FirebaseFirestore.getInstance();
        idPaciente = mAuth.getCurrentUser().getUid();


        DocumentReference documentReference = db.collection("reg_paciente").document(idPaciente);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                nombre_paciente.setText(documentSnapshot.getString("nombre"));
            }
        });

    }

    public void ir_reg_cuestioneario(View view){
        Intent cuestionario_s = new Intent( this, sintomas_cuestionario_paciente.class);
        startActivity(cuestionario_s);
    }

    public void ir_reg_presiona(View view){
        Intent presiona_s = new Intent(this, sintomas_visualiza.class);
    }



    public void ir_reg_visualiza(View view){
        Intent visualiza_s = new Intent( this, sintomas_visualiza.class);
        startActivity(visualiza_s);
    }
}