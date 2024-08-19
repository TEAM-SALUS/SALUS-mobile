package com.example.salus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salus.adaptador.TurnosDisponiblesAdapter;
import com.example.salus.dao.URLConection;
import com.example.salus.entidad.TurnoDisponible;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TurnosDisponiblesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TurnosDisponiblesAdapter adapter;
    private List<TurnoDisponible> turnosDisponibles = new ArrayList<>();
    private int medicoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnos_disponibles);

        recyclerView = findViewById(R.id.recyclerViewTurnos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int medicoId = getIntent().getIntExtra("medicoID", -1);
        if (medicoId != -1) {
            obtenerTurnosDisponibles(medicoId);
        }
    }

    private void obtenerTurnosDisponibles(int medicoId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLConection.URLPrivada)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiDjango api = retrofit.create(ApiDjango.class);
        Call<List<TurnoDisponible>> call = api.getTurnosDisponiblesPorMedico(medicoId);

        call.enqueue(new Callback<List<TurnoDisponible>>() {
            @Override
            public void onResponse(Call<List<TurnoDisponible>> call, Response<List<TurnoDisponible>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    turnosDisponibles = response.body();
                    mostrarTurnosDisponibles();
                } else {
                    Toast.makeText(TurnosDisponiblesActivity.this, "Error al obtener turnos disponibles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TurnoDisponible>> call, Throwable t) {
                Toast.makeText(TurnosDisponiblesActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarTurnosDisponibles() {
        adapter = new TurnosDisponiblesAdapter(turnosDisponibles, TurnosDisponiblesActivity.this);
        recyclerView.setAdapter(adapter);
    }
}
