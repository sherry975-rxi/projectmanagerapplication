package project.ui.console.director;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.PrintProjectInfoController;
import project.model.Project;
import project.model.User;

import java.util.Scanner;

@Component
public class DirectorMenuUI {

	@Autowired
	private US301CreateProjectUI createProject;
	@Autowired
	private US302ChangeProjectManagerUI changeManager;
	@Autowired
	private	US320ViewProjectsUI viewProjects;

	private User directorLoggedIn;
	private Project selectedProject = null;

	String options = "[1] - View projects \n" + "[2] - Create a project \n"
			+ "[3] - Change selected project's manager \n" + "______________________________________________\n"
			+ "[B] Back\n";

	String command;

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

				PrintProjectInfoController projectInfo = new PrintProjectInfoController();
				projectInfo.setProject(selectedProject);

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
				selectedProject = viewProjects.viewProjectsUI(selectedProject);
				break;

			case "2":
				createProject.createProject();
				break;

			case "3":
				changeProjectManager();
				break;

			case "b":
				System.out.println("Returning to Director Menu...");
				cycle = false;
				break;

			default:
				System.out.println("Invalid input!");
				System.out.println("");
				break;
			}

		}

	}

	private void changeProjectManager() {

		if (selectedProject == null) {
			System.out.println("Please select a project first");
		} else {
			changeManager.changeProjectManager(selectedProject);
		}
	}

	public void setDirectorLoggedIn(User directorLoggedIn) {
		this.directorLoggedIn = directorLoggedIn;
	}
}
