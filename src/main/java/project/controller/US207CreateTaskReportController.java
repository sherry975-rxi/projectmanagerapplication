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

	/**
	 * Constructor of US207CreateTaskReportController
	 * 
	 * @param email
	 *            The email of the user that will create a task report
	 */
	public US207CreateTaskReportController(String email) {
		this.company = Company.getTheInstance();
		this.projectRepository = company.getProjectsRepository();
		this.userRepository = company.getUsersRepository();
		this.username = userRepository.getUserByEmail(email);
		this.email = email;

	}

	public boolean createReportController(String taskIndex, int newTime) {

		boolean wasReportCreated = false;

		for (Task other : projectRepository.getUserTasks(username)) {
			if (other.getTaskID().equals(taskIndex) && !other.doesTaskHaveReportByGivenUser(email)) {

				other.createReport(other.getTaskCollaboratorByEmail(email));

				wasReportCreated = other.changeReportedTime(newTime, username.getEmail());
			} else if (other.doesTaskHaveReportByGivenUser(email)) {

				wasReportCreated = other.changeReportedTime(newTime, username.getEmail());

			} else {
				break;
			}
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
	public int getReportedTimeByCollaborator(String taskIndex) {
		int reportedTimeByCollaborator = 0;
		for (int i = 0; i < projectRepository.getUserTasks(username).size(); i++) {
			if (projectRepository.getUserTasks(username).get(i).getTaskID().equals(taskIndex)) {
				reportedTimeByCollaborator = projectRepository.getUserTasks(username).get(i)
						.getReportedTimeByTaskCollaborator(email);
				break;
			}
		}

		return reportedTimeByCollaborator;
	}

}
