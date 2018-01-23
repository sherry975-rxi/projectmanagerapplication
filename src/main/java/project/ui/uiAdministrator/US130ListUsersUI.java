package project.ui.uiAdministrator;

import java.util.List;
import java.util.Scanner;

import project.controller.US130ListUsersController;
import project.model.User;

public class US130ListUsersUI {

	public User displayUsersList(User user) {

		Scanner input = new Scanner(System.in);
		US130ListUsersController controller = new US130ListUsersController();
		List<String> usersList = controller.listUsersController();
		for (int i = 0; i < usersList.size(); i++) {
			System.out.println(usersList.get(i));
		}

		System.out.println("Please choose a user (If a valid number isn't provided, no user will be selected.):");
		if (input.hasNextInt()) {
			int index = Integer.parseInt(input.nextLine());
			user = controller.selectUser(index);// check later
		} else {
			System.out.println("Not a number!");
		}
		System.out.println("Returning to admin menu...");
		System.out.println("");
		return user;
	}

}
