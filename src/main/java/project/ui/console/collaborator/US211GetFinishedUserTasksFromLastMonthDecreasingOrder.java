package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US211GetFinishedUserTasksFromLastMonthInDecreasingOrderController;
import project.model.User;

import java.util.List;
import java.util.Scanner;

@Component
public class US211GetFinishedUserTasksFromLastMonthDecreasingOrder {

	@Autowired
	private US211GetFinishedUserTasksFromLastMonthInDecreasingOrderController viewTasksFinishedLastMonth;


	User user;

	public US211GetFinishedUserTasksFromLastMonthDecreasingOrder() {
		//Empty constructor for JPA purposes
	}

	public void viewLastMonthFinishedTasks() {
		boolean loop = true;
		while (loop) {
			loop = false;
		Scanner scannerInput = new Scanner(System.in);
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

	public void setUser(User user) {
		this.user = user;
	}
}
