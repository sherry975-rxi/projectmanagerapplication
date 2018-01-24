package project.ui.uiCollaborator;

import java.util.Scanner;

import project.controller.PrintProjectInfoController;
import project.controller.PrintTaskInfoController;
import project.controller.US205MarkTaskAsFinishedCollaborator;
import project.model.User;
import project.ui.MainMenuUI;

public class TaskDetailsUI {
	private User user;
	private Integer projectID;
	private String taskID;

	public TaskDetailsUI(String taskID, Integer projectID, User user) {
		this.taskID = taskID;
		this.projectID = projectID;
		this.user = user;
	}

	/**
	 * This method executes all options to execute through this UI Presents the task
	 * details and the options about this specific task Uses a switch case to treat
	 * the user's input
	 */
	public void taskDataDisplay() {
		PrintTaskInfoController taskInfo = new PrintTaskInfoController(this.taskID, this.projectID);
		taskInfo.setProjectAndTask();
		PrintProjectInfoController projectInfo = new PrintProjectInfoController(this.projectID);
		projectInfo.setProject();

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
		System.out.println("[1] Mark task as completed");
		System.out.println("[2] Request assignment to task team");
		System.out.println("[3] Request task team unassignment");
		System.out.println("[4] Update task report");
		System.out.println("______________________________________________");
		System.out.println("[P] Back to " + projectInfo.printProjectNameInfo());
		System.out.println("[B] Back to my tasks");
		System.out.println("[M] MainMenu");
		System.out.println("[E] Exit");

		Scanner scannerInput = new Scanner(System.in);
		String choice = scannerInput.nextLine().toUpperCase();
		switch (choice) {
		case "1":
			US205MarkTaskAsFinishedCollaborator taskToMark = new US205MarkTaskAsFinishedCollaborator();
			taskToMark.getProjectsThatIAmCollaborator(this.user);
			taskToMark.getUnfinishedTasksOfProjectFromCollaborator(this.projectID);
			taskToMark.getTaskToBeMarkedFinished(this.taskID);
			taskToMark.markTaskAsFinished();
			System.out.println("---- SUCCESS Task Marked As Finished ----");
			break;
		case "2":
			US204v2ConfirmTaskAssignmentToCollaboratorUI createAssignmentRequest = new US204v2ConfirmTaskAssignmentToCollaboratorUI(
					user, taskID);
			createAssignmentRequest.confirmTaskAssignmentToCollaborator();
			break;
		case "P":
			ProjectViewMenuUI previousMenu = new ProjectViewMenuUI(this.projectID, user);
			previousMenu.projectDataDisplay();
			break;
		case "B":
			UserTasksFunctionalitiesMenuUI userTasks = new UserTasksFunctionalitiesMenuUI(user);
			userTasks.displayFunctionalities();
			break;
		case "4":
			US207And208CreateUpdateTaskReportUI reportUI = new US207And208CreateUpdateTaskReportUI(user.getEmail(),
					taskID);
			reportUI.createReport();
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
			TaskDetailsUI myAtualUIView = new TaskDetailsUI(this.taskID, this.projectID, user);
			myAtualUIView.taskDataDisplay();
			break;
		}
	}

}
