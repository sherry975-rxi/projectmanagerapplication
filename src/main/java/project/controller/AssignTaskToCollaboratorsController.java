package project.controller;

import project.model.Company;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskRepository;
import project.model.User;

public class AssignTaskToCollaboratorsController {

	private TaskRepository taskRepository;
	private ProjectRepository projectRepository;
	private Project project;
	private Company company;
	private Integer projectID;

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
	 * Constructor that receives a string TaskID
	 * 
	 * @param taskID
	 *            ID of a Task
	 */
	public AssignTaskToCollaboratorsController(String taskID) {

		setProjectIDFromTaskID(taskID);
		project = projectRepository.getProjById(this.projectID);

	}

	/**
	 * This method gets the ProjectID by splitting the taskID in two.
	 * 
	 * @param taskDetails
	 *            String with the taskID
	 */
	public void setProjectIDFromTaskID(String taskID) {

		String[] partsTask = taskID.split("\\.");
		String projectID = partsTask[0];
		String taskCounter = partsTask[1];

		setProjectID(Integer.parseInt(projectID));
	}

	/**
	 * This method sets the projectID directly from an int received
	 * 
	 * @param projectID
	 *            ProjectID to set
	 */
	public void setProjectID(int projectID) {
		this.projectID = projectID;
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

	/**
	 * This method returns the project collaborator of a project from a user
	 * 
	 * @param user
	 *            User to get project collaborator
	 * @return Project collaborator of a project from a user
	 */
	public ProjectCollaborator getProjectCollaboratorFromUser(User user) {

		ProjectCollaborator projcollab;

		projcollab = project.getProjectCollaboratorFromUser(user);

		return projcollab;
	}

	/**
	 * This method retrieves a Task from its ID
	 * 
	 * @param taskID
	 *            TaskID to get the Task from
	 * 
	 * @return Task from taskID
	 */
	public Task getTaskByTaskID(String taskID) {
		Task taskToAddCollaborator = taskRepository.getTaskByID(taskID);
		return taskToAddCollaborator;

	}

}
