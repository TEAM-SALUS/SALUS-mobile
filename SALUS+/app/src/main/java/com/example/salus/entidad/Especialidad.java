package com.example.salus.entidad;

public class Especialidad {
    private Long id;
    private String nombre;
    private double precio;
    private String duracion;
    private String descripcion;
    private String foto;
    private boolean is_active;

    public Especialidad() {
    }

    public Especialidad(Long id, String nombre, double precio, String duracion, String descripcion, String foto, boolean is_active) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.duracion = duracion;
        this.descripcion = descripcion;
        this.foto = foto;
        this.is_active = is_active;
    }

    // Getters y setters
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        return "Especialidad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", duracion='" + duracion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", foto='" + foto + '\'' +
                ", is_active=" + is_active +
                '}';
    }
}
