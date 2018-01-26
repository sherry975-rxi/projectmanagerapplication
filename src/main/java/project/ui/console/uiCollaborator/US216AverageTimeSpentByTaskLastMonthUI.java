package project.ui.console.uiCollaborator;

import project.controller.US216AverageTimeSpentOnTaskLastMonthController;
import project.model.User;

public class US216AverageTimeSpentByTaskLastMonthUI {

	public void displayAveregeTimeSpentByTaskLastMonth(User user) {

		String myname = user.getName();
		String function = user.getFunction().toUpperCase();

		System.out.println("\n" + myname + " \n" + function);
		System.out.println("___________________________________________________");

		US216AverageTimeSpentOnTaskLastMonthController averageTime = new US216AverageTimeSpentOnTaskLastMonthController();
		Double average = averageTime.getAverageTimeOfFinishedTasksFromUserLastMonth(user);

		System.out.println("\n");
		System.out.println("AVERAGE TIME SPENT BY TASK: " + average + " " + "Hours");
		System.out.println("__________________________________________________");

	}
}
