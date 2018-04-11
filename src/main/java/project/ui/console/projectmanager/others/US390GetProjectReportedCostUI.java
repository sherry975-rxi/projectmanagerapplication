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
        System.out.println("[1] - The user's first cost (ALWAYS OK)");
        System.out.println("[2] - The user's last cost " + project.isCalculationMethodAllowed(Project.LAST_COLLABORATOR));
        System.out.println("[3] - Average between the users first and last cost " + project.isCalculationMethodAllowed(Project.FIRST_LAST_COLLABORATOR));
		System.out.println("[4] - Average between all of the user's costs "+ project.isCalculationMethodAllowed(Project.AVERAGE_COLLABORATOR));
        System.out.println("[Any Key] - Keep Current (Currently: " + project.getCalculationMethod() + ")");
        System.out.println("");
        scannerInput = new Scanner(System.in);
		char option = scannerInput.nextLine().charAt(0);

		Integer selectedMethod =0;
		String result;

		switch(option) {
            case '1':
            	selectedMethod=Project.FIRST_COLLABORATOR;
                result= "Earliest Collaborator Cost Selected!";
                break;
            case '2':
				selectedMethod=Project.LAST_COLLABORATOR;
                result= "Latest Collaborator Cost Selected!";
                break;
            case '3':
				selectedMethod=Project.FIRST_LAST_COLLABORATOR;
                result= "First/Last Average Cost Selected!";
                break;
			case '4':
				selectedMethod=Project.AVERAGE_COLLABORATOR;
                result= "Average Collaborator Cost Selected!";
                break;
            default:
                result= "Cost calculation not changed.";
                break;

        }

        if(project.isCalculationMethodAllowed(selectedMethod)) {
            controller.selectReportCostCalculation(project, selectedMethod);
        } else {
		    result="This calculation method is unavailable!";
        }


        return result;


	}

}
