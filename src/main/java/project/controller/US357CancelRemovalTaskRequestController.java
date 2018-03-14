package project.controller;

import project.Services.ProjectService;
import project.Services.UserContainerService;
import project.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Group 3
 * 
 *         This Class responds to the US 357 - Como Gestor de projeto, quero
 *         poder cancelar a remoção de uma tarefa por um colaborado
 *
 */
public class US357CancelRemovalTaskRequestController {

	Project project;
	Task task;
	User userToRemove;
	ProjectService projectContainer = new ProjectService();

	/**
	 * Constructor to instantiate a new US357CancelRemoveTaskRequestController
	 * 
	 * @param project
	 *            project to show the removal task requests
	 */
	public US357CancelRemovalTaskRequestController(Project project) {
		this.project = project;
		this.task = null;
		this.userToRemove = null;
	}

	/**
	 * This method returns a list of Strings that allows a Project Manager to check
	 * all the pending removal requests of a certain project
	 * 
	 * @return List of strings with the information of the pending removal requests
	 *         from a certain project
	 */
	public List<String> viewPendingRemovalRequests() {

		List<String> listOfPendingRemovalRequests = new ArrayList<>();
		listOfPendingRemovalRequests.addAll(project.viewPendingTaskRemovalRequests());

		return listOfPendingRemovalRequests;
	}

	/**
	 * This method receives a string with the information of a certain removal
	 * request and divides it in order to get the user email and task ID. Then it
	 * sets the parameters user and task from this class with those values.
	 * 
	 * @param requestData
	 *            Information of the request chosen by the user.
	 */
	public void setTaskIDandUserEmailWithRequestString(String requestData) {
		UserContainerService userContainer = new UserContainerService();
		userContainer.updateUserContainer();
		String[] parts = requestData.split("\n");
		String userEmail = parts[1];
		String taskID = parts[2];

		this.userToRemove = userContainer.getUserByEmail(userEmail);
		this.task = project.getTaskRepository().getTaskByID(taskID);
		projectContainer.saveProjectInRepository(this.project);
	}

	/**
	 * This method allows a Project Manager to accept a user request to leave a
	 * task. By accepting, the user gets removed from the task team and the task
	 * request gets deleted from the pending removal request list.
	 * 
	 * @return TRUE if the user removal was successfully done or FALSE if not.
	 */
	public boolean acceptRemovalRequestFromTask() {
		boolean acceptRemovalRequestFromTask = false;
		// Gets the project collaborator correspondent to the user
		ProjectCollaborator projectCollaboratorFromUser = project.findProjectCollaborator(this.userToRemove);

		// Removes the project Collaborator correspondent to the user from task.
		this.task.removeProjectCollaboratorFromTask(projectCollaboratorFromUser);
		// Deletes the request from the pendingRemovalRequestList
		if(project.deleteTaskRemovalRequest(projectCollaboratorFromUser, this.task)){
			projectContainer.saveProjectInRepository(this.project);
			acceptRemovalRequestFromTask = true;
		}

		return acceptRemovalRequestFromTask;
	}

	/**
	 * This method allows a Project Manager to cancel a user request to leave a
	 * task. By cancel it, the user does'nt get removed from the task team and the
	 * task request gets deleted from the pending removal request list.
	 * 
	 * @return TRUE if the cancel is successfully done FALSE if not
	 */
	public boolean cancelRemovalRequestFromTask() {
		boolean cancelRemovalRequestFromTask = false;
		// Gets the project collaborator correspondent to the user
		ProjectCollaborator projectCollaboratorFromUser = project.findProjectCollaborator(this.userToRemove);

		if(project.deleteTaskRemovalRequest(projectCollaboratorFromUser, this.task)){
			cancelRemovalRequestFromTask = true;
			projectContainer.saveProjectInRepository(this.project);
		}
		return	cancelRemovalRequestFromTask;
	}
}
