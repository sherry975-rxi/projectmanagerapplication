package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.model.Project;
import project.model.Task;
import project.services.TaskService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Group 3
 *
 */
@Controller
public class US377CollectionOfCancelledTasksFromAProjectController {

	private Project project;

	@Autowired
	private TaskService taskService;


	public US377CollectionOfCancelledTasksFromAProjectController() {
		//Empty constructor created for JPA integration tests

	}

	/**
	 * Constructor
	 *
	 * @param project
	 *            Project ID of the project with which this controllers will work
	 */
	public US377CollectionOfCancelledTasksFromAProjectController(Project project) {
		this.project = project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * This method returns the tasks from a specific project
	 * 
	 * @return List of Tasks of the chosen Project
	 */
	public List<Task> getCancelledTasksFromAProject() {

		return taskService.getProjectCancelledTasks(this.project);
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

		List<Task> taskListCancelled = taskService.getProjectCancelledTasks(proj);
		List<String> taskCancelledListToPrint = new ArrayList<>();

		for (int i = 0; i < taskListCancelled.size(); i++) {

			String cancelledTaskDescription = taskListCancelled.get(i).getDescription();
			String cancelledTaskID = "[" + taskListCancelled.get(i).getTaskID() + "]";
			String cancelledTaskIDandDescription = cancelledTaskID + " " + cancelledTaskDescription;
			taskCancelledListToPrint.add(cancelledTaskIDandDescription);
		}

		return taskCancelledListToPrint;
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