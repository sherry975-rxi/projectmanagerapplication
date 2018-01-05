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
		this.username = Company.getTheInstance().getUsersRepository().getUserByEmail(email); //TODO está a passar nos testes e não devia - confirmar antes se user existe ou não
		this.password = password;
		boolean loginSuccess = false;
		if (username.checkLogin(password) == true) {
			loginSuccess = true;
		}

		return loginSuccess;
	}

	/**
	 * this method does logOut of user. Sets the private variables of
	 * DoLoginController to null
	 */
	public void doLogOut() {

		this.username = null;
		this.password = null;

	}

}
