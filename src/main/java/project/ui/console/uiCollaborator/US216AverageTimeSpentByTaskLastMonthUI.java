package project.ui.console.uiCollaborator;

import java.util.Scanner;

import project.controller.US216AverageTimeSpentOnTaskLastMonthController;
import project.model.User;

public class US216AverageTimeSpentByTaskLastMonthUI {

	User user;

	public void displayAveregeTimeSpentByTaskLastMonth() {

		String myname = user.getName();
		String function = user.getFunction().toUpperCase();

		System.out.println("\n" + myname + " \n" + function);
		System.out.println("___________________________________________________");

		US216AverageTimeSpentOnTaskLastMonthController averageTime = new US216AverageTimeSpentOnTaskLastMonthController();
		Double average = averageTime.getAverageTimeOfFinishedTasksFromUserLastMonth(user);

		System.out.println("\n");
		System.out.println("AVERAGE TIME SPENT BY TASK: " + average + " " + "Hours");
		System.out.println("__________________________________________________");
		System.out.println("[B] Back");
		System.out.println("[M] MainMenu");
		System.out.println("[E] Exit \n");
		backMenu();
	}

	public void backMenu() {

		Scanner scannerInput = new Scanner(System.in);
		String option = scannerInput.nextLine().toUpperCase();

		switch (option) {

		case "1":
			break;
		case "2":
			break;
		case "3":
			System.out.println();
			System.out.println("--YOU HAVE EXIT FROM MAIN MENU--");
			System.exit(0);
			break;
		}
	}
}
