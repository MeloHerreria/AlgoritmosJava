package ec.edu.uce.algoritmos;

final class Cronometro {

    private static final int MUESTRAS = 5;

    private Cronometro() {

    }

    static double medirMicros(Runnable tarea, long trabajoEstimado) {
        int repeticiones = (int) Math.max(1, Math.min(20_000, 2_000_000 / Math.max(1, trabajoEstimado)));

        int calentamiento = Math.min(3 * repeticiones, 60_000);
        for (int i = 0; i < calentamiento; i++) {
            tarea.run();
        }

        double totalNs = 0;
        for (int muestra = 0; muestra < MUESTRAS; muestra++) {
            long inicio = System.nanoTime();
            for (int r = 0; r < repeticiones; r++) {
                tarea.run();
            }
            totalNs += (System.nanoTime() - inicio) / (double) repeticiones;
        }
        return totalNs / MUESTRAS / 1_000.0;
    }
}
