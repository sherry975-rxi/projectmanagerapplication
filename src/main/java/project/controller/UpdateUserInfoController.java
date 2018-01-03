package project.controller;

import project.model.Address;
import project.model.User;

public class UpdateUserInfoController {

	/**
	 * This controller update user info
	 * 
	 * respond to US 201/202
	 * 
	 * @param user
	 * @param project
	 * @param effort
	 */

	/**
	 * This method controls the update of user's name by calling the method setName
	 * that set this parameter of user's data.
	 * @param user
	 * @param name
	 */
	public void updateUserName(User user, String name) {

		user.setName(name);
	}
	
	/**
	 * This method controls the update of user's email by calling the method setEmail
	 * that set this parameter of user's data.
	 * @param user
	 * @param email
	 */
	public void updateUserEmail(User user, String email) {

		user.setEmail(email);
	}

	/**
	 * This method controls the update of user's phone by calling the method setPhone
	 * that set this parameter of user's data.
	 * @param user
	 * @param phone
	 */
	public void updateUserPhone(User user, String phone) {

		user.setPhone(phone);
	}

	/**
	 * This method controls the add of a new address to user's address list by calling the method addAddress.
	 * @param user
	 * @param address
	 */
	public void addNewAddress(User user, Address address) {
		user.addAddress(address);
	}

	/**
	 * This method controls the update of user's street by calling the method searchUserAddress 
	 * (that search the specific address and return it) and then uses the method setStreet to 
	 * change the parameter street of that specific address.
	 * @param user
	 * @param address
	 * @param street
	 */
	public void updateUserStreet(User user, Address address, String street) {
		user.searchUserAddress(address).setStreet(street);
	}
	
	/**
	 * This method controls the update of user's zipCode by calling the method searchUserAddress 
	 * (that search the specific address and return it) and then uses the method setZipCode to 
	 * change the parameter zipCode of that specific address.
	 * @param user
	 * @param address
	 * @param zipCode
	 */
	public void updateUserZipCode(User user, Address address, String zipCode) {
		user.searchUserAddress(address).setZipCode(zipCode);
	}
	
	/**
	 * This method controls the update of user's city by calling the method searchUserAddress 
	 * (that search the specific address and return it) and then uses the method setCity to 
	 * change the parameter city of that specific address.
	 * @param user
	 * @param address
	 * @param city
	 */
	public void updateUserCity(User user, Address address, String city) {
		user.searchUserAddress(address).setCity(city);
	}
	
	/**
	 * This method controls the update of user's district by calling the method searchUserAddress 
	 * (that search the specific address and return it) and then uses the method setDistrict to 
	 * change the parameter district of that specific address.
	 * @param user
	 * @param address
	 * @param district
	 */
	public void updateUserDistrict(User user, Address address, String district) {
		user.searchUserAddress(address).setDistrict(district);
	}

	/**
	 * This method controls the update of user's country by calling the method searchUserAddress 
	 * (that search the specific address and return it) and then uses the method setCountry to 
	 * change the parameter country of that specific address.
	 * @param user
	 * @param address
	 * @param country
	 */
	public void updateUserCountry(User user, Address address, String country) {
		user.searchUserAddress(address).setCountry(country);
	}
}
