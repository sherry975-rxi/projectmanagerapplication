package project.ui.console.projectmanager.tasklists;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import project.controller.PrintProjectInfoController;
import project.controller.US360GetProjectTasksWithoutCollaboratorsAssignedController;
import project.model.Project;
import project.model.User;
import project.ui.console.MainMenuUI;
import project.ui.console.projectmanager.tasks.PmTaskFunctionalitiesUI;

public class US360ProjectUnassignedTasksUI {

	public void projectUnassignedTasksUI(Project project, User user) {

		Scanner scannerInput = new Scanner(System.in);

		PrintProjectInfoController projectInfo = new PrintProjectInfoController(project);

		System.out.println("");
		System.out.println("PROJECT " + projectInfo.printProjectNameInfo().toUpperCase());
		System.out.println("___________________________________________________");
		System.out.println("ID: " + projectInfo.printProjectIDCodeInfo());
		System.out.println("STATUS: " + projectInfo.printProjectStatusInfo());
		System.out.println("DESCRIPTION: " + projectInfo.printProjectDescriptionInfo());
		System.out.println("START DATE: " + projectInfo.printProjectStartDateInfo());
		System.out.println("FINISH DATE: " + projectInfo.printProjectFinishDateInfo());
		System.out.println("PROJECT MANAGER: " + projectInfo.printProjectManagerInfo());
		System.out.println("PROJECT TEAM: " + projectInfo.printProjectTeamInfo());
		System.out.println("PROJECT BUDGET: " + projectInfo.printProjectBudgetInfo());
		System.out.println("");
		System.out.println("___________________________________________________");
		System.out.println("                 UNASSIGNED TASKS");
		System.out.println("___________________________________________________");

		US360GetProjectTasksWithoutCollaboratorsAssignedController controller = new US360GetProjectTasksWithoutCollaboratorsAssignedController();

		List<String> listOfExpiredTaskID = new ArrayList<>();

		for (int i = 0; i < controller.getProjectNotAssignedTaskList(project).size(); i++) {
			String taskInfo = controller.getProjectNotAssignedTaskList(project).get(i);
			System.out.println(taskInfo);
			listOfExpiredTaskID.add(controller.getProjectNotAssigned(project).get(i).getTaskID());
		}

		System.out.println("___________________________________________________");
		System.out.println("[B] Back");
		System.out.println("[M] MainMenu");
		System.out.println("[E] Exit \n");

		String option = scannerInput.nextLine().toUpperCase();

		// creation of a list with the options B,E and M
		List<String> listOfOptionsToCompare = new ArrayList<>();
		listOfOptionsToCompare.add("B");
		listOfOptionsToCompare.add("M");
		listOfOptionsToCompare.add("E");

		for (String ii : listOfExpiredTaskID) {

			if (option.equals(ii)) {
				PmTaskFunctionalitiesUI taskFuntionatities = new PmTaskFunctionalitiesUI(ii, project, user);
				taskFuntionatities.taskDataDisplay();
			} else if ("B".equals(option)) {
				return;

			} else if ("M".equals(option)) {
				MainMenuUI.mainMenu();
			}
			listOfOptionsToCompare.add(ii);
		}

		// In case the user input is an invalid option, the console shows a message and
		// returns to the beginning of this same menu
		if (!(listOfOptionsToCompare.contains(option))) {
			System.out.println("Please choose a valid option: ");
			this.projectUnassignedTasksUI(project, user);
		}
	}

}
