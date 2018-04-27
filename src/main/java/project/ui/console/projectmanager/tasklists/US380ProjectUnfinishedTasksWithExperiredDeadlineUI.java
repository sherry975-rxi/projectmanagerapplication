package project.ui.console.projectmanager.tasklists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.PrintProjectInfoController;
import project.controllers.US380GetProjectExpiredTaskListController;
import project.model.Project;

import java.util.Scanner;

@Component
public class US380ProjectUnfinishedTasksWithExperiredDeadlineUI {

	@Autowired
	private PrintProjectInfoController projectDetailsInfoController;

	@Autowired
	private US380GetProjectExpiredTaskListController controller;

	public void displayUnfinishedTasksWithExpiredDeadline(Project project) {
		Scanner scannerInput = new Scanner(System.in);
		String line = "___________________________________________________";

		projectDetailsInfoController.setProject(project);

		boolean loop = true;
		while (loop) {
			loop = false;
			System.out.println();
		System.out.println("PROJECT " + projectDetailsInfoController.printProjectNameInfo().toUpperCase());
		System.out.println(line);
		System.out.println("ID: " + projectDetailsInfoController.printProjectIDCodeInfo());
		System.out.println("STATUS: " + projectDetailsInfoController.printProjectStatusInfo());
		System.out.println("DESCRIPTION: " + projectDetailsInfoController.printProjectDescriptionInfo());
		System.out.println("START DATE: " + projectDetailsInfoController.printProjectStartDateInfo());
		System.out.println("FINISH DATE: " + projectDetailsInfoController.printProjectFinishDateInfo());
		System.out.println("PROJECT MANAGER: " + projectDetailsInfoController.printProjectManagerInfo());
		System.out.println("PROJECT TEAM: " + projectDetailsInfoController.printProjectTeamInfo());
		System.out.println("PROJECT BUDGET: " + projectDetailsInfoController.printProjectBudgetInfo());
			System.out.println(line);
		System.out.println(line);
		System.out.println("     UNFINISHED TASKS WITH EXPIRED DEADLINE");
		System.out.println(line);

		for (int i = 0; i < controller.getUnfinishedTaskListWithExpiredDeadline(project).size(); i++) {
			String taskInfo = controller.getUnfinishedTaskListWithExpiredDeadline(project).get(i);
			System.out.println(taskInfo);
		}

		System.out.println(line);
		System.out.println("[B] Back \n");

		String option = scannerInput.nextLine().toUpperCase();

			if (!"B".equalsIgnoreCase(option)) {
			System.out.println("Please choose a valid option: ");
			loop = true;
		}

		}
	}

}
