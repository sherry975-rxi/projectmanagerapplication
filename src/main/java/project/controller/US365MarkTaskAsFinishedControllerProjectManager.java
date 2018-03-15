package project.controller;

import project.Services.ProjectService;
import project.Services.TaskService;
import project.model.Project;
import project.model.Task;

public class US365MarkTaskAsFinishedControllerProjectManager {
	private ProjectService projContainer;
	private Task taskToBeMarked;
	private TaskService projectTaskList;
	private Project selectedProject;

	public US365MarkTaskAsFinishedControllerProjectManager(String taskID, Project selectedProject) {
		this.selectedProject=selectedProject;
		this.projectTaskList = selectedProject.getTaskRepository();
		this.taskToBeMarked = projectTaskList.getTaskByID(taskID);
	}

	public boolean setTaskAsFinished() {
		boolean wasTaskChangedToFinished = taskToBeMarked.markTaskAsFinished();

		if (wasTaskChangedToFinished) {
			projContainer.saveProjectInRepository(selectedProject);
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
