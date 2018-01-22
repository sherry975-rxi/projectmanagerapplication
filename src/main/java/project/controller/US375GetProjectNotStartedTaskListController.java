package project.controller;

import java.util.List;

import project.model.Project;
import project.model.Task;

public class US375GetProjectNotStartedTaskListController {

	/**
	 * Returns a list of tasks that belong to a Project and have not been started. -
	 * US375
	 * 
	 * @param proj
	 *            The project to search for its unstarted Tasks
	 * @return Task List
	 */

	public List<Task> getProjectNotStartedTaskList(Project proj) {

		return proj.getTaskRepository().getUnstartedTasks();

	}
}
