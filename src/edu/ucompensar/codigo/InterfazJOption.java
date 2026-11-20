/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ucompensar.codigo;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
/**
 *
 * @author 
 */
// Clase encargada de gestionar toda la interaccion grafica mediante cuadros de dialogo JOptionPane
public class InterfazJOption {
    // Referencia al controlador principal del sistema
    private GestorCine gestorCine;
    // Referencia al usuario que tiene la sesion iniciada actualmente
    private Usuario usuarioActual;

    // Constructor que recibe el gestor del cine
    public InterfazJOption(GestorCine gestorCine) {
        this.gestorCine = gestorCine;
    }

    // Metodo principal que ejecuta el ciclo de menus de acceso de la aplicacion
    public void iniciarAplicacion() {
        boolean ejecutando = true;

        while (ejecutando) {
            String[] opcionesInicio = {"Iniciar Sesion", "Registrarse", "Salir"};
            int seleccion = JOptionPane.showOptionDialog(
                null,
                "Bienvenido al Sistema de Reservas Cinefacil.\nSeleccione una opcion para continuar:",
                "Cinefacil - Menu Principal",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opcionesInicio,
                opcionesInicio[0]
            );

            if (seleccion == 0) {
                flujoIniciarSesion();
            } else if (seleccion == 1) {
                flujoRegistrarUsuario();
            } else {
                ejecutando = false;
                JOptionPane.showMessageDialog(null, "Gracias por utilizar Cinefacil. Hasta pronto.");
            }
        }
    }

