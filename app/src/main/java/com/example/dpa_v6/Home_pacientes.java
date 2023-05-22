package com.example.dpa_v6;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    String puntuacion_J,Idpaciente;

    Button CerrarS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pacientes);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        nombre_paciente = findViewById(R.id.textNombrePaciente);
        db = FirebaseFirestore.getInstance();
        idPaciente = mAuth.getCurrentUser().getUid();
        CerrarS = findViewById(R.id.Cerrar_S);

        //Cerrar sesion
        CerrarS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CerrarSesion_p();
            }
        });




        //Leer nombre del usuario
        DocumentReference documentReference = db.collection("reg_paciente").document(idPaciente);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                Idpaciente = documentSnapshot.getString("id");
                nombre_paciente.setText(documentSnapshot.getString("nombre"));
                puntuacion_J = documentSnapshot.getString("puntuacion_J");
            }
        });

    }

    public void ir_reg_cuestioneario(View view){
        Intent cuestionario_s = new Intent( this, sintomas_cuestionario_paciente.class);
        startActivity(cuestionario_s);
    }



    public void ir_reg_visualiza(View view){
        Intent visualiza_s = new Intent( this, sintomas_visualiza.class);
        startActivity(visualiza_s);
    }


    public void ir_reg_escucha(View view){
        Intent escucha_s = new Intent( this, sintomas_escucha.class);
        startActivity(escucha_s);
    }

    public void ir_reg_presiona(View view){
        Intent presiona_s = new Intent( this, sintomas_oprime.class);
        String idpaciente = Idpaciente;
        String nombreP = nombre_paciente.getText().toString();
        String puntuacionJ = puntuacion_J;

        //Se envian los datos del usuario
        presiona_s.putExtra("IDPaciente",idpaciente);
        presiona_s.putExtra("Nombre_paciente",nombreP);
        presiona_s.putExtra("puntuacion_J",puntuacionJ);
        startActivity(presiona_s);


    }


    private void CerrarSesion_p(){
        mAuth.signOut();
        this.finish();
        startActivity(new Intent(Home_pacientes.this,iniciar_paciente.class));
        Toast.makeText(this, "Se ha cerrado sesión", Toast.LENGTH_SHORT).show();
    }


}