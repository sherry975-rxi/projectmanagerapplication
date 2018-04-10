package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.dto.UserDTO;
import project.model.EmailMessage;
import project.model.SendEmail;
import project.model.User;
import project.services.UserService;

import javax.mail.MessagingException;

@Controller
public class US101RegisterUserController {
	@Autowired
	private UserService userService;

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
			String street, String zipCode, String city, String district, String country) {
		UserDTO newUser = new UserDTO(name, email, idNumber, function, phone, password);
		newUser.setUserAddress(street, zipCode, city, district, country);

		userService.createUserWithDTO(newUser);
	}

	/**
	 * Sends Verification Code
	 * @param email
	 * @throws MessagingException
	 */
	public void sendVerificationCode (String email) throws MessagingException {

		SendEmail sendEmail = new SendEmail();
		EmailMessage emailMessage = new EmailMessage();

		User user = userService.getUserByEmail(email);
		emailMessage.setEmailAddress(email);

		String emailSubject = "Verification Code";
		emailMessage.setSubject(emailSubject);

		String message = "This the code you should provide for register in Project Management App:  ";
		String generatedCode = user.getGeneratedCode();
		emailMessage.setBody(message + generatedCode);

		sendEmail.sendMail(emailMessage);
	}

	public Boolean doesCodeGeneratedMatch (String codeToCheck, String recipientEmail){

		User user = userService.getUserByEmail(recipientEmail);

		Boolean doCodesMatch = user.doesCodeGeneratedMatch(codeToCheck);

		if (!doCodesMatch){
			userService.deleteUser(recipientEmail);
		} else {
			user.setGeneratedCode("");
			userService.updateUser(user);
		}

		return doCodesMatch;
	}


	/**
	 *
	 * @param email The email to check if exists in the user repository
	 * @return TRUE if there's an user with the email, FALSE if it doesn't
	 */
	public boolean isUserInUserRepository(String email) {
		
		boolean isUserInUserRepository = userService.isUserEmailInUserContainer(email);

		return isUserInUserRepository;

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