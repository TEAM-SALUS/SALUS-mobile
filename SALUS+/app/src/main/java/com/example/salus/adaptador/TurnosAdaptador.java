package com.example.salus.adaptador;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salus.ApiDjango;
import com.example.salus.R;
import com.example.salus.entidad.Turno;
import com.example.salus.io.SalusBDApiAdapter;
import com.example.salus.login;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TurnosAdaptador extends RecyclerView.Adapter<TurnosAdaptador.ViewHolder> {
    private List<Turno> turnoLista;
    private Context context;

    public TurnosAdaptador(List<Turno> turnoLista, Context context) {
        this.turnoLista = turnoLista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_turno, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Turno turno = turnoLista.get(position);
        holder.tv_fecha.setText(String.valueOf(turno.getFecha()));
        holder.tv_hora.setText(String.valueOf(turno.getHorario()));

        // Alerta para la cancelación del turno
        holder.btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(holder.itemView.getContext());
                alerta.setTitle("Confirmar para cancelar el turno")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Llamar a la función para eliminar el turno
                                eliminarTurno(turno.getId(), position);
                                Toast.makeText(holder.itemView.getContext(), "Turno cancelado", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alerta.create();
                alertDialog.show();
            }
        });
    }

    private void eliminarTurno(Long id, int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(login.SHARED_PREFS, context.MODE_PRIVATE);
        String token = sharedPreferences.getString(login.TOKEN_KEY, null);
        Call<Void> callEliminar = SalusBDApiAdapter.getApiService().eliminarTurnoId("Token " + token,id);
        callEliminar.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    turnoLista.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Turno eliminado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al eliminar el turno", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error en la conexión", Toast.LENGTH_SHORT).show();
            };
        });
    };

    // Eliminar el item del turno
    public void eliminarItem(int position) {
        turnoLista.remove(position);
        notifyItemRemoved(position);
    };



    @Override
    public int getItemCount() {
        return turnoLista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_hora, tv_fecha;
        private Button btn_cancelar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_fecha = itemView.findViewById(R.id.tv_fecha);
            tv_hora = itemView.findViewById(R.id.tv_hora);
            btn_cancelar = itemView.findViewById(R.id.btn_cancelar);
        }
    }
}
