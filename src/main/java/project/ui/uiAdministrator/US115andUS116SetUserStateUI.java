package project.ui.uiAdministrator;

import java.util.Scanner;

import project.controller.US115andUS116SetUserStateController;
import project.model.User;

public class US115andUS116SetUserStateUI {

	public void changeUserState(User user) {

		US115andUS116SetUserStateController controller = new US115andUS116SetUserStateController(user);
		System.out.println(controller.userDataAsString());

		Scanner input = new Scanner(System.in);
		System.out.println("Proceeding will change this user's state. Are you sure you want to continue?");
		System.out.println("Press [Y] to confirm.");
		String choice = input.nextLine();

		if ("Y".equalsIgnoreCase(choice)) {
			controller.changeUserState();
			System.out.println("User state was changed successfully.");
		} else {
			System.out.println("User state was not changed.");
		}
		System.out.println("Returning to admin menu...");
		System.out.println("");
	}

}
