package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controller.US210GetAllFinishedUserTasksInDecreasingOrderController;
import project.model.User;

import java.util.Scanner;

@Component
public class US210GetAllFinishedUserTasksInDecreasingOrderUI {
	User currentUser;
	@Autowired
	US210GetAllFinishedUserTasksInDecreasingOrderController userTasks;

	public US210GetAllFinishedUserTasksInDecreasingOrderUI(User user) {
		this.currentUser = user;
	}

	public void getAllFinishedUserTasksInDecreasingOrderUI() {
		boolean loop = true;
		while (loop) {
			loop = false;
		Scanner scannerInput = new Scanner(System.in);

		userTasks.setUser(this.currentUser);

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

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
