package code;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to build Users. From this class one can create a new User(object),
 * which is defined by its name, email, idNumber, function, address list, phone
 * number, and by its profile.
 * 
 * @author Group 3
 *
 */

public class User {

	private String name;
	private String email;
	private String idNumber;
	private String function;
	private List<Address> addressList;
	private String phone;
	private Profile userProfile;
	private boolean systemUserStateActive;

	/**
	 * Constructor of the class User. Every user has list of Addresses.
	 * 
	 * @param name
	 *            Name of the new user.
	 * @param email
	 *            Email of the new user.
	 * @param idNumber
	 *            ID number of the new user.
	 * @param function
	 *            Function of the new user in the company.
	 * @param addressList
	 *            List of addresses of the new user.
	 * @param phone
	 *            Phone number of the new user.systemUserStateActive
	 */
	public User(String name, String email, String idNumber, String function, String phone) {

		this.name = name;
		this.email = email;
		this.idNumber = idNumber;
		this.function = function;
		this.addressList = new ArrayList<Address>();
		this.phone = phone;
		this.userProfile = new Profile();
		this.systemUserStateActive = true;
	}

	/**
	 * Creates an instance of Address.
	 * 
	 * @param street
	 * @param zipCode
	 * @param city
	 * @param district
	 * @param country
	 * 
	 * @return the address created.
	 */
	public Address createAddress(String street, String zipCode, String city, String district, String country) {

		Address newAddress = new Address(street, zipCode, city, district, country);

		return newAddress;

	}

	/**
	 * Returns the name of the user.
	 * 
	 * @return the name of the user
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Name to be set as the new name of the user
	 * 
	 * @param name
	 *            Name to be set as the new name of the user.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * Returns the user Email
	 * 
	 * @return the user Email.
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Email to be set as the new email address of the user
	 * 
	 * @param email
	 * 
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the ID number of the user
	 * 
	 * @return the ID number of the user.
	 */
	public String getIdNumber() {
		return this.idNumber;
	}

	/**
	 * Returns the user address list
	 * 
	 * @return Returns the user address list.
	 */
	public List<Address> getAddressList() {
		return addressList;
	}

	/**
	 * Address to be added to the user address list.
	 * 
	 * @param address
	 * 
	 */
	public void addAddress(Address address) {
		this.addressList.add(address);
	}

	/**
	 * Returns the phone number of a certain user
	 * 
	 * @return the phone number of a certain user
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * This method sets the phone number attribute in User
	 * 
	 * @param phone
	 *            Phone number that will be set as the new phone number of the user.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Returns the function of the user
	 * 
	 * @return Returns the user function.
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * This method allows the function of a user to be changed.
	 * 
	 * @param function
	 *            Function of the user in the company.
	 */
	public void setFunction(String function) {
		this.function = function;
	}

	/**
	 * Returns the user profile
	 * 
	 * @return the user profile.
	 */
	public Profile getProfile() {
		return this.userProfile;
	}

	/**
	 * Returns the SystemUserState boolean that returns true if the user is active
	 * and false if it is not active
	 * 
	 * @return the SystemUserState. True if is active, false if not.
	 */
	public boolean isSystemUserStateActive() {
		return this.systemUserStateActive;
	}

	/**
	 * Sets the state of the user state.
	 * 
	 * @param state
	 *            State of the user to be set
	 * 
	 */
	public void setSystemUserState(Boolean state) {

		this.systemUserStateActive = state;

	}

	/*
	 * Checks if the two Users are equal, by comparing their Emails.
	 * 
	 * @return TRUE if the two User Objects are equal. FALSE if two User Objects are
	 * not equal.
	 */
	@Override
	public boolean equals(Object toCompare) {
		boolean result = false;
		if (toCompare instanceof User) {
			User user1 = (User) toCompare;
			if (this.email == user1.email) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * Sets the user profile.
	 * 
	 * @param userProfile
	 *            Profile that will be set as the user profile.
	 * 
	 * @return TRUE if the user profile was successfully changed, FALSE if it
	 *         failed.
	 */
	public boolean setUserProfile(int userProfile) {

		if (userProfile == Profile.DIRECTOR) {

			this.userProfile.setDirector();
			return true;
		}

		if (userProfile == Profile.COLLABORATOR) {

			this.userProfile.setCollaborator();
			return true;
		}

		if (userProfile == Profile.VISITOR) {
			this.userProfile.setVisitor();
			return true;
		}

		return false;
	}

}
