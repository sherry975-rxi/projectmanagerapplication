package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.PrintProjectInfoController;
import project.model.User;

import java.util.Scanner;

@Component
public class ProjectViewMenuUI {

	@Autowired
	private TaskDetailsUI userTasks;

	private Integer projectID;
	private User user;

	public ProjectViewMenuUI() {
		//empty constructor
	}

	/**
	 * This method executes all options to execute through this UI Presents the
	 * project details and the task's list of project Uses a switch case to treat
	 * the user's input
	 */
	public void projectDataDisplay() {
		boolean loop = true;
		while (loop) {

		PrintProjectInfoController projectInfo = new PrintProjectInfoController(this.projectID);
		projectInfo.setProject();

		Scanner scannerInput = new Scanner(System.in);

		System.out.println(projectInfo.getAllProjectInfo());

		for (int i = 0; i < projectInfo.getProjectTaskList().size(); i++) {
			System.out.println(projectInfo.getProjectTaskList().get(i));
		}

		System.out.println("To see task's details, choose the task ID number. \n");
		System.out.println("[B] Back \n");

		String choice = scannerInput.nextLine().toUpperCase();
			if ("B".equals(choice)) {
				break;
			} else {
				loop = taskIDToSeeTaskDetaisls(choice);

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
