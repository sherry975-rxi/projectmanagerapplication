package project.ui.console.uiProjectManager.taskLists;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import project.controller.PrintProjectInfoController;
import project.controller.US372GetProjectUnfinishedTaskListController;
import project.model.Project;
import project.model.User;
import project.ui.console.MainMenuUI;
import project.ui.console.uiProjectManager.ProjectManagerMainMenuUI;
import project.ui.console.uiProjectManager.tasks.PmTaskFunctionalitiesUI;

public class US372ProjectOngoingTasksUI {

	private Project project;
	private User user;

	public void displayOnGoingTasksOfProject(Project project, User user) {

		this.project = project;
		this.user = user;

		Scanner scannerInput = new Scanner(System.in);

		PrintProjectInfoController projectInfo = new PrintProjectInfoController(this.project);

		System.out.println("");
		System.out.println("PROJECT " + projectInfo.printProjectNameInfo().toUpperCase());
		System.out.println("___________________________________________________");
		System.out.println("ID: " + projectInfo.printProjectIDCodeInfo());
		System.out.println("STATUS: " + projectInfo.printProjectStatusInfo());
		System.out.println("DESCRIPTION: " + projectInfo.printProjectDescriptionInfo());
		System.out.println("START DATE: " + projectInfo.printProjectStartDateInfo());
		System.out.println("PROJECT MANAGER: " + projectInfo.printProjectManagerInfo());
		System.out.println("PROJECT TEAM: " + projectInfo.printProjectTeamInfo());
		System.out.println("PROJECT BUDGET: " + projectInfo.printProjectBudgetInfo());
		System.out.println("");
		System.out.println("___________________________________________________");
		System.out.println("     ON GOING TASKS");
		System.out.println("___________________________________________________");

		US372GetProjectUnfinishedTaskListController controller = new US372GetProjectUnfinishedTaskListController();

		List<String> listOfOnGoingTasks = new ArrayList<>();

		for (int i = 0; i < controller.getProjectUnfinishedTaskList(this.project).size(); i++) {
			String taskInfo = controller.getOnGoingTaskListId(this.project).get(i);
			System.out.println(taskInfo);
			listOfOnGoingTasks.add(controller.getProjectUnfinishedTaskList(this.project).get(i).getTaskID());
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

		for (String ii : listOfOnGoingTasks) {

			if (option.equals(ii)) {

				PmTaskFunctionalitiesUI taskFuntionatities = new PmTaskFunctionalitiesUI(ii, this.project, this.user);
				taskFuntionatities.taskDataDisplay();
			} else if (option.equals("B")) {
				ProjectManagerMainMenuUI projectManagerMainMenuUI = new ProjectManagerMainMenuUI(this.user,
						this.project);
				projectManagerMainMenuUI.displayOptions();

			} else if (option.equals("M")) {
				MainMenuUI.mainMenu();
			} else if (option.equals("E")) {
				System.exit(0);
			}
			listOfOptionsToCompare.add(ii);
		}

		// In case the user input is an invalid option, the console shows a message and
		// returns to the beginning of this same menu
		if (!(listOfOptionsToCompare.contains(option))) {
			System.out.println("Please choose a valid option: ");
			this.displayOnGoingTasksOfProject(project, user);
		}
	}

}
