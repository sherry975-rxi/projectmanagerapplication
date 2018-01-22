package project.ui;

import java.util.Scanner;

import project.controller.SearchUsersController;
import project.controller.US208ChangeReportedTimeInTaskController;

public class US208ChangeReportedTimeInTask {

	public void changeReportedTime() {

		boolean wasTaskUpdated;
		US208ChangeReportedTimeInTaskController changeReportedTimeController;
		SearchUsersController searchUserByEmail = new SearchUsersController();

		Scanner input = new Scanner(System.in);
		System.out.println("Change Task Reported Time");
		System.out.println("");
		System.out.println("Type your Email:");

		String userEmail = input.next();
		if (!searchUserByEmail.searchUsersByEmailController(userEmail).isEmpty()) {
			changeReportedTimeController = new US208ChangeReportedTimeInTaskController(userEmail);
			System.out.println();
			System.out.println("List of active tasks:");
			System.out.println();

			for (String taskId : changeReportedTimeController.getOnGoingIDTasksOfUser()) {
				System.out.println("TaskID: " + "[" + taskId + "]");
			}
			System.out.println("Type the ID of the task you want change it's reported time:");
			System.out.println();
			String taskID = input.next();
			System.out.println("Type the new reported time:");
			String newTime = input.next();
			int newTimeToInt = 0;
			try {
				newTimeToInt = Integer.parseInt(newTime);
			} catch (NumberFormatException e) {
				System.out.println("The user didn't type a valid ID");
				return;
			}
			wasTaskUpdated = changeReportedTimeController.correctReportedTimeInTaskController(taskID, newTimeToInt);
			System.out.println();
			if (wasTaskUpdated) {
				System.out.println("The task report was updated sucessfully.");
				System.out.println(changeReportedTimeController.getReportedTimeByCollaboratorController(taskID));
			} else {
				System.out.println("The task report couldn't be updated");

			}
		}

		else {
			System.out.println("User doesn't exist.");
		}
	}

}
