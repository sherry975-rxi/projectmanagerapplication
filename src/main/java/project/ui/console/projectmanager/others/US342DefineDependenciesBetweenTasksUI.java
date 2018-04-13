package project.ui.console.projectmanager.others;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US342CreateTaskDependencyController;
import project.model.Project;
import project.model.Task;

import java.util.List;
import java.util.Scanner;

/**
 * @author group3
 *
 */
@Component
public class US342DefineDependenciesBetweenTasksUI {

	@Autowired
	private US342CreateTaskDependencyController us342Controller;

	private Project project;


	public US342DefineDependenciesBetweenTasksUI() {
		//Empty constructor for JPA purposes
	}

    public void setProject(Project project) {
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
		String invalidNumber = "Invalid number!";
		String tryAgain = "Try again?";
		String pressYToConfirm = "Press Y to confirm";
		String creationCancelled = "Task dependency creation cancelled!";
		String exitMenu = "Exiting menu.";

		Scanner scannerInput = new Scanner(System.in);

		System.out.println("                  CREATE TASK DEPENDENCY");
		System.out.println("______________________________________________");
		System.out.println();

		us342Controller.setProject(project);

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
				System.out.println(invalidNumber);
				System.out.println(tryAgain);
				System.out.println(pressYToConfirm);
				String choice = scannerInput.nextLine();

				if (!"Y".equalsIgnoreCase(choice)) {
					System.out.println(creationCancelled);
					System.out.println(exitMenu);
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
				System.out.println(invalidNumber);
				System.out.println(tryAgain);
				System.out.println(pressYToConfirm);
				String choice = scannerInput.nextLine();

				if (!"Y".equalsIgnoreCase(choice)) {
					System.out.println(creationCancelled);
					System.out.println(exitMenu);
					checkB = false;
					checkC = false;
				}
			}
		}

		if (checkC) {
			incrementDaysInputing(scannerInput, us342Controller, daughterTask, motherTask);
		}

	}

	private static void incrementDaysInputing(Scanner scannerInput, US342CreateTaskDependencyController us342Controller,
			String daughterTask, String motherTask) {
		if (us342Controller.getTaskByID(motherTask).getTaskDeadline() != null) {
			boolean checkD = true;
			while (checkD) {
				System.out.println();
				System.out.println("Choose how many days there are between the start of the \"mother\" task" + "\n"
						+ "and the start of the \"daughter\" task: ");
				System.out.println();

				System.out.println("(Inputing any non-number will exit this menu.)");
				System.out.println();

				System.out.println("If you type a negative value, the estimated start date of the daughter task will "
						+ "be set to the same day of the estimated finish date of the task mother");

				incrementDaysChoosing(scannerInput, us342Controller, daughterTask, motherTask);
			}
		} else {
			System.out.println(
					"It wasn't possible to create a Task Dependency. Please, check TaskDependency requirements to create Task Dependency");
			System.out.println();
			System.out.println("The mother task has a Task Deadline");
			System.out.println("The mother task is neither set to state FINISHED or CANCELLED");
			System.out.println("The daughter task wasn't yet initiated");
		}

	}

	private static boolean incrementDaysChoosing(Scanner scannerInput, US342CreateTaskDependencyController us342Controller,
			String daughterTask, String motherTask) {

		boolean checkE = false;
		if (scannerInput.hasNextInt()) {
			checkE = validIncrementDaysChoosing(scannerInput, us342Controller, daughterTask, motherTask);

		} else {
			System.out.println("Invalid number!");
			System.out.println("Try again?");
			System.out.println("Press Y to confirm");
			String choice = scannerInput.nextLine();

			if (!"Y".equalsIgnoreCase(choice)) {
				System.out.println("Task dependency creation cancelled!");
				System.out.println("Exiting menu.");
				checkE = false;
			}
		}
		return checkE;
	}

	private static boolean validIncrementDaysChoosing(Scanner scannerInput,
			US342CreateTaskDependencyController us342Controller, String daughterTask, String motherTask) {
		boolean result = true;
		int incrementDaysInput = Integer.parseInt(scannerInput.nextLine());
		boolean wasDependencyCreated = us342Controller.createDependenceFromTask(daughterTask, motherTask,
				incrementDaysInput);
		if (!wasDependencyCreated) {
			System.out.println(
					"It wasn't possible to create a Task Dependency. Please, check TaskDependency requirements to create Task Dependency");
			System.out.println();
			System.out.println("The mother task has a Task Deadline");
			System.out.println("The mother task is neither set to state FINISHED or CANCELLED");
			System.out.println("The daughter task wasn't yet initiated");
			result = false;
		} else {

			String estStartDateMainTask = us342Controller.getTaskEstimatedStartDateString(motherTask);
			String estStarDateDependentTask = us342Controller.getTaskEstimatedStartDateString(daughterTask);
			String estDeadlineMainTask = us342Controller.getTaskDeadlineString(motherTask);

			System.out.println("The estimated start date of main task is: " + estStartDateMainTask + "\n"
					+ "and the estimated deadline  of main task is: " + estDeadlineMainTask);
			System.out.println();

			System.out.println("The estimated start date of the dependent task is: " + estStarDateDependentTask);
			System.out.println();

			System.out.println("Are you sure you want to create this dependency?");
			System.out.println("Press Y to confirm");
			String choice = scannerInput.nextLine();

			if ("Y".equalsIgnoreCase(choice)) {
				System.out.println("Dependency successfully created.");

			} else {
				System.out.println("Task dependency creation cancelled!");
				System.out.println("Exiting menu.");
				result = false;
			}
		}
		return result;
	}
}
