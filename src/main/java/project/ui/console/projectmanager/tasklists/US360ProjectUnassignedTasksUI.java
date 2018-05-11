package project.ui.console.projectmanager.tasklists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.PrintProjectInfoController;
import project.controllers.US360GetProjectTasksWithoutCollaboratorsAssignedController;
import project.model.Project;
import project.ui.console.projectmanager.tasks.PmTaskFunctionalitiesUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class US360ProjectUnassignedTasksUI {

	@Autowired
	private PrintProjectInfoController projectInfoDetails;

	@Autowired
	private US360GetProjectTasksWithoutCollaboratorsAssignedController controller;

	@Autowired
	private PmTaskFunctionalitiesUI taskFuntionatities;

	public void projectUnassignedTasksUI(Project project) {
		

		Scanner scannerInput = new Scanner(System.in);
		String line = "___________________________________________________";
		projectInfoDetails.setProject(project);

		boolean loop = true;
		while (loop) {
			loop = false;
            System.out.println(line + "\n");
		System.out.println("- PROJECT " + projectInfoDetails.printProjectNameInfo().toUpperCase());
		System.out.println(line);
		System.out.println("- ID: " + projectInfoDetails.printProjectIDCodeInfo());
		System.out.println("- STATUS: " + projectInfoDetails.printProjectStatusInfo());
		System.out.println("- DESCRIPTION: " + projectInfoDetails.printProjectDescriptionInfo());
		System.out.println("- START DATE: " + projectInfoDetails.printProjectStartDateInfo());
		System.out.println("- FINISH DATE: " + projectInfoDetails.printProjectFinishDateInfo());
		System.out.println("- PROJECT MANAGER: " + projectInfoDetails.printProjectManagerInfo());
		System.out.println("- PROJECT TEAM: " + projectInfoDetails.printProjectTeamInfo());
		System.out.println("- PROJECT BUDGET: " + projectInfoDetails.printProjectBudgetInfo());
            System.out.println(line);
		System.out.println(line + "\n");
		System.out.println("                -VISITANT TASKS");
		System.out.println(line);

		List<String> listOfExpiredTaskID = new ArrayList<>();

		for (int i = 0; i < controller.getProjectNotAssignedTaskList(project).size(); i++) {
			String taskInfo = controller.getProjectNotAssignedTaskList(project).get(i);
			System.out.println(taskInfo);
			listOfExpiredTaskID.add(controller.getProjectNotAssigned(project).get(i).getTaskID());
		}

		System.out.println(line);
		System.out.println("[B] Back \n");

		String option = scannerInput.nextLine().toUpperCase();

		// creation of a list with the options B,E and M
		List<String> listOfOptionsToCompare = new ArrayList<>();
		listOfOptionsToCompare.add("B");

		for (String ii : listOfExpiredTaskID) {

			if (option.equals(ii)) {
				taskFuntionatities.setTaskID(ii);
				taskFuntionatities.taskDataDisplay();
				taskFuntionatities.setProject(project);
			} 
			listOfOptionsToCompare.add(ii);
		}

		// In case the user input is an invalid option, the console shows a message and
		// returns to the beginning of this same menu
		if (!(listOfOptionsToCompare.contains(option))) {
			System.out.println("Please choose a valid option: ");
			loop = true;
		}
	}

    }
}
