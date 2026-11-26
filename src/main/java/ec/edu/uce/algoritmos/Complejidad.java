package ec.edu.uce.algoritmos;

public class Complejidad {

    // ALGORITHM A: Sum of the elements of an array - O(n)
    // n = array length | Dominant operation: the addition | T(n) = n
    public static void linearSum(int[] numbers) {
        long sum = 0;
        int operations = 0;

        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
            operations++;
        }

        System.out.println("\nALGORITHM A - O(n)");
        System.out.println("Sum: " + sum);
        System.out.println("Operations: " + operations);
        System.out.println("T(n) = n");
        System.out.println("Complexity: O(n)");
    }

    // ALGORITHM B: Comparison of each element with all the others - O(n²)
    // n = array length | Dominant operation: the comparison (i != j)
    // T(n) = n * (n - 1) = n² - n
    public static void quadraticComparison(int[] numbers) {
        int comparisons = 0;
        int smaller = 0;

        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                if (i != j) {
                    comparisons++;
                    if (numbers[i] < numbers[j]) {
                        smaller++;
                    }
                }
            }
        }

        System.out.println("\nALGORITHM B - O(n²)");
        System.out.println("Pairs where numbers[i] < numbers[j]: " + smaller);
        System.out.println("Comparisons: " + comparisons);
        System.out.println("T(n) = n² - n");
        System.out.println("Complexity: O(n²)");
    }

    // ALGORITHM C: Successive halving - O(log n)
    // n = value of the number | Dominant operation: the division by 2
    // T(n) = integer part of log2(n)
    public static void halve(int number) {
        if (number <= 0) {
            System.out.println("Error: enter a positive number.");
            return;
        }

        int operations = 0;
        int value = number;

        while (value > 1) {
            value = value / 2;
            operations++;
        }

        System.out.println("\nALGORITHM C - O(log n)");
        System.out.println("Initial number: " + number);
        System.out.println("Final value: " + value);
        System.out.println("Operations: " + operations);
        System.out.println("T(n) = log2(n)");
        System.out.println("Complexity: O(log n)");
    }
}