package project.ui.console.projectmanager.tasks;

import project.controller.US365MarkTaskAsFinishedControllerProjectManager;
import project.controller.UpdateDbToContainersController;
import project.model.Project;

import java.util.Scanner;

public class US365MarkTaskAsFinishedUI {

	US365MarkTaskAsFinishedControllerProjectManager controller;

	public void markTaskAsFinished(String taskID, Project selectedProject) {
		UpdateDbToContainersController infoUpdater = new UpdateDbToContainersController();
		infoUpdater.updateDBtoContainer();

		controller = new US365MarkTaskAsFinishedControllerProjectManager(taskID, selectedProject);



			Scanner input = new Scanner(System.in);
			System.out.println("Would you really like to mark this task as completed?");
			System.out.println("(Press [Y] to confirm, anything else to exit");
			System.out.println("");

			String command = input.nextLine().toUpperCase();

			if ("Y".equals(command)) {
				if(controller.setTaskAsFinished()){
					System.out.println("Task " + taskID + " has been finished!");
				}else{
					System.out.println("You cannot finish this task yet");
				}
			} else {
				System.out.println("Task completion aborted.");
			}





		System.out.println("");
		System.out.println("Returning to main menu...");
		System.out.println("");

	}

}
