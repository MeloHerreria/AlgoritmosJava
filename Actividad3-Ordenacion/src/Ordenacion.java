package ec.edu.uce.algoritmos;

import java.util.HashMap;
import java.util.Map;

public final class Ordenacion {

    private Ordenacion() {
    }

    public static ResultadoOrdenacion bubbleSort(int[] datos) {
        int[] a = copiar(datos);
        long comparaciones = 0;
        long movimientos = 0;
        int n = a.length;

        long inicio = System.nanoTime();
        boolean huboIntercambio = true;
        for (int pasada = 0; pasada < n - 1 && huboIntercambio; pasada++) {
            huboIntercambio = false;
            for (int j = 0; j < n - 1 - pasada; j++) {
                comparaciones++;
                if (a[j] > a[j + 1]) {
                    int temporal = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temporal;
                    movimientos += 2;
                    huboIntercambio = true;
                }
            }
        }
        long fin = System.nanoTime();
        return new ResultadoOrdenacion("Bubble Sort", a, comparaciones, movimientos, fin - inicio);
    }


    public static ResultadoOrdenacion selectionSort(int[] datos) {
        int[] a = copiar(datos);
        long comparaciones = 0;
        long movimientos = 0;
        int n = a.length;

        long inicio = System.nanoTime();
        for (int i = 0; i < n - 1; i++) {
            int indiceMinimo = i;
            for (int j = i + 1; j < n; j++) {
                comparaciones++;
                if (a[j] < a[indiceMinimo]) {
                    indiceMinimo = j;
                }
            }
            if (indiceMinimo != i) {
                int temporal = a[i];
                a[i] = a[indiceMinimo];
                a[indiceMinimo] = temporal;
                movimientos += 2;
            }
        }
        long fin = System.nanoTime();
        return new ResultadoOrdenacion("Selection Sort", a, comparaciones, movimientos, fin - inicio);
    }


    public static ResultadoOrdenacion insertionSort(int[] datos) {
        int[] a = copiar(datos);
        long comparaciones = 0;
        long movimientos = 0;
        int n = a.length;

        long inicio = System.nanoTime();
        for (int i = 1; i < n; i++) {
            int clave = a[i];
            int j = i - 1;
            while (j >= 0) {
                comparaciones++;
                if (a[j] > clave) {
                    a[j + 1] = a[j];
                    movimientos++;
                    j--;
                } else {
                    break;
                }
            }
            if (j + 1 != i) {
                a[j + 1] = clave;
                movimientos++;
            }
        }
        long fin = System.nanoTime();
        return new ResultadoOrdenacion("Insertion Sort", a, comparaciones, movimientos, fin - inicio);
    }

    public static ResultadoOrdenacion shellSort(int[] datos) {
        int[] a = copiar(datos);
        long comparaciones = 0;
        long movimientos = 0;
        int n = a.length;

        long inicio = System.nanoTime();
        for (int salto = n / 2; salto > 0; salto /= 2) {
            for (int i = salto; i < n; i++) {
                int clave = a[i];
                int j = i;
                while (j >= salto) {
                    comparaciones++;
                    if (a[j - salto] > clave) {
                        a[j] = a[j - salto];
                        movimientos++;
                        j -= salto;
                    } else {
                        break;
                    }
                }
                if (j != i) {
                    a[j] = clave;
                    movimientos++;
                }
            }
        }
        long fin = System.nanoTime();
        return new ResultadoOrdenacion("Shell Sort", a, comparaciones, movimientos, fin - inicio);
    }

    /** Indica si el arreglo está ordenado de forma no decreciente. */
    public static boolean esOrdenado(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Indica si {@code resultado} contiene exactamente los mismos elementos (con las mismas
     * repeticiones) que {@code original}, es decir, si es una permutación suya.
     * Se usa para verificar que un algoritmo no pierde ni inventa datos.
     */
    public static boolean esPermutacion(int[] original, int[] resultado) {
        if (original.length != resultado.length) {
            return false;
        }
        Map<Integer, Integer> frecuencias = new HashMap<>();
        for (int valor : original) {
            frecuencias.merge(valor, 1, Integer::sum);
        }
        for (int valor : resultado) {
            Integer restante = frecuencias.get(valor);
            if (restante == null || restante == 0) {
                return false;
            }
            frecuencias.put(valor, restante - 1);
        }
        return true;
    }

    private static int[] copiar(int[] datos) {
        if (datos == null) {
            throw new IllegalArgumentException("El arreglo no puede ser nulo.");
        }
        return datos.clone();
    }
}
