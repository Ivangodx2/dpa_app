package com.example.dpa_v6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.*;

import java.util.HashMap;
import java.util.Map;

public class registrarse_especialista extends AppCompatActivity {

    private Dialog avisoSinInternet;
    private BroadcastReceiver networkReceiver;
    private FirebaseAuth mAuth;
    private FirebaseFirestore datos_especialista;
    ProgressDialog progressDialog;
    EditText enombre1;
    EditText eapelli2;
    EditText ecorreo3;
    EditText econtra4;
    EditText ecedula5;
    Button btn_dtos_espe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse_especialista);
        mAuth = FirebaseAuth.getInstance();
        datos_especialista = FirebaseFirestore.getInstance();
        enombre1 = findViewById(R.id.EditText_nombre);
        eapelli2 = findViewById(R.id.EditText_apellidos);
        ecorreo3 = findViewById(R.id.EditText_correo_e);
        econtra4 = findViewById(R.id.EditText_contrasena);
        ecedula5 = findViewById(R.id.editTextCedula);
        btn_dtos_espe = findViewById(R.id.button_Registro_bd);

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

        btn_dtos_espe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre_e = enombre1.getText().toString();
                String apellidos_e = eapelli2.getText().toString();
                String correo_e = ecorreo3.getText().toString().trim();
                String cedula_p_e = ecedula5.getText().toString().trim();
                String contrasena_e = econtra4.getText().toString();


                if (nombre_e.isEmpty() && apellidos_e.isEmpty() && cedula_p_e.isEmpty() && correo_e.isEmpty() && contrasena_e.isEmpty() || nombre_e.isEmpty() || apellidos_e.isEmpty() || cedula_p_e.isEmpty() || correo_e.isEmpty() || contrasena_e.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingresar datos faltantes", Toast.LENGTH_SHORT).show();

                } else {
                    //guardar datos
                    registrarEspecialista(nombre_e, apellidos_e, correo_e, cedula_p_e, contrasena_e);
                    finish();
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkUtils.unregisterNetworkReceiver(this, networkReceiver);
    }

    private void registrarEspecialista(String nombre_e, String apellidos_e, String correo_e, String cedula_p_e, String contrasena_e) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registrando usuario..."); // Mensaje de carga
        progressDialog.setCancelable(false); // Bloquear la interacción con la actividad
        progressDialog.setIndeterminate(true); // Usar un ProgressBar indeterminado
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(correo_e, contrasena_e).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                        String id = mAuth.getCurrentUser().getUid();
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", id);
                        map.put("nombre_e", nombre_e);
                        map.put("apellidos", apellidos_e);
                        map.put("correo_e_e", correo_e);
                        map.put("cedula_p", cedula_p_e);
                        map.put("contra_e", contrasena_e);
                        map.put("rol","2");
                        datos_especialista.collection("reg_especialista").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                finish();
                                Toast.makeText(getApplicationContext(), "REGISTO HECHO", Toast.LENGTH_SHORT).show();
                                user.sendEmailVerification();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error al guardar", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "No se guardaron los datos", Toast.LENGTH_SHORT).show();
            }
        });

    }
}