package project.ui.console.uiCollaborator;


import java.util.Scanner;

import project.controller.US210GetAllFinishedUserTasksInDecreasingOrderController;
import project.model.User;
import project.ui.console.MainMenuUI;

public class US210GetAllFinishedUserTasksInDecreasingOrderUI {
	User currentUser;

	
	public US210GetAllFinishedUserTasksInDecreasingOrderUI (User user) {
		this.currentUser = user;
	}
	
	public void getAllFinishedUserTasksInDecreasingOrderUI () {
		
		Scanner scannerInput = new Scanner(System.in);
		US210GetAllFinishedUserTasksInDecreasingOrderController userTasks = new US210GetAllFinishedUserTasksInDecreasingOrderController(this.currentUser);
		System.out.println();
		System.out.println("Finished Tasks by Decreasing Order of:");
		System.out.println(userTasks.printUserNameInfo());
		System.out.println("______________________________________________");
		
		for (int i = 0; i < userTasks.getAllFinishedUserTasksInDecreasingOrder().size(); i++) {
			System.out.println(userTasks.getAllFinishedUserTasksInDecreasingOrder().get(i));
			System.out.println();
		}
		System.out.println("______________________________________________");

		System.out.println("[B] Back");
		System.out.println("[M] MainMenu");
		System.out.println("[E] Exit");
		
		
		String choice = scannerInput.nextLine().toUpperCase();
		switch (choice) {
		case "B":
			CollectProjectsFromUserUI previousMenu = new CollectProjectsFromUserUI(this.currentUser);
			previousMenu.collectProjectsFromUser();
			break;
		case "M":
			MainMenuUI.mainMenu();
			break;
		case "E":
			System.out.println("----YOU HAVE EXIT FROM APPLICATION----");
			System.exit(0);
			break;
		
		}
	}
	
}
