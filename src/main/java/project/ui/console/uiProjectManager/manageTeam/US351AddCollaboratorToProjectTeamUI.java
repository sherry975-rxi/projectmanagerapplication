package project.ui.console.uiProjectManager.manageTeam;

import java.util.Scanner;

import project.controller.PrintProjectInfoController;
import project.controller.PrintUserInfoController;
import project.controller.US351AddColaboratorToProjectTeamController;
import project.model.Project;
import project.model.User;

public class US351AddCollaboratorToProjectTeamUI {

	Project project;
	User user;
	int costPerEffort;

	public void addCollaboratorToProjectTeam() {
		String line = "______________________________________________";
		US351AddColaboratorToProjectTeamController controller = new US351AddColaboratorToProjectTeamController();
		PrintUserInfoController userInfoPrinter = new PrintUserInfoController();
		Scanner dataIn = new Scanner(System.in);
		boolean invalidInputA = true;
		boolean invalidInputB = true;
		boolean invalidInputC = true;
		boolean invalidInputD = false;

		if (controller.getAllProjects().isEmpty()) {

			System.out.println("There are no active projects!");

		} else {
			System.out.println(line);
			System.out.println("PROJECT LIST");
			System.out.println(line);

			for (int i = 0; i < controller.getAllProjects().size(); i++) {
				PrintProjectInfoController projectInfo = new PrintProjectInfoController(
						controller.getAllProjects().get(i).getIdCode());
				System.out.println("");
				System.out.println(line);
				System.out.println("PROJECT " + projectInfo.printProjectNameInfo().toUpperCase());
				System.out.println(line);
				System.out.println("ID: " + projectInfo.printProjectIDCodeInfo());
				System.out.println("STATUS: " + projectInfo.printProjectStatusInfo());
				System.out.println("DESCRIPTION: " + projectInfo.printProjectDescriptionInfo());
				System.out.println("START DATE: " + projectInfo.printProjectStartDateInfo());
				System.out.println("FINISH DATE: " + projectInfo.printProjectFinishDateInfo());
				System.out.println("PROJECT MANAGER: " + projectInfo.printProjectManagerInfo());
				System.out.println("PROJECT TEAM: " + projectInfo.printProjectTeamInfo());
				System.out.println("PROJECT BUDGET: " + projectInfo.printProjectBudgetInfo());
				System.out.println(line);
				System.out.println("");
			}

			while (invalidInputA) {
				System.out.println("Please choose a project (input its ID):");
				System.out.println("Inputing [-1] will exit this menu.");
				int projectID = dataIn.nextInt();
				if (projectID != -1) {
					project = controller.searchProjectByID(projectID);
					if (project != null) {
						System.out.println("Project selected: " + projectID);
						invalidInputA = false;
					} else {
						System.out.println("Invalid ID provided. Please try again!");
					}
				} else {
					System.out.println("Assignment of user to project cancelled.");
					invalidInputA = false;
					invalidInputB = false;
					invalidInputC = false;
					invalidInputD = true;
				}
			}

			System.out.println(line);
			System.out.println("USER LIST");
			System.out.println(line);
			userInfoPrinter.printAllActiveUsersInfo();
			System.out.println(line);
			System.out.println("");

			while (invalidInputB) {
				System.out.println("Please choose a user (input its ID:");
				System.out.println("Inputing [E] will exit this menu.");
				String userID = dataIn.nextLine();
				if (userID.equals("E")) {
					System.out.println("Assignment of user to project cancelled.");
					invalidInputB = false;
					invalidInputC = false;
					invalidInputD = true;
				} else {
					user = controller.searchUserByID(userID);
					if (user != null) {
						System.out.println("User selected: " + userID);
						invalidInputB = false;
					} else {
						System.out.println("Invalid ID provided. Please try again!");
					}

				}
			}

			while (invalidInputC) {
				System.out.println("Please input the cost per effort for the user selected:");
				System.out.println("Inputing [-1] will exit this menu.");
				costPerEffort = dataIn.nextInt();
				if (costPerEffort != -1) {
					System.out.println("Cost per effort: " + costPerEffort);
					invalidInputC = false;
				} else {
					System.out.println("Assignment of user to project cancelled.");
					invalidInputC = false;
					invalidInputD = true;
				}
			}

			if (!invalidInputD)
				System.out.println("Please input [Y] to confirm the addition of the user to the project team:");
			System.out.println("Inputing anything else will cancel the addition.");

			String confirm = dataIn.nextLine();
			if ("Y".equalsIgnoreCase(confirm)) {
				controller.addUserToProjectTeam(user, project, costPerEffort);
				System.out.println("User successfully added to the project.");
				invalidInputD = true;
			} else {
				System.out.println("Assignment of user to project cancelled.");
				invalidInputD = true;
			}
		}
	}

}
