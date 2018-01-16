/**
 * 
 */
package project.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.Task;
import project.model.User;

/**
 * @author Group3
 *
 */
public class US347CancelOnGoingTaskController {

	int projectIDtoInstantiate;

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
	public US347CancelOnGoingTaskController(String projectIDtoInstantiate) {
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
	public List<Project> getProjectsFromUser(User user) {

		List<Project> listOfProjectsOfProjectManager = new ArrayList<>();

		listOfProjectsOfProjectManager
				.addAll(Company.getTheInstance().getProjectsRepository().getProjectsOfProjectManager(user));

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

	
	public String viewTaskState (String taskID) {
		
		Task taskToGetByID = Company.getTheInstance().getProjectsRepository().getProjById(projectIDtoInstantiate).getTaskRepository().getTaskByID(taskID);
		
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

		Task task = Company.getTheInstance().getProjectsRepository().getProjById(projectIDtoInstantiate)
				.getTaskRepository().getTaskByID(taskIDtoSetState);

		boolean cancelled = false;

		if (task.getTaskState().changeToCancelled()) 
		{
			task.setCancelDate();
			
			cancelled = true;
			return cancelled;
		}
		
		return cancelled;
	};
	

	/**
	 * Sets the ProjectID to the User Input For ProjectID
	 * 
	 * @param userInputForProjectID
	 */
	public void setProjectID(int userInputForProjectID) {
		this.projectIDtoInstantiate = userInputForProjectID;
	}
}
