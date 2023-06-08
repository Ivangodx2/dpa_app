package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class especialista_generar_correo extends AppCompatActivity {

    Button btn_enviar, btn_salir;
    EditText etx_titulo, etx_contenido;
    TextView tv_correo_paciente;
    String idpaciente,correo_real;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especialista_generar_correo);

        tv_correo_paciente = findViewById(R.id.textVCorreo_paciente);
        etx_titulo = findViewById(R.id.editTTitulo);
        etx_contenido = findViewById(R.id.editTContenido);
        btn_enviar = findViewById(R.id.btn_enviar_correo);


        db = FirebaseFirestore.getInstance();
        idpaciente = getIntent().getStringExtra("Idpaciente");

        db.collection("reg_paciente").document(idpaciente).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String correo_paciente = documentSnapshot.getString("correo_e_p");
                    correo_real = correo_paciente;
                    tv_correo_paciente.setText("Para: "+correo_real);
                }
            }
        });


        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo_enviar=correo_real;
                String correo_titulo=etx_titulo.getText().toString();
                String correo_contenido=etx_contenido.getText().toString();


                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {correo_enviar});

                intent.putExtra(Intent.EXTRA_SUBJECT,correo_titulo);
                intent.putExtra(Intent.EXTRA_TEXT, correo_contenido);

                intent.setType("message/rfc822");

                startActivity(
                        Intent.createChooser(intent,""));
                finish();
            }

        });
    }

    public void salir_e(View view){
        finish();
    }
}