package com.example.salus;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salus.adaptador.MiTurnoAdapter;
import com.example.salus.dao.URLConection;
import com.example.salus.entidad.MiTurno;
import com.example.salus.entidad.PacienteResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MisTurnos extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MiTurnoAdapter miTurnoAdapter;
    private List<MiTurno> miTurnoList;
    private String token;
    private int userId;
    private int idPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_turnos);

        recyclerView = findViewById(R.id.rvTurnos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        miTurnoList = new ArrayList<>();
        miTurnoAdapter = new MiTurnoAdapter(miTurnoList, this);
        recyclerView.setAdapter(miTurnoAdapter);

        // Obtener token y userId desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(login.SHARED_PREFS, MODE_PRIVATE);
        token = sharedPreferences.getString(login.TOKEN_KEY, null);
        userId = sharedPreferences.getInt(login.USER_ID_KEY, 0);

        // Llamar al método para obtener el id del paciente
        getIdPaciente();
    }

    private void getIdPaciente() {
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
                    Log.d("MisTurnosActivity", "ID Paciente obtenido: " + idPaciente);

                    // Una vez obtenido el id del paciente, llamar a obtenerMiTurnos()
                    obtenerMiTurnos();
                } else {
                    Log.e("MisTurnosActivity", "Error al obtener ID del paciente, código: " + response.code());
                    Toast.makeText(MisTurnos.this, "Error al cargar el perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PacienteResponse>> call, Throwable t) {
                Log.e("MisTurnosActivity", "Error al obtener el perfil: " + t.getMessage());
                Toast.makeText(MisTurnos.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void obtenerMiTurnos() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(httpLoggingInterceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLConection.URLPrivada)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiDjango apiDjango = retrofit.create(ApiDjango.class);
        Call<List<MiTurno>> call = apiDjango.getMiTurnosPorPaciente("Token " + token, idPaciente);
        call.enqueue(new Callback<List<MiTurno>>() {
            @Override
            public void onResponse(Call<List<MiTurno>> call, Response<List<MiTurno>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Si la solicitud fue exitosa, actualizar la lista de turnos y notificar al adaptador
                    miTurnoList.clear();
                    miTurnoList.addAll(response.body());
                    miTurnoAdapter.notifyDataSetChanged();
                } else {
                    // Si hubo un error en la solicitud, mostrar mensaje de error
                    Log.e("MisTurnosActivity", "Error al obtener turnos, código: " + response.code());
                    Toast.makeText(MisTurnos.this, "Error al obtener turnos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MiTurno>> call, Throwable t) {
                // Si hubo un fallo en la conexión, mostrar mensaje de error
                Log.e("MisTurnosActivity", "Error: " + t.getMessage());
                Toast.makeText(MisTurnos.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

