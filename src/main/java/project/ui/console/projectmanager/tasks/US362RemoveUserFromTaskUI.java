package project.ui.console.projectmanager.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controller.US362RemoveTaskFromProjectCollaborator;
import project.model.Project;
import project.model.Task;
import project.model.User;

import java.util.List;
import java.util.Scanner;

@Component
public class US362RemoveUserFromTaskUI {

	@Autowired
	private US362RemoveTaskFromProjectCollaborator removeColController;

	private Project project;
	private Task task;

	public US362RemoveUserFromTaskUI() {
	}

	public void displayUsersToRemove() {

		System.out.println("            PROJECT COLLABORATORS           ");
		System.out.println("____________________________________________");
		System.out.println("\n");

		removeColController.setProject(project);
		removeColController.setTask(task);
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

			chooseAction();
		}
	}

	public void chooseAction() {

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
				chooseUserToRemove(option);
			}
		}
	}

	public void chooseUserToRemove(String choice) {

		Integer choiceInt;

		try {
			choiceInt = Integer.parseInt(choice);
			int listSize = removeColController.getProjectCollaboratorsFromTask().size();

			if (choiceInt != null && choiceInt > 0 && choiceInt <= listSize) {
				removeColController.setProjectCollaborator(choiceInt - 1);
				if (removeColController.removeCollaboratorFromTask()) {
					System.out.println("--------USER REMOVED--------");

				} else {
					System.out.println("The selected user cannot be removed from the chosen task!");
				}
			}
		}

		catch (NumberFormatException npe) {
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