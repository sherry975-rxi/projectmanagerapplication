package project.ui.console.projectmanager.tasks;

import project.controller.US362RemoveTaskFromProjectCollaborator;
import project.controller.UpdateDbToContainersController;
import project.model.Project;
import project.model.Task;
import project.model.User;

import java.util.List;
import java.util.Scanner;

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
		UpdateDbToContainersController infoUpdater = new UpdateDbToContainersController();
		infoUpdater.updateDBtoContainer();

		System.out.println("            PROJECT COLLABORATORS           ");
		System.out.println("____________________________________________");
		System.out.println("\n");

		US362RemoveTaskFromProjectCollaborator removeColController = new US362RemoveTaskFromProjectCollaborator(project,
				task);
		List<String> userList = removeColController.getProjectCollaboratorsFromTask();
		int number = 1;

		if (userList.isEmpty()) {
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
		boolean repeat = true;
		while (repeat) {

			System.out.println("-------------CHOOSE A USER TO ADD-------------");
			System.out.println("\n");
			System.out.println("[B] Back");

			String option = input.nextLine().toUpperCase();

			if ("B".equals(option)) {
				System.out.println("\n");
				System.out.println("--YOU HAVE QUITTED THE MENU--");
				repeat = false;
			} else {
				chooseUserToRemove(removeColController, option);
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