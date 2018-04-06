package project.restcontroller.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import project.services.exceptions.ObjectNotFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * This class intercepts the Exceptions thrown by the services classes to the restcontroller Classes
 */
@ControllerAdvice
public class ResourceExceptionHandler {

    /**
     * This method returns the  Http status of the error thrown, the Error message and Instant when the error occurred
     * when an exception is thrown by the services classes upon not finding the searched object in the Repository.
     * This is the standard signature for this notFound method in the Framework with annotation @ControllerAdvice
     *
     * @param e       Exception thrown message and cause
     * @param request Http request
     * @return
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

        //StandardError for object not found
        StandardError error = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
