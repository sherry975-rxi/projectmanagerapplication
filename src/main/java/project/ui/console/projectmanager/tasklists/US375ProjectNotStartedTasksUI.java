package project.ui.console.projectmanager.tasklists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.PrintProjectInfoController;
import project.controllers.US375GetProjectNotStartedTaskListController;
import project.model.Project;
import project.ui.console.projectmanager.tasks.PmTaskFunctionalitiesUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class US375ProjectNotStartedTasksUI {

	@Autowired
	private PrintProjectInfoController projectDetailsInfo;

	@Autowired
	private PmTaskFunctionalitiesUI taskFuntionatities;

	@Autowired
	private US375GetProjectNotStartedTaskListController controller;

	public void projectNotStartedTasksUI(Project project) {
		

		Scanner scannerInput = new Scanner(System.in);
		String line = "___________________________________________________";

		projectDetailsInfo.setProject(project);

		boolean loop = true;
		while (loop) {
			loop = false;
		System.out.println("");
		System.out.println("PROJECT " + projectDetailsInfo.printProjectNameInfo().toUpperCase());

		System.out.println("ID: " + projectDetailsInfo.printProjectIDCodeInfo());
		System.out.println("STATUS: " + projectDetailsInfo.printProjectStatusInfo());
		System.out.println("DESCRIPTION: " + projectDetailsInfo.printProjectDescriptionInfo());
		System.out.println("START DATE: " + projectDetailsInfo.printProjectStartDateInfo());
		System.out.println("FINISH DATE: " + projectDetailsInfo.printProjectFinishDateInfo());
		System.out.println("PROJECT MANAGER: " + projectDetailsInfo.printProjectManagerInfo());
		System.out.println("PROJECT TEAM: " + projectDetailsInfo.printProjectTeamInfo());
		System.out.println("PROJECT BUDGET: " + projectDetailsInfo.printProjectBudgetInfo());

		System.out.println(line);
		System.out.println("                NOT STARTED TASKS");
		System.out.println(line);


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
				taskFuntionatities.setTaskID(ii);
				taskFuntionatities.setProject(project);
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
