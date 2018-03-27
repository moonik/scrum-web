package scrumweb.exception;

public class RemoveFromProjectException extends RuntimeException {
    public RemoveFromProjectException(String s) {
        super("Cant find member " + s + " in project.");
    }

    public RemoveFromProjectException(String s, String project) {
        super(s + " is owner of project " + project + ".");
    }

}
