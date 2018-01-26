/**
 * 
 */
package project.controller;

import project.model.Task;

/**
 * @author Group3
 *
 */
public class US347CancelOnGoingTaskController {

	private Task task;

	/**
	 * Constructor
	 * 
	 */
	public US347CancelOnGoingTaskController() {

	}

	/**
	 * Constructor
	 * 
	 * @param projectIDtoInstantiate
	 */
	public US347CancelOnGoingTaskController(Task task) {
		this.task = task;
	}

	/**
	 * Returns a string with the state of a certain Task
	 * 
	 * @param taskID
	 *            Task to get state info
	 * 
	 * @return task state as a string
	 */
	public String viewTaskState() {
		return this.task.viewTaskStateName();
	}

	/**
	 * This method changes the state of a Task from OnGoing to Cancelled
	 * 
	 * @param taskIDtoSetState
	 *            ID of the task which state is going to be changed from OnGoing to
	 *            Cancelled
	 */
	public boolean cancelOnGoingTask() {

		boolean cancelled = false;

		if (task.getTaskState().changeToCancelled()) {
			task.setCancelDate();
			cancelled = true;
		}
		return cancelled;
	}
}
