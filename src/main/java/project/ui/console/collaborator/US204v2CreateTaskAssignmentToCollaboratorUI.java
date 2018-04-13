/**
 * 
 */
package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.PrintProjectInfoController;
import project.controllers.PrintTaskInfoController;
import project.controllers.US204v2createRequestAddCollaboratorToTaskTeamController;
import project.model.ProjectCollaborator;
import project.model.User;

import java.util.Scanner;

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

@Component
public class US204v2CreateTaskAssignmentToCollaboratorUI {

	@Autowired
	private PrintTaskInfoController printTaskInfoController;

	@Autowired
	private PrintProjectInfoController printProjectInfoController;

	@Autowired
	private US204v2createRequestAddCollaboratorToTaskTeamController controller;


	User user;
	String taskID;
	Integer projID;
	ProjectCollaborator projcollab;

	public US204v2CreateTaskAssignmentToCollaboratorUI() {
		//Empty constructor for JPA purposes
	}

	public void createTaskAssignment() {

		printProjectInfoController.setProjID(this.projID);
		String projectName = printProjectInfoController.printProjectNameInfo();
		printTaskInfoController.setProjeID(this.projID);
		printTaskInfoController.setTaskID(this.taskID);
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

		controller.setTaskID(this.taskID);
		controller.setProjectID(this.projID);
		controller.setUser(this.user);

		// In case user writes something different from "y" or "n"
		while (!("n".equalsIgnoreCase(yerOrNo)) && !("y".equalsIgnoreCase(yerOrNo))) {
			System.out.println("\nInvalid answer. Try again (\"y\" or \"n\")");
			yerOrNo = input.nextLine();
		}

		if ("y".equalsIgnoreCase(yerOrNo)) {
			if (controller.createTaskTeamRequest(this.taskID, this.user)) {
				System.out.println("Your request is pending approval.");
			} else {
				System.out.println("Your request was not done.");
			}
		} else {
			System.out.println("Your request was not created.");
		}
	}


	public void setUser(User user) {
		this.user = user;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public void setProjID(Integer projID) {
		this.projID = projID;
	}

}
