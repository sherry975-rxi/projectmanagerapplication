package project.ui.console.collaborator;

import project.controller.US207and208CreateUpdateTaskReportControllers;
import project.model.User;
import project.ui.console.MainMenuUI;

import java.util.Scanner;

public class US207And208CreateUpdateTaskReportUI {

	private String taskID;
	US207and208CreateUpdateTaskReportControllers createReportController;
	private Integer projectID;
	User user;
	private Boolean isPreviousUIFromTasks;

	public US207And208CreateUpdateTaskReportUI(String email, String taskID) {

		this.createReportController = new US207and208CreateUpdateTaskReportControllers(email, taskID);
		this.taskID = taskID;
	}

	public void createReport() {


		Scanner input = new Scanner(System.in);
		if ("".equals(createReportController.getReportedCollaboratorName())) {
			System.out.println("The User still didn't create a Report.");

		} else {
			System.out.println("______________________________________________");
			System.out.println("");

			System.out.println("Report by:     " + createReportController.getReportedCollaboratorName());
			System.out.println("Reported time: " + createReportController.getReportedTimeByCollaborator());
			System.out.println("");
			System.out.println("______________________________________________");
			System.out.println("");

			System.out.println("Update Task Report");
		}

		System.out.println("Type a report time to the task or choose a option above:");
		System.out.println("");
		System.out.println("[B] Back");
		System.out.println("[M] MainMenu");
		System.out.println("[E] Exit");
		
		String newTime = input.next().toUpperCase();
		switch (newTime) {
		case "B":
			TaskDetailsUI previousUIView = new TaskDetailsUI(this.taskID, this.projectID, user, this.isPreviousUIFromTasks);
			previousUIView.taskDataDisplay();
			break;
		case "M":
			MainMenuUI.mainMenu();
			break;
		case "E":
			System.out.println("----YOU HAVE EXIT FROM APPLICATION----");
			break;
		default:
			tryToUpdateTaskReport(newTime);
			break;

		}
			
		
	}

	public void tryToUpdateTaskReport(String newTime){

		boolean wasReportCreated;

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