package scrumweb.exception;

public class ProjectAlreadyExsistsException extends RuntimeException {
    public ProjectAlreadyExsistsException(String projectname) {
        super("Project with name "+projectname+" already exists!");
    }

}
