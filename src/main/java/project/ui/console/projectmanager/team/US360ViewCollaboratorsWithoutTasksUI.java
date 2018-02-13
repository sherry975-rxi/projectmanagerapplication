package project.ui.console.projectmanager.team;

import java.util.List;
import java.util.Scanner;

import project.controller.US360ViewCollaboratorsWithoutTasksController;
import project.model.Project;
import project.model.User;
import project.ui.console.MainMenuUI;

public class US360ViewCollaboratorsWithoutTasksUI {

	Project selectedProject;
	User user;

	public US360ViewCollaboratorsWithoutTasksUI(Project selectedProject, User user) {
		this.selectedProject = selectedProject;
		this.user = user;
	}

	public void viewUnassignedCollaborators() {

		List<String> idleCollaboratorsInfo;

		US360ViewCollaboratorsWithoutTasksController controller = new US360ViewCollaboratorsWithoutTasksController(
				this.selectedProject);

		idleCollaboratorsInfo = controller.showCollaboratorsWithoutTasks();

		System.out.println(
				"_________________________________________________________________________________________________________________");
		if (idleCollaboratorsInfo.isEmpty())
			System.out.println("\nEvery Team member has one or more tasks assigned!");
		else {
			System.out.println("\nThe following Collaborators have no Tasks assigned\n");
			for (String other : idleCollaboratorsInfo) {
				System.out.println(other);
				System.out.println("");
			}
		}
		System.out.println(
				"_________________________________________________________________________________________________________________");
		System.out.println("[B] Back");
		System.out.println("[M] MainMenu");

		Scanner input = new Scanner(System.in);
		String choice = input.nextLine().toUpperCase();

		switch (choice) {
		case "B":
			return;

		case "M":
			MainMenuUI.mainMenu();
			break;

		default:
			System.out.println("Please choose a valid option!");
			viewUnassignedCollaborators();

		}

	}
}
