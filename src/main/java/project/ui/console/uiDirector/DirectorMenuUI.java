package project.ui.console.uiDirector;

import java.util.Scanner;

import project.controller.PrintProjectInfoController;
import project.model.Project;
import project.model.User;

public class DirectorMenuUI {

	User directorLoggedIn;
	Project selectedProject = null;

	String options = "[1] - View projects \n" + "[2] - Create a project \n"
			+ "[3] - Change selected project's manager \n" + "______________________________________________\n"
			+ "[B] Back\n" + "[E] Exit";

	String command;

	public DirectorMenuUI(User admin) {
		this.directorLoggedIn = admin;
	}

	public void directorMenu() {
		Scanner input = new Scanner(System.in);

		boolean cycle = true;
		while (cycle) {
			System.out.println("");
			System.out.println(
					"———————————————————————————————————————————MENU DIRECTOR——————————————————————————————————————————————————");
			System.out.println("Welcome to Director Menu, " + directorLoggedIn.getName());
			System.out.println("______________________________________________");

			System.out.println("");

			if (selectedProject != null) {
				System.out.println("> Project selected!!");
				System.out.println("");

				PrintProjectInfoController projectInfo = new PrintProjectInfoController(selectedProject);

				System.out.println("PROJECT " + projectInfo.printProjectNameInfo().toUpperCase());
				System.out.println("___________________________________________________");
				System.out.println("ID: " + projectInfo.printProjectIDCodeInfo());
				System.out.println("STATUS: " + selectedProject.getProjectStatusName());
				System.out.println("DESCRIPTION: " + projectInfo.printProjectDescriptionInfo());
				System.out.println("START DATE: " + projectInfo.printProjectStartDateInfo());
				System.out.println("PROJECT MANAGER: " + projectInfo.printProjectManagerInfo());
				System.out.println("PROJECT BUDGET: " + projectInfo.printProjectBudgetInfo());
				System.out.println("EFFORT UNITS: " + selectedProject.getEffortUnit().toString());
				System.out.println("");
				System.out.println("___________________________________________________");

				System.out.println("(Change project management command enabled)");
				System.out.println("");
			}
			System.out.println("");
			System.out.println("Please choose a command to input a field:");
			System.out.println(options);
			System.out.println("");

			command = input.nextLine().toLowerCase();

			switch (command) {
			case "1":
				US320ViewProjectsUI viewProjects = new US320ViewProjectsUI();
				selectedProject = viewProjects.viewProjectsUI(selectedProject);
				break;

			case "2":
				US301CreateProjectUI createProject = new US301CreateProjectUI();
				createProject.createProject();
				break;

			case "3":
				if (selectedProject == null) {
					System.out.println("Please select a project first");
					System.out.println("");
				} else {
					US302ChangeProjectManagerUI changeManager = new US302ChangeProjectManagerUI();
					changeManager.changeProjectManager(selectedProject);
				}
				break;

			case "b":
				System.out.println("Returning to Director Menu...");
				System.out.println("");
				cycle = false;
				break;
			case "e":
				System.out.println("--YOU HAVE EXITED FROM APPLICATION--");
				System.exit(0);
				break;

			default:
				System.out.println("Invalid input!");
				System.out.println("");
				break;
			}

		}

	}

}
