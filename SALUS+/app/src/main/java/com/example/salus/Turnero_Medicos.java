package com.example.salus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.salus.entidad.Pago;

import com.example.salus.dao.URLConection;
import com.example.salus.entidad.Autorizacion;
import com.example.salus.entidad.PacienteResponse;
import com.example.salus.entidad.Turno;
import com.example.salus.entidad.TurnoDisponible;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Turnero_Medicos extends AppCompatActivity {

    private TextView spinnerDoctor;
    private TextView tvSelectDoctor;
    private Button btnSubmit;
    private Spinner spinnerOS;
    private Spinner spinnerTD;
    private String token;
    private int userId;
    private Boolean pagado;
    private String estado;
    private int turnoDisponible;
    private int idMedico;
    private String obraSocial;
    private int idPaciente;
    private List<TurnoDisponible> turnosDisp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnero_medicos);

        spinnerDoctor = findViewById(R.id.spinnerDoctor);
        tvSelectDoctor = findViewById(R.id.tvSelectDoctor);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Este es el bundle que se trae los datos que mando desde la activity ProfesionalActivity.java
        // Te mando el nombre completo del medico y la especialidad
        // Le sumo tambien la ID del medico y la ID de la especialidad por si te lo piden
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String medico_incoming = extras.get("Medico").toString();
        String especialidad_incoming = extras.get("Especialidad").toString();

        tvSelectDoctor.setText(medico_incoming);
        spinnerDoctor.setText(especialidad_incoming);

        String medicoId = extras.get("MedicoID").toString();
        String especialidadId = extras.get("EspecialidadID").toString();
        Log.d("Medico ID:" , medicoId);
        Log.d("Especialidad ID:" , especialidadId);

        SharedPreferences sharedPreferences = getSharedPreferences(login.SHARED_PREFS, MODE_PRIVATE);
        token = sharedPreferences.getString(login.TOKEN_KEY, null);
        userId = sharedPreferences.getInt(login.USER_ID_KEY, 0);

        cargarTurnosDisponibles();

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pagado = false;

                estado = "";

                turnoDisponible = ((TurnoDisponible) spinnerTD.getSelectedItem()).getId();

                idMedico = Integer.valueOf(medicoId);

                obraSocial = spinnerOS.getSelectedItem().toString();

                getIdPaciente();

            }
        });

        List<String> obrasSoc = Arrays.asList("Particular", "IOMA", "OSDE", "SanCor");
        spinnerOS = findViewById(R.id.spinnerObraSocial);
        ArrayAdapter adapter =new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, obrasSoc);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOS.setAdapter(adapter);

    }

    private void cargarTurnosDisponibles() {

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

        Call<List<TurnoDisponible>> call = apiDjango.getTurnosDisponibles("Token " + token);
        call.enqueue(new Callback<List<TurnoDisponible>>() {
            @Override
            public void onResponse(Call<List<TurnoDisponible>> call, Response<List<TurnoDisponible>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    turnosDisp = response.body();
                    //List<String> turnosDisp = Arrays.asList("Lunes 18hs", "Miércoles 19HS", "Viernes 15hs");
                    spinnerTD = findViewById(R.id.spinnerTurnoDisponible);
                    ArrayAdapter adapterTD =new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, turnosDisp);
                    adapterTD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTD.setAdapter(adapterTD);
                } else {
                    Toast.makeText(Turnero_Medicos.this, "Error al cargar turnos disponibles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TurnoDisponible>> call, Throwable t) {
                Log.e("ProfileActivity", "Request failed: " + t.getMessage());
                Toast.makeText(Turnero_Medicos.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getIdPaciente(){
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
                    Log.d("IDP OBTENIDO", String.valueOf(idPaciente));
                    registrarTurno(pagado, estado, turnoDisponible, idPaciente, idMedico, obraSocial);
                } else {
                    Log.e("IDP NO OBTENIDO", "Error response code: " + response.code());
                    Toast.makeText(Turnero_Medicos.this, "Error al cargar el perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PacienteResponse>> call, Throwable t) {
                Log.e("ProfileActivity", "Request failed: " + t.getMessage());
                Toast.makeText(Turnero_Medicos.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registrarTurno(Boolean pagado, String estado, int turno_disponible, int id_paciente, int id_medico, String obraSocial){

        HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
        loggin.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(loggin);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLConection.URLPrivada)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        ApiDjango turno = retrofit.create(ApiDjango.class);
        Call<Turno> call = turno.agregarTurno(pagado, estado, turno_disponible, id_paciente, id_medico, obraSocial);

        call.enqueue(new Callback<Turno>() {
            @Override
            public void onResponse(Call<Turno> call, Response<Turno> response) {
                if(response.isSuccessful()){
                    Toast.makeText(Turnero_Medicos.this, "Turno registrado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Turnero_Medicos.this, "Error al registrar turno", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Turno> call, Throwable t) {
                Toast.makeText(Turnero_Medicos.this, t.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error request", t.toString());
            }
        });

    }

}
