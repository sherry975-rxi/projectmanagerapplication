package project.ui.console.projectmanager.tasklists;

import project.controller.PrintProjectInfoController;
import project.controller.US380GetProjectExpiredTaskListController;
import project.model.Project;
import project.model.User;

import java.util.Scanner;

public class US380ProjectUnfinishedTasksWithExperiredDeadlineUI {

	public void displayUnfinishedTasksWithExpiredDeadline(Project project, User user) {
		Scanner scannerInput = new Scanner(System.in);
		String line = "___________________________________________________";

		PrintProjectInfoController projectInfo = new PrintProjectInfoController(project);

		boolean loop = true;
		while (loop) {
			loop = false;
		System.out.println("");
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
		System.out.println("");
		System.out.println(line);
		System.out.println("     UNFINISHED TASKS WITH EXPIRED DEADLINE");
		System.out.println(line);

		US380GetProjectExpiredTaskListController controller = new US380GetProjectExpiredTaskListController();

		for (int i = 0; i < controller.getUnfinishedTaskListWithExpiredDeadline(project).size(); i++) {
			String taskInfo = controller.getUnfinishedTaskListWithExpiredDeadline(project).get(i);
			System.out.println(taskInfo);
		}

		System.out.println(line);
		System.out.println("[B] Back \n");

		String option = scannerInput.nextLine().toUpperCase();

		switch (option) {

		case ("B"):
			return;

		default:
			System.out.println("Please choose a valid option: ");
			loop = true;
			break;
		}

	}}

}
