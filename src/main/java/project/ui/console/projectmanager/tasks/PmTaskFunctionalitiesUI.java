package project.ui.console.projectmanager.tasks;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controller.US340CreateTaskController;
import project.controller.US342CreateTaskDependencyController;
import project.services.TaskService;
import project.controller.PrintProjectInfoController;
import project.controller.PrintTaskInfoController;
import project.model.Project;
import project.model.Task;
import project.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

@Component
public class PmTaskFunctionalitiesUI {

	@Autowired
	private TaskService taskService; //ISTO TEM DE SAIR MAL POSSA?!

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

	@Autowired
	private US340CreateTaskController us340;

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


		boolean loopESD = true;
		boolean loopDL = true;
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
			System.out.println("[5] Set Estimated Start Date");
			System.out.println("[6] Set Deadline");
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
			case "5":
				while (loopESD) {
					System.out.println("Please insert a date (DD/MM/YYYY):");
					System.out.println("Alternatively, insert [B] to go back.");
					String value = scannerInput.nextLine();
					if (!"b".equalsIgnoreCase(value)) {
						Calendar date = Calendar.getInstance();
						Date tempdate = null;
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY");
							tempdate = sdf.parse(value);
							if (!value.equals(sdf.format(date))) {
								tempdate = null;
							}
						} catch (Exception forex) {
							System.out.println("Something went wrong, please try again.");
							tempdate = null;
						}
						if (tempdate == null) {
							System.out.println("Invalid date format, please try again.");
						} else {
							date.setTime(tempdate);
							us340.setEstimatedStartDate(task, date);
							System.out.println("Estimated Start Date successfully added");
						}
					} else {
						loopESD = false;
					}
				}
				break;
			case "6":
				while (loopDL) {
					System.out.println("Please insert a date (DD/MM/YYYY):");
					System.out.println("Alternatively, insert [B] to go back.");
					String value = scannerInput.nextLine();
					if (!"b".equalsIgnoreCase(value)) {
						Calendar date = Calendar.getInstance();
						Date tempdate = null;
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY");
							tempdate = sdf.parse(value);
							if (!value.equals(sdf.format(date))) {
								tempdate = null;
							}
						} catch (Exception forex) {
							System.out.println("Something went wrong, please try again.");
							tempdate = null;
						}
						if (tempdate == null) {
							System.out.println("Invalid date format, please try again.");
						} else {
							date.setTime(tempdate);
							us340.setDeadline(task, date);
							System.out.println("Deadline successfully added");
						}
					} else {
						loopDL = false;
					}
				}
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
