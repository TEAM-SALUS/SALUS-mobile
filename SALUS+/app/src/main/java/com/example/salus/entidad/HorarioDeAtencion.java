package com.example.salus.entidad;

public class HorarioDeAtencion {
    private Long id;
    private String dia_de_la_semana;
    private String hora_entrada;
    private String hora_salida;
    private Boolean is_active;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDia_de_la_semana() {
        return dia_de_la_semana;
    }

    public void setDia_de_la_semana(String dia_de_la_semana) {
        this.dia_de_la_semana = dia_de_la_semana;
    }

    public String getHora_entrada() {
        return hora_entrada;
    }

    public void setHora_entrada(String hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    public String toString() {
        return "HorarioDeAtencion{" +
                "id=" + id +
                ", dia_de_la_semana='" + dia_de_la_semana + '\'' +
                ", hora_entrada='" + hora_entrada + '\'' +
                ", hora_salida='" + hora_salida + '\'' +
                ", is_active=" + is_active +
                '}';
    }
}
