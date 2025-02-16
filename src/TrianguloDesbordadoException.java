public class TrianguloDesbordadoException extends Exception {

    public TrianguloDesbordadoException() {
        super("El triángulo se ha desbordado fuera del área visible del JPanel.");
    }

    public TrianguloDesbordadoException(String mensaje) {
        super(mensaje);
    }
}
