package ec.edu.uce.algoritmos;


 // Actividad 2 (Semana 4)

public final class Recursion {

    private Recursion() {

    }

    public static long factorial(int n) {
        return factorial(n, new Metricas());
    }

    public static long factorial(int n, Metricas metricas) {
        Validaciones.factorial(n);
        return factorialRec(n, metricas);
    }

    private static long factorialRec(int n, Metricas metricas) {
        metricas.llamada();
        if (n <= 1) {
            return 1;
        }
        metricas.operacion();
        return n * factorialRec(n - 1, metricas);
    }


    public static long suma(int[] datos) {
        return suma(datos, new Metricas());
    }

    public static long suma(int[] datos, Metricas metricas) {
        Validaciones.arreglo(datos);
        return sumaRec(datos, 0, metricas);
    }

    private static long sumaRec(int[] datos, int indice, Metricas metricas) {
        metricas.llamada();
        if (indice == datos.length) {
            return 0;
        }
        metricas.operacion();
        return datos[indice] + sumaRec(datos, indice + 1, metricas);
    }

    public static int maximo(int[] datos) {
        return maximo(datos, new Metricas());
    }

    public static int maximo(int[] datos, Metricas metricas) {
        Validaciones.arreglo(datos);
        return maximoRec(datos, 0, metricas);
    }

    private static int maximoRec(int[] datos, int indice, Metricas metricas) {
        metricas.llamada();
        if (indice == datos.length - 1) {
            return datos[indice];
        }
        int maximoDelResto = maximoRec(datos, indice + 1, metricas);
        metricas.operacion();
        return datos[indice] > maximoDelResto ? datos[indice] : maximoDelResto;
    }
}
