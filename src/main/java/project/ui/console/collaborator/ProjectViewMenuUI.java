package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controller.PrintProjectInfoController;
import project.model.User;

import java.util.Scanner;

@Component
public class ProjectViewMenuUI {

	@Autowired
	private TaskDetailsUI userTasks;

	private Integer projectID;
	private User user;

	public ProjectViewMenuUI() {
	}

	/**
	 * This method executes all options to execute through this UI Presents the
	 * project details and the task's list of project Uses a switch case to treat
	 * the user's input
	 */
	public void projectDataDisplay() {
		boolean loop = true;
		while (loop) {
			loop = false;

		PrintProjectInfoController projectInfo = new PrintProjectInfoController(this.projectID);
		projectInfo.setProject();

		Scanner scannerInput = new Scanner(System.in);

		System.out.println("");
		System.out.println("PROJECT " + projectInfo.printProjectNameInfo().toUpperCase());
		System.out.println("______________________________________________");
		System.out.println("ID: " + projectInfo.printProjectIDCodeInfo());
		System.out.println("STATUS: " + projectInfo.printProjectStatusInfo());
		System.out.println("DESCRIPTION: " + projectInfo.printProjectDescriptionInfo());
		System.out.println("START DATE: " + projectInfo.printProjectStartDateInfo());
		System.out.println("FINISH DATE: " + projectInfo.printProjectFinishDateInfo());
		System.out.println("PROJECT MANAGER: " + projectInfo.printProjectManagerInfo());
		System.out.println("PROJECT TEAM: " + projectInfo.printProjectTeamInfo());
		System.out.println("PROJECT BUDGET: " + projectInfo.printProjectBudgetInfo());
		System.out.println("");
		System.out.println("TASKS OF " + projectInfo.printProjectNameInfo().toUpperCase() + ":");

		for (int i = 0; i < projectInfo.getProjectTaskList().size(); i++) {
			System.out.println(projectInfo.getProjectTaskList().get(i));
		}

		System.out.println("To see task's details, choose the task ID number.");
		System.out.println("");
		System.out.println("[B] Back \n");

		String choice = scannerInput.nextLine().toUpperCase();
		switch (choice) {
		case "B":
			break;
		default:
			loop = taskIDToSeeTaskDetaisls(choice);
			break;
		}
		}
	}

	public void setProjectID(Integer projectID) {
		this.projectID = projectID;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean taskIDToSeeTaskDetaisls(String choice){
		try {
			userTasks.setTaskID(choice);
			userTasks.setUser(user);
			userTasks.setProjectID(projectID);
			userTasks.taskDataDisplay();
			return false;
		}
		catch (NullPointerException npe) {
			System.out.println("Please choose a valid option: ");
			System.out.println("");

			return true;
		}

	}
}
