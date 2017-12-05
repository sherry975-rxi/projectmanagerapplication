package main.java.project.model;

/**
 * Class to builds an Address.
 * 
 * From this class one can create a new Address(object), which is defined by its
 * street, zipCode, city, district, country.
 * 
 * @author Group 3
 *
 */

public class Address {

	private String street;
	private String zipCode;
	private String city;
	private String district;
	private String country;

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
}
