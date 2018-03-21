/**
 * 
 */
package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import project.Services.TaskService;
import project.model.Project;
import project.model.Task;

/**
 * @author Group3
 *
 */
@Controller
public class US347CancelOnGoingTaskController {

	private String taskID;
	private Project project;

	@Autowired
	public TaskService taskService;

	/**
	 * Constructor
	 * 
	 * @param taskID
	 *            task id to give to the task
	 * @param project
	 *            project in which to create the task
	 */
	public US347CancelOnGoingTaskController(String taskID, Project project) {
		this.taskID = taskID;
		this.project = project;
	}

	/**
	 * Returns a string with the state of a certain Task
	 * 
	 * @return task state as a string
	 */
	public String viewTaskState() {

		Task taskToGetByID = taskService.getTaskByTaskID(taskID);

		return taskToGetByID.viewTaskStateName();
	}

	/**
	 * This method changes the state of a Task from OnGoing to Cancelledz
	 */
	public boolean cancelOnGoingTask() {

		Task task = taskService.getTaskByTaskID(taskID);
		boolean result = task.cancelTask();
		taskService.saveTask(task);
		return result;
	}
}
