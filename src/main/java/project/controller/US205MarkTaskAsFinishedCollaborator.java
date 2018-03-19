package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import project.Services.ProjectService;
import project.Services.TaskService;
import project.model.*;

import java.util.ArrayList;
import java.util.List;

public class US205MarkTaskAsFinishedCollaborator {
	private User username;
	private int projectIndex;
	private Task taskToBeMarked;
	List<Task> unfinishedTaskFromProject;

	@Autowired
	private ProjectService projectContainer;

	@Autowired
	private TaskService taskService;

	public List<Project> getProjectsThatIAmCollaborator(User user) {
		List<Project> projectsThatImProjectCollaborator = new ArrayList<>();
		this.username = user;

		projectsThatImProjectCollaborator.addAll(projectContainer.getProjectsFromUser(this.username));
		return projectsThatImProjectCollaborator;
	}

	public List<Task> getUnfinishedTasksOfProjectFromCollaborator(int projectIndex) {
		ProjectCollaborator collab;
		Project selectedProject;
		this.projectIndex = projectIndex;

		selectedProject = findProjectByID();
		collab = projectContainer.findActiveProjectCollaborator(this.username, selectedProject);

		this.unfinishedTaskFromProject = taskService.getUnfinishedTasksFromProjectCollaborator(collab);
		return unfinishedTaskFromProject;
	}

	public Task getTaskToBeMarkedFinished(String taskIndex1) {
		String taskIndex = taskIndex1;
		taskToBeMarked = taskService.getTaskByTaskID(taskIndex);

		return taskToBeMarked;
	}

	public void markTaskAsFinished() {
		taskToBeMarked.markTaskAsFinished();
	}

	private Project findProjectByID() {
		return projectContainer.getProjectById(this.projectIndex);
	}
}
