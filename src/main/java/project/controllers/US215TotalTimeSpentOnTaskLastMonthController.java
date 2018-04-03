package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.model.User;
import project.services.TaskService;

/**
 * @author Group 3
 * 
 *         This class implements the controllers that allows a user to get the
 *         total time spent doing tasks
 * 
 *
 */

@Controller
public class US215TotalTimeSpentOnTaskLastMonthController {

	@Autowired
	private TaskService taskService;

	public US215TotalTimeSpentOnTaskLastMonthController() {
		//Empty constructor created for JPA integration tests

	}

	/**
	 * This method gets the total time spent by a user in all tasks in the last
	 * month.
	 * 
	 * @param user
	 *            User
	 * 
	 * @return Returns the total time spent by the user in all tasks in the last
	 *         month.
	 */
	public double getTotalTimeOfFinishedTasksFromUserLastMonth(User user) {

		return taskService.getTotalTimeOfFinishedTasksFromUserLastMonth(user);
	}

}
