package project.ui.uiDirector;

import java.util.Scanner;

import project.controller.US301CreateProjectController;
import project.model.Project;
import project.model.User;

public class US301CreateProjectUI {

	String projectName = "(Insert Name)";
	String projectDescription = "(Insert Description)";
	String projectManagerName = "(Select Manager)";
	String effortUnitName = "(Not selected)";
	String budget = "(Not added)";
	User projectManager = null;

	String mainCommand;
	String dataInput;

	Project createdProject = null;

	public Project createProject(Project selectedProject) {

		String options = "[1] - Project Name: " + projectName;
		options += "\n[2] - Project Description: " + projectDescription;
		options += "\n[3] - Project Manager: " + projectManagerName;
		options += "\n[4] - Project Effort Unit: " + effortUnitName + "[OPTIONAL]";
		options += "\n[5] - Project Budget: " + budget + "[OPTIONAL]";
		options += "\n[any key] - view Data and commands";
		options += "\n[C] - create Project with chosen data";
		options += "\n[E] - exit to director menu";

		US301CreateProjectController controller = new US301CreateProjectController();
		Scanner input = new Scanner(System.in);
		boolean cycle = true;

		System.out.println("To create a project, fill out the following fields:");
		System.out.println("");
		System.out.println(options);
		System.out.println("");

		while (cycle) {

			mainCommand = input.nextLine().toUpperCase();
			switch (mainCommand) {
			case "1":
				System.out.println("Please insert a name for your project:");
				projectName = input.nextLine();
				break;

			case "2":
				System.out.println("Please insert a description for your project:");
				projectDescription = input.nextLine();
				break;

			case "3":
				System.out.println("Not yet implemented!");
				System.out.println("");
				break;

			case "4":
				System.out.println("Please select the effort Unit of your project:");
				System.out.println("(Type [0] for Hours, [1] for People/Month anything else to reset");
				System.out.println("");
				dataInput = input.nextLine();
				if (dataInput.equals("0"))
					effortUnitName = "Hours";
				else if (dataInput.equals("1"))
					effortUnitName = "People/Month";
				else {
					effortUnitName = "(Not selected)";
				}
				break;

			case "5":
				System.out.println("Please select the budget of your project:");
				System.out.println("(Type a number to set budget, else to reset)");
				System.out.println("");
				if (input.hasNextInt())
					budget = input.nextLine();
				else {
					dataInput = input.nextLine();
					budget = "(Not added)";
				}
				break;

			case "C":
				if (projectManager == null) {
					System.out.println("Please select a Project Manager first!");
					System.out.println("");
				} else {
					selectedProject = controller.createProject(projectName, projectDescription, projectManager);
					cycle = false;
					System.out.println("Project created!");
					System.out.println("");
				}
				break;

			case "E":
				System.out.println("Project creation cancelled!");
				System.out.println("");
				cycle = false;
				break;

			default:
				System.out.println("You have the following data:");
				System.out.println("");
				System.out.println(options);
			}

		}

		System.out.println("Returning to main menu...");
		System.out.println("");

		return selectedProject;
	}

}
