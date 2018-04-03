package project.ui.console.projectmanager.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US347CancelOnGoingTaskController;
import project.model.Project;

import java.util.Scanner;

@Component
public class US347CancelOnGoingTaskUI {

	@Autowired
	private US347CancelOnGoingTaskController controller;

	public void cancelOnGoingTask(String taskID, Project project) {
		controller.setTaskID(taskID);
		controller.setProject(project);
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
