/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ucompensar.codigo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author 
 */
// Clase modelo que consolida los datos de una reserva y formatea el comprobante de pago
public class Reserva {
    // Atributos privados de la reserva
    private String codigoReserva;
    private Usuario usuario;
    private Pelicula pelicula;
    private String funcionSeleccionada;
    private List<String> asientosSeleccionados;
    private List<ProductoComida> productosComida;
    private double totalPagado;
    private LocalDateTime fechaHoraReserva;

    // Constructor que crea una reserva con codigo unico aleatorio y calcula el costo total
    public Reserva(Usuario usuario, Pelicula pelicula, String funcionSeleccionada,
                   List<String> asientosSeleccionados, List<ProductoComida> productosComida) {
        // Generar un identificador unico corto para la reserva
        this.codigoReserva = "CF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.usuario = usuario;
        this.pelicula = pelicula;
        this.funcionSeleccionada = funcionSeleccionada;
        this.asientosSeleccionados = asientosSeleccionados;
        this.productosComida = productosComida;
        this.fechaHoraReserva = LocalDateTime.now();
        this.totalPagado = calcularTotal();
    }

    // Metodo privado que suma el valor de las entradas y el valor de la comida
    private double calcularTotal() {
        double totalEntradas = asientosSeleccionados.size() * pelicula.getPrecioEntrada();
        double totalComida = 0;
        for (ProductoComida producto : productosComida) {
            totalComida += producto.getPrecio();
        }
        return totalEntradas + totalComida;
    }

    // Metodo getter para obtener el codigo de la reserva
    public String getCodigoReserva() {
        return codigoReserva;
    }

    // Metodo getter para obtener el total calculado a pagar
    public double getTotalPagado() {
        return totalPagado;
    }

    // Metodo que construye una cadena de texto estructurada con toda la informacion del recibo
    public String generarTextoComprobante() {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        sb.append("              COMPROBANTE DE RESERVA              \n");
        sb.append("                    CINEFACIL                     \n");
        sb.append("==================================================\n");
        sb.append("Codigo de Reserva : ").append(codigoReserva).append("\n");
        sb.append("Fecha y Hora      : ").append(fechaHoraReserva.format(formatoFecha)).append("\n");
        sb.append("--------------------------------------------------\n");
        sb.append("DATOS DEL CLIENTE\n");
        sb.append("Nombre            : ").append(usuario.getNombre()).append("\n");
        sb.append("Cedula            : ").append(usuario.getCedula()).append("\n");
        sb.append("--------------------------------------------------\n");
        sb.append("DETALLES DE LA FUNCION\n");
        sb.append("Pelicula          : ").append(pelicula.getTitulo()).append("\n");
        sb.append("Horario           : ").append(funcionSeleccionada).append("\n");
        sb.append("Asientos          : ").append(String.join(", ", asientosSeleccionados)).append("\n");
        sb.append("Subtotal Entradas : $").append(String.format("%.0f", asientosSeleccionados.size() * pelicula.getPrecioEntrada())).append("\n");
        sb.append("--------------------------------------------------\n");
        sb.append("CONFITERIA Y BEBIDAS\n");
        if (productosComida.isEmpty()) {
            sb.append("Ningun producto seleccionado.\n");
        } else {
            for (ProductoComida prod : productosComida) {
                sb.append("- ").append(prod.getNombre()).append(" : $").append(String.format("%.0f", prod.getPrecio())).append("\n");
            }
        }
        sb.append("--------------------------------------------------\n");
        sb.append("TOTAL PAGADO      : $").append(String.format("%.0f", totalPagado)).append("\n");
        sb.append("==================================================\n");
        sb.append("          Gracias por su compra en Cinefacil      \n");
        sb.append("==================================================\n");
        return sb.toString();
    }
}