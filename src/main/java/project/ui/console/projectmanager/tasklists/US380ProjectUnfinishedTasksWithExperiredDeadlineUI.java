package project.ui.console.projectmanager.tasklists;

import java.util.Scanner;

import project.controller.PrintProjectInfoController;
import project.controller.US380GetProjectExpiredTaskListController;
import project.model.Project;
import project.model.User;
import project.ui.console.MainMenuUI;

public class US380ProjectUnfinishedTasksWithExperiredDeadlineUI {

	public void displayUnfinishedTasksWithExpiredDeadline(Project project, User user) {

		Scanner scannerInput = new Scanner(System.in);

		PrintProjectInfoController projectInfo = new PrintProjectInfoController(project);

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
		System.out.println("___________________________________________________");
		System.out.println("     UNFINISHED TASKS WITH EXPIRED DEADLINE");
		System.out.println("___________________________________________________");

		US380GetProjectExpiredTaskListController controller = new US380GetProjectExpiredTaskListController();

		for (int i = 0; i < controller.getUnfinishedTaskListWithExpiredDeadline(project).size(); i++) {
			String taskInfo = controller.getUnfinishedTaskListWithExpiredDeadline(project).get(i);
			System.out.println(taskInfo);
		}

		System.out.println("___________________________________________________");
		System.out.println("[B] Back");
		System.out.println("[M] MainMenu");

		String option = scannerInput.nextLine().toUpperCase();

		switch (option) {

		case ("B"):
			return;

		case ("M"):
			MainMenuUI.mainMenu();
			break;

		default:
			System.out.println("Please choose a valid option: ");
			this.displayUnfinishedTasksWithExpiredDeadline(project, user);
			break;
		}

	}

}
