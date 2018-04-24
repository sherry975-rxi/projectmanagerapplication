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
		User userReturned = null;
		List<String> usersList = controller.listUsersController();
		
		for (int i = 0; i < usersList.size(); i++) {
			System.out.println(usersList.get(i));
			System.out.println("");
		}

		System.out
				.println("Please choose user's number (If a valid number isn't provided, no user will be selected.):");
		if (input.hasNextInt()) {
			int index = Integer.parseInt(input.nextLine());
			userReturned = controller.selectUser(index);// check later
		} else {
			System.out.println("Not a number!");
			System.out.println("");
		}
		System.out.println("Returning to admin menu...");
		System.out.println("");
		return userReturned;
	}

}
