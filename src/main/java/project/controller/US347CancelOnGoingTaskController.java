/**
 * 
 */
package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.ProjectRepository;
import project.model.Task;

/**
 * @author Group3
 *
 */
public class US347CancelOnGoingTaskController {

	int projectIDtoInstantiate = 0;
	private ProjectRepository projectRepository;
	private Project project;
	private Company company;

	/**
	 * Constructor
	 * 
	 */
	public US347CancelOnGoingTaskController() {

	}

	/**
	 * Constructor
	 * 
	 * @param projectIDtoInstantiate
	 */
	public US347CancelOnGoingTaskController(int projectIDtoInstantiate) {
		this.projectIDtoInstantiate = projectIDtoInstantiate;
		company = Company.getTheInstance();
		projectRepository = company.getProjectsRepository();
		project = projectRepository.getProjById(projectIDtoInstantiate);
		project.getTaskRepository();
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
	 * Returns a string with the state of a certain Task 
	 * 
	 * @param taskID Task to get state info
	 * 
	 * @return task state as a string
	 */
	public String viewTaskState(String taskID) {

		Task taskToGetByID = Company.getTheInstance().getProjectsRepository().getProjById(projectIDtoInstantiate)
				.getTaskRepository().getTaskByID(taskID);

		return taskToGetByID.viewTaskStateName();
	}

	/**
	 * This method changes the state of a Task from OnGoing to Cancelled
	 * 
	 * @param taskIDtoSetState
	 *            ID of the task which state is going to be changed from OnGoing to
	 *            Cancelled
	 */
	public boolean cancelOnGoingTask(String taskIDtoSetState) {

		Task task = projectRepository.getProjById(this.projectIDtoInstantiate)
				.getTaskRepository().getTaskByID(taskIDtoSetState);

		boolean cancelled = false;

		if (task.getTaskState().changeToCancelled()) {
			task.setCancelDate();
			cancelled = true;
		}
		return cancelled;
	}
}
