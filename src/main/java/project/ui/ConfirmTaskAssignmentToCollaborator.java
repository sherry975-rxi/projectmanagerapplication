/**
 * 
 */
package project.ui;

import java.util.Scanner;

import project.controller.AssignTaskToCollaboratorsController;
import project.model.ProjectCollaborator;
import project.model.User;

/**
 * @author Group3
 *
 */

public class ConfirmTaskAssignmentToCollaborator {

	User user;
	String taskID;
	Integer projID;
	ProjectCollaborator projcollab;

	/**
	 * Constructor
	 * 
	 * @param user
	 *            User to add task to user task list
	 * 
	 * @param taskID
	 *            Task to add to user task list
	 */
	public ConfirmTaskAssignmentToCollaborator(User user, String taskID) {

		this.user = user;
		this.taskID = taskID;
	}

	public void confirmTaskAssignmentToCollaborator() {

		// TODO PROJECT INFO

		Scanner input = new Scanner(System.in);

		System.out.println("Are you sure you want to assign yourself to this task ? \n");
		System.out.println("[Y] to add \n");
		System.out.println("[N] to cancel \n");

		String yerOrNo = input.nextLine();

		AssignTaskToCollaboratorsController assignTaskToCollaboratorsController = new AssignTaskToCollaboratorsController(
				this.taskID);
		projcollab = assignTaskToCollaboratorsController.getProjectCollaboratorFromUser(this.user);

		// In case user writes something different from "y" or "n"
		while (!("n".equalsIgnoreCase(yerOrNo)) && !("y".equalsIgnoreCase(yerOrNo))) {
			System.out.println("\nInvalid answer. Try again (\"y\" or \"n\")");
			yerOrNo = input.nextLine();
		}

		if (yerOrNo.equalsIgnoreCase("y")) {
			if (assignTaskToCollaboratorsController.assignTaskToProjectCollaboratorController(taskID,
					projcollab) == true) {
				System.out.println("Your were successfully assigned to this task");
				// TaskDetailsUI taskDetailsUI = new
				// TaskDetailsUI(assignTaskToCollaboratorsController.getTaskByTaskID(this.taskID));
				// taskDetailsUI.taskDataDisplay();
			} else {
				System.out.println("Your were not assigned to this task");
				// TaskDetailsUI taskDetailsUI = new
				// TaskDetailsUI(assignTaskToCollaboratorsController.getTaskByTaskID(this.taskID));
				// taskDetailsUI.taskDataDisplay();
			}
		} else {
			// TaskDetailsUI taskDetailsUI = new
			// TaskDetailsUI(assignTaskToCollaboratorsController.getTaskByTaskID(this.taskID));
			// taskDetailsUI.taskDataDisplay();
		}
	}

}
