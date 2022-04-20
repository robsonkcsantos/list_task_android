package com.example.listatarefas.model;

import java.io.Serializable;

public class Task implements Serializable {

    private static int contID = 1;
    private String titulo;
    private String description;
    private int id;

    public Task(String t, String d) {
        this.titulo = t;
        this.description = d;
        this.id = contID++;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return this.id + ": " + this.titulo + " / " + this.description;
    }
}
