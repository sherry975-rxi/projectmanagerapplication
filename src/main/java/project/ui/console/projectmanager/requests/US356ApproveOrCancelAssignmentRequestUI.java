package project.ui.console.projectmanager.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controller.US356ManageAssigmentRequestController;
import project.model.Project;

import java.util.Scanner;

@Component
public class US356ApproveOrCancelAssignmentRequestUI {

	@Autowired
	private US356ManageAssigmentRequestController assignmentRequest;

	private Project project;

	/**
	 * Constructor to instantiate a new US357ApproveOrCancelAssignmentRequestUI
	 *
	 */
	public US356ApproveOrCancelAssignmentRequestUI() {
	}

	/**
	 * Displays the assignment task requests and the options available to the user
	 */
	public void displayAssignmentTaskRequests() {
		boolean condition = true;
		while (condition) {
			condition = false;

			System.out.println("\n   TASK ASSIGNMENT REQUESTS : PENDING APPROVAL      ");
			System.out.println("___________________________________________________");

			assignmentRequest.setSelectedProject(project);
			int number = 1;

			for (String other : assignmentRequest.showAllAssignmentRequests(project)) {
				System.out.println("[" + number + "]" + "Request: " + other);
				System.out.println("==========================================\n");
				number++;
			}

			System.out.println("[C] Choose a Request");
			System.out.println("[B] Back \n");

			condition = chooseOption();
		}
	}

	/**
	 * Switch case that allows the user to choose a functionality
	 *
	 */
	private boolean chooseOption() {

		boolean loop = false;
		
		Scanner input = new Scanner(System.in);

		String choice = input.nextLine().toUpperCase();

		switch (choice) {

		case "C":
			chooseRequest();
			break;
		case "B":
			break;
		default:
			System.out.println("Choose a valid option");
			loop = true;
		}
		return loop;
	}

	/**
	 * Method that allows the user to choose a request
	 *
	 */
	private void chooseRequest() {

		System.out.println("\n                 CHOOSE A REQUEST:                  ");

		Scanner input = new Scanner(System.in);
		String choice = input.nextLine();
		int listSize = assignmentRequest.showAllAssignmentRequests(project).size();

		// Guarantees that the input is valid
		try {

			Integer choiceInt = Integer.parseInt(choice);

			if (choiceInt > 0 && choiceInt <= listSize) {
				chooseApproveOrDisaprove(choiceInt);
			}

			else {
				System.out.println("----CHOOSE A VALID REQUEST----");
				displayAssignmentTaskRequests();
			}
		}

		catch (NumberFormatException npe) {
			System.out.println("----CHOOSE A VALID REQUEST----");
			displayAssignmentTaskRequests();
		}
	}

	/**
	 * Method that allows the user to choose to approve or cancel a specific request
	 *
	 * @param request
	 *            Index of the chosen request
	 */
	private void chooseApproveOrDisaprove(int request) {

		Scanner input = new Scanner(System.in);

		System.out.println("Are you sure you want to confirm this  ? \n");
		System.out.println("[Y] to remove");
		System.out.println("[N] to cancel\n");

		String yerOrNo = input.nextLine();

		// In case user writes something different from "y" or "n"
		while (!("n".equalsIgnoreCase(yerOrNo)) && !("y".equalsIgnoreCase(yerOrNo))) {
			System.out.println("\nInvalid answer. Try again (\"y\" or \"n\")");
			yerOrNo = input.nextLine();
		}

		if ("y".equalsIgnoreCase(yerOrNo)) {
			int requestNumber = request - 1;
			assignmentRequest.setSelectedAdditionRequest(requestNumber, project);
			if (assignmentRequest.approveAssignmentRequest()) {
				System.out.println("----REQUEST APPROVED----");
				System.out.println("--User assigned to Task--");
				displayAssignmentTaskRequests();
			}
		}

		else {
			System.out.println("----REQUEST CANCELLED----");
			System.out.println("--User not assigned to task--");
			assignmentRequest.approveAssignmentRequest();
			displayAssignmentTaskRequests();
		}
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
