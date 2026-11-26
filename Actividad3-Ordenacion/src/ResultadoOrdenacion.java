package ec.edu.uce.algoritmos;

public record ResultadoOrdenacion(String algoritmo, int[] arreglo, long comparaciones,
                                  long movimientos, long tiempoNs) {

    /** Tiempo en milisegundos. */
    public double tiempoMs() {
        return tiempoNs / 1_000_000.0;
    }

    /** Tiempo en microsegundos. */
    public double tiempoMicros() {
        return tiempoNs / 1_000.0;
    }
}
