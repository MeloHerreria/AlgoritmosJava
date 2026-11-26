package ec.edu.uce.algoritmos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IteracionTest {

    int[] values = {8, 3, 10, 2, 7};

    // ---------- Normal cases ----------

    @Test
    public void testFactorial() {
        int[] operations = {0};

        assertEquals(120, Iteracion.factorial(5, operations));
        assertEquals(4, operations[0]);
    }

    @Test
    public void testArraySum() {
        int[] operations = {0};

        assertEquals(30, Iteracion.arraySum(values, operations));
        assertEquals(5, operations[0]);
    }

    @Test
    public void testArrayMax() {
        int[] operations = {0};

        assertEquals(10, Iteracion.arrayMax(values, operations));
        assertEquals(4, operations[0]);
    }

    // ---------- Edge cases ----------

    @Test
    public void testFactorialOfZero() {
        assertEquals(1, Iteracion.factorial(0, new int[]{0}));
    }

    @Test
    public void testFactorialOfTwenty() {
        assertEquals(2432902008176640000L, Iteracion.factorial(20, new int[]{0}));
    }

    @Test
    public void testSingleElementArray() {
        int[] single = {42};

        assertEquals(42, Iteracion.arraySum(single, new int[]{0}));
        assertEquals(42, Iteracion.arrayMax(single, new int[]{0}));
    }

    // ---------- Invalid inputs ----------

    @Test
    public void testNegativeFactorial() {
        assertThrows(IllegalArgumentException.class, () -> {
            Iteracion.factorial(-5, new int[]{0});
        });
    }

    @Test
    public void testFactorialGreaterThan20() {
        assertThrows(IllegalArgumentException.class, () -> {
            Iteracion.factorial(21, new int[]{0});
        });
    }

    @Test
    public void testArraySumEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            Iteracion.arraySum(new int[]{}, new int[]{0});
        });
    }

    @Test
    public void testArrayMaxEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            Iteracion.arrayMax(new int[]{}, new int[]{0});
        });
    }

    @Test
    public void testArraySumNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            Iteracion.arraySum(null, new int[]{0});
        });
    }

    @Test
    public void testArrayMaxNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            Iteracion.arrayMax(null, new int[]{0});
        });
    }
}