package project.ui.console.projectmanager.tasklists;

import project.controller.PrintProjectInfoController;
import project.controller.US377CollectionOfCancelledTasksFromAProjectController;
import project.controller.UpdateDbToContainersController;
import project.model.Project;
import project.model.User;
import project.ui.console.MainMenuUI;
import project.ui.console.projectmanager.ProjectManagerMainMenuUI;
import project.ui.console.projectmanager.tasks.PmTaskFunctionalitiesUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class US377ProjectCancelledTasks {


	public void displayCancelledTasksOfProject(Project project, User user) {
		UpdateDbToContainersController infoUpdater = new UpdateDbToContainersController();
		


		String line = "___________________________________________________";

		Scanner scannerInput = new Scanner(System.in);

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
		System.out.println("PROJECT MANAGER: " + projectInfo.printProjectManagerInfo());
		System.out.println("PROJECT TEAM: " + projectInfo.printProjectTeamInfo());
		System.out.println("PROJECT BUDGET: " + projectInfo.printProjectBudgetInfo());
		System.out.println("");
		System.out.println(line);
		System.out.println("     CANCELLED TASKS");
		System.out.println(line);

		US377CollectionOfCancelledTasksFromAProjectController controller = new US377CollectionOfCancelledTasksFromAProjectController(
				project);

		List<String> listOfCancelledTasks = new ArrayList<>();

		for (int i = 0; i < controller.getCancelledTasksFromAProject().size(); i++) {
			String taskInfo = controller.getCancelledTaskListId(project).get(i);
			System.out.println(taskInfo);
			listOfCancelledTasks.add(controller.getCancelledTasksFromAProject().get(i).getTaskID());
		}

		System.out.println(line);
		System.out.println("[B] Back \n");
		String option = scannerInput.nextLine().toUpperCase();

		// creation of a list with the options B,E and M
		List<String> listOfOptionsToCompare = new ArrayList<>();
		listOfOptionsToCompare.add("B");

		for (String ii : listOfCancelledTasks) {

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
	}
}
