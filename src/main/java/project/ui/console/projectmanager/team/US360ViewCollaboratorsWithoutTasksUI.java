package project.ui.console.projectmanager.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US360ViewCollaboratorsWithoutTasksController;
import project.model.Project;

import java.util.List;
import java.util.Scanner;

@Component
public class US360ViewCollaboratorsWithoutTasksUI {

	@Autowired
	private US360ViewCollaboratorsWithoutTasksController controller;

	private Project selectedProject;

	public US360ViewCollaboratorsWithoutTasksUI() {
	}

	public void viewUnassignedCollaborators() {
		

		List<String> idleCollaboratorsInfo;

		idleCollaboratorsInfo = controller.showCollaboratorsWithoutTasks(selectedProject);

		boolean loop = true;
		while (loop) {
			loop = false;
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
		Scanner input = new Scanner(System.in);
		String choice = input.nextLine().toUpperCase();

		switch (choice) {
		case "B":
			return;

		default:
			System.out.println("Please choose a valid option!");
			loop = true;

		}
		}
	}

	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject;
	}
}
