package com.example.salus;

import com.example.salus.entidad.Autorizacion;

import com.example.salus.entidad.HorarioDeAtencion;
import com.example.salus.entidad.Medico;
import com.example.salus.entidad.Medicos;
import com.example.salus.entidad.MiTurno;
import com.example.salus.entidad.NuevoTurno;
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
import retrofit2.http.Query;


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

    @GET("medico/especialidad/{especialidadId}")
    Call<List<Medico>> getMedicosPorEspecialidad(@Path("especialidadId") int especialidadId);

    @GET("horariodeatencion-id/{medico_id}")
    Call<List<HorarioDeAtencion>> getHorariosAtencionPorMedico(@Path("medico_id") int medicoId);



    //____ Profesional ____
    @GET("medico")
    Call<List<Medicos>> getMedicos();
    // TURNOS DISPONIBLES
    @GET("turnos-disponibles/")
    Call<List<TurnoDisponible>> getTurnosDisponiblesPorMedico(@Query("medico") int medicoId);
    @POST("crear-turno/")
    Call<NuevoTurno> crearTurno(@Body NuevoTurno nuevoTurno);
    // Obtener todos los turnos de un paciente
    @GET("turnos-por-paciente/")
    Call<List<MiTurno>> getMiTurnosPorPaciente(@Header("Authorization") String token, @Query("id_paciente") int idPaciente);

    // Obtener un turno específico por su ID
    @GET("turno-reservado/{id}/")
    Call<MiTurno> getMiTurnoReservado(@Header("Authorization") String token, @Path("id") int id);

    // Actualizar un turno específico
    @PUT("turno-reservado/{id}/")
    Call<MiTurno> actualizarMiTurnoReservado(@Header("Authorization") String token, @Path("id") int id, @Body MiTurno miTurno);

    // Eliminar un turno específico
    @DELETE("turno-reservado/{id}/")
    Call<Void> eliminarMiTurnoReservado(@Header("Authorization") String token, @Path("id") int id);

/*
    //____ Pago ____
    //@POST("pagar/")
    //Call<Pago> Pago(@Body Pago pago);
*/
}