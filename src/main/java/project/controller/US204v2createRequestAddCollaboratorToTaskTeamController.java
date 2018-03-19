package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import project.Services.ProjectService;
import project.Services.TaskService;
import project.model.*;

public class US204v2createRequestAddCollaboratorToTaskTeamController {
	private User user;

	@Autowired
	private TaskService taskContainer;

	@Autowired
	private ProjectService projectContainer;

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
		this.taskID = taskID;
		this.user = user;
		setProjectIDFromTaskID(taskID);

		this.project = projectContainer.getProjectById(this.projectID);
		this.user = user;

	}

	public boolean createTaskTeamRequest() {

		Task taskToAddCollaboratorTo = taskContainer.getTaskByTaskID(this.taskID);
		return taskToAddCollaboratorTo.createTaskAssignementRequest(getProjectCollaboratorFromUser(this.user));
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

		projcollab = projectContainer.findActiveProjectCollaborator(user, project);

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
		return taskContainer.getTaskByTaskID(taskID);

	}

	/**
	 * @return projectId
	 */
	public Integer getProjectID() {
		return this.projectID;
	}

}
