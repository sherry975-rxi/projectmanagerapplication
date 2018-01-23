package project.controller;

import java.util.ArrayList;
import java.util.List;

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
			if (other.getTaskID().equals(taskIndex)) {
				other.createReport(other.getTaskCollaboratorByEmail(email));
				wasReportCreated = other.createReport(other.getTaskCollaboratorByEmail(email));
			}
		}
		return wasReportCreated;
	}

	/**
	 * @param email
	 *            The email of the user to search his associated tasks ID
	 * @return A list of Strings that contains the tasks IDs associated to him that
	 *         are set to the state "OnGoing", and don0t have any report
	 */

	public List<String> getOnGoingUnreportedTasksOfUser() {

		List<String> taskIdList = new ArrayList<>();

		for (Task other : this.projectRepository.getOnGoingUserUnreportedaTasks(this.username)) {
			taskIdList.add(other.getTaskID());

		}

		return taskIdList;
	}

	/**
	 * This method checks if a task exist in any project by checking it's ID in
	 * every project
	 * 
	 * @param taskID
	 *            The id of the project to search for
	 * 
	 * @return TRUE if if finds a match, FALSE if not
	 */
	public boolean doesTaskIdExist(String taskID) {
		boolean doesTaskExist = false;
		doesTaskExist = this.projectRepository.doesTaskIdExists(taskID);
		return doesTaskExist;
	}

}
