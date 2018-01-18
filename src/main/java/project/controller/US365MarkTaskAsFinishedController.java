package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.User;

public class US365MarkTaskAsFinishedController {
	private User username;
	private int projectIndex;
	private int taskIndex;
	private Task taskToBeMarked;
	private ProjectRepository projectList;

	public List<Project> getProjectsFromProjectCollaborator(User user) {
		List<Project> projectsThatImProjectCollaborator = new ArrayList<>();
		this.username = user;
		projectList = Company.getTheInstance().getProjectsRepository();
		projectsThatImProjectCollaborator.addAll(projectList.getProjectsFromProjectManager(this.username));
		return projectsThatImProjectCollaborator;
	}

	public List<Task> getUnfinishedTasksOfProjectFromProjectCollaborator(int projectIndex) {
		List<Task> unfinishedTaskFromProject = new ArrayList<>();
		this.projectIndex = projectIndex;
		List<Project> projectsThatImProjectCollaborator = getProjectsFromProjectCollaborator(this.username);
		unfinishedTaskFromProject = projectsThatImProjectCollaborator.get(this.projectIndex).getTaskRepository()
				.getUnFinishedTasks();
		return unfinishedTaskFromProject;
	}

	public Task getTaskToBeMarkedFinished(int taskIndex) {
		this.taskIndex = taskIndex;
		List<Task> unfinishedTaskFromProject = getUnfinishedTasksOfProjectFromProjectCollaborator(this.projectIndex);
		taskToBeMarked = unfinishedTaskFromProject.get(this.taskIndex);
		return taskToBeMarked;
	}

	public void markTaskAsFinished() {
		taskToBeMarked.markTaskAsFinished();
	}
}
