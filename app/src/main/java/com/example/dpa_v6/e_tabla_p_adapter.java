package com.example.dpa_v6;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class e_tabla_p_adapter extends FirestoreRecyclerAdapter<e_tabla_pacientes,e_tabla_p_adapter.ViewHolder> {

    Activity activity;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public e_tabla_p_adapter(@NonNull FirestoreRecyclerOptions<e_tabla_pacientes> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull e_tabla_p_adapter.ViewHolder holder, int position, @NonNull e_tabla_pacientes model) {

        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());

        final String id = documentSnapshot.getId();
        holder.txv_nombre.setText(model.getNombre());
        holder.txv_edad.setText(model.getEdad());
        holder.txv_notel.setText(model.getnum_telefonico());

        holder.btn_verpac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,especialista_ver_paciente.class );
                intent.putExtra("Idpaciente",id);
                activity.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_especialista_lpacientes,parent,false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txv_nombre;
        TextView txv_edad;
        TextView txv_notel;
        Button btn_verpac;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txv_nombre = itemView.findViewById(R.id.P_nombre);
            txv_edad = itemView.findViewById(R.id.P_edad);
            txv_notel = itemView.findViewById(R.id.P_notelefonico);
            btn_verpac = itemView.findViewById(R.id.btn_verpaciente);
        }


    }
}
