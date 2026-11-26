package ec.edu.uce.algoritmos;


final class Validaciones {

    private Validaciones() {

    }


    static void arreglo(int[] datos) {
        if (datos == null) {
            throw new IllegalArgumentException("El arreglo no puede ser nulo.");
        }
        if (datos.length == 0) {
            throw new IllegalArgumentException("El arreglo no puede estar vacío.");
        }
    }

    static void factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException(
                    "El factorial no está definido para enteros negativos: n = " + n);
        }
        if (n > Iteracion.FACTORIAL_MAX) {
            throw new IllegalArgumentException("n = " + n + " excede el máximo permitido ("
                    + Iteracion.FACTORIAL_MAX + "): el resultado desbordaría el tipo long.");
        }
    }
}
