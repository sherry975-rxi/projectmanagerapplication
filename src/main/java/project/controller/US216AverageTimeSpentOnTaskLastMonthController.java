package project.controller;

import project.model.Company;
import project.model.ProjectContainer;
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
	ProjectContainer myProjRepo;

	public US216AverageTimeSpentOnTaskLastMonthController() {
		this.myProjRepo = Company.getTheInstance().getProjectsRepository();
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

		return myProjRepo.getAverageTimeOfFinishedTasksFromUserLastMonth(user);
	}

}
