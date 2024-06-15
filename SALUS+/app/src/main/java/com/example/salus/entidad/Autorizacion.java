package com.example.salus.entidad;

public class Autorizacion {
    private String expiracy;
    private String token;
    //private int id;
    public Autorizacion() {
    }
    public Autorizacion(String expiracy, String token) {
        this.expiracy = expiracy;
        this.token = token;
        //this.id = id;
    }
    public String getExpiracy() {
        return expiracy;
    }
    public void setExpiracy(String expiracy) {
        this.expiracy = expiracy;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }



    @Override
    public String toString() {
        return "Autorizacion{" +
                "expiracy='" + expiracy + '\''+
                ", token='" + token + '\'' +
                '}';
    }
}
