package project.model;

import project.dto.userDTO;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Class UserRepository that contains all lists and methods to build lists of
 * users
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
		this.usersRepository = new ArrayList<>();
	}

	/**
	 * Creates an instance of User
	 * 
	 * @param name
	 *            Name of the user
	 * @param email
	 *            Email of the user
	 * @param idNumber
	 *            idNumber of the user
	 * @param function
	 *            Function of the user
	 * @param phone
	 *            Phone of the user
	 * @param street
	 *            Street of the user
	 * @param zipCode
	 *            ZipCode of the user
	 * @param city
	 *            City of the user
	 * @param district
	 *            District of the user
	 * @param country
	 *            Country of the user
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
	 * Creates a user from a userDTO
	 * 
	 * @param userDTO
	 *            UserDTO to create a user
	 */
	public void createUserWithDTO(userDTO userDTO) {

		// Instantias the user
		User newUser = new User(userDTO.getName(), userDTO.getEmail(), userDTO.getIdNumber(), userDTO.getFunction(),
				userDTO.getPhone());

		// Creates a new address
		Address newAddress = newUser.createAddress(userDTO.getStreet(), userDTO.getZipCode(), userDTO.getCity(),
				userDTO.getDistrict(), userDTO.getCountry());

		// Adds the address to user list
		newUser.addAddress(newAddress);

		// Sets the user password
		newUser.setPassword(userDTO.getPassword());

		// Adds the user to User repository
		addUserToUserRepository(newUser);
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
	 * @param addedUser
	 * @return boolean; TRUE if user exists in company FALSE if user doesnt exist
	 *         in company
	 */
	public boolean isUserinUserRepository(User addedUser) {

		return this.usersRepository.contains(addedUser);
	}

	/**
	 * This method returns a copy of the list of all users (usersRepository)
	 * 
	 * @return allUsers This is the copy of the List of all Users in the repository
	 */
	public List<User> getAllUsersFromRepository() {

		List<User> allUsers = new ArrayList<>(this.usersRepository);
		return allUsers;

	}

	/**
	 * This method returns a list of all active collaborators (usersRepository)
	 * 
	 * @return allCollaborators This is the copy of the List of all Users in the
	 *         repository
	 */
	public List<User> getAllActiveCollaboratorsFromRepository() {

		List<User> allCollaborators = new ArrayList<>();
		for (User other : this.usersRepository) {
			if (other.isUserActive() && other.getUserProfile().equals(Profile.COLLABORATOR)) {
				allCollaborators.add(other);
			}
		}

		return allCollaborators;

	}

	/**
	 * This method allows the Administrator to access the user list and search users
	 * by parts of email. This is achieved by using the .contains() method.
	 *
	 * @param partOfEmail
	 *            This is not the complete user email but a part of the email string
	 * 
	 * @return userListThatContainsPiecesOfEmailString The list with users that have
	 *         the query piece of email
	 */
	public List<User> searchUsersByEmail(String partOfEmail) {

		List<User> userListThatContainsPiecesOfEmailString = new ArrayList<>();

		for (User anUsersRepository : this.usersRepository) {
			if (anUsersRepository.getEmail().contains(partOfEmail)) {
				userListThatContainsPiecesOfEmailString.add(anUsersRepository);
			}

		}

		return userListThatContainsPiecesOfEmailString;

	}

	/**
	 * This method allows the Administrator to access the user list and search users
	 * by complete email. This is achieved by using the .contains() method.
	 *
	 * @param completeEmail
	 *            This is not the complete user email but a part of the email string
	 * 
	 * @return the User that matches the searched e-mail address
	 * 
	 */
	public User getUserByEmail(String completeEmail) {

		for (User anUsersRepository : this.usersRepository) {
			if (anUsersRepository.getEmail().contains(completeEmail)) {
				return anUsersRepository;
			}
		}
		return null;
	}

	/**
	 * This method allows the administrator to access the userRepository and search
	 * users by profile.
	 * 
	 * @param searchProfile
	 *            Profile of a user
	 * @return Repository of users with a certain profile
	 * 
	 */
	public List<User> searchUsersByProfile(Profile searchProfile) {

		List<User> usersByProfileList = new ArrayList<>();

        for (User anUsersRepository : this.usersRepository) {
            if (anUsersRepository.getUserProfile() == searchProfile) {
                usersByProfileList.add(anUsersRepository);
            }
        }
		return usersByProfileList;
	}

	/**
	 * This method checks if an e-mail inserted by the user is valid or not
	 * 
	 * @param email
	 *
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