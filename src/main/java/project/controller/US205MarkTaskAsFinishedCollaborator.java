package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.User;

public class US205MarkTaskAsFinishedCollaborator {
	private User username;
	private int projectIndex;
	private int taskIndex;
	private Task taskToBeMarked;
	private ProjectRepository projectList;
	private ProjectCollaborator collab;

	public List<Project> getProjectsThatIAmCollaborator(User user) {
		List<Project> projectsThatImProjectCollaborator = new ArrayList<>();
		this.username = user;
		projectList = Company.getTheInstance().getProjectsRepository();
		projectsThatImProjectCollaborator.addAll(projectList.getProjectsFromUser(this.username));
		return projectsThatImProjectCollaborator;
	}

	public List<Task> getUnfinishedTasksOfProjectFromCollaboratorr(int projectIndex) {
		List<Task> unfinishedTaskFromProject = new ArrayList<>();
		this.projectIndex = projectIndex;
		List<Project> projectsThatImProjectCollaborator = getProjectsThatIAmCollaborator(this.username);
		this.collab = projectsThatImProjectCollaborator.get(this.projectIndex)
				.getProjectCollaboratorFromUser(this.username);
		unfinishedTaskFromProject = projectsThatImProjectCollaborator.get(this.projectIndex).getTaskRepository()
				.getUnfinishedTasksFromProjectCollaborator(this.collab);
		return unfinishedTaskFromProject;
	}

	public Task getTaskToBeMarkedFinished(int taskIndex) {
		this.taskIndex = taskIndex;
		List<Task> unfinishedTaskFromProject = getUnfinishedTasksOfProjectFromCollaboratorr(this.projectIndex);
		taskToBeMarked = unfinishedTaskFromProject.get(this.taskIndex);
		return taskToBeMarked;
	}

	public void markTaskAsFinished() {
		taskToBeMarked.markTaskAsFinished();
	}
}
