package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.model.Project;
import project.model.Task;
import project.services.TaskService;

import java.util.List;

@Controller
public class US370GetProjectFinishedTaskListController {

	@Autowired
	private TaskService taskService;


	public US370GetProjectFinishedTaskListController() {
		//Empty constructor created for JPA integration tests

	}

	/**
	 * Returns a list of tasks that belong to a Project and were marked as finished.
	 * - US370
	 * 
	 * @param proj
	 *            The project to search for its finished tasks
	 * @return Task List
	 */

	public List<Task> getProjectFinishedTaskList(Project proj) {

		return taskService.getProjectFinishedTasks(proj);

	}

	/**
	 * This method create a list of all tasks finished from project in decreasing
	 * order. First creates a empty list, then add all finished tasks from the
	 * project using method getFinishedTasks. At last, apply the sort by decreasing
	 * order to that list and return it.
	 * 
	 * @param project
	 * @return a list of tasks finished by decreasing order
	 */
	public List<Task> getFinishedTasksInDescreasingOrder(Project project) {

		return taskService.getProjectFinishedTasksInDecreasingOrder(project);
	}

}
