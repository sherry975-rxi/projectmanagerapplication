package project.ui.console.uiProjectManager.tasks;

import java.util.List;
import java.util.Scanner;

import project.controller.US361AssignTaskToCollaboratorsController;
import project.model.Project;
import project.model.Task;
import project.model.User;
import project.ui.console.MainMenuUI;

public class US361AssignUserToTaskUI {

	User user;
	Project project;
	Task task;

	public US361AssignUserToTaskUI(Project project, Task task, User user) {
		this.user = user;
		this.project = project;
		this.task = task;
	}

	public void displayUsersToAssign() {

		System.out.println("            PROJECT COLLABORATORS           ");
		System.out.println("____________________________________________");

		US361AssignTaskToCollaboratorsController assignController = new US361AssignTaskToCollaboratorsController(
				project, task);
		List<String> userList = assignController.getProjectActiveTeam();
		int number = 1;

		for (String other : userList) {
			System.out.println("[" + number + "]" + other + "\n");
			number++;
		}

		chooseAction(assignController);
	}

	public void chooseAction(US361AssignTaskToCollaboratorsController assignController) {

		Scanner input = new Scanner(System.in);
		boolean condition = true;
		while (condition) {

			System.out.println("-------------CHOOSE A USER TO ADD-------------");
			System.out.println("[B] Back");
			System.out.println("[M] MainMenu");
			System.out.println("[E] Exit");
			System.out.println();

			String choice = input.nextLine().toUpperCase();

			switch (choice) {

			case "B":
				PmTaskFunctionalitiesUI caseBack = new PmTaskFunctionalitiesUI(task.getTaskID(), this.project,
						this.user);
				caseBack.taskDataDisplay();
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
				chooseUserToAssign(assignController, choice);
				break;
			}
		}
	}

	public void chooseUserToAssign(US361AssignTaskToCollaboratorsController assignController, String choice) {

		Integer choiceInt;

		try {
			choiceInt = Integer.parseInt(choice);
			int listSize = assignController.getProjectActiveTeam().size();

			if (choiceInt != null && choiceInt > 0 && choiceInt <= listSize) {
				assignController.setUserToAddToTask(choiceInt - 1);
				if (assignController.assignCollaboratorToTask()) {
					System.out.println("--------USER ASSIGNED--------");
					PmTaskFunctionalitiesUI caseBack = new PmTaskFunctionalitiesUI(task.getTaskID(), this.project,
							this.user);
					caseBack.taskDataDisplay();

				} else {
					System.out.println("Choose a valid user!");
					PmTaskFunctionalitiesUI caseBack = new PmTaskFunctionalitiesUI(task.getTaskID(), this.project,
							this.user);
					caseBack.taskDataDisplay();
				}
			}
		}

		catch (NumberFormatException npe) {
			System.out.println("-----Insert a valid user!-----");
		}
	}
}
