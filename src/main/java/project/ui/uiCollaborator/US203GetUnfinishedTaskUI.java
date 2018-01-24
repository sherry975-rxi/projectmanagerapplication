package project.ui.uiCollaborator;

import java.util.Scanner;

import project.controller.US203GetUnfinishedTaskByUser;
import project.model.User;
import project.ui.MainMenuUI;

public class US203GetUnfinishedTaskUI {
	private User user;
	private Boolean isPreviousUIFromTasks;
	private Integer projectID;

	public void displayOptions(User user) {
		this.user = user;
		US203GetUnfinishedTaskByUser unfinishedTaskByUser = new US203GetUnfinishedTaskByUser();
		int t;
		t = 0;
		Scanner scannerInput = new Scanner(System.in);

		String myname = user.getName();
		String function = user.getFunction().toUpperCase();
		String taskIdentifier = null;
		
		System.out.println("\n" + myname + " \n" + function);
		System.out.println("___________________________________________________");

		for (int i = 0; i < unfinishedTaskByUser.getUnfinishedTasksOfProjectCollaborator(user).size(); i++) {
			t = t + 1;
			System.out.println("[" + t + "] " + " "
					+ unfinishedTaskByUser.getUnfinishedTasksOfProjectCollaborator(user).get(i).getTaskID() + " "
					+ unfinishedTaskByUser.getUnfinishedTasksOfProjectCollaborator(user).get(i).getDescription());
			
			taskIdentifier = ( t + unfinishedTaskByUser.getUnfinishedTasksOfProjectCollaborator(user).get(i).getTaskID()).toString();
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
			
		default:
			try {
				
				if (option == taskIdentifier) {
				this.isPreviousUIFromTasks = true; 
				TaskDetailsUI userTasks1 = new TaskDetailsUI(taskIdentifier, this.projectID, this.user, this.isPreviousUIFromTasks);
				userTasks1.taskDataDisplay();				
				}
			}
			catch (NullPointerException npe) {
				System.out.println("Please choose a valid option: ");
				System.out.println("");
				ProjectViewMenuUI myAtualUIView = new ProjectViewMenuUI(projectID, user);
				myAtualUIView.projectDataDisplay();
			}
			break;
		}
	}

}