    // Metodo que solicita nombre y cedula mediante JOptionPane para iniciar sesion
    private void flujoIniciarSesion() {
        String nombre = JOptionPane.showInputDialog(null, "Ingrese su nombre completo:", "Iniciar Sesion", JOptionPane.QUESTION_MESSAGE);
        if (nombre == null) return; // El usuario presiono cancelar

        String cedula = JOptionPane.showInputDialog(null, "Ingrese su numero de cedula (solo numeros):", "Iniciar Sesion", JOptionPane.QUESTION_MESSAGE);
        if (cedula == null) return;

        // Validar seguridad de la cedula para evitar caracteres especiales
        if (!gestorCine.esCedulaValida(cedula)) {
            JOptionPane.showMessageDialog(null, "Error: La cedula debe contener unicamente digitos numericos.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = gestorCine.iniciarSesion(nombre, cedula);
        if (usuario != null) {
            this.usuarioActual = usuario;
            JOptionPane.showMessageDialog(null, "Acceso concedido. Bienvenido, " + usuarioActual.getNombre() + ".", "Sesion Iniciada", JOptionPane.INFORMATION_MESSAGE);
            menuUsuarioAutenticado();
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado o datos incorrectos.", "Error de Acceso", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Metodo que solicita datos mediante JOptionPane para crear una cuenta nueva
    private void flujoRegistrarUsuario() {
        String nombre = JOptionPane.showInputDialog(null, "Ingrese su nombre completo para el registro:", "Registro de Usuario", JOptionPane.QUESTION_MESSAGE);
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre no puede estar vacio.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cedula = JOptionPane.showInputDialog(null, "Ingrese su numero de cedula (solo numeros):", "Registro de Usuario", JOptionPane.QUESTION_MESSAGE);
        if (cedula == null) return;

        if (!gestorCine.esCedulaValida(cedula)) {
            JOptionPane.showMessageDialog(null, "Error: La cedula solo puede contener numeros sin espacios ni simbolos.", "Error de Seguridad", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean registrado = gestorCine.registrarUsuario(nombre, cedula);
        if (registrado) {
            JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente. Ya puede iniciar sesion.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Error: Ya existe un usuario registrado con esa cedula.", "Cedula Duplicada", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Metodo que muestra las opciones disponibles una vez que el usuario ha iniciado sesion
    private void menuUsuarioAutenticado() {
        boolean enSesion = true;

        while (enSesion) {
            String[] opcionesMenu = {
                "Ver Cartelera y Reservar",
                "Administracion (Agregar Pelicula)",
                "Cerrar Sesion"
            };

            int opcion = JOptionPane.showOptionDialog(
                null,
                "Usuario activo: " + usuarioActual.getNombre() + " (CC: " + usuarioActual.getCedula() + ")\nSeleccione la operacion que desea realizar:",
                "Cinefacil - Panel de Usuario",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcionesMenu,
                opcionesMenu[0]
            );

            if (opcion == 0) {
                flujoCompletoReserva();
            } else if (opcion == 1) {
                flujoAgregarPeliculaAdmin();
            } else {
                this.usuarioActual = null;
                enSesion = false;
            }
        }
    }

    // Metodo que guia paso a paso la reserva: pelicula, funcion, sala con botones, comida y pago
    private void flujoCompletoReserva() {
        // Paso 1: Seleccionar Pelicula desde un cuadro desplegable JOptionPane
        List<Pelicula> listaPeliculas = gestorCine.getCartelera();
        Pelicula[] arregloPeliculas = listaPeliculas.toArray(new Pelicula[0]);

        Pelicula peliculaElegida = (Pelicula) JOptionPane.showInputDialog(
            null,
            "Seleccione la pelicula de la cartelera:",
            "Paso 1: Cartelera",
            JOptionPane.QUESTION_MESSAGE,
            null,
            arregloPeliculas,
            arregloPeliculas[0]
        );

        if (peliculaElegida == null) return;

        // Mostrar sinopsis de la pelicula seleccionada
        JOptionPane.showMessageDialog(
            null,
            "Titulo: " + peliculaElegida.getTitulo() + "\nDuracion: " + peliculaElegida.getDuracionMinutos() + " minutos\nSinopsis: " + peliculaElegida.getSinopsis(),
            "Informacion de la Pelicula",
            JOptionPane.INFORMATION_MESSAGE
        );

        // Paso 2: Seleccionar Fecha y Horario
        String[] arregloFunciones = peliculaElegida.getFuncionesDisponibles().toArray(new String[0]);
        String funcionElegida = (String) JOptionPane.showInputDialog(
            null,
            "Seleccione la fecha y horario de la funcion:",
            "Paso 2: Seleccion de Funcion",
            JOptionPane.QUESTION_MESSAGE,
            null,
            arregloFunciones,
            arregloFunciones[0]
        );

        if (funcionElegida == null) return;

        // Paso 3: Seleccionar Asientos mediante un mapa visual incrustado en JOptionPane
        List<String> asientosSeleccionados = seleccionarAsientosConMapaVisual(peliculaElegida);
        if (asientosSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No selecciono ningun asiento. Reserva cancelada.", "Reserva Cancelada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Paso 4: Agregar Comida y Bebidas opcionales
        List<ProductoComida> carritoComida = seleccionarComidaOpcional();

        // Paso 5: Resumen y Simulacion de Pago
        Reserva reservaPreliminar = new Reserva(usuarioActual, peliculaElegida, funcionElegida, asientosSeleccionados, carritoComida);

        int confirmarPago = JOptionPane.showConfirmDialog(
            null,
            "Resumen de su pedido:\n" +
            "Pelicula: " + peliculaElegida.getTitulo() + "\n" +
            "Funcion: " + funcionElegida + "\n" +
            "Asientos: " + String.join(", ", asientosSeleccionados) + "\n" +
            "Productos de comida: " + carritoComida.size() + "\n" +
            "TOTAL A PAGAR: $" + String.format("%.0f", reservaPreliminar.getTotalPagado()) + "\n\n" +
            "Desea confirmar y simular el pago ahora?",
            "Paso 5: Confirmacion de Pago",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmarPago == JOptionPane.YES_OPTION) {
            // Confirmar asientos como ocupados definitivamente (estado 1)
            confirmarAsientosDefinitivos(peliculaElegida);
            String rutaArchivo = gestorCine.guardarComprobanteLocal(reservaPreliminar);

            // Mostrar comprobante final en un JTextArea dentro de JOptionPane
            JTextArea areaTexto = new JTextArea(reservaPreliminar.generarTextoComprobante());
            areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
            areaTexto.setEditable(false);

            JOptionPane.showMessageDialog(
                null,
                new JScrollPane(areaTexto),
                "Pago Exitoso - Guardado en: " + rutaArchivo,
                JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            // Liberar asientos bloqueados temporalmente si el usuario cancela el pago
            liberarAsientosTemporales(peliculaElegida);
            JOptionPane.showMessageDialog(null, "Pago cancelado. Los asientos han sido liberados.", "Operacion Cancelada", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Metodo que construye una cuadricula visual de botones con colores y la muestra en JOptionPane
    private List<String> seleccionarAsientosConMapaVisual(Pelicula pelicula) {
        List<String> seleccionados = new ArrayList<>();
        long tiempoInicio = System.currentTimeMillis();
        final long TIEMPO_MAXIMO_MS = 5 * 60 * 1000; // 5 minutos en milisegundos

        // Panel contenedor con pantalla y cuadricula de asientos
        JPanel panelPrincipal = new JPanel(new java.awt.BorderLayout(10, 10));
        JLabel lblPantalla = new JLabel("PANTALLA (Verde: Libre | Amarillo: Seleccionado | Rojo: Ocupado)", SwingConstants.CENTER);
        lblPantalla.setOpaque(true);
        lblPantalla.setBackground(Color.DARK_GRAY);
        lblPantalla.setForeground(Color.WHITE);
        panelPrincipal.add(lblPantalla, java.awt.BorderLayout.NORTH);

        JPanel panelCuadricula = new JPanel(new GridLayout(4, 5, 6, 6));
        char[] letras = {'A', 'B', 'C', 'D'};
        int[][] matriz = pelicula.getEstadoAsientos();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                String codigo = letras[i] + String.valueOf(j + 1);
                JButton btnAsiento = new JButton(codigo);
                final int fila = i;
                final int col = j;

                if (matriz[i][j] == 1) {
                    btnAsiento.setBackground(Color.RED);
                    btnAsiento.setEnabled(false);
                } else {
                    btnAsiento.setBackground(Color.GREEN);
                }

                // Evento al hacer clic en cada boton del mapa dentro del JOptionPane
                btnAsiento.addActionListener(e -> {
                    if (pelicula.getEstadoAsientos()[fila][col] == 0) {
                        pelicula.setEstadoAsiento(fila, col, 2); // Bloqueo temporal
                        btnAsiento.setBackground(Color.YELLOW);
                        seleccionados.add(codigo);
                    } else if (pelicula.getEstadoAsientos()[fila][col] == 2) {
                        pelicula.setEstadoAsiento(fila, col, 0); // Liberar
                        btnAsiento.setBackground(Color.GREEN);
                        seleccionados.remove(codigo);
                    }
                });

                panelCuadricula.add(btnAsiento);
            }
        }

        panelPrincipal.add(panelCuadricula, java.awt.BorderLayout.CENTER);

        String[] botonesDialogo = {"Confirmar Asientos", "Cancelar"};
        int respuesta = JOptionPane.showOptionDialog(
            null,
            panelPrincipal,
            "Paso 3: Mapa de Sala (Bloqueo maximo de 5 minutos)",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            botonesDialogo,
            botonesDialogo[0]
        );

        // Verificar si excedio el tiempo limite de 5 minutos
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoInicio;
        if (tiempoTranscurrido > TIEMPO_MAXIMO_MS) {
            liberarAsientosTemporales(pelicula);
            seleccionados.clear();
            JOptionPane.showMessageDialog(null, "El tiempo de bloqueo de 5 minutos expiro. Los asientos fueron liberados.", "Tiempo Expirado", JOptionPane.WARNING_MESSAGE);
            return seleccionados;
        }

        if (respuesta != 0) {
            liberarAsientosTemporales(pelicula);
            seleccionados.clear();
        }

        return seleccionados;
    }

    // Metodo que permite agregar productos de comida en bucle mediante JOptionPane
    private List<ProductoComida> seleccionarComidaOpcional() {
        List<ProductoComida> carrito = new ArrayList<>();
        ProductoComida[] menu = gestorCine.getMenuComida().toArray(new ProductoComida[0]);

        int agregar = JOptionPane.showConfirmDialog(
            null,
            "Desea agregar comida o bebidas a su reserva?",
            "Paso 4: Modulo de Confiteria",
            JOptionPane.YES_NO_OPTION
        );

        while (agregar == JOptionPane.YES_OPTION) {
            ProductoComida prodElegido = (ProductoComida) JOptionPane.showInputDialog(
                null,
                "Seleccione un producto del menu:",
                "Menu de Comida",
                JOptionPane.QUESTION_MESSAGE,
                null,
                menu,
                menu[0]
            );

            if (prodElegido != null) {
                carrito.add(prodElegido);
                agregar = JOptionPane.showConfirmDialog(
                    null,
                    "Producto agregado: " + prodElegido.getNombre() + ".\nDesea agregar otro producto?",
                    "Continuar Pedido",
                    JOptionPane.YES_NO_OPTION
                );
            } else {
                break;
            }
        }

        return carrito;
    }

    // Metodo que cambia los asientos bloqueados temporalmente (2) a ocupados (1)
    private void confirmarAsientosDefinitivos(Pelicula pelicula) {
        int[][] matriz = pelicula.getEstadoAsientos();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (matriz[i][j] == 2) {
                    pelicula.setEstadoAsiento(i, j, 1);
                }
            }
        }
    }

    // Metodo que libera los asientos bloqueados temporalmente (2) devolviendolos a libre (0)
    private void liberarAsientosTemporales(Pelicula pelicula) {
        int[][] matriz = pelicula.getEstadoAsientos();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (matriz[i][j] == 2) {
                    pelicula.setEstadoAsiento(i, j, 0);
                }
            }
        }
    }

    // Metodo administrativo basico mediante JOptionPane para agregar peliculas a la cartelera
    private void flujoAgregarPeliculaAdmin() {
        try {
            String titulo = JOptionPane.showInputDialog(null, "Ingrese el titulo de la nueva pelicula:", "Administracion", JOptionPane.QUESTION_MESSAGE);
            if (titulo == null || titulo.trim().isEmpty()) return;

            String sinopsis = JOptionPane.showInputDialog(null, "Ingrese la sinopsis de la pelicula:", "Administracion", JOptionPane.QUESTION_MESSAGE);
            if (sinopsis == null || sinopsis.trim().isEmpty()) return;

            String duracionStr = JOptionPane.showInputDialog(null, "Ingrese la duracion en minutos (ej. 120):", "Administracion", JOptionPane.QUESTION_MESSAGE);
            if (duracionStr == null) return;
            int duracion = Integer.parseInt(duracionStr.trim());

            String precioStr = JOptionPane.showInputDialog(null, "Ingrese el precio de la entrada (ej. 14000):", "Administracion", JOptionPane.QUESTION_MESSAGE);
            if (precioStr == null) return;
            double precio = Double.parseDouble(precioStr.trim());

            String funcion = JOptionPane.showInputDialog(null, "Ingrese la fecha y hora de la funcion (ej. 2026-10-15 19:00):", "Administracion", JOptionPane.QUESTION_MESSAGE);
            if (funcion == null || funcion.trim().isEmpty()) return;

            List<String> funciones = new ArrayList<>();
            funciones.add(funcion.trim());

            Pelicula nuevaPelicula = new Pelicula(titulo.trim(), sinopsis.trim(), duracion, precio, funciones);
            gestorCine.agregarPelicula(nuevaPelicula);

            JOptionPane.showMessageDialog(null, "Pelicula agregada correctamente a la cartelera.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: La duracion y el precio deben ser valores numericos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}
