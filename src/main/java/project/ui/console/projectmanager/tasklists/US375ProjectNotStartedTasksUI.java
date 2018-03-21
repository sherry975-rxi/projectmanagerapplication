package project.ui.console.projectmanager.tasklists;

import project.controller.PrintProjectInfoController;
import project.controller.US375GetProjectNotStartedTaskListController;
import project.controller.UpdateDbToContainersController;
import project.model.Project;
import project.model.User;
import project.ui.console.MainMenuUI;
import project.ui.console.projectmanager.ProjectManagerMainMenuUI;
import project.ui.console.projectmanager.tasks.PmTaskFunctionalitiesUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class US375ProjectNotStartedTasksUI {

	public void projectNotStartedTasksUI(Project project, User user) {
		UpdateDbToContainersController infoUpdater = new UpdateDbToContainersController();
		

		Scanner scannerInput = new Scanner(System.in);
		String line = "___________________________________________________";

		PrintProjectInfoController projectInfo = new PrintProjectInfoController(project);

		boolean loop = true;
		while (loop) {
			loop = false;
			infoUpdater.updateDBtoContainer();
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
		System.out.println("                NOT STARTED TASKS");
		System.out.println(line);

		US375GetProjectNotStartedTaskListController controller = new US375GetProjectNotStartedTaskListController();

		List<String> listOfExpiredTaskID = new ArrayList<>();

		for (int i = 0; i < controller.getProjectNotStartedTasks(project).size(); i++) {
			String taskInfo = controller.getProjectNotStartedTaskList(project).get(i);
			System.out.println(taskInfo);
			listOfExpiredTaskID.add(controller.getProjectNotStartedTasks(project).get(i).getTaskID());
		}

		System.out.println(line);
		System.out.println("[B] Back \n");

		String option = scannerInput.nextLine().toUpperCase();

		// creation of a list with the options B,E and M
		List<String> listOfOptionsToCompare = new ArrayList<>();
		listOfOptionsToCompare.add("B");

		for (String ii : listOfExpiredTaskID) {

			if ((ii.equals(option))) {
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
