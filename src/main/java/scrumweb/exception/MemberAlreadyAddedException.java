package scrumweb.exception;

public class MemberAlreadyAddedException extends RuntimeException{
    public MemberAlreadyAddedException(String s) {
        super("Member " + s + " already added to project.");
    }
}
