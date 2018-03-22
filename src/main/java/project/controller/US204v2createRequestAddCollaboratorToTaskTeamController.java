package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.services.ProjectService;
import project.services.TaskService;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;

@Controller
public class US204v2createRequestAddCollaboratorToTaskTeamController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private ProjectService projectService;

	public User user;
	public Project project;
	public Integer projectID;
	public String taskID;

	/**
	 * 
	 * US 204v2 - Como Gestor de projeto, quero poder atribuir uma tarefa a um
	 * colaborador.
	 * 
	 * This controller allows a user to create a request to add be added to the
	 * TaskTeam that Project Manager will latter approve.
	 */

	public void setUser(User user) {
		this.user = user;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public void setProjectID(Integer projectID) {
		this.projectID = projectID;
	}

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

	// /**
	// * Constructor
	// *
	// * @param taskID
	// * ID of a Task that the User wants to add himself to
	// */
	// public US204v2createRequestAddCollaboratorToTaskTeamController(String taskID,
	// User user) {
	// this.taskID = taskID;
	// this.user = user;
	// setProjectIDFromTaskID(taskID);
	//
	// this.project = projectContainer.getProjectById(this.projectID);
	// this.user = user;
	//
	// }

	public US204v2createRequestAddCollaboratorToTaskTeamController() {

	}

	// public boolean createTaskTeamRequest() {
	//
	// Task taskToAddCollaboratorTo = taskContainer.getTaskByTaskID(this.taskID);
	// return
	// taskToAddCollaboratorTo.createTaskAssignementRequest(getProjectCollaboratorFromUser(this.user));
	// }

	public boolean createTaskTeamRequest(String taskID, User user) {

		Task taskToAddCollaboratorTo = taskService.getTaskByTaskID(taskID);
		return taskToAddCollaboratorTo.createTaskAssignementRequest(getProjectCollaboratorFromUser(user));
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

		projcollab = projectService.findActiveProjectCollaborator(user, project);

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
		return taskService.getTaskByTaskID(taskID);

	}

	/**
	 * @return projectId
	 */
	public Integer getProjectID() {
		return this.projectID;
	}

}
