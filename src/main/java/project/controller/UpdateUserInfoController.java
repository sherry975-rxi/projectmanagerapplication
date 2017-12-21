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
	public UpdateUserInfoController() {

	}

	public void updateUserName(User user, String name) {

		user.setName(name);
	}

	public void updateUserEmail(User user, String email) {

		user.setEmail(email);
	}

	public void updateUserPhone(User user, String phone) {

		user.setPhone(phone);
	}
	
	public void addNewAddress(User user, Address address) {
		user.addAddress(address);
	}
	
	public void updateUserStreet(User user, Address address, String street) {

		}
		
	}

}
