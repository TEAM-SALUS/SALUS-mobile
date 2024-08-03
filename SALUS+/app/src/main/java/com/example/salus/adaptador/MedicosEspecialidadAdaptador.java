package com.example.salus.adaptador;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.salus.MedicoActivity;
import com.example.salus.PagoActivity;
import com.example.salus.R;
import com.example.salus.entidad.Especialidad;
import com.example.salus.entidad.HorarioDeAtencion;
import com.example.salus.entidad.Medico;
import com.example.salus.enums.EDiaSemana;
import com.example.salus.io.SalusBDApiAdapter;
import com.example.salus.io.URLConection;
import com.example.salus.login;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicosEspecialidadAdaptador extends RecyclerView.Adapter<MedicosEspecialidadAdaptador.ViewHolder> {
    private List<Medico> medicosLista;
    private Context context;

    public MedicosEspecialidadAdaptador(List<Medico> medicosLista, Context context) {
        this.medicosLista = medicosLista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_medicos_especialidad, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicosEspecialidadAdaptador.ViewHolder holder, int position) {
        Glide.with(context).load(URLConection.URLPrivadaIMG + medicosLista.get(position).getFoto()).into(holder.imgMedicosEspecialidad);
        holder.txtMatriculaMedicosEspecialidad.setText(medicosLista.get(position).getMatricula());
        holder.txtNombreMedicosEspecialidad.setText(medicosLista.get(position).getNombre());
        holder.txtApellidoMedicosEspecialidad.setText(medicosLista.get(position).getApellido());
        holder.txtIdEspecialidadMedicosEspecialidad.setText(medicosLista.get(position).getId_especialidad().toString());
        holder.txtHorarioMedicoEspecialidad.setText(medicosLista.get(position).getId_horario().toString());

        this.obtenerHorarioMedico(medicosLista.get(position).getId_horario(), holder.txtHorarioMedicoEspecialidad);
        this.obtenerEspecialidad(medicosLista.get(position).getId_especialidad(), holder.txtIdEspecialidadMedicosEspecialidad);

        holder.imgMedicosEspecialidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MedicoActivity.class);
                Toast.makeText(context, "Selecciono a: " + medicosLista.get(position).getNombre(), Toast.LENGTH_LONG).show();
                intent.putExtra("medicoSeleccionado", medicosLista.get(position).getId());
                //context.startActivity(intent);
                context.startActivity(Intent.createChooser(intent, "Compartir en").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        holder.btnMedicoEspecialidadReservarTurno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PagoActivity.class);
                Toast.makeText(context, "Ir a pagar: " + medicosLista.get(position).getNombre(), Toast.LENGTH_LONG).show();
                intent.putExtra("fecha", holder.in_date.getText().toString());
                intent.putExtra("hora", holder.in_time.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putSerializable("medico", medicosLista.get(position));
                intent.putExtras(bundle);
                intent.putExtra("idMedico", medicosLista.get(position).getId());
                intent.putExtra("especialidad", holder.txtIdEspecialidadMedicosEspecialidad.getText().toString());
                context.startActivity(intent);
            }
        });
        //int mYear, mMonth, mDay, mHour, mMinute;

        /**
         * fecha y hora
         * <a hreff="https://www.digitalocean.com/community/tutorials/android-date-time-picker-dialog">tuto google</a>
         * */
        holder.btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay;
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                holder.in_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        holder.btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mHour, mMinute;
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                holder.in_time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicosLista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMedicosEspecialidad;
        private TextView txtMatriculaMedicosEspecialidad;
        private TextView txtNombreMedicosEspecialidad;
        private TextView txtApellidoMedicosEspecialidad;
        private TextView txtIdEspecialidadMedicosEspecialidad;
        private TextView txtHorarioMedicoEspecialidad;
        private Button btnMedicoEspecialidadReservarTurno;
        private Button btn_date, btn_time;
        private EditText in_date, in_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMedicosEspecialidad = itemView.findViewById(R.id.imgMedicosEspecialidad);
            txtMatriculaMedicosEspecialidad = itemView.findViewById(R.id.txtMatriculaMedicosEspecialidad);
            txtNombreMedicosEspecialidad = itemView.findViewById(R.id.txtNombreMedicosEspecialidad);
            txtApellidoMedicosEspecialidad = itemView.findViewById(R.id.txtApellidoMedicosEspecialidad);
            txtIdEspecialidadMedicosEspecialidad = itemView.findViewById(R.id.txtIdEspecialidadMedicosEspecialidad);
            txtHorarioMedicoEspecialidad = itemView.findViewById(R.id.txtHorarioMedicoEspecialidad);
            btnMedicoEspecialidadReservarTurno = itemView.findViewById(R.id.btnMedicoEspecialidadReservarTurno);
            btn_date = itemView.findViewById(R.id.btn_date);
            btn_time = itemView.findViewById(R.id.btn_time);
            in_date = itemView.findViewById(R.id.in_date);
            in_time = itemView.findViewById(R.id.in_time);
        }
    }

    private void obtenerEspecialidad(Long idEspecialidad, TextView txtIdEspecialidadMedicosEspecialidad) {
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
                    String txtEspecialidad = "";
                    txtEspecialidad = especialidad.getId() + "-" + especialidad.getNombre() + "-" + especialidad.getPrecio();
                    txtIdEspecialidadMedicosEspecialidad.setText(txtEspecialidad);

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

    private void obtenerHorarioMedico(Long idHorario, TextView txtHorarioMedicoEspecialidad) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(login.SHARED_PREFS, context.MODE_PRIVATE);
        String token = sharedPreferences.getString(login.TOKEN_KEY, null);

        Call<HorarioDeAtencion> call = SalusBDApiAdapter.getApiService().getHorarioDeAtencionId("Token " + token, idHorario);
        call.enqueue(new Callback<HorarioDeAtencion>() {
            @Override
            public void onResponse(Call<HorarioDeAtencion> call, Response<HorarioDeAtencion> response) {
                HorarioDeAtencion horarioDeAtencion = response.body();
                Log.d("horario", "hora recuperada " + horarioDeAtencion.toString());
                Log.d("horario", "hora recuperada " + horarioDeAtencion.getDia_de_la_semana().split(";")[0]);
                String dias = "";
                for (String d : horarioDeAtencion.getDia_de_la_semana().split(";")) {
                    dias += EDiaSemana.getByIndex(Integer.parseInt(d)) + ", ";
                    Log.d("horario", String.valueOf(EDiaSemana.getByIndex(Integer.parseInt(d))));
                }
                txtHorarioMedicoEspecialidad.setText(dias + " " + horarioDeAtencion.getHora_entrada() + "-" + horarioDeAtencion.getHora_salida());
            }

            @Override
            public void onFailure(Call<HorarioDeAtencion> call, Throwable t) {
                Toast.makeText(context, "Algo salio mal o Error en las credenciales", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
