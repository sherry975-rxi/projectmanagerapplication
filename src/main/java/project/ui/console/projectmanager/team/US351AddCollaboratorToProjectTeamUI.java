package project.ui.console.projectmanager.team;

import java.util.Scanner;

import project.controller.US351AddColaboratorToProjectTeamController;
import project.model.Company;
import project.model.Project;
import project.model.User;

public class US351AddCollaboratorToProjectTeamUI {

	User user;
	int costPerEffort;

	public void addCollaboratorToProjectTeam(Project project) {
		String line = "______________________________________________";
		US351AddColaboratorToProjectTeamController controller = new US351AddColaboratorToProjectTeamController();
		Scanner dataIn = new Scanner(System.in);
		boolean invalidInputA = true;
		boolean invalidInputB = true;
		boolean invalidInputC = false;

		System.out.println(line);
		System.out.println("USER LIST");
		System.out.println(line);
		for (User each : Company.getTheInstance().getUsersRepository().getAllUsersFromRepository()) {
			System.out.println(each.getName());
			System.out.println(each.getIdNumber());
			System.out.println(each.getEmail());
			System.out.println(each.getPhone());
			System.out.println(each.getFunction());
			System.out.println();
		}
		System.out.println(line);
		System.out.println("");

		while (invalidInputA) {
			System.out.println("Please choose a user (input its ID:");
			System.out.println("Inputing [E] will exit this menu.");
			System.out.println(line);
			String userID = dataIn.nextLine();
			if ("E".equals(userID)) {
				System.out.println("Assignment of user to project cancelled.");
				invalidInputA = false;
				invalidInputB = false;
				invalidInputC = true;
			} else {
				invalidInputA = searchUserUI(controller, userID, project);
			}
		}

		while (invalidInputB) {
			System.out.println("Please input the cost per effort for the user selected:");
			System.out.println("Inputing [-1] will exit this menu.\n");
			costPerEffort = dataIn.nextInt();
			dataIn.nextLine();
			if (costPerEffort != -1) {
				System.out.println("Cost per effort: " + costPerEffort);
				invalidInputB = false;
			} else {
				System.out.println("Assignment of user to project cancelled.");
				invalidInputB = false;
				invalidInputC = true;
			}
		}

		if (!invalidInputC)
			System.out.println("Please input [Y] to confirm the addition of the user to the project team:");
		System.out.println("Inputing anything else will cancel the addition.\n");

		String confirm = dataIn.nextLine();
		if ("Y".equalsIgnoreCase(confirm)) {
			controller.addUserToProjectTeam(user, project, costPerEffort);
			System.out.println("\nUser successfully added to the project.");
		} else {
			System.out.println("\nAssignment of user to project cancelled.");
		}
	}

	private boolean searchUserUI(US351AddColaboratorToProjectTeamController controller, String userID,
			Project project) {
		boolean result = true;
		user = controller.searchUserByID(userID);
		if (!controller.isUserAlreadyInProject(user, project)) {
			System.out.println("User selected: " + userID);
			this.user = controller.searchUserByID(userID);
			result = false;
		} else if (user != null) {
			System.out.println("User is already in the project team");
		} else {
			System.out.println("Invalid ID provided. Please try again!");
		}
		return result;
	}
}
