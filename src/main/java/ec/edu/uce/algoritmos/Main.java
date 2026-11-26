package ec.edu.uce.algoritmos;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println("      ALGORITHMS PROJECT");
        System.out.println("================================");

        activity1();
        activity2();
        MainOrdenacion.main(args);
        // activity4();

        System.out.println("\n================================");
        System.out.println("        END OF PROGRAM");
        System.out.println("================================");


    }

    // ================= ACTIVITY 1 =================
    private static void activity1() {

        System.out.println("\n================================");
        System.out.println("          ACTIVITY 1");
        System.out.println("================================");

        int[] data = {5, 10, 15, 20};

        System.out.println("\n--- ALGORITHM A: O(n) ---");
        Complejidad.linearSum(data);

        System.out.println("\n--- ALGORITHM B: O(n^2) ---");
        Complejidad.quadraticComparison(data);

        System.out.println("\n--- ALGORITHM C: O(log n) ---");
        Complejidad.halve(20);

        int[] sizes = {10, 100, 1000, 10000};
        Random random = new Random();

        System.out.println("\n--- TESTS WITH DIFFERENT SIZES ---");

        for (int n : sizes) {

            int[] numbers = new int[n];

            for (int i = 0; i < n; i++) {
                numbers[i] = random.nextInt(1000) + 1;
            }

            System.out.println("\nArray size: " + n);

            Complejidad.linearSum(numbers);
            Complejidad.quadraticComparison(numbers);
            Complejidad.halve(n);
        }
    }

    // ================= ACTIVITY 2 =================
    private static void activity2() {

        System.out.println("\n================================");
        System.out.println("          ACTIVITY 2");
        System.out.println("================================");

        int number = 5;
        int[] values = {8, 3, 10, 2, 7};

        int[] operations = {0};
        long start;
        long elapsed;

        // ---------- FACTORIAL ----------
        System.out.println("\n--- FACTORIAL ---");

        operations[0] = 0;
        start = System.nanoTime();
        long iterativeFactorial = Iteracion.factorial(number, operations);
        elapsed = System.nanoTime() - start;
        System.out.println("Iterative: " + iterativeFactorial
                + " | Operations: " + operations[0] + " | Time: " + elapsed + " ns");

        operations[0] = 0;
        start = System.nanoTime();
        long recursiveFactorial = Recursion.factorial(number, operations);
        elapsed = System.nanoTime() - start;
        System.out.println("Recursive: " + recursiveFactorial
                + " | Operations: " + operations[0] + " | Time: " + elapsed + " ns");

        System.out.println("Do the results match? " + (iterativeFactorial == recursiveFactorial));

        // ---------- ARRAY SUM ----------
        System.out.println("\n--- ARRAY SUM ---");

        operations[0] = 0;
        start = System.nanoTime();
        long iterativeSum = Iteracion.arraySum(values, operations);
        elapsed = System.nanoTime() - start;
        System.out.println("Iterative: " + iterativeSum
                + " | Operations: " + operations[0] + " | Time: " + elapsed + " ns");

        operations[0] = 0;
        start = System.nanoTime();
        long recursiveSum = Recursion.arraySum(values, 0, operations);
        elapsed = System.nanoTime() - start;
        System.out.println("Recursive: " + recursiveSum
                + " | Operations: " + operations[0] + " | Time: " + elapsed + " ns");

        System.out.println("Do the results match? " + (iterativeSum == recursiveSum));

        // ---------- ARRAY MAXIMUM ----------
        System.out.println("\n--- ARRAY MAXIMUM ---");

        operations[0] = 0;
        start = System.nanoTime();
        int iterativeMax = Iteracion.arrayMax(values, operations);
        elapsed = System.nanoTime() - start;
        System.out.println("Iterative: " + iterativeMax
                + " | Comparisons: " + operations[0] + " | Time: " + elapsed + " ns");

        operations[0] = 0;
        start = System.nanoTime();
        int recursiveMax = Recursion.arrayMax(values, 0, operations);
        elapsed = System.nanoTime() - start;
        System.out.println("Recursive: " + recursiveMax
                + " | Comparisons: " + operations[0] + " | Time: " + elapsed + " ns");

        System.out.println("Do the results match? " + (iterativeMax == recursiveMax));

        // ---------- STACK OVERFLOW RISK ----------
        System.out.println("\n--- STACK OVERFLOW (array of 1,000,000 elements) ---");

        int[] huge = new int[1000000];
        operations[0] = 0;

        System.out.println("Iterative sum: " + Iteracion.arraySum(huge, operations));

        try {
            System.out.println("Recursive sum: " + Recursion.arraySum(huge, 0, operations));
        } catch (StackOverflowError e) {
            System.out.println("Recursive sum: StackOverflowError (the call stack was exhausted)");
        }
    }


}