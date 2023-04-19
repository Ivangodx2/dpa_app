package com.example.dpa_v6;

import androidx.annotation.Nullable;
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

public class iniciar_sesion_especialista extends AppCompatActivity {

    EditText edtCorreo,edtContra;
    Button BtnEntrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion_especialista);
        edtCorreo=findViewById(R.id.TextEspeEmailAddress);
        edtContra=findViewById(R.id.EdittextEsepePassword);
        BtnEntrar=findViewById(R.id.button_Iniciar_S);

        BtnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarUser("http://192.168.0.198/Base_DAtos_DPA/validacion_usuarios.php");
            }
        });

    }

    private void validarUser(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent intent=new Intent(getApplicationContext(),home_especialista.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(iniciar_sesion_especialista.this, "Correo o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(iniciar_sesion_especialista.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros= new HashMap<String,String>();
                parametros.put("email",edtCorreo.getText().toString());
                parametros.put("contrasena",edtContra.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void ir_registro_especialista(View view){
        Intent ir_registro_especialista = new Intent( this, registrarse_especialista.class);
        startActivity(ir_registro_especialista);
    }

    public void ir_rec_contra_espe(View view){
        Intent ir_rec_contra = new Intent( this, recuperar_codigo.class);
        startActivity(ir_rec_contra);
    }
}