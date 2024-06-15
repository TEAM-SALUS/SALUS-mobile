package com.example.salus.adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.salus.ConsultaActivity;
import com.example.salus.EspecialidadesActivity;
import com.example.salus.ProfesionalActivity;
import com.example.salus.R;
import com.example.salus.entidad.Especialidad;

import java.util.List;

public class EspecialidadesAdapter extends RecyclerView.Adapter<EspecialidadesAdapter.ViewHolder> {
    private List<Especialidad> especialidades;

    private Context context;

    public EspecialidadesAdapter(List<Especialidad> especialidades, Context context) {
        this.especialidades = especialidades;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_especialidad, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Especialidad especialidad = especialidades.get(position);
        holder.tvNombre.setText(especialidad.getNombre());

        // Convertir el precio a cadena y mostrarlo
        holder.tvPrecio.setText(String.valueOf(especialidad.getPrecio()));

        // Mostrar la duración como cadena
        holder.tvDuracion.setText(especialidad.getDuracion());

        holder.tvDescripcion.setText(especialidad.getDescripcion());

        // Cargar la imagen con Glide
        Glide.with(holder.itemView.getContext())
                .load(especialidad.getFoto())
                .into(holder.ivFoto);

        holder.btnProfesionales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfesionalActivity.class);
                intent.putExtra("especialidadId", especialidad.getId()); // Verifica que especialidad.getId() retorne el ID correcto
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return especialidades.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvPrecio, tvDuracion, tvDescripcion;
        ImageView ivFoto;
        Button btnProfesionales;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvDuracion = itemView.findViewById(R.id.tvDuracion);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            btnProfesionales = itemView.findViewById(R.id.btnProfesionales);
        }
    }

}

