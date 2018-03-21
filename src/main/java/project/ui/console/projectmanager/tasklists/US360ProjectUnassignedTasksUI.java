package project.ui.console.projectmanager.tasklists;

import project.controller.PrintProjectInfoController;
import project.controller.US360GetProjectTasksWithoutCollaboratorsAssignedController;
import project.model.Project;
import project.model.User;
import project.ui.console.projectmanager.tasks.PmTaskFunctionalitiesUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class US360ProjectUnassignedTasksUI {

	public void projectUnassignedTasksUI(Project project, User user) {
		

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
		System.out.println("                 UNASSIGNED TASKS");
		System.out.println(line);

		US360GetProjectTasksWithoutCollaboratorsAssignedController controller = new US360GetProjectTasksWithoutCollaboratorsAssignedController();

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
				PmTaskFunctionalitiesUI taskFuntionatities = new PmTaskFunctionalitiesUI(ii, project, user);
				taskFuntionatities.taskDataDisplay();
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

}}
