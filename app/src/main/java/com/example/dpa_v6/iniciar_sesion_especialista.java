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

public class iniciar_sesion_especialista extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText edtCorreo,edtContra;
    Button btn_iniciar_s_e;
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

    public void iniciarSesion_espe (String edtCorreo_e, String edtContra_e){

        mAuth.signInWithEmailAndPassword(edtCorreo.getText().toString(),edtContra.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent= new Intent(getApplicationContext(), home_especialista.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Sesión iniciada", Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "Datos incorrectos", task.getException());
                            Toast.makeText(getApplicationContext(), "Datos incorrectos.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
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