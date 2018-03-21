package project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import project.Services.TaskService;
import project.model.Project;
import project.model.Task;

@Controller
public class US372GetProjectUnfinishedTaskListController {

	@Autowired
	private TaskService taskService;

	/*
	 * Default constructor
	 */
	public US372GetProjectUnfinishedTaskListController() {

	}

	/**
	 * Returns a list of tasks that belong to a Project and are not marked as
	 * finished. - US372
	 * 
	 * @param proj
	 *            The project to search for its unfinished tasks
	 * @return Task List
	 */

	public List<Task> getProjectUnfinishedTaskList(Project proj) {

		return taskService.getProjectUnFinishedTasks(proj);

	}

	/**
	 * This methods gets all the onGoing tasks in the form of a List of Strings,
	 * with the taskId and Description of each task.
	 * 
	 * @param proj
	 *            The project to search for its OnGoing tasks
	 * 
	 * @return Task List
	 */
	public List<String> getUnfinishedTaskListId(Project proj) {

		List<Task> taskListOnGoing = taskService.getProjectUnFinishedTasks(proj);
		List<String> taskListOnGoingToPrint = new ArrayList<>();

		for (int i = 0; i < taskListOnGoing.size(); i++) {

			String taskOnGoingDescription = taskListOnGoing.get(i).getDescription();
			String taskID = taskListOnGoing.get(i).getTaskID();
			String taskIDandDescription = "[" + taskID + "]" + " " + taskOnGoingDescription;
			taskListOnGoingToPrint.add(taskIDandDescription);
		}

		return taskListOnGoingToPrint;
	}

	/**
	 * This method splits a Sting by the space and only return the left part of the
	 * string until the first space
	 * 
	 * @param string
	 *            String to split
	 */
	public String splitStringByFirstSpace(String string) {

		String[] partsCancelledTask = string.split(" ");

		return partsCancelledTask[0];
	}

}
