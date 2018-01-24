package project.ui.uiAdministrator;

import java.util.Scanner;

import project.model.User;

public class AdminMenuUI {

	User adminLoggedIn;
	User selectedUser;

	String options = "[1] - view all users \n" + "[2] - search users by profile or email \n"
			+ "[3] - manage selected user profile \n" + "[4] - manage selected user state\n"
			+ "[E] - exit to main menu";

	String command;

	public AdminMenuUI(User admin) {
		this.adminLoggedIn = admin;
	}

	public void adminMenu() {
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

			System.out.println("Welcome to admin menu, " + adminLoggedIn.getName() + ". Please choose a command:");
			System.out.println(options);
			System.out.println("");

			command = input.nextLine().toLowerCase();

			switch (command) {
			case "1":
				US130ListUsersUI listUsersUI = new US130ListUsersUI();
				selectedUser = listUsersUI.displayUsersList(selectedUser);
				break;
			case "2":
				US135andUS136ListUsersUI searchUsersUI = new US135andUS136ListUsersUI();
				selectedUser = searchUsersUI.displayUsersList(selectedUser);
				break;

			case "3":
				if (selectedUser == null)
					System.out.println("No user selected!");
				else {
					US110andUS112SetUserProfileUI changeUserProfileUI = new US110andUS112SetUserProfileUI();
					changeUserProfileUI.changeUserProfile(selectedUser);
				}
				break;

			case "4":
				if (selectedUser == null)
					System.out.println("No user selected!");
				else {
					US115andUS116SetUserStateUI changeUserStateUI = new US115andUS116SetUserStateUI();
					changeUserStateUI.changeUserState(selectedUser);
				}
				break;

			case "e":
				System.out.println("Returning to main menu...");
				System.out.println("");
				cycle = false;
				break;

			default:
				System.out.println("Invalid input!");
				System.out.println("");
				break;

			}

		}

	}

}
