package com.example.salus;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Turnos extends AppCompatActivity {
    private RecyclerView recyclerTurno;
    //private TurnosAdaptador turnosAdaptador;
    //public EspecialidadesAdapter especialidadesAdapter;
    private Context context;
    private ApiDjango api;
    ImageButton turno_wpp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnos);
        context = getApplicationContext();
        recyclerTurno = findViewById(R.id.recyclerTurno);
        recyclerTurno.setLayoutManager(new LinearLayoutManager(context));
        turno_wpp = findViewById(R.id.turno_wpp);

        turno_wpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wppurl = "https://wa.me/+543525482570?text=¡Hola! Quiero solicitar información sobre los servicios y reservar un turno.";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(wppurl));
                startActivity(i);
            }
        });

        /* Obtener la instancia de Retrofit usando ApiClient y la URL base
        Retrofit retrofit = ApiClient.getClient(URLConection.URLPrivada);
        api = retrofit.create(ApiDjango.class);

        // Obtener toda la lista de los turnos
        Call<List<Turno>> call = api.getTurnos();
        call.enqueue(new Callback<List<Turno>>() {
            @Override
            public void onResponse(Call<List<Turno>> call, Response<List<Turno>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Turno> turnosLista = response.body();
                    for (Turno turno : turnosLista) {
                        Log.d("TURNO", String.valueOf(turno.getFecha()));
                    }
                    turnosAdaptador = new TurnosAdaptador(turnosLista, Turnos.this, api);
                    recyclerTurno.setAdapter(turnosAdaptador);
                    Toast.makeText(Turnos.this, "API funcionando", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Turnos.this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Turno>> call, Throwable t) {
                Log.e("Turnos", "Error al realizar la llamada a la API.", t);
                Toast.makeText(Turnos.this, "Error. Detalles: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
/*
        Button botonPagar = findViewById(R.id.botonPagar);
        botonPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pago pago = new Pago();
                pago.setMonto(100);  // Establecer el monto
                pago.setFecha("2024-06-06");  // Establecer la fecha
                pago.setHora("10:00:00");  // Establecer la hora
                pago.setEstado("Pendiente");  // Establecer el estado
                pago.setId_turno(1);  // Establecer el ID del turno

                realizarPago(pago);
            }
        });

        recyclerPago = findViewById(R.id.recyclerPago);
    }

    public void realizarPago(Pago pago) {
        Call<Pago> call = api.pagar(pago);
        call.enqueue(new Callback<Pago>() {
            @Override
            public void onResponse(Call<Pago> call, Response<Pago> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Turnos.this, "Pago realizado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Turnos.this, "Error en el pago", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pago> call, Throwable t) {
                Toast.makeText(Turnos.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
    /*
    public void eliminarTurno(int id, int position) {
        Call<Void> callEliminar = api.eliminarTurno(id);
        callEliminar.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    turnosAdaptador.eliminarItem(position);
                    Toast.makeText(Turnos.this, "Turno eliminado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Turnos.this, "Error al eliminar el turno", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Turnos.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
