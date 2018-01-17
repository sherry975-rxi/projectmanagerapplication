/**
 * 
 */
package project.controller;

import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskRepository;

/**
 * @author Group 3
 *
 */
public class US377CollectionOfCancelledTasksFromAProject {
	private int projectIDtoInstantiate;
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
	 * This method returns the tasks from a specific project
	 * 
	 * @return List of Tasks of the chosen Project
	 */
	public List<Task> getCancelledTasksFromAProject() {

		Project projectToGetTasks = projectRepository.getProjById(this.projectIDtoInstantiate);

		return projectToGetTasks.getTaskRepository().getCancelledTasks();
	}
}