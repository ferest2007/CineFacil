/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ucompensar.codigo;

/**
 *
 * @author 
 */
// Clase modelo que representa a un usuario registrado en el sistema de cine
public class Usuario {
    // Atributos privados para guardar el nombre y el numero de cedula del usuario
    private String nombre;
    private String cedula;

    // Constructor de la clase Usuario que recibe e inicializa el nombre y la cedula
    public Usuario(String nombre, String cedula) {
        this.nombre = nombre;
        this.cedula = cedula;
    }

    // Metodo getter que retorna el nombre del usuario
    public String getNombre() {
        return nombre;
    }

    // Metodo setter que asigna o actualiza el nombre del usuario
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Metodo getter que retorna la cedula del usuario
    public String getCedula() {
        return cedula;
    }

    // Metodo setter que asigna o actualiza la cedula del usuario
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
}