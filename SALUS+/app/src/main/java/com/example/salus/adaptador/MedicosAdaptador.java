package com.example.salus.adaptador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
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
import com.example.salus.MedicoActivity;
import com.example.salus.R;
import com.example.salus.entidad.Especialidad;
import com.example.salus.entidad.Medico;
import com.example.salus.io.SalusBDApiAdapter;
import com.example.salus.io.URLConection;
import com.example.salus.login;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicosAdaptador extends RecyclerView.Adapter<MedicosAdaptador.ViewHolder> {
    private List<Medico> medicosLista;
    private Context context;

    public MedicosAdaptador(List<Medico> medicosLista, Context context) {
        this.medicosLista = medicosLista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_medicos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicosAdaptador.ViewHolder holder, int position) {
        Glide.with(context).load(URLConection.URLPrivadaIMG + medicosLista.get(position).getFoto()).into(holder.imgMedicos);
        holder.txtMatriculaMedicos.setText(medicosLista.get(position).getMatricula());
        holder.txtNombreMedicos.setText(medicosLista.get(position).getNombre());
        holder.txtApellidoMedicos.setText(medicosLista.get(position).getApellido());
        holder.txtIdEspecialidadMedicos.setText(medicosLista.get(position).getId_especialidad().toString());

        this.obtenerEspecialidad(medicosLista.get(position).getId_especialidad(), holder.txtIdEspecialidadMedicos);
        holder.btnMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MedicoActivity.class);
                Toast.makeText(context, "Selecciono a: " + medicosLista.get(position).getNombre(), Toast.LENGTH_LONG).show();
                intent.putExtra("medicoSeleccionado", medicosLista.get(position).getId());
                //context.startActivity(intent);
                context.startActivity(Intent.createChooser(intent, "Compartir en").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }


    @Override
    public int getItemCount() {
        return medicosLista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMedicos;
        private TextView txtMatriculaMedicos;
        private TextView txtNombreMedicos;
        private TextView txtApellidoMedicos;
        private TextView txtIdEspecialidadMedicos;
        private Button btnMedico;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMedicos = itemView.findViewById(R.id.imgMedicos);
            txtMatriculaMedicos = itemView.findViewById(R.id.txtMatriculaMedicos);
            txtNombreMedicos = itemView.findViewById(R.id.txtNombreMedicos);
            txtApellidoMedicos = itemView.findViewById(R.id.txtApellidoMedicos);
            txtIdEspecialidadMedicos = itemView.findViewById(R.id.txtIdEspecialidadMedicos);
            btnMedico = itemView.findViewById(R.id.btnMedico);
        }
    }

    private void obtenerEspecialidad(Long idEspecialidad, TextView txtIdEspecialidadMedicos) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(login.SHARED_PREFS, context.MODE_PRIVATE);
        String token = sharedPreferences.getString(login.TOKEN_KEY, null);
        Call<Especialidad> call = SalusBDApiAdapter.getApiService().getEspecialidadId("Token " + token, idEspecialidad);
        call.enqueue(new Callback<Especialidad>() {
            @Override
            public void onResponse(Call<Especialidad> call, Response<Especialidad> response) {
                if (response.isSuccessful()) {
                    Especialidad especialidad = response.body();
                    Log.d("especialidad: ", "especialidad recuperada " + especialidad.toString());
                    Log.d("token", "especialidad token " + token);
                    txtIdEspecialidadMedicos.setText(especialidad.getNombre());

                } else {
                    Log.d("error especialidad", "especialidad no recuperada");
                    Toast.makeText(context, "Algo salio mal o Error en las credenciales", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Especialidad> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error request", t.toString());
            }
        });
    }
}
