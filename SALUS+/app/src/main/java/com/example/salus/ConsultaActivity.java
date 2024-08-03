package com.example.salus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class ConsultaActivity extends AppCompatActivity {
    private Context context;
    private Button btnHome;
    private TextView consulta_textFcha;
    private TextView consulta_textHora;
    private TextView consulta_textSintomas;
    private TextView consulta_textDiagnostico;
    private TextView consulta_textTratamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        context = getApplicationContext();
        btnHome = findViewById(R.id.consulta_btnConsulta);
        consulta_textFcha = findViewById(R.id.consulta_textFecha);
        consulta_textHora = findViewById(R.id.consulta_textHora);
        consulta_textSintomas = findViewById(R.id.consulta_textSintomas);
        consulta_textDiagnostico = findViewById(R.id.consulta_textDiagnostico);
        consulta_textTratamiento = findViewById(R.id.consulta_textTratamiento);

        iniciar();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, home.class);
                lipiarTextView();
                startActivity(intent);
            }
        });
    }
    private void iniciar() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Integer idConsulta = Integer.parseInt(extras.get("consultaSeleccionada").toString());
        Log.e("idConsulta", idConsulta.toString());

        HttpLoggingInterceptor httpLogginInterceptor = new HttpLoggingInterceptor();
        httpLogginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(httpLogginInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://192.168.56.1:8000/api/v1/")

                .baseUrl(URLConection.URLPrivada)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ApiRegistroDeConsulta apiRegistroDeConsulta = retrofit.create(ApiRegistroDeConsulta.class);
        Call<List<RegistroDeConsulta>> call = apiRegistroDeConsulta.listarId(idConsulta);
        call.enqueue(new Callback<List<RegistroDeConsulta>>() {
            @Override
            public void onResponse(Call<List<RegistroDeConsulta>> call, Response<List<RegistroDeConsulta>> response) {
                if(response.isSuccessful() && response.body() != null){
                    Toast.makeText(context, "Bienvenido", Toast.LENGTH_SHORT).show();

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
                        consulta_textFcha.setText(obj.get(0).getFecha().toString());
                        consulta_textHora.setText(obj.get(0).getHora().toString());
                        consulta_textSintomas.setText(obj.get(0).getSintomas().toString());
                        consulta_textDiagnostico.setText(obj.get(0).getDiagnostico().toString());
                        consulta_textTratamiento.setText(obj.get(0).getTratamiento().toString());
                        consulta_textTratamiento.setText(obj.get(0).getTratamiento().toString());

                }else{
                    Toast.makeText(context, "Error en las credenciales", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<RegistroDeConsulta>> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error request", t.toString());
            }}
        );

    }
    private void lipiarTextView(){
        consulta_textFcha.setText("");
        consulta_textHora.setText("");
        consulta_textSintomas.setText("");
        consulta_textDiagnostico.setText("");
        consulta_textTratamiento.setText("");
    }
}