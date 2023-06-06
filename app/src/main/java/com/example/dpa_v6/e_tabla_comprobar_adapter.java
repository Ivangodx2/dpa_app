package com.example.dpa_v6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Timer;
import java.util.TimerTask;

public class e_tabla_comprobar_adapter  extends FirestoreRecyclerAdapter<e_tabla_pacientes,e_tabla_comprobar_adapter.ViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public e_tabla_comprobar_adapter(@NonNull FirestoreRecyclerOptions<e_tabla_pacientes> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull e_tabla_pacientes model) {


        holder.txv_nombre_e.setText(model.getNombre());
        int V_ansiedad = Integer.parseInt(model.getPorcentaje_A());
        System.out.println(V_ansiedad);


        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                int counta_PB = V_ansiedad;
                counta_PB++;
                holder.progressBar_e.setProgress(counta_PB);

                if (counta_PB == V_ansiedad){
                    timer.cancel();
                }

            }
        };
        timer.schedule(timerTask,0,100);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_especialista_comprpacientes,parent,false);

        return new e_tabla_comprobar_adapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txv_nombre_e;
        ProgressBar progressBar_e;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txv_nombre_e = itemView.findViewById(R.id.P_nombre_e);
            progressBar_e = itemView.findViewById(R.id.progressBar_ansiedad_com_e);
        }


    }


}


