package project.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.test.context.junit4.SpringRunner;

import project.model.*;
import project.model.sendcode.ValidationMethod;
import project.services.UserService;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class US105CreatePasswordAndAuthenticationMechanismControllerTest {

    @InjectMocks
    US105CreatePasswordAndAuthenticationMechanismController controller;

    @Mock
    UserService userService;

    @Mock
    CodeGenerator codeGenerator;

    @Mock
    User user1;

    @Test
    public void setUserPassword() {

        controller.setUserPassword(user1, "testPass");

        verify(user1,times(1)).setPassword("testPass");

        verify(userService,times(1)).updateUser(user1);

    }

    @Test
    public void performAuthentication() throws IOException, MessagingException {

        when(codeGenerator.generateCode()).thenReturn("0000");

        assertTrue("SMS sent! Please input the code sent to you:".equals
                (controller.performAuthentication(user1.getPhone(),user1.getEmail(),user1.getQuestion(),"1")));

    }

    @Test
    public void performAuthenticationWhenInvalid() throws IOException, MessagingException {

        when(codeGenerator.generateCode()).thenReturn("0000");

        assertTrue("Invalid method selected. Please choose a valid one.".equals
                (controller.performAuthentication(user1.getPhone(),user1.getEmail(),user1.getQuestion(),"5")));

    }

    @Test
    public void isCodeValid() throws IOException, MessagingException {

        when(codeGenerator.generateCode()).thenReturn("0000");

        controller.performAuthentication(user1.getPhone(),user1.getEmail(),user1.getQuestion(),"1");

        assertTrue(controller.isCodeValid("0000", user1));

    }

    @Test
    public void isCodeWhenNotValid() throws IOException, MessagingException {

        when(codeGenerator.generateCode()).thenReturn("0000");

        controller.performAuthentication(user1.getPhone(),user1.getEmail(),user1.getQuestion(),"1");

        assertFalse(controller.isCodeValid("1111", user1));

    }

    @Test
    public void getValidation() throws IOException, MessagingException {

        when(codeGenerator.generateCode()).thenReturn("0000");

        controller.performAuthentication(user1.getPhone(),user1.getEmail(),user1.getQuestion(),"1");

        assertTrue(controller.getValidation() instanceof ValidationMethod);


    }
}
