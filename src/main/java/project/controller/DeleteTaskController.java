package project.controller;

import project.model.Company;
import project.model.Project;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskRepository;

public class DeleteTaskController {

	private TaskRepository taskRepository;
	private ProjectRepository projectRepository;
	private Project project;
	private Company company;

	/**
	 * 
	 * 
	 * US 345 - Como Gestor de projeto, quero poder eliminar uma tarefa não
	 * iniciada.
	 * 
	 * 
	 * 
	 * This controller allows a Project Manager to delete a task from the Task
	 * Repository The "State" of the Task must be set to
	 * 
	 * "Assigned", "Planned" "Created" or "Ready"
	 * 
	 * Otherwise the task can't be deleted from the task repository.
	 * 
	 * 
	 *
	 */

	/**
	 * @param proj
	 *            The Project that will be associated to the Controller
	 */
	public DeleteTaskController(int projId) {

		company = Company.getTheInstance();
		projectRepository = company.getProjectsRepository();
		project = projectRepository.getProjById(projId);
		taskRepository = project.getTaskRepository();

	}

	/**
	 * @param projectID
	 *            The ProjectId that contains the task that the ProjectManager wants
	 *            to delete
	 * @param taskID
	 *            The ID of the task that will be deleted
	 */

	public boolean deleteTask(int projectID, String taskID) {

		/*
		 * Creates a variable that returns if the task was deleted
		 */
		boolean wasTaskDeleted = false;

		/*
		 * Finds the task by it's id
		 */
		Task taskToDelete = this.taskRepository.getTaskByID(taskID);

		/*
		 * If the taskID matches a task in the repository, the task will be deleted,
		 * else, this method will return false
		 */
		if (taskToDelete != null) {

			// Deletes the task from the task Repository
			wasTaskDeleted = this.taskRepository.deleteTask(taskToDelete);

		}

		else {
			wasTaskDeleted = false;
		}

		return wasTaskDeleted;

	}

}
