package ec.edu.uce.algoritmos;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public final class MainOrdenacion {

    private static final List<Function<int[], ResultadoOrdenacion>> ALGORITMOS = List.of(
            Ordenacion::bubbleSort,
            Ordenacion::selectionSort,
            Ordenacion::insertionSort,
            Ordenacion::shellSort);

    private MainOrdenacion() {
    }

    public static void main(String[] args) {
        System.out.println("=".repeat(96));
        System.out.println("ACTIVIDAD 3 - ALGORITMOS DE ORDENACIÓN");
        System.out.println("=".repeat(96));
        System.out.println("""
                Criterio de conteo documentado:
                  - Comparación: cada evaluación que compara dos valores del arreglo.
                  - Movimiento : cada escritura de un elemento en el arreglo (intercambio = 2;
                                 desplazamiento = 1; colocación final de la clave = 1 si cambia de lugar).
                  - Tiempo     : System.nanoTime() solo alrededor del algoritmo (la copia queda fuera).
                """);

        int[] datos = {45, 12, 90, 12, 3, 70, 45, 18};
        int[] esperado = {3, 12, 12, 18, 45, 45, 70, 90};
        System.out.println("Conjunto inicial : " + Arrays.toString(datos));
        System.out.println("Salida esperada  : " + Arrays.toString(esperado));
        System.out.println();
        System.out.printf("%-15s %-34s %6s %6s %12s  %s%n",
                "Algoritmo", "Resultado", "Comp.", "Mov.", "Tiempo (µs)", "¿Correcto?");
        System.out.println("-".repeat(92));
        for (Function<int[], ResultadoOrdenacion> algoritmo : ALGORITMOS) {
            ResultadoOrdenacion r = algoritmo.apply(datos);
            System.out.printf(Locale.ROOT, "%-15s %-34s %6d %6d %12.3f  %s%n", r.algoritmo(),
                    Arrays.toString(r.arreglo()), r.comparaciones(), r.movimientos(), r.tiempoMicros(),
                    Arrays.equals(r.arreglo(), esperado) ? "SI" : "NO");
        }
        System.out.println("Arreglo original (no se modifica): " + Arrays.toString(datos));

        System.out.println();
        System.out.println("Casos de validación:");
        String formato = "%-18s %-15s %-22s %6s %6s  %s%n";
        System.out.printf(formato, "Caso", "Algoritmo", "Resultado", "Comp.", "Mov.", "¿Correcto?");
        System.out.println("-".repeat(92));
        Object[][] casos = {
                {"Arreglo vacío", new int[0]},
                {"Un solo elemento", new int[] {7}},
                {"Datos repetidos", new int[] {5, 5, 5, 5, 5}},
                {"Ya ordenado", new int[] {1, 2, 3, 4, 5, 6}},
                {"Orden inverso", new int[] {6, 5, 4, 3, 2, 1}},
        };
        for (Object[] caso : casos) {
            int[] entrada = (int[]) caso[1];
            for (Function<int[], ResultadoOrdenacion> algoritmo : ALGORITMOS) {
                ResultadoOrdenacion r = algoritmo.apply(entrada);
                boolean ok = Ordenacion.esOrdenado(r.arreglo()) && Ordenacion.esPermutacion(entrada, r.arreglo());
                System.out.printf(Locale.ROOT, formato, caso[0], r.algoritmo(), Arrays.toString(r.arreglo()),
                        r.comparaciones(), r.movimientos(), ok ? "SI" : "NO");
            }
        }
        contextoAcademico();
    }

    private static void contextoAcademico() {
        System.out.println();
        System.out.println("CONTEXTO - ORDENAR CALIFICACIONES DE ESTUDIANTES");
        int[] notas = {78, 92, 65, 88, 92, 54, 71, 83, 99, 60};
        System.out.println("Calificaciones registradas (sobre 100): " + Arrays.toString(notas));
        System.out.printf("%-15s %-42s %6s %6s%n", "Algoritmo", "Ordenadas de menor a mayor", "Comp.", "Mov.");
        System.out.println("-".repeat(72));
        int[] ordenadas = null;
        for (Function<int[], ResultadoOrdenacion> algoritmo : ALGORITMOS) {
            ResultadoOrdenacion r = algoritmo.apply(notas);
            System.out.printf(Locale.ROOT, "%-15s %-42s %6d %6d%n", r.algoritmo(),
                    Arrays.toString(r.arreglo()), r.comparaciones(), r.movimientos());
            if (ordenadas == null) {
                ordenadas = r.arreglo();
            } else if (!Arrays.equals(ordenadas, r.arreglo())) {
                throw new IllegalStateException("Los algoritmos no coinciden: " + r.algoritmo());
            }
        }
        StringBuilder ranking = new StringBuilder();
        for (int i = ordenadas.length - 1; i >= 0; i--) {
            ranking.append(ordenadas.length - i).append("º=").append(ordenadas[i]).append("  ");
        }
        System.out.println("Ranking (mayor a menor): " + ranking.toString().trim());
        System.out.println("Nota mínima: " + ordenadas[0] + "   Nota máxima: " + ordenadas[ordenadas.length - 1]);
    }
}
