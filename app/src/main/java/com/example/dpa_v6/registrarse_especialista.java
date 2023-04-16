package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.*;

public class registrarse_especialista extends AppCompatActivity {
EditText e1,e2,e3,e4,e5,e6,e7,e8;
Button b,mostrar,buscar;

RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse_especialista);
        relacionamosVistas();

    }

    public void relacionamosVistas(){
        e1=(EditText) findViewById(R.id.EditText_nombre);
        e2=(EditText) findViewById(R.id.EditText_apellidos);
        e3=(EditText) findViewById(R.id.EditText_correo_e);
        e4=(EditText) findViewById(R.id.EditText_contrasena);
        e5=(EditText) findViewById(R.id.editTextCedula);
        b=(Button) findViewById(R.id.button_Registro_bd);
    }

    public void insertar(View v){
        final String nombre=e1.getText().toString();
        final String apellidos=e2.getText().toString();
        final String correo_e=e3.getText().toString();
        final String contrasena=e4.getText().toString();
        final String cedula_p=e5.getText().toString();

        String url="http://192.168.1.73/Base_DAtos_DPA/ingresar_dtos_especial.php?nombre="+nombre+"&apellidos="+apellidos+"&email="+correo_e+"&contrasena="+contrasena+"&cedula_p="+cedula_p;
        RequestQueue servicio= Volley.newRequestQueue(this);
        StringRequest respuesta=new StringRequest(
                Request.Method.GET, url,(response) -> {
            Toast.makeText(getApplicationContext(),
                    response,Toast.LENGTH_SHORT).show();
        },error -> {
            Toast.makeText(getApplicationContext(), "Error con la comunicación", Toast.LENGTH_SHORT).show();
        });
        servicio.add(respuesta);
    }

}