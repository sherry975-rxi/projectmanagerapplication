package project.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class US342CreateTaskDependencyController {

	int projectIDtoInstantiate;

	/**
	 * Constructor
	 * 
	 */
	public US342CreateTaskDependencyController() {

	}

	/**
	 * Constructor
	 * 
	 * @param projectIDtoInstantiate
	 */
	public US342CreateTaskDependencyController(String projectIDtoInstantiate) {
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
	public List<Project> getProjectsFromUser(User projectManager) {

		List<Project> listOfProjectsOfProjectManager = new ArrayList<>();

		listOfProjectsOfProjectManager
				.addAll(Company.getTheInstance().getProjectsRepository().getProjectsFromUser(projectManager));

		return listOfProjectsOfProjectManager;
	}

	/**
	 * This method returns the tasks from a specific project
	 * 
	 * @param userInputForProjectID
	 *            Project ID to get the tasks from
	 * 
	 * @return List of Tasks of the chosen Project
	 */
	public List<Task> getTasksFromAProject() {

		List<Task> tasksFromProject = new ArrayList<>();
		Project projectToGetTasks = Company.getTheInstance().getProjectsRepository()
				.getProjById(this.projectIDtoInstantiate);

		tasksFromProject.addAll(projectToGetTasks.getTaskRepository().getProjectTaskRepository());

		return tasksFromProject;
	}

	/**
	 * This method creates the Dependence of a Task from another task and defines
	 * the number of days that must be spent until the dependent task starts.
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
	public void createDependenceFromTask(String taskDependentID, String taskReferenceID, int incrementDays) {

		Task taskDependent = Company.getTheInstance().getProjectsRepository().getProjById(projectIDtoInstantiate)
				.getTaskRepository().getTaskByID(taskDependentID);
		Task taskReference = Company.getTheInstance().getProjectsRepository().getProjById(projectIDtoInstantiate)
				.getTaskRepository().getTaskByID(taskReferenceID);

		taskDependent.createTaskDependence(taskReference);
	}

	/**
	 * Sets the ProjectID to the User Input For ProjectID
	 * 
	 * @param userInputForProjectID
	 */
	public void setProjectID(int userInputForProjectID) {
		this.projectIDtoInstantiate = userInputForProjectID;
	}

	public String getTaskDependentEstimatedStartDate(String taskDependentID) {

		Task taskToGetEstimatedStartDate = Company.getTheInstance().getProjectsRepository()
				.getProjById(projectIDtoInstantiate).getTaskRepository().getTaskByID(taskDependentID);

		Calendar aaa = Calendar.getInstance();
		aaa = taskToGetEstimatedStartDate.getEstimatedTaskStartDate();
		Date estimatedStartDate = aaa.getTime();
		SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String estimatedStartDateString = newDateFormat.format(estimatedStartDate).toString();

		return estimatedStartDateString;
	}

	public String getTaskReferenceEstimatedStartDate(String taskReferenceID) {

		Task taskToGetEstimatedStartDate = Company.getTheInstance().getProjectsRepository()
				.getProjById(projectIDtoInstantiate).getTaskRepository().getTaskByID(taskReferenceID);

		Calendar aaa = Calendar.getInstance();
		aaa = taskToGetEstimatedStartDate.getEstimatedTaskStartDate();
		Date estimatedStartDate = aaa.getTime();
		SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String estimatedStartDateString = newDateFormat.format(estimatedStartDate).toString();

		return estimatedStartDateString;
	}

}
