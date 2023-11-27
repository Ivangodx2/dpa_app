package com.example.dpa_v6.registro_controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.dpa_v6.registrarse_paciente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registro_paciente_controller {
    private Context context;
    private registrarse_paciente registrarse_paciente_activity;
    private FirebaseAuth mAuth;
    private FirebaseFirestore datos_paciente;


    public Registro_paciente_controller(Context context, registrarse_paciente registrarse_paciente_activity) {
        this.context = context;
        this.registrarse_paciente_activity = registrarse_paciente_activity;
        mAuth = FirebaseAuth.getInstance();
        datos_paciente = FirebaseFirestore.getInstance();
    }

    public void registrarPasientes(String nombre_p, String apellidos_p, String correo_p, String num_telef, String edad_p, String contrasena_p){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Registrando usuario...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        mAuth.fetchSignInMethodsForEmail(correo_p).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (task.isSuccessful()) {
                    SignInMethodQueryResult result = task.getResult();
                    if (result.getSignInMethods() != null && result.getSignInMethods().size() > 0) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Correo ya registrado", Toast.LENGTH_LONG).show();
                    } else {
                        mAuth.createUserWithEmailAndPassword(correo_p,contrasena_p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {


                                    String id = mAuth.getCurrentUser().getUid();
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("id", id);
                                    map.put("nombre", nombre_p);
                                    map.put("apellidos", apellidos_p);
                                    map.put("correo_e_p", correo_p);
                                    map.put("num_telefonico", num_telef);
                                    map.put("edad", edad_p);
                                    map.put("contra_p", contrasena_p);
                                    map.put("puntuacion_J", "0");
                                    map.put("puntaje_cuesti", "0");
                                    map.put("puntaje_vsual", "0");
                                    map.put("puntaje_escuch", "0");
                                    map.put("puntaje_identifica", "0");
                                    map.put("porcentaje_A", "0");
                                    map.put("rol", "1");

                                    datos_paciente.collection("reg_paciente").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            progressDialog.dismiss();
                                            Toast.makeText(context, "Registro hecho, verifica tu correo", Toast.LENGTH_SHORT).show();
                                            user.sendEmailVerification();
                                            registrarse_paciente_activity.finish();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else{
                                    progressDialog.dismiss();
                                    Toast.makeText(context, "No se guardaron los datos", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }else {
                    progressDialog.dismiss();
                    System.out.println("Error al verificar el correo");
                }
            }
        });
    }
}