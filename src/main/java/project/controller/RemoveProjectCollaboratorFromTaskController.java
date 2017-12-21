package project.controller;

import project.model.ProjectCollaborator;
import project.model.Task;

public class RemoveProjectCollaboratorFromTaskController {

	/**
	 * @param taskToRemove
	 *            The task which will be removed from the ProjectCollaborator
	 * @param collaborator
	 *            The collaborator that wants to remove a task from it's taskList
	 */

	public void removeProjectCollaboratorFromTaskController(Task taskToRemove, ProjectCollaborator collaborator) {

		taskToRemove.removeProjectCollaboratorFromTask(collaborator);

	}
}
