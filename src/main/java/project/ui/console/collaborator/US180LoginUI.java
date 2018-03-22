package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controller.US180DoLoginController;

import java.util.Scanner;

@Component
public class US180LoginUI {

	@Autowired
	private US180DoLoginController login;

	public void doLogin() {

		Scanner input = new Scanner(System.in);
		System.out.println("Username: ");
		String user = input.nextLine();
		System.out.println("Password: ");
		String pass = input.nextLine();
		if (login.doLogin(user, pass)) {
			System.out.println(" Login Successful! ");
		} else {
			System.out.println(" Login Failed! ");
		}
	}

}
