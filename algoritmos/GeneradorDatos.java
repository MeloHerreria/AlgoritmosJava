package ec.edu.uce.algoritmos;

import java.util.Random;


public final class GeneradorDatos {


    public static final long SEMILLA_BASE = 20261126L;

    private GeneradorDatos() {

    }

    public static int[] generar(TipoDatos tipo, int n, long semilla) {
        if (n < 0) {
            throw new IllegalArgumentException("El tamaño no puede ser negativo: " + n);
        }
        Random azar = new Random(semilla);
        int[] datos = new int[n];
        switch (tipo) {
            case ASCENDENTE -> {
                for (int i = 0; i < n; i++) {
                    datos[i] = i + 1;
                }
            }
            case DESCENDENTE -> {
                for (int i = 0; i < n; i++) {
                    datos[i] = n - i;
                }
            }
            case ALEATORIO -> {
                int limite = Math.max(10, n * 10);
                for (int i = 0; i < n; i++) {
                    datos[i] = azar.nextInt(limite);
                }
            }
            case REPETIDOS -> {
                int distintos = Math.max(3, n / 10);
                for (int i = 0; i < n; i++) {
                    datos[i] = azar.nextInt(distintos) + 1;
                }
            }
            case CASI_ORDENADO -> {
                for (int i = 0; i < n; i++) {
                    datos[i] = i + 1;
                }
                int intercambios = Math.max(1, n / 20);
                for (int k = 0; k < intercambios && n > 1; k++) {
                    int i = azar.nextInt(n);
                    int j = azar.nextInt(n);
                    int tmp = datos[i];
                    datos[i] = datos[j];
                    datos[j] = tmp;
                }
            }
        }
        return datos;
    }

    public static long semillaPara(int n, TipoDatos tipo) {
        return SEMILLA_BASE + 31L * n + tipo.ordinal();
    }
}
