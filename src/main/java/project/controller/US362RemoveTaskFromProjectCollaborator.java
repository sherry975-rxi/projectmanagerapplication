/**
 * 
 */
package project.controller;

import project.Services.ProjectService;
import project.Services.TaskService;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Group3
 *
 */
public class US362RemoveTaskFromProjectCollaborator {

	private TaskService taskService;
	private ProjectCollaborator projectCollaborator;
	private Project project;
	private Task task;

	/**
	 * Constructor
	 * 
	 * @param projectIDtoInstantiate
	 */
	public US362RemoveTaskFromProjectCollaborator(Project project, Task task) {
		this.project = project;
		this.task = task;
		this.projectCollaborator = null;
	}

	/**
	 * This method returns the List of Collaborators from a specific task
	 * 
	 * @return Returns a list of strings with the task members information
	 */
	public List<String> getProjectCollaboratorsFromTask() {

		List<String> taskTeam = new ArrayList<>();
		for (ProjectCollaborator other : taskService.getProjectCollaboratorsFromTask(this.project,this.task)) {

			String userName = other.getUserFromProjectCollaborator().getName();
			String userEmail = other.getUserFromProjectCollaborator().getEmail();
			String userFunction = other.getUserFromProjectCollaborator().getFunction();
			String userInfo = "Name: " + userName + "\n" + "Email: " + userEmail + "\n" + "Function: " + userFunction;
			taskTeam.add(userInfo);
		}

		return taskTeam;
	}

	/**
	 * This method removes a project collaborator from the Task Team of a specific
	 * task
	 */

	public boolean removeCollaboratorFromTask() {
		boolean removeCollaboratorFromTask = false;
		 if(this.task.removeProjectCollaboratorFromTask(this.projectCollaborator)){
			 taskService.saveTask(this.task);
			 removeCollaboratorFromTask = true;
		 }
		return removeCollaboratorFromTask;
	}

	/**
	 * Sets the projectCollaborator
	 * 
	 * @param projectCollaboratorIndex
	 *            Position of the project Collaborator in the task team list
	 */
	public void setProjectCollaborator(Integer projectCollaboratorIndex) {
		this.projectCollaborator = taskService.getProjectCollaboratorsFromTask(this.project,this.task).get(projectCollaboratorIndex);

	}

	/**
	 * Gets the project collaborator from task
	 * 
	 * @return Returns the project collaborator
	 */
	public ProjectCollaborator getProjectCollaborator() {
		return this.projectCollaborator;
	}

}
