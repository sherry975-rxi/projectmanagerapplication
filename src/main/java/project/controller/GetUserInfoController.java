package project.controller;

import java.util.List;

import project.model.Address;
import project.model.Company;
import project.model.User;

/**
 * This controller can provide informations about a specific user.
 * 
 * @author Group 3
 *
 */
public class GetUserInfoController {

	/**
	 * This controller returns a list of all users in the User Repository
	 * 
	 * @return List<User> a copy of the User database
	 */
	public List<User> getUsersList() {

		return Company.getTheInstance().getUsersRepository().getAllUsersFromRepository();

	}

	/**
	 * Returns the name of the user.
	 * 
	 * @return the name of the user
	 */
	public String getName(User user) {
		return user.getName();
	}

	/**
	 * 
	 * Returns the user Email
	 * 
	 * @return the user Email.
	 */
	public String getEmail(User user) {
		return user.getEmail();
	}

	/**
	 * Returns the ID number of the user
	 * 
	 * @return the ID number of the user.
	 */
	public String getIdNumber(User user) {
		return user.getIdNumber();
	}

	/**
	 * Returns the function of the user
	 * 
	 * @return Returns the user function.
	 */
	public String getFunction(User user) {
		return user.getFunction();
	}

	/**
	 * Returns the phone number of a certain user
	 * 
	 * @return the phone number of a certain user
	 */
	public String getPhone(User user) {
		return user.getPhone();
	}

	/**
	 * @param emailToSearch
	 *            The string of email to search for
	 * @return This method returns a list of users with an email that contain the
	 *         same text that the user typed
	 */
	public User getUserByEmailController(String emailToSearch) {

		return Company.getTheInstance().getUsersRepository().getUserByEmail(emailToSearch);

	}

	/**
	 * this method search one specific address in user address list, if find one
	 * match, the method return this specific address object.
	 * 
	 * @param findAddress
	 * @return address
	 */
	public Address searchUserAddress(User user, String street) {
		for (Address other : this.getAllAddresses(user)) {
			if (other.getStreet().equals(street))
				return other;
		}
		return null;
	}

	/**
	 * 
	 * Gets the street
	 * 
	 * @return Street
	 */
	public String getStreet(Address address) {
		return address.getStreet();
	}

	/**
	 * Gets the City
	 * 
	 * @return city City
	 */
	public String getCity(Address address) {
		return address.getCity();
	}

	/**
	 * Gets the Country
	 * 
	 * @return country Country
	 */
	public String getCountry(Address address) {
		return address.getCountry();
	}

	/**
	 * Gets the District
	 * 
	 * @return district District
	 */
	public String getDistrict(Address address) {
		return address.getDistrict();
	}

	/**
	 * Gets the zipCode
	 * 
	 * @return zipCode zipCode
	 */
	public String getZipCode(Address address) {
		return address.getZipCode();
	}

	/**
	 * This method returns a list of all the addresses from a user
	 * 
	 * @param user
	 */
	public List<Address> getAllAddresses(User user) {
		return user.getAddressList();
	}
}
