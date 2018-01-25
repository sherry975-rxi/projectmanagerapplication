package project.ui.console.uiProjectManager.others;

import java.util.Scanner;

import project.controller.CalculateReportedProjectCostController;
import project.controller.PrintProjectInfoController;
import project.model.Project;
import project.model.User;
import project.ui.console.MainMenuUI;

public class US390GetProjectReportedCostUI {

	// Integer projectID
	// Display projectCost

	private Project project;
	private User user;

	public void displayProjectCost(Project project, User user) {

		this.project = project;
		this.user = user;
		String line = "___________________________________________________";

		Scanner scannerInput = new Scanner(System.in);

		PrintProjectInfoController projectInfo = new PrintProjectInfoController(this.project);

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
		System.out.println("     PROJECT COST");
		System.out.println(line);

		CalculateReportedProjectCostController controller = new CalculateReportedProjectCostController();

		System.out.println();

		System.out.println("The reported project cost until now is:");
		System.out.println();
		System.out.println(controller.calculateReportedProjectCostController(this.project));
		System.out.println();
		System.out.println("The reported cost to each task of the project is:");
		System.out.println();
		int i = 0;

		for (String other : controller.calculeReportedCostOfEachTaskController(project)) {
			System.out.print("TaskID " + controller.getTaskId(project).get(i) + " " + "  Cost: " + other);
			i++;
			System.out.println();
		}

		System.out.println(line);
		System.out.println("[M] MainMenu");
		System.out.println("[E] Exit \n");

		String option = scannerInput.nextLine().toUpperCase();

		if (option.equals("M")) {
			MainMenuUI.mainMenu();
		} else if (option.equals("E")) {
			System.exit(0);
		} else {
			System.out.println("Please choose a valid option: ");
			this.displayProjectCost(this.project, this.user);
		}

	}
}
