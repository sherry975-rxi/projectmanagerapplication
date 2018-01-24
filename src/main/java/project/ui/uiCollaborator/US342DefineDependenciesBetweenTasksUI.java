package project.ui.uiCollaborator;

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
		String daughterTask = "";
		String motherTask = "";
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
			System.out.println("Choose a \"daughter\" task by inputing its ID: ");
			daughterTask = scannerInput.nextLine();
			if (us342Controller.projectContainsSelectedTask(daughterTask)) {
				checkA = false;
			} else {
				System.out.println("Invalid number!");
				System.out.println("Try again?");
				System.out.println("Press Y to confirm");
				String choice = scannerInput.nextLine();

				if (!"Y".equalsIgnoreCase(choice)) {
					System.out.println("Task dependency creation cancelled!");
					System.out.println("Exiting menu.");
					checkA = false;
					checkB = false;
					checkC = false;
				}
			}

		}

		while (checkB)

		{
			System.out.println("Choose a \"mother\" task by inputing its ID: ");
			motherTask = scannerInput.nextLine();
			if (us342Controller.projectContainsSelectedTask(motherTask)) {
				checkB = false;
			} else {
				System.out.println("Invalid number!");
				System.out.println("Try again?");
				System.out.println("Press Y to confirm");
				String choice = scannerInput.nextLine();

				if (!"Y".equalsIgnoreCase(choice)) {
					System.out.println("Task dependency creation cancelled!");
					System.out.println("Exiting menu.");
					checkB = false;
					checkC = false;
				}
			}
		}

		if (checkC) {
			if (us342Controller.getTaskByID(motherTask).getEstimatedTaskStartDate() != null) {
				boolean checkD = true;
				while (checkD) {
					System.out.println("Choose how many days there are between the start of the \"mother\" task" + "\n"
							+ "and the start of the \"daughter\" task: ");
					System.out.println("(Inputing any non-number will exit this menu.)");
					if (scannerInput.hasNextInt()) {
						incrementDays = Integer.parseInt(scannerInput.nextLine());
						String estStartDateMainTask = us342Controller.getTaskEstimatedStartDateString(motherTask);
						String estStarDateDependentTask = us342Controller.getTaskEstimatedStartDateString(daughterTask);

						System.out.println("The estimated start date of main task is: " + estStartDateMainTask + "\n"
								+ "and the estimated start date of the dependent task is: " + estStarDateDependentTask);
						System.out.println("Are you sure you want to create this dependency?");
						System.out.println("Press Y to confirm");
						String choice = scannerInput.nextLine();

						if ("Y".equalsIgnoreCase(choice)) {
							us342Controller.createDependenceFromTask(daughterTask, motherTask, incrementDays);
							System.out.println("Dependency successfully created.");
							checkD = false;

						} else {
							System.out.println("Task dependency creation cancelled!");
							System.out.println("Exiting menu.");
							checkD = false;
						}

					} else {
						System.out.println("Invalid number!");
						System.out.println("Try again?");
						System.out.println("Press Y to confirm");
						String choice = scannerInput.nextLine();

						if (!"Y".equalsIgnoreCase(choice)) {
							System.out.println("Task dependency creation cancelled!");
							System.out.println("Exiting menu.");
							checkD = false;
						}
					}
				}
			} else {
				boolean checkD = true;
				while (checkD) {
					System.out.println("Are you sure you want to create this dependency?");
					System.out.println("Press Y to confirm");
					String choice = scannerInput.nextLine();

					if ("Y".equalsIgnoreCase(choice)) {
						us342Controller.createDependenceFromTaskWithoutEstimatedStartDate(daughterTask, motherTask);
						System.out.println("Dependency successfully created.");
						checkD = false;
					} else {
						System.out.println("Task dependency creation cancelled!");
						System.out.println("Exiting menu.");
						checkD = false;
					}
				}
			}
		}

	}
}
