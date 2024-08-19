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
import com.example.salus.entidad.Turno;

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
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button btnSubmit;
    private Spinner spinnerOS;
    private Spinner spinnerTD;
    private int pacienteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnero_medicos);

        spinnerDoctor = findViewById(R.id.spinnerDoctor);
        tvSelectDoctor = findViewById(R.id.tvSelectDoctor);
        btnSubmit = findViewById(R.id.btnSubmit);

        SharedPreferences sharedPreferences = getSharedPreferences(login.SHARED_PREFS, MODE_PRIVATE);
        pacienteId = sharedPreferences.getInt(login.USER_ID_KEY, 0);

        if (pacienteId == 0) {
            Toast.makeText(this, "Error: ID del paciente no proporcionado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Configurar Spinner con una lista de doctores
        /*ArrayList<String> doctorList = new ArrayList<>();
        doctorList.add("Dr. Smith");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doctorList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctor.setAdapter(adapter);*/

        // Comente lo de arriba porque ese codigo creaba el spinner, el cual ya no es mas un spinner
        // sino que es un textView que seria donde se ve la especialidad ahora

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

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
            @Override
            public void onClick(View v) {

                //Intent intento = new Intent(Turnero_Medicos.this, Pago.class);
                //intento.putExtra("Medico",spinnerDoctor.getText());
                //intento.putExtra("MedicoID",medicoId);
                //intento.putExtra("EspecialidadID",especialidadId);

                //String selectedDoctor = spinnerDoctor.getSelectedItem().toString();
                //String selectedDoctor = "asd";
                //int day = datePicker.getDayOfMonth();
                //int month = datePicker.getMonth();
                //int year = datePicker.getYear();
                //int hour = timePicker.getHour();
                //int minute = timePicker.getMinute();
                //intento.putExtra("Fecha",year+"/"+month+"/"+day);
                //intento.putExtra("Hora",hour+":"+minute);

                //String appointmentDetails = "Doctor: " + selectedDoctor + "\n" +
                        //"Date: " + day + "/" + (month + 1) + "/" + year + "\n" +
                        //"Time: " + String.format("%02d:%02d", hour, minute);

                //Toast.makeText(Turnero_Medicos.this, appointmentDetails, Toast.LENGTH_LONG).show();

                //startActivity(Intent.createChooser(intento,"Compartir en").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                Boolean pagado = false;
                String estado = "";
                Integer turno_disponible = 1;
                //Integer id_paciente = 3;
                Integer id_medico = 1;
                String obra_social = "Particular";
                loggin.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(loggin);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URLConection.URLPrivada)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
                ApiDjango turno = retrofit.create(ApiDjango.class);
                Call<Turno> call = turno.agregarTurno(pagado, estado, turno_disponible, pacienteId, id_medico, obra_social);

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
        });

        List<String> turnosDisp = Arrays.asList("Lunes 18hs", "Miércoles 19HS", "Viernes 15hs");
        spinnerTD = findViewById(R.id.spinnerTurnoDisponible);
        ArrayAdapter adapterTD =new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, turnosDisp);
        adapterTD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTD.setAdapter(adapterTD);

        List<String> obrasSoc = Arrays.asList("Particular", "IOMA", "OSDE", "SanCor");
        spinnerOS = findViewById(R.id.spinnerObraSocial);
        ArrayAdapter adapter =new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, obrasSoc);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOS.setAdapter(adapter);

    }


}
