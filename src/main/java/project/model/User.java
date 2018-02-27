package project.model;

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

	private int id;
	private String name;
	private String email;
	private String idNumber;
	private String function;
	private List<Address> addressList;
	private String phone;
	private Profile userProfile;
	private boolean systemUserStateActive;
	private String password;

	/**
	 * Empty Constructor for User
	 */
	public User () {}

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
	 * @param phone
	 *            Phone number of the new user.systemUserStateActive
	 */
	public User(String name, String email, String idNumber, String function, String phone) {

		this.name = name;
		this.email = email;
		this.idNumber = idNumber;
		this.function = function;
		this.addressList = new ArrayList<>();
		this.phone = phone;
		this.userProfile = Profile.UNASSIGNED;
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

		return new Address(street, zipCode, city, district, country);

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
	 * Returns the SystemUserState boolean that returns true if the user is active
	 * and false if it is not active
	 * 
	 * @return the SystemUserState. True if is active, false if not.
	 */
	public boolean isUserActive() {
		return this.systemUserStateActive;
	}

	/**
	 * Sets the state of the user state.
	 *
	 *            State of the user to be set
	 * 
	 */
	public void changeUserState() {

		if (this.systemUserStateActive) {
			this.systemUserStateActive = false;
		} else {
			this.systemUserStateActive = true;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 3;
		result = prime * result + email.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return email.equals(other.email);
	}

	/**
	 * Sets the user profile.
	 * 
	 * @param userProfile
	 *            Profile that will be set as the user profile.
	 * 
	 * @return Sets the user profile equal to the @param userProfile received.
	 */
	public void setUserProfile(Profile userProfile) {

		this.userProfile = userProfile;

	}

	/**
	 * Gets the user profile.
	 * 
	 * 
	 * @return the user profile.
	 */
	public Profile getUserProfile() {

		return this.userProfile;

	}

	/**
	 * this method search one specific address in user address list, if find one
	 * match, the method return this specific address object.
	 * 
	 * @param street
	 * @return address
	 */
	public Address searchUserAddress(String street) {
		for (Address other : this.addressList) {
			if (other.getStreet().equals(street))
				return other;
		}
		return null;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean checkLogin(String password) {
		boolean found;
		found = false;
		if (this.password.equals(password)) {
			found = true;
		}
		return found;
	}
}
