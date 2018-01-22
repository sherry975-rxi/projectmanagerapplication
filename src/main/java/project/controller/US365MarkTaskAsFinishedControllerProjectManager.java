package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.User;

public class US365MarkTaskAsFinishedControllerProjectManager {
	private User username;
	private int projectIndex;
	private int taskIndex;
	private Task taskToBeMarked;
	private ProjectRepository projectList;

	public List<Project> getProjectsFromProjectManager(User user) {
		List<Project> projectsThatImProjectManager = new ArrayList<>();
		this.username = user;
		projectList = Company.getTheInstance().getProjectsRepository();
		projectsThatImProjectManager.addAll(projectList.getProjectsFromProjectManager(this.username));
		return projectsThatImProjectManager;
	}

	public List<Task> getUnfinishedTasksOfProjectFromProjectManager(int projectIndex) {
		List<Task> unfinishedTaskFromProject = new ArrayList<>();
		this.projectIndex = projectIndex;
		List<Project> projectsThatImProjectManager = getProjectsFromProjectManager(this.username);
		unfinishedTaskFromProject = projectsThatImProjectManager.get(this.projectIndex).getTaskRepository()
				.getUnFinishedTasks();
		return unfinishedTaskFromProject;
	}

	public Task getTaskToBeMarkedFinished(int taskIndex) {
		this.taskIndex = taskIndex;
		List<Task> unfinishedTaskFromProject = getUnfinishedTasksOfProjectFromProjectManager(this.projectIndex);
		taskToBeMarked = unfinishedTaskFromProject.get(this.taskIndex);
		return taskToBeMarked;
	}

	public void markTaskAsFinished() {
		taskToBeMarked.markTaskAsFinished();
	}
}
