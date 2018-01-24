package project.ui;

import java.util.Calendar;
import java.util.Scanner;

import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.User;
import project.model.taskStateInterface.OnGoing;
import project.ui.uiAdministrator.AdminMenuUI;
import project.ui.uiCollaborator.CollaboratorMainMenuUI;
import project.ui.uiCollaborator.DirectorMenuUI;
import project.ui.uiCollaborator.US101UserRegisterUI;
import project.ui.uiCollaborator.US208LoginUI;

public class MainMenuUI {

	private static Company myCompany;
	private static User userAdmin;
	private static User userDirector;
	private static User user1;
	private static User user2;
	private static User projectManager;
	private static Project project1;
	private static Project project2;
	private static Project project3;
	private static ProjectCollaborator projcollab1;
	private static ProjectCollaborator projcollab2;
	private static ProjectCollaborator projcollab3;
	private static Task task1;
	private static Task task2;

	public static void main(String[] args) {

		// Instantiates the company
		myCompany = Company.getTheInstance();

		// Instantiate the users, sets their passwords
		userAdmin = myCompany.getUsersRepository().createUser("John Cena", "admin@gmail.com", "001", "Admin grupo 3",
				"917653635", "Avenida dos Aliados", "4000-654", "Porto", "Porto", "Portugal");
		userAdmin.setPassword("123456");
		userDirector = myCompany.getUsersRepository().createUser("Jane Doe", "director@gmail.com", "002",
				"Director grupo 3", "917653636", "Avenida dos Aliados", "4000-654", "Porto", "Porto", "Portugal");
		userDirector.setPassword("abcdef");

		user1 = myCompany.getUsersRepository().createUser("Joao Silva", "aluno_3_@gmail.com", "010",
				"Estudante Grupo 3", "937653635", "Avenida dos Aliados", "4000-654", "Porto", "Porto", "Portugal");
		user1.setPassword("switch");
		user2 = myCompany.getUsersRepository().createUser("Andreia Tirapicos", "aluno_2_@gmail.com", "011",
				"Estudante Grupo 3", "955553635", "Avenida de Franca", "4455-654", "Leca da Palmeira", "Matosinhos",
				"Portugal");
		user2.setPassword("tirapicos");
		projectManager = myCompany.getUsersRepository().createUser("Sara Pereira", "aluno_1_@gmail.com", "012",
				"Estudante Grupo 3", "9333333", "Rua Torta", "4455-666", "Leca da Palmeira", "Matosinhos", "Portugal");
		// addition of users to the company
		myCompany.getUsersRepository().addUserToUserRepository(userAdmin);
		myCompany.getUsersRepository().addUserToUserRepository(userDirector);
		myCompany.getUsersRepository().addUserToUserRepository(user1);
		myCompany.getUsersRepository().addUserToUserRepository(user2);
		myCompany.getUsersRepository().addUserToUserRepository(projectManager);

		// set user as collaborator
		userDirector.setUserProfile(Profile.DIRECTOR);
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);
		projectManager.setUserProfile(Profile.COLLABORATOR);

		// Instantiates a project and add it to the company
		project1 = myCompany.getProjectsRepository().createProject("Projeto GP", "Aplicação para Gestão de Projetos",
				projectManager);
		project2 = myCompany.getProjectsRepository().createProject("Projeto Apostas", "Plataforma Web para Apostas",
				projectManager);
		project3 = myCompany.getProjectsRepository().createProject("Projeto HomeBanking",
				"Aplicação iOS para HomeBanking", user1);

		TaskCollaborator tWorker1;

		// Add data to project1
		// add start date to project
		Calendar startDate = Calendar.getInstance();
		startDate.set(2017, Calendar.JANUARY, 2, 12, 31, 00);
		project1.setStartdate(startDate);

		// add finish date to project
		Calendar finishDate = Calendar.getInstance();
		finishDate.set(2017, Calendar.FEBRUARY, 2, 12, 31, 00);
		project1.setFinishdate(finishDate);

		// addition of projects to the company
		myCompany.getProjectsRepository().addProjectToProjectRepository(project1);
		myCompany.getProjectsRepository().addProjectToProjectRepository(project2);
		myCompany.getProjectsRepository().addProjectToProjectRepository(project3);

		// Create Project collaborators
		projcollab1 = new ProjectCollaborator(user1, 2);
		projcollab2 = new ProjectCollaborator(user2, 2);
		projcollab3 = new ProjectCollaborator(projectManager, 2);

		// addition of ProjectCollaborators 1 and 2 to project1 and project2
		project1.addProjectCollaboratorToProjectTeam(projcollab1);
		project1.addProjectCollaboratorToProjectTeam(projcollab2);
		project2.addProjectCollaboratorToProjectTeam(projcollab1);
		project2.addProjectCollaboratorToProjectTeam(projcollab2);

		// addition of ProjectCollaborators 2 and 3(user projectManager) to project3
		project3.addProjectCollaboratorToProjectTeam(projcollab2);
		project3.addProjectCollaboratorToProjectTeam(projcollab3);

		// Instantiates a task
		task1 = project1.getTaskRepository().createTask("Desenvolver código para responder à US399");
		project1.getTaskRepository().addProjectTask(task1);
		// Creates a new taksCollaborator
		tWorker1 = new TaskCollaborator(projcollab1);
		// Adds the taskCollaborator to task1
		task1.addTaskCollaboratorToTask(tWorker1);

