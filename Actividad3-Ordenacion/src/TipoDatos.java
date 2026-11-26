package ec.edu.uce.algoritmos;

/** Distribuciones de datos utilizadas en el benchmark (Actividad 4). */
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

    /** Nombre legible para tablas e informes. */
    public String etiqueta() {
        return etiqueta;
    }
}
