package com.example.salus;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiClient {
    private static ApiDjango API_SERVICE;

    /**
     * Localhost IP for AVD emulators: 10.0.2.2
     * http://192.168.0.44:8000/api/v1/
     */
    private static final String BASE_URL = "http://192.168.0.44:8000/api/v1/";

    // Método para obtener una instancia de Retrofit
    public static ApiDjango getClient() {
        // Configurar el interceptor de logging para ver las solicitudes y respuestas HTTP en el logcat
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Construir el cliente OkHttp y añadir el interceptor de logging
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        if (API_SERVICE == null) {

            // Construir la instancia de Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)  // URL base para las solicitudes HTTP
                    .addConverterFactory(GsonConverterFactory.create())  // Convertidor para manejar JSON
                    .client(httpClient.build())  // Cliente OkHttp configurado con logging
                    .build();

            API_SERVICE = retrofit.create(ApiDjango.class);
        }
        return API_SERVICE;
    }
}
