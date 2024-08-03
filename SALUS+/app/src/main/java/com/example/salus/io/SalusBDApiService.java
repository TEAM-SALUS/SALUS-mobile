package com.example.salus.io;

import com.example.salus.entidad.Especialidad;
import com.example.salus.entidad.HorarioDeAtencion;
import com.example.salus.entidad.Medico;
import com.example.salus.entidad.Paciente;
import com.example.salus.entidad.PacienteEditar;
import com.example.salus.entidad.Pago;
import com.example.salus.entidad.Turno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SalusBDApiService {
    /**
     * MEDICOS
     */
    @GET("medico-lista")
    Call<List<Medico>> getMedicosLista();

    @GET("medico-id/{id}")
    Call<Medico> getMedicoId(@Path("id") int id);

    @GET("medico-especialidad/{id}")
    Call<List<Medico>> getMedicosEspecialidadLista(@Header("Authorization") String token, @Path("id") Long id);

    /**
     * ESPECIALIDADES
     */
    @GET("especialidad-id/{id}")
    Call<Especialidad> getEspecialidadId(@Header("Authorization") String token, @Path("id") Long id);

    @GET("especialidad-lista")
    Call<List<Especialidad>> getEspecialidadesLista();

    /**
     * HORARIOS DE ATENCION
     */
    @GET("horario-de-atencion-id/{id}")
    Call<HorarioDeAtencion> getHorarioDeAtencionId(@Header("Authorization") String token, @Path("id") Long id);

    /**
     * TURNOS
     */
    @GET("turno-paciente/{id}")
    Call<List<Turno>> getTurnosPacienteLista(@Header("Authorization") String s, @Path("id") Long id);

    @DELETE("turno-id/{id}")
    Call<Void> eliminarTurnoId(@Header("Authorization") String token, @Path("id") Long id);

    @POST("registrarturno")
    Call<Turno> crearTurno(@Header("Authorization") String s, @Body Turno turno);

    /**
     * PACIENTESS
     */
    @GET("paciente-id-usuario/{id}")
    Call<Paciente> getPacienteUsuarioId(@Header("Authorization") String s, @Path("id") Integer id);
    @PUT("paciente-id-usuario/{id}")
    Call<Paciente> updatePaciente(@Header("Authorization") String s, @Path("id") Integer id, @Body PacienteEditar paciente);

    /**
     * PAGOS
     */
    @POST("Pagar/")
    Call<Pago> crearPago(@Header("Authorization") String s, @Body Pago pago);

}
