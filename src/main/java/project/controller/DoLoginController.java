package project.controller;

public class DoLoginController {

	private String username;
	private String password;

	public void doLogin(String username, String password) {

		this.username = username;
		this.password = password;

	}

	public void doLogOut() {

		this.username = null;
		this.password = null;

	}

}
