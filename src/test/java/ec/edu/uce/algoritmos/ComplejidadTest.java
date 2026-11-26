package ec.edu.uce.algoritmos;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComplejidadTest {

    private String captureOutput(Runnable action) {
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(output));
            action.run();
        } finally {
            System.setOut(originalOutput);
        }

        return output.toString();
    }

    @Test
    public void testLinearSum() {
        int[] numbers = {5, 10, 15, 20};

        String result = captureOutput(() -> Complejidad.linearSum(numbers));

        assertTrue(result.contains("Sum: 50"));
        assertTrue(result.contains("Operations: 4"));
        assertTrue(result.contains("O(n)"));
    }

    @Test
    public void testQuadraticComparison() {
        int[] numbers = {5, 10, 15, 20};

        String result = captureOutput(() -> Complejidad.quadraticComparison(numbers));

        assertTrue(result.contains("Comparisons: 12"));
        assertTrue(result.contains("O(n²)"));
    }

    @Test
    public void testHalve() {
        String result = captureOutput(() -> Complejidad.halve(16));

        assertTrue(result.contains("Final value: 1"));
        assertTrue(result.contains("Operations: 4"));
        assertTrue(result.contains("O(log n)"));
    }

    @Test
    public void testHalveInvalidNumber() {
        String result = captureOutput(() -> Complejidad.halve(0));

        assertTrue(result.contains("Error"));
    }
}