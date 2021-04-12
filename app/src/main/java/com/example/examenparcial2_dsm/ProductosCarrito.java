package com.example.examenparcial2_dsm;

public class ProductosCarrito {
    public String id;
    public String nombre;
    public double precio;
    public int cantidadpedida;

    public ProductosCarrito(){

    }

    public ProductosCarrito(String id, String nombre, double precio, int cantidadpedida) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadpedida = cantidadpedida;
    }
    public String toString(){
        final String s = this.nombre + " - $" + this.precio + " Cantidad:" +this.cantidadpedida;
        return s;
    }

}


