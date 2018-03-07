package project.controller;

import project.model.Company;
import project.model.User;

public class DoLoginController {

	/**
	 * This method does the login of the user
	 * 
	 * @param username
	 *            Sets private variable username to inserted username by user
	 * @param password
	 *            Sets private variable password to inserted password by user
	 */
	public boolean doLogin(String email, String password1) {
		String password;
		User username;
		username = Company.getTheInstance().getUsersContainer().getUserByEmail(email);
		password = password1;
		boolean loginSuccess = false;
		if (Company.getTheInstance().getUsersContainer().isUserinUserRepository(username)
				&& username.checkLogin(password)) {
			loginSuccess = true;
		}

		return loginSuccess;
	}

}
