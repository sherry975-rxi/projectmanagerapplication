package project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import project.Services.ProjectService;
import project.Services.TaskService;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;

/**
 * @author Group 3
 * 
 *         This Class responds to the USComo colaborador, eu pretendo remover
 *         uma tarefa que consta na minha lista de tarefas.
 *
 */
@Controller
public class US206RemovalTaskRequestController {

	private Integer projectID;
	private String taskID;
	private User user;

	@Autowired
	public ProjectService projectContainer;

	@Autowired
	public TaskService taskService;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setProjectID(Integer projectID) {
		this.projectID = projectID;
	}
	
	public US206RemovalTaskRequestController() {
		
	}
		
	/**
	 * Constructor to instantiate a new CollaboratorRemovalrequest
	 * 
	 * @param user
	 *            User that asks for the removal
	 */
	public US206RemovalTaskRequestController(User user) {
		this.user = user;
		this.projectID = null;
		this.taskID = null;

	}

	/**
	 * This method instantiates a new removalRequest and adds it to the
	 * removalRequestList of a certain project.
	 * 
	 */
	public boolean createRequest() {

		boolean createdSucess = false;

		Project project = projectContainer.getProjectById(this.projectID);
		Task taskBeRemovedOf = taskService.getTaskByTaskID(this.taskID);
		ProjectCollaborator projectCollaborator = projectContainer.findActiveProjectCollaborator(this.user, project);

		if (taskBeRemovedOf != null && !taskBeRemovedOf.isRemovalRequestAlreadyCreated(projectCollaborator)) {

			taskBeRemovedOf.createTaskRemovalRequest(projectCollaborator);
			createdSucess = true;
		}

		return createdSucess;
	}

	/**
	 * This methods gets all the unfinished tasks from a certain user and returns
	 * that list in a form of a List of Strings, with the taskId and Description of
	 * each task.
	 * 
	 * @return
	 */
	public List<String> getUnfinishedTaskListFromUser() {

		List<Task> usersTask = taskService.getUnfinishedUserTaskList(user);
		List<String> userTaskDetails = new ArrayList<>();

		for (int i = 0; i < usersTask.size(); i++) {

			String taskDescription = usersTask.get(i).getDescription();
			String taskIDCode = "[" + usersTask.get(i).getTaskID() + "]";
			String taskIDandDescription = taskIDCode + " " + taskDescription;
			userTaskDetails.add(taskIDandDescription);
		}

		return userTaskDetails;
	}

	/**
	 * This method gets the ProjectID by spliting the taskID in two.
	 * 
	 * @param taskID
	 *            String with the taskID
	 */
	public void setProjectIDFromTaskID(String taskID) {

		String[] partsTask = taskID.split("\\.");
		String projectIDCode = partsTask[0];

		setProjectID(Integer.parseInt(projectIDCode));
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
	 * This method sets the ptaskID directly from an String received
	 * 
	 * @param taskID
	 *            TaskID to set
	 */
	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	/**
	 * @return Task ID
	 */
	public String getTaskID() {
		return this.taskID;
	}

	/**
	 * @return projectId
	 */
	public Integer getProjectID() {
		return this.projectID;
	}

}
