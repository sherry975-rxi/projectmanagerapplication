package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import project.Services.TaskService;
import project.model.Project;
import project.model.Task;

@Controller
public class US340CreateTaskController {

	@Autowired
	public TaskService taskService;

	private Project chosenProject;

	/*
	 * Default constructor
	 */

	public US340CreateTaskController() {

	}

	public void setChosenProject(Project chosenProject) {
		this.chosenProject = chosenProject;
	}

	/**
	 * This constructor creates a target controller. Currently, it receives a
	 * project but it should receive a Project Controller or Project Manager
	 * controller
	 * 
	 * 
	 * @param target
	 *            Task Repository
	 */
	public US340CreateTaskController(Project target) {

		chosenProject = target;
	}

	public TaskService getTaskService() {
		return this.taskService;
	}

	/**
	 * This controller calls the create task method, then the add task method, and
	 * finally confirms whether it was added successfully
	 * 
	 * @param taskDescription
	 * @param estimatedTaskEffort
	 * @param estimatedTaskStartDate
	 * @param taskDeadline
	 * @param estimatedCost
	 * @return the added task
	 */
	public boolean addTask(String description) {
		Boolean wasTaskSaved = true;
		Task newTask = taskService.createTask(description, chosenProject);
		if (newTask == null) {
			wasTaskSaved = false;
		}
		return wasTaskSaved;

	}

}
