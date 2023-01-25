package ir.ac.kntu;

public class SemanticException extends Exception{
    public SemanticException(String message, RuntimeException ex) {
        super(message);
    }
}
