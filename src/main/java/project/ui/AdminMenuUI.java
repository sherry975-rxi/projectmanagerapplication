package project.ui;

import java.util.Scanner;

import project.model.User;

public class AdminMenuUI {

	User adminLoggedIn;
	User selectedUser;

	String options = "[1] - view all users \n" + "[2] - search users by profile \n" + "[3] - search Users by Email \n"
			+ "[4] - manage selected user state\n" + "[5] - manage selected user profile\n" + "[H] - view commands \n"
			+ "[E] - exit to main menu";

	String command;

	public AdminMenuUI(User admin) {
		this.adminLoggedIn = admin;
	}

	// TODO implement missing options
	public void adminMenu() {
		System.out.println("Welcome to admin menu, " + adminLoggedIn.getName() + ". Please choose a command:");
		System.out.println(options);
		Scanner input = new Scanner(System.in);

		boolean cycle = true;
		while (cycle) {

			if (selectedUser != null) {
				System.out.println("User selected!");
				System.out.println(selectedUser.getIdNumber() + ": " + selectedUser.getName() + "("
						+ selectedUser.getEmail() + ")");
				System.out.println("(User management commands enabled!)");
				System.out.println("");
			}

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
				if (selectedUser == null)
					System.out.println("No user selected!");
				else {
					US115andUS116SetUserStateUI changeUserStateUI = new US115andUS116SetUserStateUI();
					changeUserStateUI.changeUserState(selectedUser);
				}
				break;

			case "5":
				if (selectedUser == null)
					System.out.println("No user selected!");
				else {
					US110andUS112SetUserProfileUI changeUserProfileUI = new US110andUS112SetUserProfileUI();
					changeUserProfileUI.changeUserProfile(selectedUser);
				}
				break;

			case "e":
				System.out.println("Returning to main menu...");
				cycle = false;
				break;

			case "h":
				System.out.println(options);
				break;

			default:
				System.out.println("Please choose a command:");
				break;

			}

		}

	}

}
