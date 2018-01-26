package project.dto;

import java.util.List;

import project.model.Address;
import project.model.Profile;
import project.model.User;

public class userDTO {

	private String name;
	private String email;
	private String idNumber;
	private String function;
	private List<Address> addressList;
	private String phone;
	private Profile userProfile;
	private String password;
	private String street;
	private String zipCode;
	private String city;
	private String district;
	private String country;

	/**
	 * Instantiates a userDTO from another user
	 * 
	 * @param user
	 *            User
	 */
	public userDTO(User user) {
		this.name = user.getName();
		this.email = user.getEmail();
		this.idNumber = user.getIdNumber();
		this.function = user.getFunction();
		this.addressList.addAll(user.getAddressList());
		this.userProfile = user.getUserProfile();
	}

	/**
	 * Creates a userDTO with the same parameters that a User is created
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
	 * @param password
	 *            Password of the user
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
	 */
	public userDTO(String name, String email, String idNumber, String function, String phone, String password,
			String street, String zipCode, String city, String district, String country) {
		this.name = name;
		this.email = email;
		this.idNumber = idNumber;
		this.function = function;
		this.phone = phone;
		this.password = password;
		this.street = street;
		this.zipCode = zipCode;
		this.city = city;
		this.district = district;
		this.country = country;
	}

	/**
	 * Gets the ZipCode of the user
	 * 
	 * @return Returns the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Gets the Name of the user
	 * 
	 * @return Returns the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the Email of the user
	 * 
	 * @return Returns the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Gets the User id Number
	 * 
	 * @return Returns the idNumber
	 */
	public String getIdNumber() {
		return idNumber;
	}

	/**
	 * Gets the user Function
	 * 
	 * @return Returns the function
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * Gets the phone
	 * 
	 * @return Returns the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Gets the user profile
	 * 
	 * @return Returns the profile
	 */
	public Profile getUserProfile() {
		return userProfile;
	}

	/**
	 * Gets the Password of the user
	 * 
	 * @return Returns the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the user street
	 * 
	 * @return Returns the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Gets the user city
	 * 
	 * @return Returns the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Gets the user district
	 * 
	 * @return Returns the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * Gets the user country
	 * 
	 * @return Returns the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Gets the user address list
	 * 
	 * @return Returns the address list
	 */
	public List<Address> getAddressList() {
		return addressList;
	}

}
