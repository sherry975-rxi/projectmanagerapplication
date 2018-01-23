package project.ui;

import java.util.Scanner;

import project.controller.US207CreateTaskReportController;

public class US207CreateTaskReportUI {

	private String email;
	private boolean wasReportCreated;
	private String taskID;
	US207CreateTaskReportController createReportController;

	public US207CreateTaskReportUI(String email, String taskID) {

		this.email = email;
		this.createReportController = new US207CreateTaskReportController(email);
		this.taskID = taskID;
	}

	public void createReport() {

		Scanner input = new Scanner(System.in);

		System.out.println("Reported Time to the Task:");
		System.out.println(createReportController.getReportedTimeByCollaborator(taskID));

		System.out.println("");

		System.out.println("Update Task Report:");
		System.out.println("");
		System.out.println("Type a report time to the task:");
		System.out.println();
		String newTime = input.next();
		int newTimeToInt;
		try {
			newTimeToInt = Integer.parseInt(newTime);

			System.out.println();
			wasReportCreated = createReportController.createReportController(taskID, newTimeToInt);

			if (wasReportCreated) {
				System.out.println("The task report was updated sucessfully.");
				System.out.println();

				System.out.println("Reported time: " + createReportController.getReportedTimeByCollaborator(taskID));
			} else {
				System.out.println("The task report couldn't be updated");

			}
		} catch (NumberFormatException e) {
			System.out.println("Number not valid");
		}

	}

}