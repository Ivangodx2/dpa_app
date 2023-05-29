package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class sintomas_identifica extends AppCompatActivity {

    CheckBox res1, res2, res3,res4,res5,res6,res7,res8;

    String IDPacietne, result_identifica;
    FirebaseFirestore db;
    int resultado_ident;
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

        //Recibir datos
        db = FirebaseFirestore.getInstance();
        Bundle intent = getIntent().getExtras();
        result_identifica = intent.getString("puntaje_identifica");
        IDPacietne = getIntent().getStringExtra("IDPaciente");

    }

    public void enviar_identifica(View view){

        if (res1.isChecked()){
            resultado_ident= resultado_ident+1;
        }
        if (res2.isChecked()){
            resultado_ident= resultado_ident+1;
        }
        if (res3.isChecked()){
            resultado_ident= resultado_ident+1;
        }
        if (res4.isChecked()){
            resultado_ident= resultado_ident+1;
        }
        if (res5.isChecked()){
            resultado_ident= resultado_ident+1;
        }
        if (res6.isChecked()){
            resultado_ident= resultado_ident+1;
        }
        if (res7.isChecked()){
            resultado_ident= resultado_ident+1;
        }
        if (res8.isChecked()){
            resultado_ident= resultado_ident+1;
        }


        GuardarDatosBD();
        Intent regresar_home_paciente = new Intent( this, Home_pacientes.class);
        finish();
        Toast.makeText(getApplicationContext(), "Respuestas guardadas", Toast.LENGTH_SHORT).show();
        startActivity(regresar_home_paciente);

    }

    public void salir_p(View view){
        Intent salir_a_home_paciente = new Intent( this, Home_pacientes.class);
        startActivity(salir_a_home_paciente);
        finish();
    }


    private void GuardarDatosBD(){
        //Actualizar base de datos
        String puntuacionPaciente_identif = Integer.toString(resultado_ident);

        Map<String,Object> map = new HashMap<>();
        map.put("puntaje_identifica",puntuacionPaciente_identif);
        db.collection("reg_paciente").document(IDPacietne).update(map);
    }

}