package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class especialista_pacientes extends AppCompatActivity {


    RecyclerView RecycleV_listpacient;
    e_tabla_p_adapter list_pac_adapter;
    FirebaseFirestore base_datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especialista_pacientes);


        //Lista de pacientes
        RecycleV_listpacient = findViewById(R.id.list_pacientes);
        RecycleV_listpacient.setLayoutManager(new LinearLayoutManager(this));
        base_datos= FirebaseFirestore.getInstance();

        Query query =base_datos.collection("reg_paciente");

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
            }
            return false;
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        list_pac_adapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        list_pac_adapter.startListening();
    }

}