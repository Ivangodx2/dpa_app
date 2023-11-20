package com.example.dpa_v6;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.dpa_v6.inicio_sesion_controller.InicioSesion_especialista_controller;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class iniciar_sesion_especialista extends AppCompatActivity {

    private Dialog avisoSinInternet;
    private BroadcastReceiver networkReceiver;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    EditText edtCorreo,edtContra;
    Button btn_iniciar_s_e;
    String user_rol;
    private static final int TIEMPO_CARGA = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion_especialista);
        edtCorreo=findViewById(R.id.TextEspeEmailAddress);
        edtContra=findViewById(R.id.EdittextEsepePassword);
        btn_iniciar_s_e=findViewById(R.id.button_Iniciar_S);
        mAuth = FirebaseAuth.getInstance();
        avisoSinInternet = new Dialog(this);
        avisoSinInternet.setContentView(R.layout.avisosininternet);
        avisoSinInternet.setCancelable(false);

        InicioSesion_especialista_controller inicioSesion_especialista_controller = new InicioSesion_especialista_controller(this,this);
        networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isNetworkAvailable = NetworkUtils.isNetworkAvailable(context);
                if (isNetworkAvailable) {
                    NetworkUtils.ocultarAvisoSinConexion(avisoSinInternet);
                } else {
                    NetworkUtils.mostrarAvisoSinConexion(context, avisoSinInternet);
                }
            }
        };
        NetworkUtils.registerNetworkReceiver(this, networkReceiver);
        btn_iniciar_s_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edtCorreo_e = edtCorreo.getText().toString();
                String edtContra_e = edtContra.getText().toString();

                if (edtCorreo_e.isEmpty() && edtContra_e.isEmpty() || edtCorreo_e.isEmpty() || edtContra_e.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingresar datos", Toast.LENGTH_SHORT).show();

                }else {
                    //Inicio de sesion
                    inicioSesion_especialista_controller.iniciarSesionEspecialista(edtCorreo_e, edtContra_e);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkUtils.unregisterNetworkReceiver(this, networkReceiver);
    }



}