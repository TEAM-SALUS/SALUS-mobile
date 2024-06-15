package com.example.salus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.salus.dao.URLConection;
import com.example.salus.entidad.Autorizacion;
import com.example.salus.entidad.UserProfile;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity {
    public static final String TOKEN="TOKEN";
    public static final String ID = "ID";
    public static final String DNI_CLIENT="DNICLIENTE";
    public static final String COD_SERVICIO="CODSERVICIO";
    public static final String DNI_PROFESIONAL="DNIPROFESIONAL";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TOKEN_KEY = "token";
    public static final String USER_ID_KEY = "userId";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn_ingresar = findViewById(R.id.btn_ingresar);
        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            EditText username = findViewById(R.id.login_user);
            EditText password = findViewById(R.id.login_pass);
            HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                loggin.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(loggin);
                Retrofit retrofit = new Retrofit.Builder()

                        .baseUrl(URLConection.URLPrivada)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
                ApiDjango login = retrofit.create(ApiDjango.class);
                Call<Autorizacion> call = login.LOGIN_CALL(user, pass);

                if(user.isEmpty()){
                    username.setError("El nombre de usuario es requerido");
                } else if (pass.isEmpty()) {
                    password.setError("La contraaseña es requerida");
                }else{
                    call.enqueue(new Callback<Autorizacion>() {
                        @Override
                        public void onResponse(Call<Autorizacion> call, Response<Autorizacion> response) {
                            if(response.isSuccessful() && response.body() != null){
                                username.getText().clear();
                                password.getText().clear();
                                String token = response.body().getToken();
                                //int ID = response.body().getid();
                                //Intent intent = new Intent(login.this, home.class);
                                //intent.putExtra("token", token);
                                //intent.putExtra("id", ID);
                                //startActivity(intent);
                                // Guardar el token en SharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(TOKEN_KEY, token);
                                editor.apply();

                                // Obtener el perfil del usuario para extraer el ID
                                getUserProfile(token);
                               // Toast.makeText(login.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(login.this, "Error en las credenciales", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Autorizacion> call, Throwable t) {
                            Toast.makeText(login.this, t.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("Error request", t.toString());
                        }
                    });
                }

            }
        });
    }
    private void getUserProfile(String token) {
        Log.d("getUserProfile", "Token de autorización: " + token);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLConection.URLPrivada)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ApiDjango apiDjango = retrofit.create(ApiDjango.class);
        String requestUrl = URLConection.URLPrivada + "/api/v1/profile";
        Log.d("getUserProfile", "URL de la solicitud: " + requestUrl);
        Call<UserProfile> call = apiDjango.getProfile("Token " + token);

        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserProfile userProfile = response.body();

                    // Log completo de la respuesta

                    Log.d("getUserProfile", "Response body: " + userProfile.toString());

                    int userId = response.body().getId();

                    // Guardar el ID del usuario en SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(USER_ID_KEY, userId);
                    editor.apply();

                    // Ir a la siguiente actividad
                    Intent intent = new Intent(login.this, home.class);
                    startActivity(intent);
                    Toast.makeText(login.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(login.this, "Error al obtener el perfil del usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Toast.makeText(login.this, t.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error request", t.toString());
            }
        });
    }
}


