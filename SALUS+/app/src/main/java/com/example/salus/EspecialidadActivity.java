package com.example.salus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.salus.adaptador.MedicosEspecialidadAdaptador;
import com.example.salus.entidad.Especialidad;
import com.example.salus.entidad.Medico;
import com.example.salus.io.SalusBDApiAdapter;
import com.example.salus.io.URLConection;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EspecialidadActivity extends AppCompatActivity implements Callback<Especialidad> {
    private ImageView imgEspecialidad;
    private TextView txtNombreEspecialidad;
    private TextView txtPrecioEspecialidad;
    private TextView txtDuracionEspecialidad;
    private TextView txtDescripcionEspecialidad;
    private String token;
    private RecyclerView recyclerMedicos;
    private MedicosEspecialidadAdaptador medicosEspecialidadAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especialidad);

        this.iniciar();
    }

    private void iniciar() {
        this.imgEspecialidad = findViewById(R.id.imgEspecialidad);
        this.txtNombreEspecialidad = findViewById(R.id.txtNombreEspecialidad);
        this.txtPrecioEspecialidad = findViewById(R.id.txtPrecioEspecialidad);
        this.txtDuracionEspecialidad = findViewById(R.id.txtDuracionEspecialidad);
        this.txtDescripcionEspecialidad = findViewById(R.id.txtDescripcionEspecialidad);

        this.recyclerMedicos = findViewById(R.id.recyclerMedicosEspecialidad);
        this.recyclerMedicos.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Long especialidadId = extras.getLong("especialidadId", 0);
        Log.d("especialidadId", especialidadId.toString());

        SharedPreferences sharedPreferences = getSharedPreferences(login.SHARED_PREFS, MODE_PRIVATE);
        this.token = sharedPreferences.getString(login.TOKEN_KEY, null);

        Call<Especialidad> call = SalusBDApiAdapter.getApiService().getEspecialidadId("Token " + this.token, especialidadId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Especialidad> call, Response<Especialidad> response) {
        if (response.isSuccessful()) {
            Especialidad especialidad = response.body();
            Log.d("especialidad: ", "especialidad recuperada " + especialidad.toString());
            Log.d("token", "especialidad token " + token);

            Glide.with(this).load(URLConection.URLPrivadaIMG + especialidad.getFoto()).into(this.imgEspecialidad);
            this.txtNombreEspecialidad.setText(especialidad.getNombre());
            this.txtPrecioEspecialidad.setText(String.valueOf(especialidad.getPrecio()));
            this.txtDuracionEspecialidad.setText(especialidad.getDuracion());
            this.txtDescripcionEspecialidad.setText(especialidad.getDescripcion());

            this.CargarMedicosEspecialidad(especialidad.getId());
        } else {
            Log.d("error especialidad", "especialidad no recuperada");
            Toast.makeText(this, "Algo salio mal o Error en las credenciales", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<Especialidad> call, Throwable t) {
        Toast.makeText(this, t.toString(), Toast.LENGTH_SHORT).show();
        Log.e("Error request", t.toString());
    }

    private void CargarMedicosEspecialidad(Long id) {
        Call<List<Medico>> call = SalusBDApiAdapter.getApiService().getMedicosEspecialidadLista("Token " + this.token, id);
        call.enqueue(new Callback<List<Medico>>() {
            @Override
            public void onResponse(Call<List<Medico>> call, Response<List<Medico>> response) {
                if (response.isSuccessful()) {
                    List<Medico> medicosLista = response.body();
                    Log.d("onResponse medicosEspecialidadLista", "Size of medicoLista " + medicosLista.size());
                    medicosEspecialidadAdaptador = new MedicosEspecialidadAdaptador(medicosLista, EspecialidadActivity.this);
                    recyclerMedicos.setAdapter(medicosEspecialidadAdaptador);
                    if (medicosLista.size() != 0) {
                        Log.d("onResponse medicosEspecialidadLista", "foto of medicoLista " + "http://192.168.0.44:8000" + medicosLista.get(1).getFoto());
                    }
                } else {
                    Toast.makeText(EspecialidadActivity.this, "Algo salio mal o Error en las credenciales", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Medico>> call, Throwable t) {
                Toast.makeText(EspecialidadActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error request", t.toString());
            }
        });
    }
}