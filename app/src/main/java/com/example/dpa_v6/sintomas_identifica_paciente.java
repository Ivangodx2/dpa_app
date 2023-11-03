package com.example.dpa_v6;

import static com.example.dpa_v6.NetworkUtils.isNetworkAvailable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class sintomas_identifica_paciente extends AppCompatActivity {

    CheckBox res1, res2, res3,res4,res5,res6,res7,res8,res9;

    String IDPacietne, result_identifica;
    FirebaseFirestore db;
    int resultado_ident;
    Button info_identifica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas_identifica);
        res1= findViewById(R.id.checkBox);
        res2= findViewById(R.id.checkBox2);
        res3= findViewById(R.id.checkBox3);
        res4= findViewById(R.id.checkBox4);
        res5= findViewById(R.id.checkBox5);
        res6= findViewById(R.id.checkBox6);
        res7= findViewById(R.id.checkBox7);
        res8= findViewById(R.id.checkBox8);
        res9= findViewById(R.id.checkBox9);
        info_identifica= findViewById(R.id.icn_identifica);

        //Recibir datos
        db = FirebaseFirestore.getInstance();
        Bundle intent = getIntent().getExtras();
        result_identifica = intent.getString("puntaje_identifica");
        IDPacietne = getIntent().getStringExtra("IDPaciente");

        info_identifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sintomas_identifica_paciente.this, popup_identifica.class));
            }
        });


        res9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    res1.setChecked(false);
                    res1.setEnabled(false);
                    res2.setChecked(false);
                    res2.setEnabled(false);
                    res3.setChecked(false);
                    res3.setEnabled(false);
                    res4.setChecked(false);
                    res4.setEnabled(false);
                    res5.setChecked(false);
                    res5.setEnabled(false);
                    res6.setChecked(false);
                    res6.setEnabled(false);
                    res7.setChecked(false);
                    res7.setEnabled(false);
                    res8.setChecked(false);
                    res8.setEnabled(false);
                }else {
                    res1.setEnabled(true);
                    res2.setEnabled(true);
                    res3.setEnabled(true);
                    res4.setEnabled(true);
                    res5.setEnabled(true);
                    res6.setEnabled(true);
                    res7.setEnabled(true);
                    res8.setEnabled(true);
                }
            }
        });

    }


    public void salir_p(View view){
        finish();
    }


    public void GuardarDatosBD(View view){

        if (res1.isChecked() == false && res2.isChecked() == false && res3.isChecked() == false && res4.isChecked() == false && res5.isChecked() == false && res6.isChecked() == false && res7.isChecked() == false && res8.isChecked() == false && res9.isChecked() == false) {
            Toast.makeText(getApplicationContext(), "Elegir una respuesta o respuestas", Toast.LENGTH_SHORT).show();
        }
        else{
            if (res1.isChecked()) {
                resultado_ident = resultado_ident + 1;
            }
            if (res2.isChecked()) {
                resultado_ident = resultado_ident + 1;
            }
            if (res3.isChecked()) {
                resultado_ident = resultado_ident + 1;
            }
            if (res4.isChecked()) {
                resultado_ident = resultado_ident + 1;
            }
            if (res5.isChecked()) {
                resultado_ident = resultado_ident + 1;
            }
            if (res6.isChecked()) {
                resultado_ident = resultado_ident + 1;
            }
            if (res7.isChecked()) {
                resultado_ident = resultado_ident + 1;
            }
            if (res8.isChecked()) {
                resultado_ident = resultado_ident + 1;
            }

            //Actualizar base de datos
            if (!isNetworkAvailable(getApplicationContext())) {
                Toast.makeText(getApplicationContext(), "No se pueden guardar los datos.", Toast.LENGTH_SHORT).show();
                return;
            } else {
                String puntuacionPaciente_identif = Integer.toString(resultado_ident);

                Map<String, Object> map = new HashMap<>();
                map.put("puntaje_identifica", puntuacionPaciente_identif);
                db.collection("reg_paciente").document(IDPacietne).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // La actualización se realizó con éxito
                                Toast.makeText(getApplicationContext(), "Respuestas guardadas", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }

}