/*package com.example.salus.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salus.R;
import com.example.salus.entidad.Pago;

import java.util.List;

public class PagoAdaptador extends RecyclerView.Adapter<PagoAdaptador.PagoViewHolder> {
    private List<Pago> pagosLista;
    private Context context;

    public PagoAdaptador(List<Pago> pagosLista, Context context) {
        this.pagosLista = pagosLista;
        this.context = context;
    }

    @NonNull
    @Override
    public PagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pago, parent, false);
        return new PagoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoViewHolder holder, int position) {
        Pago pago = pagosLista.get(position);
        holder.monto.setText(String.valueOf(pago.getMonto()));
        holder.fecha.setText(pago.getFecha());
        holder.hora.setText(pago.getHora());
        holder.estado.setText(pago.getEstado());
        holder.idTurno.setText(String.valueOf(pago.getIdTurno()));
    }

    @Override
    public int getItemCount() {
        return pagosLista.size();
    }

    public static class PagoViewHolder extends RecyclerView.ViewHolder {
        TextView monto, fecha, hora, estado, idTurno;

        public PagoViewHolder(@NonNull View itemView) {
            super(itemView);
            monto = itemView.findViewById(R.id.monto);
            fecha = itemView.findViewById(R.id.fecha);
            hora = itemView.findViewById(R.id.hora);
            estado = itemView.findViewById(R.id.estado);
            idTurno = itemView.findViewById(R.id.id_turno);
        }
    }
}
*/