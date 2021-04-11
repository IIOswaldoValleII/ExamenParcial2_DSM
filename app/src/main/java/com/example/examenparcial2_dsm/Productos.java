package com.example.examenparcial2_dsm;

public class Productos {
    public String id;
    public String nombre;
    public String descripcion;
    public double precio;
    public int stock;

    public Productos(){

    }

    public Productos(String id, String nombre, String descripcion, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    public String toString(){
        final String s = this.nombre + " - $" + this.precio;
        return s;
    }
}


