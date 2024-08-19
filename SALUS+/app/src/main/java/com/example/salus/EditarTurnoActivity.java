package com.example.salus;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.salus.dao.URLConection;
import com.example.salus.entidad.MiTurno;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditarTurnoActivity extends AppCompatActivity {
    private EditText etObraSocial;
    private Button btnGuardar;
    private MiTurno miTurno;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_turno);

        etObraSocial = findViewById(R.id.etObraSocial);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Obtener el objeto MiTurno enviado desde la actividad anterior
        miTurno = (MiTurno) getIntent().getSerializableExtra("miTurno");

        if (miTurno != null) {
            etObraSocial.setText(miTurno.getObra_social());
        }

        // Obtener el token de SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(login.SHARED_PREFS, MODE_PRIVATE);
        token = sharedPreferences.getString(login.TOKEN_KEY, null);

        // Configurar listener para el botón Guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevaObraSocial = etObraSocial.getText().toString();
                if (nuevaObraSocial.isEmpty()) {
                    Toast.makeText(EditarTurnoActivity.this, "Por favor, ingrese la obra social", Toast.LENGTH_SHORT).show();
                } else {
                    // Actualizar el objeto MiTurno con la nueva obra social
                    miTurno.setObra_social(nuevaObraSocial);
                    // Llamar al método para actualizar el turno en el servidor
                    actualizarTurno(miTurno);
                }
            }
        });
    }

    private void actualizarTurno(MiTurno miTurno) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLConection.URLPrivada)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiDjango apiDjango = retrofit.create(ApiDjango.class);

        Call<MiTurno> call = apiDjango.actualizarMiTurnoReservado("Token " + token, miTurno.getId(), miTurno);
        Log.d("EditarTurnoActivity", "Calling URL: " + URLConection.URLPrivada + "turno-reservado/" + miTurno.getId() + "/");
        Log.d("EditarTurnoActivity", "Request body: " + miTurno.toString());

        call.enqueue(new Callback<MiTurno>() {
            @Override
            public void onResponse(Call<MiTurno> call, Response<MiTurno> response) {
                if (response.isSuccessful()) {
                    Log.d("EditarTurnoActivity", "Turno actualizado con éxito: " + response.body().toString());
                    Toast.makeText(EditarTurnoActivity.this, "Turno actualizado con éxito", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<MiTurno> call, Throwable t) {
                Log.e("EditarTurnoActivity", "Error: " + t.getMessage());
                Toast.makeText(EditarTurnoActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleErrorResponse(Response<MiTurno> response) {
        try {
            Log.e("EditarTurnoActivity", "Error al actualizar turno, código: " + response.code());
            Log.e("EditarTurnoActivity", "Error body: " + response.errorBody().string());
            Toast.makeText(EditarTurnoActivity.this, "Error al actualizar turno: " + response.message(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("EditarTurnoActivity", "Error al procesar error body: " + e.getMessage());
            Toast.makeText(EditarTurnoActivity.this, "Error al actualizar turno", Toast.LENGTH_SHORT).show();
        }
    }

}
