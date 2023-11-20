package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;


import com.example.dpa_v6.registro_controller.Registro_especialista_controller;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registrarse_especialista extends AppCompatActivity {

    private Dialog avisoSinInternet;
    private Registro_especialista_controller registro_especialista_controller;
    private BroadcastReceiver networkReceiver;
    private FirebaseAuth mAuth;
    private FirebaseFirestore datos_especialista;
    ProgressDialog progressDialog;
    EditText enombre1;
    EditText eapelli2;
    EditText ecorreo3;
    EditText econtra4;
    EditText ecedula5;
    Button btn_dtos_espe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse_especialista);
        mAuth = FirebaseAuth.getInstance();
        datos_especialista = FirebaseFirestore.getInstance();
        enombre1 = findViewById(R.id.EditText_nombre);
        eapelli2 = findViewById(R.id.EditText_apellidos);
        ecorreo3 = findViewById(R.id.EditText_correo_e);
        econtra4 = findViewById(R.id.EditText_contrasena);
        ecedula5 = findViewById(R.id.editTextCedula);
        btn_dtos_espe = findViewById(R.id.button_Registro_bd);
        avisoSinInternet = new Dialog(this);
        avisoSinInternet.setContentView(R.layout.avisosininternet);
        avisoSinInternet.setCancelable(false);
        registro_especialista_controller = new Registro_especialista_controller(this,this);

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

        //Cambio a mayuscula
        enombre1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No es necesario implementar este método
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No es necesario implementar este método
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String textoNE = editable.toString();
                if (!textoNE.isEmpty()) {
                    String primeraLetraMayusculaNE = textoNE.substring(0, 1).toUpperCase();
                    String restoDelTextoNE = textoNE.substring(1);
                    String textoModificadoNE = primeraLetraMayusculaNE + restoDelTextoNE;

                    enombre1.removeTextChangedListener(this);
                    enombre1.setText(textoModificadoNE);
                    enombre1.setSelection(textoModificadoNE.length()); // Coloca el cursor al final del texto modificado
                }
            }
        });

        eapelli2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No es necesario implementar este método
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No es necesario implementar este método
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String textoAE = editable.toString();
                if (!textoAE.isEmpty()) {
                    String primeraLetraMayusculaAE = textoAE.substring(0, 1).toUpperCase();
                    String restoDelTextoAE = textoAE.substring(1);
                    String textoModificadoAE = primeraLetraMayusculaAE + restoDelTextoAE;

                    eapelli2.removeTextChangedListener(this);
                    eapelli2.setText(textoModificadoAE);
                    eapelli2.setSelection(textoModificadoAE.length()); // Coloca el cursor al final del texto modificado
                }
            }
        });


        NetworkUtils.registerNetworkReceiver(this, networkReceiver);
        btn_dtos_espe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre_e = enombre1.getText().toString();
                String apellidos_e = eapelli2.getText().toString();
                String correo_e = ecorreo3.getText().toString().trim();
                String cedula_p_e = ecedula5.getText().toString().trim();
                String contrasena_e = econtra4.getText().toString();


                if (nombre_e.isEmpty() && apellidos_e.isEmpty() && cedula_p_e.isEmpty() && correo_e.isEmpty() && contrasena_e.isEmpty() || nombre_e.isEmpty() || apellidos_e.isEmpty() || cedula_p_e.isEmpty() || correo_e.isEmpty() || contrasena_e.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingresar datos faltantes", Toast.LENGTH_SHORT).show();

                } else {
                    if (validarDatos()){
                        //guardar datos
                        registro_especialista_controller.registrarEspecialista(nombre_e, apellidos_e, correo_e, cedula_p_e, contrasena_e);
                    }
                }
            }
        });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkUtils.unregisterNetworkReceiver(this, networkReceiver);
    }

    public boolean validarDatos(){
        boolean retorno=true;
        String nombreValidarE = enombre1.getText().toString();
        String apellidoValidarE = eapelli2.getText().toString();
        String correoValidarE = ecorreo3.getText().toString();
        String contrasenaValidarE = econtra4.getText().toString();
        String cedulaPValidarE = ecedula5.getText().toString();
        if (contieneCaracteresEspeciales(nombreValidarE)){
            enombre1.setError("Nombre(s) no válido(s)");
            retorno=false;
        }
        if (contieneCaracteresEspeciales(apellidoValidarE)){
            eapelli2.setError("Apellidos no válidos");
            retorno=false;
        }
        if (!isValidEmail(correoValidarE)) {
            ecorreo3.setError("Correo electrónico no válido");}
        
        if(contrasenaValidarE.length()>20){
            econtra4.setError("Usa 20 caracteres o menos para tu contraseña");
            retorno=false;
        } else if (contrasenaValidarE.length()<=5) {
            econtra4.setError("Usa 6 caracteres o más para tu contraseña ");
            retorno=false;
        }
        if (cedulaPValidarE.length()<8){
            ecedula5.setError("Debe tener 8 números");
            retorno=false;
        }

        return retorno;
    }
    private boolean contieneCaracteresEspeciales(String cadena) {
        return !cadena.matches("^[a-zA-ZÁ-Úá-úÜüñÑ ]+$");
    }
    public static boolean isValidEmail(String correo) {
        return Patterns.EMAIL_ADDRESS.matcher(correo).matches();
    }

}