package project.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import project.controller.TasksFiltersController;
import project.model.Task;
import project.model.User;

public class US211GetFinishedUserTasksFromLastMonthDecreasingOrder {

	User user;
	String date;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public US211GetFinishedUserTasksFromLastMonthDecreasingOrder(User user) {
		this.user = user;
	}

	public void viewLastMonthFinishedTasks() {
		TasksFiltersController viewTasksFinishedLastMonth = new TasksFiltersController();
		List<Task> lastMonthFinishedTasks = viewTasksFinishedLastMonth
				.getFinishedUserTasksFromLastMonthInDecreasingOrder(user);

		if (lastMonthFinishedTasks.size() == 0)
			System.out.println("You completed no Tasks last month!");

		else {
			System.out.println("Last month, you completed the following Tasks:");

			for (Task other : lastMonthFinishedTasks) {
				Calendar finishDate = other.getFinishDate();
				date = sdf.format(finishDate.getTime());
				System.out.println(date + " - " + other.getDescription());
			}
		}
		System.out.println("");

	}
}
