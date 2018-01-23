package project.controller;

import project.model.Company;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.User;
import project.model.UserRepository;

public class US207CreateTaskReportController {

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
	public US207CreateTaskReportController(String email, String taskID) {
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

	public boolean createReportController(int newTime) {

		boolean wasReportCreated = false;

		if (!task.doesTaskHaveReportByGivenUser(email)) {

			task.createReport(task.getTaskCollaboratorByEmail(email));

			wasReportCreated = task.changeReportedTime(newTime, username.getEmail());
		} else if (task.doesTaskHaveReportByGivenUser(email)) {

			wasReportCreated = task.changeReportedTime(newTime, username.getEmail());

		} else {
			wasReportCreated = false;
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
		String reporterName = new String();

		reporterName = task.getReporterName(email);

		return reporterName;
	}

}
