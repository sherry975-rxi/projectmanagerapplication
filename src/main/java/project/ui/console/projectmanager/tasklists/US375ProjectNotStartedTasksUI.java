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
	private PrintProjectInfoController projectInfo;

	@Autowired
	private PmTaskFunctionalitiesUI taskFuntionatities;

	@Autowired
	private US375GetProjectNotStartedTaskListController controller;

	public void projectNotStartedTasksUI(Project project) {
		

		Scanner scannerInput = new Scanner(System.in);
		String line = "___________________________________________________";

		projectInfo.setProject(project);

		boolean loop = true;
		while (loop) {
			loop = false;
		System.out.println("");
		System.out.println("PROJECT " + projectInfo.printProjectNameInfo().toUpperCase());

		System.out.println("ID: " + projectInfo.printProjectIDCodeInfo());
		System.out.println("STATUS: " + projectInfo.printProjectStatusInfo());
		System.out.println("DESCRIPTION: " + projectInfo.printProjectDescriptionInfo());
		System.out.println("START DATE: " + projectInfo.printProjectStartDateInfo());
		System.out.println("FINISH DATE: " + projectInfo.printProjectFinishDateInfo());
		System.out.println("PROJECT MANAGER: " + projectInfo.printProjectManagerInfo());
		System.out.println("PROJECT TEAM: " + projectInfo.printProjectTeamInfo());
		System.out.println("PROJECT BUDGET: " + projectInfo.printProjectBudgetInfo());

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
