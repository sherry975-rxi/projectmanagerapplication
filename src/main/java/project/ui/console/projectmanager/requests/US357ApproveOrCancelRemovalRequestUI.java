package project.ui.console.projectmanager.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US357CancelRemovalTaskRequestController;
import project.model.Project;

import java.util.Scanner;

@Component
public class US357ApproveOrCancelRemovalRequestUI {

	@Autowired
	private US357CancelRemovalTaskRequestController cancelRequest;

	Project project;

	public US357ApproveOrCancelRemovalRequestUI() {
		//Empty constructor for JPA purposes
	}

	/**
	 * Displays the removal task requests and the options available to the user
	 */
	public void displayRemovalTaskRequests() {

		boolean condition = true;
		while (condition) {
			condition = false;
			System.out.println("\n    TASKS REMOVAL REQUESTS PENDING APPROVAL      ");
			System.out.println("___________________________________________________");

			cancelRequest.setProject(project);
			int number = 1;

			for (String other : cancelRequest.viewPendingRemovalRequests()) {
				System.out.println("[" + number + "]" + "Request: " + other);
				System.out.println("==========================================\n");
				number++;
			}

			System.out.println("[C] Choose a Request");
			System.out.println("[B] Back");
			System.out.println("[M] MainMenu");

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
		int listSize = cancelRequest.viewPendingRemovalRequests().size();

		// Guarantees that the input is valid
		try {

			Integer choiceInt = Integer.parseInt(choice);

			if (choiceInt > 0 && choiceInt <= listSize) {
				chooseApproveOrDisaprove(choiceInt);
			}

			else {
				System.out.println("----CHOOSE A VALID REQUEST----");
				displayRemovalTaskRequests();
			}
		}

		catch (NumberFormatException npe) {
			System.out.println("----CHOOSE A VALID REQUEST----");
			displayRemovalTaskRequests();
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
			String requestInfo = cancelRequest.viewPendingRemovalRequests().get(requestNumber);
			cancelRequest.setTaskIDandUserEmailWithRequestString(requestInfo);
			if (cancelRequest.acceptRemovalRequestFromTask()) {
				System.out.println("----REQUEST APPROVED----");
				System.out.println("--User deleted from task--");
				displayRemovalTaskRequests();
			}
		}

		else {
			System.out.println("----REQUEST CANCELLED----");
			System.out.println("--User not deleted from task--");
			cancelRequest.cancelRemovalRequestFromTask();
			displayRemovalTaskRequests();
		}
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
