/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ucompensar.codigo;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author 
 */
// Clase modelo que almacena los datos de una pelicula y el mapa de sus asientos
public class Pelicula {
    // Atributos privados de la pelicula
    private String titulo;
    private String sinopsis;
    private int duracionMinutos;
    private double precioEntrada;
    private List<String> funcionesDisponibles;
    // Matriz entera de 4 filas por 5 columnas para representar las butacas
    // 0 = Libre, 1 = Ocupado permanentemente, 2 = Seleccionado temporalmente
    private int[][] estadoAsientos;

    // Constructor que inicializa la informacion de la pelicula y deja todos los asientos libres
    public Pelicula(String titulo, String sinopsis, int duracionMinutos, double precioEntrada, List<String> funcionesDisponibles) {
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.duracionMinutos = duracionMinutos;
        this.precioEntrada = precioEntrada;
        this.funcionesDisponibles = new ArrayList<>(funcionesDisponibles);
        // Crear matriz de 4 filas y 5 columnas inicializada en cero (libre)
        this.estadoAsientos = new int[4][5];
    }

    // Metodo getter para obtener el titulo de la pelicula
    public String getTitulo() {
        return titulo;
    }

    // Metodo getter para obtener la sinopsis de la pelicula
    public String getSinopsis() {
        return sinopsis;
    }

    // Metodo getter para obtener la duracion en minutos de la pelicula
    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    // Metodo getter para obtener el precio de cada boleta
    public double getPrecioEntrada() {
        return precioEntrada;
    }

    // Metodo getter para obtener la lista de horarios y fechas disponibles
    public List<String> getFuncionesDisponibles() {
        return funcionesDisponibles;
    }

    // Metodo getter para obtener la matriz de estados de los asientos
    public int[][] getEstadoAsientos() {
        return estadoAsientos;
    }

    // Metodo setter para modificar el estado de un asiento segun su fila y columna
    public void setEstadoAsiento(int fila, int columna, int estado) {
        this.estadoAsientos[fila][columna] = estado;
    }

    // Metodo toString sobrescrito para mostrar el titulo y duracion en listas desplegables
    @Override
    public String toString() {
        return titulo + " (" + duracionMinutos + " min) - $" + String.format("%.0f", precioEntrada);
    }
}
