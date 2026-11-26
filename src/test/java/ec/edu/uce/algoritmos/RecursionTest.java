package ec.edu.uce.algoritmos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecursionTest {

    int[] values = {8, 3, 10, 2, 7};

    // ---------- Normal cases ----------

    @Test
    public void testFactorial() {
        int[] operations = {0};

        assertEquals(120, Recursion.factorial(5, operations));
        assertEquals(4, operations[0]);
    }

    @Test
    public void testArraySum() {
        int[] operations = {0};

        assertEquals(30, Recursion.arraySum(values, 0, operations));
        assertEquals(5, operations[0]);
    }

    @Test
    public void testArrayMax() {
        int[] operations = {0};

        assertEquals(10, Recursion.arrayMax(values, 0, operations));
        assertEquals(4, operations[0]);
    }

    // ---------- Edge cases ----------

    @Test
    public void testFactorialOfZero() {
        assertEquals(1, Recursion.factorial(0, new int[]{0}));
    }

    @Test
    public void testFactorialOfTwenty() {
        assertEquals(2432902008176640000L, Recursion.factorial(20, new int[]{0}));
    }

    @Test
    public void testSingleElementArray() {
        int[] single = {42};

        assertEquals(42, Recursion.arraySum(single, 0, new int[]{0}));
        assertEquals(42, Recursion.arrayMax(single, 0, new int[]{0}));
    }

    // ---------- Invalid inputs ----------

    @Test
    public void testNegativeFactorial() {
        assertThrows(IllegalArgumentException.class, () -> {
            Recursion.factorial(-5, new int[]{0});
        });
    }

    @Test
    public void testFactorialGreaterThan20() {
        assertThrows(IllegalArgumentException.class, () -> {
            Recursion.factorial(21, new int[]{0});
        });
    }

    @Test
    public void testArraySumEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            Recursion.arraySum(new int[]{}, 0, new int[]{0});
        });
    }

    @Test
    public void testArrayMaxEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            Recursion.arrayMax(new int[]{}, 0, new int[]{0});
        });
    }

    @Test
    public void testArraySumNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            Recursion.arraySum(null, 0, new int[]{0});
        });
    }

    @Test
    public void testArrayMaxNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            Recursion.arrayMax(null, 0, new int[]{0});
        });
    }
}