package project.controller;

public class DoLoginController {

	private String username;
	private String password;

	/**
	 * This method does the login of the user
	 * 
	 * @param username
	 *            Sets private variable username to inserted username by user
	 * @param password
	 *            Sets private variable password to inserted password by user
	 */
	public void doLogin(String username, String password) {

		this.username = username;
		this.password = password;

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
