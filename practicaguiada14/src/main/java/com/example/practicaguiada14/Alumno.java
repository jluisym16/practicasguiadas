package com.example.practicaguiada14;

public class Alumno {
    private String nombre, apellidos, curso;

    public Alumno(String nombre, String apellidos, String curso) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.curso = curso;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
