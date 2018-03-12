/**
 * 
 */
package project.controller;

import project.model.Project;
import project.model.ProjectContainer;
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
	 * @param taskID
	 * @param project
	 */
	public US347CancelOnGoingTaskController(String taskID, Project project) {
		this.taskID = taskID;
		this.project = project;
	}

	/**
	 * Returns a string with the state of a certain Task
	 * 
	 * @return task state as a string
	 */
	public String viewTaskState() {

		Task taskToGetByID = this.project.getTaskRepository().getTaskByID(taskID);

		return taskToGetByID.viewTaskStateName();
	}

	/**
	 * This method changes the state of a Task from OnGoing to Cancelled
	 *
	 *            ID of the task which state is going to be changed from OnGoing to
	 *            Cancelled
	 */
	public boolean cancelOnGoingTask() {
		ProjectContainer projectContainer = new ProjectContainer();
		Task task = this.project.getTaskRepository().getTaskByID(taskID);

		boolean cancelled = false;
		task.setCancelDate();
		if (task.getTaskState().changeToCancelled()) {
			cancelled = true;
			projectContainer.addProjectToProjectContainerX(this.project);
		} else {
			task.cancelledDateClear();
		}
		return cancelled;
	}
}
