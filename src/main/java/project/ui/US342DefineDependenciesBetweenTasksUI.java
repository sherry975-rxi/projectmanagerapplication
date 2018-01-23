package project.ui;

import java.util.List;
import java.util.Scanner;

import project.controller.US342CreateTaskDependencyController;
import project.model.Project;
import project.model.Task;

/**
 * @author group3
 *
 */
public class US342DefineDependenciesBetweenTasksUI {

	private Project project;

	/**
	 * @param user
	 */
	public US342DefineDependenciesBetweenTasksUI(Project project) {
		this.project = project;
	}

	/**
	 * 
	 */
	public void chooseProject() {

		boolean checkA = true;
		boolean checkB = true;
		boolean checkC = true;
		int daughterTask = 0;
		int motherTask = 0;
		int incrementDays = 0;

		Scanner scannerInput = new Scanner(System.in);
		US342CreateTaskDependencyController us342Controller = new US342CreateTaskDependencyController(project);
		List<Task> projTaskList = us342Controller.getTasksFromAProject();

		for (int i = 0; i < us342Controller.getTasksFromAProject().size(); i++) {
			System.out.println("[" + i + 1 + "] " + " " + projTaskList.get(i).getTaskID() + " "
					+ projTaskList.get(i).getDescription());
			System.out.println();
		}
		System.out.println("___________________________________________________");
		System.out.println();

		while (checkA) {
			System.out.println("Choose a \"daughter\" task: ");
			System.out.println("(Inputing any non-number will exit this menu.)");
			if (scannerInput.hasNextInt()) {
				daughterTask = Integer.parseInt(scannerInput.nextLine());
				if (daughterTask <= projTaskList.size()) {
					checkA = false;
				} else {
					System.out.println("Invalid number!");
				}
			} else {
				System.out.println("Not a number. Exiting menu.");
				checkB = false;
				checkC = false;
			}

			if (projTaskList.get(motherTask - 1).getEstimatedTaskStartDate() == null) {
				checkC = false;
			}
		}

		while (checkB) {
			System.out.println("Choose a \"mother\" task: ");
			System.out.println("(Inputing any non-number will exit this menu.)");
			if (scannerInput.hasNextInt()) {
				motherTask = Integer.parseInt(scannerInput.nextLine());
				if (motherTask == daughterTask) {
					System.out.println("You chose the same task in both cases.");
					System.out.println("Impossible dependency!");
				} else if (motherTask <= projTaskList.size()) {
					checkA = false;
				} else {
					System.out.println("Invalid number!");
				}
			} else {
				System.out.println("Not a number. Exiting menu.");
				checkC = false;
			}
		}

		if (checkC) {
			boolean checkD = true;
			while (checkD) {
				System.out.println("Choose how many days there are between the start of the \"mother\" task" + "\n"
						+ "and the start of the \"daughter\" task: ");
				System.out.println("(Inputing any non-number will exit this menu.)");
				if (scannerInput.hasNextInt()) {
					incrementDays = Integer.parseInt(scannerInput.nextLine());
					String estStartDateMainTask = us342Controller
							.getTaskEstimatedStartDateString(projTaskList.get(motherTask - 1).getTaskID());
					String estStarDateDependentTask = us342Controller
							.getTaskEstimatedStartDateString(projTaskList.get(daughterTask - 1).getTaskID());

					System.out.println("The estimated start date of main task is: " + estStartDateMainTask + " "
							+ "and the estimated start date of the dependent task is: " + estStarDateDependentTask);
					System.out.println("Are you sure you want to create this dependency?");
					System.out.println("Press Y to confirm");
					String choice = scannerInput.nextLine();

					if ("Y".equalsIgnoreCase(choice)) {
						us342Controller.createDependenceFromTask(projTaskList.get(daughterTask - 1).getTaskID(),
								projTaskList.get(motherTask - 1).getTaskID(), incrementDays);
						System.out.println("Dependency successfully created.");

					} else {
						System.out.println("Task dependency creation cancelled!");
						System.out.println("Exiting menu.");
					}

				} else {
					System.out.println("Not a number. Exiting menu.");
					checkD = false;
				}
			}
		} else {
			System.out.println("Are you sure you want to create this dependency?");
			System.out.println("Press Y to confirm");
			String choice = scannerInput.nextLine();

			if ("Y".equalsIgnoreCase(choice)) {
				us342Controller.createDependenceFromTaskWithoutEstimatedStartDate(
						projTaskList.get(daughterTask - 1).getTaskID(), projTaskList.get(motherTask - 1).getTaskID());
				System.out.println("Dependency successfully created.");

			} else {
				System.out.println("Task dependency creation cancelled!");
				System.out.println("Exiting menu.");
			}
		}

	}
}
