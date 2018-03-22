package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.Services.ProjectService;
import project.Services.TaskService;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;

import java.util.ArrayList;
import java.util.List;

@Controller
public class US205MarkTaskAsFinishedCollaboratorController {
	private User username;
	private int projectIndex;
	private Task taskToBeMarked;
	List<Task> unfinishedTaskFromProject;

	@Autowired
	private ProjectService projectContainer;

	@Autowired
	private TaskService taskService;

	public void setUsername(User username) {
		this.username = username;
	}

	public void setProjectIndex(int projectIndex) {
		this.projectIndex = projectIndex;
	}

	public void setTaskToBeMarked(Task taskToBeMarked) {
		this.taskToBeMarked = taskToBeMarked;
	}

	public void setUnfinishedTaskFromProject(List<Task> unfinishedTaskFromProject) {
		this.unfinishedTaskFromProject = unfinishedTaskFromProject;
	}

	/**
	 * Empty constructor
	 */
	public US205MarkTaskAsFinishedCollaboratorController() {
	};

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
