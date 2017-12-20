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
	 * @return a string stating whether or not the task was added successfully
	 */
	public String addTask(String taskDescription, int estimatedTaskEffort, Calendar estimatedTaskStartDate, Calendar taskDeadline, int estimatedCost) {
		Task toAdd = target.createTask(taskDescription, estimatedTaskEffort, estimatedTaskStartDate, taskDeadline, estimatedCost);
		
		target.addProjectTask(toAdd);
		
		if(target.getUnFinishedTasks().contains(toAdd)) {
			return "OK!";
		}
		else
			return "Task not added!";
	}

}
