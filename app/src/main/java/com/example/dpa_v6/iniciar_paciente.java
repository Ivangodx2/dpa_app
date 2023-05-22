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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class iniciar_paciente extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText edtCorreo_p,edtContra_p;
    Button iniciar_sesion_paciente;

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
                String edtCorreo_p_p = edtContra_p.getText().toString();
                String edtContra_p_p = edtContra_p.getText().toString();

                if (edtCorreo_p_p.isEmpty() && edtContra_p_p.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingresar datos", Toast.LENGTH_SHORT).show();

                }else{
                    //Inicio de sesion
                    iniciarSesion_pac(edtCorreo_p_p,edtContra_p_p);
                }

            }
        });



    }

    public void iniciarSesion_pac (String edtCorreo_p_p, String edtContra_p_p){

        mAuth.signInWithEmailAndPassword(edtCorreo_p.getText().toString(),edtContra_p.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent= new Intent(getApplicationContext(), Home_pacientes.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Sesion iniciada", Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "Datos incorrectos", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
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