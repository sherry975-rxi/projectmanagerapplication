package project.ui.console.uiCollaborator;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import project.controller.US211GetFinishedUserTasksFromLastMonthInDecreasingOrderController;
import project.model.User;
import project.ui.console.MainMenuUI;

public class US211GetFinishedUserTasksFromLastMonthDecreasingOrder {

	User user;
	String date;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public US211GetFinishedUserTasksFromLastMonthDecreasingOrder(User user) {
		this.user = user;
	}

	public void viewLastMonthFinishedTasks() {
		Scanner scannerInput = new Scanner(System.in);
		US211GetFinishedUserTasksFromLastMonthInDecreasingOrderController viewTasksFinishedLastMonth = new US211GetFinishedUserTasksFromLastMonthInDecreasingOrderController();
		List<String> lastMonthFinishedTasks = viewTasksFinishedLastMonth
				.getFinishedUserTasksFromLastMonthInDecreasingOrder(user);
		System.out.println("Finished Tasks by Decreasing Order of:");
		System.out.println(user.getName());
		System.out.println("______________________________________________");
		if (lastMonthFinishedTasks.size() == 0) {
			System.out.println("You completed no Tasks last month!");

		} else {
			System.out.println(lastMonthFinishedTasks);
		}
		System.out.println("______________________________________________");

		System.out.println("[B] Back");
		System.out.println("[M] MainMenu");
		System.out.println("[E] Exit");

		System.out.println("");

		String choice = scannerInput.nextLine().toUpperCase();
		switch (choice) {
		case "B":
			UserTasksFunctionalitiesMenuUI previousMenu = new UserTasksFunctionalitiesMenuUI(user);
			previousMenu.displayFunctionalities();
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
