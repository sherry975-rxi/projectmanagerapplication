package project.controller;

import project.Services.ProjectService;
import project.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Group 3
 * 
 *         This Class responds to the USComo colaborador, eu pretendo remover
 *         uma tarefa que consta na minha lista de tarefas.
 *
 */
public class US206RemovalTaskRequestController {

	private Integer projectID;
	private String taskID;
	private User user;
	private ProjectService projectContainer;
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
		this.projectContainer = new ProjectService();
	}

	/**
	 * This method instantiates a new removalRequest and adds it to the
	 * removalRequestList of a certain project.
	 * 
	 */
	public boolean createRequest() {

		boolean createdSucess = false;

		Project project = projectContainer.getProjById(this.projectID);
		Task taskBeRemovedOf = project.getTaskRepository().getTaskByID(this.taskID);
		ProjectCollaborator projectCollaborator = project.findProjectCollaborator(this.user);

		if (taskBeRemovedOf != null && !project.isRemovalRequestAlreadyCreated(projectCollaborator, taskBeRemovedOf)) {

			project.createTaskRemovalRequest(projectCollaborator, taskBeRemovedOf);
			createdSucess = true;
		}
		projectContainer.updateProjectContainer();
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

		List<Task> usersTask = projectContainer.getUnfinishedUserTaskList(user);
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
