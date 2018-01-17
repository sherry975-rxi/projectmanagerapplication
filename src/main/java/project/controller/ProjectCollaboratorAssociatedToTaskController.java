package project.controller;

import project.model.Company;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;

public class ProjectCollaboratorAssociatedToTaskController {

	int projectID;

	/*
	 * This controller lets an user add, mark as finished, or remove a task that's
	 * associated to him. Since only the Project Manager can directly add or remove
	 * team members from a Task, those methods instead create a request for the
	 * project manager to approve or reject
	 * 
	 * * @param int selectedProjectID the ID of the selected Project
	 * 
	 */
	public ProjectCollaboratorAssociatedToTaskController(int selectedProjectID) {
		this.projectID = selectedProjectID;
	}

	/**
	 * This method receives the task and projectCollaborator data of the user
	 * wishing to remove the task. It attempts to create a report for the
	 * ProjectManager to approve or reject
	 * 
	 * 
	 * @param taskToRemove
	 *            The task which will be removed from the ProjectCollaborator
	 * 
	 * @param collaborator
	 *            The collaborator that wants to remove a task that's associated to
	 *            him
	 * 
	 * @return true if the request was created successfully, false otherwise
	 */

	public boolean createTaskWorkerRemovalRequestController(Task taskToRemove, ProjectCollaborator collaborator) {
		Project selectedProject = Company.getTheInstance().getProjectsRepository().getProjById(this.projectID);
		if (selectedProject != null) {
			return selectedProject.createTaskRemovalRequest(collaborator, taskToRemove);
		} else
			return false;

	}

	// TODO implement method in task to remove all collaborators from the task and
	// to clear the list of requests related to it
	/**
	 * @param taskToMarkAsFinished
	 *            The task that will be marked as finished
	 * 
	 * @return true or false if the Task state was successfully marked as finished
	 */
	public boolean markTaskAsFinishedController(Task taskToMarkAsFinished) {
		taskToMarkAsFinished.setFinishDate();

		return taskToMarkAsFinished.getTaskState().changeToFinished();

	}

	// TODO this method requires a validation that an assignment request from the
	// same user doesn't already exist
	/**
	 * This method receives the task and projectCollaborator data of the user
	 * wishing to join the task team. It attempts to create a report for the
	 * ProjectManager to approve or reject
	 * 
	 * @param taskToAssociateColaborator
	 *            The task which will be associated to a ProjectCollaborator
	 * @param collaborator
	 *            The collaborator which will have a task associated to him
	 * 
	 * @return true if the request was created successfully, false otherwise
	 */
	public boolean createTaskWorkerAssignmentRequestController(Task taskToAssociateColaborator,
			ProjectCollaborator collaborator) {
		Project selectedProject = Company.getTheInstance().getProjectsRepository().getProjById(this.projectID);

		if (selectedProject != null) {
			return selectedProject.createTaskAssignementRequest(collaborator, taskToAssociateColaborator);
		} else
			return false;
	}

}
