package project.ui.console.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US135andUS136SearchUsersController;
import project.model.Profile;
import project.model.User;

import java.util.List;
import java.util.Scanner;

@Component
public class US135andUS136ListUsersUI {
	@Autowired
	private US135andUS136SearchUsersController controller;

	String options = "[0] - search Unassigned Users \n" + "[1] - search Directors \n" + "[2] - search Collaborators \n"
			+ "Else, find emails containing your input\n";

	public User displayUsersList(User user) {


		Scanner input = new Scanner(System.in);

		List<String> searchList;
		String command;

		User selected = user;

		System.out.println("To find Users, press...");
		System.out.println(options);

		command = input.nextLine();

		switch (command) {
		case "0":
			searchList = controller.searchUsersByProfileController(Profile.VISITANT);
			break;
		case "1":
			searchList = controller.searchUsersByProfileController(Profile.DIRECTOR);
			break;
		case "2":
			searchList = controller.searchUsersByProfileController(Profile.COLLABORATOR);
			break;
		default:
			searchList = controller.searchUsersByEmailController(command);
			break;
		}

		if (searchList.isEmpty()) {
			System.out.println("No users found!");
			System.out.println("");
		} else {
			for (String other : searchList) {
				System.out.println(other);
				System.out.println("");
			}

			System.out.println(
					"Please choose a user's number (If a valid number isn't provided, no user will be selected.):");
			if (input.hasNextInt()) {
				int index = Integer.parseInt(input.nextLine());
				selected = controller.selectUser(index);// check later
			} else {
				System.out.println("Not a number!");
				System.out.println("");
			}
		}

		System.out.println("Returning to admin menu...");
		System.out.println("");
		return selected;
	}

}
