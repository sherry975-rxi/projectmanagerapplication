package project.ui.console.projectmanager.tasks;

import project.controller.US347CancelOnGoingTaskController;
import project.model.Project;

import java.util.Scanner;

public class US347CancelOnGoingTaskUI {

	public void cancelOnGoingTask(String taskID, Project project) {
		US347CancelOnGoingTaskController controller = new US347CancelOnGoingTaskController(taskID, project);
		Scanner dataInput = new Scanner(System.in);

		System.out.println("Are you sure you want to mark this task as cancelled?");
		System.out.println("Press [Y] to confirm, any other key will cancel this process.");
		String input = dataInput.nextLine();

		if ("Y".equalsIgnoreCase(input)) {
			if (controller.cancelOnGoingTask()) {
				System.out.println("Task successfully marked as cancelled.");
			} else {
				System.out.println("The selected task can't be marked as cancelled.");
			}

		} else {
			System.out.println("Process cancelled. Returning to the previous menu.");
		}
	}
}
