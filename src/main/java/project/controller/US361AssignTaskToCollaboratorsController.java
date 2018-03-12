package project.controller;

import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.ProjectContainer;
import project.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Group 3
 * 
 *         * US 361 - Como Gestor de projeto, quero poder atribuir uma tarefa a
 *         um colaborador.
 */
public class US361AssignTaskToCollaboratorsController {

	private Project project;
	private Task task;
	private ProjectCollaborator projectCollaborator;

	/**
	 * Constructor to instantiate a new US361TaskToCollaboratorsController
	 * 
	 * @param project
	 *            Project
	 * @param task
	 *            Task to add the user to
	 */
	public US361AssignTaskToCollaboratorsController(Project project, Task task) {
		this.project = project;
		this.task = task;
	}

	/**
	 * Gets the project active users
	 * 
	 * @return Returns a list of strings with the name and email of the active
	 *         project members
	 */
	public List<String> getProjectActiveTeam() {

		List<String> projectTeam = new ArrayList<>();

		for (ProjectCollaborator other : this.project.getProjectTeam()) {
			String userName = other.getUserFromProjectCollaborator().getName();
			String userEmail = other.getUserFromProjectCollaborator().getEmail();
			String userFunction = other.getUserFromProjectCollaborator().getFunction();
			String userInfo = "Name: " + userName + "\n" + "Email: " + userEmail + "\n" + "Function: " + userFunction;
			projectTeam.add(userInfo);
		}
		return projectTeam;
	}

	/**
	 * Sets the projectCollaborator by getting searching the active project team
	 * using the index number
	 * 
	 * @param userIndex
	 *            Index of the projectCollaborator in the ActiveProjectTeam List
	 */
	public void setUserToAddToTask(int userIndex) {
		this.projectCollaborator = project.getActiveProjectTeam().get(userIndex);
	}

	/**
	 * Returns the project collaborator that corresponds to the chosen user
	 */
	public ProjectCollaborator getUserToAddToTask() {
		return this.projectCollaborator;
	}

	/**
	 * Assigns the chosen collaborator to the task
	 * 
	 * @return TRUE if it was possible to add or FALSE if not
	 */
	public boolean assignCollaboratorToTask() {
		boolean assignCollaboratorToTask = false;
		ProjectContainer projectContainer = new ProjectContainer();
		if(task.addProjectCollaboratorToTask(this.projectCollaborator)){
			projectContainer.addProjectToProjectContainerX(this.project);
			assignCollaboratorToTask = true;
		}
		return assignCollaboratorToTask;
	}

}
