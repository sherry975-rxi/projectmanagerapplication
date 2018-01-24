package project.ui.uiDirector;

import java.util.Scanner;

import project.model.Project;
import project.model.User;

public class DirectorMenuUI {

	User directorLoggedIn;
	Project selectedProject = null;

	String options = "[1] - view projects \n" + "[2] - create a project \n"
			+ "[3] - Change selected project's manager \n" + "[E] - exit to main menu";

	String command;

	public DirectorMenuUI(User admin) {
		this.directorLoggedIn = admin;
	}

	// TODO implement missing options!
	public void directorMenu() {
		Scanner input = new Scanner(System.in);

		boolean cycle = true;
		while (cycle) {

			if (selectedProject != null) {
				System.out.println("Project selected!");
				System.out.println(selectedProject.getIdCode() + " (" + selectedProject.getProjectStatusName() + ") - "
						+ selectedProject.getName());
				System.out.println("(Change project management command enabled)");
				System.out.println("");
			}
			System.out.println("Welcome to director menu, " + directorLoggedIn.getName());
			System.out.println("Please choose a command:");
			System.out.println(options);
			System.out.println("");

			command = input.nextLine().toLowerCase();

			switch (command) {
			case "1":
				US320ViewProjectsUI viewProjects = new US320ViewProjectsUI();
				selectedProject = viewProjects.viewProjectsUI(selectedProject);
				break;

			case "2":
				System.out.println("Not yet implemented!");
				break;

			case "3":
				if (selectedProject == null) {
					System.out.println("Please select a project first");
					System.out.println("");
				} else {
					System.out.println("Not yet implemented!");
				}
				break;

			case "e":
				System.out.println("Returning to main menu...");
				System.out.println("");
				cycle = false;
				break;

			default:
				System.out.println("Invalid input!");
				System.out.println("");
				break;
			}

		}

	}

}
