package project.ui.console.projectmanager.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US361AssignTaskToCollaboratorsController;
import project.model.Project;
import project.model.Task;

import java.util.List;
import java.util.Scanner;

@Component
public class US361AssignUserToTaskUI {

	@Autowired
	private US361AssignTaskToCollaboratorsController assignController;

	private Project project;
	private Task task;

	public US361AssignUserToTaskUI() {
	}

	public void displayUsersToAssign() {

		System.out.println("            PROJECT COLLABORATORS           ");
		System.out.println("____________________________________________");

		assignController.setProject(project);
		assignController.setTask(task);
		List<String> userList = assignController.getProjectActiveTeam();
		int number = 1;

		for (String other : userList) {
			System.out.println("[" + number + "]" + other + "\n");
			number++;
		}

		chooseAction();
	}

	public void chooseAction() {

		Scanner input = new Scanner(System.in);
		boolean condition = true;
		while (condition) {

			System.out.println("-------------CHOOSE A USER TO ADD-------------");
			System.out.println("\n");
			System.out.println("[B] Back");

			String choice = input.nextLine().toUpperCase();

			if ("B".equals(choice)) {
				System.out.println("\n");
				System.out.println("--YOU HAVE QUITTED THE MENU--");
				condition = false;
			} else {
				chooseUserToAssign(choice);
			}

		}
	}

	public void chooseUserToAssign(String choice) {

		Integer choiceInt;

		try {
			choiceInt = Integer.parseInt(choice);
			int listSize = assignController.getProjectActiveTeam().size();

			if (choiceInt != null && choiceInt > 0 && choiceInt <= listSize) {
				assignController.setUserToAddToTask(choiceInt - 1);
				if (assignController.assignCollaboratorToTask()) {
					System.out.println("--------USER ASSIGNED--------");

				} else {
					System.out.println("The selected user cannot be assigned to the chosen task!");
				}
			}
		}

		catch (NumberFormatException nfe) {
			System.out.println("-----An invalid number was inserted!-----");
		}
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setTask(Task task) {
		this.task = task;
	}
}
