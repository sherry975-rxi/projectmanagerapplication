package project.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import project.model.Project;
import project.model.Task;

/**
 * @author Group 3
 * 
 *         This class implements the controller correspondent to the
 *         createDependenceFromTask functionality.
 *
 */
public class US342CreateTaskDependencyController {

	private Project project;

	/**
	 * Constructor
	 * 
	 * @param projectIDtoInstantiate
	 */
	public US342CreateTaskDependencyController(Project project) {
		this.project = project;
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

		tasksFromProject = project.getTaskRepository().getProjectTaskRepository();

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

		Task taskDependent = project.getTaskRepository().getTaskByID(taskDependentID);
		Task taskReference = project.getTaskRepository().getTaskByID(taskReferenceID);

		taskDependent.createTaskDependence(taskReference);

		Calendar newEstimatedStartDate = (Calendar) taskReference.getEstimatedTaskStartDate().clone();
		newEstimatedStartDate.add(Calendar.DAY_OF_MONTH, incrementDays);
		taskDependent.setEstimatedTaskStartDate(newEstimatedStartDate);
	}

	public void createDependenceFromTaskWithoutEstimatedStartDate(String taskDependentID, String taskReferenceID) {

		Task taskDependent = project.getTaskRepository().getTaskByID(taskDependentID);
		Task taskReference = project.getTaskRepository().getTaskByID(taskReferenceID);

		taskDependent.createTaskDependence(taskReference);
	}

	public String getTaskEstimatedStartDateString(String taskDependentID) {

		Task taskToGetEstimatedStartDate = project.getTaskRepository().getTaskByID(taskDependentID);

		Calendar aaa = Calendar.getInstance();
		aaa = taskToGetEstimatedStartDate.getEstimatedTaskStartDate();
		Date estimatedStartDate = aaa.getTime();
		SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String estimatedStartDateString = newDateFormat.format(estimatedStartDate).toString();

		return estimatedStartDateString;
	}

	public boolean projectContainsSelectedTask(String id) {
		boolean result = false;
		for (int i = 0; i < project.getTaskRepository().getProjectTaskRepository().size(); i++) {
			if (id.equals(project.getTaskRepository().getProjectTaskRepository().get(i).getTaskID())) {
				result = true;
				break;
			}
		}
		return result;
	}

	public Task getTaskByID(String id) {
		return project.getTaskRepository().getTaskByID(id);
	}

}
