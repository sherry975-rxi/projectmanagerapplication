package project.ui;

import java.util.Scanner;

import project.controller.SearchUsersController;
import project.controller.US208ChangeReportedTimeInTaskController;

public class US208ChangeReportedTimeInTaskUI {

	public void changeReportedTime() {

		boolean wasTaskUpdated;
		String idTask;
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
			System.out.println();

			System.out.println("Type the ID of the task you want change it's reported time:");
			System.out.println();

			String taskID = input.next();
			System.out.println("Reported time to the task:  "
					+ changeReportedTimeController.getReportedTimeByCollaboratorController(taskID));

			System.out.println("Type the new reported time:");
			System.out.println();
			String newTime = input.next();
			int newTimeToInt;
			try {
				newTimeToInt = Integer.parseInt(newTime);

				System.out.println();
				wasTaskUpdated = changeReportedTimeController.correctReportedTimeInTaskController(taskID, newTimeToInt);

				if (wasTaskUpdated) {
					System.out.println("The task report was updated sucessfully.");
					System.out.println("New reported time: "
							+ changeReportedTimeController.getReportedTimeByCollaboratorController(taskID));
				} else {
					System.out.println("The task report couldn't be updated");
					System.out.println(changeReportedTimeController.getReportedTimeByCollaboratorController(taskID));

				}
			} catch (NumberFormatException e) {
				System.out.println("Number not valid");
			}

		}

		else {
			System.out.println("User doesn't exist.");
		}
	}

}
