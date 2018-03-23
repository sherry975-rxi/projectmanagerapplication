package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.model.User;
import project.services.TaskService;

/**
 * @author Group 3
 * 
 *         This class implements the controller that allows a user to get the
 *         and the average time spent by task in the last month.
 * 
 *
 */

@Controller
public class US216AverageTimeSpentOnTaskLastMonthController {

	@Autowired
	TaskService taskService;

	public US216AverageTimeSpentOnTaskLastMonthController() {
		//Empty constructor created for JPA integration tests

	}

	/**
	 * This method gets the average time spent by a user in all tasks in the last
	 * month.
	 * 
	 * @param user
	 *            User
	 * 
	 * @return Returns the average time spent by the user in all tasks in the last
	 *         month.
	 */
	public double getAverageTimeOfFinishedTasksFromUserLastMonth(User user) {

		return taskService.getAverageTimeOfFinishedTasksFromUserLastMonth(user);
	}

}
