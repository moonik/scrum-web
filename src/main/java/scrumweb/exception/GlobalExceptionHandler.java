package scrumweb.exception;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(Exception ex){
        return ex.getMessage();
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleBadCredentailsException(){
        return "Sorry, Username or password is incorrect!";
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleProjectNotFoundException(Exception ex){
        return ex.getMessage();
    }

    @ExceptionHandler(ProjectAlreadyExsistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleProjectAlreadyExistsException(Exception ex){
        return ex.getMessage();
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserAlreadyExistsException(Exception ex){
        return ex.getMessage();
    }

    @ExceptionHandler(EmptyFileException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String handleEmptyFileException(Exception ex) {
        return ex.getMessage();
    }
}
