package project.ui.console.collaborator;

import project.controller.US211GetFinishedUserTasksFromLastMonthInDecreasingOrderController;
import project.model.User;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class US211GetFinishedUserTasksFromLastMonthDecreasingOrder {

	User user;
	String date;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public US211GetFinishedUserTasksFromLastMonthDecreasingOrder(User user) {
		this.user = user;
	}

	public void viewLastMonthFinishedTasks() {
		boolean loop = true;
		while (loop) {
			loop = false;
		Scanner scannerInput = new Scanner(System.in);
		US211GetFinishedUserTasksFromLastMonthInDecreasingOrderController viewTasksFinishedLastMonth = new US211GetFinishedUserTasksFromLastMonthInDecreasingOrderController();
		List<String> lastMonthFinishedTasks = viewTasksFinishedLastMonth
				.getFinishedUserTasksFromLastMonthInDecreasingOrder(user);
		System.out.println("Finished Tasks by Decreasing Order of:");
		System.out.println(user.getName());
		System.out.println("______________________________________________");
		if (lastMonthFinishedTasks.isEmpty()) {
			System.out.println("You completed no Tasks last month!");

		} else {
			System.out.println(lastMonthFinishedTasks);
		}
		System.out.println("______________________________________________");

		System.out.println("[B] Back \n");

		System.out.println("");


			String choice = scannerInput.nextLine().toUpperCase();
			switch (choice) {
			case "B":
				break;
			default:
				System.out.println("Invalid input. Please retry:");
				loop = true;
				break;
			}

	}
	}
}
