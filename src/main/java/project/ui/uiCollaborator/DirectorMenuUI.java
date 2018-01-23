package project.ui.uiCollaborator;

import java.util.Scanner;

import project.model.User;

public class DirectorMenuUI {

	User directorLoggedIn;

	String options = "[1] - view all projects \n" + "[2] - view active projects \n"
			+ "[3] - view all active collaborators \n" + "[4] - create a project \n"
			+ "[5] - manage selected project \n" + "[E] - exit to main menu";

	String command;

	public DirectorMenuUI(User admin) {
		this.directorLoggedIn = admin;
	}

	// TODO implement missing options!
	public void directorMenu() {
		Scanner input = new Scanner(System.in);

		boolean cycle = true;
		while (cycle) {

			System.out.println("");
			System.out
					.println("Welcome to director menu, " + directorLoggedIn.getName() + ". Please choose a command:");
			System.out.println(options);

			command = input.nextLine().toLowerCase();

			switch (command) {
			case "1":
				System.out.println("Not yet implemented!");
				break;

			case "2":
				System.out.println("Not yet implemented!");
				break;

			case "3":
				System.out.println("Not yet implemented!");
				break;

			case "4":
				System.out.println("Not yet implemented!");
				break;

			case "5":
				System.out.println("Not yet implemented!");
				break;

			case "e":
				System.out.println("Returning to main menu...");
				System.out.println("");
				cycle = false;
				break;

			default:
				System.out.println("Invalid input!");
				break;
			}

		}

	}

}
