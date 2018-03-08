package project.ui.console.collaborator;

import project.controller.PrintProjectInfoController;
import project.controller.PrintTaskInfoController;
import project.controller.US206RemovalTaskRequestController;
import project.controller.UpdateDbToContainersController;
import project.model.User;

import java.util.Scanner;

public class US206CreateRemovalTaskRequestUI {
	User user;
	String taskID;
	Integer projID;
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
	public US206CreateRemovalTaskRequestUI(User user, String taskID) {

		this.user = user;
		this.taskID = taskID;
		this.projID = null;
	}

	public void cancelRemovalTaskRequestUI() {
		UpdateDbToContainersController infoUpdater = new UpdateDbToContainersController();
		infoUpdater.updateDBtoContainer();

		US206RemovalTaskRequestController controller = new US206RemovalTaskRequestController(this.user);
		controller.setProjectIDFromTaskID(taskID);
		controller.setTaskID(taskID);
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
			System.out.println("\nInvalid answer. Try again (\"Y\" or \"N\")");
			yerOrNo = input.nextLine().toUpperCase();
		}

		if ("Y".equalsIgnoreCase(yerOrNo)) {
			if (controller.createRequest()) {
				System.out.println("Your task removal is pending Project Manager approval");
				TaskDetailsUI taskDetailsUI = new TaskDetailsUI(this.taskID, this.projID, this.user,
						this.isPreviousUIFromTasks);
				taskDetailsUI.taskDataDisplay();
			} else {
				System.out.println("[ERROR!: Please choose a valid Task]");
				TaskDetailsUI taskDetailsUI = new TaskDetailsUI(this.taskID, this.projID, this.user,
						this.isPreviousUIFromTasks);
				taskDetailsUI.taskDataDisplay();
			}
		} else {
			TaskDetailsUI taskDetailsUI = new TaskDetailsUI(this.taskID, this.projID, this.user,
					this.isPreviousUIFromTasks);
			taskDetailsUI.taskDataDisplay();
		}
	}
}
