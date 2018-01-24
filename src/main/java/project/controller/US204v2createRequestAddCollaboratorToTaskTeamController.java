package project.controller;

import project.model.Company;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskRepository;
import project.model.User;

public class US204v2createRequestAddCollaboratorToTaskTeamController {
	private User user;
	private TaskRepository taskRepository;
	private ProjectRepository projectRepository;
	private Project project;
	private Company company;
	private Integer projectID;
	private String taskID;

	/**
	 * 
	 * US 204v2 - Como Gestor de projeto, quero poder atribuir uma tarefa a um
	 * colaborador.
	 * 
	 * This controller allows a user to create a request to add be added to the
	 * TaskTeam that Project Manager will latter approve.
	 */

	/**
	 * Constructor
	 *
	 * @param projectID
	 *            The ProjectId that corresponds to the taskID that the User wants
	 *            to add himself to
	 */
	// public US204v2createRequestAddCollaboratorToTaskTeamController(int projId) {
	//
	// company = Company.getTheInstance();
	// projectRepository = company.getProjectsRepository();
	// project = projectRepository.getProjById(projId);
	//
	// }

	/**
	 * Constructor
	 * 
	 * @param taskID
	 *            ID of a Task that the User wants to add himself to
	 */
	public US204v2createRequestAddCollaboratorToTaskTeamController(String taskID, User user) {
		setProjectIDFromTaskID(taskID);
		this.company = Company.getTheInstance();
		this.projectRepository = company.getProjectsRepository();
		this.project = projectRepository.getProjById(this.projectID);
		this.taskRepository = project.getTaskRepository();
		this.user = user;

	}

	public boolean createTaskTeamRequest() {

		Task taskToAddCollaboratorTo = taskRepository.getTaskByID(this.taskID);
		return project.createTaskAssignementRequest(getProjectCollaboratorFromUser(this.user), taskToAddCollaboratorTo);
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

		setProjectID(Integer.parseInt(projectID));
	}

	/**
	 * This method sets the projectID directly from an integer that is received
	 * 
	 * @param projectID
	 *            ProjectID to set
	 */
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	/**
	 * This method returns the project collaborator of a project from a user
	 * 
	 * @param user
	 *            User to get project collaborator
	 * @return Project collaborator of a project from a user
	 */
	private ProjectCollaborator getProjectCollaboratorFromUser(User user) {

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

	/**
	 * @return projectId
	 */
	public Integer getProjectID() {
		return this.projectID;
	}

}
