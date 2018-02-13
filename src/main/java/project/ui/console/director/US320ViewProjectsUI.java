package project.ui.console.director;

import java.util.List;
import java.util.Scanner;

import project.controller.US320ViewProjectsController;
import project.model.Project;

public class US320ViewProjectsUI {

	public Project viewProjectsUI(Project selectedProject) {

		Scanner input = new Scanner(System.in);
		US320ViewProjectsController controller = new US320ViewProjectsController();
		List<String> projectsList;

		System.out.println("Type [0] to view active projects, or any key to view all projects:");
		System.out.println("");

		if (input.nextLine().equals("0")) {
			projectsList = controller.viewActiveProjects();
		} else {
			projectsList = controller.viewAllProjects();
		}

		if (projectsList.isEmpty()) {
			System.out.println("No projects listed!");
			System.out.println("");
		} else {
			for (String other : projectsList) {
				System.out.println(other);
				System.out.println("");
			}

			System.out.println(
					"Please choose a project index (If a valid number isn't provided, no project will be selected.):");
			if (input.hasNextInt()) {
				int index = Integer.parseInt(input.nextLine());
				selectedProject = controller.selectProject(index);
			} else {
				System.out.println("Not a number!");
			}

		}
		System.out.println("Returning to Director Menu...");
		System.out.println("");

		return selectedProject;
	}

}
