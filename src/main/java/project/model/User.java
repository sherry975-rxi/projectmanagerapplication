package project.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class to build Users. From this class one can create a new User(object),
 * which is defined by its name, email, idNumber, function, address list, phone
 * number, and by its profile.
 *
 * @author Group 3
 */
@Entity
@Table(name = "User")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "User_ID")
	private int id;
	private String name;
	private String email;
	private String idNumber;
	private String function;
	static final long serialVersionUID = 44L;
	@Embedded
	private ArrayList<Address> addressList;
	private String phone;
	@Enumerated(EnumType.STRING)
	private Profile userProfile;
	private boolean systemUserStateActive;
	private String password;

	/**
	 * Empty Constructor for User
	 */
	public User() {
	}

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
	 * @return the address created.
	 */
	public Address createAddress(String street, String zipCode, String city, String district, String country) {
		return new Address(street, zipCode, city, district, country);
	}

	/**
	 * Returns the id of the user.
	 *
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Id to be set as the new id of the user
	 *
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
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
	 * Set new ID Number of User
	 *
	 * @param idNumber
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * Returns the user address list
	 *
	 * @return Returns the user address list.
	 */
	public ArrayList<Address> getAddressList() {
		return addressList;
	}

	/**
	 * Set new addressList to User
	 *
	 * @param addresses
	 */
	public void setAddressList(ArrayList<Address> addresses) {
		this.addressList = addresses;
	}

	/**
	 * Address to be added to the user address list.
	 *
	 * @param address
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
	 * Set the state of User
	 *
	 * @param systemUserStateActive
	 */
	public void setSystemUserStateActive(boolean systemUserStateActive) {
		this.systemUserStateActive = systemUserStateActive;
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
	 * Sets the user profile.
	 *
	 * @param userProfile
	 *            Profile that will be set as the user profile.
	 * @return Sets the user profile equal to the @param userProfile received.
	 */
	public void setUserProfile(Profile userProfile) {
		this.userProfile = userProfile;
	}

	/**
	 * Gets the user profile.
	 *
	 * @return the user profile.
	 */
	public Profile getUserProfile() {
		return this.userProfile;
	}

	/**
	 * Sets the state of the user state.
	 */
	public void changeUserState() {
		if (this.systemUserStateActive) {
			this.systemUserStateActive = false;
		} else {
			this.systemUserStateActive = true;
		}
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

	/**
	 * Set Password to user
	 *
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * this method verify if the given password match with User password
	 *
	 * @param password
	 * @return true or false if the password match
	 */
	public boolean checkLogin(String password) {
		boolean found;
		found = false;
		if (this.password.equals(password)) {
			found = true;
		}
		return found;
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
}