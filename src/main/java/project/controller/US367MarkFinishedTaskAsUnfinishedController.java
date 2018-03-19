package project.controller;

import project.Services.ProjectService;
import project.Services.TaskService;
import project.model.Project;
import project.model.Task;

public class US367MarkFinishedTaskAsUnfinishedController {

	private Project project;
	private String taskID;
	TaskService taskService;

	/**
	 * This constructor receives a Project and a Task's ID, storing these values and allowing them to be used when removing a task's finished state
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
	 * If this option is selected by the UI, it removes the selected task's finished state and returns it to StandBy state
	 *
	 */

	public void markFinishedTaskAsUnfinished() {
		Task task;
		task = taskService.getTaskByTaskID(this.taskID);
		task.removeFinishDate();
		taskService.saveTask(task);

	}

}
