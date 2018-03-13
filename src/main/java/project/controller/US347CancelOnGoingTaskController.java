/**
 * 
 */
package project.controller;

import project.Services.ProjectContainerService;
import project.model.Project;
import project.model.Task;

/**
 * @author Group3
 *
 */
public class US347CancelOnGoingTaskController {

	private String taskID;
	private Project project;

	/**
	 * Constructor
	 * 
	 * @param taskID task id to give to the task
	 * @param project project in which to create the task
	 */
	public US347CancelOnGoingTaskController(String taskID, Project project) {
		this.taskID = taskID;
		this.project = project;
	}

	/**
	 * Returns a string with the state of a certain Task

	 * @return task state as a string
	 */
	public String viewTaskState() {

		Task taskToGetByID = this.project.getTaskRepository().getTaskByID(taskID);

		return taskToGetByID.viewTaskStateName();
	}

	/**
	 * This method changes the state of a Task from OnGoing to Cancelledz
	 */
	public boolean cancelOnGoingTask() {

		ProjectContainerService projContainer = new ProjectContainerService();
		Task task = this.project.getTaskRepository().getTaskByID(taskID);
		boolean result = task.cancelTask();
		projContainer.saveProjectInRepository(project);
		return result;
	}
}
