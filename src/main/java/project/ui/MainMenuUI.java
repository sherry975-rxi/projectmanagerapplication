package project.ui;

import java.util.Calendar;
import java.util.Scanner;

import project.controller.PrintUserInfoController;
import project.model.Company;
import project.model.Project;
import project.model.Task;
import project.model.User;

public class MainMenuUI {

	private static User user1;

	public static void main(String[] args) {
		Company myCompany;
		Project project1;
		Project project2;
		Project project3;
		Task task1;
		Task task2;

		// Instantiates the company
		myCompany = Company.getTheInstance();

		// Instantiate a user and set its password
		user1 = myCompany.getUsersRepository().createUser("Manel", "user2@gmail.com", "001", "Empregado", "930000000",
				"rua cinzenta", "6789-654", "porto", "porto", "portugal");
		user1.setPassword("manel123");
		myCompany.getUsersRepository().addUserToUserRepository(user1);

		// Instantiates a project
		project1 = myCompany.getProjectsRepository().createProject("ProjectA", "descriptionA", user1);
		myCompany.getProjectsRepository().addProjectToProjectRepository(project1);
		project2 = myCompany.getProjectsRepository().createProject("ProjectB", "descriptionB", user1);
		myCompany.getProjectsRepository().addProjectToProjectRepository(project2);
		project3 = myCompany.getProjectsRepository().createProject("ProjectC", "descriptionC", user1);
		myCompany.getProjectsRepository().addProjectToProjectRepository(project3);

		// Instantiates the dates to set as estimated start date and task deadline
		Calendar estimatedTaskStartDate = Calendar.getInstance();
		Calendar taskDeadline = Calendar.getInstance();
		estimatedTaskStartDate.set(2018, Calendar.JANUARY, 5);
		taskDeadline.set(2018, Calendar.DECEMBER, 22);

		// Instantiates the tasks
		task1 = project1.getTaskRepository().createTask("Tarefa aaaa", 10, estimatedTaskStartDate, taskDeadline, 1000);
		task2 = project1.getTaskRepository().createTask("Tarefa bbbb", 10, estimatedTaskStartDate, taskDeadline, 1000);
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);

		mainMenu();
	}

	public static void mainMenu() {

		Scanner input = new Scanner(System.in);
		boolean condition = true;
		while (condition) {
			System.out.println("Please select a user story:");
			System.out.println("102 - Regist user");
			System.out.println("180 - Login");
			System.out.println("201 - Update user info");
			System.out.println("342 - Create dependencies between tasks");
			System.out.println("998 - View all users");
			System.out.println("999 - View user address");
			System.out.println("0 - EXIT");
			System.out.println();

			String choice = input.nextLine();
			switch (choice) {
			case "102":
				UserRegisterUI userRegister = new UserRegisterUI();
				userRegister.userRegister();
				break;
			case "180":
				LoginUI doLogin = new LoginUI();
				doLogin.doLogin();
				break;
			case "201":
				UpdateUserInfoUI updateUserInfo = new UpdateUserInfoUI(user1);
				updateUserInfo.chooseWhatInfoToUpdate();
				break;
			case "342":
				DefineDependenciesBetweenTasksUI defineDependenciesBetweenTasksUI = new DefineDependenciesBetweenTasksUI(
						user1);
				defineDependenciesBetweenTasksUI.chooseProject();
				break;
			case "998":
				PrintUserInfoController userInfo = new PrintUserInfoController();
				userInfo.printAllUsersInfo();
				break;
			case "999":
				System.out.println("Please provide an email to select a user:");
				String email = input.nextLine();
				PrintUserInfoController userAddressInfo = new PrintUserInfoController();
				User userToSearchAddress = userAddressInfo.getUserByEmailController(email);
				userAddressInfo.printAllAddressesFromUser(userToSearchAddress);
				break;
			case "0":
				condition = false;
				break;
			}
		}
	}

}
