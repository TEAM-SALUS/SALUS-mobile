package com.example.salus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.salus.adaptador.MedicosAdaptador;
import com.example.salus.entidad.Medico;
import com.example.salus.io.SalusBDApiAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicosActivity extends AppCompatActivity implements Callback<List<Medico>> {
    private Context context;
    private RecyclerView recyclerMedicos;
    private MedicosAdaptador medicosAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicos);

        this.iniciar();

        Call<List<Medico>> call = SalusBDApiAdapter.getApiService().getMedicosLista();
        call.enqueue(this);
    }

    private void iniciar() {
        context = getApplicationContext();
        recyclerMedicos = findViewById(R.id.recyclerMedicos);
        recyclerMedicos.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onResponse(Call<List<Medico>> call, Response<List<Medico>> response) {
        if (response.isSuccessful()) {
            List<Medico> medicosLista = response.body();
            Log.d("onResponse medicosLista", "Size of medicoLista " + medicosLista.size());
            Log.d("onResponse medicosLista", "foto of medicoLista " + "http://192.168.0.44:8000" + medicosLista.get(1).getFoto());
            medicosAdaptador = new MedicosAdaptador(medicosLista, context);
            recyclerMedicos.setAdapter(medicosAdaptador);
        } else {
            Toast.makeText(context, "Algo salio mal o Error en las credenciales", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<List<Medico>> call, Throwable t) {
        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
        Log.e("Error request", t.toString());
    }
}