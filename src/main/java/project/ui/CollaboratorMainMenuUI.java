package project.ui;

import java.util.Scanner;

import project.model.User;

/**
 * @author group3
 *
 */

public class CollaboratorMainMenuUI {

	private User user;

	/**
	 * Creates the UI
	 * 
	 * @param user
	 */
	public CollaboratorMainMenuUI(User user) {
		this.user = user;

	}

	public void displayOptions() {
		Scanner scannerInput = new Scanner(System.in);

		String myname = user.getName();
		String function = user.getFunction().toUpperCase();

		System.out.println("\n" + myname + " \n" + function);
		System.out.println("___________________________________________________");

		System.out.println("\n");
		System.out.println("[1] Update User Register Info");
		System.out.println("[2] Projects");
		System.out.println("[3] Tasks\n");
		System.out.println("Please choose an option: \n");
		System.out.println("___________________________________________________");
		System.out.println("[B] Back");
		System.out.println("[M] MainMenu");
		System.out.println("[E] Exit \n");

		String option = scannerInput.nextLine().toUpperCase();

		switch (option) {
		case "1":
			US201and202UpdateUserInfoUI updateUserInfoUI = new US201and202UpdateUserInfoUI(this.user);
			updateUserInfoUI.chooseWhatInfoToUpdate();
			break;
		case "2":
			CollectProjectsFromUserUI collectProjectsFromUserUI = new CollectProjectsFromUserUI(this.user);
			collectProjectsFromUserUI.collectProjectsFromUser();
			break;
		case "3":
			UserTasksFunctionalitiesMenuUI tasksFunctionalities = new UserTasksFunctionalitiesMenuUI(user);
			tasksFunctionalities.displayFunctionalities();
			break;

		case "B":
			break;
		case "M":
			MainMenuUI.mainMenu();
			break;
		case "E":
			System.exit(0);
			break;
		default:
			displayOptions();
		}
	}
}
