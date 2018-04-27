package project.ui.console.projectmanager.tasklists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.PrintProjectInfoController;
import project.controllers.US377CollectionOfCancelledTasksFromAProjectController;
import project.model.Project;
import project.ui.console.projectmanager.tasks.PmTaskFunctionalitiesUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class US377ProjectCancelledTasks {

	@Autowired
	private PrintProjectInfoController projectInformationController;

	@Autowired
	private US377CollectionOfCancelledTasksFromAProjectController controller;

	@Autowired
	private PmTaskFunctionalitiesUI taskFuntionatities;

	public void displayCancelledTasksOfProject(Project project) {

		String line = "___________________________________________________";

		Scanner scannerInput = new Scanner(System.in);

		projectInformationController.setProject(project);

		boolean loop = true;
		while (loop) {
			loop = false;

		System.out.println("PROJECT " + projectInformationController.printProjectNameInfo().toUpperCase());
		System.out.println(line);
		System.out.println("ID: " + projectInformationController.printProjectIDCodeInfo());
		System.out.println("STATUS: " + projectInformationController.printProjectStatusInfo());
		System.out.println("DESCRIPTION: " + projectInformationController.printProjectDescriptionInfo());
		System.out.println("START DATE: " + projectInformationController.printProjectStartDateInfo());
		System.out.println("PROJECT MANAGER: " + projectInformationController.printProjectManagerInfo());
		System.out.println("PROJECT TEAM: " + projectInformationController.printProjectTeamInfo());
		System.out.println("PROJECT BUDGET: " + projectInformationController.printProjectBudgetInfo());


		System.out.println("     CANCELLED TASKS");


		controller.setProject(project);

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
				taskFuntionatities.setProject(project);
				taskFuntionatities.setTaskID(ii);
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
