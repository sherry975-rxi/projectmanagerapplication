package project.ui.console.projectmanager.tasklists;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import project.controller.PrintProjectInfoController;
import project.controller.US372GetProjectUnfinishedTaskListController;
import project.model.Project;
import project.model.User;
import project.ui.console.MainMenuUI;
import project.ui.console.projectmanager.ProjectManagerMainMenuUI;
import project.ui.console.projectmanager.tasks.PmTaskFunctionalitiesUI;

public class US372ProjectUnfinishedTasksUI {

	private Project project;
	private User user;

	public void displayUnfinishedOfProject(Project project, User user) {

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
		System.out.println("PROJECT MANAGER: " + projectInfo.printProjectManagerInfo());
		System.out.println("PROJECT TEAM: " + projectInfo.printProjectTeamInfo());
		System.out.println("PROJECT BUDGET: " + projectInfo.printProjectBudgetInfo());
		System.out.println("");
		System.out.println(line);
		System.out.println("     UNFINISHED TASKS");
		System.out.println(line);

		US372GetProjectUnfinishedTaskListController controller = new US372GetProjectUnfinishedTaskListController();

		List<String> listOfOnGoingTasks = new ArrayList<>();

		for (int i = 0; i < controller.getProjectUnfinishedTaskList(this.project).size(); i++) {
			String taskInfo = controller.getUnfinishedTaskListId(this.project).get(i);
			System.out.println(taskInfo);
			listOfOnGoingTasks.add(controller.getProjectUnfinishedTaskList(this.project).get(i).getTaskID());
		}
		System.out.println();
		System.out.println("Please choose a task to see more options:");
		System.out.println(line);
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
			} else if ("B".equals(option)) {
				ProjectManagerMainMenuUI projectManagerMainMenuUI = new ProjectManagerMainMenuUI(this.user,
						this.project);
				projectManagerMainMenuUI.displayOptions();

			} else if ("M".equals(option)) {
				MainMenuUI.mainMenu();
			} else if ("E".equals(option)) {
				System.out.println("----YOU HAVE EXIT FROM APPLICATION----");
				break;
			}
			listOfOptionsToCompare.add(ii);
		}

		// In case the user input is an invalid option, the console shows a message and
		// returns to the beginning of this same menu
		if (!(listOfOptionsToCompare.contains(option))) {
			System.out.println("Please choose a valid option: ");
			this.displayUnfinishedOfProject(project, user);
		}
	}

}
