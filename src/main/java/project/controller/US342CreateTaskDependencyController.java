package project.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import project.Services.TaskService;
import project.model.Project;
import project.model.Task;

/**
 * @author Group 3
 * 
 *         This class implements the controller correspondent to the
 *         createDependenceFromTask functionality.
 *
 */
@Controller
public class US342CreateTaskDependencyController {

	private Project project;

	@Autowired
	private TaskService taskService;

	/*
	 * Default constructor
	 */

	public US342CreateTaskDependencyController() {

	}

	/*
	 * Getters and Setters
	 */

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * Constructor
	 * 
	 * @param project
	 */
	public US342CreateTaskDependencyController(Project project) {
		this.project = project;
	}

	/**
	 * This method returns the tasks from a specific project
	 *
	 * 
	 * @return List of Tasks of the chosen Project
	 */
	public List<Task> getTasksFromAProject() {

		return taskService.getTaskListOfWhichDependenciesCanBeCreated(project);
	}

	/**
	 * This method creates the Dependence of a Task from another task and defines
	 * the number of days that must be spent until the dependent task starts.
	 * 
	 * @param taskDependentID
	 *            Task that will be set as dependent from another one.
	 * @param taskReferenceID
	 *            Task that will be set as the reference.
	 * @param incrementDays
	 *            Days that will be incremented to the estimated start date of the
	 *            reference task in order to set the estimated start date of the
	 *            dependent task
	 */
	public boolean createDependenceFromTask(String taskDependentID, String taskReferenceID, int incrementDays) {

		boolean wasTaskDependencyCreated = false;

		Task taskDependent = taskService.getTaskByTaskID(taskDependentID);
		Task taskReference = taskService.getTaskByTaskID(taskReferenceID);

		wasTaskDependencyCreated = taskDependent.createTaskDependence(taskReference, incrementDays);

		return wasTaskDependencyCreated;

	}

	/**
	 * This method checks if its possible to create a Task Dependency
	 * 
	 * @param taskDependentID
	 *            The daughter task to check for
	 * 
	 * @param taskReferenceID
	 *            The mother task to check for
	 * 
	 * @return TRUE if possible, FALSE if not
	 */
	public boolean isTaskDependencyPossible(String taskDependentID, String taskReferenceID) {

		boolean isTaskDependencyPossible = false;

		Task taskDependent = taskService.getTaskByTaskID(taskDependentID);
		Task taskReference = taskService.getTaskByTaskID(taskReferenceID);

		isTaskDependencyPossible = taskDependent.isCreatingTaskDependencyValid(taskReference);

		return isTaskDependencyPossible;

	}

	/**
	 * @param taskDependentID
	 *            The daughter task to remove dependence from
	 * @param taskReferenceID
	 *            the mother task that will be removed from the list of dependencies
	 * @return TRUE if it was successfully remove, FALSE if not
	 */
	public boolean removeDependenceFromTask(String taskDependentID, String taskReferenceID) {

		boolean wasTaskDependencyRemoved = false;

		Task taskDependent = taskService.getTaskByTaskID(taskDependentID);
		Task taskReference = taskService.getTaskByTaskID(taskReferenceID);

		wasTaskDependencyRemoved = taskDependent.removeTaskDependence(taskReference);

		return wasTaskDependencyRemoved;

	}

	/**
	 * @param taskDependentID
	 *            The dependent task id
	 * @return The string with the estimated start date
	 */
	public String getTaskEstimatedStartDateString(String taskDependentID) {

		Task taskToGetEstimatedStartDate = taskService.getTaskByTaskID(taskDependentID);

		Calendar calendar = taskToGetEstimatedStartDate.getEstimatedTaskStartDate();

		String estimatedStartDateString = "No estimated start date";

		if (calendar != null) {
			Date estimatedStartDate = calendar.getTime();
			SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			estimatedStartDateString = newDateFormat.format(estimatedStartDate);
		}

		return estimatedStartDateString;
	}

	/**
	 * @param taskDependentID
	 *            The mother task to get it's deadline
	 * @return A string with the deadline of the task
	 */
	public String getTaskDeadlineString(String taskDependentID) {

		Task taskToGetDeadline = taskService.getTaskByTaskID(taskDependentID);

		Calendar calendar = taskToGetDeadline.getTaskDeadline();

		String estimatedDeadlineString = "No estimated deadline";
		if (calendar != null) {
			Date estimatedDeadline = calendar.getTime();
			SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			estimatedDeadlineString = newDateFormat.format(estimatedDeadline);
		}

		return estimatedDeadlineString;
	}

	/**
	 * @param id
	 *            Checks if a project contains a task by its ID
	 * @return TRUE if the ID matches a task in the Task Repository, FALSE if not
	 */
	public boolean projectContainsSelectedTask(String id) {
		boolean result = false;
		for (int i = 0; i < taskService.getProjectTasks(project).size(); i++) {
			if (id.equals(taskService.getProjectTasks(project).get(i).getTaskID())) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * @param id
	 *            The ID of the task to retrieve in the repository
	 * @return A task with the matching ID
	 */
	public Task getTaskByID(String id) {
		return taskService.getTaskByTaskID(id);
	}

}
