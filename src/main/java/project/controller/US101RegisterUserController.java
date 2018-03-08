package project.controller;

import project.dto.UserDTO;
import project.model.Company;
import project.model.User;
import project.model.UserContainer;

public class US101RegisterUserController {

	UserContainer userRegistry;

	/**
	 * This constructor creates a user Registration controller. *
	 * 
	 */
	public US101RegisterUserController() {

		this.userRegistry = Company.getTheInstance().getUsersContainer();

	}


	/**
	 * After creating the Controller, this method is called to both create and add
	 * the User
	 *
	 * @param name Name of the user
	 * @param email Email of the User
	 * @param idNumber idNumber of the User
	 * @param function function of the User
	 * @param phone phone of the User
	 * @param password password of the User
	 * @param street street of the User
	 * @param zipCode zipCode of the User
	 * @param city cityof the User
	 * @param district district of the User
	 * @param country country of the User
	 */
	public void addNewUser(String name, String email, String idNumber, String function, String phone, String password,
			String street, String zipCode, String city, String district, String country) {

		UserDTO newUser = new UserDTO(name, email, idNumber, function, phone, password);

		newUser.setUserAddress(street, zipCode, city, district, country);

		userRegistry.createUserWithDTO(newUser);
	}

	public boolean isUserInUserRepository(String email) {

		return userRegistry.getUserByEmail(email) != null;

	}

	public boolean isUserEmailValid(String email) {
		return userRegistry.isEmailAddressValid(email);
	}

	public boolean wasUserAdded(boolean wasAdded) {
		return wasAdded;
	}

	public boolean isEmailValidController(String email) {
		return this.userRegistry.isEmailAddressValid(email);

	}

}
