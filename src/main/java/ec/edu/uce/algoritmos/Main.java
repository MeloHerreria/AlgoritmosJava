
package ec.edu.uce.algoritmos;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Scanner;

public final class Main {

    private static final Path SALIDA_POR_DEFECTO =
            Path.of("resultados", "tablas");

    private Main() {
    }

    public static void main(String[] args) throws IOException {

        PrintStream out = System.out;
        Path salida = SALIDA_POR_DEFECTO;

        boolean ejecutarTodo = false;
        int actividad = 0;

        // Procesar argumentos
        for (int i = 0; i < args.length; i++) {

            switch (args[i]) {

                case "--todo" -> ejecutarTodo = true;

                case "--actividad" ->
                        actividad = i + 1 < args.length
                                ? parsear(args[++i]) : -1;

                case "--salida" -> {
                    if (i + 1 < args.length) {
                        salida = Path.of(args[++i]);
                    }
                }

                default -> {
                    out.println("Argumento no reconocido: " + args[i]);
                    return;
                }
            }
        }

        if (ejecutarTodo) {
            ejecutarTodas(out, salida);

        } else if (actividad != 0) {

            if (!ejecutarActividad(actividad, out, salida)) {
                out.println("Actividad no válida. Use un número del 1 al 4.");
            }

        } else {
            mostrarMenu(out, salida);
        }
    }

    // =========================================
    // MENÚ PRINCIPAL
    // =========================================

    private static void mostrarMenu(
            PrintStream out, Path salida) throws IOException {

        Scanner teclado = new Scanner(System.in);

        while (true) {

            out.println();
            out.println("==================================================");
            out.println("       TAREA GRUPAL INTEGRADORA - ALGORITMOS");
            out.println("       UNIVERSIDAD CENTRAL DEL ECUADOR");
            out.println("==================================================");

            out.println("1. Actividad 1 - Análisis de complejidad");
            out.println("2. Actividad 2 - Iterativo vs recursivo");
            out.println("3. Actividad 3 - Algoritmos de ordenación");
            out.println("4. Actividad 4 - Benchmark y comparación experimental");
            out.println("5. Ejecutar todas las actividades");
            out.println("6. Contexto académico - Registros y calificaciones");
            out.println("0. Salir");

            out.println("--------------------------------------------------");
            out.print("Seleccione una opción: ");

            if (!teclado.hasNextLine()) {
                return;
            }

            String opcion = teclado.nextLine().trim();

            switch (opcion) {

                case "0" -> {
                    out.println("Programa finalizado.");
                    return;
                }

                case "5" -> ejecutarTodas(out, salida);

                case "6" -> {
                    encabezado(out, "CONTEXTO ACADÉMICO");
                    Demostraciones.contextoAcademico(out);
                }

                default -> {
                    int numero = parsear(opcion);

                    if (!ejecutarActividad(numero, out, salida)) {
                        out.println("Opción no válida.");
                    }
                }
            }
        }
    }

    // =========================================
    // EJECUTAR UNA ACTIVIDAD
    // =========================================

    private static boolean ejecutarActividad(
            int actividad, PrintStream out, Path salida)
            throws IOException {

        switch (actividad) {

            case 1 -> {
                encabezado(out, "ACTIVIDAD 1 - ANÁLISIS DE COMPLEJIDAD");
                Demostraciones.actividad1(out, salida);
            }

            case 2 -> {
                encabezado(out, "ACTIVIDAD 2 - ITERATIVO VS RECURSIVO");
                Demostraciones.actividad2(out, salida);
            }

            case 3 -> {
                encabezado(out, "ACTIVIDAD 3 - ALGORITMOS DE ORDENACIÓN");
                Demostraciones.actividad3(out);
            }

            case 4 -> {
                encabezado(out, "ACTIVIDAD 4 - BENCHMARK");
                Demostraciones.actividad4(out, salida);
            }

            default -> {
                return false;
            }
        }

        return true;
    }

    // =========================================
    // EJECUTAR TODO EN ORDEN
    // =========================================

    private static void ejecutarTodas(
            PrintStream out, Path salida) throws IOException {

        out.println();
        out.println("==================================================");
        out.println("          EJECUCIÓN COMPLETA DEL PROYECTO");
        out.println("==================================================");

        ejecutarActividad(1, out, salida);

        ejecutarActividad(2, out, salida);

        ejecutarActividad(3, out, salida);

        encabezado(out, "CONTEXTO ACADÉMICO");
        Demostraciones.contextoAcademico(out);

        ejecutarActividad(4, out, salida);

        out.println();
        out.println("==================================================");
        out.println("          TODAS LAS ACTIVIDADES FINALIZADAS");
        out.println("==================================================");
    }

    // =========================================
    // ENCABEZADOS
    // =========================================

    private static void encabezado(
            PrintStream out, String titulo) {

        out.println();
        out.println("==================================================");
        out.println(" " + titulo);
        out.println("==================================================");
    }

    // =========================================
    // CONVERSIÓN DE OPCIONES
    // =========================================

    private static int parsear(String texto) {

        try {
            return Integer.parseInt(texto.trim());

        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
