package ec.edu.uce.algoritmos;

public class Recursion {

    // RECURSIVE FACTORIAL - Time O(n), space O(n) due to the call stack
    // Base case: n <= 1. Counts the multiplications: n - 1
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

    // RECURSIVE SUM - Time O(n), space O(n) due to the call stack
    // Called with index = 0. Base case: index == numbers.length
    // Counts the additions: n
    public static long arraySum(int[] numbers, int index, int[] operations) {

        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException(
                    "The array cannot be empty."
            );
        }

        if (index == numbers.length) {
            return 0;
        }

        operations[0]++;

        return numbers[index] + arraySum(numbers, index + 1, operations);
    }

    // RECURSIVE MAXIMUM - Time O(n), space O(n) due to the call stack
    // Called with index = 0. Base case: last element of the array
    // Counts the comparisons: n - 1
    public static int arrayMax(int[] numbers, int index, int[] operations) {

        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException(
                    "The array cannot be empty."
            );
        }

        if (index == numbers.length - 1) {
            return numbers[index];
        }

        int remainingMax = arrayMax(numbers, index + 1, operations);

        operations[0]++;

        if (numbers[index] > remainingMax) {
            return numbers[index];
        } else {
            return remainingMax;
        }
    }
}