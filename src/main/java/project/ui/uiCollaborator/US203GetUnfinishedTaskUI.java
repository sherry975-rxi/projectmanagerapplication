package project.ui.uiCollaborator;

import java.util.Scanner;

import project.controller.US203GetUnfinishedTaskByUser;
import project.model.User;
import project.ui.MainMenuUI;

public class US203GetUnfinishedTaskUI {
	private User user;

	public void displayOptions(User user) {
		this.user = user;
		US203GetUnfinishedTaskByUser unfinishedTaskByUser = new US203GetUnfinishedTaskByUser();
		int t;
		t = 0;
		Scanner scannerInput = new Scanner(System.in);

		String myname = user.getName();
		String function = user.getFunction().toUpperCase();

		System.out.println("\n" + myname + " \n" + function);
		System.out.println("___________________________________________________");

		for (int i = 0; i < unfinishedTaskByUser.getUnfinishedTasksOfProjectCollaborator(user).size(); i++) {
			t = t + 1;
			System.out.println("["
					+ unfinishedTaskByUser.getUnfinishedTasksOfProjectCollaborator(user).get(i).getTaskID() + "]" + " "
					+ unfinishedTaskByUser.getUnfinishedTasksOfProjectCollaborator(user).get(i).getDescription());
		}
		System.out.println("___________________________________________________");
		System.out.println("[B] Back");
		System.out.println("[M] MainMenu");
		System.out.println("[E] Exit \n");

		String option = scannerInput.nextLine().toUpperCase();

		switch (option) {

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
