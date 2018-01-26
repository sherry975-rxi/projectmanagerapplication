package project.controller;


import project.model.TaskRepository;
import project.model.Project;
import project.model.Task;

public class US340CreateTaskController {
	
	private TaskRepository taskRepository;
	
	// TODO must receive the project the User manages
	/**
	 * This constructor creates a target controller. Currently, it receives a project but it should receive a Project Controller or Project Manager controller
	 * 
	 * 
	 * @param target Task Repository
	 */
	public US340CreateTaskController(Project target) {
		
		this.taskRepository=target.getTaskRepository();
	}
	
	public TaskRepository getTaskRepository() {
		return this.taskRepository;
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
	public Task addTask(String description, int estimatedTaskEffort, int estimatedCost) {
		Task newTask = taskRepository.createTask(description);
		
		taskRepository.addProjectTask(newTask);

		return newTask;
	
	}
	
}

