package project.ui;

import java.util.Calendar;
import java.util.Scanner;

import project.controller.PrintUserInfoController;
import project.model.Company;
import project.model.Project;
import project.model.Task;
import project.model.User;

public class MainMenuUI {

	private static Company myCompany;
	private static User user1;
	private static User user;
	private static Project project1;
	private static Project project2;
	private static Project project3;
	private static Task task1;
	private static Task task2;

	public static void main(String[] args) {

		// Instantiates the company
		myCompany = Company.getTheInstance();

		// Instantiate a user and set its password
		user1 = myCompany.getUsersRepository().createUser("Manel", "user2@gmail.com", "001", "Empregado", "930000000",
				"rua cinzenta", "6789-654", "porto", "porto", "portugal");
		user1.setPassword("manel123");
		myCompany.getUsersRepository().addUserToUserRepository(user1);

		// Instantiates a project
		project1 = myCompany.getProjectsRepository().createProject("ProjectA", "description", user1);
		myCompany.getProjectsRepository().addProjectToProjectRepository(project1);
		project2 = myCompany.getProjectsRepository().createProject("ProjectB", "description", user1);
		myCompany.getProjectsRepository().addProjectToProjectRepository(project2);
		project3 = myCompany.getProjectsRepository().createProject("ProjectC", "description", user1);
		myCompany.getProjectsRepository().addProjectToProjectRepository(project3);

		// Instantiates the dates to set as estimated start date and task deadline
		Calendar estimatedTaskStartDate = Calendar.getInstance();
		Calendar taskDeadline = Calendar.getInstance();
		estimatedTaskStartDate.set(2017, Calendar.DECEMBER, 22);
		taskDeadline.set(2018, Calendar.DECEMBER, 22);

		// Instantiates the tasks
		task1 = project1.getTaskRepository().createTask("description", 10, estimatedTaskStartDate, taskDeadline, 1000);
		task2 = project1.getTaskRepository().createTask("descriptionA", 10, estimatedTaskStartDate, taskDeadline, 1000);

		mainMenu();
	}

	public static void mainMenu() {

		Scanner input = new Scanner(System.in);
		boolean condition = true;
		do {
			System.out.println("Choose a user story:");
			System.out.println("102");
			System.out.println("180");
			System.out.println("201");
			System.out.println("342");
			System.out.println("998 to view all users");
			System.out.println("999 to view user's address");
			System.out.println("0 to exit");
			System.out.println();

			int choice = Integer.parseInt(input.nextLine());
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
			case 342:
				DefineDependenciesBetweenTasksUI defineDependenciesBetweenTasksUI = new DefineDependenciesBetweenTasksUI(
						user1);
				defineDependenciesBetweenTasksUI.chooseProject();
				mainMenu();
				break;
			case 998:
				PrintUserInfoController userInfo = new PrintUserInfoController();
				userInfo.printAllUsersInfo();
				break;
			case 999:
				System.out.println("Please provide an email to select a user:");
				String email = input.nextLine();
				PrintUserInfoController userAddressInfo = new PrintUserInfoController();
				User userToSearchAddress = userAddressInfo.getUserByEmailController(email);
				userAddressInfo.printAllAddressesFromUser(userToSearchAddress);
				break;
			case 0:
				condition = false;
				break;
			}
		} while (condition);
	}

}
