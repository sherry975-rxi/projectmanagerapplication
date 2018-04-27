package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.PrintProjectInfoController;
import project.controllers.PrintTaskInfoController;
import project.controllers.US204v2createRequestAddCollaboratorToTaskTeamController;
import project.controllers.US205MarkTaskAsFinishedCollaboratorController;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;
import project.services.TaskService;

import java.util.Scanner;

@Component
public class TaskDetailsUI {

	@Autowired
	private PrintTaskInfoController taskDetails;

	@Autowired
	private PrintProjectInfoController projectInfo;

	@Autowired
	private US204v2createRequestAddCollaboratorToTaskTeamController controllerMember;

	@Autowired
	private US204v2CreateTaskAssignmentToCollaboratorUI createAssignmentRequest;

	@Autowired
	private US205MarkTaskAsFinishedCollaboratorController taskToMark;

	@Autowired
	private US206CreateRemovalTaskRequestUI createCollabRemovalRequest;

	@Autowired
	private US207And208CreateOrUpdateTaskReportUI reportUI;

	@Autowired
	private TaskService taskService;

	private User user;
	private Integer projectID;
	private String taskID;
	private Task task;
	private ProjectCollaborator projCollaborator;

	private static final String CANT_DO_IT = "You can't do it because you aren't assigned to this task.";


	public TaskDetailsUI() {
		//Empty constructor for JPA purposes
	}

	private void printMenuOption(){
		System.out.println("\n" + "PROJECT - " + projectInfo.printProjectNameInfo() + "\n");
		System.out.println("                     TASK                    ");
		System.out.println("*** " + taskDetails.printTaskNameInfo().toUpperCase() + " ***");
		System.out.println("______________________________________________");
		System.out.println("ID: " + taskDetails.printTaskIDCodeInfo());
		System.out.println("STATUS: " + taskDetails.printTaskStateInfo());
		System.out.println("ESTIMATED START DATE: " + taskDetails.printTaskEstimatedStartDateInfo());
		System.out.println("START DATE: " + taskDetails.printTaskStartDateInfo());
		System.out.println("DEADLINE: " + taskDetails.printTaskDeadlineInfo());
		System.out.println("FINISH DATE: " + taskDetails.printTaskFinishDateInfo());
		System.out.println("TASK TEAM: " + taskDetails.printTaskTeamInfo());
		System.out.println("TASK BUDGET: " + taskDetails.printTaskBudgetInfo() + "\n");
		System.out.println("[1] Mark task as completed");
		System.out.println("[2] Request assignment to task team");
		System.out.println("[3] Request task team removal");
		System.out.println("[4] Create/Update task report");
		System.out.println("______________________________________________");
		System.out.println("[B] Back \n");
	}

	/**
	 * This method executes all options to execute through this UI Presents the task
	 * details and the options about this specific task Uses a switch case to treat
	 * the user's input
	 */
	public void taskDataDisplay() {
		taskDetails.setProjeID(this.projectID);
		taskDetails.setTaskID(this.taskID);
		taskDetails.setProjectAndTask();
		projectInfo.setProjID(this.projectID);
		projectInfo.setProject();

		boolean condition = true;
		while (condition) {
			condition = false;

			printMenuOption();

			Scanner scannerInput = new Scanner(System.in);
			String choice = scannerInput.nextLine().toUpperCase();
			switch (choice) {
			case "1":
				caseOne();
                break;

			case "2":
				caseTwo();
				break;
			case "3":
				projCollaborator = new ProjectCollaborator(this.user, this.projectID);

				task = controllerMember.getTaskByTaskID(this.taskID);
				checkAndAddRemovalRequest(projCollaborator);

				break;
			case "4":
				System.out.println("Test the report creation");
				ProjectCollaborator projCollaborator2 = new ProjectCollaborator(this.user, this.projectID);
				checkAndCreateReportRequest(projCollaborator2);
				break;
			case "B":
				break;
			default:
				System.out.println("Please choose a valid option.");
				System.out.println();
				condition = true;
				break;
			}
		}
	}

	private void caseOne() {
		projCollaborator = new ProjectCollaborator(this.user, this.projectID);

		if (!task.isProjectCollaboratorActiveInTaskTeam(projCollaborator)) {
			System.out.println(CANT_DO_IT);
		} else {

			taskToMark.setTaskToBeMarked(task);
			taskToMark.markTaskAsFinished();
			System.out.println("---- SUCCESS Task Marked As Finished ----");
		}
	}

	private void caseTwo() {
		projCollaborator = new ProjectCollaborator(this.user, this.projectID);

		if (task.isProjectCollaboratorActiveInTaskTeam(projCollaborator) || task.isTaskFinished()) {
			System.out.println(CANT_DO_IT);
		} else {
			createAssignmentRequest.setProjID(this.projectID);
			createAssignmentRequest.setTaskID(this.taskID);
			createAssignmentRequest.setUser(this.user);
			createAssignmentRequest.createTaskAssignment();
		}
	}


	private void checkAndAddRemovalRequest(ProjectCollaborator projCollaborator1) {
		if (!task.isProjectCollaboratorActiveInTaskTeam(projCollaborator1)) {
			System.out.println(CANT_DO_IT);
		} else {
			createCollabRemovalRequest.setUser(this.user);
			createCollabRemovalRequest.setTaskID(this.taskID);
			createCollabRemovalRequest.cancelRemovalTaskRequestUI();
		}
	}

	private void checkAndCreateReportRequest(ProjectCollaborator projCollaborator2) {
		if (!task.isProjectCollaboratorActiveInTaskTeam(projCollaborator2)) {
			System.out.println(CANT_DO_IT);
		} else {
			reportUI.setTaskCollaboratorThroughEmail(this.user.getEmail());

			reportUI.createReport();
		}
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setProjectID(Integer projectID) {
		this.projectID = projectID;
	}

	/**
	 * hotfix for crashes in task details UI due to letter values in XML Task ID's
	 *
	 * @param taskID
	 */
	public void setTaskID(String taskID) {
		this.taskID = taskID;
		this.task = taskService.getTaskByTaskID(taskID);
		this.projectID = task.getProject().getProjectId();
	}
}
