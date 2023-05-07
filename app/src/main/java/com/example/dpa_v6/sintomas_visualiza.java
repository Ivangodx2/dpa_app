package com.example.dpa_v6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dpa_v6.Grid_imgs_adapter.Imagenes_visualizar;

public class sintomas_visualiza extends AppCompatActivity {

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    RadioButton opc1f, opc2f, opc3f, opc4f;
    TextView ntitulo_f, txttitulo_f;
    Button siguiente_f, btnsalir_f;

    int resultado_encu=0;
    int Ntitulo_f=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas_visualiza);
        img1 = (ImageView) findViewById(R.id.imageViewF1);
        img2 = (ImageView) findViewById(R.id.imageViewF2);
        img3 = (ImageView) findViewById(R.id.imageViewF3);

        opc1f = (RadioButton) findViewById(R.id.radioButton);
        opc2f = (RadioButton) findViewById(R.id.radioButton2);
        opc3f = (RadioButton) findViewById(R.id.radioButton3);
        opc4f = (RadioButton) findViewById(R.id.radioButton4);

        ntitulo_f = (TextView) findViewById(R.id.titulo);
        txttitulo_f=(TextView) findViewById(R.id.sintoma_titulo);

        siguiente_f=(Button) findViewById(R.id.siguiente_fobia);
        btnsalir_f=(Button) findViewById(R.id.salir_fobia);


    }

    public void mueve_click(View view) {
        if (opc1f.isChecked() == false && opc2f.isChecked() == false && opc3f.isChecked() == false && opc4f.isChecked() == false){
            Toast.makeText(getApplicationContext(), "Elija una opcion", Toast.LENGTH_SHORT).show();
        }else if(Ntitulo_f == 1){
            if (opc1f.isChecked()){
                resultado_encu= resultado_encu+1;
            }
            if (opc2f.isChecked()){
                resultado_encu= resultado_encu+2;
            }
            if (opc3f.isChecked()){
                resultado_encu= resultado_encu+3;
            }
            if (opc4f.isChecked()){
                resultado_encu= resultado_encu+4;
            }
            opc1f.setChecked(false);
            opc2f.setChecked(false);
            opc3f.setChecked(false);
            opc4f.setChecked(false);

            Ntitulo_f = 1 + Ntitulo_f;
            //Reinicio de prguntas
            ntitulo_f.setText("Prgunta 2");
            txttitulo_f.setText("Agorafobia");

            img1.setImageResource(R.drawable.agorafobia);
            img2.setImageResource(R.drawable.agorafobia1);
            img3.setImageResource(R.drawable.agorafobia2);


            //Se reinician las opciones
        }else if(Ntitulo_f == 2){

                if (opc1f.isChecked()){
                    resultado_encu= resultado_encu+1;
                }
                if (opc2f.isChecked()){
                    resultado_encu= resultado_encu+2;
                }
                if (opc3f.isChecked()){
                    resultado_encu= resultado_encu+3;
                }
                if (opc4f.isChecked()){
                    resultado_encu= resultado_encu+4;
                }
                opc1f.setChecked(false);
                opc2f.setChecked(false);
                opc3f.setChecked(false);
                opc4f.setChecked(false);

                Ntitulo_f = 1 + Ntitulo_f;
                //Reinicio de prguntas
                ntitulo_f.setText("Prgunta 3");
                txttitulo_f.setText("Claustrofobia");

                img1.setImageResource(R.drawable.claustrofobia1);
                img2.setImageResource(R.drawable.claustrofobia2);
                img3.setImageResource(R.drawable.claustrofobia3);


                //Se reinician las opciones
            }else if(Ntitulo_f == 3){

                    if (opc1f.isChecked()){
                        resultado_encu= resultado_encu+1;
                    }
                    if (opc2f.isChecked()){
                        resultado_encu= resultado_encu+2;
                    }
                    if (opc3f.isChecked()){
                        resultado_encu= resultado_encu+3;
                    }
                    if (opc4f.isChecked()){
                        resultado_encu= resultado_encu+4;
                    }
                    opc1f.setChecked(false);
                    opc2f.setChecked(false);
                    opc3f.setChecked(false);
                    opc4f.setChecked(false);

                    Ntitulo_f = 1 + Ntitulo_f;
                    //Reinicio de prguntas
                    ntitulo_f.setText("Prgunta 4");
                    txttitulo_f.setText("Fobia social");

                    img1.setImageResource(R.drawable.fobia_social);
                    img2.setImageResource(R.drawable.fobia_social2);
                    img3.setImageResource(R.drawable.fobiasocial3);


                    //Se reinician las opciones
                }else if(Ntitulo_f == 4){
                    if (opc1f.isChecked()){
                        resultado_encu= resultado_encu+1;
                    }
                    if (opc2f.isChecked()){
                        resultado_encu= resultado_encu+2;
                    }
                    if (opc3f.isChecked()){
                        resultado_encu= resultado_encu+3;
                    }
                    if (opc4f.isChecked()){
                        resultado_encu= resultado_encu+4;
                    }

                    img1.setVisibility(View.GONE);
                    img2.setVisibility(View.GONE);
                    img3.setVisibility(View.GONE);
                    txttitulo_f.setText("nota obtenida "+resultado_encu);
                    siguiente_f.setVisibility(View.GONE);
                    opc1f.setVisibility(View.GONE);
                    opc2f.setVisibility(View.GONE);
                    opc3f.setVisibility(View.GONE);
                    opc4f.setVisibility(View.GONE);
        }
    }

    public void salir_v(View view){
        finish();
    }
}

