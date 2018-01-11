package project.controller;

import project.model.Company;
import project.model.User;

public class DoLoginController {

	private User username;
	private String password;

	/**
	 * This method does the login of the user
	 * 
	 * @param username
	 *            Sets private variable username to inserted username by user
	 * @param password
	 *            Sets private variable password to inserted password by user
	 */
	public boolean doLogin(String email, String password) {
		this.username = Company.getTheInstance().getUsersRepository().getUserByEmail(email);
		this.password = password;
		boolean loginSuccess = false;
		if (Company.getTheInstance().getUsersRepository().isUserinUserRepository(username)) {
			if (username.checkLogin(password) == true) {
				loginSuccess = true;
			}
		}

		return loginSuccess;
	}

}
