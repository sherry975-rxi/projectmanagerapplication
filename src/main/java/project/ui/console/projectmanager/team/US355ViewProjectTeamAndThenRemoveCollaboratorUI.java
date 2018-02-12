package project.ui.console.projectmanager.team;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import project.controller.PrintProjectInfoController;
import project.controller.US355ViewProjectTeamAndThenRemoveCollaboratorController;
import project.model.Project;
import project.model.User;
import project.ui.console.MainMenuUI;
import project.ui.console.projectmanager.ProjectManagerMainMenuUI;

public class US355ViewProjectTeamAndThenRemoveCollaboratorUI {

	private Project project;
	private User user;

	public void viewProjectTeamAndThenRemoveCollaboratorUI(Project project, User user) {

		this.project = project;
		this.user = user;

		Scanner scannerInput = new Scanner(System.in);

		PrintProjectInfoController projectInfo = new PrintProjectInfoController(this.project);

		System.out.println("");
		System.out.println("PROJECT " + projectInfo.printProjectNameInfo().toUpperCase());
		System.out.println("___________________________________________________");
		System.out.println("ID: " + projectInfo.printProjectIDCodeInfo());
		System.out.println("STATUS: " + projectInfo.printProjectStatusInfo());
		System.out.println("DESCRIPTION: " + projectInfo.printProjectDescriptionInfo());
		System.out.println("START DATE: " + projectInfo.printProjectStartDateInfo());
		System.out.println("FINISH DATE: " + projectInfo.printProjectFinishDateInfo());
		System.out.println("PROJECT MANAGER: " + projectInfo.printProjectManagerInfo());
		System.out.println("PROJECT TEAM: " + projectInfo.printProjectTeamInfo());
		System.out.println("PROJECT BUDGET: " + projectInfo.printProjectBudgetInfo());
		System.out.println("");
		System.out.println(" Project Team");
		System.out.println("___________________________________________________");

		US355ViewProjectTeamAndThenRemoveCollaboratorController controller = new US355ViewProjectTeamAndThenRemoveCollaboratorController(
				this.project);

		List<String> listOfProjectCollaboratorsName = new ArrayList<>();

		for (int i = 0; i < controller.getProjectTeamName().size(); i++) {
			String projectCollaboratorName = controller.getProjectTeamName().get(i);
			int indexName = i + 1;
			System.out.println(projectCollaboratorName);
			listOfProjectCollaboratorsName.add(String.valueOf(indexName));
		}
		System.out.println();
		System.out.println("\nChoose an collaborator to remove or choose an option:");
		System.out.println("_______________________________________________________");
		System.out.println("[B] Back");
		System.out.println("[M] MainMenu");
		System.out.println("[E] Exit \n");

		String option = scannerInput.nextLine().toUpperCase();

		// creation of a list with the options B,E and M
		List<String> listOfOptionsToCompare = new ArrayList<>();
		listOfOptionsToCompare.add("B");
		listOfOptionsToCompare.add("M");
		listOfOptionsToCompare.add("E");

		List<User> listOfUser = new ArrayList<User>();

		listOfUser = controller.getActiveProjectCollaboratorFromTeam();

		for (int i = 0; i < listOfUser.size(); i++) {
			if (option.equals(String.valueOf(i + 1))) {

				System.out.println("Are you sure you want to remove this collaborator?");
				System.out.println("\"y\" or \"n\"\n");
				String yesOrNo = scannerInput.nextLine().toUpperCase();

				while (!("n".equalsIgnoreCase(yesOrNo)) && !("y".equalsIgnoreCase(yesOrNo))) {
					System.out.println("\nInvalid answer. Try again (\"y\" or \"n\")");
					yesOrNo = scannerInput.nextLine();
				}
				if (yesOrNo.equalsIgnoreCase("y")) {
					if (controller.removeCollaboratorFromProjectTeam(listOfUser.get(i)) == true) {
						System.out.println("You removed the user from this Project.");
						this.viewProjectTeamAndThenRemoveCollaboratorUI(this.project, this.user);
					} else {
						System.out.println("Your request was not successful.");
						this.viewProjectTeamAndThenRemoveCollaboratorUI(this.project, this.user);
					}

				}

			} else if (option.equals("B")) {
				ProjectManagerMainMenuUI projectManagerMainMenuUI = new ProjectManagerMainMenuUI(this.user,
						this.project);
				projectManagerMainMenuUI.displayOptions();

			} else if (option.equals("M")) {
				MainMenuUI.mainMenu();
			} else if (option.equals("E")) {
				System.exit(0);
			}
			listOfOptionsToCompare.add(String.valueOf(i + 1));
		}

		// In case the user input is an invalid option, the console shows a message and
		// returns to the beginning of this same menu
		if (!(listOfOptionsToCompare.contains(option))) {
			System.out.println("Please choose a valid option: ");
			this.viewProjectTeamAndThenRemoveCollaboratorUI(this.project, this.user);
		}
	}
}
