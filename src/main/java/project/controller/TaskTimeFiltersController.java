/**
 * 
 */
package project.controller;

import project.model.Company;
import project.model.User;

/**
 * @author Group 3
 * 
 *         This class implements the controller that allows a user to get the
 *         total time spent doing tasks and the average time spent by task in
 *         the last month in the last month
 * 
 *
 */
public class TaskTimeFiltersController {

	Company myCompany;

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

		if (myCompany.getProjectsRepository().getUserTasks(user) != null) {

			return myCompany.getProjectsRepository().getTotalTimeOfFinishedTasksFromUserLastMonth(user);

		}

		else

			return 0;
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

		if (myCompany.getProjectsRepository().getUserTasks(user) != null) {

			return myCompany.getProjectsRepository().getAverageTimeOfFinishedTasksFromUserLastMonth(user);

		}

		else

			return 0;
	}

}
