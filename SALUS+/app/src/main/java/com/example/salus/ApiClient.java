package com.example.salus;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiClient {
    private static Retrofit retrofit = null;

    // Método para obtener una instancia de Retrofit
    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            // Configurar el interceptor de logging para ver las solicitudes y respuestas HTTP en el logcat
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Construir el cliente OkHttp y añadir el interceptor de logging
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            // Construir la instancia de Retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)  // URL base para las solicitudes HTTP
                    .addConverterFactory(GsonConverterFactory.create())  // Convertidor para manejar JSON
                    .client(httpClient.build())  // Cliente OkHttp configurado con logging
                    .build();
        }
        return retrofit;
    }
}
