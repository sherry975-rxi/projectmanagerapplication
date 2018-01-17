/**
 * 
 */
package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskRepository;
import project.model.User;

/**
 * @author Group 3
 *
 */
public class US377CollectionOfCancelledTasksFromAProject {
	private int projectIDtoInstantiate = 0;
	private TaskRepository taskRepository;
	private ProjectRepository projectRepository;
	private Project project;
	private Company company;

	/**
	 * Constructor
	 * 
	 */
	public US377CollectionOfCancelledTasksFromAProject() {
	}

	/**
	 * Constructor
	 * 
	 * @param projectIDtoInstantiate
	 */
	public US377CollectionOfCancelledTasksFromAProject(int projectIDtoInstantiate) {
		this.projectIDtoInstantiate = projectIDtoInstantiate;
		company = Company.getTheInstance();
		projectRepository = company.getProjectsRepository();
		project = projectRepository.getProjById(projectIDtoInstantiate);
		taskRepository = project.getTaskRepository();
	}

	/**
	 * This method returns a set of Projects where a certain user
	 * 
	 * @param user
	 * 
	 * @return List of Projects of User
	 */
	public List<Project> getProjectsFromUser(User user) {
		List<Project> listOfProjectsOfProjectManager = new ArrayList<>();

		listOfProjectsOfProjectManager.addAll(projectRepository.getProjectsFromUser(user));

		return listOfProjectsOfProjectManager;
	}

	/**
	 * This method returns the tasks from a specific project
	 * 
	 * @return List of Tasks of the chosen Project
	 */
	public List<Task> getCancelledTasksFromAProject() {

		Project projectToGetTasks = projectRepository.getProjById(this.projectIDtoInstantiate);

		return projectToGetTasks.getTaskRepository().getCancelledTasks();
	}
}