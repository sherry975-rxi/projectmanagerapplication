package project.ui.uiCollaborator;

import java.util.Scanner;

import project.controller.PrintProjectInfoController;
import project.controller.PrintTaskInfoController;
import project.controller.US206CancelRemovalTaskRequestController;
import project.model.User;

public class US206CancelRemovalTaskRequestUI {
	User user;
	String taskID;
	Integer projID;

	/**
	 * Constructor
	 * 
	 * @param user
	 *            User to add task to user task list
	 * 
	 * @param taskID
	 *            Task to add to user task list
	 */
	public US206CancelRemovalTaskRequestUI(User user, String taskID) {

		this.user = user;
		this.taskID = taskID;
		this.projID = null;
	}

	public void cancelRemovalTaskRequestUI() {

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

		System.out.println("Are you sure you want to remove yourself from this task ? \n");
		System.out.println("[Y] to remove");
		System.out.println("[N] to cancel\n");

		String yerOrNo = input.nextLine();

		// In case user writes something different from "y" or "n"
		while (!("n".equalsIgnoreCase(yerOrNo)) && !("y".equalsIgnoreCase(yerOrNo))) {
			System.out.println("\nInvalid answer. Try again (\"y\" or \"n\")");
			yerOrNo = input.nextLine();
		}

		if (yerOrNo.equalsIgnoreCase("y")) {
			if (controller.createRequest() == true) {
				System.out.println("Your task removal is pending Project Manager approval");
				TaskDetailsUI taskDetailsUI = new TaskDetailsUI(this.taskID, this.projID, this.user);
				taskDetailsUI.taskDataDisplay();
			} else {
				System.out.println("[ERROR!: Please choose a valid Task]");
				TaskDetailsUI taskDetailsUI = new TaskDetailsUI(this.taskID, this.projID, this.user);
				taskDetailsUI.taskDataDisplay();
			}
		} else {
			TaskDetailsUI taskDetailsUI = new TaskDetailsUI(this.taskID, this.projID, this.user);
			taskDetailsUI.taskDataDisplay();
		}
	}
}
