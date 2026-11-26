package ec.edu.uce.algoritmos;


public enum TipoDatos {
    ASCENDENTE("Ascendentes"),
    DESCENDENTE("Descendentes"),
    ALEATORIO("Aleatorios"),
    REPETIDOS("Con repetidos"),
    CASI_ORDENADO("Casi ordenados");

    private final String etiqueta;

    TipoDatos(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String etiqueta() {
        return etiqueta;
    }
}
