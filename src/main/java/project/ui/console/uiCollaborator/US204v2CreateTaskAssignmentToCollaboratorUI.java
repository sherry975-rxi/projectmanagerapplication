/**
 * 
 */
package project.ui.console.uiCollaborator;

import java.util.Scanner;

import project.controller.PrintProjectInfoController;
import project.controller.PrintTaskInfoController;
import project.controller.US204v2createRequestAddCollaboratorToTaskTeamController;
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

public class US204v2CreateTaskAssignmentToCollaboratorUI {

	User user;
	String taskID;
	Integer projID;
	ProjectCollaborator projcollab;
	private Boolean isPreviousUIFromTasks;

	/**
	 * Constructor
	 * 
	 * @param user
	 *            User to add task to user task list
	 * 
	 * @param taskID
	 *            Task to add to user task list
	 */
	public US204v2CreateTaskAssignmentToCollaboratorUI(User user, String taskID, Integer projID) {

		this.user = user;
		this.taskID = taskID;
		this.projID = projID;
	}

	public void createTaskAssignment() {

		PrintProjectInfoController printProjectInfoController = new PrintProjectInfoController(this.projID);
		String projectName = printProjectInfoController.printProjectNameInfo();
		PrintTaskInfoController printTaskInfoController = new PrintTaskInfoController(this.taskID, this.projID);
		String taskName = printTaskInfoController.printTaskNameInfo();

		System.out.println("PROJECT - " + projectName);
		System.out.println("");
		System.out.println("                     TASK                    ");
		System.out.println("*** " + taskName + " ***");
		System.out.println("______________________________________________");

		Scanner input = new Scanner(System.in);

		System.out.println("Are you sure you want to assign yourself to this task ? \n");
		System.out.println("[Y] to add");
		System.out.println("[N] to cancel \n");

		String yerOrNo = input.nextLine();

		US204v2createRequestAddCollaboratorToTaskTeamController controller = new US204v2createRequestAddCollaboratorToTaskTeamController(
				taskID, user);

		// In case user writes something different from "y" or "n"
		while (!("n".equalsIgnoreCase(yerOrNo)) && !("y".equalsIgnoreCase(yerOrNo))) {
			System.out.println("\nInvalid answer. Try again (\"y\" or \"n\")");
			yerOrNo = input.nextLine();
		}

		if (yerOrNo.equalsIgnoreCase("y")) {
			if (controller.createTaskTeamRequest() == true) {
				System.out.println("Your request is pending approval.");
				TaskDetailsUI taskDetailsUI = new TaskDetailsUI(taskID, projID, user, this.isPreviousUIFromTasks);
				taskDetailsUI.taskDataDisplay();
			} else {
				System.out.println("Your request was not done.");
				TaskDetailsUI taskDetailsUI = new TaskDetailsUI(taskID, projID, user, this.isPreviousUIFromTasks);
				taskDetailsUI.taskDataDisplay();
			}
		} else {
			System.out.println("Your request was not created.");
			TaskDetailsUI taskDetailsUI = new TaskDetailsUI(taskID, projID, user, this.isPreviousUIFromTasks);
			taskDetailsUI.taskDataDisplay();
		}
	}

}
