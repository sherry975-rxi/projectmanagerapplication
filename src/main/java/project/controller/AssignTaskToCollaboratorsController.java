package project.controller;

import project.model.Company;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskRepository;

public class AssignTaskToCollaboratorsController {

	private TaskRepository taskRepository;
	private ProjectRepository projectRepository;
	private Project project;
	private Company company;

	/**
	 * 
	 * 
	 * US 361 - Como Gestor de projeto, quero poder atribuir uma tarefa a um
	 * colaborador.
	 * 
	 * 
	 * 
	 * This controller allows a Project Manager to assign a task to
	 * ProjectCollaborator
	 * 
	 * 
	 *
	 */

	/**
	 * @param projectID
	 *            The ProjectId that contains the task that the ProjectManager wants
	 *            to delete
	 * 
	 */

	public AssignTaskToCollaboratorsController(int projId) {

		company = Company.getTheInstance();
		projectRepository = company.getProjectsRepository();
		project = projectRepository.getProjById(projId);
		taskRepository = project.getTaskRepository();

	}

	/**
	 * @param taskID
	 *            The id of the task that will be assigned to the TaaskCollaborator
	 * @param taskCollaborator
	 *            The task collaborator that the Project Manager wants to assign a
	 *            task to.
	 */
	public boolean assignTaskToProjectCollaboratorController(String taskID, ProjectCollaborator projCollaborator) {

		boolean wasTheTaskAddedToCollaborator = false;
		// Gets the task by the id of the task
		Task taskToAddCollaborator = taskRepository.getTaskByID(taskID);

		// Verifies if the taskToAddCollaborator and projCollaborator are valid
		if (taskToAddCollaborator != null && projCollaborator != null) {

			/*
			 * adds the ProjectCollaborator to the TaksTeam and returns and gets the result
			 * of the method
			 */
			wasTheTaskAddedToCollaborator = taskToAddCollaborator.addProjectCollaboratorToTask(projCollaborator);

		}

		// Returns true if the ProjectCollaborator was successfully added to the
		// TaskTeams
		return wasTheTaskAddedToCollaborator;

	}

}