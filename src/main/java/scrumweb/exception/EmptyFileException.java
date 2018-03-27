package scrumweb.exception;

public class EmptyFileException extends RuntimeException {
    public EmptyFileException (String filename) {
        super("Failed to store empty file " + filename);
    }
}
