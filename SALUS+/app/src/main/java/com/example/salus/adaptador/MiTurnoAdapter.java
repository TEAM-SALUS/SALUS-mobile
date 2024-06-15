package com.example.salus.adaptador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salus.ApiDjango;
import com.example.salus.EditarTurnoActivity;
import com.example.salus.R;
import com.example.salus.dao.URLConection;
import com.example.salus.entidad.MiTurno;
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
        TextView tvTurnoDetalle;
        Button btnEditar, btnEliminar;

        public MiTurnoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTurnoDetalle = itemView.findViewById(R.id.tvTurnoDetalle);
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
            tvTurnoDetalle.setText(miTurno.toString());
        }
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
