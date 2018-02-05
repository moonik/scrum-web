package scrumweb.exception;

public class ProjectAlreadyExsistsException extends RuntimeException {
    public ProjectAlreadyExsistsException(String projectkey) {
        super("Project with key "+projectkey+" already exists!");
    }

}
