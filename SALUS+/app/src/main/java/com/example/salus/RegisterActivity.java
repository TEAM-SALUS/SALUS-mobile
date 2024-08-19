package com.example.salus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.salus.dao.URLConection;
import com.example.salus.entidad.PacienteRequest;
import com.example.salus.entidad.RegisterRequest;
import com.example.salus.entidad.RegisterResponse;
import com.example.salus.entidad.UserProfileResponse;
import com.example.salus.entidad.UsuarioResponse;


import java.io.IOException;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.Gson;
public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etEmail, etPassword, etDni, etNombre, etApellido, etTelefono;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUsername = findViewById(R.id.etPacienteUser);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etClave);
        etDni = findViewById(R.id.etDniPaciente);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etTelefono = findViewById(R.id.etTelefono);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    registerUser();
                }
            }
        });
    }

    private boolean validateInputs() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String dni = etDni.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();

        if (username.isEmpty()) {
            etUsername.setError("El nombre de usuario es obligatorio");
            etUsername.requestFocus();
            return false;
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Introduce un email válido");
            etEmail.requestFocus();
            return false;
        }

        if (password.isEmpty() || password.length() < 6) {
            etPassword.setError("La contraseña debe tener al menos 6 caracteres");
            etPassword.requestFocus();
            return false;
        }

        if (dni.isEmpty() || !Pattern.matches("\\d{8,}", dni)) {
            etDni.setError("El DNI debe ser solo números y tener al menos 8 caracteres");
            etDni.requestFocus();
            return false;
        }

        if (nombre.isEmpty()) {
            etNombre.setError("El nombre es obligatorio");
            etNombre.requestFocus();
            return false;
        }

        if (apellido.isEmpty()) {
            etApellido.setError("El apellido es obligatorio");
            etApellido.requestFocus();
            return false;
        }

        if (telefono.isEmpty()) {
            etTelefono.setError("El teléfono es obligatorio");
            etTelefono.requestFocus();
            return false;
        }

        return true;
    }

    private void registerUser() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        RegisterRequest registerRequest = new RegisterRequest(username, email, password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLConection.URLPrivada)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiDjango apiDjango = retrofit.create(ApiDjango.class);

        Call<RegisterResponse> call = apiDjango.registerUser(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    getUserProfile(token);
                } else {
                    Log.e("RegisterActivity", "Error en la respuesta: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.e("RegisterActivity", "Error body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(RegisterActivity.this, "Error en el registro de usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("RegisterActivity", "Fallo en la llamada: " + t.getMessage(), t);
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserProfile(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLConection.URLPrivada)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiDjango apiDjango = retrofit.create(ApiDjango.class);

        Call<UserProfileResponse> call = apiDjango.getUserProfile("Token " + token);
        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int pacienteUser = response.body().getId();
                    Log.d("RegisterActivity", "User ID: " + pacienteUser);
                    registerPaciente(token, pacienteUser);

                } else {
                    Log.e("RegisterActivity", "Error en la respuesta del perfil: " + response.message());
                    Toast.makeText(RegisterActivity.this, "Error al obtener el perfil del usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                Log.e("RegisterActivity", "Fallo en la llamada del perfil: " + t.getMessage(), t);
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void registerPaciente(String token, int pacienteUser) {
        Log.d("RegisterActivity", "User ID: " + pacienteUser);
        String dni = etDni.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String clave = etPassword.getText().toString().trim(); // Usar etPassword como clave
        String telefono = etTelefono.getText().toString().trim();

        PacienteRequest pacienteRequest = new PacienteRequest(dni, nombre, apellido, email, clave, telefono, pacienteUser);
        Log.d("RegisterActivity", "PacienteRequest: " + new Gson().toJson(pacienteRequest));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLConection.URLPrivada)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiDjango apiDjango = retrofit.create(ApiDjango.class);

        Call<UsuarioResponse> call = apiDjango.registerPaciente("Token " + token, pacienteRequest);
        call.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Error en el registro del paciente", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }}



