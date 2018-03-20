package project.ui.console.projectmanager.tasks;

import java.util.Scanner;

import project.Services.TaskService;
import project.controller.PrintProjectInfoController;
import project.controller.PrintTaskInfoController;
import project.controller.UpdateDbToContainersController;
import project.model.Project;
import project.model.Task;
import project.model.User;
import project.ui.console.MainMenuUI;

public class PmTaskFunctionalitiesUI {

	private Project project;
	private String taskID;
	private User user;
	private Task task;
	private TaskService taskService;

	public PmTaskFunctionalitiesUI(String taskID, Project project, User user) {
		this.taskID = taskID;
		this.project = project;
		this.user = user;
		this.task = taskService.getTaskByTaskID(taskID);
	}

	public void taskDataDisplay() {
		UpdateDbToContainersController infoUpdater = new UpdateDbToContainersController();
		PrintTaskInfoController taskInfo = new PrintTaskInfoController(this.taskID, this.project.getIdCode());
		taskInfo.setProjectAndTask();
		PrintProjectInfoController projectInfo = new PrintProjectInfoController(this.project.getIdCode());
		projectInfo.setProject();

		boolean condition = true;
		while (condition) {
			infoUpdater.updateDBtoContainer();
			System.out.println("");
			System.out.println("PROJECT - " + projectInfo.printProjectNameInfo());
			System.out.println("");
			System.out.println("                     TASK                    ");
			System.out.println("*** " + taskInfo.printTaskNameInfo().toUpperCase() + " ***");
			System.out.println("______________________________________________");
			System.out.println("ID: " + taskInfo.printTaskIDCodeInfo());
			System.out.println("STATUS: " + taskInfo.printTaskStateInfo());
			System.out.println("ESTIMATED START DATE: " + taskInfo.printTaskEstimatedStartDateInfo());
			System.out.println("START DATE: " + taskInfo.printTaskStartDateInfo());
			System.out.println("DEADLINE: " + taskInfo.printTaskDeadlineInfo());
			System.out.println("FINISH DATE: " + taskInfo.printTaskFinishDateInfo());
			System.out.println("TASK TEAM: " + taskInfo.printTaskTeamInfo());
			System.out.println("TASK BUDGET: " + taskInfo.printTaskBudgetInfo());
			System.out.println("");
			System.out.println("[1] Assign User");
			System.out.println("[2] Remove User");
			System.out.println("[3] Mark task as finished");
			System.out.println("[4] Cancel task");
			System.out.println("______________________________________________");
			System.out.println("[B] Back");
			System.out.println("[M] MainMenu");
			System.out.println("[E] Exit");

			Scanner scannerInput = new Scanner(System.in);
			String choice = scannerInput.nextLine().toUpperCase();
			switch (choice) {
			case "1":
				US361AssignUserToTaskUI case1UI = new US361AssignUserToTaskUI(this.project, this.task, this.user);
				case1UI.displayUsersToAssign();
				break;
			case "2":
				US362RemoveUserFromTaskUI case2UI = new US362RemoveUserFromTaskUI(this.project, this.task, this.user);
				case2UI.displayUsersToRemove();

				break;
			case "3":
				US365MarkTaskAsFinishedUI case3UI = new US365MarkTaskAsFinishedUI();
				case3UI.markTaskAsFinished(taskID, project);
				break;
			case "4":
				US347CancelOnGoingTaskUI us347UI = new US347CancelOnGoingTaskUI();
				us347UI.cancelOnGoingTask(taskID, project);
				break;
			case "B":
				condition = false;
				break;
			case "M":
				MainMenuUI.mainMenu();
				break;
			case "E":
				System.out.println("----YOU HAVE EXIT FROM APPLICATION----");
				condition = false;
				break;
			default:
				System.out.println("Please choose a valid option.");
				System.out.println("");
				break;
			}
		}
	}
}
