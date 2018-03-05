package project.controller;

import project.model.*;

public class US207and208CreateUpdateTaskReportControllers {

	private User username;
	private UserRepository userRepository;
	private Company company;
	private ProjectRepository projectRepository;
	private String email;
	private Task task;

	/**
	 * Constructor of US207CreateTaskReportController
	 * 
	 * @param email
	 *            The email of the user that will create a task report
	 */
	public US207and208CreateUpdateTaskReportControllers(String email, String taskID) {
		this.company = Company.getTheInstance();
		this.projectRepository = company.getProjectsRepository();
		this.userRepository = company.getUsersRepository();
		this.username = userRepository.getUserByEmail(email);
		this.email = email;
		for (Task other : projectRepository.getUserTasks(username)) {
			if (other.getTaskID().equals(taskID)) {
				task = other;
			}
		}

	}

	/**
	 * This controller creates or updates a Report with a given time
	 * 
	 * @param newTime
	 *            The time associated to the report
	 * 
	 * @return TRUE if the report is created, FALSE if not
	 */
	public boolean createReportController(int newTime) {

		boolean wasReportCreated = false;

		if (!task.doesTaskHaveReportByGivenUser(email)) {

			task.createReport(task.getTaskCollaboratorByEmail(email));

			wasReportCreated = task.changeReportedTime(newTime, username.getEmail());
		} else {
			task.changeReportedTime(newTime, username.getEmail());
			wasReportCreated = task.changeReportedTime(newTime, username.getEmail());

		}
		return wasReportCreated;

	}

	/**
	 * @param email
	 *            The email of the user to search his associated tasks ID
	 * @param taskIndex
	 *            The task to look for it's report
	 * @return The reported time by a given TaskCollaborator
	 */
	public int getReportedTimeByCollaborator() {
		int reportedTimeByCollaborator = 0;

		reportedTimeByCollaborator = task.getReportedTimeByTaskCollaborator(email);

		return reportedTimeByCollaborator;
	}

	/**
	 * @param email
	 *            The email of the user to search his name
	 * @param taskIndex
	 *            The task to look for it's report
	 * @return The reporter Name
	 */
	public String getReportedCollaboratorName() {

		return  task.getReporterName(email);
	}

}
