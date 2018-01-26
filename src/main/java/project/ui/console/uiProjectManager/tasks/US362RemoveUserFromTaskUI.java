package project.ui.console.uiProjectManager.tasks;

import java.util.List;
import java.util.Scanner;

import project.controller.US362RemoveTaskFromProjectCollaborator;
import project.model.Project;
import project.model.Task;
import project.model.User;
import project.ui.console.MainMenuUI;
import project.ui.console.uiProjectManager.ProjectManagerMainMenuUI;

public class US362RemoveUserFromTaskUI {

	Project project;
	Task task;
	User user;

	public US362RemoveUserFromTaskUI(Project project, Task task, User user) {
		this.user = user;
		this.project = project;
		this.task = task;
	}

	public void displayUsersToRemove() {

		System.out.println("            PROJECT COLLABORATORS           ");
		System.out.println("____________________________________________");
		System.out.println("\n");

		US362RemoveTaskFromProjectCollaborator removeColController = new US362RemoveTaskFromProjectCollaborator(project,
				task);
		List<String> userList = removeColController.getProjectCollaboratorsFromTask();
		int number = 1;

		if (userList.size() <= 0) {
			System.out.println("\n");
			System.out.println("-----Task team is empty-----");
			System.out.println("\n");
		} else {
			for (String other : userList) {
				System.out.println("[" + number + "]" + other + "\n");
				number++;
			}

			chooseAction(removeColController);
		}
	}

	public void chooseAction(US362RemoveTaskFromProjectCollaborator removeColController) {

		Scanner input = new Scanner(System.in);
		boolean condition = true;
		while (condition) {

			System.out.println("-------------CHOOSE A USER TO REMOVE-------------");
			System.out.println("\n");
			System.out.println("[B] Back");
			System.out.println("[M] MainMenu");
			System.out.println("[E] Exit");
			System.out.println();

			String choice = input.nextLine().toUpperCase();

			switch (choice) {

			case "B":
				break;
			case "M":
				MainMenuUI.mainMenu();
				break;
			case "E":
				System.out.println("\n");
				System.out.println("--YOU HAVE EXIT FROM THE APPLICATION--");
				condition = false;
				break;
			default:
				chooseUserToRemove(removeColController, choice);
				break;
			}
		}
	}

	public void chooseUserToRemove(US362RemoveTaskFromProjectCollaborator removeColController, String choice) {

		Integer choiceInt;

		try {
			choiceInt = Integer.parseInt(choice);
			int listSize = removeColController.getProjectCollaboratorsFromTask().size();

			if (choiceInt != null && choiceInt > 0 && choiceInt <= listSize) {
				removeColController.setProjectCollaborator(choiceInt - 1);
				if (removeColController.removeCollaboratorFromTask()) {
					System.out.println("--------USER REMOVED--------");
					ProjectManagerMainMenuUI projectmanagerMenu = new ProjectManagerMainMenuUI(user, project);
					projectmanagerMenu.displayOptions(); // to

				} else {
					System.out.println("Choose a valid user!");
					ProjectManagerMainMenuUI projectmanagerMenu = new ProjectManagerMainMenuUI(user, project);
				}
			}
		}

		catch (NumberFormatException npe) {
			System.out.println("-----Insert a valid user!-----");
		}
	}
}