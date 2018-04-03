package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.PrintProjectInfoController;
import project.controllers.PrintTaskInfoController;
import project.controllers.US206RemovalTaskRequestController;
import project.model.User;

import java.util.Scanner;

@Component
public class US206CreateRemovalTaskRequestUI {
	@Autowired
	private US206RemovalTaskRequestController controller;

	@Autowired
	private PrintTaskInfoController taskInfo;

	@Autowired
	private PrintProjectInfoController projectInfo;

	User user;
	String taskID;
	Integer projID;
	private Boolean isPreviousUIFromTasks;

	/**
	 * Constructor
	 *
	 */
	public US206CreateRemovalTaskRequestUI() {
	}

	public void cancelRemovalTaskRequestUI() {

		controller.setUser(this.user);
		controller.setProjectID(projID);
		controller.setTaskID(taskID);
		projID = controller.getProjectID();

		projectInfo.setProjID(this.projID);
		String projectName = projectInfo.printProjectNameInfo();
		taskInfo.setTaskID(this.taskID);
		taskInfo.setProjeID(this.projID);
		String taskName = taskInfo.printTaskNameInfo();

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
			} else {
				System.out.println("[ERROR!: Please choose a valid Task]");
			}
		} else {
			System.out.println("Your task removal was not created");
		}
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}
}
