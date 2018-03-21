package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import project.Services.TaskService;
import project.model.Project;
import project.model.Task;

@Controller
public class US365MarkTaskAsFinishedControllerProjectManager {

	@Autowired
	private TaskService projectTaskList;
	private Task taskToBeMarked;
	private Project selectedProject;

	/*
	 * Default constructor
	 */

	public US365MarkTaskAsFinishedControllerProjectManager() {

	}

	/*
	 * Getters and Setters
	 */

	public Task getTaskToBeMarked() {
		return taskToBeMarked;
	}

	public void setTaskToBeMarked(String taskID) {
		this.taskToBeMarked = projectTaskList.getTaskByTaskID(taskID);
	}

	public Project getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject;
	}

	public US365MarkTaskAsFinishedControllerProjectManager(String taskID, Project selectedProject) {
		this.selectedProject = selectedProject;
		this.taskToBeMarked = projectTaskList.getTaskByTaskID(taskID);
	}

	public boolean setTaskAsFinished() {
		boolean wasTaskChangedToFinished = taskToBeMarked.markTaskAsFinished();

		if (wasTaskChangedToFinished) {
			projectTaskList.saveTask(this.taskToBeMarked);
		}

		return wasTaskChangedToFinished;
	}

	/**
	 * 
	 * This methods are unnecessary in this controller, but they will be in a
	 * different one
	 * 
	 * 
	 * 
	 * public List<Project> getProjectsFromProjectManager(User user) { List<Project>
	 * projectsThatImProjectManager = new ArrayList<>(); this.username = user;
	 * projectList = Company.getTheInstance().getProjectsContainer();
	 * projectsThatImProjectManager.addAll(projectList.getProjectsFromProjectManager(this.username));
	 * return projectsThatImProjectManager; }
	 *
	 *
	 * 
	 * public List<Task> getUnfinishedTasksOfProjectFromProjectManager(int
	 * projectIndex) { List<Task> unfinishedTaskFromProject = new ArrayList<>();
	 * this.projectIndex = projectIndex; List<Project> projectsThatImProjectManager
	 * = getProjectsFromProjectManager(this.username); unfinishedTaskFromProject =
	 * projectsThatImProjectManager.get(this.projectIndex).getTaskRepository()
	 * .getUnFinishedTasks(); return unfinishedTaskFromProject; }
	 *
	 *
	 * public Task getTaskToBeMarkedFinished(int taskIndex) { this.taskIndex =
	 * taskIndex; taskToBeMarked = unfinishedTaskFromProject.get(this.taskIndex);
	 * return taskToBeMarked; }
	 *
	 */

}
