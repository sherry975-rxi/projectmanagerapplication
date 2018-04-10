package project.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.*;
import project.services.UserService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"project.services", "project.model", "project.controllers"})
public class US105CreatePasswordAndAuthenticationMechanismControllerTest {

    @Autowired
    UserService userService;

    @InjectMocks
    US105CreatePasswordAndAuthenticationMechanismController controller;

    @Mock
    SendSMS smsMessage;

    @Mock
    SendEmail sendEmail;

    @Mock
    CodeGenerator codeGenerator;

    User user1;

    @Before
    public void setUp() {

        // create user
        user1 = userService.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
                "2401-00", "Test", "Testo", "Testistan");

        // set user as collaborator
        user1.setUserProfile(Profile.COLLABORATOR);

        // set question to user
        user1.setQuestion("1");

        //set answer to user
        user1.setAnswer("test answer");

        userService.updateUser(user1);

    }

    @After
    public void clear() {

        user1 = null;
    }


    @Test
    public void setUserPasswordTest() {
        String newPassword = "testPassword";

        assertFalse(user1.hasPassword());

        //controller.setUserPassword(user1, newPassword);

        //assertTrue(user1.hasPassword());

    }

    @Test
    public void questionAuthenticationTest() {

        assertEquals("1",controller.questionAuthentication(user1));

    }

    @Test
    public void isRightAnswerTest() {

        assertTrue(controller.isRightAnswer("test answer", user1));

    }

    @Test
    public void smsAuthenticationTest() {

        controller.smsAuthentication("+351937429087");

        verify(smsMessage, times(1)).sendMessage(anyString(), anyString());

    }

    @Test
    public void emailAuthenticationTest() throws Exception{

        controller.emailAuthentication("dsomonteiro@gmail.com");

        verify(sendEmail, times(1)).sendMail(anyObject());

    }

    @Test
    public void isCodeValidTest() {

        when(codeGenerator.generateCode()).thenReturn("123456");

        controller.emailAuthentication("dsomonteiro@gmail.com");

        assertTrue(controller.isCodeValid("123456"));

    }
}
