package project.restcontroller.exceptions;


import org.junit.Test;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import project.services.exceptions.ObjectNotFoundException;

import static org.junit.Assert.*;


public class ResourceExceptionHandlerTest {


    /**
     * GIVEN a ResourceExceptionHandler
     * WHEN the method objectNotFound is called
     * THEN a response entity with the status NOT_FOUND must be returned
     */
    @Test
    public void objectNotFound(){


        MockHttpServletRequest request = new  MockHttpServletRequest();
        ObjectNotFoundException exception = new ObjectNotFoundException("Error");

        //GIVEN a ResourceExceptionHandler
        ResourceExceptionHandler handler = new ResourceExceptionHandler();

        //WHEN the method objectNotFound is called
        ResponseEntity<StandardError> rresponseResult = handler.objectNotFound(exception, request);

        //THEN a response entity with the status NOT_FOUND must be returned
        assertEquals(HttpStatus.NOT_FOUND, rresponseResult.getStatusCode());

    }

    /**
     * GIVEN a ResourceExceptionHandler
     * WHEN the method profileNotFound is called
     * THEN a response entity with the status NOT_FOUND must be returned
     */
    @Test
    public void profileNotFound() {

        MockHttpServletRequest request = new  MockHttpServletRequest();
        IllegalArgumentException exception = new IllegalArgumentException("Error");

        //GIVEN a ResourceExceptionHandler
        ResourceExceptionHandler handler = new ResourceExceptionHandler();

        //WHEN the method objectNotFound is called
        ResponseEntity<StandardError> rresponseResult = handler.profileNotFound(exception, request);

        //THEN a response entity with the status NOT_FOUND must be returned
        assertEquals(HttpStatus.NOT_FOUND, rresponseResult.getStatusCode());



    }
}