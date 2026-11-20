/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ucompensar.codigo;

/**
 *
 * @author 
 */
// Clase principal que contiene el punto de entrada para ejecutar el programa
public class Main {
    // Metodo main que instancia el controlador y lanza la interfaz con JOptionPane
    public static void main(String[] args) {
        // Crear instancia del gestor con los datos iniciales del cine
        GestorCine gestor = new GestorCine();
        // Crear instancia de la interfaz grafica basada en cuadros de dialogo JOptionPane
        InterfazJOption interfaz = new InterfazJOption(gestor);
        // Iniciar el flujo principal de la aplicacion
        interfaz.iniciarAplicacion();
    }
}
