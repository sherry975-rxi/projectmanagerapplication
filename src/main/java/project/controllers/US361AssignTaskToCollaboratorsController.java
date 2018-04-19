package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.services.ProjectService;
import project.services.TaskService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Group 3
 * 
 *         * US 361 - Como Gestor de projeto, quero poder atribuir uma tarefa a
 *         um colaborador.
 */
@Controller
public class US361AssignTaskToCollaboratorsController {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private TaskService taskService;

	private Project project;
	private Task task;
	private ProjectCollaborator projectCollaborator;


	public US361AssignTaskToCollaboratorsController() {
		//Empty constructor created for JPA integration tests

	}

	/*
	 * Getters and Setters
	 */
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public ProjectCollaborator getProjectCollaborator() {
		return projectCollaborator;
	}

	public void setProjectCollaborator(ProjectCollaborator projectCollaborator) {
		this.projectCollaborator = projectCollaborator;
	}

	/**
	 * Gets the project active users
	 * 
	 * @return Returns a list of strings with the name and email of the active
	 *         project members
	 */
	public List<String> getProjectActiveTeam() {

		List<String> projectTeam = new ArrayList<>();

		for (ProjectCollaborator other : projectService.getProjectTeam(this.project)) {
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
		this.projectCollaborator = projectService.getActiveProjectTeam(this.project).get(userIndex);

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
		if (task.addProjectCollaboratorToTask(this.projectCollaborator)) {
			projectService.saveProject(this.project);
			taskService.saveTask(this.task);
			assignCollaboratorToTask = true;
		}
		return assignCollaboratorToTask;
	}

}
