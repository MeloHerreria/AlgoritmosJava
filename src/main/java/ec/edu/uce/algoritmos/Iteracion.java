package ec.edu.uce.algoritmos;

public class Iteracion {

    // ITERATIVE FACTORIAL - Time O(n), space O(1)
    // Counts the multiplications: n - 1 (the loop starts at 2)
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

    // ITERATIVE SUM - Time O(n), space O(1)
    // Counts the additions: n
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

    // ITERATIVE MAXIMUM - Time O(n), space O(1)
    // Counts the comparisons: n - 1
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
}