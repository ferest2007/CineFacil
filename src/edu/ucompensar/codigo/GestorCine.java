/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ucompensar.codigo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 *
 * @author 
 */
// Clase controladora encargada de gestionar usuarios, cartelera, menu y archivos locales
public class GestorCine {
    // Listas privadas en memoria para almacenar los registros del sistema
    private List<Usuario> usuariosRegistrados;
    private List<Pelicula> cartelera;
    private List<ProductoComida> menuComida;
    private List<Reserva> historialReservas;

    // Constructor que inicializa las colecciones y carga los datos iniciales por defecto
    public GestorCine() {
        this.usuariosRegistrados = new ArrayList<>();
        this.cartelera = new ArrayList<>();
        this.menuComida = new ArrayList<>();
        this.historialReservas = new ArrayList<>();
        cargarDatosIniciales();
    }

    // Metodo privado para poblar el sistema con un usuario, peliculas y comida iniciales
    private void cargarDatosIniciales() {
        // Usuario de prueba precargado en el sistema
        usuariosRegistrados.add(new Usuario("Carlos Hurtado", "123456789"));

        // Peliculas iniciales en cartelera
        cartelera.add(new Pelicula(
            "Batman: El Caballero de la Noche",
            "Batman, el teniente Gordon y el fiscal Harvey Dent luchan contra el crimen organizado en Gotham hasta que aparece el Joker.",
            169,
            15000,
            Arrays.asList("2026-10-10 16:00", "2026-10-10 19:30", "2026-10-11 21:00")
        ));

        cartelera.add(new Pelicula(
            "Avengers: Endgame",
            "Tras los eventos devastadores de Infinity War, los Vengadores restantes se reunen una vez mas para revertir las acciones de Thanos.",
            125,
            12000,
            Arrays.asList("2026-10-10 14:00", "2026-10-10 18:00", "2026-10-11 20:15")
        ));

        cartelera.add(new Pelicula(
           "Spider-Man: Sin Camino a Casa",
            "Con la identidad de Spider-Man revelada, Peter Parker pide ayuda al Doctor Strange, desatando una fractura en el multiverso.",
            110,
            13500,
            Arrays.asList("2026-10-10 15:30", "2026-10-11 17:00")
        ));

        // Productos iniciales del menu de comida
        menuComida.add(new ProductoComida("Combo Crispetas Medianas + Gaseosa", 18000));
        menuComida.add(new ProductoComida("Combo Crispetas Grandes + 2 Gaseosas", 28000));
        menuComida.add(new ProductoComida("Perro Caliente Clasico", 12000));
        menuComida.add(new ProductoComida("Nachos con Queso Cheddar", 14000));
        menuComida.add(new ProductoComida("Botella de Agua 600ml", 5000));
    }

    // Metodo que valida que la cedula contenga unicamente caracteres numericos del 0 al 9
    public boolean esCedulaValida(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return false;
        }
        return cedula.trim().matches("^[0-9]+$");
    }

    // Metodo que registra un nuevo usuario verificando que la cedula no este repetida
    public boolean registrarUsuario(String nombre, String cedula) {
        if (!esCedulaValida(cedula) || nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        for (Usuario u : usuariosRegistrados) {
            if (u.getCedula().equals(cedula.trim())) {
                return false; // La cedula ya existe en la lista
            }
        }
        usuariosRegistrados.add(new Usuario(nombre.trim(), cedula.trim()));
        return true;
    }

    // Metodo que busca y retorna un usuario si su nombre y cedula coinciden
    public Usuario iniciarSesion(String nombre, String cedula) {
        if (!esCedulaValida(cedula) || nombre == null || nombre.trim().isEmpty()) {
            return null;
        }
        for (Usuario u : usuariosRegistrados) {
            if (u.getCedula().equals(cedula.trim()) && u.getNombre().equalsIgnoreCase(nombre.trim())) {
                return u;
            }
        }
        return null;
    }

    // Metodo getter que retorna la lista de peliculas disponibles
    public List<Pelicula> getCartelera() {
        return cartelera;
    }

    // Metodo que agrega una nueva pelicula a la cartelera desde el modo administrador
    public void agregarPelicula(Pelicula nuevaPelicula) {
        cartelera.add(nuevaPelicula);
    }

    // Metodo getter que retorna la lista de productos de comida
    public List<ProductoComida> getMenuComida() {
        return menuComida;
    }

    // Metodo que guarda la reserva en memoria y crea un archivo de texto organizado por fecha
    public String guardarComprobanteLocal(Reserva reserva) {
        historialReservas.add(reserva);

        // Obtener la fecha actual en formato AAAA-MM-DD para nombrar la subcarpeta
        String fechaCarpeta = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String rutaCarpeta = "comprobantes_cinefacil" + File.separator + fechaCarpeta;

        File directorio = new File(rutaCarpeta);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        // Crear el archivo con extension .txt usando el codigo de la reserva
        File archivoComprobante = new File(directorio, reserva.getCodigoReserva() + ".txt");

        try (FileWriter escritor = new FileWriter(archivoComprobante)) {
            escritor.write(reserva.generarTextoComprobante());
            return archivoComprobante.getAbsolutePath();
        } catch (IOException e) {
            return null;
        }
    }
}