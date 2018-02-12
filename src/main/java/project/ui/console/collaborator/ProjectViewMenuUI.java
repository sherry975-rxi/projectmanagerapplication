package project.ui.console.collaborator;

import java.util.Scanner;

import project.controller.PrintProjectInfoController;
import project.model.User;
import project.ui.console.MainMenuUI;

public class ProjectViewMenuUI {

	private Integer projectID;
	private User user;
	private String taskID;
	private Boolean isPreviousUIFromTasks;
	
	public ProjectViewMenuUI(Integer projectID, User user) {
		this.user = user;
		this.projectID = projectID;	
	}

	/**
	 * This method executes all options to execute through this UI Presents the
	 * project details and the task's list of project Uses a switch case to treat
	 * the user's input
	 */
	public void projectDataDisplay() {

		PrintProjectInfoController projectInfo = new PrintProjectInfoController(this.projectID);
		projectInfo.setProject();

		Scanner scannerInput = new Scanner(System.in);

		System.out.println("");
		System.out.println("PROJECT " + projectInfo.printProjectNameInfo().toUpperCase());
		System.out.println("______________________________________________");
		System.out.println("ID: " + projectInfo.printProjectIDCodeInfo());
		System.out.println("STATUS: " + projectInfo.printProjectStatusInfo());
		System.out.println("DESCRIPTION: " + projectInfo.printProjectDescriptionInfo());
		System.out.println("START DATE: " + projectInfo.printProjectStartDateInfo());
		System.out.println("FINISH DATE: " + projectInfo.printProjectFinishDateInfo());
		System.out.println("PROJECT MANAGER: " + projectInfo.printProjectManagerInfo());
		System.out.println("PROJECT TEAM: " + projectInfo.printProjectTeamInfo());
		System.out.println("PROJECT BUDGET: " + projectInfo.printProjectBudgetInfo());
		System.out.println("");
		System.out.println("TASKS OF " + projectInfo.printProjectNameInfo().toUpperCase() + ":");

		for (int i = 0; i < projectInfo.getProjectTaskList().size(); i++) {
			System.out.println(projectInfo.getProjectTaskList().get(i));
		}

		System.out.println("To see task's details, choose the task ID number.");
		System.out.println("");
		System.out.println("[B] Back");
		System.out.println("[M] MainMenu");
		System.out.println("[E] Exit");

		String choice = scannerInput.nextLine().toUpperCase();
		switch (choice) {
		case "B":
			CollectProjectsFromUserUI previousMenu = new CollectProjectsFromUserUI(user);
			previousMenu.collectProjectsFromUser();
			break;
		case "M":
			MainMenuUI.mainMenu();
			break;
		case "E":
			System.out.println("----YOU HAVE EXIT FROM APPLICATION----");
			System.exit(0);
			break;
		default:
			try {
				this.isPreviousUIFromTasks = true; 
				TaskDetailsUI userTasks = new TaskDetailsUI(choice, this.projectID, this.user, this.isPreviousUIFromTasks);
				userTasks.taskDataDisplay();
			}
			catch (NullPointerException npe) {
				System.out.println("Please choose a valid option: ");
				System.out.println("");
				ProjectViewMenuUI myAtualUIView = new ProjectViewMenuUI(projectID, user);
				myAtualUIView.projectDataDisplay();
			}

			break;
		}
	}
}
