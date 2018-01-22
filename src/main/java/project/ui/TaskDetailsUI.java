package project.ui;

import java.util.Scanner;

import project.controller.PrintTaskInfoController;
import project.model.Project;
import project.model.Task;
import project.model.User;

public class TaskDetailsUI {
	private User user;
	private Project project;
	private Task task;
	
	public TaskDetailsUI(Task task) {
		this.task = task;
	}

	public void taskDataDisplay() {
		PrintTaskInfoController taskInfo = new PrintTaskInfoController(task);
		
		Scanner scannerInput = new Scanner(System.in);
		boolean condition = true;
		
		while (condition) {
			System.out.println(taskInfo.printProjectNameInfo());
			System.out.println(taskInfo.printTaskNameInfo());
			System.out.println("______________________________________________");
			System.out.println("ID: " + taskInfo.printTaskIDCodeInfo());
			System.out.println("STATUS: " + taskInfo.printTaskStateInfo());
			System.out.println("ESTIMATED START DATE: " + taskInfo.printTaskEstimatedStartDateInfo());
			System.out.println("START DATE: " + taskInfo.printTaskStateInfo());
			System.out.println("DEADLINE: " + taskInfo.printTaskDeadlineInfo());
			System.out.println("FINISH DATE: " + taskInfo.printTaskFinishDateInfo());
			System.out.println("TASK TEAM: " + taskInfo.printTaskTeamInfo());
			System.out.println("TASK BUDGET: " + taskInfo.printTaskBudgetInfo());
			System.out.println("");
			System.out.println("[P] Back to " + taskInfo.printProjectNameInfo());
			System.out.println("[B] Back to my tasks");
			System.out.println("[M] MainMenu");
			System.out.println("[E] Exit");
			
			String choice = scannerInput.nextLine().toUpperCase();
			switch (choice) {
			case "P":
				ProjectViewMenuUI previousMenu = new ProjectViewMenuUI(project, user);
				previousMenu.projectDataDisplay();
				break;
			case "B":
				UserTasksFunctionalitiesMenuUI userTasks = new UserTasksFunctionalitiesMenuUI(user);
				userTasks.chooseFunctionality();
				break;
			case "M":
				MainMenuUI.mainMenu();
				break;
			case "E":
				System.out.println("----YOU HAVE EXIT FROM APPLICATION----");
				System.exit(0);
				break;
			default:
				System.out.println("Please choose a valid option: ");
				System.out.println("");
				TaskDetailsUI myAtualUIView = new TaskDetailsUI(task);
				myAtualUIView.taskDataDisplay();
				break;
			}
	}
	}
	
}
