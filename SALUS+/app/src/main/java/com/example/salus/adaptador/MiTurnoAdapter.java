package com.example.salus.adaptador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salus.ApiClient;
import com.example.salus.ApiDjango;
import com.example.salus.EditarTurnoActivity;
import com.example.salus.R;
import com.example.salus.dao.URLConection;
import com.example.salus.entidad.Especialidad;
import com.example.salus.entidad.Medico;
import com.example.salus.entidad.MiTurno;
import com.example.salus.entidad.Turno;
import com.example.salus.entidad.TurnoDisponible;
import com.example.salus.login;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MiTurnoAdapter extends RecyclerView.Adapter<MiTurnoAdapter.MiTurnoViewHolder> {
    private List<MiTurno> miTurnoList;
    private Context context;
    //private static String msn;

    public MiTurnoAdapter(List<MiTurno> miTurnoList, Context context) {
        this.miTurnoList = miTurnoList;
        this.context = context;
    }

    @NonNull
    @Override
    public MiTurnoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detalle_turno, parent, false);
        return new MiTurnoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiTurnoViewHolder holder, int position) {
        MiTurno miTurno = miTurnoList.get(position);
        holder.bind(miTurno);
    }

    @Override
    public int getItemCount() {
        return miTurnoList.size();
    }

    public class MiTurnoViewHolder extends RecyclerView.ViewHolder {
        TextView tvEspecialidadTurno;
        TextView tvFechaTurno;
        TextView tvHoraTurno;
        TextView tvMedicoTurno;
        TextView tvObraSocialTurno;
        Button btnEditar, btnEliminar;

        public MiTurnoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEspecialidadTurno = itemView.findViewById(R.id.tvEspecialidadTurno);
            tvFechaTurno = itemView.findViewById(R.id.tvFechaTurno);
            tvHoraTurno = itemView.findViewById(R.id.tvHoraTurno);
            tvMedicoTurno = itemView.findViewById(R.id.tvMedicoTurno);
            tvObraSocialTurno = itemView.findViewById(R.id.tvObraSocialTurno);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);

            btnEditar.setOnClickListener(v -> {
                MiTurno miTurno = miTurnoList.get(getAdapterPosition());
                Intent intent = new Intent(context, EditarTurnoActivity.class);
                intent.putExtra("miTurno", miTurno);
                context.startActivity(intent);
            });

            btnEliminar.setOnClickListener(v -> {
                MiTurno miTurno = miTurnoList.get(getAdapterPosition());
                eliminarTurno(miTurno.getId(), getAdapterPosition());
            });
        }

        public void bind(MiTurno miTurno) {
            if (miTurno != null) {
                tvObraSocialTurno.setText("Obra Social: "+miTurno.getObra_social());
                //msn += "Estado: " + miTurno.getEstado() + "\n";
                obtenerTurnoDisponible(miTurno, tvEspecialidadTurno, tvFechaTurno, tvHoraTurno, tvMedicoTurno);
            }


        }
    }

    private void probandoApi() {
        Call<List<Turno>> call = ApiClient.getClient().getTurnos();
        call.enqueue(new Callback<List<Turno>>() {
            @Override
            public void onResponse(Call<List<Turno>> call, Response<List<Turno>> response) {
                if (response.isSuccessful()) {
                    List<Turno> lt = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<Turno>> call, Throwable t) {

            }
        });
    }

    private void obtenerTurnoDisponible(MiTurno miTurno, TextView tvEspecialidadTurno, TextView tvFechaTurno, TextView tvHoraTurno, TextView tvMedico) {
        Call<TurnoDisponible> call = ApiClient.getClient().getTurnosDisponiblesPorId(miTurno.getTurno_disponible());
        call.enqueue(new Callback<TurnoDisponible>() {
            @Override
            public void onResponse(Call<TurnoDisponible> call, Response<TurnoDisponible> response) {
                if (response.isSuccessful()) {
                    TurnoDisponible td = response.body();
                    if (td != null) {
                        //msn += "Dia: " + td.getDia() + "\n";
                        //msn += "Hora: " + td.getHora() + "\n";
                        tvFechaTurno.setText("Fecha: "+td.getDia());
                        tvHoraTurno.setText("Hora: "+td.getHora());
                        obtenerMedico(miTurno, tvMedico, tvEspecialidadTurno);
                    }
                } else {
                    Toast.makeText(context, "Turno disponible no obtenido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TurnoDisponible> call, Throwable t) {
                Toast.makeText(context, "Error al obtener turno disponible", Toast.LENGTH_SHORT).show();
                Log.e("Error al obtener turno", t.toString());
            }
        });
        //msn += "$2";
    }

    private void obtenerMedico(MiTurno miTurno, TextView tvMedico, TextView tvEspecialidadTurno) {
        Call<Medico> call = ApiClient.getClient().getMedicoId(miTurno.getId_medico());
        call.enqueue(new Callback<Medico>() {
            @Override
            public void onResponse(Call<Medico> call, Response<Medico> response) {
                if (response.isSuccessful()) {
                    Medico m = response.body();
                    if (m != null) {
                        //msn += "Matricula: " + m.getMatricula() + "\n";
                        //msn += "Medico: " + m.getApellido() + "\n";
                        tvMedico.setText("Profesional: "+m.getApellido());
                        obtenerEspecialidad(m, tvEspecialidadTurno);
                    }
                } else {
                    Toast.makeText(context, "Medico no obtenido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Medico> call, Throwable t) {
                Toast.makeText(context, "Error al obtener medico", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void obtenerEspecialidad(Medico medico, TextView tvEspecialidadTurno) {
        Call<Especialidad> call = ApiClient.getClient().getEspecialidadId(medico.getId_especialidad());
        call.enqueue(new Callback<Especialidad>() {
            @Override
            public void onResponse(Call<Especialidad> call, Response<Especialidad> response) {
                if (response.isSuccessful()) {
                    Especialidad e = response.body();
                    if (e != null) {
                        //msn += "Matricula: " + m.getMatricula() + "\n";
                        //msn += "Medico: " + m.getApellido() + "\n";
                        tvEspecialidadTurno.setText(e.getNombre());
                    }
                } else {
                    Toast.makeText(context, "Especialidad no obtenida", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Especialidad> call, Throwable t) {
                Toast.makeText(context, "Error al obtener especialidad", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void eliminarTurno(int turnoId, int position) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLConection.URLPrivada)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiDjango apiDjango = retrofit.create(ApiDjango.class);
        SharedPreferences sharedPreferences = context.getSharedPreferences(login.SHARED_PREFS, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(login.TOKEN_KEY, null);

        Call<Void> call = apiDjango.eliminarMiTurnoReservado("Token " + token, turnoId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Si la eliminación fue exitosa, actualizar la lista y notificar al adaptador
                    miTurnoList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, miTurnoList.size());
                    Toast.makeText(context, "Turno eliminado con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    // Si hubo un error en la eliminación, mostrar mensaje de error
                    Toast.makeText(context, "Error al eliminar turno", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Si hubo un fallo en la conexión, mostrar mensaje de error
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
