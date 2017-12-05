package main.java.project.model;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

/**
 * Class UserRepository that contains all lists and methods to build lists of users
 * 
 * @author Group3
 *
 */
public class UserRepository {

		private List<User> usersList;
		

		/**
		 * Constructor for UserRepository includes usersList creation 
		 */

		public UserRepository() {
			this.usersList = new ArrayList<User>();
		}

//		/**
//		 * Creates an instance of User
//		 * 
//		 * @param name
//		 * @param email
//		 * @param idNumber
//		 * @param function
//		 * @param phone
//		 * 
//		 * @return the project created
//		 */
//		public User createUser(String name, String email, String idNumber, String function, String phone, String street,
//				String zipCode, String city, String district, String country) {
//			User newUser = new User(name, email, idNumber, function, phone);
//			Address newAddress = newUser.createAddress(street, zipCode, city, district, country);
//			newUser.addAddress(newAddress);
//
//			return newUser;
//
//		}

		/**
		 * This method adds users to the usersList if the user doesn't exists
		 * 
		 * @param toAddUsers
		 *            User added to the List of Users
		 */
		public boolean addUserToUserList(User toAddUsers) {
			if (!this.usersList.contains(toAddUsers)) {
				this.usersList.add(toAddUsers);
				return true;
			}
			return false;
		}

		/**
		 * This method returns the list of users (usersList)
		 * 
		 * @return usersList This is the List of all Users created
		 */
		public List<User> getUsersList() {
			return this.usersList;
		}

		/**
		 * This method allows the administrator to see if a given user already exists in
		 * company
		 * 
		 * @param user
		 *            user
		 * @return boolean; TRUE if user exists in company FALSE if user doesn't exist
		 *         in company
		 * 
		 */

		public boolean doesUserExist(User user) {
			if (this.getUsersList().contains(user)) {
				return true;
			} else {
				return false;
			}

		}

		/**
		 * This method allows the administrator to access the user list and search users
		 * by email. This is achieved by using the .contains() method.
		 */
		/**
		 * @param email
		 *            user email
		 * 
		 * @return list with users that have the required email
		 */
		public List<User> searchUsersByEmail(String email) {

			List<User> userListThatContainsEmailString = new ArrayList<User>();

			for (int i = 0; i < this.usersList.size(); i++) {
				if (usersList.get(i).getEmail().contains(email)) {
					userListThatContainsEmailString.add(usersList.get(i));
				}

			}

			return userListThatContainsEmailString;

		}

		/**
		 * This method allows the administrator to access the user list and search users
		 * by profile.
		 * 
		 * @param searchProfile
		 *            Profile of a user
		 * @return A list of users with a certain profile
		 * 
		 */
		public List<User> searchUsersByProfile(Profile searchProfile) {

			List<User> usersByProfileList = new ArrayList<User>();

			for (int i = 0; i < this.getUsersList().size(); i++) {
				if (searchProfile.getProfileInt() == this.getUsersList().get(i).getProfile().getProfileInt()) {
					usersByProfileList.add(this.getUsersList().get(i));

				}
			}

			return usersByProfileList;

		}


		/**
		 * This method checks if an email inserted by the user is valid or not
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
			} catch (AddressException ex) {
				result = false;
			}
			return result;
		}

	}	
}
