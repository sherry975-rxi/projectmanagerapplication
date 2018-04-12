package project.ui.console.projectmanager.others;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.PrintProjectInfoController;
import project.controllers.US390CalculateReportedProjectCostController;
import project.model.Project;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class US390GetProjectReportedCostUI {

	@Autowired
	private PrintProjectInfoController projectInfo;

	@Autowired
	private US390CalculateReportedProjectCostController controller;

	Scanner scannerInput;

	public Project displayProjectCost(Project project) {
		String line = "___________________________________________________";

		projectInfo.setProject(project);

		boolean loop = true;
		while (loop) {
			scannerInput = new Scanner(System.in);
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

        System.out.print(selectReportCostCalculation(project));
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

		}

		return project;
	}

	private String selectReportCostCalculation(Project project) {
		System.out.println("");
		System.out.println("Should a single user have different costs throughout the same report, calculate using:");
        System.out.println(projectInfo.printCostCalculationMethods());
        System.out.println("[Any Key] - Keep Current Selection");
        System.out.println("");
        scannerInput = new Scanner(System.in);

		String invalid = "Invalid Input, using current selection.";
		String valid = "Selected ";
		String result;

		Integer selectedMethod;

		try{
			selectedMethod = scannerInput.nextInt();
			if(project.isCalculationMethodAllowed(selectedMethod)) {
			    controller.selectReportCostCalculation(project, selectedMethod);
				result = valid + selectedMethod;
			} else {
				result = selectedMethod + ": " + invalid;
			}
			scannerInput.nextLine();

		} catch (InputMismatchException i) {
			result = scannerInput.nextLine() + ": " + invalid;
		}


        return result;


	}

}
