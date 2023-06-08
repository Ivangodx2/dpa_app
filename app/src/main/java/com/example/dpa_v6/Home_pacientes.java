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

    String puntuacion_J,Idpaciente, pntj_cuesti, pntj_visuali, pntj_escha,pntj_ident,nombrePaciente_Dig;

    Button CerrarS,btn_diagnostico;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pacientes);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        nombre_paciente = findViewById(R.id.textNombrePaciente);
        idPaciente = mAuth.getCurrentUser().getUid();
        CerrarS = findViewById(R.id.Cerrar_S);
        btn_diagnostico = findViewById(R.id.button_diagnostico);


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
                if (documentSnapshot.getString("nombre") == null){
                    finish();
                    Intent isespecialista= new Intent(getApplicationContext(),home_especialista.class);
                    startActivity(isespecialista);

                }else{
                    nombre_paciente.setText("P. "+ documentSnapshot.getString("nombre"));
                }
                nombrePaciente_Dig = documentSnapshot.getString("nombre");
                puntuacion_J = documentSnapshot.getString("puntuacion_J");
                pntj_cuesti = documentSnapshot.getString("puntaje_cuesti");
                pntj_visuali = documentSnapshot.getString("puntaje_vsual");
                pntj_escha = documentSnapshot.getString("puntaje_escuch");
                pntj_ident = documentSnapshot.getString("puntaje_identifica");
            }
        });


    }

    public void ir_reg_cuestioneario(View view){
        Intent cuestionario_s = new Intent( this, sintomas_cuestionario_paciente.class);
        String idpaciente = Idpaciente;
        String nombreP = nombre_paciente.getText().toString();
        String pntj_cuest = pntj_cuesti;

        //Se envian los datos del usuario
        cuestionario_s.putExtra("IDPaciente",idpaciente);
        cuestionario_s.putExtra("puntaje_cuesti",pntj_cuest);
        startActivity(cuestionario_s);
    }



    public void ir_reg_visualiza(View view){
        Intent visualiza_s = new Intent( this, sintomas_visualiza.class);
        String idpaciente = Idpaciente;
        String pntj_visul = pntj_visuali;

        //Se envian los datos del usuario
        visualiza_s.putExtra("IDPaciente",idpaciente);
        visualiza_s.putExtra("puntaje_vsual",pntj_visul);
        startActivity(visualiza_s);
    }


    public void ir_reg_escucha(View view){
        Intent escucha_s = new Intent( this, sintomas_escucha.class);
        String idpaciente = Idpaciente;
        String pntj_escucha = pntj_escha;

        //Se envian los datos del usuario
        escucha_s.putExtra("IDPaciente",idpaciente);
        escucha_s.putExtra("puntaje_escuch",pntj_escucha);
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

    public void ir_reg_identifica(View view){
        Intent identifica_s = new Intent( this, sintomas_identifica.class);
        String idpaciente = Idpaciente;
        String pntj_identifi = pntj_ident;

        //Se envian los datos del usuario
        identifica_s.putExtra("IDPaciente",idpaciente);
        identifica_s.putExtra("puntaje_identifica",pntj_identifi);
        startActivity(identifica_s);

    }

    public void ir_reg_diagnostico(View view){
        Intent diagnostico_s = new Intent(this, diagnostico_paciente.class);
        String idpaciente = Idpaciente;
        String nombrePaciente = nombrePaciente_Dig;
        String pntj_cuest_R = pntj_cuesti;
        String pntj_visul_R = pntj_visuali;
        String pntj_escucha_R = pntj_escha;
        String puntuacionJ_R = puntuacion_J;
        String pntj_identifi_R = pntj_ident;

        //Se envian los datos del usuario
        diagnostico_s.putExtra("IDPaciente",idpaciente);
        diagnostico_s.putExtra("nombre",nombrePaciente);
        diagnostico_s.putExtra("puntaje_cuesti",pntj_cuest_R);
        diagnostico_s.putExtra("puntaje_vsual",pntj_visul_R);
        diagnostico_s.putExtra("puntaje_escuch",pntj_escucha_R);
        diagnostico_s.putExtra("puntuacion_J",puntuacionJ_R);
        diagnostico_s.putExtra("puntaje_identifica",pntj_identifi_R);
        startActivity(diagnostico_s);
    }


    private void CerrarSesion_p(){
        mAuth.signOut();
        finish();
        Toast.makeText(this, "Se ha cerrado sesión", Toast.LENGTH_SHORT).show();
    }




}