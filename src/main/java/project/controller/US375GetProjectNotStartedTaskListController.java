package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Project;
import project.model.Task;

public class US375GetProjectNotStartedTaskListController {

	/**
	 * This methods gets all the not started task and returns the Tasks with these
	 * conditions in the form of a List of Strings, with the taskId and Description
	 * of each task.
	 * 
	 * @param proj
	 *            The project to search for not started Tasks
	 * 
	 * @return Task List
	 */
	public List<String> getProjectNotStartedTaskList(Project proj) {

		List<Task> taskListNotStarted = proj.getTaskRepository().getUnstartedTasks();
		List<String> taskListToPrint = new ArrayList<>();

		for (int i = 0; i < taskListNotStarted.size(); i++) {

			String taskDescription = taskListNotStarted.get(i).getDescription();
			String taskID = "[" + taskListNotStarted.get(i).getTaskID() + "]";
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
