/**
 * 
 */
package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.Task;
import project.model.User;

/**
 * @author Group 3
 *
 */
public class US377CollectionOfCancelledTasksFromAProject {
	int projectIDtoInstantiate;

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
	public US377CollectionOfCancelledTasksFromAProject(String projectIDtoInstantiate) {
		this.projectIDtoInstantiate = 0;
	}

	/**
	 * This method returns a set of Projects where a certain user was defined as
	 * Project Manager
	 * 
	 * @param projectManager
	 *            User defined as Project Manager
	 * 
	 * @return List of Projects of a Project Manager
	 */
	public List<Project> getProjectsFromUser(User user) {
		List<Project> listOfProjectsOfProjectManager = new ArrayList<>();
		listOfProjectsOfProjectManager
				.addAll(Company.getTheInstance().getProjectsRepository().getProjectsOfProjectManager(user));
		return listOfProjectsOfProjectManager;
	}

	/**
	 * This method returns the tasks from a specific project
	 * 
	 * @return List of Tasks of the chosen Project
	 */
	public List<Task> getCancelledTasksFromAProject() {

		Project projectToGetTasks = Company.getTheInstance().getProjectsRepository()
				.getProjById(this.projectIDtoInstantiate);

		return projectToGetTasks.getTaskRepository().getCancelledTasks();
	}
}