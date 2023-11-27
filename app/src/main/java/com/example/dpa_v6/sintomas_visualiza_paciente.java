package com.example.dpa_v6;

import static com.example.dpa_v6.NetworkUtils.isNetworkAvailable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;


public class sintomas_visualiza_paciente extends AppCompatActivity {

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    RadioButton opc1f, opc2f, opc3f, opc4f;
    TextView ntitulo_f, txttitulo_f;
    Button siguiente_f, btnsalir_f, info_visualiza;

    int resultado_visualiza=0;
    int Ntitulo_f=1;
    String IDPacietne;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas_visualiza);
        img1 = (ImageView) findViewById(R.id.imageViewF1);
        img2 = (ImageView) findViewById(R.id.imageViewF2);
        img3 = (ImageView) findViewById(R.id.imageViewF3);

        opc1f = findViewById(R.id.radioButton);
        opc2f = findViewById(R.id.radioButton2);
        opc3f = findViewById(R.id.radioButton3);
        opc4f = findViewById(R.id.radioButton4);

        ntitulo_f = (TextView) findViewById(R.id.tituloF);
        txttitulo_f=(TextView) findViewById(R.id.sintoma_titulo);

        siguiente_f=(Button) findViewById(R.id.siguiente_fobia);
        btnsalir_f=(Button) findViewById(R.id.salir_fobia);
        info_visualiza= findViewById(R.id.Btn_info_visualiza);

        //Recibir datos
        db = FirebaseFirestore.getInstance();
        Bundle intent = getIntent().getExtras();
        IDPacietne = getIntent().getStringExtra("IDPaciente");

        info_visualiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sintomas_visualiza_paciente.this, popup_visualiza.class));
            }
        });


    }



    public void mueve_click(View view) {
        if (opc1f.isChecked() == false && opc2f.isChecked() == false && opc3f.isChecked() == false && opc4f.isChecked() == false){
            Toast.makeText(getApplicationContext(), "Elija una opcion", Toast.LENGTH_SHORT).show();
        }else if(Ntitulo_f == 1){

            if (opc1f.isChecked()){
                resultado_visualiza= resultado_visualiza+1;
            }
            if (opc2f.isChecked()){
                resultado_visualiza= resultado_visualiza+2;
            }
            if (opc3f.isChecked()){
                resultado_visualiza= resultado_visualiza+3;
            }
            if (opc4f.isChecked()){
                resultado_visualiza= resultado_visualiza+4;
            }

            Ntitulo_f = 1 + Ntitulo_f;
            //Reinicio de prguntas
            txttitulo_f.setText("Agorafobia");


            img1.setImageResource(R.drawable.agorafobia);
            img2.setImageResource(R.drawable.agorafobia1);
            img3.setImageResource(R.drawable.agorafobia2);

            //Se reinician las opciones
        }else if(Ntitulo_f == 2){

                if (opc1f.isChecked()){
                    resultado_visualiza= resultado_visualiza+1;
                }
                if (opc2f.isChecked()){
                    resultado_visualiza= resultado_visualiza+2;
                }
                if (opc3f.isChecked()){
                    resultado_visualiza= resultado_visualiza+3;
                }
                if (opc4f.isChecked()){
                    resultado_visualiza= resultado_visualiza+4;
                }

                Ntitulo_f = 1 + Ntitulo_f;
                //Reinicio de prguntas
                txttitulo_f.setText("Claustrofobia");

                img1.setImageResource(R.drawable.claustrofobia1);
                img2.setImageResource(R.drawable.claustrofobia2);
                img3.setImageResource(R.drawable.claustrofobia3);


                //Se reinician las opciones
            }else if(Ntitulo_f == 3){

                    if (opc1f.isChecked()){
                        resultado_visualiza= resultado_visualiza+1;
                    }
                    if (opc2f.isChecked()){
                        resultado_visualiza= resultado_visualiza+2;
                    }
                    if (opc3f.isChecked()){
                        resultado_visualiza= resultado_visualiza+3;
                    }
                    if (opc4f.isChecked()){
                        resultado_visualiza= resultado_visualiza+4;
                    }

                    Ntitulo_f = 1 + Ntitulo_f;
                    //Reinicio de prguntas
                    txttitulo_f.setText("Fobia social");

                    img1.setImageResource(R.drawable.fobia_social3);
                    img2.setImageResource(R.drawable.fobia_social2);
                    img3.setImageResource(R.drawable.fobiasocial3);


                    //Se reinician las opciones
                }else if(Ntitulo_f == 4){
                    if (opc1f.isChecked()){
                        resultado_visualiza= resultado_visualiza+1;
                    }
                    if (opc2f.isChecked()){
                        resultado_visualiza= resultado_visualiza+2;
                    }
                    if (opc3f.isChecked()){
                        resultado_visualiza= resultado_visualiza+3;
                    }
                    if (opc4f.isChecked()){
                        resultado_visualiza= resultado_visualiza+4;
                    }

                    GuardarDatosBD();
                    img1.setVisibility(View.GONE);
                    img2.setVisibility(View.GONE);
                    img3.setVisibility(View.GONE);
                    txttitulo_f.setText("Prueba vizualiza terminada.");
                    siguiente_f.setVisibility(View.GONE);
                    opc1f.setVisibility(View.GONE);
                    opc2f.setVisibility(View.GONE);
                    opc3f.setVisibility(View.GONE);
                    opc4f.setVisibility(View.GONE);

        }
    }
    public void salir_p(View view){
        finish();
    }
    private void GuardarDatosBD(){
        if (!isNetworkAvailable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "No se pueden guardar los datos.", Toast.LENGTH_SHORT).show();
            return;
        }else {
            //Actualizar base de datos
            String puntuacionPaciente_visualiza = Integer.toString(resultado_visualiza);

            Map<String, Object> map = new HashMap<>();
            map.put("puntaje_vsual", puntuacionPaciente_visualiza);
            db.collection("reg_paciente").document(IDPacietne).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // La actualización se realizó con éxito
                            Toast.makeText(getApplicationContext(), "Respuestas guardadas", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // La actualización falló
                            // Aquí puedes mostrar un aviso o realizar acciones de manejo de errores
                            Toast.makeText(getApplicationContext(), "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}

