package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Project;
import project.model.Task;

/**
 * @author Group 3
 *
 */
public class US377CollectionOfCancelledTasksFromAProjectController {
	private Project project;

	/**
	 * Constructor
	 * 
	 * @param project Project ID of the project with which this controller will work
	 */
	public US377CollectionOfCancelledTasksFromAProjectController(Project project) {
		this.project = project;
	}

	/**
	 * this method return the list of cancelled tasks
	 * 
	 * @param proj
     *
	 * @return
	 */
	public List<Task> getProjectCancelledTasks(Project proj) {
		return proj.getTaskRepository().getCancelledTasks();
	}

	/**
	 * This method returns the tasks from a specific project
	 * 
	 * @return List of Tasks of the chosen Project
	 */
	public List<Task> getCancelledTasksFromAProject() {

		return project.getTaskRepository().getCancelledTasks();
	}

	/**
	 * This methods gets all the cancelled tasks in the form of a List of Strings,
	 * with the taskId and Description of each task.
	 * 
	 * @param proj
	 *            The project to search for cancelled tasks
	 * 
	 * @return Task List
	 */
	public List<String> getCancelledTaskListId(Project proj) {

		List<Task> taskListCanceleld = proj.getTaskRepository().getCancelledTasks();
		List<String> taskCanceleldListToPrint = new ArrayList<>();

		for (int i = 0; i < taskListCanceleld.size(); i++) {

			String taskDescription = taskListCanceleld.get(i).getDescription();
			String taskID = "[" + taskListCanceleld.get(i).getTaskID() + "]";
			String taskIDandDescription = taskID + " " + taskDescription;
			taskCanceleldListToPrint.add(taskIDandDescription);
		}

		return taskCanceleldListToPrint;
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