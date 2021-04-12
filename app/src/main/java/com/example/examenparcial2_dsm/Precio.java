package com.example.examenparcial2_dsm;

public class Precio {
    public String id;
    public String nombre;
    public String descripcion;
    public double precio;
    public int stock;

    public Precio(){

    }

    public Precio(String id, String nombre, String descripcion, double precio, int stock) {
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
