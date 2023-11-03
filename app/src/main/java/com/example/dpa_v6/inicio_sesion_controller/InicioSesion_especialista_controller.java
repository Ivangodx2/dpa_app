package com.example.dpa_v6.inicio_sesion_controller;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.dpa_v6.home_especialista;
import com.example.dpa_v6.iniciar_sesion_especialista;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

public class InicioSesion_especialista_controller {

    private Context context;
    private iniciar_sesion_especialista iniciar_sesion_especialista_activity;
    private FirebaseAuth mAuth;
    public String user_rol;

    public InicioSesion_especialista_controller(Context context,iniciar_sesion_especialista iniciar_sesion_especialista_activity) {
        this.context = context;
        this.iniciar_sesion_especialista_activity = iniciar_sesion_especialista_activity;
        mAuth = FirebaseAuth.getInstance();
    }

    public void iniciarSesionEspecialista(String edtCorreo_e, String edtContra_e) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Iniciando sesión..."); // Mensaje de carga
        progressDialog.setCancelable(false); // Bloquear la interacción con la actividad
        progressDialog.setIndeterminate(true); // Usar un ProgressBar indeterminado
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(edtCorreo_e, edtContra_e)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (task.isSuccessful()) {
                                    // El inicio de sesión fue exitoso
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    if (user != null) {
                                        String userId_e = user.getUid();
                                        // Realizar una consulta a Firestore para obtener información adicional sobre el usuario
                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                        DocumentReference user_espe = db.collection("reg_especialista").document(userId_e);

                                        user_espe.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if (documentSnapshot.exists()) {
                                                    // El documento existe en Firestore
                                                    user_rol = documentSnapshot.getString("rol");

                                                    System.out.println(user_rol);
                                                    if (user_rol.equals("2")) {
                                                        // Usuario es un especialista
                                                        Intent intent = new Intent(context, home_especialista.class);
                                                        context.startActivity(intent);
                                                        progressDialog.dismiss();
                                                    }

                                                    Toast.makeText(context, "Sesión iniciada", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(context, "El usuario no es especialista.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Manejar el error de consulta a Firestore
                                                progressDialog.dismiss();
                                                Log.e(TAG, "Error al consultar Firestore", e);
                                            }
                                        });

                                        if (!user.isEmailVerified()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(context, "Correo no verificado", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // El usuario es nulo, lo que generalmente no debería suceder si el inicio de sesión es exitoso
                                        progressDialog.dismiss();
                                        Log.e(TAG, "El usuario es nulo después del inicio de sesión exitoso");
                                    }
                                } else {
                                    // Si el inicio de sesión falla, muestra un mensaje al usuario
                                    progressDialog.dismiss();
                                    Log.w(TAG, "Datos incorrectos", task.getException());
                                    Toast.makeText(context, "Datos incorrectos.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, 3000);
                    }
                });
    }
}