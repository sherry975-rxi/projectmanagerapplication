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
	private Task taskToBeMarked;

	public List<Project> getProjectsThatIAmCollaborator(User user) {
		List<Project> projectsThatImProjectCollaborator = new ArrayList<>();
		this.username = user;
		ProjectRepository projectList = Company.getTheInstance().getProjectsRepository();
		projectsThatImProjectCollaborator.addAll(projectList.getProjectsFromUser(this.username));
		return projectsThatImProjectCollaborator;
	}

	public List<Task> getUnfinishedTasksOfProjectFromCollaborator(int projectIndex) {
		ProjectCollaborator collab;
		Project selectedProject;
		List<Task> unfinishedTaskFromProject = new ArrayList<>();
		this.projectIndex = projectIndex;
		selectedProject = Company.getTheInstance().getProjectsRepository().getProjById(this.projectIndex);
		collab = Company.getTheInstance().getProjectsRepository().getProjById(this.projectIndex)
				.getProjectCollaboratorFromUser(this.username);
		unfinishedTaskFromProject = selectedProject.getTaskRepository()
				.getUnfinishedTasksFromProjectCollaborator(collab);
		return unfinishedTaskFromProject;
	}

	public Task getTaskToBeMarkedFinished(String taskIndex1) {
		String taskIndex = taskIndex1;
		List<Task> unfinishedTaskFromProject = getUnfinishedTasksOfProjectFromCollaborator(this.projectIndex);
		taskToBeMarked = Company.getTheInstance().getProjectsRepository().getProjById(this.projectIndex)
				.getTaskRepository().getTaskByID(taskIndex);

		return taskToBeMarked;
	}

	public void markTaskAsFinished() {
		taskToBeMarked.markTaskAsFinished();
	}
}
