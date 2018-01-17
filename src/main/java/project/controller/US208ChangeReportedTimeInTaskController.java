package project.controller;

import project.model.Company;
import project.model.User;

public class US208ChangeReportedTimeInTaskController {
	private int reportIndex;
	private int newTime;
	private int taskIndex;
	private User username;

	/**
	 * This method is used to allow a user to change a reported time in a task.
	 * 
	 * @param taskIndex
	 *            int used to select a task from list of tasks
	 * @param newTime
	 *            int used to set a new time of a task report
	 * @param reportIndex
	 *            int used to selec a report from a lst of reports
	 * @param email
	 *            string defining an email that will be used to retrieve the
	 *            respective user
	 * @return returns a boolean to confirm if the operation was sucessfull
	 */
	public boolean correctReportedTimeInTaskController(int taskIndex, int newTime, int reportIndex, String email) {
		boolean taskCorrected = false;
		this.reportIndex = reportIndex;
		this.newTime = newTime;
		this.taskIndex = taskIndex;
		this.username = Company.getTheInstance().getUsersRepository().getUserByEmail(email);
		if (Company.getTheInstance().getUsersRepository().isUserinUserRepository(username)) {
			Company.getTheInstance().getProjectsRepository().getUserTasks(username).get(taskIndex)
					.changeReportedTime(newTime, reportIndex);
			taskCorrected = true;
		}
		return taskCorrected;

	}

}
