package project.ui.console.uiCollaborator;

import project.controller.US215TotalTimeSpentOnTaskLastMonthController;
import project.model.User;

public class US215TotalTimeSpentOnTaskLastMonthUI {

	User user;

	public void displayTotalTimeSpentOnTasksLastMonth(User user) {

		String myname = user.getName();
		String function = user.getFunction().toUpperCase();

		System.out.println("\n" + myname + " \n" + function);
		System.out.println("___________________________________________________");

		US215TotalTimeSpentOnTaskLastMonthController totalTime = new US215TotalTimeSpentOnTaskLastMonthController();
		Double result = totalTime.getTotalTimeOfFinishedTasksFromUserLastMonth(user);

		System.out.println("\n");
		System.out.println("TOTAL TIME SPENT DOING TASKS LAST MONTH: " + result + " " + "Hours");
		System.out.println("__________________________________________________");
	}

}
