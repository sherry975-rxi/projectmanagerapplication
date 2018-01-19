package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
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
public class US206V2RemovalTaskRequestController {

	private Integer projectID;
	private String taskID;
	private User user;

	/**
	 * Constructor to instantiate a new CollaboratorRemovalrequest
	 * 
	 * @param user
	 *            User that asks for the removal
	 * @param projectID
	 *            Project ID of project where the task is
	 * @param taskID
	 *            Task ID of the task that the user wants to be removed from
	 */
	public US206V2RemovalTaskRequestController(User user) {
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

		Project project = Company.getTheInstance().getProjectsRepository().getProjById(this.projectID);
		Task taskBeRemovedOf = project.getTaskRepository().getTaskByID(this.taskID);
		ProjectCollaborator projectCollaborator = project.findProjectCollaborator(this.user);

		if (taskBeRemovedOf != null && !project.isRemovalRequestAlreadyCreated(projectCollaborator, taskBeRemovedOf)) {

			project.createTaskRemovalRequest(projectCollaborator, taskBeRemovedOf);
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

		List<Task> usersTask = Company.getTheInstance().getProjectsRepository().getUnfinishedUserTaskList(user);
		List<String> userTaskDetails = new ArrayList<>();

		for (int i = 0; i < usersTask.size(); i++) {

			String taskDescription = usersTask.get(i).getDescription();
			String taskID = "[" + usersTask.get(i).getTaskID() + "]";
			String taskIDandDescription = taskID + " " + taskDescription;
			userTaskDetails.add(taskIDandDescription);
		}

		return userTaskDetails;
	}

	/**
	 * This method gets the ProjectID by spliting the taskID in two.
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
