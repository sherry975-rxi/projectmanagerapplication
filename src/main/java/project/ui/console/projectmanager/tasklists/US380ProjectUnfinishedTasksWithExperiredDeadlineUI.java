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
	private PrintProjectInfoController projectInfo;

	@Autowired
	private US380GetProjectExpiredTaskListController controller;

	public void displayUnfinishedTasksWithExpiredDeadline(Project project) {
		Scanner scannerInput = new Scanner(System.in);
		String line = "___________________________________________________";

		projectInfo.setProject(project);

		boolean loop = true;
		while (loop) {
			loop = false;
			System.out.println();
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
			System.out.println();
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
