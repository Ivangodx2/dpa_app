package com.example.dpa_v6;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class iniciar_paciente extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText edtCorreo_p,edtContra_p;
    Button iniciar_sesion_paciente;
    String user_rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_paciente);
        edtCorreo_p=findViewById(R.id.TextEspeEmailAddress_p);
        edtContra_p=findViewById(R.id.EdittextContra_Pas);
        iniciar_sesion_paciente=findViewById(R.id.button_Iniciar_S_p);
        mAuth = FirebaseAuth.getInstance();


        iniciar_sesion_paciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edtCorreo_p_p = edtCorreo_p.getText().toString();
                String edtContra_p_p = edtContra_p.getText().toString();

                if (edtCorreo_p_p.isEmpty() && edtContra_p_p.isEmpty() || edtCorreo_p_p.isEmpty() || edtContra_p_p.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingresar datos", Toast.LENGTH_SHORT).show();

                }else {
                    //Inicio de sesion
                    iniciarSesion_pac(edtCorreo_p_p,edtContra_p_p);
                }

            }
        });



    }


    public void iniciarSesion_pac(String edtCorreo_p_p, String edtContra_p_p) {
        mAuth.signInWithEmailAndPassword(edtCorreo_p_p, edtContra_p_p)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // El inicio de sesión fue exitoso
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user != null) {
                                String userId = user.getUid();

                                // Realizar una consulta a Firestore para obtener información adicional sobre el usuario
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                DocumentReference userRef = db.collection("reg_paciente").document(userId);

                                userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if (documentSnapshot.exists()) {
                                            // El documento existe en Firestore
                                            user_rol = documentSnapshot.getString("rol");

                                            System.out.println(user_rol);
                                            if (user_rol.equals("1")) {
                                                // Usuario es un paciente
                                                Intent intent = new Intent(getApplicationContext(), home_pacientes.class);
                                                startActivity(intent);
                                            }
                                            Toast.makeText(getApplicationContext(), "Sesión iniciada", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Intent intent = new Intent(getApplicationContext(), home_especialista.class);
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

    public void ir_reg_paciente(View view){
        Intent reg_paciente = new Intent( this, registrarse_paciente.class);
        startActivity(reg_paciente);
    }

    public void ir_rec_contra_p(View view){
        Intent rec_contra = new Intent( this, recuperar_contrasena.class);
        startActivity(rec_contra);
    }




}