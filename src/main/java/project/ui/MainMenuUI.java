package project.ui;

import java.util.Calendar;
import java.util.Scanner;

import project.model.Company;
import project.model.Project;
import project.model.Task;
import project.model.User;

public class MainMenuUI {

	private static Company myCompany;
	private static User user1;
	private static User user;
	private static Project project1;
	private static Task task1;
	private static Task task2;
	private static Calendar estimatedTaskStartDate = Calendar.getInstance();
	private static Calendar taskDeadline = Calendar.getInstance();

	public static void main(String[] args) {

		// Instantiates the company
		myCompany = Company.getTheInstance();

		// Instantiate a user
		user1 = myCompany.getUsersRepository().createUser("Manel", "user2@gmail.com", "001", "Empregado", "930000000",
				"rua cinzenta", "6789-654", "porto", "porto", "portugal");

		// Instantiates a project
		project1 = myCompany.getProjectsRepository().createProject("name", "description", user1);

		// Instantiates the dates to set as estimated start date and task deadline
		estimatedTaskStartDate.set(2017, Calendar.DECEMBER, 22);
		taskDeadline.set(2018, Calendar.DECEMBER, 22);

		// Instantiates the tasks
		task1 = project1.getTaskRepository().createTask("description", 10, estimatedTaskStartDate, taskDeadline, 1000);
		task2 = project1.getTaskRepository().createTask("descriptionA", 10, estimatedTaskStartDate, taskDeadline, 1000);

		mainMenu();
	}

	public static void mainMenu() {

		Scanner input = new Scanner(System.in);

		System.out.println("Choose a user story:");
		System.out.println("102");
		System.out.println("180");
		System.out.println("201");
		System.out.println("998 to view all users");
		System.out.println("999 to view user's address");
		System.out.println("0 to exit");

		int choice = input.nextInt();
		switch (choice) {
		case 102:
			UserRegisterUI userRegister = new UserRegisterUI();
			userRegister.userRegister();
			mainMenu();
			break;
		/*
		 * case 180: TODO insert Login UI break;
		 */
		case 201:
			UpdateUserInfoUI updateUserInfo = new UpdateUserInfoUI(user1);
			updateUserInfo.chooseWhatInfoToUpdate();
			mainMenu();
			break;
		case 998:
			break;
		case 0:
			// TODO method to close menu
			break;
		}
	}

}
