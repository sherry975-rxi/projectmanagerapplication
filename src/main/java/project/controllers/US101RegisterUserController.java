package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.dto.UserDTO;
import project.model.CodeGenerator;
import project.model.EmailMessage;
import project.model.User;
import project.model.sendcode.SendCodeFactory;
import project.model.sendcode.ValidationMethod;
import project.services.UserService;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class US101RegisterUserController {
	@Autowired
	private UserService userService;

	private CodeGenerator codeGenerator;

    private String generatedCode;

    private SendCodeFactory sendCodeFactory;

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
	 * @param password
	 *            password of the User
	 * @param street
	 *            street of the User
	 * @param zipCode
	 *            zipCode of the User
	 * @param city
	 *            cityof the User
	 * @param district
	 *            district of the User
	 * @param country
	 *            country of the User
	 */
	public void addNewUser(String name, String email, String idNumber, String function, String phone, String password,
			String street, String zipCode, String city, String district, String country, String question, String answer) {

		UserDTO newUser = new UserDTO(name, email, idNumber, function, phone, password, question, answer);
		newUser.setUserAddress(street, zipCode, city, district, country);

		userService.createUserWithDTO(newUser);

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

		codeGenerator = new CodeGenerator();
		String generatedCode = codeGenerator.generateCode();
		String message = "This is the code you should provide for register in Project Management App:  "
				+ generatedCode;

		sendCodeFactory = new SendCodeFactory();

		ValidationMethod validationMethod = sendCodeFactory.getCodeSenderType(senderType).get();

		String userPhone = userService.getUserByEmail(email).getPhone();

		String userQuestion = userService.getUserByEmail(email).getQuestion();

		validationMethod.performValidationMethod(userPhone, email, userQuestion, message);


	}

	public Boolean doesCodeGeneratedMatch (String codeToCheck, String recipientEmail){


		Boolean doCodesMatch = this.codeGenerator.doesCodeGeneratedMatch(codeToCheck);

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



}