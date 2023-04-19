package com.example.dpa_v6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
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
import com.google.firebase.firestore.FirebaseFirestore;


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

    private FirebaseAuth mAuth;
    private FirebaseFirestore datos_especialista;
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
        enombre1= findViewById(R.id.EditText_nombre);
        eapelli2= findViewById(R.id.EditText_apellidos);
        ecorreo3= findViewById(R.id.EditText_correo_e);
        econtra4= findViewById(R.id.EditText_contrasena);
        ecedula5= findViewById(R.id.editTextCedula);
        btn_dtos_espe= findViewById(R.id.button_Registro_bd);

        btn_dtos_espe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre_e = enombre1.getText().toString();
                String apellidos_e = eapelli2.getText().toString();
                String cedula_p_e = ecedula5.getText().toString().trim();

                if (nombre_e.isEmpty() && apellidos_e.isEmpty() && cedula_p_e.isEmpty() ){
                    Toast.makeText(getApplicationContext(), "Ingresar datos faltantes", Toast.LENGTH_SHORT).show();

                }else{
                    postdtos(nombre_e,apellidos_e,cedula_p_e);
                    registrarEspecialista(v);
                    Toast.makeText(getApplicationContext(), "REGISTO HECHO", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), iniciar_sesion_especialista.class);
                }
            }
        });


    }

    private void postdtos(String nombre_e, String apellidos_e, String cedula_p_e) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombre_e);
        map.put("apellidos", apellidos_e);
        map.put("cedula_pro", cedula_p_e);

        datos_especialista.collection("reg_especialista").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {


            }
            }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "No se guardaron los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    public void registrarEspecialista(View view){

        mAuth.createUserWithEmailAndPassword(ecorreo3.getText().toString(), econtra4.getText().toString())

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            //updateUI(null);
                        }
                    }
                });}

}