package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.model.Project;
import project.model.Task;
import project.services.TaskService;

@Controller
public class US365MarkTaskAsFinishedControllerProjectManager {

	@Autowired
	private TaskService taskService;

	private Task taskToBeMarked;
	private Project selectedProject;


	public US365MarkTaskAsFinishedControllerProjectManager() {
		//Empty constructor created for JPA integration tests

	}

	/*
	 * Getters and Setters
	 */

	public Task getTaskToBeMarked() {
		return taskToBeMarked;
	}

	public void setTaskToBeMarked(String taskID) {
		this.taskToBeMarked = taskService.getTaskByTaskID(taskID);
	}

	public Project getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject;
	}

	public boolean setTaskAsFinished() {
		boolean wasTaskChangedToFinished = taskToBeMarked.markTaskAsFinished();

		if (wasTaskChangedToFinished) {
			taskService.saveTask(this.taskToBeMarked);
		}

		return wasTaskChangedToFinished;
	}


}
