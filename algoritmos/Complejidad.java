package ec.edu.uce.algoritmos;

import java.util.ArrayList;
import java.util.List;

//Actividad 1

public final class Complejidad {


    private static volatile long sumidero;

    private Complejidad() {

    }


    public record Medicion(String algoritmo, String bigO, long n, long operacionesMedidas,
                           long operacionesTeoricas, double tiempoMicros) {


        public boolean coincide() {
            return operacionesMedidas == operacionesTeoricas;
        }
    }

    public static long sumaLineal(int[] a, Metricas metricas) {
        long suma = 0;
        for (int i = 0; i < a.length; i++) {
            suma += a[i];
            metricas.operacion();
        }
        return suma;
    }



    public static long contarParesIguales(int[] a, Metricas metricas) {
        long pares = 0;
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                metricas.operacion();
                if (a[i] == a[j]) {
                    pares++;
                }
            }
        }
        return pares;
    }

    public static int reduccionSucesiva(long n, Metricas metricas) {
        if (n < 1) {
            throw new IllegalArgumentException("n debe ser un entero positivo: " + n);
        }
        int pasos = 0;
        while (n > 1) {
            n = n / 2;
            pasos++;
            metricas.operacion();
        }
        return pasos;
    }



    public static long teoricoLineal(long n) {
        return n;
    }

    public static long teoricoCuadratico(long n) {
        return n * (n - 1) / 2;
    }

    public static long teoricoLogaritmico(long n) {
        return 63 - Long.numberOfLeadingZeros(n);
    }


    public static long instruccionesA(long n) {
        return 3 * n + 4;
    }

    public static long instruccionesB(long n) {
        return 2 * n * n + 2 * n;
    }

    public static long instruccionesC(long n) {
        return 3 * teoricoLogaritmico(n) + 3;
    }

    public static List<Medicion> experimentar() {
        List<Medicion> filas = new ArrayList<>();

        for (int n : new int[] {10, 100, 1_000, 10_000, 100_000, 1_000_000}) {
            int[] datos = GeneradorDatos.generar(TipoDatos.ALEATORIO, n, GeneradorDatos.SEMILLA_BASE);
            Metricas m = new Metricas();
            sumaLineal(datos, m);
            double micros = Cronometro.medirMicros(() -> sumidero += sumaLineal(datos, new Metricas()), n);
            filas.add(new Medicion("A - Suma lineal", "O(n)", n, m.getOperaciones(),
                    teoricoLineal(n), micros));
        }

        for (int n : new int[] {10, 100, 1_000, 10_000}) {
            int[] datos = GeneradorDatos.generar(TipoDatos.ALEATORIO, n, GeneradorDatos.SEMILLA_BASE);
            Metricas m = new Metricas();
            contarParesIguales(datos, m);
            double micros = Cronometro.medirMicros(() -> sumidero += contarParesIguales(datos, new Metricas()),
                    (long) n * n / 2);
            filas.add(new Medicion("B - Ciclos anidados", "O(n^2)", n, m.getOperaciones(),
                    teoricoCuadratico(n), micros));
        }

        long[] valores = {10L, 100L, 1_000L, 1_000_000L, 1_000_000_000L,
                1_000_000_000_000L, 1_000_000_000_000_000_000L};
        for (long n : valores) {
            Metricas m = new Metricas();
            reduccionSucesiva(n, m);
            double micros = Cronometro.medirMicros(() -> sumidero += reduccionSucesiva(n, new Metricas()), 1);
            filas.add(new Medicion("C - Reduccion sucesiva", "O(log n)", n, m.getOperaciones(),
                    teoricoLogaritmico(n), micros));
        }
        return filas;
    }
}
