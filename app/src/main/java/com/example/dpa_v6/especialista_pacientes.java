package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class especialista_pacientes extends AppCompatActivity {

    private Dialog avisoSinInternet;
    private BroadcastReceiver networkReceiver;
    SearchView sv_pacientes;
    RecyclerView RecycleV_listpacient;
    e_tabla_p_adapter list_pac_adapter;
    List<e_tabla_pacientes> pacientes_list;
    FirebaseFirestore base_datos;
    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especialista_pacientes);


        //Lista de pacientes
        RecycleV_listpacient = findViewById(R.id.list_pacientes);
        RecycleV_listpacient.setLayoutManager(new LinearLayoutManager(this));
        base_datos= FirebaseFirestore.getInstance();
        pacientes_list = new ArrayList<>();
        sv_pacientes=findViewById(R.id.searchview_p);

        //___________________Conexion
        avisoSinInternet = new Dialog(this);
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
        //fitrado
        filtrado_view();

        //Datos
        query =base_datos.collection("reg_paciente");

        FirestoreRecyclerOptions<e_tabla_pacientes> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<e_tabla_pacientes>()
                .setQuery(query, e_tabla_pacientes.class).build();

        list_pac_adapter = new e_tabla_p_adapter(firestoreRecyclerOptions, this);
        list_pac_adapter.notifyDataSetChanged();
        RecycleV_listpacient.setAdapter(list_pac_adapter);


        //NavigationBar
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navi);
        bottomNavigationView.setSelectedItemId(R.id.pacientes_es);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_esp:
                    Intent intent = new Intent(getApplicationContext(), home_especialista.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivityForResult(intent, 0);
                    overridePendingTransition(0,0);
                    finish();
                    return true;

                case R.id.pacientes_es:

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

    private void filtrado_view() {
        sv_pacientes.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                textSeach(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                textSeach(newText);
                return false;
            }
        });
    }

    private void textSeach(String text) {
        FirestoreRecyclerOptions<e_tabla_pacientes> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<e_tabla_pacientes>()
                .setQuery(query.orderBy("nombre").startAt(text).endAt(text+"~"), e_tabla_pacientes.class).build();

        list_pac_adapter = new e_tabla_p_adapter(firestoreRecyclerOptions, this);
        list_pac_adapter.startListening();
        RecycleV_listpacient.setAdapter(list_pac_adapter);

    }


    @Override
    protected void onStart(){
        super.onStart();
        list_pac_adapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        list_pac_adapter.stopListening();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkUtils.unregisterNetworkReceiver(this, networkReceiver);
    }

    @Override
    public void onBackPressed() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navi);
        bottomNavigationView.setSelectedItemId(R.id.home_esp);
        Intent intent = new Intent(getApplicationContext(), home_especialista.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0,0);
        finish();
    }

}