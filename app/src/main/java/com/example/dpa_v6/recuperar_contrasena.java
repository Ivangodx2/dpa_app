package com.example.dpa_v6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class recuperar_contrasena extends AppCompatActivity {
    Button enviar_corre;
    EditText emailedit_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena);

        enviar_corre = findViewById(R.id.button_recuperarcontra);
        emailedit_text = findViewById(R.id.EdittextCorreo_RC);
        enviar_corre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    public void validate(){
        String email = emailedit_text.getText().toString().trim();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailedit_text.setError("Correo invalido");
            return;
        }

        sendEmail(email);
    }

    public void sendEmail(String email){
        FirebaseAuth auth= FirebaseAuth.getInstance();
        String emailAddress=email;
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(recuperar_contrasena.this, "Correo enviado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(recuperar_contrasena.this,iniciar_sesion_especialista.class);
                            startActivity(intent);
                            finish();

                        }else {
                            Toast.makeText(recuperar_contrasena.this, "Correo invalido", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}