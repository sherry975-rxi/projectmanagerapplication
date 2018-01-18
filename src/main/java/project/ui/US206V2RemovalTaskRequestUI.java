package project.ui;

import java.util.Scanner;

import project.controller.US206V2RemovalTaskRequestController;
import project.model.User;

public class US206V2RemovalTaskRequestUI {

	int projectID;
	User user;
	String taskID;

	/**
	 * Constructor to instantiate a new CollaboratorRemovalrequestUI
	 * 
	 * @param user
	 *            User that asks for the removal
	 * @param projectID
	 *            Project ID of project where the task is
	 * @param taskID
	 *            Task ID of the task that the user wants to be removed from
	 */
	public US206V2RemovalTaskRequestUI(User user, int projectID, String taskID) {
		this.user = user;
		this.projectID = projectID;
		this.taskID = taskID;
	}

	public void confirmRequest() {
		Scanner input = new Scanner(System.in);
		System.out.println("Do you really want to remove this task from your workload?");
		System.out.println("Y for yes.");
		String answer = input.nextLine();
		if (answer.equalsIgnoreCase("y")) {
			US206V2RemovalTaskRequestController controller = new US206V2RemovalTaskRequestController(this.user,
					this.projectID, this.taskID);
			if (controller.createRequest()) {
				System.out.println("Task removal is pending Project Manager approval.");
			} else {
				System.out.println("Task removal is already pending Project Manager approval.");
			}
		}
	}
}
