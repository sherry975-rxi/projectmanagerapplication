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
	private PrintTaskInfoController taskInfo;

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


	public TaskDetailsUI() {
		//Empty constructor for JPA purposes
	}

	private void printMenuOption(){
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

		System.out.println("[1] Mark task as completed");
		System.out.println("[2] Request assignment to task team");
		System.out.println("[3] Request task team unassignment");
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
		String cantDoIt = "You can't do it because you aren't assigned to this task.";
		taskInfo.setProjeID(this.projectID);
		taskInfo.setTaskID(this.taskID);
		taskInfo.setProjectAndTask();
		projectInfo.setProjID(this.projectID);
		projectInfo.setProject();
		ProjectCollaborator projCollaborator;

		boolean condition = true;
		while (condition) {
			condition = false;

			printMenuOption();

			Scanner scannerInput = new Scanner(System.in);
			String choice = scannerInput.nextLine().toUpperCase();
			switch (choice) {
			case "1":
                projCollaborator = new ProjectCollaborator(this.user, this.projectID);

                if (!task.isProjectCollaboratorActiveInTaskTeam(projCollaborator)) {
                    System.out.println(cantDoIt);
                } else {

                taskToMark.setTaskToBeMarked(task);
                taskToMark.markTaskAsFinished();
                System.out.println("---- SUCCESS Task Marked As Finished ----");
                }
                break;

			case "2":
				projCollaborator = new ProjectCollaborator(this.user, this.projectID);

				if (task.isProjectCollaboratorActiveInTaskTeam(projCollaborator) || task.isTaskFinished()) {
					System.out.println(cantDoIt);
				} else {
				createAssignmentRequest.setProjID(this.projectID);
				createAssignmentRequest.setTaskID(this.taskID);
				createAssignmentRequest.setUser(this.user);
				createAssignmentRequest.createTaskAssignment();
				}
				break;
			case "3":
				projCollaborator = new ProjectCollaborator(this.user, this.projectID);

				controllerMember.setTaskID(this.taskID);
				controllerMember.setUser(this.user);
				task = controllerMember.getTaskByTaskID(this.taskID);
				checkAndAddRemovalRequest(projCollaborator, cantDoIt);

				break;
			case "4":
				controllerMember.setTaskID(this.taskID);
				controllerMember.setUser(this.user);
				System.out.println("Test the report creation");
				ProjectCollaborator projCollaborator2 = new ProjectCollaborator(this.user, this.projectID);
				checkAndCreateReportRequest(projCollaborator2, cantDoIt);
				break;
			case "B":
				break;
			default:
				System.out.println("Please choose a valid option.");
				System.out.println("");
				condition = true;
				break;
			}
		}
	}

	private void checkAndAddRemovalRequest(ProjectCollaborator projCollaborator1, String cantDoIt) {
		if (!task.isProjectCollaboratorActiveInTaskTeam(projCollaborator1)) {
			System.out.println(cantDoIt);
		} else {
			createCollabRemovalRequest.setUser(this.user);
			createCollabRemovalRequest.setTaskID(this.taskID);
			createCollabRemovalRequest.cancelRemovalTaskRequestUI();
		}
	}

	private void checkAndCreateReportRequest(ProjectCollaborator projCollaborator2, String cantDoIt) {
		if (!task.isProjectCollaboratorActiveInTaskTeam(projCollaborator2)) {
			System.out.println(cantDoIt);
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
