package project.ui;

import java.util.Scanner;

import project.controller.PrintProjectInfoController;
import project.model.Project;
import project.model.Task;
import project.model.User;

public class ProjectViewMenuUI {

	private Project project;
	private User user;
	private Task task;
	
	public ProjectViewMenuUI(Project project, User user) {

		this.project = project;
		this.user = user;
	}

	public void projectDataDisplay() {

		PrintProjectInfoController projectInfo = new PrintProjectInfoController(project);

		Scanner scannerInput = new Scanner(System.in);
		boolean condition = true;

		while (condition) {
			System.out.println("PROJECT " + projectInfo.printProjectNameInfo());

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
			System.out.println("TASKS OF " + projectInfo.printProjectNameInfo() + ":");

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
				int taskPosition = projectInfo.getTasksIDs().indexOf(choice);
				if(taskPosition >= 0) {
					Task choosedTask = projectInfo.getTasks().get(taskPosition);
					TaskDetailsUI userTasks = new TaskDetailsUI(choosedTask);
					userTasks.taskDataDisplay();
				}				
				else {
					System.out.println("Please choose a valid option: ");
					System.out.println("");
					ProjectViewMenuUI myAtualUIView = new ProjectViewMenuUI(project, user);
					myAtualUIView.projectDataDisplay();
				}
				break;
			}
		}
	}
}
