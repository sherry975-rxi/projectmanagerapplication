package project.controller;

import project.Services.ProjectContainerService;
import project.model.*;

import java.util.ArrayList;
import java.util.List;

public class US205MarkTaskAsFinishedCollaborator {
	private User username;
	private int projectIndex;
	private Task taskToBeMarked;
	List<Task> unfinishedTaskFromProject;
	private ProjectContainerService projectContainer;

	public List<Project> getProjectsThatIAmCollaborator(User user) {
		List<Project> projectsThatImProjectCollaborator = new ArrayList<>();
		this.username = user;
		projectContainer = new ProjectContainerService();
		projectsThatImProjectCollaborator.addAll(projectContainer.getProjectsFromUser(this.username));
		return projectsThatImProjectCollaborator;
	}

	public List<Task> getUnfinishedTasksOfProjectFromCollaborator(int projectIndex) {
		ProjectCollaborator collab;
		Project selectedProject;
		this.projectIndex = projectIndex;
		selectedProject = projectContainer.getProjById(this.projectIndex);
		collab = projectContainer.getProjById(this.projectIndex)
				.getProjectCollaboratorFromUser(this.username);
		this.unfinishedTaskFromProject = selectedProject.getTaskRepository()
				.getUnfinishedTasksFromProjectCollaborator(collab);
		return unfinishedTaskFromProject;
	}

	public Task getTaskToBeMarkedFinished(String taskIndex1) {
		String taskIndex = taskIndex1;
		taskToBeMarked = projectContainer.getProjById(this.projectIndex)
				.getTaskRepository().getTaskByID(taskIndex);

		return taskToBeMarked;
	}

	public void markTaskAsFinished() {
		taskToBeMarked.markTaskAsFinished();
	}
}
