package ec.edu.uce.algoritmos;


public final class Metricas {

    private long operaciones;
    private long llamadas;


    public void operacion() {
        operaciones++;
    }


    public void llamada() {
        llamadas++;
    }

    public long getOperaciones() {
        return operaciones;
    }

    public long getLlamadas() {
        return llamadas;
    }

    public void reiniciar() {
        operaciones = 0;
        llamadas = 0;
    }
}
