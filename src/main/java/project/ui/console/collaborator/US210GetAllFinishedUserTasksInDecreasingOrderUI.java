package project.ui.console.collaborator;

import project.controller.US210GetAllFinishedUserTasksInDecreasingOrderController;
import project.controller.UpdateDbToContainersController;
import project.model.User;
import project.ui.console.MainMenuUI;

import java.util.Scanner;

public class US210GetAllFinishedUserTasksInDecreasingOrderUI {
	User currentUser;

	public US210GetAllFinishedUserTasksInDecreasingOrderUI(User user) {
		this.currentUser = user;
	}

	public void getAllFinishedUserTasksInDecreasingOrderUI() {
		boolean loop = true;
		while (loop) {
			loop = false;
		UpdateDbToContainersController infoUpdater = new UpdateDbToContainersController();
		infoUpdater.updateDBtoContainer();

		Scanner scannerInput = new Scanner(System.in);
		US210GetAllFinishedUserTasksInDecreasingOrderController userTasks = new US210GetAllFinishedUserTasksInDecreasingOrderController(
				this.currentUser);
		System.out.println();
		System.out.println("Finished Tasks by Decreasing Order of:");
		System.out.println(userTasks.printUserNameInfo());
		System.out.println("______________________________________________");

		for (int i = 0; i < userTasks.getAllFinishedUserTasksInDecreasingOrder().size(); i++) {
			System.out.println(userTasks.getAllFinishedUserTasksInDecreasingOrder().get(i));
			System.out.println();
		}
		System.out.println("______________________________________________");

		System.out.println("[B] Back \n");
		

		String choice = scannerInput.nextLine().toUpperCase();
		switch (choice) {
		case "B":
			break;
		default:
			System.out.println("Invalid input. Please retry:");
			loop = true;

		}
		}
	}

}
