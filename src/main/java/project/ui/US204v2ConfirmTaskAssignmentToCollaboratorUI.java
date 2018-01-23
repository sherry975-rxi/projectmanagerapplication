/**
 * 
 */
package project.ui;

import java.util.Scanner;

import project.controller.AssignTaskToCollaboratorsController;
import project.controller.PrintProjectInfoController;
import project.controller.PrintTaskInfoController;
import project.controller.US206CancelRemovalTaskRequestController;
import project.model.ProjectCollaborator;
import project.model.User;

/**
 * In this UI the user (project collaborator) confirms his intention to assign
 * himself to a task as task collaborator. If he confirms this intent, an
 * assignment request is sent to the project manager of this project and if the
 * project manager confirms the assignment, the UI returns a message confirming
 * the effectiveness of this addition.
 * 
 * @author Group3
 *
 */

public class US204v2ConfirmTaskAssignmentToCollaboratorUI {

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
	public US204v2ConfirmTaskAssignmentToCollaboratorUI(User user, String taskID) {

		this.user = user;
		this.taskID = taskID;
	}

	public void confirmTaskAssignmentToCollaborator() {

		US206CancelRemovalTaskRequestController controller = new US206CancelRemovalTaskRequestController(this.user);
		controller.setProjectIDFromTaskID(taskID);
		projID = controller.getProjectID();

		PrintProjectInfoController printProjectInfoController = new PrintProjectInfoController(this.projID);
		String projectName = printProjectInfoController.printProjectNameInfo();
		PrintTaskInfoController printTaskInfoController = new PrintTaskInfoController(this.taskID, this.projID);
		String taskName = printTaskInfoController.printTaskNameInfo();

		System.out.println("PROJECT - " + projectName);
		System.out.println("__________________________________________________");
		System.out.println("Task - " + taskName + "\n");

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
				// TODO release this commented code when TaskDetailsUI is ready
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
