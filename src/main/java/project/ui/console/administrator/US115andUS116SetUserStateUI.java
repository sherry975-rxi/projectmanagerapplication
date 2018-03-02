package project.ui.console.administrator;

import project.controller.US110andUS112SetUserProfileController;
import project.controller.US115andUS116SetUserStateController;
import project.model.User;

import java.util.Scanner;

public class US115andUS116SetUserStateUI {

	public void changeUserState(User user) {

		US115andUS116SetUserStateController controllerA = new US115andUS116SetUserStateController(user);
		US110andUS112SetUserProfileController controllerB = new US110andUS112SetUserProfileController();

		System.out.println(user.getIdNumber() + " - " + controllerA.userStateAsString() + ": " + user.getName() + " - "
				+ controllerB.userProfileAsString(user));

		Scanner input = new Scanner(System.in);
		System.out.println("Proceeding will change this user's state. Are you sure you want to continue?");
		System.out.println("Press [Y] to confirm.");
		String choice = input.nextLine();

		if ("Y".equalsIgnoreCase(choice)) {
			controllerA.changeUserState();
			System.out.println("User state was changed successfully.");
		} else {
			System.out.println("User state was not changed.");
		}
		System.out.println("Returning to admin menu...");
		System.out.println("");
	}

}
