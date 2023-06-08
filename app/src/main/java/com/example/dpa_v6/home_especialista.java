package com.example.dpa_v6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class home_especialista extends AppCompatActivity {

    private TextView nombre_especialista;

    FirebaseAuth mAuth;
    Button btn_pacien, btn_comp;
    FirebaseFirestore db;
    private String idEspecialista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_especialista);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        nombre_especialista = findViewById(R.id.textNombreEspecialista);
        idEspecialista = mAuth.getCurrentUser().getUid();
        btn_pacien=findViewById(R.id.btnPacientes);
        btn_comp=findViewById(R.id.btnComprobar);

        DocumentReference documentReference = db.collection("reg_especialista").document(idEspecialista);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                idEspecialista = documentSnapshot.getString("id");
                if (documentSnapshot.getString("nombre_e") == null){
                    finish();
                    Intent ispaciente= new Intent(getApplicationContext(),Home_pacientes.class);
                    startActivity(ispaciente);

                }else{
                    nombre_especialista.setText("Dr. "+documentSnapshot.getString("nombre_e"));
                }
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
    }

    public void ir_esp_comprobar(View view){
        Intent comprobar_e = new Intent( this, especialista_comprobar.class);
        startActivity(comprobar_e);

    }

    public void CerrarSesion_e(View view){

        try {
        mAuth.signOut();
        finish();
        btn_pacien.setEnabled(false);
        btn_comp.setEnabled(false);
        Toast.makeText(this, "Se ha cerrado sesión", Toast.LENGTH_SHORT).show();


            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}