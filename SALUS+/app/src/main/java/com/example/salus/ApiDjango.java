package com.example.salus;

import com.example.salus.entidad.Autorizacion;

import com.example.salus.entidad.Medicos;
import com.example.salus.entidad.Turno;

import java.util.List;

import com.example.salus.entidad.Especialidad;
import com.example.salus.entidad.PacienteRequest;
import com.example.salus.entidad.PacienteResponse;
import com.example.salus.entidad.RegisterRequest;
import com.example.salus.entidad.RegisterResponse;
import com.example.salus.entidad.TurnoDisponible;
import com.example.salus.entidad.UserProfile;
import com.example.salus.entidad.UserProfileResponse;
import com.example.salus.entidad.UsuarioResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ApiDjango {
    @FormUrlEncoded
    @POST("login")
    Call<Autorizacion> LOGIN_CALL(
      @Field("username") String username,
      @Field("password") String password
    );


    //____ TURNOS ____
    @GET("turno")
    Call<List<Turno>> getTurnos();
    @DELETE("turno/{id}")
    Call<Void> eliminarTurno(@Path("id") int id);
    @PUT("turno/{id}")
    Call<Turno> actualizarTurno(@Path("id") @Body Turno turno);
    @FormUrlEncoded
    @POST("turno/")
    Call<Turno> agregarTurno(
            @Field("pagado") Boolean pagado,
            @Field("estado") String estado,
            @Field("turno_disponible") Integer turno_disponible,
            @Field("id_paciente") Integer id_paciente,
            @Field("id_medico") Integer id_medico,
            @Field("obra_social") String obra_social
    );


    //____ REGISTRO ____
    @POST("registro")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);
    @GET("/api/v1/profile")
    Call<UserProfileResponse> getUserProfile(@Header("Authorization") String token);

    @POST("paciente-registro")
    Call<UsuarioResponse> registerPaciente(@Header("Authorization") String token, @Body PacienteRequest pacienteRequest);
    //____ PERFIL ____
    @GET("/api/v1/profile")
    Call<UserProfile> getProfile(@Header("Authorization") String token);

    @GET("/api/v1/paciente-user/{id}")
    Call<List<PacienteResponse>> getPerfil(@Header("Authorization") String token, @Path("id") int id);

    @PUT("/api/v1/paciente-user/{id}")
    Call<PacienteResponse> updatePaciente(@Header("Authorization") String token, @Path("id") int id, @Body PacienteRequest pacienteRequest);

    @DELETE("/api/v1/paciente-user/{id}")
    Call<Void> deletePaciente(@Header("Authorization") String token, @Path("id") int id);

    @DELETE("/api/v1/profile")
    Call<Void> deleteUser(@Header("Authorization") String token);

    //____ ESPECIALIDAD ____
    @GET("especialidad")
    Call<List<Especialidad>> getEspecialidades();


    //____ Profesional ____
    @GET("medico")
    Call<List<Medicos>> getMedicos();

    @GET("turnos-disponibles")
    Call<List<TurnoDisponible>> getTurnosDisponibles(@Header("Authorization") String token);


}

