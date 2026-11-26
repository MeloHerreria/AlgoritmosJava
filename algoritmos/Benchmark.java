package ec.edu.uce.algoritmos;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;


 //Actividad 4 – Benchmark y comparación

public final class Benchmark {

    public static final int[] TAMANOS = {10, 100, 1_000, 10_000};


    public static final int REPETICIONES = 10;


    private static final int RONDAS_CALENTAMIENTO = 3;

    private Benchmark() {

    }


    public record Fila(String algoritmo, int n, TipoDatos tipo, long comparaciones,
                       long movimientos, double tiempoPromedioMs, double desviacionMs) {
    }

    private static Map<String, Function<int[], ResultadoOrdenacion>> algoritmos() {
        Map<String, Function<int[], ResultadoOrdenacion>> mapa = new LinkedHashMap<>();
        mapa.put("Bubble Sort", Ordenacion::bubbleSort);
        mapa.put("Selection Sort", Ordenacion::selectionSort);
        mapa.put("Insertion Sort", Ordenacion::insertionSort);
        mapa.put("Shell Sort", Ordenacion::shellSort);
        return mapa;
    }

    public static List<Fila> ejecutar(PrintStream progreso) {
        Map<String, Function<int[], ResultadoOrdenacion>> algoritmos = algoritmos();
        calentarJvm(algoritmos);

        List<Fila> filas = new ArrayList<>();
        for (int n : TAMANOS) {
            for (TipoDatos tipo : TipoDatos.values()) {
                int[] base = GeneradorDatos.generar(tipo, n, GeneradorDatos.semillaPara(n, tipo));
                int[] referencia = null;

                for (Map.Entry<String, Function<int[], ResultadoOrdenacion>> alg : algoritmos.entrySet()) {
                    Function<int[], ResultadoOrdenacion> ordenar = alg.getValue();

                    ResultadoOrdenacion descarte = ordenar.apply(base);
                    verificar(alg.getKey(), base, descarte.arreglo());
                    if (referencia == null) {
                        referencia = descarte.arreglo();
                    } else if (!Arrays.equals(referencia, descarte.arreglo())) {
                        throw new IllegalStateException(alg.getKey() + " produjo un resultado distinto "
                                + "al de los demás algoritmos (n=" + n + ", " + tipo + ")");
                    }

                    double[] tiemposMs = new double[REPETICIONES];
                    ResultadoOrdenacion ultimo = descarte;
                    for (int r = 0; r < REPETICIONES; r++) {
                        ultimo = ordenar.apply(base);
                        tiemposMs[r] = ultimo.tiempoMs();
                    }
                    filas.add(new Fila(alg.getKey(), n, tipo, ultimo.comparaciones(),
                            ultimo.movimientos(), promedio(tiemposMs), desviacion(tiemposMs)));
                }
            }
            if (progreso != null) {
                progreso.println("  tamaño n = " + n + " completado");
            }
        }
        return filas;
    }

    private static void calentarJvm(Map<String, Function<int[], ResultadoOrdenacion>> algoritmos) {
        long acumulado = 0;
        for (int ronda = 0; ronda < RONDAS_CALENTAMIENTO; ronda++) {
            for (int n : TAMANOS) {
                for (TipoDatos tipo : TipoDatos.values()) {
                    int[] base = GeneradorDatos.generar(tipo, n, GeneradorDatos.semillaPara(n, tipo));
                    for (Function<int[], ResultadoOrdenacion> f : algoritmos.values()) {
                        acumulado += f.apply(base).comparaciones();
                    }
                }
            }
        }
        if (acumulado == 42) {
            System.out.print("");
        }
    }

    private static void verificar(String nombre, int[] original, int[] resultado) {
        if (!Ordenacion.esOrdenado(resultado) || !Ordenacion.esPermutacion(original, resultado)) {
            throw new IllegalStateException(nombre + " no ordenó correctamente el conjunto de prueba.");
        }
    }

    private static double promedio(double[] valores) {
        double suma = 0;
        for (double v : valores) {
            suma += v;
        }
        return suma / valores.length;
    }


