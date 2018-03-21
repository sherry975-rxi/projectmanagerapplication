/**
 * 
 */
package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.model.User;

import java.util.Scanner;

/**
 * @author Group3
 *
 */

@Component
public class UserTasksFunctionalitiesMenuUI {

	@Autowired
	private	US203GetUnfinishedTaskUI unfinishedTasksFromUser;

	@Autowired
	private US210GetAllFinishedUserTasksInDecreasingOrderUI finishedTasksFromUserDecreasingOrder;

	@Autowired
	private US211GetFinishedUserTasksFromLastMonthDecreasingOrder userTasksLastMonthDecreasingOrder;

	@Autowired
	private US215TotalTimeSpentOnTaskLastMonthUI totalTimeSpentOnTasksLastMonthUi;

	@Autowired
	private US216AverageTimeSpentByTaskLastMonthUI averegeTimeSpentOnTasksLastMonthUi;

	private User user;

	/**
	 * Constructor to instantiate a new UserTasksFunctionalitiesMenuUI
	 *
	 */
	public UserTasksFunctionalitiesMenuUI() {

	}

	/**
	 * This method displays the functionalities that the user can do related to its
	 * tasks
	 */
	public void displayFunctionalities() {


		String myname = user.getName();
		String function = user.getFunction().toUpperCase();

		System.out.println("\n" + myname + " \n" + function);
		System.out.println("                    MY TASKS");
		System.out.println("___________________________________________________");

		System.out.println("[1] View unfinished tasks");
		System.out.println("[2] View finished tasks : Decreasing Order");
		System.out.println("[3] View finished tasks in the last month : Decreasing order");
		System.out.println("[4] View total time spent on tasks in the last month");
		System.out.println("[5] View average time spent by task in the last month");
		System.out.println("");
		System.out.println("___________________________________________________");
		System.out.println("[B] Back");
		System.out.println("[M] MainMenu");
		System.out.println("Please choose an option: \n");

		chooseFunctionality();

	}

	/**
	 * Switch case that allows to choose the functionality
	 */
	public void chooseFunctionality() {
		boolean loop = true;
		while (loop) {
			loop = false;
		Scanner scannerInput = new Scanner(System.in);
		String option = scannerInput.nextLine().toUpperCase();

		switch (option) {
		case "1":
			unfinishedTasksFromUser.displayOptions(this.user);
			break;
		case "2":
			finishedTasksFromUserDecreasingOrder.setCurrentUser(this.user);
			finishedTasksFromUserDecreasingOrder.getAllFinishedUserTasksInDecreasingOrderUI();
			break;
		case "3":
			userTasksLastMonthDecreasingOrder.setUser(this.user);
			userTasksLastMonthDecreasingOrder.viewLastMonthFinishedTasks();
			break;
		case "4":
			totalTimeSpentOnTasksLastMonthUi.displayTotalTimeSpentOnTasksLastMonth(user);
			break;
		case "5":
			averegeTimeSpentOnTasksLastMonthUi.displayAveregeTimeSpentByTaskLastMonth(user);
			break;
		case "B":
			return;

		default:
			System.out.println("Error! Option not valid. Please insert an option again.");
			loop = true;
		}
	}}

	public void setUser(User user) {
		this.user = user;
	}
}
