package com.example.salus.adaptador;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salus.ConfirmarTurnoActivity;
import com.example.salus.ProfesionalActivity;
import com.example.salus.R;
import com.example.salus.entidad.TurnoDisponible;

import java.util.List;

public class TurnosDisponiblesAdapter extends RecyclerView.Adapter<TurnosDisponiblesAdapter.ViewHolder> {
    private List<TurnoDisponible> turnos;
    private Context context;

    public TurnosDisponiblesAdapter(List<TurnoDisponible> turnos, Context context) {
        this.turnos = turnos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_turno_disponible, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TurnoDisponible turno = turnos.get(position);
        holder.tvDia.setText(turno.getDia().toString());
        holder.tvHora.setText(turno.getHora().toString());

        holder.btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementar la lógica para reservar turno
                reservarTurno(turno);
            }
        });
    }

    private void reservarTurno(TurnoDisponible turno) {
        // Aquí puedes implementar la lógica para reservar el turno
        Toast.makeText(context, "Reservando turno: " + turno.getDia() + " " + turno.getHora(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, ConfirmarTurnoActivity.class);
        intent.putExtra("dia", turno.getDia());
        intent.putExtra("hora", turno.getHora());
        intent.putExtra("idTurnoDisponible", turno.getId());
        Log.d("TurnoDisponible", turno.toString());

        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return turnos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDia;
        public TextView tvHora;
        public Button btnReservar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDia = itemView.findViewById(R.id.tvDia);
            tvHora = itemView.findViewById(R.id.tvHora);
            btnReservar = itemView.findViewById(R.id.btnReservar);
        }
    }
}

