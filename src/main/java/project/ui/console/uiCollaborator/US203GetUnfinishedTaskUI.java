package project.ui.console.uiCollaborator;

import java.util.Scanner;

import project.controller.US203GetUnfinishedTaskByUser;
import project.model.User;
import project.ui.console.MainMenuUI;

public class US203GetUnfinishedTaskUI {
	private User user;
	private Boolean isPreviousUIFromTasks;
	
	private String[] split;
	private int projID;

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
			UserTasksFunctionalitiesMenuUI previousMenu = new UserTasksFunctionalitiesMenuUI(user);
			previousMenu.displayFunctionalities();
			break;
		case "M":
			MainMenuUI.mainMenu();
			break;
		case "E":
			System.exit(0);
			break;
		default:
			try {
				split = option.split("\\.");
				projID = Integer.valueOf(split[0]);

				TaskDetailsUI taskSelected = new TaskDetailsUI(option, projID, this.user, this.isPreviousUIFromTasks);
				taskSelected.taskDataDisplay();
			}

			catch (NullPointerException npe) {
				System.out.println("Please choose a valid option: ");
				System.out.println("");
				US203GetUnfinishedTaskUI unfinishedTaskByUser1 = new US203GetUnfinishedTaskUI();
				unfinishedTaskByUser1.displayOptions(this.user);
			}

			break;
		}
	}
}
