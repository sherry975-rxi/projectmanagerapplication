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
 *         This class implements the controller correspondent to the
 *         createDependenceFromTask functionality.
 *
 */
public class US342Controller {

	/**
	 * This method returns a set of Projects where a certain user was defined as
	 * Project Manager
	 * 
	 * @param projectManager
	 *            User defined as Project Manager
	 * 
	 * @return List of Projects of a Project Manager
	 */
	public List<Project> getProjectsFromUser(User projectManager) {

		List<Project> listOfProjectsOfProjectManager = new ArrayList<>();

		listOfProjectsOfProjectManager
				.addAll(Company.getTheInstance().getProjectsRepository().getProjectsOfProjectManager(projectManager));

		return listOfProjectsOfProjectManager;
	}

	/**
	 * This method returns the tasks from a specific project
	 * 
	 * @param projectToGetTasksFrom
	 *            Project to get the tasks from
	 * 
	 * @return List of Tasks of the chosen Project
	 */
	public List<Task> getTasksFromAProject(Project projectToGetTasksFrom) {

		List<Task> tasksFromProject = new ArrayList<>();

		tasksFromProject.addAll(projectToGetTasksFrom.getTaskRepository().getProjectTaskRepository());

		return tasksFromProject;
	}

	/**
	 * This method creates the Dependence of a Task from another task and defines
	 * the number of days that must be spent util the dependent task starts.
	 * 
	 * @param taskDependent
	 *            Task that will be set as dependent from another one.
	 * @param taskReference
	 *            Task that will be set as the reference.
	 * @param incrementDays
	 *            Days that will be incremented to the estimated start date of the
	 *            reference task in order to set the estimated start date of the
	 *            dependent task
	 */
	public void createDependenceFromTask(Task taskDependent, Task taskReference, int incrementDays) {

		taskDependent.createTaskDependence(taskReference, incrementDays);
	}

}
