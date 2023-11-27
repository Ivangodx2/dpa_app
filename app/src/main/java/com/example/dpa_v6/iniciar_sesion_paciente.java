package com.example.dpa_v6;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class iniciar_sesion_paciente extends AppCompatActivity {

    private Dialog avisoSinInternet;
    private BroadcastReceiver networkReceiver;
    FirebaseAuth mAuth;
    EditText edtCorreo_p,edtContra_p;
    Button iniciar_sesion_paciente;
    ProgressDialog progressDialog;
    String user_rol;
    Button iniciar_sesion_btn;
    private static final int TIEMPO_CARGA = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_paciente);
        edtCorreo_p=findViewById(R.id.TextEspeEmailAddress_p);
        edtContra_p=findViewById(R.id.EdittextContra_Pas);
        iniciar_sesion_paciente=findViewById(R.id.button_Iniciar_S_p);
        mAuth = FirebaseAuth.getInstance();
        iniciar_sesion_btn=findViewById(R.id.button_Iniciar_S_p);

        //___________________Conexion
        avisoSinInternet = new Dialog(this);
        avisoSinInternet.requestWindowFeature(Window.FEATURE_NO_TITLE);
        avisoSinInternet.setContentView(R.layout.avisosininternet);
        avisoSinInternet.setCancelable(false);
        avisoSinInternet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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


        iniciar_sesion_paciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edtCorreo_p_p = edtCorreo_p.getText().toString();
                String edtContra_p_p = edtContra_p.getText().toString();

                if (edtCorreo_p_p.isEmpty() && edtContra_p_p.isEmpty() || edtCorreo_p_p.isEmpty() || edtContra_p_p.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingresar datos", Toast.LENGTH_SHORT).show();

                }else {
                    //Inicio de sesion
                    iniciarSesion_pac(edtCorreo_p_p,edtContra_p_p);
                }

            }
        });



    }


    public void iniciarSesion_pac(String edtCorreo_p_p, String edtContra_p_p) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Iniciando sesión..."); // Mensaje de carga
        progressDialog.setCancelable(false); // Bloquear la interacción con la actividad
        progressDialog.setIndeterminate(true); // Usar un ProgressBar indeterminado
        progressDialog.show();


        mAuth.signInWithEmailAndPassword(edtCorreo_p_p, edtContra_p_p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (task.isSuccessful()) {
                                    // El inicio de sesión fue exitoso
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        String userId = user.getUid();

                                        // Realizar una consulta a Firestore para obtener información adicional sobre el usuario
                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                        DocumentReference userRef = db.collection("reg_paciente").document(userId);
                                        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if (documentSnapshot.exists()) {
                                                    // El documento existe en Firestore
                                                    user_rol = documentSnapshot.getString("rol");

                                                    if (user_rol.equals("1")) {
                                                        if (!user.isEmailVerified()) {
                                                                progressDialog.dismiss();
                                                                Toast.makeText(getApplicationContext(), "Correo no verificado", Toast.LENGTH_SHORT).show();
                                                        }else{
                                                            Intent intent = new Intent(getApplicationContext(), home_pacientes.class);
                                                            startActivity(intent);
                                                            progressDialog.dismiss();
                                                            Toast.makeText(getApplicationContext(), "Sesión iniciada", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                                else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(getApplicationContext(), "El usuario no es paciente", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Manejar el error de consulta a Firestore
                                                progressDialog.dismiss();
                                                Log.e(TAG, "Error al consultar Firestore", e);
                                            }
                                        });

                                    } else {
                                        // El usuario es nulo, lo que generalmente no debería suceder si el inicio de sesión es exitoso
                                        progressDialog.dismiss();
                                        Log.e(TAG, "El usuario es nulo después del inicio de sesión exitoso");
                                    }
                                } else {
                                    // Si el inicio de sesión falla, muestra un mensaje al usuario
                                    progressDialog.dismiss();
                                    Log.w(TAG, "Datos incorrectos", task.getException());
                                    Toast.makeText(getApplicationContext(), "Datos incorrectos.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },TIEMPO_CARGA);
                    }

        });
    }

    public void ir_reg_paciente(View view){
        Intent reg_paciente = new Intent( this, registrarse_paciente.class);
        startActivity(reg_paciente);
    }

    public void ir_rec_contra_p(View view){
        Intent rec_contra = new Intent( this, recuperar_contrasena.class);
        startActivity(rec_contra);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkUtils.unregisterNetworkReceiver(this, networkReceiver);
    }


}