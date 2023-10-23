package com.example.dpa_v6;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class home_especialista extends AppCompatActivity {

    private Dialog avisoSinInternet;
    private BroadcastReceiver networkReceiver;
    private TextView nombre_especialista;

    FirebaseAuth mAuth;
    Button cerrar_sesion;
    FirebaseFirestore db;
    private String idEspecialista;
    ProgressDialog progressDialog_h;
    private static final int TIEMPO_CARGA = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_especialista);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        nombre_especialista = findViewById(R.id.textNombreEspecialista);
        idEspecialista = mAuth.getCurrentUser().getUid();
        cerrar_sesion = (Button) findViewById(R.id.Btn_CerrarSE);

        progressDialog_h = new ProgressDialog(this);
        progressDialog_h.setMessage("Cerrando sesión..."); // Mensaje de carga
        progressDialog_h.setCancelable(false); // Bloquear la interacción con la actividad
        progressDialog_h.setIndeterminate(true); // Usar un ProgressBar indeterminado

        //___________________Conexion
        avisoSinInternet = new Dialog(this);
        avisoSinInternet.setContentView(R.layout.avisosininternet);
        avisoSinInternet.setCancelable(false);
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

        cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog_h.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run () {

                        Toast.makeText(home_especialista.this, "Sesion cerrada", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        progressDialog_h.dismiss();
                        finish();
                    }
                },TIEMPO_CARGA);
            }
        });

        DocumentReference documentReference = db.collection("reg_especialista").document(idEspecialista);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                idEspecialista = documentSnapshot.getString("id");
                nombre_especialista.setText("Dr. "+documentSnapshot.getString("nombre_e"));

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navi);
        bottomNavigationView.setSelectedItemId(R.id.home_esp);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_esp:
                    return true;

                case R.id.pacientes_es:
                    Intent intent2 = new Intent(getApplicationContext(), especialista_pacientes.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivityForResult(intent2, 0);
                    overridePendingTransition(0,0);
                    finish();
                    return true;

                case R.id.comprobar_es:
                    Intent intent3 = new Intent(getApplicationContext(), especialista_comprobar.class);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivityForResult(intent3, 0);
                    overridePendingTransition(0,0);
                    finish();
                    return true;
            }
            return false;
        });
    }

    public void ir_esp_pacientes(View view){
        Intent pacientes_e = new Intent( this, especialista_pacientes.class);
        startActivity(pacientes_e);
        finish();
    }

    public void ir_esp_comprobar(View view){
        Intent comprobar_e = new Intent( this, especialista_comprobar.class);
        startActivity(comprobar_e);
        finish();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkUtils.unregisterNetworkReceiver(this, networkReceiver);
    }


}