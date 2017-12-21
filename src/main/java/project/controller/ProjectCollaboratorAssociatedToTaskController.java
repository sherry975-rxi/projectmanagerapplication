package project.controller;

import project.model.ProjectCollaborator;
import project.model.Task;

public class ProjectCollaboratorAssociatedToTaskController {

	/*
	 * This controller lets an user add, mark as finished, or remove a task that's
	 * associated to him
	 * 
	 * 
	 * @param taskToRemove The task which will be removed from the
	 * ProjectCollaborator
	 * 
	 * @param collaborator The collaborator that wants to remove a task that's
	 * associated to him
	 */

	public void removeProjectCollaboratorFromTaskController(Task taskToRemove, ProjectCollaborator collaborator) {

		taskToRemove.removeProjectCollaboratorFromTask(collaborator);

	}

	/**
	 * @param taskToMarkAsFinished
	 *            The task that will be marked as finished
	 * 
	 */
	public void markTaskAsFinishedController(Task taskToMarkAsFinished) {

		taskToMarkAsFinished.markTaskAsFinished();

	}

	/**
	 * @param taskToAssociateColaborator
	 *            The task which will be associated to a ProjectCollaborator
	 * @param collaborator
	 *            The collaborator which will have a task associated to him
	 */
	public void addProjectCollaboratorToTaskController(Task taskToAssociateColaborator,
			ProjectCollaborator collaborator) {

		taskToAssociateColaborator.addProjectCollaboratorToTask(collaborator);

	}

}
