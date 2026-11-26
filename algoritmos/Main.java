package ec.edu.uce.algoritmos;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Scanner;


public final class Main {

    private static final Path SALIDA_POR_DEFECTO = Path.of("resultados", "tablas");

    private Main() {
    }

    public static void main(String[] args) throws IOException {
        PrintStream out = System.out;
        Path salida = SALIDA_POR_DEFECTO;
        boolean todo = false;
        int actividad = 0;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--todo" -> todo = true;
                case "--actividad" -> actividad = i + 1 < args.length ? parsear(args[++i]) : -1;
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

        if (todo) {
            ejecutarTodo(out, salida);
        } else if (actividad != 0) {
            if (!ejecutar(actividad, out, salida)) {
                out.println("Actividad no válida. Use un número del 1 al 4.");
            }
        } else {
            menu(out, salida);
        }
    }

    private static void menu(PrintStream out, Path salida) throws IOException {
        Scanner teclado = new Scanner(System.in);
        while (true) {
            out.println();
            out.println("TAREA GRUPAL INTEGRADORA - ALGORITMOS (UCE) - PARTE II");
            out.println("  1. Actividad 1 - Análisis de complejidad (O(n), O(n²), O(log n))");
            out.println("  2. Actividad 2 - Iterativo vs recursivo (factorial, suma, máximo)");
            out.println("  3. Actividad 3 - Algoritmos de ordenación");
            out.println("  4. Actividad 4 - Benchmark y comparación experimental");
            out.println("  5. Ejecutar todo (incluye el contexto de registros académicos)");
            out.println("  6. Contexto - Registros académicos (calificaciones)");
            out.println("  0. Salir");
            out.print("Seleccione una opción: ");
            if (!teclado.hasNextLine()) {
                return;                                 // fin de la entrada estándar
            }
            String opcion = teclado.nextLine().trim();
            switch (opcion) {
                case "0" -> {
                    out.println("Hasta luego.");
                    return;
                }
                case "5" -> ejecutarTodo(out, salida);
                case "6" -> Demostraciones.contextoAcademico(out);
                default -> {
                    if (!ejecutar(parsear(opcion), out, salida)) {
                        out.println("Opción no válida.");
                    }
                }
            }
        }
    }

    private static boolean ejecutar(int actividad, PrintStream out, Path salida) throws IOException {
        switch (actividad) {
            case 1 -> Demostraciones.actividad1(out, salida);
            case 2 -> Demostraciones.actividad2(out, salida);
            case 3 -> Demostraciones.actividad3(out);
            case 4 -> Demostraciones.actividad4(out, salida);
            default -> {
                return false;
            }
        }
        return true;
    }

    private static void ejecutarTodo(PrintStream out, Path salida) throws IOException {
        Demostraciones.actividad1(out, salida);
        Demostraciones.actividad2(out, salida);
        Demostraciones.actividad3(out);
        Demostraciones.contextoAcademico(out);
        Demostraciones.actividad4(out, salida);
    }

    private static int parsear(String texto) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
