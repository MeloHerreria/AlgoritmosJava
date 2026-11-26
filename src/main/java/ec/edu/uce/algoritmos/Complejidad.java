
package ec.edu.uce.algoritmos;

import java.util.ArrayList;
import java.util.List;

// Actividad 1
public final class Complejidad {

    private static volatile long sumidero;

    private Complejidad() {
    }

    public record Medicion(
            String algoritmo,
            String bigO,
            long n,
            long operacionesMedidas,
            long operacionesTeoricas,
            double tiempoMicros) {

        public boolean coincide() {
            return operacionesMedidas == operacionesTeoricas;
        }
    }

    // A: Suma lineal O(n)
    public static long sumaLineal(int[] a, Metricas metricas) {
        long suma = 0;

        for (int i = 0; i < a.length; i++) {
            suma += a[i];
            metricas.operacion();
        }

        return suma;
    }

    // B: Compara cada elemento con todos los demás O(n²)
    public static long compararTodos(int[] a, Metricas metricas) {
        long menores = 0;

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                if (i != j) {
                    metricas.operacion();

                    if (a[i] < a[j]) {
                        menores++;
                    }
                }
            }
        }

        return menores;
    }

    // C: Reducción sucesiva a la mitad O(log n)
    public static int reduccionSucesiva(long n, Metricas metricas) {
        if (n < 1) {
            throw new IllegalArgumentException(
                    "n debe ser un entero positivo: " + n);
        }

        int pasos = 0;

        while (n > 1) {
            n = n / 2;
            pasos++;
            metricas.operacion();
        }

        return pasos;
    }

    // Conteos teóricos
    public static long teoricoLineal(long n) {
        return n;
    }

    public static long teoricoCuadratico(long n) {
        return n * (n - 1);
    }

    public static long teoricoLogaritmico(long n) {
        if (n < 1) {
            throw new IllegalArgumentException("n debe ser positivo.");
        }

        return 63 - Long.numberOfLeadingZeros(n);
    }

    // Mediciones experimentales
    public static List<Medicion> experimentar() {
        List<Medicion> filas = new ArrayList<>();

        // Actividad A
        for (int n : new int[]{
                10, 100, 1_000, 10_000, 100_000, 1_000_000
        }) {
            int[] datos = GeneradorDatos.generar(
                    TipoDatos.ALEATORIO,
                    n,
                    GeneradorDatos.SEMILLA_BASE
            );

            Metricas m = new Metricas();
            sumaLineal(datos, m);

            double micros = Cronometro.medirMicros(
                    () -> sumidero += sumaLineal(datos, new Metricas()),
                    n
            );

            filas.add(new Medicion(
                    "A - Suma lineal",
                    "O(n)",
                    n,
                    m.getOperaciones(),
                    teoricoLineal(n),
                    micros
            ));
        }

        // Actividad B
        for (int n : new int[]{10, 100, 1_000, 10_000}) {
            int[] datos = GeneradorDatos.generar(
                    TipoDatos.ALEATORIO,
                    n,
                    GeneradorDatos.SEMILLA_BASE
            );

            Metricas m = new Metricas();
            compararTodos(datos, m);

            double micros = Cronometro.medirMicros(
                    () -> sumidero += compararTodos(datos, new Metricas()),
                    (long) n * (n - 1)
            );

            filas.add(new Medicion(
                    "B - Comparacion de elementos",
                    "O(n^2)",
                    n,
                    m.getOperaciones(),
                    teoricoCuadratico(n),
                    micros
            ));
        }

        // Actividad C
        long[] valores = {
                10L,
                100L,
                1_000L,
                1_000_000L,
                1_000_000_000L,
                1_000_000_000_000L,
                1_000_000_000_000_000_000L
        };

        for (long n : valores) {
            Metricas m = new Metricas();
            reduccionSucesiva(n, m);

            double micros = Cronometro.medirMicros(
                    () -> sumidero += reduccionSucesiva(n, new Metricas()),
                    1
            );

            filas.add(new Medicion(
                    "C - Reduccion sucesiva",
                    "O(log n)",
                    n,
                    m.getOperaciones(),
                    teoricoLogaritmico(n),
                    micros
            ));
        }

        return filas;
    }

    // Métodos conservados para los tests originales

    public static void linearSum(int[] numbers) {
        Metricas m = new Metricas();
        long suma = sumaLineal(numbers, m);

        System.out.println("Sum: " + suma);
        System.out.println("Operations: " + m.getOperaciones());
        System.out.println("T(n) = n");
        System.out.println("O(n)");
    }

    public static void quadraticComparison(int[] numbers) {
        Metricas m = new Metricas();
        long menores = compararTodos(numbers, m);

        System.out.println("Smaller pairs: " + menores);
        System.out.println("Comparisons: " + m.getOperaciones());
        System.out.println("T(n) = n² - n");
        System.out.println("O(n²)");
    }

    public static void halve(int number) {
        if (number <= 0) {
            System.out.println("Error: number must be positive.");
            return;
        }

        Metricas m = new Metricas();
        int pasos = reduccionSucesiva(number, m);

        System.out.println("Initial value: " + number);
        System.out.println("Final value: 1");
        System.out.println("Operations: " + pasos);
        System.out.println("T(n) = floor(log2 n)");
        System.out.println("O(log n)");
    }
}
