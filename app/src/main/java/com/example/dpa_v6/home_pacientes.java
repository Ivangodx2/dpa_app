package com.example.dpa_v6;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class home_pacientes extends AppCompatActivity {

    private Dialog avisoSinInternet;
    private BroadcastReceiver networkReceiver;
    private TextView nombre_paciente;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private String idPaciente;
    private static final int TIEMPO_CARGA = 3000;
    ProgressDialog progressDialog_h;
    String puntuacion_J,Idpaciente, pntj_cuesti, pntj_visuali, pntj_escha,pntj_ident,nombrePaciente_Dig;

    Button Cerrar_sesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pacientes);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        nombre_paciente = findViewById(R.id.textNombrePaciente);
        idPaciente = mAuth.getCurrentUser().getUid();
        Cerrar_sesion = (Button) findViewById(R.id.Cerrar_S);

        progressDialog_h = new ProgressDialog(this);
        progressDialog_h.setMessage("Cerrando sesión..."); // Mensaje de carga
        progressDialog_h.setCancelable(false); // Bloquear la interacción con la actividad
        progressDialog_h.setIndeterminate(true); // Usar un ProgressBar indeterminado
        //Conexión
        avisoSinInternet = new Dialog(this);
        avisoSinInternet.setContentView(R.layout.avisosininternet);
        avisoSinInternet.setCancelable(false);
        networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isNetworkAvailable = NetworkUtils.isNetworkAvailable(context);
                if (isNetworkAvailable) {
                    NetworkUtils.ocultarAvisoSinConexion(avisoSinInternet);
                } else {
                    NetworkUtils.mostrarAvisoSinConexion(context, avisoSinInternet);
                }
            }
        };
        NetworkUtils.registerNetworkReceiver(this, networkReceiver);


        //Cerrar sesion
        Cerrar_sesion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressDialog_h.show();
                new Handler().postDelayed(new Runnable() {
                @Override
                public void run () {

                    Toast.makeText(home_pacientes.this, "Sesion cerrada", Toast.LENGTH_SHORT).show();
                    mAuth.signOut();
                    progressDialog_h.dismiss();
                    finish();
                    }
                },TIEMPO_CARGA);
            }
        });




        //Leer nombre del usuario
        DocumentReference documentReference = db.collection("reg_paciente").document(idPaciente);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                Idpaciente = documentSnapshot.getString("id");
                nombre_paciente.setText("P. "+ documentSnapshot.getString("nombre"));
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
        Intent visualiza_s = new Intent( this, sintomas_visualiza_paciente.class);
        String idpaciente = Idpaciente;
        String pntj_visul = pntj_visuali;

        //Se envian los datos del usuario
        visualiza_s.putExtra("IDPaciente",idpaciente);
        visualiza_s.putExtra("puntaje_vsual",pntj_visul);
        startActivity(visualiza_s);
    }


    public void ir_reg_escucha(View view){
        Intent escucha_s = new Intent( this, sintomas_escucha_paciente.class);
        String idpaciente = Idpaciente;
        String pntj_escucha = pntj_escha;

        //Se envian los datos del usuario
        escucha_s.putExtra("IDPaciente",idpaciente);
        escucha_s.putExtra("puntaje_escuch",pntj_escucha);
        startActivity(escucha_s);
    }

    public void ir_reg_presiona(View view){
        Intent presiona_s = new Intent( this, sintomas_oprime_paciente.class);
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
        Intent identifica_s = new Intent( this, sintomas_identifica_paciente.class);
        String idpaciente = Idpaciente;
        String pntj_identifi = pntj_ident;

        //Se envian los datos del usuario
        identifica_s.putExtra("IDPaciente",idpaciente);
        identifica_s.putExtra("puntaje_identifica",pntj_identifi);
        startActivity(identifica_s);

    }

    public void ir_reg_diagnostico(View view){

        DocumentReference documentReference = db.collection("reg_paciente").document(idPaciente);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                Idpaciente = documentSnapshot.getString("id");
                nombrePaciente_Dig = documentSnapshot.getString("nombre");
                puntuacion_J = documentSnapshot.getString("puntuacion_J");
                pntj_cuesti = documentSnapshot.getString("puntaje_cuesti");
                pntj_visuali = documentSnapshot.getString("puntaje_vsual");
                pntj_escha = documentSnapshot.getString("puntaje_escuch");
                pntj_ident = documentSnapshot.getString("puntaje_identifica");

                // Los valores se actualizaron en Consulta_Dtos(), así que los obtienes aquí
                String idpaciente = Idpaciente;
                String nombrePaciente = nombrePaciente_Dig;
                String pntj_cuest_R = pntj_cuesti;
                String pntj_visul_R = pntj_visuali;
                String pntj_escucha_R = pntj_escha;
                String puntuacionJ_R = puntuacion_J;
                String pntj_identifi_R = pntj_ident;

                Intent diagnostico_s = new Intent(home_pacientes.this, diagnostico_paciente.class);

                diagnostico_s.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                // Se envían los datos del usuario
                diagnostico_s.putExtra("IDPaciente", idpaciente);
                diagnostico_s.putExtra("nombre", nombrePaciente);
                diagnostico_s.putExtra("puntaje_cuesti", pntj_cuest_R);
                diagnostico_s.putExtra("puntaje_vsual", pntj_visul_R);
                diagnostico_s.putExtra("puntaje_escuch", pntj_escucha_R);
                diagnostico_s.putExtra("puntuacion_J", puntuacionJ_R);
                diagnostico_s.putExtra("puntaje_identifica", pntj_identifi_R);
                startActivity(diagnostico_s);
            }
        });
    }



    public void ir_diagnostico_final(View view){

        DocumentReference documentReference = db.collection("reg_paciente").document(idPaciente);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                Idpaciente = documentSnapshot.getString("id");
                nombrePaciente_Dig = documentSnapshot.getString("nombre");
                puntuacion_J = documentSnapshot.getString("puntuacion_J");
                pntj_cuesti = documentSnapshot.getString("puntaje_cuesti");
                pntj_visuali = documentSnapshot.getString("puntaje_vsual");
                pntj_escha = documentSnapshot.getString("puntaje_escuch");
                pntj_ident = documentSnapshot.getString("puntaje_identifica");

                // Los valores se actualizaron en Consulta_Dtos(), así que los obtienes aquí
                String idpaciente = Idpaciente;
                String nombrePaciente = nombrePaciente_Dig;
                String pntj_cuest_R = pntj_cuesti;
                String pntj_visul_R = pntj_visuali;
                String pntj_escucha_R = pntj_escha;
                String puntuacionJ_R = puntuacion_J;
                String pntj_identifi_R = pntj_ident;

                Intent diagnostico_s = new Intent(home_pacientes.this, diagnostico_paciente_grafica.class);

                diagnostico_s.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                // Se envían los datos del usuario
                diagnostico_s.putExtra("IDPaciente", idpaciente);
                diagnostico_s.putExtra("nombre", nombrePaciente);
                diagnostico_s.putExtra("puntaje_cuesti", pntj_cuest_R);
                diagnostico_s.putExtra("puntaje_vsual", pntj_visul_R);
                diagnostico_s.putExtra("puntaje_escuch", pntj_escucha_R);
                diagnostico_s.putExtra("puntuacion_J", puntuacionJ_R);
                diagnostico_s.putExtra("puntaje_identifica", pntj_identifi_R);
                startActivity(diagnostico_s);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkUtils.unregisterNetworkReceiver(this, networkReceiver);
    }




}