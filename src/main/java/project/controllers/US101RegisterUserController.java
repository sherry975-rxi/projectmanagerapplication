package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.dto.UserDTO;
import project.model.CodeGenerator;
import project.model.EmailMessage;
import project.model.sendcode.SendCodeFactory;
import project.model.sendcode.ValidationMethod;
import project.services.UserService;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class US101RegisterUserController {
	@Autowired
	private UserService userService;

    private String correctCode;

	public US101RegisterUserController() {
		//Empty constructor created for JPA integration tests

	}

	/**
	 * After creating the Controller, this method is called to both create and add
	 * the User
	 *
	 * @param name
	 *            Name of the user
	 * @param email
	 *            Email of the User
	 * @param idNumber
	 *            idNumber of the User
	 * @param function
	 *            function of the User
	 * @param phone
	 *            phone of the User
	 * @param question
	 *            security question
	 * @param answer
	 * 			  answer for the question
	 */

    public UserDTO createUserDTO(String name, String email, String idNumber, String function, String phone, String question, String answer) {

       UserDTO userDTO= new UserDTO(name, email, idNumber, function, phone, question , answer);

       return userDTO;
	}

	/**
	 * Sends Verification Code
	 * @param email
	 * @throws MessagingException
	 */
	public void sendVerificationCode (String email, String senderType) throws MessagingException, IOException {

		EmailMessage emailMessage = new EmailMessage();

		emailMessage.setEmailAddress(email);

		String emailSubject = "Verification Code";
		emailMessage.setSubject(emailSubject);

        CodeGenerator codeGenerator = new CodeGenerator();

        correctCode = codeGenerator.generateCode();

		String message = "This is the code you should provide for register in Project Management App:  "
                + correctCode;

		SendCodeFactory sendCodeFactory;

		sendCodeFactory = new SendCodeFactory();

		ValidationMethod validationMethod = sendCodeFactory.getCodeSenderType(senderType).orElse(null);

		String userPhone = userService.getUserByEmail(email).getPhone();

		String userQuestion = userService.getUserByEmail(email).getQuestion();

        if (validationMethod != null) {
            validationMethod.performValidationMethod(userPhone, email, userQuestion, message);
        }


	}

	public Boolean doesCodeGeneratedMatch (String codeToCheck, String recipientEmail){


        Boolean doCodesMatch = correctCode.equals(codeToCheck);

		if (!doCodesMatch){
			userService.deleteUser(recipientEmail);
		}

		return doCodesMatch;
	}


	/**
	 *
	 * @param email The email to check if exists in the user repository
	 * @return TRUE if there's an user with the email, FALSE if it doesn't
	 */
	public boolean isUserInUserRepository(String email) {

		return userService.isUserEmailInUserContainer(email);


	}

	public boolean isUserEmailValid(String email) {
		return userService.isEmailAddressValid(email);
	}

	public boolean wasUserAdded(boolean wasAdded) {
		return wasAdded;
	}

	public boolean isEmailValidController(String email) {
		return this.userService.isEmailAddressValid(email);
	}

    /**
     * Adds an address to the userDTO
     *
     * @param street   street of the User
     * @param zipCode  zipCode of the User
     * @param city     cityof the User
     * @param district district of the User
     * @param country  country of the User
     * @return
     */
    public UserDTO setAddress(UserDTO userDTO, String street, String zipCode, String city, String district, String country) {

        userDTO.setUserAddress(street, zipCode, city, district, country);
        return userDTO;
    }

    public void addNewUserToDbFromDTO(UserDTO userDTO) {
        userService.createUserWithDTO(userDTO);
    }

}