    private static double desviacion(double[] valores) {
        if (valores.length < 2) {
            return 0;
        }
        double media = promedio(valores);
        double suma = 0;
        for (double v : valores) {
            suma += (v - media) * (v - media);
        }
        return Math.sqrt(suma / (valores.length - 1));
    }




    public static void imprimirTabla(List<Fila> filas, PrintStream salida) {
        String formato = "%-15s %7s  %-15s %14s %14s %20s%n";
        salida.printf(formato, "Algoritmo", "n", "Tipo de datos", "Comparaciones", "Movimientos",
                "Tiempo promedio (ms)");
        salida.println("-".repeat(92));
        for (Fila f : filas) {
            salida.printf(Locale.ROOT, "%-15s %7d  %-15s %14d %14d %20.6f%n", f.algoritmo(), f.n(),
                    f.tipo().etiqueta(), f.comparaciones(), f.movimientos(), f.tiempoPromedioMs());
        }
    }


    public static void guardarCsv(List<Fila> filas, Path archivo) throws IOException {
        StringBuilder sb = new StringBuilder(
                "algoritmo,n,tipo_datos,comparaciones,movimientos,tiempo_promedio_ms,desviacion_ms\n");
        for (Fila f : filas) {
            sb.append(String.format(Locale.ROOT, "%s,%d,%s,%d,%d,%.6f,%.6f%n", f.algoritmo(), f.n(),
                    f.tipo().etiqueta(), f.comparaciones(), f.movimientos(), f.tiempoPromedioMs(),
                    f.desviacionMs()));
        }
        escribir(archivo, sb.toString());
    }


    public static void guardarMarkdown(List<Fila> filas, Path archivo) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("| Algoritmo | n | Tipo de datos | Comparaciones | Movimientos | Tiempo promedio (ms) |\n");
        sb.append("|---|---:|---|---:|---:|---:|\n");
        for (Fila f : filas) {
            sb.append(String.format(Locale.ROOT, "| %s | %d | %s | %d | %d | %.6f |%n", f.algoritmo(),
                    f.n(), f.tipo().etiqueta(), f.comparaciones(), f.movimientos(), f.tiempoPromedioMs()));
        }
        escribir(archivo, sb.toString());
    }


    public static String infoEquipo() {
        Runtime rt = Runtime.getRuntime();
        StringBuilder sb = new StringBuilder();
        sb.append("Sistema operativo : ").append(System.getProperty("os.name")).append(' ')
                .append(System.getProperty("os.version")).append(" (")
                .append(System.getProperty("os.arch")).append(")\n");
        sb.append("Procesador        : ").append(modeloCpu()).append('\n');
        sb.append("Núcleos lógicos   : ").append(rt.availableProcessors()).append('\n');
        sb.append("Memoria máx. JVM  : ").append(rt.maxMemory() / (1024 * 1024)).append(" MB\n");
        sb.append("Java              : ").append(System.getProperty("java.version")).append(" (")
                .append(System.getProperty("java.vm.name")).append(")\n");
        sb.append("Repeticiones      : ").append(REPETICIONES).append(" medidas + 1 de descarte por combinación\n");
        sb.append("Semilla base      : ").append(GeneradorDatos.SEMILLA_BASE);
        return sb.toString();
    }


    private static String modeloCpu() {
        try {
            Path cpuinfo = Path.of("/proc/cpuinfo");
            if (Files.exists(cpuinfo)) {
                for (String linea : Files.readAllLines(cpuinfo)) {
                    if (linea.startsWith("model name")) {
                        return linea.substring(linea.indexOf(':') + 1).trim();
                    }
                }
            }
        } catch (IOException ignorada) {

        }
        String id = System.getenv("PROCESSOR_IDENTIFIER");     // Windows
        return id != null ? id : "no disponible (completar manualmente)";
    }

    private static void escribir(Path archivo, String contenido) throws IOException {
        if (archivo.getParent() != null) {
            Files.createDirectories(archivo.getParent());
        }
        Files.writeString(archivo, contenido, StandardCharsets.UTF_8);
    }
}
