package project.controller;

import project.Services.UserService;
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
	    UserService userContainer;
	    userContainer = new UserService();
		String password;
		User username;
		username = userContainer.getUserByEmail(email);
		password = password1;
		boolean loginSuccess = false;
		if (userContainer.isUserinUserContainer(username)
				&& username.checkLogin(password)) {
			loginSuccess = true;
		}

		return loginSuccess;
	}

}
