package project.ui.console.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US130ListUsersController;
import project.model.User;

import java.util.List;
import java.util.Scanner;

@Component
public class US130ListUsersUI {
	@Autowired
	private US130ListUsersController controller;

	public User displayUsersList() {
		Scanner input = new Scanner(System.in);
		User selectedUser = null;
		List<String> usersList = controller.listUsersController();
		
		for (int i = 0; i < usersList.size(); i++) {
			System.out.println(usersList.get(i));
			System.out.println("");
		}

		System.out
				.println("Please select a user's number (If a valid number isn't provided, no user will be selected):");
		if (input.hasNextInt()) {
			int userNumber = Integer.parseInt(input.nextLine());
			selectedUser = controller.selectUser(userNumber);// check later

		} else {

			System.out.println("Not a number!\n");
		}
		System.out.println("Returning to admin menu...\n");
		return selectedUser;
	}

}
