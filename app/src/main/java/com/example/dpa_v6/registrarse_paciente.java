package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class registrarse_paciente extends AppCompatActivity {

    EditText p1,p2,p3,p4,p5;
    Button registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse_paciente);
        relacionamosVistas_p();
    }

    public void relacionamosVistas_p(){
        p1=(EditText) findViewById(R.id.EditText_nombre_p);
        p2=(EditText) findViewById(R.id.EditText_apellidos_p);
        p3=(EditText) findViewById(R.id.EditText_correo_e_p);
        p4=(EditText) findViewById(R.id.editTextTelefono_p);
        p5=(EditText) findViewById(R.id.editTextEdad_p);
        registro=(Button) findViewById(R.id.button_Registro_bd_pac);
    }

    public void insertar_p(View v){
        final String nombre=p1.getText().toString();
        final String apellidos=p2.getText().toString();
        final String correo_e=p3.getText().toString();
        final String num_telef=p4.getText().toString();
        final String edad=p5.getText().toString();

        String url="http://192.168.1.73/Base_DAtos_DPA/ingresar_dtos_paciente.php?nombre="+nombre+"&apellidos="+apellidos+"&email="+correo_e+"&num_telef="+num_telef+"&edad="+edad;
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