package com.example.salus.entidad;

public class ObraSocial {
    private Long id;
    private String nombre;
    private String foto;
    private boolean is_active;

    public ObraSocial() {
    }

    public ObraSocial(Long id, String nombre, String foto, boolean is_active) {
        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
        this.is_active = is_active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    public String toString() {
        return "ObraSocial{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", foto='" + foto + '\'' +
                ", is_active=" + is_active +
                '}';
    }
}
