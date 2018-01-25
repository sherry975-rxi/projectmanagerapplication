package project.ui.console.uiProjectManager;

import java.util.Scanner;

import project.controller.PrintProjectInfoController;
import project.model.Project;
import project.model.User;
import project.ui.console.MainMenuUI;
import project.ui.console.uiCollaborator.CollectProjectsFromUserUI;
import project.ui.console.uiProjectManager.manageRequests.US356ApproveOrCancelAssignmentRequestUI;
import project.ui.console.uiProjectManager.manageRequests.US357ApproveOrCancelRemovalRequestUI;
import project.ui.console.uiProjectManager.taskLists.US360ProjectUnassignedTasksUI;
import project.ui.console.uiProjectManager.taskLists.US370ProjectFinishedTasksDecreasingOrderUI;
import project.ui.console.uiProjectManager.taskLists.US372ProjectOngoingTasksUI;
import project.ui.console.uiProjectManager.taskLists.US375ProjectNotStartedTasksUI;
import project.ui.console.uiProjectManager.taskLists.US377ProjectCancelledTasks;
import project.ui.console.uiProjectManager.taskLists.US380ProjectUnfinishedTasksWithExperiredDeadlineUI;


public class ProjectManagerMainMenuUI {
	private User projectManager;
	private Project project;
	
	/**
	 * Creates the UI
	 * 
	 * @param user
	 */
	public ProjectManagerMainMenuUI(User user, Project project) {
		this.projectManager = user;
		this.project = project;

	}	
	
	public void displayOptions() {
		
		PrintProjectInfoController projectInfo = new PrintProjectInfoController(this.project);
		projectInfo.setProject();

		Scanner scannerInput = new Scanner(System.in);

		System.out.println("———————————————————————————————————————————MENU PROJECT MANAGER——————————————————————————————————————————————————");
		System.out.println("                               PROJECT " + projectInfo.printProjectNameInfo().toUpperCase() + " - " + "PROJECT MANAGER " + projectInfo.printProjectManagerInfo()); // OU projectManager.getName()
		System.out.println("");
		System.out.println("ID: " + projectInfo.printProjectIDCodeInfo() + "        " + "STATUS: " + projectInfo.printProjectStatusInfo());
		System.out.println("DESCRIPTION: " + projectInfo.printProjectDescriptionInfo());
		System.out.println("START DATE: " + projectInfo.printProjectStartDateInfo() + "        " + "FINISH DATE: " + projectInfo.printProjectFinishDateInfo());
		System.out.println("PROJECT BUDGET: " + projectInfo.printProjectBudgetInfo());
		System.out.println("");
		System.out.println("TASKS LIST:                                                                TEAM:");
		
		System.out.println("    [A1] - Unfinished                                                         [B1] - View Project Team");
		System.out.println("    [A2] - Unfinished  w/ expired Finish Date or Deadline                     [B2] - Add Collaborator");
		System.out.println("    [A3] - Finished by Decreasing order of Finish Date                        [B3] - Show Unassigned Collaborators");
		System.out.println("    [A4] - Not Started");
		System.out.println("    [A5] - Not Assigned");
		System.out.println("    [A6] - Cancelled");
		System.out.println("    [A7] - All Tasks");
		System.out.println("");
		System.out.println("REQUESTS:                                                                  OTHERS:");
		System.out.println("    [C1] - View Assignment Requests                                           [D1] - View Project Cost");
		System.out.println("    [C2] - View Removal Requests                                              [D2] - Create Task");
		System.out.println("                                                                              [D3] - Create Dependency");
		System.out.println("");
		System.out.println("_________________________________________________________________________________________________________________");
		System.out.println("[B] Back");
		System.out.println("[M] MainMenu");
		System.out.println("[E] Exit \n");

		String option = scannerInput.nextLine().toUpperCase();

		switch (option) {
		case "A1":
			US372ProjectOngoingTasksUI optionA1 = new US372ProjectOngoingTasksUI();
			optionA1.displayOnGoingTasksOfProject(this.project, this.projectManager);
			break;
		case "A2":
			US380ProjectUnfinishedTasksWithExperiredDeadlineUI optionA2 = new US380ProjectUnfinishedTasksWithExperiredDeadlineUI();
			optionA2.displayUnfinishedTasksWithExpiredDeadline(this.project, this.projectManager);
			break;
		case "A3":
			US370ProjectFinishedTasksDecreasingOrderUI optionA3 = new US370ProjectFinishedTasksDecreasingOrderUI(this.project.getIdCode(), this.projectManager);
			optionA3.projectDataDisplay();
			break;
		case "A4":
			US375ProjectNotStartedTasksUI optionA4 = new US375ProjectNotStartedTasksUI();
			optionA4.projectNotStartedTasksUI(this.project, this.projectManager);
			break;
		case "A5":
			US360ProjectUnassignedTasksUI optionA5 = new US360ProjectUnassignedTasksUI();
			optionA5.projectUnassignedTasksUI(this.project, this.projectManager);
			break;
		case "A6":
			US377ProjectCancelledTasks optionA6 = new US377ProjectCancelledTasks();
			optionA6.displayCancelledTasksOfProject(this.project, this.projectManager);
			break;
		case "A7":
			
			break;
		case "B1":
			
			break;
		case "B2":
			
			break;
		case "B3":
			
			break;
		case "C1":
			US356ApproveOrCancelAssignmentRequestUI optionC1 = new US356ApproveOrCancelAssignmentRequestUI(this.projectManager, this.project);
			optionC1.displayAssignmentTaskRequests();
			break;
		case "C2":
			US357ApproveOrCancelRemovalRequestUI optionC2 = new US357ApproveOrCancelRemovalRequestUI(this.projectManager, this.project);
			optionC2.displayRemovalTaskRequests();
			break;
		case "D1":
			
			break;
		case "D2":
			
			break;
		case "D3":
			
			break;


		case "B":
			CollectProjectsFromUserUI backUI = new CollectProjectsFromUserUI(this.projectManager);
			backUI.collectProjectsFromUser();
			break;
		case "M":
			MainMenuUI.mainMenu();
			break;
		case "E":
			System.out.println("----YOU HAVE EXIT FROM APPLICATION----");
			System.exit(0);
			break;
		default:
			displayOptions();
		}
	}

}
