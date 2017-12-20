package project.controller;

import java.util.Calendar;


import project.model.TaskRepository;
import project.model.Project;
import project.model.Task;

public class CreateTaskController {
	
	private TaskRepository target;
	
	// TODO must receive the project the User manages
	/**
	 * This constructor creates a target controller. Currently, it recieves a project but it should recieve a Project Controller or Project Manager controller
	 * 
	 * 
	 * @param target
	 */
	public CreateTaskController(Project target) {
		
		this.target=target.getTaskRepository();
	}
	/**
	 * This controller calls the create task method, then the add task method, and finally confirms whether it was added successfully
	 * 
	 * @param taskDescription
	 * @param estimatedTaskEffort
	 * @param estimatedTaskStartDate
	 * @param taskDeadline
	 * @param estimatedCost
	 * @return the added task
	 */
	public Task addTask(String taskDescription, int estimatedTaskEffort, Calendar estimatedTaskStartDate, Calendar taskDeadline, int estimatedCost) {
		Task toAdd = target.createTask(taskDescription, estimatedTaskEffort, estimatedTaskStartDate, taskDeadline, estimatedCost);
		
		target.addProjectTask(toAdd);
		
		int index = target.getUnstartedTasks().size();
		
		return target.getUnstartedTasks().get(index-1);
	}

}
