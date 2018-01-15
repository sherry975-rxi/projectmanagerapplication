package project.controller;

import java.util.InputMismatchException;

import project.model.Company;
import project.model.Task;

public class DeleteTaskController {

	private Task taskToDelete;

	/**
	 * 
	 * This controller allows a Project Manager to delete a task from the Task
	 * Repository The "State" of the Task must be set to
	 * 
	 * "Assigned", "Planned" "Created" or "Ready"
	 * 
	 * Otherwise the task can't be deleted from the task repository.
	 * 
	 * The ta
	 *
	 */

	/**
	 * @param projectID
	 *            The ProjectId that contains the task that the ProjectManager wants
	 *            to delete
	 * @param taskID
	 *            The ID of the task that will be deleted
	 */
	public void deleteTask(int projectID, String taskID) {

		try {
			taskToDelete = Company.getTheInstance().getProjectsRepository().getProjById(projectID).getTaskRepository()
					.getTaskByID(taskID);

			// Deletes the task from the task Repository
			Company.getTheInstance().getProjectsRepository().getProjById(projectID).getTaskRepository()
					.deleteTask(taskToDelete);

		} catch (InputMismatchException e) {
			System.out.println("The ID of the task doesn't exist in the Project. Please, try again");

		}

	}

}
