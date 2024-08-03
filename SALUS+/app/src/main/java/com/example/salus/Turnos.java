package com.example.salus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.bumptech.glide.Glide;
import com.example.salus.adaptador.TurnosAdaptador;
import com.example.salus.entidad.Medico;
import com.example.salus.entidad.Paciente;
import com.example.salus.entidad.Turno;
import com.example.salus.io.SalusBDApiAdapter;
import com.example.salus.io.URLConection;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Turnos extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerTurno;
    private TurnosAdaptador turnosAdaptador;
    //public EspecialidadesAdapter especialidadesAdapter;
    private ApiDjango api;
    private ImageButton turno_wpp;
    private String token;
    private Integer id;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnos);

        this.iniciar();

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
    private void iniciar() {
        this.context = getApplicationContext();
        this.recyclerTurno = findViewById(R.id.recyclerTurno);
        this.recyclerTurno.setLayoutManager(new LinearLayoutManager(context));
        this.turno_wpp = findViewById(R.id.turno_wpp);

        Log.d("Iniciar", "Entro inciar 1");

        SharedPreferences sharedPreferences = getSharedPreferences(login.SHARED_PREFS, MODE_PRIVATE);
        this.token = sharedPreferences.getString(login.TOKEN_KEY, null);
        this.id = sharedPreferences.getInt(login.USER_ID_KEY, 0);

        Log.d("Iniciar", "Entro inciar 1.token" + this.token);
        Log.d("Iniciar", "Entro inciar 1.id" + this.id);

        this.obtenerPacientePorUsuario();

    }

    /**
     * @apiNote busca paciente por usuario
     * @see <a href="https://youtu.be/F8YDPCmYAJ0?si=04TORX6ep1S7PI2w>Ir a YouTube tuto</a>
     */
    private void obtenerPacientePorUsuario() {
        Call<Paciente> call = SalusBDApiAdapter.getApiService().getPacienteUsuarioId("Token " + this.token, this.id);
        call.enqueue(new Callback<Paciente>() {
            @Override
            public void onResponse(Call<Paciente> call, Response<Paciente> response) {
                Log.d("Iniciar", "Entro inciar 3");
                paciente = response.body();
                Log.d("onResponse paciente", "paciente recuperada " + paciente.toString());
                obtenerTurnosPorPaciente();            }

            @Override
            public void onFailure(Call<Paciente> call, Throwable t) {
                Log.d("onResponse paciente", "ponResponse paciente error");
                Toast.makeText(context, "Algo salio mal o Error en las credenciales", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void obtenerTurnosPorPaciente() {
        Call<List<Turno>> call = SalusBDApiAdapter.getApiService().getTurnosPacienteLista("Token " + this.token, this.paciente.getId());
        call.enqueue(new Callback<List<Turno>>() {
            @Override
            public void onResponse(Call<List<Turno>> call, Response<List<Turno>> response) {
                if (response.isSuccessful()) {
                    Log.d("Iniciar", "Entro inciar 2");
                    List<Turno> turnosLista = response.body();
                    Log.d("onResponse turnosLista: ", "Size turnosLista " + turnosLista.size());
                    turnosAdaptador = new TurnosAdaptador(turnosLista, context);
                    recyclerTurno.setAdapter(turnosAdaptador);
                } else {
                    Toast.makeText(context, "Algo salio mal o Error en las credenciales", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Turno>> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error request", t.toString());
            }
        });
    }
}
