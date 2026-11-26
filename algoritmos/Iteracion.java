package ec.edu.uce.algoritmos;


 //Actividad 2
public final class Iteracion {

    public static final int FACTORIAL_MAX = 20;

    private Iteracion() {

    }


    public static long factorial(int n) {
        return factorial(n, new Metricas());
    }


    public static long factorial(int n, Metricas metricas) {
        Validaciones.factorial(n);
        long resultado = 1;
        for (int i = 2; i <= n; i++) {
            resultado *= i;
            metricas.operacion();
        }
        return resultado;
    }


    public static long suma(int[] datos) {
        return suma(datos, new Metricas());
    }

    public static long suma(int[] datos, Metricas metricas) {
        Validaciones.arreglo(datos);
        long acumulado = 0;
        for (int i = 0; i < datos.length; i++) {
            acumulado += datos[i];
            metricas.operacion();
        }
        return acumulado;
    }

    public static int maximo(int[] datos) {
        return maximo(datos, new Metricas());
    }


    public static int maximo(int[] datos, Metricas metricas) {
        Validaciones.arreglo(datos);
        int max = datos[0];
        for (int i = 1; i < datos.length; i++) {
            metricas.operacion();
            if (datos[i] > max) {
                max = datos[i];
            }
        }
        return max;
    }
}
