package project.controller;

import project.Services.ProjectContainerService;
import project.model.User;

/**
 * @author Group 3
 * 
 *         This class implements the controller that allows a user to get the
 *         and the average time spent by task in the last month.
 * 
 *
 */

public class US216AverageTimeSpentOnTaskLastMonthController {
	ProjectContainerService projectContainer;

	public US216AverageTimeSpentOnTaskLastMonthController() {
		this.projectContainer = new ProjectContainerService();
		projectContainer.updateProjectContainer();
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

		return projectContainer.getAverageTimeOfFinishedTasksFromUserLastMonth(user);
	}

}
