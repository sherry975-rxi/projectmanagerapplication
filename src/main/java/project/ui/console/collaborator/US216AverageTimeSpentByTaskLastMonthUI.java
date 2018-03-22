package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Component;
import project.controller.US216AverageTimeSpentOnTaskLastMonthController;
import project.model.User;

@Component
public class US216AverageTimeSpentByTaskLastMonthUI {
	@Autowired
	private US216AverageTimeSpentOnTaskLastMonthController averageTime;

	public void displayAveregeTimeSpentByTaskLastMonth(User user) {

		String myname = user.getName();
		String function = user.getFunction().toUpperCase();

		System.out.println("\n" + myname + " \n" + function);
		System.out.println("___________________________________________________");
		Double average = averageTime.getAverageTimeOfFinishedTasksFromUserLastMonth(user);

		System.out.println("\n");
		System.out.println("AVERAGE TIME SPENT BY TASK: " + average + " " + "Hours");
		System.out.println("__________________________________________________");

	}
}
