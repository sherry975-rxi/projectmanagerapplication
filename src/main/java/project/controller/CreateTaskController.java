package project.controller;

import java.util.Calendar;


import project.model.TaskRepository;
import project.model.Project;
import project.model.Task;

public class CreateTaskController {
	
	private TaskRepository target;
	
	// TODO must receive the project the User manages
	
	public CreateTaskController(Project target) {
		
		this.target=target.getTaskRepository();
	}
	
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
