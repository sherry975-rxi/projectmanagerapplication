package project.ui.console.projectmanager.requests;

import project.controller.US356ManageAssigmentRequestController;
import project.model.Project;
import project.model.User;
import project.ui.console.MainMenuUI;
import project.ui.console.projectmanager.ProjectManagerMainMenuUI;

import java.util.Scanner;

public class US356ApproveOrCancelAssignmentRequestUI {

	Project project;
	User user;

	/**
	 * Constructor to instantiate a new US357ApproveOrCancelAssignmentRequestUI
	 * 
	 * @param user
	 *            User Project Manager
	 * @param project
	 *            Project where the user is Project manager
	 */
	public US356ApproveOrCancelAssignmentRequestUI(User user, Project project) {
		this.project = project;
		this.user = user;
	}

	/**
	 * Displays the assignment task requests and the options available to the user
	 */
	public void displayAssignmentTaskRequests() {

		boolean condition = true;
		while (condition) {

			System.out.println("\n   TASK ASSIGNMENT REQUESTS : PENDING APPROVAL      ");
			System.out.println("___________________________________________________");

			US356ManageAssigmentRequestController assignmentRequest = new US356ManageAssigmentRequestController(
					this.project);
			int number = 1;

			for (String other : assignmentRequest.showAllAssignmentRequests()) {
				System.out.println("[" + number + "]" + "Request: " + other);
				System.out.println("==========================================\n");
				number++;
			}

			System.out.println("[C] Choose a Request");
			System.out.println("[B] Back");
			System.out.println("[M] MainMenu");

			chooseOption(assignmentRequest);
		}
	}

	/**
	 * Switch case that allows the user to choose a functionality
	 * 
	 * @param assignmentRequest
	 *            US356ManageAssigmentRequestController previously instantiated
	 */
	private void chooseOption(US356ManageAssigmentRequestController assignmentRequest) {

		Scanner input = new Scanner(System.in);

		String choice = input.nextLine().toUpperCase();

		switch (choice) {

		case "C":
			chooseRequest(assignmentRequest);
			break;
		case "B":
			ProjectManagerMainMenuUI previousMenu = new ProjectManagerMainMenuUI(user, project);
			previousMenu.displayOptions();
			break;
		case "M":
			MainMenuUI.mainMenu();
			break;
		default:
			System.out.println("Choose a valid option");
			displayAssignmentTaskRequests();
		}
	}

	/**
	 * Method that allows the user to choose a request
	 * 
	 * @param assignmentRequest
	 *            US356ManageAssigmentRequestController previously instantiated
	 */
	private void chooseRequest(US356ManageAssigmentRequestController assignmentRequest) {

		System.out.println("\n                 CHOOSE A REQUEST:                  ");

		Scanner input = new Scanner(System.in);
		String choice = input.nextLine();
		int listSize = assignmentRequest.showAllAssignmentRequests().size();

		// Guarantees that the input is valid
		try {

			Integer choiceInt = Integer.parseInt(choice);

			if (choiceInt > 0 && choiceInt <= listSize) {
				chooseApproveOrDisaprove(assignmentRequest, choiceInt);
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
	 * @param assignmentRequest
	 *            US356ManageAssigmentRequestController previously instantiated
	 * @param request
	 *            Index of the chosen request
	 */
	private void chooseApproveOrDisaprove(US356ManageAssigmentRequestController assignmentRequest, int request) {

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
			assignmentRequest.setSelectedAdditionRequest(requestNumber);
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
}
