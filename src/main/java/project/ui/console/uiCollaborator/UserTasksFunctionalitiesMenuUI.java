/**
 * 
 */
package project.ui.console.uiCollaborator;

import java.util.Scanner;

import project.model.User;
import project.ui.console.MainMenuUI;

/**
 * @author Group3
 *
 */
public class UserTasksFunctionalitiesMenuUI {

	private User user;

	/**
	 * Constructor to instantiate a new UserTasksFunctionalitiesMenuUI
	 * 
	 * @param user
	 *            User to get tasks from
	 */
	public UserTasksFunctionalitiesMenuUI(User user) {
		this.user = user;
	}

	/**
	 * This method displays the functionalities that the user can do related to its
	 * tasks
	 */
	public void displayFunctionalities() {

		String myname = user.getName();
		String function = user.getFunction().toUpperCase();

		System.out.println("\n" + myname + " \n" + function);
		System.out.println("___________________________________________________");

		System.out.println("[1] View unfinished tasks");
		System.out.println("[2] View finished tasks : Decreasing Order");
		System.out.println("[3] View finished tasks in the last month : Decreasing order");
		System.out.println("[4] View total time spent on tasks in the last month");
		System.out.println("[5] View average time spent by task in the last month");
		System.out.println("[B] Back");
		System.out.println("[M] MainMenu");
		System.out.println("[E] Exit \n");
		System.out.println("Please choose an option: \n");
		System.out.println("___________________________________________________");

		chooseFunctionality();

	}

	/**
	 * Switch case that allows to choose the functionality
	 */
	public void chooseFunctionality() {

		Scanner scannerInput = new Scanner(System.in);
		String option = scannerInput.nextLine().toUpperCase();

		switch (option) {
		case "1":
			US203GetUnfinishedTaskUI unfinishedTasksFromUser = new US203GetUnfinishedTaskUI();
			unfinishedTasksFromUser.displayOptions(this.user);
			break;
		case "2":
			US210GetAllFinishedUserTasksInDecreasingOrderUI finishedTasksFromUserDecreasingOrder = new US210GetAllFinishedUserTasksInDecreasingOrderUI(
					this.user);
			finishedTasksFromUserDecreasingOrder.getAllFinishedUserTasksInDecreasingOrderUI();
			break;
		case "3":
			US211GetFinishedUserTasksFromLastMonthDecreasingOrder userTasksLastMonthDecreasingOrder = new US211GetFinishedUserTasksFromLastMonthDecreasingOrder(
					this.user);
			userTasksLastMonthDecreasingOrder.viewLastMonthFinishedTasks();
			break;
		case "4":
			US215TotalTimeSpentOnTaskLastMonthUI totalTimeSpentOnTasksLastMonthUi = new US215TotalTimeSpentOnTaskLastMonthUI();
			totalTimeSpentOnTasksLastMonthUi.displayTotalTimeSpentOnTasksLastMonth(user);
			break;
		case "5":
			US216AverageTimeSpentByTaskLastMonthUI averegeTimeSpentOnTasksLastMonthUi = new US216AverageTimeSpentByTaskLastMonthUI();
			averegeTimeSpentOnTasksLastMonthUi.displayAveregeTimeSpentByTaskLastMonth(user);
			break;
		case "B":
			CollaboratorMainMenuUI collaboratorMainMenu = new CollaboratorMainMenuUI(this.user);
			collaboratorMainMenu.displayOptions();
			break;
		case "M":
			MainMenuUI.mainMenu();
			break;
		case "E":
			System.exit(0);
			break;

		default:
			System.out.println("Error! Option not valid. Please insert an option again.");
			displayFunctionalities();
		}
	}
}
