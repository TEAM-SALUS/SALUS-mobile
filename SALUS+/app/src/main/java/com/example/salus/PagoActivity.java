package com.example.salus;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.salus.adaptador.MedicosEspecialidadAdaptador;
import com.example.salus.entidad.Medico;
import com.example.salus.entidad.Paciente;
import com.example.salus.entidad.Pago;
import com.example.salus.entidad.Turno;
import com.example.salus.enums.EEstadoTurno;
import com.example.salus.io.SalusBDApiAdapter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagoActivity extends AppCompatActivity {
    private EditText editTextMonto;
    private EditText editTextFecha;
    private EditText editTextHora;
    private EditText editTextEspecialidad;
    private EditText editTextMedico;
    private Button btnPagar;
    private String token;
    private Integer id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);

        this.iniciar();

        this.btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PagoActivity.this, "CLICK PAGAR", Toast.LENGTH_LONG).show();
                pagar();
            }
        });
    }

    private void iniciar() {
        this.editTextMonto = findViewById(R.id.editTextMonto);
        this.editTextFecha = findViewById(R.id.editTextFecha);
        this.editTextHora = findViewById(R.id.editTextHora);
        this.editTextEspecialidad = findViewById(R.id.editTextEspecialidad);
        this.editTextMedico = findViewById(R.id.editTextMedico);
        this.btnPagar = findViewById(R.id.btnPagar);

        this.editTextMonto.setEnabled(false);
        this.editTextFecha.setEnabled(false);
        this.editTextHora.setEnabled(false);
        this.editTextEspecialidad.setEnabled(false);
        this.editTextMedico.setEnabled(false);

        SharedPreferences sharedPreferences = getSharedPreferences(login.SHARED_PREFS, MODE_PRIVATE);
        token = sharedPreferences.getString(login.TOKEN_KEY, null);
        id = sharedPreferences.getInt(login.USER_ID_KEY, 0);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String fecha = extras.getString("fecha");
        String hora = extras.getString("hora");
        Long idMedico = extras.getLong("idMedico");
        String especialidad = extras.getString("especialidad");
        Medico medico = (Medico) extras.getSerializable("medico");


        Log.d("PA1", fecha);
        Log.d("PA1", hora);
        Log.d("PA1", idMedico.toString());
        Log.d("PA1", especialidad);
        Log.d("PA1", medico.toString());
        Log.d("PA1", token);
        Log.d("PA1", id.toString());

        this.editTextMonto.setText(especialidad.split("-")[2]);
        this.editTextFecha.setText(fecha);
        this.editTextHora.setText(hora);
        this.editTextEspecialidad.setText(especialidad.split("-")[1]);
        this.editTextMedico.setText(medico.getId() + ", " + medico.getMatricula() + ", " + medico.getApellido() + ", " + medico.getNombre());

    }

    private void pagar() {

        this.obtenerIdPaciente();
    }

    private void obtenerIdPaciente() {
        Call<Paciente> call = SalusBDApiAdapter.getApiService().getPacienteUsuarioId("Token " + this.token, this.id);
        call.enqueue(new Callback<Paciente>() {
            @Override
            public void onResponse(Call<Paciente> call, Response<Paciente> response) {
                if (response.isSuccessful()) {
                    Paciente paciente = response.body();
                    Log.d("onResponse pacientePago", paciente.toString());
                    Log.d("onResponse pacientePago", editTextFecha.getText().toString());
                    Log.d("onResponse pacientePago", editTextFecha.getText().toString().split("-")[0]);
                    Log.d("onResponse pacientePago", editTextFecha.getText().toString().split("-")[1]);
                    Log.d("onResponse pacientePago", editTextFecha.getText().toString().split("-")[2]);
                    String auxFecha = editTextFecha.getText().toString().split("-")[2] + "-" + editTextFecha.getText().toString().split("-")[1] + "-" +
                            editTextFecha.getText().toString().split("-")[0];
                    Log.d("onResponse pacientePago", auxFecha);


                    Turno turno = new Turno(
                            null,
                            auxFecha,
                            editTextHora.getText().toString(),
                            true,
                            EEstadoTurno.Pendiente,
                            null,
                            null,
                            null,
                            true,
                            Long.parseLong(editTextMedico.getText().toString().split(",")[0].trim()),
                            paciente.getId());

                    //Log.d("onResponse pacientePago", turno.toString());

                    registrarTurno(turno);

                } else {
                    Toast.makeText(PagoActivity.this, "Algo salio mal o Error en las credenciales", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Paciente> call, Throwable t) {
                Toast.makeText(PagoActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error request", t.toString());
            }
        });
    }

    private void registrarTurno(Turno turno) {
        Call<Turno> call = SalusBDApiAdapter.getApiService().crearTurno("Token " + this.token, turno);
        call.enqueue(new Callback<Turno>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Turno> call, Response<Turno> response) {
                if (response.isSuccessful()) {
                    Turno turno = response.body();
                    Log.d("onResponse turno-pago", "turno pagado " + turno.toString());

                    Pago pago = new Pago(
                            null,
                            Double.parseDouble(editTextMonto.getText().toString()),
                            LocalDate.now().toString(),
                            LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm")),
                            EEstadoTurno.Pendiente,
                            true,
                            turno.getId()
                    );
                    Log.d("onResponse turno-pago", "pago pagado " + pago.toString());

                    registrarPago(pago);
                } else {
                    Toast.makeText(PagoActivity.this, "Algo salio mal o Error en las credenciales", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Turno> call, Throwable t) {
                Toast.makeText(PagoActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error request", t.toString());
            }
        });
    }

    private void registrarPago(Pago pago) {
        Call<Pago> call = SalusBDApiAdapter.getApiService().crearPago("Token " + this.token, pago);
        call.enqueue(new Callback<Pago>() {
            @Override
            public void onResponse(Call<Pago> call, Response<Pago> response) {
                if (response.isSuccessful()) {
                    Pago pago = response.body();
                    Log.d("onResponse turno-pago", "pago pagado " + pago.toString());

                    irTurno();
                } else {
                    Toast.makeText(PagoActivity.this, "Algo salio mal o Error en las credenciales", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pago> call, Throwable t) {
                Toast.makeText(PagoActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error request", t.toString());
            }
        });
    }

    private void irTurno() {
        Intent intent = new Intent(this, Turnos.class);
        startActivity(intent);
    }
}