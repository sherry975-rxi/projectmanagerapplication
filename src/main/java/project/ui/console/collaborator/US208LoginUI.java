package project.ui.console.collaborator;

import project.controller.DoLoginController;

import java.util.Scanner;

public class US208LoginUI {

	public void doLogin() {
		Scanner input = new Scanner(System.in);
		DoLoginController login = new DoLoginController();
		System.out.println("Username: ");
		String user = input.next();
		System.out.println("Password: ");
		String pass = input.next();
		if (login.doLogin(user, pass)) {
			System.out.println(" Login Successful! ");
		} else {
			System.out.println(" Login Failed! ");
		}
	}

}
