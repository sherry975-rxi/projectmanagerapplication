package project.controller;

import project.model.Task;

public class MarkTaskAsFinishedController {

	/**
	 * This controller lets an user mark a given task as finished
	 * 
	 */

	public void markTaskAsFinishedController(Task task) {
		task.markTaskAsFinished();

	}

}
