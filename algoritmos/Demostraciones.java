package ec.edu.uce.algoritmos;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;


public final class Demostraciones {

    private static volatile long sumidero;

    private Demostraciones() {

    }

    //Actividad 1: Análisis práctico de complejidad
    public static void actividad1(PrintStream out, Path salida) throws IOException {
        encabezado(out, "ACTIVIDAD 1 - ANÁLISIS PRÁCTICO DE COMPLEJIDAD ALGORÍTMICA");

        out.println("""
                Algoritmo A - Recorrido lineal (suma de elementos)
                  Tamaño de entrada n : longitud del arreglo
                  Operación dominante : suma  acum += a[i]
                  Veces que se ejecuta: n
                  T(n) por instrucciones = 3n + 4          ->  Big O = O(n)

                Algoritmo B - Ciclos anidados (cada elemento contra los siguientes)
                  Tamaño de entrada n : longitud del arreglo
                  Operación dominante : comparación  a[i] == a[j]
                  Veces que se ejecuta: n(n-1)/2
                  T(n) por instrucciones (peor caso) = 2n² + 2n  ->  Big O = O(n²)

                Algoritmo C - Reducción sucesiva (n = n / 2 hasta llegar a 1)
                  Tamaño de entrada n : valor del número (el costo depende de log2 n)
                  Operación dominante : división  n = n / 2
                  Veces que se ejecuta: floor(log2 n)
                  T(n) por instrucciones = 3·floor(log2 n) + 3  ->  Big O = O(log n)
                """);

        List<Complejidad.Medicion> filas = Complejidad.experimentar();
        String formato = "%-22s %-9s %20d %14d %14d %10s %15.3f%n";
        out.printf("%-22s %-9s %20s %14s %14s %10s %15s%n", "Algoritmo", "Big O", "n", "Ops. medidas",
                "Ops. teóricas", "¿Coincide?", "Tiempo (µs)");
        out.println("-".repeat(108));
        for (Complejidad.Medicion f : filas) {
            out.printf(Locale.ROOT, formato, f.algoritmo(), f.bigO(), f.n(), f.operacionesMedidas(),
                    f.operacionesTeoricas(), f.coincide() ? "SI" : "NO", f.tiempoMicros());
        }

        boolean todas = filas.stream().allMatch(Complejidad.Medicion::coincide);
        out.println();
        out.println(todas
                ? "Resultado: el conteo experimental coincide EXACTAMENTE con la fórmula teórica en las "
                + filas.size() + " pruebas."
                : "ATENCIÓN: hay diferencias entre el conteo experimental y el teórico.");

        StringBuilder csv = new StringBuilder(
                "algoritmo,big_o,n,operaciones_medidas,operaciones_teoricas,coincide,tiempo_micros\n");
        for (Complejidad.Medicion f : filas) {
            csv.append(String.format(Locale.ROOT, "%s,%s,%d,%d,%d,%s,%.4f%n", f.algoritmo(), f.bigO(), f.n(),
                    f.operacionesMedidas(), f.operacionesTeoricas(), f.coincide(), f.tiempoMicros()));
        }
        guardar(salida.resolve("complejidad.csv"), csv.toString());
    }


