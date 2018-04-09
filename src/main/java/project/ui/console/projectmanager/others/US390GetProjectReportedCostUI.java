package project.ui.console.projectmanager.others;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.PrintProjectInfoController;
import project.controllers.US390CalculateReportedProjectCostController;
import project.model.Project;

import java.util.Scanner;

@Component
public class US390GetProjectReportedCostUI {

	@Autowired
	private PrintProjectInfoController projectInfo;

	@Autowired
	private US390CalculateReportedProjectCostController controller;

	public final int FIRST_COLLABORATOR = 1;
	public final int LAST_COLLABORATOR = 2;
	public final int FIRST_LAST_COLLABORATOR = 3;
	public final int AVERAGE_COLLABORATOR = 4;

	Scanner scannerInput;

	public void displayProjectCost(Project project) {
		String line = "___________________________________________________";

		scannerInput = new Scanner(System.in);

		projectInfo.setProject(project);

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

		// TODO Create controller that calculates cost using the chosen method

		System.out.println("     PROJECT COST");
		System.out.println(line);

		System.out.println();

		System.out.println("The reported project cost until now is:");
		System.out.println();
		System.out.println(controller.calculateReportedProjectCostController(project));
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
		System.out.println("[B] Back \n");

		String option = scannerInput.nextLine().toUpperCase();

			if (!("B".equals(option))){
				System.out.println("Please choose a valid option: ");
				loop = true;
			}
		}
	}

	private int selectReportCostCalculation() {
		System.out.println("");
		System.out.println("Should a single user have different costs through the same report, calculate using:");
		System.out.println("");
        System.out.println("[1] - The user's first cost");
        System.out.println("[2] - The user's last cost");
        System.out.println("[3] - Average between the users first and last cost");
        System.out.println("[Any Key] - Average between all of the user's costs");
        System.out.println("");
        scannerInput = new Scanner(System.in);
		char option = scannerInput.nextLine().charAt(0);

		switch(option) {
            case '1':
                System.out.println("First Cost Selected!");
                return this.FIRST_COLLABORATOR;
            case '2':
                System.out.println("Last Cost Selected!");
                return this.LAST_COLLABORATOR;
            case '3':
                System.out.println("First/Last Average Cost Selected!");
                return this.FIRST_LAST_COLLABORATOR;
            default:
                System.out.println("Average Cost Selected!");
                return this.AVERAGE_COLLABORATOR;
        }


	}

}
