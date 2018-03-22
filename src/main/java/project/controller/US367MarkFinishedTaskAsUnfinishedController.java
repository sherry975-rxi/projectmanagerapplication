package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.Services.TaskService;
import project.model.Project;
import project.model.Task;

@Controller
public class US367MarkFinishedTaskAsUnfinishedController {

	@Autowired
	private TaskService taskService;

	private Project project;
	private String taskID;

	/*
	 * Default constructor
	 */
	public US367MarkFinishedTaskAsUnfinishedController() {

	}

	/*
	 * Getters and Setters
	 */

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getTaskID() {
		return taskID;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	/**
	 * This constructor receives a Project and a Task's ID, storing these values and
	 * allowing them to be used when removing a task's finished state
	 *
	 * @param proj
	 * @param idTask
	 */
	public US367MarkFinishedTaskAsUnfinishedController(Project proj, String idTask) {
		this.project = proj;
		this.taskID = idTask;

	}

	/**
	 *
	 * If this option is selected by the UI, it removes the selected task's finished
	 * state and returns it to StandBy state
	 *
	 */

	public void markFinishedTaskAsUnfinished() {
		Task task;
		task = taskService.getTaskByTaskID(this.taskID);
		task.removeFinishDate();
		taskService.saveTask(task);

	}

}
