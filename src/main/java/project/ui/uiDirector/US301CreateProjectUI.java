package project.ui.uiDirector;

import java.util.List;
import java.util.Scanner;

import project.controller.US301CreateProjectController;
import project.model.User;

public class US301CreateProjectUI {

	String projectName = "(Insert Name)";
	String projectDescription = "(Insert Description)";
	String projectManagerName = "(Select Manager)";
	String effortUnitName = "Hours";
	Integer budget = 0;
	User projectManager = null;

	String mainCommand;
	String dataInput;

	public void createProject() {

		US301CreateProjectController controller = new US301CreateProjectController();
		Scanner mainComm = new Scanner(System.in);
		Scanner dataIn = new Scanner(System.in);
		boolean cycle = true;
		List<String> projectCollaborators;

		System.out.println("To create a project, input the following fields:");
		System.out.println(viewOptions());
		System.out.println("");

		while (cycle) {

			if (projectManager != null) {
				projectManagerName = projectManager.getName();
			}

			System.out.println("Choose a command:");
			mainCommand = mainComm.nextLine().toUpperCase();

			switch (mainCommand) {
			case "1":
				System.out.println("Please insert a name for your project:");
				dataInput = dataIn.nextLine();
				projectName = dataInput;
				break;

			case "2":
				System.out.println("Please insert a description for your project:");
				dataInput = dataIn.nextLine();
				projectDescription = dataInput;
				break;

			case "3":
				projectCollaborators = controller.listActiveCollaborators();
				if (projectCollaborators.size() == 0) {
					System.out.println("No Collaborators available, project creation impossible!!");
					cycle = false;

				} else {
					System.out.println("Please select a collaborator:");
					System.out.println("");
					for (String other : projectCollaborators) {
						System.out.println(other);
						System.out.println("");
					}
					System.out.println(
							"(Type only the number from the list, invalid number won't change current selection");

					if (dataIn.hasNextInt()) {
						projectManager = controller.selectCollaborator(dataIn.nextInt());
					}

					else {
						dataInput = dataIn.nextLine();
						System.out.println("Not a number!");
						System.out.println("");
					}
				}
				break;

			case "4":
				System.out.println("Press [0] to change the effort Unit of your project:");
				System.out.println("(Currently:" + effortUnitName + ")");
				System.out.println("");
				dataInput = dataIn.nextLine();
				System.out.println(dataInput);
				if (dataInput.equals("0")) {
					if (effortUnitName.equals("Hours")) {
						effortUnitName = "Person/Month";
					} else
						effortUnitName = "Hours";
				}
				break;

			case "5":
				System.out.println("Please type the budget of your project:");
				System.out.println("(Currently:" + budget + ")");
				System.out.println("");
				if (dataIn.hasNextInt())
					budget = dataIn.nextInt();
				else {
					dataInput = dataIn.nextLine();
					System.out.println("Not a number!");
				}
				break;

			case "C":
				if (projectManager == null) {
					System.out.println("Please select a Project Manager first!");
					System.out.println("");
				} else {
					controller.createProject(projectName, projectDescription, projectManager);
					controller.changeBudget(budget);
					if (effortUnitName.equals("Person/Month")) {
						controller.changeEffortUnitToPersonMonth();
					}
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
				System.out.println(viewOptions());
				System.out.println("");
			}

		}

		System.out.println("Returning to main menu...");
		System.out.println("");

	}

	private String viewOptions() {
		String options = "[1] - Project Name: " + projectName;
		options += "\n[2] - Project Description: " + projectDescription;
		options += "\n[3] - Project Manager: " + projectManagerName;
		options += "\n[4] - Project Effort Unit: " + effortUnitName;
		options += "\n[5] - Project Budget: " + budget;
		options += "\n[any key] - view Data and commands";
		options += "\n[C] - create Project with chosen data";
		options += "\n[E] - exit to director menu";

		return options;
	}

}
