package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controller.US215TotalTimeSpentOnTaskLastMonthController;
import project.model.User;

@Component
public class US215TotalTimeSpentOnTaskLastMonthUI {

	User user;

	@Autowired
	US215TotalTimeSpentOnTaskLastMonthController totalTime;

	public void displayTotalTimeSpentOnTasksLastMonth(User user) {

		String myname = user.getName();
		String function = user.getFunction().toUpperCase();

		System.out.println("\n" + myname + " \n" + function);
		System.out.println("___________________________________________________");

		Double result = totalTime.getTotalTimeOfFinishedTasksFromUserLastMonth(user);

		System.out.println("\n");
		System.out.println("TOTAL TIME SPENT DOING TASKS LAST MONTH: " + result + " " + "Hours");
		System.out.println("__________________________________________________");
	}

}
