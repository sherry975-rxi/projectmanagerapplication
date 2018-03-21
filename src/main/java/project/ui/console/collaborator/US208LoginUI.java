package project.ui.console.collaborator;

import project.controller.US180DoLoginController;
import project.controller.UpdateDbToContainersController;

import java.util.Scanner;

public class US208LoginUI {

	public void doLogin() {
		UpdateDbToContainersController infoUpdater = new UpdateDbToContainersController();
		infoUpdater.updateDBtoContainer();
		Scanner input = new Scanner(System.in);
		US180DoLoginController login = new US180DoLoginController();
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
