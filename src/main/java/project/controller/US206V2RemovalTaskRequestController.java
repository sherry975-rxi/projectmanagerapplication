package project.controller;

import project.model.Company;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;

/**
 * @author Group 3
 * 
 *         This Class responds to the USComo colaborador, eu pretendo remover
 *         uma tarefa que consta na minha lista de tarefas.
 *
 */
public class US206V2RemovalTaskRequestController {

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
	public US206V2RemovalTaskRequestController(User user, int projectID, String taskID) {
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

		boolean createdSucess = false;

		Project project = Company.getTheInstance().getProjectsRepository().getProjById(this.projectID);
		Task taskBeRemovedOf = project.getTaskRepository().getTaskByID(this.taskID);
		ProjectCollaborator projectCollaborator = project.findProjectCollaborator(this.user);
		if (taskBeRemovedOf != null && !project.isRemovalRequestAlreadyCreated(projectCollaborator, taskBeRemovedOf)) {
			
			project.createTaskRemovalRequest(projectCollaborator, taskBeRemovedOf);
			createdSucess = true;
		}
		return createdSucess;
	}

}
