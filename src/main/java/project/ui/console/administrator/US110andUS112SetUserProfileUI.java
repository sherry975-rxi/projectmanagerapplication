package project.ui.console.administrator;

import java.util.Scanner;

import project.controller.US110andUS112SetUserProfileController;
import project.controller.US115andUS116SetUserStateController;
import project.model.User;

public class US110andUS112SetUserProfileUI {

	public void changeUserProfile(User user) {
		Scanner input = new Scanner(System.in);
		String option;
		US110andUS112SetUserProfileController controllerA = new US110andUS112SetUserProfileController();
		US115andUS116SetUserStateController controllerB = new US115andUS116SetUserStateController(user);
		System.out.println(user.getIdNumber() + " - " + controllerB.userStateAsString() + ": " + user.getName() + " - "
				+ controllerA.userProfileAsString(user));
		switch (user.getUserProfile()) {
		case COLLABORATOR:
			System.out.println("Proceeding will set this user profile as Director. Continue?");
			System.out.println("Press [Y] to confirm.");
			option = input.nextLine();
			if ("Y".equalsIgnoreCase(option)) {
				controllerA.setUserAsDirector(user);
				System.out.println("User profile successfully set as Director.");
			} else {
				System.out.println("User profile wasn't changed.");
			}
			break;
		case DIRECTOR:
			System.out.println("Proceeding will set this user profile as Collaborator. Continue?");
			System.out.println("Press [Y] to confirm.");
			option = input.nextLine();
			if ("Y".equalsIgnoreCase(option)) {
				controllerA.setUserAsCollaborator(user);
				System.out.println("User profile successfully set as Collaborator.");
			} else {
				System.out.println("User profile wasn't changed.");
			}
			break;
		default:
			System.out.println("Please select which profile to give to the user:");
			System.out.println("[D] - Director");
			System.out.println("[C] - Collaborator");
			option = input.nextLine();
			if ("D".equalsIgnoreCase(option)) {
				controllerA.setUserAsDirector(user);
				System.out.println("User profile successfully set as Director.");
			} else if ("C".equalsIgnoreCase(option)) {
				controllerA.setUserAsCollaborator(user);
				System.out.println("User profile successfully set as Collaborator.");
			} else {
				System.out.println("Invalid input.");
			}
			break;
		}
	}

}
