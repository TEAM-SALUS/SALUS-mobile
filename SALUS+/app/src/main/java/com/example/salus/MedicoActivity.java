package com.example.salus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.salus.entidad.Especialidad;
import com.example.salus.entidad.HorarioDeAtencion;
import com.example.salus.entidad.Medico;
import com.example.salus.enums.EDiaSemana;
import com.example.salus.io.SalusBDApiAdapter;
import com.example.salus.io.SalusBDApiService;
import com.example.salus.io.URLConection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicoActivity extends AppCompatActivity implements Callback<Medico> {
    private Context context;
    private ImageView imgMedico;
    private TextView txtMatriculaMedico;
    private TextView txtNombreMedico;
    private TextView txtApellidoMedico;
    private TextView txtEmailMedico;
    private TextView txtTelefonoMedico;
    private TextView txtEspecialidadMedico;
    private TextView txtHorarioMedico;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico);

        this.iniciar();

        this.imgMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, home.class);
                limpiarView();
                startActivity(intent);
            }
        });
    }

    private void iniciar() {
        this.context = getApplicationContext();
        this.imgMedico = findViewById(R.id.imgMedico);
        this.txtMatriculaMedico = findViewById(R.id.txtMatriculaMedico);
        this.txtNombreMedico = findViewById(R.id.txtNombreMedico);
        this.txtApellidoMedico = findViewById(R.id.txtApellidoMedico);
        this.txtEmailMedico = findViewById(R.id.txtEmailMedico);
        this.txtTelefonoMedico = findViewById(R.id.txtTelefonoMedico);
        this.txtEspecialidadMedico = findViewById(R.id.txtEspecialidadMedico);
        this.txtHorarioMedico = findViewById(R.id.txtHorarioMedico);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //Integer idMedico = extras.getInt("medicoSeleccionado",0);
        Integer idMedico = Integer.parseInt(extras.get("medicoSeleccionado").toString());
        Log.d("idMedico: ", idMedico.toString());

        SharedPreferences sharedPreferences = getSharedPreferences(login.SHARED_PREFS, MODE_PRIVATE);
        this.token = sharedPreferences.getString(login.TOKEN_KEY, null);

        Call<Medico> call = SalusBDApiAdapter.getApiService().getMedicoId(idMedico);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Medico> call, Response<Medico> response) {
        if (response.isSuccessful()) {
            Medico medico = response.body();
            Log.d("medico: ", "medico recuperado " + medico.toString());

            //imgMedico = medico.getFoto();
            Glide.with(this.context).load(URLConection.URLPrivadaIMG + medico.getFoto()).into(this.imgMedico);
            this.txtMatriculaMedico.setText(medico.getMatricula());
            this.txtNombreMedico.setText(medico.getNombre());
            this.txtApellidoMedico.setText(medico.getApellido());
            this.txtEmailMedico.setText(medico.getEmail());
            this.txtTelefonoMedico.setText(medico.getTelefono());
            this.txtEspecialidadMedico.setText(medico.getId_especialidad().toString());
            this.txtHorarioMedico.setText(medico.getId_especialidad().toString());
            this.obtenerEspecialidad(medico.getId_especialidad());
            this.obtenerHorarioMedico(medico.getId_horario());

        } else {
            Toast.makeText(this.context, "Algo salio mal o Error en las credenciales", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<Medico> call, Throwable t) {
        Toast.makeText(this.context, t.toString(), Toast.LENGTH_SHORT).show();
        Log.e("Error request", t.toString());
    }

    private void obtenerEspecialidad(Long idEspecialidad) {
        Call<Especialidad> call = SalusBDApiAdapter.getApiService().getEspecialidadId("Token " + token, idEspecialidad);
        call.enqueue(new Callback<Especialidad>() {
            @Override
            public void onResponse(Call<Especialidad> call, Response<Especialidad> response) {
                if (response.isSuccessful()) {
                    Especialidad especialidad = response.body();
                    Log.d("especialidad: ", "especialidad recuperada " + especialidad.toString());
                    Log.d("token", "especialidad token " + token);
                    txtEspecialidadMedico.setText(especialidad.getNombre());

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

    private void obtenerHorarioMedico(Long idHorario) {
        Call<HorarioDeAtencion> call = SalusBDApiAdapter.getApiService().getHorarioDeAtencionId("Token " + this.token, idHorario);
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
                txtHorarioMedico.setText(dias + " " + horarioDeAtencion.getHora_entrada() + "-" + horarioDeAtencion.getHora_salida());
            }

            @Override
            public void onFailure(Call<HorarioDeAtencion> call, Throwable t) {
                Toast.makeText(context, "Algo salio mal o Error en las credenciales", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limpiarView() {
        this.imgMedico.setImageResource(R.drawable.dr_pequena_1);
        this.txtMatriculaMedico.setText("");
        this.txtNombreMedico.setText("");
        this.txtApellidoMedico.setText("");
        this.txtEmailMedico.setText("");
        this.txtTelefonoMedico.setText("");
        this.txtEspecialidadMedico.setText("");
        this.txtHorarioMedico.setText("");
    }
}
