package project.ui.console.projectmanager.tasklists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controller.PrintProjectInfoController;
import project.controller.US372GetProjectUnfinishedTaskListController;
import project.model.Project;
import project.model.User;
import project.ui.console.projectmanager.tasks.PmTaskFunctionalitiesUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class US372ProjectUnfinishedTasksUI {

	@Autowired
	private PrintProjectInfoController projectInfo;

	@Autowired
	private US372GetProjectUnfinishedTaskListController controller;

	@Autowired
	private PmTaskFunctionalitiesUI taskFuntionatities;


	public void displayUnfinishedOfProject(Project project, User user) {


		String line = "___________________________________________________";

		Scanner scannerInput = new Scanner(System.in);

		projectInfo.setProject(project);

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
		System.out.println("PROJECT MANAGER: " + projectInfo.printProjectManagerInfo());
		System.out.println("PROJECT TEAM: " + projectInfo.printProjectTeamInfo());
		System.out.println("PROJECT BUDGET: " + projectInfo.printProjectBudgetInfo());
		System.out.println("");
		System.out.println(line);
		System.out.println("     UNFINISHED TASKS");
		System.out.println(line);

		List<String> listOfOnGoingTasks = new ArrayList<>();

		for (int i = 0; i < controller.getProjectUnfinishedTaskList(project).size(); i++) {
			String taskInfo = controller.getUnfinishedTaskListId(project).get(i);
			System.out.println(taskInfo);
			listOfOnGoingTasks.add(controller.getProjectUnfinishedTaskList(project).get(i).getTaskID());
		}
		System.out.println();
		System.out.println("Please choose a task to see more options:");
		System.out.println(line);
		System.out.println("[B] Back \n");

		String option = scannerInput.nextLine().toUpperCase();

		// creation of a list with the options B,E and M
		List<String> listOfOptionsToCompare = new ArrayList<>();
		listOfOptionsToCompare.add("B");

		for (String ii : listOfOnGoingTasks) {

			if (option.equals(ii)) {
				taskFuntionatities.setTaskID(ii);
				taskFuntionatities.setProject(project);
				taskFuntionatities.setUser(user);
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
