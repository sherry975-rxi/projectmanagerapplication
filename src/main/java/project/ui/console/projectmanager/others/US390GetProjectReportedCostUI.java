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

	public void displayProjectCost(Project project) {
		String line = "___________________________________________________";

		Scanner scannerInput = new Scanner(System.in);

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
	}}
}