		OnGoing onGoingState = new OnGoing(task1);
		task1.setTaskState(onGoingState);
		task1.setStartDate(startDate);

		task1.createReport(tWorker1);
		task1.getReports().get(0).setReportedTime(20);

		task2 = project1.getTaskRepository().createTask("Desenvolver código para responder à US122");
		project1.getTaskRepository().addProjectTask(task2);

		// project1.setStartdate(Calendar.getInstance());
		// PrintProjectInfoController projectInfo = new
		// PrintProjectInfoController(project1);
		// System.out.println("Start date: " + projectInfo.printProjectStartDateInfo());
		mainMenu();
	}

	public static void mainMenu() {

		printImage();

		Scanner input = new Scanner(System.in);
		boolean condition = true;
		while (condition) {

			System.out.println();
			System.out.println("                  MAIN MENU");
			System.out.println("______________________________________________");
			System.out.println("[1] New Registration");
			System.out.println("[2] Login");
			System.out.println("[3] Administrator (Demo mode - John Cena)");
			System.out.println("[4] Director (Demo mode - Jane Doe)");
			System.out.println("[5] Collaborator");
			System.out.println("______________________________________________");
			System.out.println("[E] Exit");
			System.out.println();

			String choice = input.nextLine().toUpperCase();
			switch (choice) {
			case "1":
				US101UserRegisterUI userRegister = new US101UserRegisterUI();
				userRegister.userRegister();
				break;
			case "2":
				US208LoginUI doLogin = new US208LoginUI();
				doLogin.doLogin();
				break;
			case "3":
				AdminMenuUI adminMenu = new AdminMenuUI(userAdmin);
				adminMenu.adminMenu();
				break;

			case "4":
				userDirector.setUserProfile(Profile.DIRECTOR);
				DirectorMenuUI directorMenu = new DirectorMenuUI(userDirector);
				directorMenu.directorMenu();
				break;

			case "5":
				CollaboratorMainMenuUI collaboratorMenu = new CollaboratorMainMenuUI(user1);
				collaboratorMenu.displayOptions();

				break;
			case "E":
				System.out.println();
				System.out.println("--YOU HAVE EXIT FROM MAIN MENU--");
				condition = false;

				break;

			}
		}
	}

	public static void printImage() {

		System.out.println(
				"                                                                                                  ");
		System.out.println(
				"                                                                                                    ");
		System.out.println(
				"                                                                                                    ");
		System.out.println(
				"              ://-           `.--.`                                            `://.                ");
		System.out.println(
				"            .dmmmmh`        -//////.                                          /mmmmms               ");
		System.out.println(
				"            :mmmmmm-        ///////:                                          smmmmmm               ");
		System.out.println(
				"             hNmdy:         ./++++/.                  `       `               `ohmNm/               ");
		System.out.println(
				"            oNNm-   ////+++++++++++++++++++/          ssssssss-      /ssssyyyo   oNNm-              ");
		System.out.println(
				"           +NNNNm-  /++++++++++++++++++++++/          sssssssy       .yyyyyyyo  oNNNNm.             ");
		System.out.println(
				"          :NNNNNNm//+++++++++++++++++++++++/          ssssssss+.    .oyyyyyyys/sNNNNNNd`            ");
		System.out.println(
				"         -NNNsmNNNNNmmdo+++++++++++++++++++/      ``  ssyyyyyyyysoosyyyyyyhmmNNNNNNymNNh            ");
		System.out.println(
				"        `mNNd`.+osyhmNNs+++++++++++++/.   `-   `+ssss/syyyyyyyyyyyyyyyyyyydNNmhys+/`:NNNs           ");
		System.out.println(
				"        `NNNNs`     ++++++++++++++o++          syyyyyyyyyyyyyyyyyyyyyyyyyyyyys     :dNNNy           ");
		System.out.println(
				"         NNmmNNo`   ++++++++oooooooo+`         oyyyyyyyyyyyyyyyyyyyyyyyyyyyyys   .yNNdNNs           ");
		System.out.println(
				"         mMm`sMMh   ++ooooooooooooooo+:.`.-:    :+oo+:yyyyyyyyyyyyyyyyyyyyyyys  .NMm/:MMo           ");
		System.out.println(
				"        yMMy .MMm   +oooooooo/-..:+oooooooo+         `yyyyyyyyyyyyyyyyyyyyhhhs  :MMd .NMN/          ");
		System.out.println(
				"      `hMMd` :MMM   oooooooo.      :ooooooo+          yyyyyyyyyyyyyyhhhhhhhhhs  /MMm  -NMM+         ");
		System.out.println(
				"     `dMMm`  /MMM`  oooooooo       .ooooooo+          yyyyyyyyhhhhhhhhhhhhhhhs  oMMM   /MMMo        ");
		System.out.println(
				"    `:dmm: ``/dmh` `oooooooo/`   `.oooooooo+         `yyyyyyyyyyyyyyyyyyyyyyys  /mmh.`  ommy.``     ");
		System.out.println(
				"                                                                                                    ");
		System.out.println(
				"                                                                                                    ");
	}
}
