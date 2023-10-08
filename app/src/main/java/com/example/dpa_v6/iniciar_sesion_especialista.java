package com.example.dpa_v6;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import java.util.HashMap;
import java.util.Map;

public class iniciar_sesion_especialista extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText edtCorreo,edtContra;
    Button btn_iniciar_s_e;
    String user_rol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion_especialista);
        edtCorreo=findViewById(R.id.TextEspeEmailAddress);
        edtContra=findViewById(R.id.EdittextEsepePassword);
        btn_iniciar_s_e=findViewById(R.id.button_Iniciar_S);
        mAuth = FirebaseAuth.getInstance();

        btn_iniciar_s_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edtCorreo_e = edtCorreo.getText().toString();
                String edtContra_e = edtContra.getText().toString();

                if (edtCorreo_e.isEmpty() && edtContra_e.isEmpty() || edtCorreo_e.isEmpty() || edtContra_e.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingresar datos", Toast.LENGTH_SHORT).show();

                }else {
                    //Inicio de sesion
                    iniciarSesion_espe(edtCorreo_e,edtContra_e);
                }
            }
        });



    }


    public void iniciarSesion_espe(String edtCorreo_e, String edtContra_e) {
        mAuth.signInWithEmailAndPassword(edtCorreo_e, edtContra_e)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // El inicio de sesión fue exitoso
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user != null) {
                                String userId_e = user.getUid();
                                System.out.println(userId_e);
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
                                                Intent intent = new Intent(getApplicationContext(), home_especialista.class);
                                                startActivity(intent);
                                            }

                                            Toast.makeText(getApplicationContext(), "Sesión iniciada", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Intent intent = new Intent(getApplicationContext(), home_pacientes.class);
                                            startActivity(intent);
                                            Toast.makeText(getApplicationContext(), "Sesión iniciada", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Manejar el error de consulta a Firestore
                                        Log.e(TAG, "Error al consultar Firestore", e);
                                    }
                                });

                                if (!user.isEmailVerified()) {
                                    Toast.makeText(getApplicationContext(), "Correo no verificado", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // El usuario es nulo, lo que generalmente no debería suceder si el inicio de sesión es exitoso
                                Log.e(TAG, "El usuario es nulo después del inicio de sesión exitoso");
                            }
                        } else {
                            // Si el inicio de sesión falla, muestra un mensaje al usuario
                            Log.w(TAG, "Datos incorrectos", task.getException());
                            Toast.makeText(getApplicationContext(), "Datos incorrectos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void ir_reg_especialista(View view){
        Intent reg_especial = new Intent( this, registrarse_especialista.class);
        startActivity(reg_especial);
    }

    public void ir_rec_contra_e(View view){
        Intent rec_contra = new Intent( this, recuperar_contrasena.class);
        startActivity(rec_contra);
    }


}