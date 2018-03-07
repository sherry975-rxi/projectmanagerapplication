package project.controller;

import project.model.*;

import java.util.ArrayList;
import java.util.List;

public class US205MarkTaskAsFinishedCollaborator {
	private User username;
	private int projectIndex;
	private Task taskToBeMarked;
	List<Task> unfinishedTaskFromProject;

	public List<Project> getProjectsThatIAmCollaborator(User user) {
		List<Project> projectsThatImProjectCollaborator = new ArrayList<>();
		this.username = user;
		ProjectContainer projectList = Company.getTheInstance().getProjectsContainer();
		projectsThatImProjectCollaborator.addAll(projectList.getProjectsFromUser(this.username));
		return projectsThatImProjectCollaborator;
	}

	public List<Task> getUnfinishedTasksOfProjectFromCollaborator(int projectIndex) {
		ProjectCollaborator collab;
		Project selectedProject;
		this.projectIndex = projectIndex;
		selectedProject = Company.getTheInstance().getProjectsContainer().getProjById(this.projectIndex);
		collab = Company.getTheInstance().getProjectsContainer().getProjById(this.projectIndex)
				.getProjectCollaboratorFromUser(this.username);
		this.unfinishedTaskFromProject = selectedProject.getTaskRepository()
				.getUnfinishedTasksFromProjectCollaborator(collab);
		return unfinishedTaskFromProject;
	}

	public Task getTaskToBeMarkedFinished(String taskIndex1) {
		String taskIndex = taskIndex1;
		taskToBeMarked = Company.getTheInstance().getProjectsContainer().getProjById(this.projectIndex)
				.getTaskRepository().getTaskByID(taskIndex);

		return taskToBeMarked;
	}

	public void markTaskAsFinished() {
		taskToBeMarked.markTaskAsFinished();
	}
}
