package project.ui.console.uiCollaborator;

import java.text.SimpleDateFormat;
import java.util.List;

import project.controller.US211GetFinishedUserTasksFromLastMonthInDecreasingOrderController;
import project.model.User;

public class US211GetFinishedUserTasksFromLastMonthDecreasingOrder {

	User user;
	String date;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public US211GetFinishedUserTasksFromLastMonthDecreasingOrder(User user) {
		this.user = user;
	}

	public void viewLastMonthFinishedTasks() {
		US211GetFinishedUserTasksFromLastMonthInDecreasingOrderController viewTasksFinishedLastMonth = new US211GetFinishedUserTasksFromLastMonthInDecreasingOrderController();
		List<String> lastMonthFinishedTasks = viewTasksFinishedLastMonth
				.getFinishedUserTasksFromLastMonthInDecreasingOrder(user);

		if (lastMonthFinishedTasks.size() == 0)
			System.out.println("You completed no Tasks last month!");

		else {
			System.out.println("Last month, you completed the following Tasks:");

			System.out.println(lastMonthFinishedTasks);
		}
		System.out.println("");

	}
}
