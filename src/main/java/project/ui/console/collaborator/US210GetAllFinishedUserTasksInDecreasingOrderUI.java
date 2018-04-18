package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US210GetAllFinishedUserTasksInDecreasingOrderController;
import project.model.User;

import java.util.Scanner;

@Component
public class US210GetAllFinishedUserTasksInDecreasingOrderUI {

	@Autowired
	US210GetAllFinishedUserTasksInDecreasingOrderController userTasks;

	User currentUser;


	public US210GetAllFinishedUserTasksInDecreasingOrderUI() {
		//Empty constructor for JPA purposes
	}

	public void getAllFinishedUserTasksInDecreasingOrderUI() {
		while (true) {
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
		if(choice != "B") {
			break;
		}
		else{
			System.out.println("Invalid input. Please retry:");

		}
		}
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
