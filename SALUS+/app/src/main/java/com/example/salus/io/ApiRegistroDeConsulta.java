package com.example.salus.io;

import com.example.salus.entidad.RegistroDeConsulta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRegistroDeConsulta {
    @GET("registrodeconsulta-id/{id}") // Reemplaza "endpoint" con la ruta de tu endpoint
    Call<List<RegistroDeConsulta>> listarId(@Path("id") int id);

    @GET("registrodeconsulta-id/") // Cambia la URL según tu API
    Call<RegistroDeConsulta> getData(@Query("id") int id);

    @GET("registrodeconsulta-turno/{idt}")
    Call<List<RegistroDeConsulta>> listarPorIdTurno(@Path("idt") int idt);

    @GET("registrodeconsulta-lista")
    Call<List<RegistroDeConsulta>> listarTodos();
}
