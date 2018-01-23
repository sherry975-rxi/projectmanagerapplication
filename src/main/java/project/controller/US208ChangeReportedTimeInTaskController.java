package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.User;
import project.model.UserRepository;

public class US208ChangeReportedTimeInTaskController {

	private User username;
	private UserRepository userRepository;
	private Company company;
	private ProjectRepository projectRepository;
	private String email;

	public US208ChangeReportedTimeInTaskController(String email) {
		this.company = Company.getTheInstance();
		this.projectRepository = company.getProjectsRepository();
		this.userRepository = company.getUsersRepository();
		this.username = userRepository.getUserByEmail(email);
		this.email = email;

	}

	/**
	 * This method is used to allow a user to change a reported time in a task.
	 * 
	 * @param taskIndex
	 *            String used to select a task from list of tasks
	 * @param newTime
	 *            int used to set a new time of a task report
	 * @param email
	 *            string defining an email that will be used to retrieve the
	 *            respective user
	 * @return returns a boolean to confirm if the operation was successfull
	 * 
	 */
	public boolean correctReportedTimeInTaskController(String taskIndex, int newTime) {

		boolean wasTaskCorrected = false;

		for (int i = 0; i < projectRepository.getUserTasks(username).size(); i++) {
			if (projectRepository.getUserTasks(username).get(i).getTaskID().equals(taskIndex)) {
				wasTaskCorrected = projectRepository.getUserTasks(username).get(i).changeReportedTime(newTime, email);
				;
			}
		}
		return wasTaskCorrected;
	}

	/**
	 * @param email
	 *            The email of the user to search his associated tasks ID
	 * @return A list of Strings that contains the tasks IDs associated to him
	 */

	public List<String> getOnGoingIDTasksOfUser() {

		List<String> taskIdList = new ArrayList<>();

		for (Task other : this.projectRepository.getOnGoingUserReportedTasks(this.username)) {
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

	/**
	 * @param email
	 *            The email of the user to search his associated tasks ID
	 * @param taskIndex
	 *            The task to look for it's report
	 * @return The reported time by a given TaskCollaborator
	 */
	public int getReportedTimeByCollaboratorController(String taskIndex) {
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
