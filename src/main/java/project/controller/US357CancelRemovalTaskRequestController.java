package project.controller;

import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;

/**
 * @author Group 3
 * 
 *         This Class responds to the US 357 - Como Gestor de projeto, quero
 *         poder cancelar a remoção de uma tarefa por um colaborado
 *
 */
public class US357CancelRemovalTaskRequestController {

	int projectID;
	String taskID;
	String userEmail;

	/**
	 * Constructor to instantiate a new US357CancelRemoveTaskRequestController
	 * 
	 * @param projectID
	 *            Project ID of the project that the user wants to see the removal
	 *            task request list
	 */
	public US357CancelRemovalTaskRequestController(int projectID) {
		this.projectID = projectID;
		this.taskID = null;
		this.userEmail = null;
	}

	/**
	 * This method returns a list of Strings that allows a Project Manager to check
	 * all the pending removal requests of a certain project
	 * 
	 * @return List of strings with the information of the pending removal requests
	 *         from a certain project
	 */
	public List<String> viewPendingRemovalRequests() {

		Project projectToGetRequests = Company.getTheInstance().getProjectsRepository().getProjById(this.projectID);

		List<String> listOfPendingRemovalRequests = projectToGetRequests.viewPendingTaskRemovalRequests();

		return listOfPendingRemovalRequests;
	}

	/**
	 * This method receives a string with the information of a certain removal
	 * request and divides it in order to get the user email and task ID. Then it
	 * sets the parameters userEmail and taskID from this class with those values.
	 * 
	 * @param requestData
	 *            Information of the request chosen by the user.
	 */
	public void setTaskIDandUserEmailWithRequestString(String requestData) {

		String[] parts = requestData.split("\n");
		String userName = parts[0];
		String userEmail = parts[1];
		String taskID = parts[2];
		String taskDescription = parts[3];

		this.userEmail = userEmail;
		this.taskID = taskID;
	}

	/**
	 * This method checks if the task returned by the getProjById method is not
	 * null.
	 * 
	 * @return TRUE if is valid FALSE if it is not valid.
	 */
	public boolean isTaskIDValid() {

		Project projectToGetRequests = Company.getTheInstance().getProjectsRepository().getProjById(this.projectID);
		Boolean isTaskValid = false;

		if (projectToGetRequests.getTaskRepository().getTaskByID(this.taskID) != null) {
			isTaskValid = true;
		}

		return isTaskValid;
	}

	/**
	 * This method checks if the user returned by the getUserByEmail method is not
	 * null.
	 * 
	 * @return TRUE if is valid FALSE if it is not valid.
	 */
	public boolean isEmailFromAUser() {

		Boolean isUserEmailValid = false;

		if (Company.getTheInstance().getUsersRepository().getUserByEmail(this.userEmail) != null) {
			isUserEmailValid = true;
		}

		return isUserEmailValid;
	}

	/**
	 * This method allows a Project Manager to accept a user request to leave a
	 * task. By accepting, the user gets removed from the task team and the task
	 * request gets deleted from the pending removal request list.
	 * 
	 * @param requestData
	 *            Information of the request chosen by the user.
	 * 
	 * @return TRUE if the user removal was successfully done or FALSE if not.
	 */
	public boolean acceptRemovalRequestFromTask(String requestData) {

		boolean removalAccepted = false;

		// This condition certifies that the userEmail and TaskID are valid
		if (this.isTaskIDValid() == true && this.isEmailFromAUser() == true) {

			// Gets the user with the user email
			User userToRemoveFromTask = Company.getTheInstance().getUsersRepository().getUserByEmail(userEmail);
			// Gets the project with the project ID
			Project project = Company.getTheInstance().getProjectsRepository().getProjById(this.projectID);
			// Gets the project collaborator correspondent to the user
			ProjectCollaborator projectCollaboratorFromUser = project.findProjectCollaborator(userToRemoveFromTask);
			// Gets the task from the task Id
			Task taskToRemoveFrom = project.getTaskRepository().getTaskByID(taskID);

			// Removes the project Collaborator correspondent to the user from task.
			taskToRemoveFrom.removeProjectCollaboratorFromTask(projectCollaboratorFromUser);

			// Deletes the request from the pendingRemovalRequestList
			project.deleteTaskRemovalRequest(projectCollaboratorFromUser, taskToRemoveFrom);

			removalAccepted = true;

		}

		return removalAccepted;
	}

	/**
	 * This method allows a Project Manager to cancel a user request to leave a
	 * task. By cancel it, the user does'nt get removed from the task team and the
	 * task request gets deleted from the pending removal request list.
	 * 
	 * @return TRUE if the cancel is successfully done FALSE if not
	 */
	public boolean cancelRemovalRequestFromTask() {

		boolean removalCancelled = false;
		// This condition certifies that the userEmail and TaskID are valid
		if (this.isTaskIDValid() == true && this.isEmailFromAUser() == true) {

			// Gets the user with the user email
			User userToRemoveFromTask = Company.getTheInstance().getUsersRepository().getUserByEmail(userEmail);
			// Gets the project with the project ID
			Project project = Company.getTheInstance().getProjectsRepository().getProjById(this.projectID);
			// Gets the project collaborator correspondent to the user
			ProjectCollaborator projectCollaboratorFromUser = project.findProjectCollaborator(userToRemoveFromTask);
			// Gets the task from the task Id
			Task task = project.getTaskRepository().getTaskByID(this.taskID);
			// Deletes the request from the pendingRemovalRequestList

			project.deleteTaskRemovalRequest(projectCollaboratorFromUser, task);

			removalCancelled = true;
		}

		return removalCancelled;
	}
}
