package project.ui.console.projectmanager.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US365MarkTaskAsFinishedControllerProjectManager;
import project.model.Project;

import java.util.Scanner;

@Component
public class US365MarkTaskAsFinishedUI {

	 @Autowired
	private US365MarkTaskAsFinishedControllerProjectManager controller;

	public void markTaskAsFinished(String taskID, Project selectedProject) {

		controller.setTaskToBeMarked(taskID);
		controller.setSelectedProject(selectedProject);

		Scanner input = new Scanner(System.in);
		System.out.println("Would you really like to mark this task as completed?");
		System.out.println("(Press [Y] to confirm, anything else to exit");
		System.out.println("");

		String command = input.nextLine().toUpperCase();

		if ("Y".equals(command)) {
			if (controller.setTaskAsFinished()) {
				System.out.println("Task " + taskID + " has been finished!");
			} else {
				System.out.println("You cannot finish this task yet");
			}
		} else {
			System.out.println("Task completion aborted.");
		}

		System.out.println("");
		System.out.println("Returning...");
		System.out.println("");

	}

}
