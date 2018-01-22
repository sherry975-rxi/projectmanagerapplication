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
	private String taskIndex;
	private Task taskToBeMarked;
	private ProjectRepository projectList;
	private ProjectCollaborator collab;
	private Project selectedProject;

	public List<Project> getProjectsThatIAmCollaborator(User user) {
		List<Project> projectsThatImProjectCollaborator = new ArrayList<>();
		this.username = user;
		projectList = Company.getTheInstance().getProjectsRepository();
		projectsThatImProjectCollaborator.addAll(projectList.getProjectsFromUser(this.username));
		return projectsThatImProjectCollaborator;
	}

	public List<Task> getUnfinishedTasksOfProjectFromCollaborator(int projectIndex) {
		List<Task> unfinishedTaskFromProject = new ArrayList<>();
		this.projectIndex = projectIndex;
		selectedProject = Company.getTheInstance().getProjectsRepository().getProjById(this.projectIndex);
		this.collab = Company.getTheInstance().getProjectsRepository().getProjById(this.projectIndex)
				.getProjectCollaboratorFromUser(this.username);
		unfinishedTaskFromProject = selectedProject.getTaskRepository()
				.getUnfinishedTasksFromProjectCollaborator(this.collab);
		return unfinishedTaskFromProject;
	}

	public Task getTaskToBeMarkedFinished(String taskIndex) {
		this.taskIndex = taskIndex;
		List<Task> unfinishedTaskFromProject = getUnfinishedTasksOfProjectFromCollaborator(this.projectIndex);
		taskToBeMarked = Company.getTheInstance().getProjectsRepository().getProjById(this.projectIndex)
				.getTaskRepository().getTaskByID(this.taskIndex);

		return taskToBeMarked;
	}

	public void markTaskAsFinished() {
		taskToBeMarked.markTaskAsFinished();
	}
}
