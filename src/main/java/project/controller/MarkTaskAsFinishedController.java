package project.controller;

import project.model.Task;

public class MarkTaskAsFinishedController {

	/**
	 * This controller lets an user mark a given task as finished
	 * 
	 */

	/**
	 * @param task
	 *            The task that will be marked as finished
	 */
	public void markTaskAsFinishedController(Task task) {
		task.markTaskAsFinished();

	}

}
