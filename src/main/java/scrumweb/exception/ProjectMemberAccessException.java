package scrumweb.exception;

public class ProjectMemberAccessException  extends RuntimeException{
    public ProjectMemberAccessException(String s) {
        super("Can not accept user " + s + " in project");
    }

    public ProjectMemberAccessException() {
        super("You cant decline request for access if it does not exists.");
    }
}
