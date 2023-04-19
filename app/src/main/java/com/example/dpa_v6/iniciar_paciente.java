package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class iniciar_paciente extends AppCompatActivity {

    EditText edtCorreo_p;

    Button BtnInicio_p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_paciente);
        edtCorreo_p=findViewById(R.id.TextEspeEmailAddress_p);

        BtnInicio_p=findViewById(R.id.button_Iniciar_S_p);

        BtnInicio_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarUser_p("http://192.168.0.198/Base_DAtos_DPA/validacion_usuario_p.php");
            }
        });
    }

    private void validarUser_p(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent intent=new Intent(getApplicationContext(),home_especialista.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(iniciar_paciente.this, "Correo incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(iniciar_paciente.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametro= new HashMap<String,String>();
                parametro.put("email",edtCorreo_p.getText().toString());

                return parametro;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void ir_registro_pac(View view){
        Intent registro_pac = new Intent( this, registrarse_paciente.class);
        startActivity(registro_pac);
    }


}