package project.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.CodeGenerator;
import project.model.User;
import project.model.sendcode.AnswerValidation;
import project.model.sendcode.SMSSender;
import project.model.sendcode.SendCodeFactory;
import project.model.sendcode.ValidationMethod;
import project.services.UserService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class US105CreatePasswordAndAuthenticationMechanismControllerTest {

    @InjectMocks
    US105CreatePasswordAndAuthenticationMechanismController controller;

    @Mock
    UserService userService;

    @Mock
    CodeGenerator codeGenerator;

    @Mock
    SMSSender smsSender;

    @Mock
    AnswerValidation answerValidation;

    @Mock
    SendCodeFactory sendCodeFactory;

    @Mock
    User user1;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Test
    public void setUserPassword() {

        when(passwordEncoder.encode(anyString())).thenReturn("testPass");

        controller.setUserPassword(user1, "testPass");

        verify(user1,times(1)).setPassword("testPass");

        verify(userService,times(1)).updateUser(user1);

    }

    @Test
    public void performAuthentication() throws IOException, MessagingException {

        when(codeGenerator.generateCode()).thenReturn("0000");

        when(sendCodeFactory.getCodeSenderType("1")).thenReturn(Optional.of(smsSender));

        when(smsSender.performValidationMethod(anyString(), anyString(), anyString(), anyString())).thenReturn("DONE!");

        assertTrue("DONE!".equals
                (controller.performAuthentication("phone", "email", "question", "1")));

    }

    @Test
    public void performAuthenticationWithNull() throws IOException, MessagingException {

        when(codeGenerator.generateCode()).thenReturn("0000");

        when(sendCodeFactory.getCodeSenderType("1")).thenReturn(Optional.empty());

        assertTrue("Invalid method selected. Please choose a valid one.".equals
                (controller.performAuthentication("phone", "email", "question", "1")));

    }

    @Test
    public void isCodeValid() throws IOException, MessagingException {

        when(codeGenerator.generateCode()).thenReturn("0000");

        when(sendCodeFactory.getCodeSenderType("1")).thenReturn(Optional.of(smsSender));

        when(smsSender.checkRightAnswer(anyString(), anyString())).thenReturn(true);

        controller.performAuthentication(user1.getPhone(),user1.getEmail(),user1.getQuestion(),"1");

        assertTrue(controller.isCodeValid("0000", user1));

    }

    @Test
    public void isCodeValidDifferentValidationMethod() throws IOException, MessagingException {

        when(codeGenerator.generateCode()).thenReturn("0000");

        when(sendCodeFactory.getCodeSenderType("1")).thenReturn(Optional.of(answerValidation));

        when(smsSender.checkRightAnswer(anyString(), anyString())).thenReturn(false);

        controller.performAuthentication(user1.getPhone(),user1.getEmail(),user1.getQuestion(),"1");

        assertFalse(controller.isCodeValid("1111", user1));

    }

    @Test
    public void getValidation() throws IOException, MessagingException {

        when(codeGenerator.generateCode()).thenReturn("0000");

        when(sendCodeFactory.getCodeSenderType("1")).thenReturn(Optional.of(smsSender));

        controller.performAuthentication(user1.getPhone(),user1.getEmail(),user1.getQuestion(),"1");

        assertTrue(controller.getValidation() instanceof ValidationMethod);


    }
}
