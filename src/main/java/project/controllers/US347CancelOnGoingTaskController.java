/**
 * 
 */
package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.model.Project;
import project.model.Task;
import project.services.TaskService;

/**
 * @author Group3
 *
 */
@Controller
public class US347CancelOnGoingTaskController {

	@Autowired
	private TaskService taskService;

	private String taskID;
	private Project project;

	public String getTaskID() {
		return taskID;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
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
