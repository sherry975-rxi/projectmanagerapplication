package project.controller;

import java.util.List;

import project.model.Project;
import project.model.Task;

public class US372GetProjectUnfinishedTaskListController {

	/**
	 * Returns a list of tasks that belong to a Project and are not marked as
	 * finished. - US372
	 * 
	 * @param proj
	 *            The project to search for its unfinished tasks
	 * @return Task List
	 */

	public List<Task> getProjectUnfinishedTaskList(Project proj) {

		return proj.getTaskRepository().getUnFinishedTasks();

	}

}
