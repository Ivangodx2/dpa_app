package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class especialista_comprobar extends AppCompatActivity {



    RecyclerView RecycleV_listpacient_bar;
    e_tabla_comprobar_adapter e_Tabla_comprobar_adapter;
    FirebaseFirestore base_datos;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especialista_comprobar);

        //Lista de pacientes
        RecycleV_listpacient_bar = findViewById(R.id.list_pacientes_bar);
        RecycleV_listpacient_bar.setLayoutManager(new LinearLayoutManager(this));
        base_datos= FirebaseFirestore.getInstance();

        Query query =base_datos.collection("reg_paciente");

        FirestoreRecyclerOptions<e_tabla_pacientes> fireStoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<e_tabla_pacientes>()
                .setQuery(query, e_tabla_pacientes.class).build();

        e_Tabla_comprobar_adapter = new e_tabla_comprobar_adapter(fireStoreRecyclerOptions);
        e_Tabla_comprobar_adapter.notifyDataSetChanged();
        RecycleV_listpacient_bar.setAdapter(e_Tabla_comprobar_adapter);





        //Nav_bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navi);
        bottomNavigationView.setSelectedItemId(R.id.comprobar_es);

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
                    Intent intent2 = new Intent(getApplicationContext(), especialista_pacientes.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivityForResult(intent2, 0);
                    overridePendingTransition(0,0);
                    finish();

                    return true;

                case R.id.comprobar_es:
                    return true;
            }
            return false;
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        e_Tabla_comprobar_adapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        e_Tabla_comprobar_adapter.stopListening();
    }
}