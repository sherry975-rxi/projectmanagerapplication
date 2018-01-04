package project.controller;

import project.model.User;

/**
 * This controller can provide informations about a specific user.
 * 
 * @author Group 3
 *
 */
public class GetUserInfoController {

	
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
	
}
