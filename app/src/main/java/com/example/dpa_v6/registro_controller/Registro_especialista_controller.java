package com.example.dpa_v6.registro_controller;

import android.content.Context;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.example.dpa_v6.registrarse_especialista;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Registro_especialista_controller {
    private Context context;
    private registrarse_especialista registrarse_especialista_activity;
    private FirebaseAuth mAuth;
    private FirebaseFirestore datos_especialista;

    public Registro_especialista_controller(Context context, registrarse_especialista registrarse_especialista_activity) {
        this.context = context;
        this.registrarse_especialista_activity = registrarse_especialista_activity;
        mAuth = FirebaseAuth.getInstance();
        datos_especialista = FirebaseFirestore.getInstance();
    }

    public void registrarEspecialista(String nombre_e, String apellidos_e, String correo_e, String cedula_p_e, String contrasena_e) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Registrando usuario...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        // Verificar si el correo ya está en uso
        mAuth.fetchSignInMethodsForEmail(correo_e).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (task.isSuccessful()) {
                    SignInMethodQueryResult result = task.getResult();
                    if (result.getSignInMethods() != null && result.getSignInMethods().size() > 0) {
                        // El correo ya está en uso, muestra un mensaje de error en la interfaz de usuario
                        progressDialog.dismiss();
                        Toast.makeText(context, "Correo ya registrado", Toast.LENGTH_LONG).show();
                    } else {
                        // El correo no está en uso, procede con el registro
                        mAuth.createUserWithEmailAndPassword(correo_e, contrasena_e).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Registro exitoso, almacena información adicional en Firestore
                                    String id = mAuth.getCurrentUser().getUid();
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("id", id);
                                    map.put("nombre_e", nombre_e);
                                    map.put("apellidos", apellidos_e);
                                    map.put("correo_e_e", correo_e);
                                    map.put("cedula_p", cedula_p_e);
                                    map.put("contra_e", contrasena_e);
                                    map.put("rol", "2");
                                    datos_especialista.collection("reg_especialista").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            progressDialog.dismiss();
                                            Toast.makeText(context, "Registro hecho, verifica tu correo", Toast.LENGTH_SHORT).show();
                                            user.sendEmailVerification();
                                            registrarse_especialista_activity.finish();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(context, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, "No se guardaron los datos", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Error al verificar el correo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
