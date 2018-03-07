package project.controller;

import project.model.Company;
import project.model.ProjectContainer;
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

	ProjectContainer myProjRepo;

	public US215TotalTimeSpentOnTaskLastMonthController() {
		this.myProjRepo = Company.getTheInstance().getProjectsRepository();
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

		return myProjRepo.getTotalTimeOfFinishedTasksFromUserLastMonth(user);
	}

}
