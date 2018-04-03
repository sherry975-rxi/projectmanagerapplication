package project.ui.console.projectmanager.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.PrintProjectInfoController;
import project.controllers.US355ViewProjectTeamAndThenRemoveCollaboratorController;
import project.model.Project;
import project.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class US355ViewProjectTeamAndThenRemoveCollaboratorUI {

	@Autowired
	private PrintProjectInfoController projectInfo;

	@Autowired
	private US355ViewProjectTeamAndThenRemoveCollaboratorController controller;

	public void viewProjectTeamAndThenRemoveCollaboratorUI(Project project, User user) {
		

		Scanner scannerInput = new Scanner(System.in);

		projectInfo.setProject(project);

		boolean loop = true;
		while (loop) {
			loop = false;
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

		controller.setProj(project);

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
		System.out.println("[B] Back \n");

		String option = scannerInput.nextLine().toUpperCase();

		// creation of a list with the options B,E and M
		List<String> listOfOptionsToCompare = new ArrayList<>();
		listOfOptionsToCompare.add("B");

		List<User> listOfUser = controller.getActiveProjectCollaboratorFromTeam();

		for (int i = 0; i < listOfUser.size(); i++) {
			if (option.equals(String.valueOf(i + 1))) {

				System.out.println("Are you sure you want to remove this collaborator?");
				System.out.println("\"y\" or \"n\"\n");
				String yesOrNo = scannerInput.nextLine().toUpperCase();

				while (!("n".equalsIgnoreCase(yesOrNo)) && !("y".equalsIgnoreCase(yesOrNo))) {
					System.out.println("\nInvalid answer. Try again (\"y\" or \"n\")");
					yesOrNo = scannerInput.nextLine();
				}
				collaboratorRemovalUI(yesOrNo, listOfUser, i);

			} else if ("B".equals(option)) {
				break;

			}
			listOfOptionsToCompare.add(String.valueOf(i + 1));
		}

		// In case the user input is an invalid option, the console shows a message and
		// returns to the beginning of this same menu
		if (!(listOfOptionsToCompare.contains(option))) {
			System.out.println("Please choose a valid option: ");
			loop = true;
		}
		}
	}

	private void collaboratorRemovalUI(String yesOrNo, List<User> listOfUser, int i) {
		if ("y".equalsIgnoreCase(yesOrNo)) {
			if (controller.removeCollaboratorFromProjectTeam(listOfUser.get(i))) {
				System.out.println("You removed the user from this Project.");
			} else {
				System.out.println("Your request was not successful.");
			}

		}
	}
}
