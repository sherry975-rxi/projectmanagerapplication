/**
 * 
 */
package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskRepository;

/**
 * @author Group 3
 *
 */
public class US377CollectionOfCancelledTasksFromAProject {
	private int projectIDtoInstantiate;
	private TaskRepository taskRepository;
	private ProjectRepository projectRepository;
	private Project project;
	private Company company;

	/**
	 * Constructor
	 * 
	 * @param projectIDtoInstantiate
	 */
	public US377CollectionOfCancelledTasksFromAProject(Project project) {
		this.project = project;
		taskRepository = project.getTaskRepository();
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

		List<Task> taskListOnGoing = proj.getTaskRepository().getCancelledTasks();
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