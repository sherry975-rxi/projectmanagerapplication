package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.Services.UserService;
import project.model.User;

@Controller
public class DoLoginController {

	@Autowired
	public UserService userService;

	/**
	 * This method does the login of the user
	 * 
	 * @param email
	 *            Sets private variable username to inserted username by user
	 * @param password1
	 *            Sets private variable password to inserted password by user
	 */
	public boolean doLogin(String email, String password1) {

		String password;
		User username;
		username = userService.getUserByEmail(email);
		password = password1;
		boolean loginSuccess = false;
		if (username== null) {
			return loginSuccess;
		} else if (userService.isUserinUserContainer(username)
				&& username.checkLogin(password)) {
			loginSuccess = true;
		}

		return loginSuccess;
	}

}
