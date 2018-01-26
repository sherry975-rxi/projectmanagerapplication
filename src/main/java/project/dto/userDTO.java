package project.dto;

import java.util.List;

import project.model.Address;
import project.model.Profile;
import project.model.User;

public class userDTO {

	public String name;
	public String email;
	public String idNumber;
	public String function;
	public List<Address> addressList;
	public String phone;
	public Profile userProfile;
	public String password;
	public String street;
	public String zipCode;
	public String city;
	public String district;
	public String country;

	public userDTO(User user) {
		this.name = user.getName();
		this.email = user.getEmail();
		this.idNumber = user.getIdNumber();
		this.function = user.getFunction();
		this.addressList.addAll(user.getAddressList());
		this.userProfile = user.getUserProfile();
	}

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

}
