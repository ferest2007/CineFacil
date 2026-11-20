# CineFácil

Aplicación de escritorio desarrollada en Java para gestionar la reserva de entradas de cine y pedidos de confitería, integrando interfaz gráfica con Swing, validaciones de seguridad y persistencia de comprobantes en archivos locales.

## Funcionalidades Principales

* **Registro e Inicio de Sesión:** Validación numérica de cédula mediante expresiones regulares y control de usuarios duplicados.
* **Consulta de Cartelera:** Visualización de películas disponibles con sinopsis, duración, precio de boleta y horarios.
* **Mapa Interactivo de Sala:** Cuadrícula visual (4x5) con botones dinámicos por color (verde: libre, amarillo: seleccionado, rojo: ocupado) y bloqueo temporal automático de 5 minutos.
* **Módulo de Confitería:** Selección acumulativa de combos y bebidas integrados al cálculo total de la reserva.
* **Comprobantes Locales:** Generación automática de recibos con identificador único (`UUID`) almacenados en archivos `.txt` organizados por carpetas según la fecha (`comprobantes_cinefacil/AAAA-MM-DD/`).
* **Panel Administrativo:** Opción para agregar nuevas películas, horarios y tarifas a la cartelera en tiempo de ejecución.

## Estructura del Proyecto (`edu.ucompensar.codigo`)

* **`Main.java`**: Punto de entrada que inicializa el controlador y lanza la interfaz gráfica.
* **`GestorCine.java`**: Controlador principal encargado de administrar usuarios, cartelera, menú de comida, validaciones y escritura de archivos.
* **`InterfazJOption.java`**: Capa de vista construida con `JOptionPane` y paneles Swing que guía el flujo completo de reserva y administración.
* **`Usuario.java`**: Modelo de datos del cliente (nombre y cédula).
* **`Pelicula.java`**: Modelo de película que incluye la matriz bidimensional de disponibilidad de asientos.
* **`ProductoComida.java`**: Modelo de los ítems de confitería y sus precios.
* **`Reserva.java`**: Modelo que consolida asientos, productos, cálculo de totales y formato del comprobante de pago.

## Tecnologías y Ejecución

* **Lenguaje:** Java (JDK 8 o superior)
* **Interfaz y Utilidades:** Java Swing (`JOptionPane`), `java.io`, `java.time`
* **IDE:** Apache NetBeans

Para ejecutarlo, clona el repositorio, abre la carpeta del proyecto en NetBeans y ejecuta la clase `Main.java`.
