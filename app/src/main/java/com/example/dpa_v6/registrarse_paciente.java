package com.example.dpa_v6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

public class registrarse_paciente extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore datos_paciente;
    EditText enombre1;
    EditText eapelli2;
    EditText ecorreo3;
    EditText econtra4;
    EditText etelef5;
    EditText eedad6;

    Button btn_dtos_paci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse_paciente);
        mAuth = FirebaseAuth.getInstance();
        datos_paciente = FirebaseFirestore.getInstance();
        enombre1= findViewById(R.id.EditText_nombre_p);
        eapelli2= findViewById(R.id.EditText_apellidos_p);
        ecorreo3= findViewById(R.id.EditText_correo_e_p);
        econtra4= findViewById(R.id.EditText_contrasena_p);
        etelef5= findViewById(R.id.editTextTelefono_p);
        eedad6= findViewById(R.id.editTextTelefono_p);
        btn_dtos_paci= findViewById(R.id.button_Registro_bd_pac);

        btn_dtos_paci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre_p = enombre1.getText().toString();
                String apellidos_p = eapelli2.getText().toString();
                String correo_p = ecorreo3.getText().toString().trim();
                String num_telef = etelef5.getText().toString().trim();
                String edad_p = eedad6.getText().toString().trim();
                String contrasena_p = econtra4.getText().toString();


                if (nombre_p.isEmpty() && apellidos_p.isEmpty() && correo_p.isEmpty() && num_telef.isEmpty() && edad_p.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingresar datos faltantes", Toast.LENGTH_SHORT).show();

                }else{
                    //registro
                    registrarPasientes(nombre_p,apellidos_p,correo_p,num_telef,edad_p,contrasena_p);
                }
            }
        });

    }

    private void registrarPasientes(String nombre_p, String apellidos_p, String correo_p, String num_telef, String edad_p, String contrasena_p){
        mAuth.createUserWithEmailAndPassword(correo_p,contrasena_p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String id = mAuth.getCurrentUser().getUid();
                Map<String, Object> map = new HashMap<>();
                map.put("id",id);
                map.put("nombre", nombre_p);
                map.put("apellidos", apellidos_p);
                map.put("correo_e_p", correo_p);
                map.put("num_telefonico", num_telef);
                map.put("edad", edad_p);
                map.put("contra_p",contrasena_p);
                datos_paciente.collection("reg_paciente").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        startActivity(new Intent(registrarse_paciente.this, iniciar_paciente.class));
                        Toast.makeText(getApplicationContext(), "REGISTO HECHO", Toast.LENGTH_SHORT).show();
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