package project.services.exceptions;


/**
 * This class was created to throw an exception in the service layer  whenever an authorization finds an error
 */
public class AuthorizationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor for class that receives an exception message and passes that message to the superClass
     * RuntimeException
     *
     * @param msg Exception message received
     */

    public AuthorizationException(String msg) {
        super(msg);
    }

    /**
     * Constructor for class that receives an exception message and the cause for some exception that
     * occurs before the exception message is thrown and passes the message and the cause to the superClass
     * RuntimeException
     *
     * @param msg   Exception message received
     * @param cause Cause for some exception that occurs before the exception message is thrown
     */
    public AuthorizationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
