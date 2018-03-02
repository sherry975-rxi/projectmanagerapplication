package project.ui.console.projectmanager.others;

import project.controller.PrintProjectInfoController;
import project.controller.US340CreateTaskController;
import project.model.Project;
import project.ui.console.MainMenuUI;

import java.util.Scanner;

/**
 * Constructor to instantiate a new US340CreateTaskUI
 * 
 * @param user
 *            User Project Manager
 * @param project
 *            Project where the user is Project manager
 * @param task
 *            Task which will be created by the Project manager in the target
 *            project
 * 
 */
public class US340CreateTaskUI {

	private Project project;

	public US340CreateTaskUI(Project project) {
		this.project = project;
	}

	public void createTask() {

		String line = "___________________________________________________";
		String blank = "";
		Scanner scannerInput = new Scanner(System.in);

		PrintProjectInfoController projectInfo = new PrintProjectInfoController(this.project);

		System.out.println(blank);
		System.out.println("PROJECT " + projectInfo.printProjectNameInfo().toUpperCase());
		System.out.println(line);
		System.out.println("ID: " + projectInfo.printProjectIDCodeInfo());
		System.out.println("STATUS: " + projectInfo.printProjectStatusInfo());
		System.out.println("DESCRIPTION: " + projectInfo.printProjectDescriptionInfo());
		System.out.println("START DATE: " + projectInfo.printProjectStartDateInfo());
		System.out.println("FINISH DATE: " + projectInfo.printProjectFinishDateInfo());
		System.out.println("PROJECT MANAGER: " + projectInfo.printProjectManagerInfo());
		System.out.println("PROJECT TEAM: " + projectInfo.printProjectTeamInfo());
		System.out.println("PROJECT BUDGET: " + projectInfo.printProjectBudgetInfo() + "\n");
		System.out.println(line);
		System.out.println("     CREATE TASK");
		System.out.println(line);

		Scanner input = new Scanner(System.in);

		System.out.println("Do you want to create a new task in this project?");
		System.out.println("[Y] to create task");
		System.out.println("[N] to cancel \n");

		String yerOrNo = input.nextLine();
		US340CreateTaskController createTaskController = new US340CreateTaskController(this.project);

		while (!("n".equalsIgnoreCase(yerOrNo)) && !("y".equalsIgnoreCase(yerOrNo))) {
			System.out.println("\nInvalid answer. Please try again (\"y\" or \"n\")");
			yerOrNo = input.nextLine();
		}

		if ("y".equalsIgnoreCase(yerOrNo)) {
			System.out.println();
			System.out.println("Insert information relative to the task.");

		} else {
			System.out.println();
			System.out.println("Conditions not accepted.");
			System.out.println();
			return;
		}

		System.out.println("Enter description to the task: ");
		String description = scannerInput.nextLine();

		System.out.println("Enter estimated task effort: ");
		Scanner estimatedTaskEffort = new Scanner(System.in);

		int number = estimatedTaskEffortUI(estimatedTaskEffort);

		System.out.println("Thank you! Got " + number);

		System.out.println("Enter estimated cost: ");
		Scanner estimatedCost = new Scanner(System.in);

		int cost = estimatedTaskCostUI(estimatedCost);

		System.out.println("Thank you! Got " + cost);

		System.out.println();
		System.out.println("You added the following data for the task: ");
		System.out.println(blank + "[1] Task description: " + description);
		System.out.println(blank + "[2] Estimated task effort: " + number);
		// System.out.println(" " + "[3] Estimated task start date: " +
		// estimatedTaskStartDate);
		// System.out.println(" " + "[4] Task deadline: " + taskDeadline);
		System.out.println(blank + "[5] Estimated Cost: " + cost);

		System.out.println();
		System.out.println("Is your data correct? (y to confirm; n to deny)");

		String confirm = scannerInput.nextLine();

		if ("y".equalsIgnoreCase(confirm)) {
			createTaskController.addTask(description);
			System.out.println();
			System.out.println("Task was successfully added to this project.");
			System.out.println();

		} else { // In case user choose "n".
			System.out.println();
			System.out.println("Creation of task cancelled.");
			System.out.println();

		}

		System.out.println(line);
		System.out.println("[B] Back");
		System.out.println("[M] MainMenu \n");

		String option = scannerInput.nextLine().toUpperCase();

		while (!("M".equals(option)) && !("B".equals(option))) {
			System.out.println("Please enter a valid option!");
			option = scannerInput.nextLine().toUpperCase();
		}

		if ("B".equals(option)) {
			return;
		} else if ("M".equals(option)) {
			MainMenuUI.mainMenu();
		}
	}

	private static int estimatedTaskEffortUI(Scanner estimatedTaskEffort) {
		int result = 0;
		do {
			System.out.println("Please enter a positive number!");
			while (!estimatedTaskEffort.hasNextInt()) {
				System.out.println("That's not a number!");
				estimatedTaskEffort.next(); // this is important!
			}
			result = estimatedTaskEffort.nextInt();
			estimatedTaskEffort.nextLine();
		} while (result <= 0);

		return result;
	}

	private static int estimatedTaskCostUI(Scanner estimatedCost) {
		int result = 0;
		do {
			System.out.println("Please enter a positive number!");
			while (!estimatedCost.hasNextInt()) {
				System.out.println("That's not a number!");
				estimatedCost.next(); // this is important!
			}
			result = estimatedCost.nextInt();
			estimatedCost.nextLine();
		} while (result <= 0);

		return result;
	}
}
