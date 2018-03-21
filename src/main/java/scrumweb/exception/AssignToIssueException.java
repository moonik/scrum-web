package scrumweb.exception;

public class AssignToIssueException extends RuntimeException {
    public AssignToIssueException() {
        super("Cant assign nonmember to issue");
    }

    public AssignToIssueException(String s) {
        super(s + " is not assigned to this issue.");
    }

}
