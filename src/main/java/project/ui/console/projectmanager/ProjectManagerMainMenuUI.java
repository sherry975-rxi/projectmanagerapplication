package project.ui.console.projectmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.PrintProjectInfoController;
import project.model.Project;
import project.model.User;
import project.ui.console.projectmanager.others.US340CreateTaskUI;
import project.ui.console.projectmanager.others.US342DefineDependenciesBetweenTasksUI;
import project.ui.console.projectmanager.others.US390GetProjectReportedCostUI;
import project.ui.console.projectmanager.requests.US356ApproveOrCancelAssignmentRequestUI;
import project.ui.console.projectmanager.requests.US357ApproveOrCancelRemovalRequestUI;
import project.ui.console.projectmanager.tasklists.*;
import project.ui.console.projectmanager.team.US351AddCollaboratorToProjectTeamUI;
import project.ui.console.projectmanager.team.US355ViewProjectTeamAndThenRemoveCollaboratorUI;
import project.ui.console.projectmanager.team.US360ViewCollaboratorsWithoutTasksUI;

import java.util.Scanner;

@Component
public class ProjectManagerMainMenuUI {

	@Autowired
	private PrintProjectInfoController projectInfo;

	@Autowired
	private US372ProjectUnfinishedTasksUI optionA1;

	@Autowired
	private US380ProjectUnfinishedTasksWithExperiredDeadlineUI optionA2;

	@Autowired
	private US370ProjectFinishedTasksDecreasingOrderUI optionA3;

	@Autowired
	private US375ProjectNotStartedTasksUI optionA4;

	@Autowired
	private US360ProjectUnassignedTasksUI optionA5;

	@Autowired
	private	US377ProjectCancelledTasks optionA6;

	@Autowired
	private	US355ViewProjectTeamAndThenRemoveCollaboratorUI optionB1;

	@Autowired
	private US351AddCollaboratorToProjectTeamUI optionB2;

	@Autowired
	private US360ViewCollaboratorsWithoutTasksUI optionB3;

	@Autowired
	private US356ApproveOrCancelAssignmentRequestUI optionC1;

	@Autowired
	private US357ApproveOrCancelRemovalRequestUI optionC2;

	@Autowired
	private US390GetProjectReportedCostUI optionD1;

	@Autowired
	private US340CreateTaskUI optionD2;

	@Autowired
	private	US342DefineDependenciesBetweenTasksUI optionD3;

	private User projectManager;
	private Project project;

	/**
	 * Creates the UI
	 */
	public ProjectManagerMainMenuUI() {
	}

	public void displayOptions() {

		projectInfo.setProject(project);
		


		boolean loop = true;
		while (loop) {
            Scanner scannerInput = new Scanner(System.in);
		System.out.println(
				"———————————————————————————————————————————MENU PROJECT MANAGER——————————————————————————————————————————————————");
		System.out.println("                               Project " + projectInfo.printProjectNameInfo().toUpperCase()
				+ " - " + "Project Manager " + projectInfo.printProjectManagerInfo()); // OU projectManager.getName()
		System.out.println("");
		System.out.println("ID: " + projectInfo.printProjectIDCodeInfo() + "        " + "STATUS: "
				+ projectInfo.printProjectStatusInfo());
		System.out.println("DESCRIPTION: " + projectInfo.printProjectDescriptionInfo());
		System.out.println("START DATE: " + projectInfo.printProjectStartDateInfo() + "        " + "FINISH DATE: "
				+ projectInfo.printProjectFinishDateInfo());
		System.out.println("PROJECT BUDGET: " + projectInfo.printProjectBudgetInfo());
		System.out.println("");
		System.out.println("TASKS LIST:                                                                TEAM:");

		System.out.println(
				"    [A1] - Unfinished                                                         [B1] - View Project Team");
		System.out.println(
				"    [A2] - Unfinished  w/ expired Deadline                                    [B2] - Add Collaborator");
		System.out.println(
				"    [A3] - Finished by Decreasing order of Finish Date                        [B3] - Show Unassigned Collaborators");
		System.out.println("    [A4] - Not Started");
		System.out.println("    [A5] - Not Assigned");
		System.out.println("    [A6] - Cancelled");
		System.out.println("");
		System.out.println("REQUESTS:                                                                  OTHERS:");
		System.out.println(
				"    [C1] - View Assignment Requests                                           [D1] - View Project Cost");
		System.out.println(
				"    [C2] - View Removal Requests                                              [D2] - Create Task");
		System.out.println(
				"                                                                              [D3] - Create Dependency");
		System.out.println("");
		System.out.println(
				"_________________________________________________________________________________________________________________");
		System.out.println("[B] Back\n");

		String option = scannerInput.nextLine().toUpperCase();

		switch (option) {
		case "A1":
			optionA1.displayUnfinishedOfProject(this.project);
			break;
		case "A2":
			optionA2.displayUnfinishedTasksWithExpiredDeadline(this.project, this.projectManager);
			break;
		case "A3":
			optionA3.setProj(project);
			optionA3.projectDataDisplay();
			break;
		case "A4":
			optionA4.projectNotStartedTasksUI(this.project);
			break;
		case "A5":
			optionA5.projectUnassignedTasksUI(this.project);
			break;
		case "A6":
			optionA6.displayCancelledTasksOfProject(this.project);
			break;
		case "B1":
			optionB1.viewProjectTeamAndThenRemoveCollaboratorUI(this.project);
			break;
		case "B2":
			optionB2.addCollaboratorToProjectTeam(project);
			break;
		case "B3":
			optionB3.setSelectedProject(project);
			optionB3.viewUnassignedCollaborators();
			break;
		case "C1":
			optionC1.setProject(project);
			optionC1.displayAssignmentTaskRequests();
			break;
		case "C2":
			optionC2.setProject(project);
			optionC2.displayRemovalTaskRequests();
			break;
		case "D1":
			this.project = optionD1.displayProjectCost(this.project);
			break;
		case "D2":
			optionD2.setProject(project);
			optionD2.createTask();
			break;
		case "D3":
			optionD3.setProject(project);
			optionD3.chooseProject();
			break;

		case "B":
		    loop=false;
			break;
		
		default:
			System.out.println("Invalid input. Please retry:");
			break;
		}
	}

}

	public void setProjectManager(User projectManager) {
		this.projectManager = projectManager;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
