/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ucompensar.codigo;

/**
 *
 * @author 
 */
// Clase modelo que representa un producto de comida o bebida de la confiteria
public class ProductoComida {
    // Atributos privados del producto
    private String nombre;
    private double precio;

    // Constructor que inicializa el nombre y el precio del producto
    public ProductoComida(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    // Metodo getter que devuelve el nombre del producto
    public String getNombre() {
        return nombre;
    }

    // Metodo getter que devuelve el precio del producto
    public double getPrecio() {
        return precio;
    }

    // Metodo toString sobrescrito para mostrar el nombre y precio en los cuadros de opcion
    @Override
    public String toString() {
        return nombre + " ($" + String.format("%.0f", precio) + ")";
    }
}
