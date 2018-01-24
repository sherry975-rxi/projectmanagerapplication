package project.controller;

import java.util.ArrayList;
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

	/**
	 * This methods gets all the unfinished tasks with expired deadline and returns
	 * the Tasks with these conditions in the form of a List of Strings, with the
	 * taskId and Description of each task.
	 * 
	 * @param proj
	 *            The project to search for its expired Tasks
	 * 
	 * @return Task List
	 */
	public List<String> getOnGoingTaskListId(Project proj) {

		List<Task> taskListOnGoing = proj.getTaskRepository().getOnGoingTasks();
		List<String> taskListToPrint = new ArrayList<>();

		for (int i = 0; i < taskListOnGoing.size(); i++) {

			String taskDescription = taskListOnGoing.get(i).getDescription();
			String taskID = "[" + taskListOnGoing.get(i).getTaskID() + "]";
			String taskIDandDescription = taskID + " " + taskDescription;
			taskListToPrint.add(taskIDandDescription);
		}

		return taskListToPrint;
	}

	/**
	 * This method splits a Sting by the space and only return the left part of the
	 * string until the first space
	 * 
	 * @param string
	 *            String to split
	 */
	public String splitStringByFirstSpace(String string) {

		String[] partsTask = string.split(" ");
		String firstPartOfString = partsTask[0];

		return firstPartOfString;
	}

}
