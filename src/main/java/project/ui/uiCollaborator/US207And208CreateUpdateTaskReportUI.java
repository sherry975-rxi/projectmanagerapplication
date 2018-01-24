package project.ui.uiCollaborator;

import java.util.Scanner;

import project.controller.US207and208CreateUpdateTaskReportControllers;

public class US207And208CreateUpdateTaskReportUI {

	private String email;
	private boolean wasReportCreated;
	private String taskID;
	US207and208CreateUpdateTaskReportControllers createReportController;

	public US207And208CreateUpdateTaskReportUI(String email, String taskID) {

		this.email = email;
		this.createReportController = new US207and208CreateUpdateTaskReportControllers(email, taskID);
		this.taskID = taskID;
	}

	public void createReport() {

		Scanner input = new Scanner(System.in);
		if (createReportController.getReportedCollaboratorName().equals("")) {
			System.out.println("The User still didn't create a Report.");

		} else {
			System.out.println("______________________________________________");
			System.out.println("");

			System.out.println("Report by:     " + createReportController.getReportedCollaboratorName());
			System.out.println("Reported time: " + createReportController.getReportedTimeByCollaborator());
			System.out.println("");
			System.out.println("______________________________________________");
			System.out.println("");

			System.out.println("Update Task Report:");
		}

		System.out.println("");
		System.out.println("Type a report time to the task:");
		System.out.println();
		String newTime = input.next();
		int newTimeToInt;
		try {
			newTimeToInt = Integer.parseInt(newTime);

			System.out.println();
			wasReportCreated = createReportController.createReportController(newTimeToInt);

			if (wasReportCreated) {
				System.out.println("The task report was updated sucessfully.");
				System.out.println();
				System.out.println("Report by: " + createReportController.getReportedCollaboratorName());

				System.out.println("Reported time: " + createReportController.getReportedTimeByCollaborator());
			} else {
				System.out.println("The task report couldn't be updated");

			}
		} catch (NumberFormatException e) {
			System.out.println("Number not valid");
		}

	}

}