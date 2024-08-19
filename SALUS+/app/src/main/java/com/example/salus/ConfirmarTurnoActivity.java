package com.example.salus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.salus.dao.URLConection;
import com.example.salus.entidad.NuevoTurno;
import com.example.salus.entidad.PacienteResponse;
import com.example.salus.entidad.TurnoDisponible;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfirmarTurnoActivity extends AppCompatActivity {

    private TextView tvDia, tvHora;
    private EditText etObraSocial;
    private Button btnConfirmar;

    private int idTurnoDisponible;
    private int idPaciente;
    private int idMedico;
    private String token;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_turno);

        tvDia = findViewById(R.id.tvDia);
        tvHora = findViewById(R.id.tvHora);
        etObraSocial = findViewById(R.id.etObraSocial);
        btnConfirmar = findViewById(R.id.btnConfirmar);

        SharedPreferences sharedPreferences = getSharedPreferences(login.SHARED_PREFS, MODE_PRIVATE);
        token = sharedPreferences.getString(login.TOKEN_KEY, null);
        userId = sharedPreferences.getInt(login.USER_ID_KEY, 0);

        idTurnoDisponible = getIntent().getIntExtra("idTurnoDisponible", 0);
        String dia = getIntent().getStringExtra("dia");
        String hora = getIntent().getStringExtra("hora");
        tvDia.setText(dia);
        tvHora.setText(hora);

        idPaciente = getIdPaciente();
        idMedico = getIdMedicoFromSharedPreferences();

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String obraSocial = etObraSocial.getText().toString();
                if (obraSocial.isEmpty()) {
                    Toast.makeText(ConfirmarTurnoActivity.this, "Por favor, ingrese la obra social", Toast.LENGTH_SHORT).show();
                } else {
                    confirmarTurno();
                }
            }
        });
    }

    private int getIdPaciente() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(httpLoggingInterceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLConection.URLPrivada)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ApiDjango apiDjango = retrofit.create(ApiDjango.class);

        Call<List<PacienteResponse>> call = apiDjango.getPerfil("Token " + token, userId);
        call.enqueue(new Callback<List<PacienteResponse>>() {
            @Override
            public void onResponse(Call<List<PacienteResponse>> call, Response<List<PacienteResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    PacienteResponse paciente = response.body().get(0);
                    idPaciente = paciente.getId();
                    Log.d("ConfirmarTurnoActivity", "ID Paciente obtenido: " + idPaciente);
                } else {
                    Log.e("ConfirmarTurnoActivity", "Error al obtener ID del paciente, código: " + response.code());
                    Toast.makeText(ConfirmarTurnoActivity.this, "Error al cargar el perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PacienteResponse>> call, Throwable t) {
                Log.e("ConfirmarTurnoActivity", "Error al obtener el perfil: " + t.getMessage());
                Toast.makeText(ConfirmarTurnoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return 0;
    }

    private int getIdMedicoFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        int idMedico = sharedPreferences.getInt("idMedico", 0);
        if (idMedico == 0) {
            Toast.makeText(this, "Error: ID del médico no proporcionado", Toast.LENGTH_SHORT).show();
            finish();
        }
        return idMedico;
    }

    private void confirmarTurno() {
        String obraSocial = etObraSocial.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLConection.URLPrivada)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiDjango api = retrofit.create(ApiDjango.class);
        NuevoTurno nuevoTurno = new NuevoTurno();
        nuevoTurno.setPagado(false); // Por defecto no está pagado
        nuevoTurno.setEstado("Pendiente"); // Por defecto está pendiente
        nuevoTurno.setId_paciente(idPaciente);
        nuevoTurno.setId_medico(idMedico);
        nuevoTurno.setTurno_disponible(idTurnoDisponible); // Agregar ID del turno disponible
        nuevoTurno.setObra_social(obraSocial);

        Log.d("ConfirmarTurnoActivity", "Datos a enviar: " + nuevoTurno.toString());

        Call<NuevoTurno> call = api.crearTurno(nuevoTurno);
        call.enqueue(new Callback<NuevoTurno>() {
            @Override
            public void onResponse(Call<NuevoTurno> call, Response<NuevoTurno> response) {
                if (response.isSuccessful()) {
                    Log.d("ConfirmarTurnoActivity", "Turno reservado con éxito: " + response.body().toString());
                    Toast.makeText(ConfirmarTurnoActivity.this, "Turno reservado con éxito", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ConfirmarTurnoActivity.this, home.class); // Redirigir al Home
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("ConfirmarTurnoActivity", "Error al reservar turno, código: " + response.code());
                    Toast.makeText(ConfirmarTurnoActivity.this, "Error al reservar turno", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NuevoTurno> call, Throwable t) {
                Log.e("ConfirmarTurnoActivity", "Error: " + t.getMessage());
                Toast.makeText(ConfirmarTurnoActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


