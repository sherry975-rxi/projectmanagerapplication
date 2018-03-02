package project.ui.console.director;

import project.controller.US301CreateProjectController;
import project.model.EffortUnit;
import project.model.User;

import java.util.List;
import java.util.Scanner;

public class US301CreateProjectUI {

	String projectName = "(Insert Name)";
	String projectDescription = "(Insert Description)";
	String projectManagerName = "(Select Manager)";

	EffortUnit currentEffortUnit = EffortUnit.HOURS;

	Integer budget = 0;
	User projectManager = null;

	boolean isProjectCreationOngoing = true;

	String mainCommand;
	String dataInput;

	public void createProject() {

		US301CreateProjectController controller = new US301CreateProjectController();
		Scanner mainComm = new Scanner(System.in);
		Scanner dataIn = new Scanner(System.in);

		System.out.println("To create a project, input the following fields:");
		System.out.println(viewOptions());
		System.out.println("");

		while (isProjectCreationOngoing) {

			System.out.println("Please choose a command to input a field:");
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
				dataIn = this.caseThree(controller, dataIn);
				break;

			case "4":
				dataIn = this.caseFour(dataIn);
				break;

			case "5":
				dataIn = this.caseFive(dataIn);
				break;

			case "C":
				this.caseC(controller);
				break;

			case "B":
				System.out.println("Project creation cancelled!");
				System.out.println("");
				isProjectCreationOngoing = false;
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

	/**
	 * This method displays the available commands, as well as the data already
	 * inputed in each of the fields
	 * 
	 * @return a String of all commands and data to be used in Project creation
	 */
	private String viewOptions() {
		String options = "[1] - Project Name: " + projectName;
		options += "\n[2] - Project Description: " + projectDescription;
		options += "\n[3] - Project Manager: " + projectManagerName;
		options += "\n[4] - Project Effort Unit: " + currentEffortUnit.toString();
		options += "\n[5] - Project Budget: " + budget;
		options += "\n[any key] - View Data and commands";
		options += "\n[C] - Create Project with chosen data";
		options += "\n[B] - Cancel Project creation and return to Director Menu";

		return options;
	}

	/**
	 * Case three deals with the selection of a project manager. It receives the
	 * controller and the data Input Scanner, and executes the Project Manager
	 * selection method
	 * 
	 * @param controller
	 * @param dataIn
	 * 
	 * @return the given scanner, so its input can be refreshed and not read by the
	 *         next scanner
	 */
	private Scanner caseThree(US301CreateProjectController controller, Scanner dataIn) {
		List<String> projectCollaborators = controller.listActiveCollaborators();

		if (projectCollaborators.isEmpty()) {
			System.out.println("No Collaborators available, project creation impossible!!");
			isProjectCreationOngoing = false;

		} else {
			System.out.println("Please select a collaborator:");
			System.out.println("");
			for (String other : projectCollaborators) {
				System.out.println(other);
				System.out.println("");
			}
			System.out.println("(Type only the number from the list, invalid number won't change current selection");

			if (dataIn.hasNextInt()) {
				projectManager = controller.selectCollaborator(dataIn.nextInt());
				dataInput = dataIn.nextLine();

				if (projectManager != null) {
					projectManagerName = projectManager.getName();
				}
			}

			else {
				dataInput = dataIn.nextLine();
				System.out.println("Not a number!");
				System.out.println("");
			}
		}
		return dataIn;

	}

	/**
	 * Case four deals with the selection of effort units. It receives the scanner
	 * and current Effort Units as String, and executes the selection method
	 * 
	 * @param dataIn
	 * @param hourString
	 * 
	 * @return the given scanner, so its input can be refreshed and not read by the
	 *         next scanner
	 */
	private Scanner caseFour(Scanner dataIn) {
		System.out.println("Press [0] to change the effort Unit of your project:");
		System.out.println("(Currently: " + currentEffortUnit.toString() + ")");
		System.out.println("");
		dataInput = dataIn.nextLine();
		if ("0".equals(dataInput)) {
			if (currentEffortUnit.equals(EffortUnit.HOURS)) {
				currentEffortUnit = EffortUnit.PERSON_MONTH;
				System.out.println("The effort Unit of this project changed to Person/Month.");
			} else {
				currentEffortUnit = EffortUnit.HOURS;
				System.out.println("The effort Unit of this project changed to Hours.");
			}
			System.out.println("");
		}
		return dataIn;
	}

	/**
	 * Case five deals with the selection of budget. It receives the scanner and,
	 * and executes the selection method
	 * 
	 * @param dataIn
	 * 
	 * @return the given scanner, so its input can be refreshed and not read by the
	 *         next scanner
	 */
	private Scanner caseFive(Scanner dataIn) {
		System.out.println("Please type the budget of your project:");
		System.out.println("(Currently:" + budget + ")");
		System.out.println("");
		if (dataIn.hasNextInt()) {
			budget = dataIn.nextInt();
			dataInput = dataIn.nextLine();
		} else {
			dataInput = dataIn.nextLine();
			System.out.println("Not a number!");
		}
		return dataIn;
	}

	/**
	 * This case attempts to create the project with the data provided. Should a
	 * project manager not exit, creation will fail and return to data submission
	 * form
	 * 
	 * @param controller
	 */

	private void caseC(US301CreateProjectController controller) {
		if (projectManager == null) {
			System.out.println("Please select a Project Manager first!");
			System.out.println("");
		} else {
			controller.createProject(projectName, projectDescription, projectManager);
			controller.changeBudget(budget);
			if (currentEffortUnit.equals(EffortUnit.PERSON_MONTH)) {
				controller.changeEffortUnitToPersonMonth();
			}
			System.out.println("Project created!");
			System.out.println("");
			isProjectCreationOngoing = false;

		}
	}

}
