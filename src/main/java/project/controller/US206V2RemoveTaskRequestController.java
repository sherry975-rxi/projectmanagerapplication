package project.controller;

import project.model.Company;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;

/**
 * @author Group 3
 *
 */
public class US206V2RemoveTaskRequestController {

	int projectID;
	String taskID;
	User user;

	/**
	 * Constructor to instantiate a new CollaboratorRemovalrequest
	 * 
	 * @param user
	 *            User that asks for the removal
	 * @param projectID
	 *            Project ID of project where the task is
	 * @param taskID
	 *            Task ID of the task that the user wants to be removed from
	 */
	public US206V2RemoveTaskRequestController(User user, int projectID, String taskID) {
		this.user = user;
		this.projectID = projectID;
		this.taskID = taskID;
	}

	/**
	 * This method instantiates a new removalRequest and adds it to the
	 * removalRequestList of a certain project.
	 * 
	 */
	public boolean createRequest() {

		Project project = Company.getTheInstance().getProjectsRepository().getProjById(this.projectID);
		Task taskBeRemovedOf = project.getTaskRepository().getTaskByID(this.taskID);
		ProjectCollaborator projectCollaborator = project.findProjectCollaborator(this.user);

		return project.createTaskRemovalRequest(projectCollaborator, taskBeRemovedOf);

	}

}
