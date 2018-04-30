package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.List;

/**
 * @author Group 3
 * 
 *         This Class responds to the US 357 - Como Gestor de projeto, quero
 *         poder cancelar a remoção de uma tarefa por um colaborado
 *
 */
@Controller
public class US357CancelRemovalTaskRequestController {

	private Project project;
	private Task task;
	private User userToRemove;

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private TaskService taskService;

	public US357CancelRemovalTaskRequestController() {
		//Empty constructor created for JPA integration tests
	}


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

	public User getUserToRemove() {
		return userToRemove;
	}

	public void setUserToRemove(User userToRemove) {
		this.userToRemove = userToRemove;
	}


	/**
	 * This method returns a list of Strings that allows a Project Manager to check
	 * all the pending removal requests of a certain project
	 * 
	 * @return List of strings with the information of the pending removal requests
	 *         from a certain project
	 */
	public List<String> viewPendingRemovalRequests() {

		return taskService.viewAllProjectTaskRemovalRequests(project);
	}

	/**
	 * This method receives a string with the information of a certain removal
	 * request and divides it in order to get the user email and task ID. Then it
	 * sets the parameters user and task from this class with those values.
	 * 
	 * @param requestData
	 *            Information of the request chosen by the user.
	 */
	public void setTaskIDandUserEmailWithRequestString(String requestData) {
		String[] parts = requestData.split("\n");
		String userEmail = parts[1];
		String taskID = parts[2];

		this.userToRemove = userService.getUserByEmail(userEmail);
		this.task = taskService.getTaskByTaskID(taskID);

	}

	/**
	 * This method allows a Project Manager to accept a user request to leave a
	 * task. By accepting, the user gets removed from the task team and the task
	 * request gets deleted from the pending removal request list.
	 * 
	 * @return TRUE if the user removal was successfully done or FALSE if not.
	 */
	public boolean acceptRemovalRequestFromTask() {
		boolean acceptRemovalRequestFromTask = false;
		// Gets the project collaborator correspondent to the user
		ProjectCollaborator projectCollaboratorFromUser = projectService
				.findActiveProjectCollaborator(this.userToRemove, this.project);

		// Removes the project Collaborator correspondent to the user from task.
		//this.task.removeProjectCollaboratorFromTask(projectCollaboratorFromUser);
		// Deletes the request from the pendingRemovalRequestList
		if (task.approveTaskRemovalRequest(projectCollaboratorFromUser)) {
			taskService.saveTask(task);
			acceptRemovalRequestFromTask = true;
		}

		return acceptRemovalRequestFromTask;
	}

	/**
	 * This method allows a Project Manager to cancel a user request to leave a
	 * task. By cancel it, the user does'nt get removed from the task team and the
	 * task request gets deleted from the pending removal request list.
	 * 
	 * @return TRUE if the cancel is successfully done FALSE if not
	 */
	public boolean cancelRemovalRequestFromTask() {
		boolean cancelRemovalRequestFromTask = false;
		// Gets the project collaborator correspondent to the user
		ProjectCollaborator projectCollaboratorFromUser = projectService
				.findActiveProjectCollaborator(this.userToRemove, project);

		if (task.rejectTaskRemovalRequest(projectCollaboratorFromUser)) {
			taskService.saveTask(task);
			cancelRemovalRequestFromTask = true;
		}
		return cancelRemovalRequestFromTask;
	}
}
