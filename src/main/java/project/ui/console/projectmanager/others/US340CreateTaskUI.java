package project.ui.console.projectmanager.others;

import java.util.Scanner;

import project.controller.PrintProjectInfoController;
import project.controller.US340CreateTaskController;
import project.model.Project;
import project.model.User;
import project.ui.console.MainMenuUI;

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
	private User user;

	public US340CreateTaskUI(User user, Project project) {
		this.user = user;
		this.project = project;
	}

	public void createTask() {

		String line = "___________________________________________________";
		Scanner scannerInput = new Scanner(System.in);

		PrintProjectInfoController projectInfo = new PrintProjectInfoController(this.project);

		System.out.println("");
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

		if (yerOrNo.equalsIgnoreCase("y")) {
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
		int number;
		do {
			System.out.println("Please enter a positive number!");
			while (!estimatedTaskEffort.hasNextInt()) {
				System.out.println("That's not a number!");
				estimatedTaskEffort.next(); // this is important!
			}
			number = estimatedTaskEffort.nextInt();
			estimatedTaskEffort.nextLine();
		} while (number <= 0);
		System.out.println("Thank you! Got " + number);

		System.out.println("Enter estimated cost: ");
		Scanner estimatedCost = new Scanner(System.in);
		int cost;
		do {
			System.out.println("Please enter a positive number!");
			while (!estimatedCost.hasNextInt()) {
				System.out.println("That's not a number!");
				estimatedCost.next(); // this is important!
			}
			cost = estimatedCost.nextInt();
			estimatedCost.nextLine();
		} while (cost <= 0);
		System.out.println("Thank you! Got " + cost);

		System.out.println();
		System.out.println("You added the following data for the task: ");
		System.out.println("             " + "[1] Task description: " + description);
		System.out.println("             " + "[2] Estimated task effort: " + number);
		// System.out.println(" " + "[3] Estimated task start date: " +
		// estimatedTaskStartDate);
		// System.out.println(" " + "[4] Task deadline: " + taskDeadline);
		System.out.println("             " + "[5] Estimated Cost: " + cost);

		System.out.println();
		System.out.println("Is your data correct? (y to confirm; n to deny)");

		String confirm = scannerInput.nextLine();

		if ("y".equalsIgnoreCase(confirm)) {
			createTaskController.addTask(description, number, cost);
			System.out.println();
			System.out.println("Task was successfully added to this project.");
			System.out.println();

		} else { // In case user choose "n".
			System.out.println();
			System.out.println("Creation of task cancelled.");
			System.out.println();

		}

		System.out.println(line);
		System.out.println("[M] MainMenu");
		System.out.println("[E] Exit \n");

		String option = scannerInput.nextLine().toUpperCase();

		if (option.equals("M")) {
			MainMenuUI.mainMenu();
		} else if (option.equals("E")) {
			System.exit(0);

		}
	}
}