/**
 * 
 */
package project.ui;

import java.util.Scanner;

import project.model.User;

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
		System.out.println("[6] Mark task as finished");
		System.out.println("[7] Remove Task from my list");
		System.out.println("[8] Update Task Report");
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
		// case "2":
		// US203ViewFinishedTasksFromUser finishedTasksFromUser = new
		// US203ViewFinishedTasksFromUser(this.user);
		// finishedTasksFromUser.displayfinishedTasksFromUser();
		// break;
		case "3":
			break;
		case "4":
			US215TotalTimeSpentOnTaskLastMonthUI totalTimeSpentOnTasksLastMonthUi = new US215TotalTimeSpentOnTaskLastMonthUI();
			totalTimeSpentOnTasksLastMonthUi.displayTotalTimeSpentOnTasksLastMonth(user);
			break;
		case "5":
			US216AverageTimeSpentByTaskLastMonthUI averegeTimeSpentOnTasksLastMonthUi = new US216AverageTimeSpentByTaskLastMonthUI();
			averegeTimeSpentOnTasksLastMonthUi.displayAveregeTimeSpentByTaskLastMonth();
			break;
		// case "6":
		// US205MarkTaskAsFinished markTaskAsFinished = new
		// US205MarkTaskAsFinished(this.user);
		// markTaskAsFinished.chooseTaskToMarkAsFinished();
		case "7":
			US206V2RemovalTaskRequestUI removalTaskFromUserList = new US206V2RemovalTaskRequestUI(this.user);
			removalTaskFromUserList.displayTasksFromUser();
			break;
		case "8":
			US208ChangeReportedTimeInTask updateTaskReport = new US208ChangeReportedTimeInTask();
			updateTaskReport.changeReportedTime();
			break;
		case "B":
			// Menu3 menuThree = new Menu3();
			// // TODO when this menu is done is necessary to include a method.
			break;
		case "M":
			MainMenuUI.mainMenu();
			break;
		case "E":
			System.exit(0);
			break;
		}
	}

}
