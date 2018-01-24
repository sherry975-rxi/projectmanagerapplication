package project.ui.uiProjectManager.uiPmTasksLists;

import java.util.Scanner;

import project.controller.PrintProjectInfoController;
import project.controller.US380GetProjectExpiredTaskListController;
import project.model.Project;
import project.ui.MainMenuUI;

public class US380ProjectUnfinishedTasksWithExperiredDeadlineUI {

	private Project project;

	public void displayUnfinishedTasksWithExperiredDeadline(Project project) {

		this.project = project;

		Scanner scannerInput = new Scanner(System.in);

		PrintProjectInfoController projectInfo = new PrintProjectInfoController(this.project);

		System.out.println("PROJECT - " + projectInfo.printProjectNameInfo());
		System.out.println("");
		System.out.println("                     TASK LIST                    ");
		System.out.println("___________________________________________________");

		US380GetProjectExpiredTaskListController controller = new US380GetProjectExpiredTaskListController();

		for (int i = 0; i < controller.getUnfinishedTaskListWithExpiredDeadline(this.project).size(); i++) {
			System.out.println(controller.getUnfinishedTaskListWithExpiredDeadline(this.project).get(i));
		}

		System.out.println("___________________________________________________");
		System.out.println("[B] Back");
		System.out.println("[M] MainMenu");
		System.out.println("[E] Exit \n");

		String option = scannerInput.nextLine().toUpperCase();

		switch (option) {

		case "B":
			// Menu3 menuThree = new Menu3();
			// // TODO when this menu is done is necessary to include a method.
			break;
		case "M":
			MainMenuUI.mainMenu();

			break;
		case "E":
			System.exit(0);
		}
	}

}
