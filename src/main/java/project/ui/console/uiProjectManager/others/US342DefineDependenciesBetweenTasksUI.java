package project.ui.console.uiProjectManager.others;

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

		System.out.println("                  CREATE TASK DEPENDENCY");
		System.out.println("______________________________________________");
		System.out.println();
		US342CreateTaskDependencyController us342Controller = new US342CreateTaskDependencyController(project);
		List<Task> projTaskList = us342Controller.getTasksFromAProject();

		for (int i = 0; i < us342Controller.getTasksFromAProject().size(); i++) {
			System.out
					.println("[" + projTaskList.get(i).getTaskID() + "] " + " " + projTaskList.get(i).getDescription());
			System.out.println();
		}

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
			if (us342Controller.getTaskByID(motherTask).getTaskDeadline() != null) {
				boolean checkD = true;
				while (checkD) {
					System.out.println();
					System.out.println("Choose how many days there are between the start of the \"mother\" task" + "\n"
							+ "and the start of the \"daughter\" task: ");
					System.out.println();

					System.out.println("(Inputing any non-number will exit this menu.)");
					System.out.println();

					System.out
							.println("If you type a negative value, the estimated start date of the daughter task will "
									+ "be set to the same day of the estimated finish date of the task mother");

					if (scannerInput.hasNextInt()) {
						incrementDays = Integer.parseInt(scannerInput.nextLine());
						boolean wasDependencyCreated = us342Controller.createDependenceFromTask(daughterTask,
								motherTask, incrementDays);
						if (!wasDependencyCreated) {
							System.out.println(
									"It wasn't possible to create a Task Dependency. Please, check TaskDependency requirements to create Task Dependency");
							System.out.println();
							this.chooseProject();

						}

						String estStartDateMainTask = us342Controller.getTaskEstimatedStartDateString(motherTask);
						String estStarDateDependentTask = us342Controller.getTaskEstimatedStartDateString(daughterTask);
						String estDeadlineMainTask = us342Controller.getTaskDeadlineString(motherTask);

						System.out.println("The estimated start date of main task is: " + estStartDateMainTask + "\n"
								+ "and the estimated deadline  of main task is: " + estDeadlineMainTask);
						System.out.println();

						System.out.println(
								"The estimated start date of the dependent task is: " + estStarDateDependentTask);
						System.out.println();

						System.out.println("Are you sure you want to create this dependency?");
						System.out.println("Press Y to confirm");
						String choice = scannerInput.nextLine();

						if ("Y".equalsIgnoreCase(choice)) {
							if (wasDependencyCreated) {
								System.out.println("Dependency successfully created.");
								checkD = false;

							} else {
								us342Controller.removeDependenceFromTask(daughterTask, motherTask);

								System.out.println(
										"Task Dependency couldn't be created. Please, check TaskDependency requirements to create Task Dependency");
								System.out.println("The mother task has a Task Deadline");
								System.out.println("The mother task is neither set to state FINISHED or CANCELLED");
								System.out.println("The daughter task wasn't yet initiated");

								checkD = false;

							}

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
				System.out.println(
						"It wasn't possible to create a Task Dependency. Please, check TaskDependency requirements to create Task Dependency");
			}
		}

	}
}
