package project.controller;

import project.model.*;

public class US204v2createRequestAddCollaboratorToTaskTeamController {
	private User user;
	private TaskContainer taskContainer;
	private ProjectContainer projectContainer;
	private Project project;
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
	// projectContainer = company.getProjectsContainer();
	// project = projectContainer.getProjById(projId);
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
		this.projectContainer = new ProjectContainer();
		this.project = projectContainer.getProjById(this.projectID);
		this.taskContainer = project.getTaskRepository();
		this.user = user;

	}

	public boolean createTaskTeamRequest() {

		Task taskToAddCollaboratorTo = taskContainer.getTaskByID(this.taskID);
		return project.createTaskAssignementRequest(getProjectCollaboratorFromUser(this.user), taskToAddCollaboratorTo);
	}

	/**
	 * This method gets the ProjectID by splitting the taskID in two.
	 *
	 * @param taskID
	 *            String with the taskID
	 */
	public void setProjectIDFromTaskID(String taskID) {

		String[] partsTask = taskID.split("\\.");
		String projID = partsTask[0];

		setProjectID(Integer.parseInt(projID));
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
		return taskContainer.getTaskByID(taskID);

	}

	/**
	 * @return projectId
	 */
	public Integer getProjectID() {
		return this.projectID;
	}

}
