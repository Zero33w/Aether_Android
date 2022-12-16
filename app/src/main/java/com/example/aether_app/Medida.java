package com.example.aether_app;

public class Medida {
    private String idMedicion;
    private String idSensor;
    private String valorMedicion;
    private long momentoMedicion;
    private String latitud;
    private String longitud;

    public Medida(){

    }

    public Medida(String idMedicion, String idSensor, String valorMedicion, long momentoMedicion, String latitud, String longitud) {
        this.idMedicion = idMedicion;
        this.idSensor = idSensor;
        this.valorMedicion = valorMedicion;
        this.momentoMedicion = momentoMedicion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public long getMomentoMedicion() {
        return momentoMedicion;
    }
}
