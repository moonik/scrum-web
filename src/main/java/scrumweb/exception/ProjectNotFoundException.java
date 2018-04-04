package scrumweb.exception;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(String projectKey){
        super("Project with key "+projectKey+" not found!");
    }
}