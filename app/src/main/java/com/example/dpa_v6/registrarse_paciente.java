package com.example.dpa_v6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dpa_v6.registro_controller.Registro_especialista_controller;
import com.example.dpa_v6.registro_controller.Registro_paciente_controller;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registrarse_paciente extends AppCompatActivity {

    private Dialog avisoSinInternet;
    private BroadcastReceiver networkReceiver;

    private Registro_paciente_controller registro_paciente_controller;
    private FirebaseAuth mAuth;
    private FirebaseFirestore datos_paciente;
    EditText enombre1;
    EditText eapelli2;
    EditText ecorreo3;
    EditText econtra4;
    EditText etelef5;
    EditText eedad6;

    Button btn_dtos_paci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse_paciente);
        mAuth = FirebaseAuth.getInstance();
        datos_paciente = FirebaseFirestore.getInstance();
        enombre1= findViewById(R.id.EditText_nombre_p);
        eapelli2= findViewById(R.id.EditText_apellidos_p);
        ecorreo3= findViewById(R.id.EditText_correo_e_p);
        econtra4= findViewById(R.id.EditText_contrasena_p);
        etelef5= findViewById(R.id.editTextTelefono_p);
        eedad6= findViewById(R.id.editTextEdad_p);
        btn_dtos_paci= findViewById(R.id.button_Registro_bd_pac);
        registro_paciente_controller = new Registro_paciente_controller(this,this);
        //___________________Conexion a internet
        avisoSinInternet = new Dialog(this);
        avisoSinInternet.setContentView(R.layout.avisosininternet);
        avisoSinInternet.setCancelable(false);

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
                String textoNP = editable.toString();
                if (!textoNP.isEmpty()) {
                    String primeraLetraMayusculaNP = textoNP.substring(0, 1).toUpperCase();
                    String restoDelTextoNP = textoNP.substring(1);
                    String textoModificadoNP = primeraLetraMayusculaNP + restoDelTextoNP;

                    enombre1.removeTextChangedListener(this);
                    enombre1.setText(textoModificadoNP);
                    enombre1.setSelection(textoModificadoNP.length()); // Coloca el cursor al final del texto modificado
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
                String textoAP = editable.toString();
                if (!textoAP.isEmpty()) {
                    String primeraLetraMayusculaAP = textoAP.substring(0, 1).toUpperCase();
                    String restoDelTextoAP = textoAP.substring(1);
                    String textoModificadoAP = primeraLetraMayusculaAP + restoDelTextoAP;

                    eapelli2.removeTextChangedListener(this);
                    eapelli2.setText(textoModificadoAP);
                    eapelli2.setSelection(textoModificadoAP.length()); // Coloca el cursor al final del texto modificado
                }
            }
        });

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


        btn_dtos_paci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre_p = enombre1.getText().toString();
                String apellidos_p = eapelli2.getText().toString();
                String correo_p = ecorreo3.getText().toString().trim();
                String num_telef = etelef5.getText().toString().trim();
                String edad_p = eedad6.getText().toString().trim();
                String contrasena_p = econtra4.getText().toString();


                if (nombre_p.isEmpty() && apellidos_p.isEmpty() && correo_p.isEmpty() && num_telef.isEmpty() && edad_p.isEmpty() || nombre_p.isEmpty() || apellidos_p.isEmpty() || correo_p.isEmpty() || num_telef.isEmpty() || edad_p.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingresar datos faltantes", Toast.LENGTH_SHORT).show();

                }else{
                    if(validarDatos()){
                        //registro
                        registro_paciente_controller.registrarPasientes(nombre_p,apellidos_p,correo_p,num_telef,edad_p,contrasena_p);
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
        String nombreValidarP = enombre1.getText().toString();
        String apellidoValidarP = eapelli2.getText().toString();
        String correoValidarP = ecorreo3.getText().toString();
        String contrasenaValidarP = econtra4.getText().toString();
        String numeroTValidarP = etelef5.getText().toString();
        if (contieneCaracteresEspeciales(nombreValidarP)){
            enombre1.setError("Nombre(s) no valido(s)");
            retorno=false;
        }
        if (contieneCaracteresEspeciales(apellidoValidarP)){
            eapelli2.setError("Apellidos no validos");
            retorno=false;
        }
        if (!isValidEmail(correoValidarP)) {
            ecorreo3.setError("Correo electrónico no válido");}

        if(contrasenaValidarP.length()>20){
            econtra4.setError("Debe ser menor o igual 20 caracteres");
            retorno=false;
        }
        if (numeroTValidarP.length()<10){
            etelef5.setError("Debe tener 10 números");
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