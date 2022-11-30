package com.example.aether_app;

public class Usuario {

    private String nombre;
    private String correo;
    private int avatar;

    public Usuario() {
    }

    public Usuario(String correo) {
        this.correo = correo;
    }

    public Usuario(String nombre, String correo, int avatar) {
        this.nombre = nombre;
        this.correo = correo;
        this.avatar = avatar;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public int getAvatar() {
        return avatar;
    }
}