package project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Class to builds an Address.
 * 
 * From this class one can create a new Address(object), which is defined by its
 * street, zipCode, city, district, country.
 * 
 * @author Group 3
 *
 */
@Entity
public class Address implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String street;
	private String zipCode;
	private String city;
	private String district;
	private String country;
	static final long serialVersionUID = 42L;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "User_id")
	private User user;

	public Address(){}

	/**
	 * This method defines the Constructor Address which will have the following
	 * fields as Strings:
	 * 
	 * @param street
	 * @param zipCode
	 * @param city
	 * @param district
	 * @param country
	 */
	public Address(String street, String zipCode, String city, String district, String country) {
		this.street = street;
		this.zipCode = zipCode;
		this.city = city;
		this.district = district;
		this.country = country;

	}



	/**
	 * Sets the street
	 * 
	 * @param street
	 *            Street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * 
	 * Gets the street
	 * 
	 * @return Street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Sets the zipCode
	 * 
	 * @param zipCode
	 *            zipCode
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Gets the zipCode
	 * 
	 * @return zipCode zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Sets the City
	 * 
	 * @param city
	 *            City
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the City
	 * 
	 * @return city City
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the District
	 * 
	 * @param district
	 *            District
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * Gets the District
	 * 
	 * @return district District
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * Sets the Country
	 * 
	 * @param country
	 *            Country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the Country
	 * 
	 * @return country Country
	 */
	public String getCountry() {
		return country;
	}

	public void setUserInAddress(User userInAddress){
		this.user = userInAddress;
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
		result = prime * result + ((street == null) ? 0 : street.hashCode());
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
		Address other = (Address) obj;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		return true;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	}



