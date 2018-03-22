package project.ui.console.projectmanager.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.Services.TaskService;
import project.controller.PrintProjectInfoController;
import project.controller.PrintTaskInfoController;
import project.model.Project;
import project.model.Task;
import project.model.User;

import java.util.Scanner;

@Component
public class PmTaskFunctionalitiesUI {

	@Autowired
	private TaskService taskService; //QUE E ISTO PA?!

	@Autowired
	private PrintTaskInfoController taskInfo;

	@Autowired
	private PrintProjectInfoController projectInfo;

	@Autowired
	private US361AssignUserToTaskUI case1UI;

	@Autowired
	private US362RemoveUserFromTaskUI case2UI;

	@Autowired
	private US365MarkTaskAsFinishedUI case3UI;

	@Autowired
	private US347CancelOnGoingTaskUI us347UI;

	private Project project;
	private String taskID;
	private User user;
	private Task task;


	public PmTaskFunctionalitiesUI() {
	}

	public void taskDataDisplay() {
		this.task = taskService.getTaskByTaskID(taskID);
		taskInfo.setTaskID(taskID);
		taskInfo.setProjeID(project.getIdCode());
		taskInfo.setProjectAndTask();
		projectInfo.setProjID(project.getIdCode());
		projectInfo.setProject();

		boolean condition = true;
		while (condition) {
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
				case1UI.setProject(project);
				case1UI.setTask(task);
				case1UI.displayUsersToAssign();
				break;
			case "2":
				case2UI.setProject(project);
				case2UI.setTask(task);
				case2UI.displayUsersToRemove();

				break;
			case "3":
				case3UI.markTaskAsFinished(taskID, project);
				break;
			case "4":
				us347UI.cancelOnGoingTask(taskID, project);
				break;
			case "B":
				condition = false;
				break;
			default:
				System.out.println("Please choose a valid option.");
				System.out.println("");
				break;
			}
		}
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
