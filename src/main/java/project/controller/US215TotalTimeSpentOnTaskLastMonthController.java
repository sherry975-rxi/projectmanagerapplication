package project.controller;

import project.Services.ProjectService;
import project.model.User;

/**
 * @author Group 3
 * 
 *         This class implements the controller that allows a user to get the
 *         total time spent doing tasks
 * 
 *
 */
public class US215TotalTimeSpentOnTaskLastMonthController {

	ProjectService projectContainer;

	public US215TotalTimeSpentOnTaskLastMonthController() {
		this.projectContainer = new ProjectService();
		projectContainer.updateProjectContainer();
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

		return projectContainer.getTotalTimeOfFinishedTasksFromUserLastMonth(user);
	}

}
