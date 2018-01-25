package project.ui.console.uiDirector;

import java.util.List;
import java.util.Scanner;

import project.controller.US302ChangeProjectManagerController;
import project.model.Project;

public class US302ChangeProjectManagerUI {

	String command;
	List<String> possibleManagers;

	public void changeProjectManager(Project toChangeManager) {

		Scanner input = new Scanner(System.in);

		System.out.println(toChangeManager.getProjectManager().getName() + " currently manages Project "
				+ toChangeManager.getName());
		System.out.println("Would you like to assign a different manager?");
		System.out.println("(Press [Y] to change, anything else to exit)");
		System.out.println("");

		command = input.nextLine().toUpperCase();

		if ("Y".equals(command)) {
			US302ChangeProjectManagerController changeManagerController = new US302ChangeProjectManagerController(
					toChangeManager);
			System.out.println("Select a collaborator from the list:");
			System.out.println("");
			possibleManagers = changeManagerController.listPossibleManagers();

			for (String other : possibleManagers) {
				System.out.println(other);
				System.out.println("");
			}
			System.out.println(
					"(Select one of the numbers displayed above. Invalid input will select the existing manager)");

			if (input.hasNextInt()) {
				int selection = input.nextInt();
				if (changeManagerController.isNewManagerDifferentFromFirst(selection)) {
					System.out.println("Manager changed successfully!");
					System.out.println("");
				} else {
					System.out.println("That collaborator is already the manager!");
					System.out.println("");
				}

			} else {
				System.out.println("Not a number!");
				System.out.println("");
			}
		}

		System.out.println("Returning to director menu...");
		System.out.println("");

	}

}
