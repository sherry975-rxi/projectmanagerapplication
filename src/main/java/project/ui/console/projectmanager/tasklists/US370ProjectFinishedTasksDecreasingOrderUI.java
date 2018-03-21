package project.ui.console.projectmanager.tasklists;

import project.controller.PrintProjectInfoController;
import project.controller.US367MarkFinishedTaskAsUnfinishedController;
import project.controller.US370GetProjectFinishedTaskListController;
import project.model.Project;
import project.model.Task;
import project.model.User;

import java.util.List;
import java.util.Scanner;

public class US370ProjectFinishedTasksDecreasingOrderUI {

	private Project proj;
	private User user;

	public US370ProjectFinishedTasksDecreasingOrderUI(Project project, User user) {
		this.user = user;
		this.proj = project;
	}

	/**
	 * This method executes all options to execute through this UI Presents the
	 * project details and the task's list of project Uses a switch case to treat
	 * the user's input
	 */
	public void projectDataDisplay() {
		

		PrintProjectInfoController projectInfo = new PrintProjectInfoController(this.proj.getIdCode());
		int projectID = proj.getIdCode();
		projectInfo.setProject();
		US370GetProjectFinishedTaskListController projectFinishedTaskList = new US370GetProjectFinishedTaskListController();

		String line = "___________________________________________________";
		Scanner scannerInput = new Scanner(System.in);
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
		System.out.println("FINISH DATE: " + projectInfo.printProjectFinishDateInfo());
		System.out.println("PROJECT MANAGER: " + projectInfo.printProjectManagerInfo());
		System.out.println("PROJECT TEAM: " + projectInfo.printProjectTeamInfo());
		System.out.println("PROJECT BUDGET: " + projectInfo.printProjectBudgetInfo());
		System.out.println(line);
		System.out.println("               FINISHED TASKS                 ");
		System.out.println(line);

		List<Task> finishedTasksDecreasingOrder = projectFinishedTaskList.getFinishedTasksInDescreasingOrder(this.proj);

		for (int i = 0; i < finishedTasksDecreasingOrder.size(); i++) {
			System.out.println("[" + finishedTasksDecreasingOrder.get(i).getTaskID() + "] "
					+ finishedTasksDecreasingOrder.get(i).getDescription());
		}

		System.out.println("To roll back a task from Finish back to Ongoing, choose the task ID number.");
		System.out.println("");
		System.out.println("[B] Back \n");

		String choice = scannerInput.nextLine().toUpperCase();
		switch (choice) {
		case "B":
			break;
		default:
			try {
				US367MarkFinishedTaskAsUnfinishedController taskToMarkAsUnfinished = new US367MarkFinishedTaskAsUnfinishedController(
						this.proj, choice);
				taskToMarkAsUnfinished.markFinishedTaskAsUnfinished();
			}

			catch (NullPointerException npe) {
				System.out.println("Please choose a valid option: ");
				System.out.println("");
				loop = true;
			}

			break;
		}
	}}
}

// Integer projectID
// Display list
// 1.Choose task to mark as not Finished US 367
