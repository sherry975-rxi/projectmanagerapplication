package project.controller;

import project.model.*;

import java.time.LocalDate;

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
	 * @param timeToReport
	 *            The time associated to the report
	 * 
	 * @return TRUE if the report is created, FALSE if not
	 */
	public boolean createReportController(double timeToReport) {

		boolean wasReportCreated = false;

		wasReportCreated = task.createReport(task.getTaskCollaboratorByEmail(email), LocalDate.now(), timeToReport);

		return wasReportCreated;

	}



}