    // Actividad 2: factorial, suma y máximo en versión iterativa y recursiva.
    public static void actividad2(PrintStream out, Path salida) throws IOException {
        encabezado(out, "ACTIVIDAD 2 - ALGORITMOS ITERATIVOS Y RECURSIVOS");


        int[] valores = {8, 3, 10, 2, 7};
        int numero = 5;
        out.println("2.1 Casos de prueba mínimos: valores = " + Arrays.toString(valores) + ", numero = " + numero);
        out.printf("%-26s %12s %12s %10s  %s%n", "Problema", "Iterativo", "Recursivo", "Esperado", "¿Correcto?");
        out.println("-".repeat(78));
        filaPrueba(out, "Factorial de 5", Iteracion.factorial(numero), Recursion.factorial(numero), 120);
        filaPrueba(out, "Suma del arreglo", Iteracion.suma(valores), Recursion.suma(valores), 30);
        filaPrueba(out, "Máximo del arreglo", Iteracion.maximo(valores), Recursion.maximo(valores), 10);


        out.println();
        out.println("2.2 Casos límite y validación de entradas");
        out.printf("%-34s %-30s %-30s%n", "Caso", "Iterativo", "Recursivo");
        out.println("-".repeat(96));
        limite(out, "factorial(0)", () -> Iteracion.factorial(0), () -> Recursion.factorial(0));
        limite(out, "factorial(1)", () -> Iteracion.factorial(1), () -> Recursion.factorial(1));
        limite(out, "factorial(20)  [máximo en long]", () -> Iteracion.factorial(20), () -> Recursion.factorial(20));
        limite(out, "factorial(21)  [desbordaría]", () -> Iteracion.factorial(21), () -> Recursion.factorial(21));
        limite(out, "factorial(-3)  [negativo]", () -> Iteracion.factorial(-3), () -> Recursion.factorial(-3));
        limite(out, "suma({})  [arreglo vacío]", () -> Iteracion.suma(new int[0]), () -> Recursion.suma(new int[0]));
        limite(out, "suma(null)", () -> Iteracion.suma(null), () -> Recursion.suma(null));
        limite(out, "maximo({})  [arreglo vacío]", () -> Iteracion.maximo(new int[0]), () -> Recursion.maximo(new int[0]));
        limite(out, "maximo(null)", () -> Iteracion.maximo(null), () -> Recursion.maximo(null));
        limite(out, "suma({42})  [un elemento]", () -> Iteracion.suma(new int[] {42}), () -> Recursion.suma(new int[] {42}));
        limite(out, "maximo({42})  [un elemento]", () -> Iteracion.maximo(new int[] {42}), () -> Recursion.maximo(new int[] {42}));
        limite(out, "maximo({-5,-2,-9})  [negativos]", () -> Iteracion.maximo(new int[] {-5, -2, -9}),
                () -> Recursion.maximo(new int[] {-5, -2, -9}));
        limite(out, "suma({MAX_INT, MAX_INT})", () -> Iteracion.suma(new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE}),
                () -> Recursion.suma(new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE}));


        out.println();
        out.println("2.3 Comparación de resultados, operaciones y tiempo (promedio, µs)");
        out.printf("%-10s %-10s %7s %20s %12s %10s %14s%n", "Problema", "Versión", "n", "Resultado",
                "Operaciones", "Llamadas", "Tiempo (µs)");
        out.println("-".repeat(90));
        StringBuilder csv = new StringBuilder("problema,version,n,resultado,operaciones,llamadas,tiempo_micros\n");

        for (int n : new int[] {5, 10, 20}) {
            Metricas mi = new Metricas();
            Metricas mr = new Metricas();
            long ri = Iteracion.factorial(n, mi);
            long rr = Recursion.factorial(n, mr);
            double ti = Cronometro.medirMicros(() -> sumidero += Iteracion.factorial(n), n);
            double tr = Cronometro.medirMicros(() -> sumidero += Recursion.factorial(n), n);
            filaMedicion(out, csv, "Factorial", "Iterativa", n, ri, mi, ti);
            filaMedicion(out, csv, "Factorial", "Recursiva", n, rr, mr, tr);
        }
        for (int n : new int[] {10, 100, 1_000, 5_000}) {
            int[] datos = GeneradorDatos.generar(TipoDatos.ALEATORIO, n, GeneradorDatos.SEMILLA_BASE);
            Metricas mi = new Metricas();
            Metricas mr = new Metricas();
            long si = Iteracion.suma(datos, mi);
            long sr = Recursion.suma(datos, mr);
            double ti = Cronometro.medirMicros(() -> sumidero += Iteracion.suma(datos), n);
            double tr = Cronometro.medirMicros(() -> sumidero += Recursion.suma(datos), n);
            filaMedicion(out, csv, "Suma", "Iterativa", n, si, mi, ti);
            filaMedicion(out, csv, "Suma", "Recursiva", n, sr, mr, tr);

            Metricas xi = new Metricas();
            Metricas xr = new Metricas();
            long mxi = Iteracion.maximo(datos, xi);
            long mxr = Recursion.maximo(datos, xr);
            double tmi = Cronometro.medirMicros(() -> sumidero += Iteracion.maximo(datos), n);
            double tmr = Cronometro.medirMicros(() -> sumidero += Recursion.maximo(datos), n);
            filaMedicion(out, csv, "Máximo", "Iterativa", n, mxi, xi, tmi);
            filaMedicion(out, csv, "Máximo", "Recursiva", n, mxr, xr, tmr);

            if (si != sr || mxi != mxr) {
                throw new IllegalStateException("Las versiones iterativa y recursiva difieren para n = " + n);
            }
        }
        out.println("Las versiones iterativa y recursiva devolvieron el mismo resultado en todas las pruebas.");
        guardar(salida.resolve("iterativo_vs_recursivo.csv"), csv.toString());

        out.println();
        out.println("2.4 Riesgo de desbordamiento de pila (suma de un arreglo de n unos; pila por defecto de la JVM)");
        out.printf("%12s  %-22s %-22s%n", "n", "Iterativa", "Recursiva");
        out.println("-".repeat(60));
        StringBuilder csvPila = new StringBuilder("n,iterativa,recursiva\n");
        for (int n : new int[] {1_000, 5_000, 10_000, 25_000, 50_000, 100_000, 500_000, 1_000_000}) {
            int[] unos = new int[n];
            Arrays.fill(unos, 1);
            String iter = Iteracion.suma(unos) == n ? "OK" : "INCORRECTO";
            String rec = intentarSumaRecursiva(unos);
            out.printf(Locale.ROOT, "%12d  %-22s %-22s%n", n, iter, rec);
            csvPila.append(n).append(',').append(iter).append(',').append(rec).append('\n');
        }
        guardar(salida.resolve("pila_recursion.csv"), csvPila.toString());
    }

    private static String intentarSumaRecursiva(int[] datos) {
        try {
            return Recursion.suma(datos) == datos.length ? "OK" : "INCORRECTO";
        } catch (StackOverflowError e) {
            return "StackOverflowError";
        }
    }

    private static void filaPrueba(PrintStream out, String problema, long iterativo, long recursivo, long esperado) {
        boolean ok = iterativo == esperado && recursivo == esperado;
        out.printf("%-26s %12d %12d %10d  %s%n", problema, iterativo, recursivo, esperado, ok ? "SI" : "NO");
    }

    private static void limite(PrintStream out, String caso, Supplier<Object> iterativo, Supplier<Object> recursivo) {
        out.printf("%-34s %-30s %-30s%n", caso, ejecutarSeguro(iterativo), ejecutarSeguro(recursivo));
    }


    private static String ejecutarSeguro(Supplier<Object> tarea) {
        try {
            return String.valueOf(tarea.get());
        } catch (IllegalArgumentException e) {
            return "IllegalArgumentException";
        }
    }

    private static void filaMedicion(PrintStream out, StringBuilder csv, String problema,
                                     String version, int n, long resultado, Metricas m, double micros) {
        out.printf(Locale.ROOT, "%-10s %-10s %7d %20d %12d %10d %14.3f%n", problema, version, n, resultado,
                m.getOperaciones(), m.getLlamadas(), micros);
        csv.append(String.format(Locale.ROOT, "%s,%s,%d,%d,%d,%d,%.4f%n", problema, version, n, resultado,
                m.getOperaciones(), m.getLlamadas(), micros));
    }


    // Actividad 3: algoritmos de ordenación con el conjunto de prueba y casos límite.
    public static void actividad3(PrintStream out) {
        encabezado(out, "ACTIVIDAD 3 - ALGORITMOS DE ORDENACIÓN");

        out.println("""
                Criterio de conteo documentado:
                  - Comparación: cada evaluación que compara dos valores del arreglo.
                  - Movimiento : cada escritura de un elemento en el arreglo (un intercambio = 2 movimientos;
                                 un desplazamiento = 1; la colocación final de la clave = 1 si cambia de lugar).
                  - Tiempo     : System.nanoTime() solo alrededor del algoritmo (la copia queda fuera).
                """);

        int[] datos = {45, 12, 90, 12, 3, 70, 45, 18};
        int[] esperado = {3, 12, 12, 18, 45, 45, 70, 90};
        out.println("Conjunto inicial : " + Arrays.toString(datos));
        out.println("Salida esperada  : " + Arrays.toString(esperado));
        out.println();
        out.printf("%-15s %-34s %6s %6s %12s  %s%n", "Algoritmo", "Resultado", "Comp.", "Mov.", "Tiempo (µs)", "¿Correcto?");
        out.println("-".repeat(92));
        for (ResultadoOrdenacion r : ejecutarTodos(datos)) {
            out.printf(Locale.ROOT, "%-15s %-34s %6d %6d %12.3f  %s%n", r.algoritmo(), Arrays.toString(r.arreglo()),
                    r.comparaciones(), r.movimientos(), r.tiempoMicros(),
                    Arrays.equals(r.arreglo(), esperado) ? "SI" : "NO");
        }
        out.println("Arreglo original tras ordenar (no se modifica): " + Arrays.toString(datos));

        out.println();
        out.println("Casos límite (comparaciones / movimientos):");
        String formato = "%-30s %-15s %-24s %6s %6s  %s%n";
        out.printf(formato, "Caso", "Algoritmo", "Resultado", "Comp.", "Mov.", "¿Correcto?");
        out.println("-".repeat(92));
        Object[][] casos = {
                {"Arreglo vacío", new int[0]},
                {"Un solo elemento", new int[] {7}},
                {"Datos repetidos", new int[] {5, 5, 5, 5, 5}},
                {"Ya ordenado", new int[] {1, 2, 3, 4, 5, 6}},
                {"Orden inverso", new int[] {6, 5, 4, 3, 2, 1}},
        };
        for (Object[] caso : casos) {
            int[] entrada = (int[]) caso[1];
            for (ResultadoOrdenacion r : ejecutarTodos(entrada)) {
                boolean ok = Ordenacion.esOrdenado(r.arreglo()) && Ordenacion.esPermutacion(entrada, r.arreglo());
                out.printf(Locale.ROOT, formato, caso[0], r.algoritmo(), Arrays.toString(r.arreglo()),
                        r.comparaciones(), r.movimientos(), ok ? "SI" : "NO");
            }
        }
    }

    private static List<ResultadoOrdenacion> ejecutarTodos(int[] datos) {
        List<Function<int[], ResultadoOrdenacion>> algoritmos = List.of(
                Ordenacion::bubbleSort, Ordenacion::selectionSort,
                Ordenacion::insertionSort, Ordenacion::shellSort);
        return algoritmos.stream().map(f -> f.apply(datos)).toList();
    }



    public static void contextoAcademico(PrintStream out) {
        encabezado(out, "CONTEXTO - REGISTROS ACADÉMICOS (calificaciones sobre 100)");
        int[] notas = {78, 92, 65, 88, 92, 54, 71, 83, 99, 60};
        out.println("Calificaciones registradas : " + Arrays.toString(notas));
        long suma = Iteracion.suma(notas);
        out.println("Nota máxima (iterativo)    : " + Iteracion.maximo(notas));
        out.println("Nota máxima (recursivo)    : " + Recursion.maximo(notas));
        out.println("Suma de calificaciones     : " + suma);
        out.printf(Locale.ROOT, "Promedio del curso         : %.2f%n", (double) suma / notas.length);
        ResultadoOrdenacion orden = Ordenacion.shellSort(notas);
        out.println("Ordenadas (Shell Sort)     : " + Arrays.toString(orden.arreglo()));
        int[] ranking = orden.arreglo();
        StringBuilder sb = new StringBuilder();
        for (int i = ranking.length - 1; i >= 0; i--) {
            sb.append(ranking.length - i).append("º=").append(ranking[i]).append(i > 0 ? "  " : "");
        }
        out.println("Ranking (mayor a menor)    : " + sb);
    }



    // Actividad 4: benchmark completo; guarda CSV, tabla Markdown y datos del equipo.
    public static void actividad4(PrintStream out, Path salida) throws IOException {
        encabezado(out, "ACTIVIDAD 4 - BENCHMARK Y COMPARACIÓN EXPERIMENTAL");
        out.println("Características del equipo:");
        out.println(Benchmark.infoEquipo());
        out.println();
        out.println("Ejecutando benchmark (4 algoritmos x 4 tamaños x 5 tipos de datos x "
                + Benchmark.REPETICIONES + " repeticiones)...");

        List<Benchmark.Fila> filas = Benchmark.ejecutar(out);   // sin impresiones durante la medición

        out.println();
        Benchmark.imprimirTabla(filas, out);

        Benchmark.guardarCsv(filas, salida.resolve("benchmark_resultados.csv"));
        Benchmark.guardarMarkdown(filas, salida.resolve("benchmark_resultados.md"));
        guardar(salida.resolve("equipo.txt"), Benchmark.infoEquipo() + "\n");
        out.println();
        out.println("Archivos generados en " + salida.toAbsolutePath() + ":");
        out.println("  benchmark_resultados.csv, benchmark_resultados.md, equipo.txt");
    }

    // ---------------------------------------------------------------- Utilidades

    private static void encabezado(PrintStream out, String titulo) {
        out.println();
        out.println("=".repeat(100));
        out.println(titulo);
        out.println("=".repeat(100));
    }

    private static void guardar(Path archivo, String contenido) throws IOException {
        if (archivo.getParent() != null) {
            Files.createDirectories(archivo.getParent());
        }
        Files.writeString(archivo, contenido, StandardCharsets.UTF_8);
    }
}
