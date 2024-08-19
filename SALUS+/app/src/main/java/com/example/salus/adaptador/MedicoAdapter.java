package com.example.salus.adaptador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.salus.R;
import com.example.salus.TurnosDisponiblesActivity;
import com.example.salus.entidad.HorarioDeAtencion;
import com.example.salus.entidad.Medico;

import java.util.List;

public class MedicoAdapter extends RecyclerView.Adapter<MedicoAdapter.ViewHolder> {
    private List<Medico> medicos;
    private Context context;

    public MedicoAdapter(List<Medico> medicos, Context context) {
        this.medicos = medicos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medico, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medico medico = medicos.get(position);
        holder.tvNombre.setText(medico.getNombre() + " " + medico.getApellido());
        holder.tvEmail.setText(medico.getEmail());

        // Cargar la imagen del médico con Glide
        Glide.with(holder.itemView.getContext())
                .load(medico.getFoto())
                .into(holder.ivFoto);

        // Mostrar los horarios de atención
        if (medico.getHorariosDeAtencion() != null && !medico.getHorariosDeAtencion().isEmpty()) {
            StringBuilder horariosStr = new StringBuilder();
            for (HorarioDeAtencion horario : medico.getHorariosDeAtencion()) {
                horariosStr.append(horario.getDiaDeLaSemana())
                        .append(": ")
                        .append(horario.getHoraEntrada())
                        .append(" - ")
                        .append(horario.getHoraSalida())
                        .append("\n");
            }
            holder.tvHorario.setText(horariosStr.toString());
        } else {
            holder.tvHorario.setText("Horarios no disponibles");
        }

        // Configurar el botón para ver turnos disponibles
        holder.btnTurnosDisponibles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

                // Guardar el idMedico en SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("idMedico", medico.getId());
                editor.apply();

                // Intent para ver turnos disponibles
                Intent intent = new Intent(context, TurnosDisponiblesActivity.class);
                intent.putExtra("medicoID", medico.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNombre;
        public TextView tvEmail;
        public TextView tvHorario;
        public ImageView ivFoto;
        public Button btnTurnosDisponibles;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvHorario = itemView.findViewById(R.id.tvHorario);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            btnTurnosDisponibles = itemView.findViewById(R.id.btnTurnosDisponibles);
        }
    }
}





/*package com.example.salus.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.salus.R;
import com.example.salus.entidad.Medico;

import java.util.List;

public class MedicoAdapter extends RecyclerView.Adapter<MedicoAdapter.ViewHolder> {
    private List<Medico> medicos;

    public MedicoAdapter (List<Medico> medicos) {
        this.medicos = medicos;
    }

    @NonNull
    @Override
    public MedicoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_especialidad, parent, false);
        return new MedicoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicoAdapter.ViewHolder holder, int position) {
        Medico medico = medicos.get(position);

        holder.mNombre.setText(medico.getNombre());

        holder.mApellido.setText(medico.getApellido());

        // Convertir el precio a cadena y mostrarlo
        holder.mEspecialidad.setText(String.valueOf(medico.getId_especialidad()));


        // Cargar la imagen con Glide
        Glide.with(holder.itemView.getContext())
                .load(medico.getFoto())
                .into(holder.ivFoto);
    }

    @Override
    public int getItemCount() {
        return medicos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mNombre, mApellido, mEspecialidad;
        ImageView ivFoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mNombre = itemView.findViewById(R.id.prTitulo);
            mEspecialidad = itemView.findViewById(R.id.prEspecialidad);
            ivFoto = itemView.findViewById(R.id.ivFoto);
        }
    }
}*/



