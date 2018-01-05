package project.controller;

import project.model.Company;
import project.model.User;
import project.model.UserRepository;

public class RegisterUserController {

	UserRepository userRegistry;

	/**
	 * This constructor creates a user Registration controller. *
	 * 
	 */
	public RegisterUserController() {

		this.userRegistry = Company.getTheInstance().getUsersRepository();

	}

	/**
	 * After creating the Controller, this method is called to both create and add
	 * the User
	 * 
	 * @param User
	 *            name
	 * @param User
	 *            email
	 * @param User
	 *            idNumber
	 * @param User
	 *            function
	 * @param User
	 *            phone
	 * @param User
	 *            password
	 * @param User
	 *            Address street
	 * @param User
	 *            Address zipCode
	 * @param User
	 *            Address city
	 * @param User
	 *            Address district
	 * @param User
	 *            Address country
	 * 
	 * @return the User if added successfully, null if the email already exists or
	 *         is invalid
	 */
	public void addNewUser(String name, String email, String idNumber, String function, String phone, String password,
			String street, String zipCode, String city, String district, String country) {

		User newUser = userRegistry.createUser(name, email, idNumber, function, phone, street, zipCode, city, district,
				country);
		newUser.setPassword(password);
		userRegistry.addUserToUserRepository(newUser);

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
