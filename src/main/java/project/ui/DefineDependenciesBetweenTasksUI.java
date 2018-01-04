package project.ui;

import java.util.Calendar;
import java.util.Scanner;

import project.controller.US342Controller;
import project.model.User;

/**
 * @author group3
 *
 */
public class DefineDependenciesBetweenTasksUI {

	private User user;

	/**
	 * @param user
	 */
	public DefineDependenciesBetweenTasksUI(User user) {
		this.user = user;
	}

	/**
	 * 
	 */
	public void chooseProject() {

		Scanner input = new Scanner(System.in);

		System.out.println("Choose a project: ");

		US342Controller us342Controller = new US342Controller();

		int projectListSize = us342Controller.getProjectsFromUser(user).size();

		// show of all projects where user is Project Manager
		for (int indexProject = 0; indexProject < projectListSize; indexProject++) {

			String projectName = us342Controller.getProjectsFromUser(user).get(indexProject).getName();
			int projectID = us342Controller.getProjectsFromUser(user).get(indexProject).getIdCode();
			String projectIDString = Integer.toString(projectID);

			System.out.println(projectName + " " + "ProjectID: " + projectIDString);

		}

		System.out.println();

		int userInputForProjectID = Integer.parseInt(input.nextLine());

		
		us342Controller.setProjectID(userInputForProjectID);
		
		// print tasks
		int taskListSize = us342Controller.getTasksFromAProject().size();

		// show task list from Project input by User
		for (int indexTask = 0; indexTask < taskListSize; indexTask++) {

			String taskName = us342Controller.getTasksFromAProject().get(indexTask).getDescription();
			String taskID = us342Controller.getTasksFromAProject().get(indexTask).getTaskID();

			System.out.println(taskName + " " + "taskID: " + taskID);

		}
		
		System.out.println();
		
		System.out.println("Choose dependent task: ");
		
		String userInputForDependentTaskID = input.nextLine();
		
		System.out.println("Choose main task: ");
		
		String userInputForMainTaskID = input.nextLine();
		
		System.out.println("Choose number of days to create dependence: ");
		
		int userInputForIncrementDays = Integer.parseInt(input.nextLine());
		
		us342Controller.createDependenceFromTask(userInputForDependentTaskID, userInputForMainTaskID, userInputForIncrementDays);
		
		System.out.println();
		
		System.out.println("Dependency successfully created.");
		
		System.out.println();
	}

}
