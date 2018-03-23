package scrumweb.exception;

public class CantAssignToIssueException extends RuntimeException {
    public CantAssignToIssueException() {
        super("Cant assign nonmember to issue");
    }

    public CantAssignToIssueException(String s) {
        super(s + " is not assigned to this issue.");
    }

}
