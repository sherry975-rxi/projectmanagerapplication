package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.services.TaskService;
import project.model.User;

/**
 * @author Group 3
 * 
 *         This class implements the controller that allows a user to get the
 *         total time spent doing tasks
 * 
 *
 */

@Controller
public class US215TotalTimeSpentOnTaskLastMonthController {

	@Autowired
	private TaskService taskService;

	public US215TotalTimeSpentOnTaskLastMonthController() {

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
