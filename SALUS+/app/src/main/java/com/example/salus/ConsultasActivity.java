package com.example.salus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.salus.adaptador.ConsultasAdaptador;
import com.example.salus.io.ApiRegistroDeConsulta;
import com.example.salus.io.URLConection;
import com.example.salus.entidad.RegistroDeConsulta;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultasActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerConsultas;
    private ConsultasAdaptador consultasAdaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        context = getApplicationContext();
        recyclerConsultas = findViewById(R.id.recyclerConsultas);
        recyclerConsultas.setLayoutManager(new LinearLayoutManager(context));

        HttpLoggingInterceptor httpLogginInterceptor = new HttpLoggingInterceptor();
        httpLogginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(httpLogginInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLConection.URLPrivada)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ApiRegistroDeConsulta apiRegistroDeConsulta = retrofit.create(ApiRegistroDeConsulta.class);
        Call<List<RegistroDeConsulta>> call = apiRegistroDeConsulta.listarTodos();
        call.enqueue(new Callback<List<RegistroDeConsulta>>() {
            @Override
            public void onResponse(Call<List<RegistroDeConsulta>> call, Response<List<RegistroDeConsulta>> response) {
                if(response.isSuccessful() && response.body() != null){
                    Toast.makeText(context, "Bienvenido", Toast.LENGTH_SHORT).show();

                    Log.e("json back",response.body().toString());

                    List<RegistroDeConsulta> obj = response.body();

                    for (RegistroDeConsulta registroDeConsulta : obj) {
                        Log.d(
                                "especialidad",
                                "id " + registroDeConsulta.getId()
                                + ", Fecha " + registroDeConsulta.getFecha()
                                + ", Hora " + registroDeConsulta.getHora()
                                + ", Sintomas " + registroDeConsulta.getSintomas()
                                + ", Diagnostico " + registroDeConsulta.getDiagnostico()
                                + ", Tratamiento " + registroDeConsulta.getTratamiento()
                                + ", id turno " + registroDeConsulta.getId_turno()
                        );
                    }
                    Log.e("json back",obj.toString());
                    consultasAdaptador = new ConsultasAdaptador(obj,context);
                    recyclerConsultas.setAdapter(consultasAdaptador);
                }else{
                    Toast.makeText(context, "Error en las credenciales", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RegistroDeConsulta>> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error request", t.toString());
            }});

        //iniciar();
    }
    /*
    private void iniciar() {
        context = getApplicationContext();
        recyclerConsultas = findViewById(R.id.recyclerConsultas);
        recyclerConsultas.setLayoutManager(new LinearLayoutManager(context));

        HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
        loggin.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(loggin);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.44:8000/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ApiRegistroDeConsulta login = retrofit.create(ApiRegistroDeConsulta.class);
        Call<RegistroDeConsulta> call = login.listarId(1);
        call.enqueue(new Callback<RegistroDeConsulta>() {
            @Override
            public void onResponse(Call<RegistroDeConsulta> call, Response<RegistroDeConsulta> response) {
                if(response.isSuccessful() && response.body() != null){
                    Toast.makeText(context, "Bienvenido", Toast.LENGTH_SHORT).show();
                    ArrayList<RegistroDeConsulta> listaRDC = new ArrayList<RegistroDeConsulta>();
                    listaRDC.add(response.body());
                    consultasAdaptador = new ConsultasAdaptador(listaRDC,context);
                    recyclerConsultas.setAdapter(consultasAdaptador);
                }else{
                    Toast.makeText(context, "Error en las credenciales", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegistroDeConsulta> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error request", t.toString());
            }
        });
*/
/*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.44:8000/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRegistroDeConsulta inter = retrofit.create(ApiRegistroDeConsulta.class);
        Call<ArrayList<RegistroDeConsulta>> cal = inter.listarTodos();
        cal.enqueue(new Callback<ArrayList<RegistroDeConsulta>>() {
            @Override
            public void onResponse(Call<ArrayList<RegistroDeConsulta>> call, Response<ArrayList<RegistroDeConsulta>> response) {
                consultasAdaptador = new ConsultasAdaptador(response.body(),context);
                recyclerConsultas.setAdapter(consultasAdaptador);
            }

            @Override
            public void onFailure(Call<ArrayList<RegistroDeConsulta>> call, Throwable t) {
                //Toast.makeText(context,"error",Toast.LENGTH_LONG).show();
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error request",t.toString());
            }
        });

    }
 */
}