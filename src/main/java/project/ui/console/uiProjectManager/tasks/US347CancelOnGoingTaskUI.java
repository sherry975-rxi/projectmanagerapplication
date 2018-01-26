package project.ui.console.uiProjectManager.tasks;

import java.util.Scanner;

import project.controller.US347CancelOnGoingTaskController;
import project.model.Task;

public class US347CancelOnGoingTaskUI {

	public void cancelOnGoingTask(Task task) {
		US347CancelOnGoingTaskController controller = new US347CancelOnGoingTaskController(task);
		Scanner dataInput = new Scanner(System.in);

		System.out.println("Are you sure you want to mark this task as finished?");
		System.out.println("Press [Y] to confirm, any other key will cancel this process.");
		String input = dataInput.nextLine();
		if ("Y".equalsIgnoreCase(input)) {
			if (controller.cancelOnGoingTask()) {
				System.out.println("Task successfully marked as finished.");

			} else {
				System.out.println("The selected task can't be marked as finished.");
			}

		} else {
			System.out.println("Process cancelled. Returning to the previous menu.");
		}
	}
}
