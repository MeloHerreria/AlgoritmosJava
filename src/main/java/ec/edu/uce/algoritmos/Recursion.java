
package ec.edu.uce.algoritmos;

public class Recursion {

    // FACTORIAL RECURSIVO
    public static long factorial(int n, int[] operations) {

        if (n < 0 || n > 20) {
            throw new IllegalArgumentException(
                    "The number must be between 0 and 20."
            );
        }

        if (n <= 1) {
            return 1;
        }

        operations[0]++;

        return n * factorial(n - 1, operations);
    }

    // SUMA RECURSIVA
    public static long arraySum(
            int[] numbers, int index, int[] operations) {

        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException(
                    "The array cannot be empty."
            );
        }

        if (index == numbers.length) {
            return 0;
        }

        operations[0]++;

        return numbers[index] +
                arraySum(numbers, index + 1, operations);
    }

    // MÁXIMO RECURSIVO
    public static int arrayMax(
            int[] numbers, int index, int[] operations) {

        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException(
                    "The array cannot be empty."
            );
        }

        if (index == numbers.length - 1) {
            return numbers[index];
        }

        int remainingMax = arrayMax(
                numbers, index + 1, operations
        );

        operations[0]++;

        if (numbers[index] > remainingMax) {
            return numbers[index];
        } else {
            return remainingMax;
        }
    }

    // =========================================
    // MÉTODOS COMPATIBLES CON DEMOSTRACIONES
    // =========================================

    public static long factorial(int n) {
        int[] operations = {0};
        return factorial(n, operations);
    }

    public static long factorial(int n, Metricas metricas) {
        validarMetricas(metricas);

        int[] operations = {0};
        long resultado = factorial(n, operations);

        int llamadas = Math.max(1, n);
        registrarLlamadas(llamadas, metricas);
        registrarOperaciones(operations[0], metricas);

        return resultado;
    }

    public static long suma(int[] datos) {
        int[] operations = {0};
        return arraySum(datos, 0, operations);
    }

    public static long suma(int[] datos, Metricas metricas) {
        validarMetricas(metricas);
        validarArreglo(datos);

        int[] operations = {0};
        long resultado = arraySum(datos, 0, operations);

        registrarLlamadas(datos.length + 1, metricas);
        registrarOperaciones(operations[0], metricas);

        return resultado;
    }

    public static int maximo(int[] datos) {
        int[] operations = {0};
        return arrayMax(datos, 0, operations);
    }

    public static int maximo(int[] datos, Metricas metricas) {
        validarMetricas(metricas);
        validarArreglo(datos);

        int[] operations = {0};
        int resultado = arrayMax(datos, 0, operations);

        registrarLlamadas(datos.length, metricas);
        registrarOperaciones(operations[0], metricas);

        return resultado;
    }

    private static void validarMetricas(Metricas metricas) {
        if (metricas == null) {
            throw new IllegalArgumentException(
                    "Metricas cannot be null."
            );
        }
    }

    private static void validarArreglo(int[] datos) {
        if (datos == null || datos.length == 0) {
            throw new IllegalArgumentException(
                    "The array cannot be empty."
            );
        }
    }

    private static void registrarOperaciones(
            int cantidad, Metricas metricas) {

        for (int i = 0; i < cantidad; i++) {
            metricas.operacion();
        }
    }

    private static void registrarLlamadas(
            int cantidad, Metricas metricas) {

        for (int i = 0; i < cantidad; i++) {
            metricas.llamada();
        }
    }
}
