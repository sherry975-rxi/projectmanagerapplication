package project.controller;

import java.util.List;

import project.model.Project;
import project.model.Task;

public class US380GetProjectExpiredTaskListController {

	/**
	 * Returns a list of tasks that belong to a Project and have expired. - US380
	 * 
	 * @param proj
	 *            The project to search for its expired Tasks
	 * @return Task List
	 */

	public List<Task> getProjectExpiredTaskList(Project proj) {

		return proj.getTaskRepository().getExpiredTasks();

	}

}
