package main.java.project.model;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Class UserRepository that contains all lists and methods to build lists of users
 * 
 * @author Group3
 *
 */
public class UserRepository {

	private List<User> usersRepository;


	/**
	 * Constructor for UserRepository includes usersList creation 
	 */
	public UserRepository() {
		this.usersRepository = new ArrayList<User>();
	}

	/**
	 * Creates an instance of User
	 * 
	 * @param name
	 * @param email
	 * @param idNumber
	 * @param function
	 * @param phone
	 * @param street
	 * @param zipCode
	 * @param city
	 * @param district
	 * @param country
	 * 
	 * @return the User created and instantiated
	 */
	public User createUser(String name, String email, String idNumber, String function, String phone, String street,
			String zipCode, String city, String district, String country) {

		User newUser = new User(name, email, idNumber, function, phone);

		Address newAddress = newUser.createAddress(street, zipCode, city, district, country);

		newUser.addAddress(newAddress);

		return newUser;
	}

	/**
	 * This method adds users to the usersList if the user doesn’t exists
	 * 
	 * @param toAddUsers
	 *            User added to the List of Users
	 */
	public void addUserToUserRepository(User toAddUsers) {
		if (!this.usersRepository.contains(toAddUsers)) {
			this.usersRepository.add(toAddUsers);
		}
	}
	
	/**
	 * This method allows the administrator to see if a given user already exists in
	 * company
	 * 
	 * @param user
	 *            user
	 * @return boolean; TRUE if user exists in company FALSE if user doesn’t exist
	 *         in company
	 */
	public boolean isUserinUserRepository(User addedUser) {
		if (this.usersRepository.contains(addedUser)) {
			return true;
		}
		return false;
	}

	/**
	 * This method returns a copy of the list of all users (usersRepository)
	 * 
	 * @return allUsers This is the copy of the List of all Users in the repository
	 */
	public List<User> getAllUsersFromRepository() {

		List <User> allUsers = new ArrayList<>();
		allUsers.addAll(this.usersRepository);
		return allUsers;

	}


	/**
	 * This method allows the Administrator to access the user list and search users
	 * by parts of email. This is achieved by using the .contains() method.
	 *
	 * @param partOfEmail
	 *            		This is not the complete user email but a part of the email string
	 * 
	 * @return userListThatContainsPiecesOfEmailString 
	 * 					The list with users that have the query piece of email
	 */
	public List<User> searchUsersByEmail(String partOfEmail) {

		List<User> userListThatContainsPiecesOfEmailString = new ArrayList<User>();

		for (int i = 0; i < this.usersRepository.size(); i++) {
			if (usersRepository.get(i).getEmail().contains(partOfEmail)) {
				userListThatContainsPiecesOfEmailString.add(usersRepository.get(i));
			}

		}

		return userListThatContainsPiecesOfEmailString;

	}

	/**
	 * This method allows the Administrator to access the user list and search users
	 * by complete email. This is achieved by using the .contains() method.
	 *
	 * @param completeEmail
	 *            		This is not the complete user email but a part of the email string
	 * 
	 * @return the User that matches the searched e-mail address
	 * 				
	 */
	public User getUserByEmail(String completeEmail) {

		for (int i = 0; i < this.usersRepository.size(); i++) {
			if (usersRepository.get(i).getEmail().contains(completeEmail)) {	
				return usersRepository.get(i);
			}
		}
		return null;
	}

	/**
	 * This method allows the administrator to access the userRepository and search users
	 * by profile.
	 * 
	 * @param searchProfile
	 *            Profile of a user
	 * @return Repository of users with a certain profile
	 * 
	 */
	public List<User> searchUsersByProfile(Profile searchProfile) {

		List<User> usersByProfileList = new ArrayList<User>();

		for (int i = 0; i < this.usersRepository.size(); i++) {
			if(usersRepository.get(i).getUserProfile() == searchProfile) {
				usersByProfileList.add(this.usersRepository.get(i));
			}
		}
		return usersByProfileList;
	}

	
	/**
	 * This method checks if an e-mail inserted by the user is valid or not
	 * 
	 * @param String
	 *            email
	 * @return TRUE if email is valid FALSE if email is invalid
	 */
	public boolean isEmailAddressValid(String email) {
		boolean result = true;

		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} 
		catch (AddressException ex) {
			result = false;
		}
		return result;
	}

}	