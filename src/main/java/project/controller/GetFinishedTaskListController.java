package project.controller;

import java.util.List;

import project.model.Project;
import project.model.Task;;

public class GetFinishedTaskListController {

	/**
	 * @param p1
	 *            The project to search for it's finished tasks
	 * @return This method returns a list of tasks that belong to p1 and were marked
	 *         as finished.
	 */

	public List<Task> getFinishedTaskListController(Project p1) {

		return p1.getTaskRepository().getFinishedTasks();

	}

}
