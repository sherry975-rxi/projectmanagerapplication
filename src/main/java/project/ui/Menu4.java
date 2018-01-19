package project.ui;

import java.util.Scanner;

import project.model.User;

/**
 * @author group3
 *
 */

public class Menu4 {

	private User user;

	/**
	 * Creates the UI
	 * 
	 * @param user
	 */
	public Menu4(User user) {
		this.user = user;

	}

	public void displayOptions() {
		Scanner scannerInput = new Scanner(System.in);

		String myname = user.getName();
		String function = user.getFunction().toUpperCase();

		System.out.println("\n" + myname + " \n" + function);
		System.out.println("___________________________________________________");

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
			UpdateUserInfoUI updateUserInfoUI = new UpdateUserInfoUI(this.user);
			updateUserInfoUI.chooseWhatInfoToUpdate();
			break;
		case "2":
			CollectProjectsFromUserUI collectProjectsFromUserUI = new CollectProjectsFromUserUI(this.user);
			collectProjectsFromUserUI.collectProjectsFromUser();
			break;
		case "3":

		case "B":
			// Menu3 menuThree = new Menu3();
			// // TODO when this menu is done is necessary to include a method.
			break;
		case "M":
			MainMenuUI.mainMenu();

			break;
		case "E":
			System.exit(0);
		}
	}
}
