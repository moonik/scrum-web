package scrumweb.exception;

public class IssueTypeDoesNotExists extends RuntimeException {
    public IssueTypeDoesNotExists(String s) {
        super("Issue type" + s + "does not exists!");
    }
}
