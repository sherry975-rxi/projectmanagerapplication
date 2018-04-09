package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.dto.UserDTO;
import project.model.User;
import project.services.UserService;

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

		setFirstLogin(userService.getUserByEmail(email));

		updateUser(userService.getUserByEmail(email));
	}

	public boolean isUserInUserRepository(String email) {
		return userService.getUserByEmail(email) != null;
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
	 * This method change the user variable firstLogin to false.
	 *
	 * @param user
	 */
	private void setFirstLogin(User user) {

		user.setHasLoggedIn(true);
	}

	/**
	 *  Method that saves the user to the database
	 *
	 * @param user
	 */
	private void updateUser(User user) {

		userService.updateUser(user);
	}

}