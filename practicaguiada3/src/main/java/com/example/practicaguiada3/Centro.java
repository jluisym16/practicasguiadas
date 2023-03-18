package com.example.practicaguiada3;

public class Centro {
    private final String nombre, direccion;
    private final int imagen;

    public Centro(String nombre, String direccion, int imagen) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getImagen() {
        return imagen;
    }
}
