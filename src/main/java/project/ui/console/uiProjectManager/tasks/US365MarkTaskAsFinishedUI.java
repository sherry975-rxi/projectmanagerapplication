package project.ui.console.uiProjectManager.tasks;

import java.util.Scanner;

import project.controller.US365MarkTaskAsFinishedControllerProjectManager;
import project.model.Project;

public class US365MarkTaskAsFinishedUI {

	US365MarkTaskAsFinishedControllerProjectManager controller;

	public void markTaskAsFinished(String taskID, Project selectedProject) {

		controller = new US365MarkTaskAsFinishedControllerProjectManager(taskID, selectedProject);

		if (controller.canTaskFinish()) {

			Scanner input = new Scanner(System.in);
			System.out.println("Would you really like to mark this task as completed?");
			System.out.println("(Press [Y] to confirm, anything else to exit");
			System.out.println("");

			String command = input.nextLine().toUpperCase();

			if ("Y".equals(command)) {
				controller.setTaskAsFinished();
				System.out.println("Task " + taskID + " has been finished!");
			} else {
				System.out.println("Task completion aborted.");
			}

		} else {
			System.out.println("You cannot finish this task yet");
		}

		System.out.println("");
		System.out.println("Returning to main menu...");
		System.out.println("");

	}

}
