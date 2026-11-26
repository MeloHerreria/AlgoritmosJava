
package ec.edu.uce.algoritmos;

public class Iteracion {

    // FACTORIAL ITERATIVO
    public static long factorial(int n, int[] operations) {

        if (n < 0 || n > 20) {
            throw new IllegalArgumentException(
                    "The number must be between 0 and 20."
            );
        }

        long result = 1;

        for (int i = 2; i <= n; i++) {
            result = result * i;
            operations[0]++;
        }

        return result;
    }

    // SUMA ITERATIVA
    public static long arraySum(int[] numbers, int[] operations) {

        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException(
                    "The array cannot be empty."
            );
        }

        long sum = 0;

        for (int i = 0; i < numbers.length; i++) {
            sum = sum + numbers[i];
            operations[0]++;
        }

        return sum;
    }

    // MÁXIMO ITERATIVO
    public static int arrayMax(int[] numbers, int[] operations) {

        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException(
                    "The array cannot be empty."
            );
        }

        int max = numbers[0];

        for (int i = 1; i < numbers.length; i++) {
            operations[0]++;

            if (numbers[i] > max) {
                max = numbers[i];
            }
        }

        return max;
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

        registrarOperaciones(operations[0], metricas);

        return resultado;
    }

    public static long suma(int[] datos) {
        int[] operations = {0};
        return arraySum(datos, operations);
    }

    public static long suma(int[] datos, Metricas metricas) {
        validarMetricas(metricas);

        int[] operations = {0};
        long resultado = arraySum(datos, operations);

        registrarOperaciones(operations[0], metricas);

        return resultado;
    }

    public static int maximo(int[] datos) {
        int[] operations = {0};
        return arrayMax(datos, operations);
    }

    public static int maximo(int[] datos, Metricas metricas) {
        validarMetricas(metricas);

        int[] operations = {0};
        int resultado = arrayMax(datos, operations);

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

    private static void registrarOperaciones(
            int cantidad, Metricas metricas) {

        for (int i = 0; i < cantidad; i++) {
            metricas.operacion();
        }
    }
}
