package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.model.*;
import project.services.UserService;
import project.ui.console.collaborator.US105CreatePasswordAndAuthenticationMechanismUI;

import javax.mail.MessagingException;

@Controller
public class US105CreatePasswordAndAuthenticationMechanismController {

    @Autowired
    private UserService userService;

    private String code;

    SendSMS sender = new SendSMS();

    SendEmail emailSender = new SendEmail();

    CodeGenerator codeGenerator = new CodeGenerator();

    public US105CreatePasswordAndAuthenticationMechanismController() {
        //Empty constructor created for JPA integration tests

    }

    /**
     * This method set a new password to user and change the variable firstLogin to false and
     * save the changes in DB
     *
     * @param user
     * @param newPassword
     */
    public void setUserPassword(User user, String newPassword) {

        user.setPassword(newPassword);

        userService.updateUser(user);
    }

    /**
     * Method to find and return the question associated with a specific user
     * @param user user whose question we are searching for
     * @return the question of the user
     */
    public String questionAuthentication(User user){

        return user.getQuestion();

    }

    /**
     * Method to determine if the answer provided is the right one
     * @param answer the answer provided by the user
     * @param user the user trying to login
     * @return true if it's the right answer, false if it isn't
     */
    public boolean isRightAnswer(String answer, User user){

        return answer.equalsIgnoreCase(user.getAnswer());
    }

    public void smsAuthentication(String phone) {

        String code = codeGenerator.generateCode();
        this.code = code;

        sender.sendMessage(code, phone);

    }

    public void emailAuthentication(String email) {

        String code = codeGenerator.generateCode();
        this.code = code;
        EmailMessage emsg = new EmailMessage();

        emsg.setSubject("Validation Code!");
        emsg.setEmailAddress(email);
        emsg.setBody("Please enter this code to validate your account:\n\n" + code);

        try {
            emailSender.sendMail(emsg);
        } catch (MessagingException e) {
            US105CreatePasswordAndAuthenticationMechanismUI.errorSendingEmail();
        }
    }

    public boolean isCodeValid(String code) {
        return code.equals(this.code);
    }

}
