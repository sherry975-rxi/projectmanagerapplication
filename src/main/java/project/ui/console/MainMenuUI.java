package project.ui.console;

import java.util.Calendar;
import java.util.Scanner;

import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.User;
import project.model.taskStateInterface.Cancelled;
import project.model.taskStateInterface.OnGoing;
import project.ui.console.uiAdministrator.AdminMenuUI;
import project.ui.console.uiCollaborator.CollaboratorMainMenuUI;
import project.ui.console.uiCollaborator.US101UserRegisterUI;
import project.ui.console.uiCollaborator.US208LoginUI;
import project.ui.console.uiDirector.DirectorMenuUI;

public class MainMenuUI {

	private static Company myCompany;
	private static User userAdmin;
	private static User userDirector;
	private static User userJSilva;
	private static User userATirapicos;
	private static User projectManager;
	private static Project projectGP;
	private static Project projectApostas;
	private static Project projectHomeBanking;
	private static ProjectCollaborator projcollabJSilva;
	private static ProjectCollaborator projcollabATirapicos;
	private static ProjectCollaborator projcollabManager;
	private static Task taskGP1;
	private static Task taskGP2;
	private static Task taskGP3;
	private static Task taskGP4;
	private static Task taskHB5;

	public static void main(String[] args) {

		// Instantiates the company
		myCompany = Company.getTheInstance();

		// Instantiate the users, sets their passwords
		userAdmin = myCompany.getUsersRepository().createUser("Teresa Ribeiro", "admin@gmail.com", "001",
				"Administrator", "917653635", "Avenida dos Aliados", "4000-654", "Porto", "Porto", "Portugal");
		userAdmin.setPassword("123456");
		userDirector = myCompany.getUsersRepository().createUser("Roberto Santos", "director@gmail.com", "002",
				"Director", "917653636", "Avenida dos Aliados", "4000-654", "Porto", "Porto", "Portugal");
		userDirector.setPassword("abcdef");

		userJSilva = myCompany.getUsersRepository().createUser("João Silva", "jsilva@gmail.com", "010", "Comercial",
				"937653635", "Avenida dos Aliados", "4000-654", "Porto", "Porto", "Portugal");
		userJSilva.setPassword("switch");
		userATirapicos = myCompany.getUsersRepository().createUser("Andreia Tirapicos", "atirapicos@gmail.com", "011",
				"Comercial", "955553635", "Avenida de Franca", "4455-654", "Leca da Palmeira", "Porto", "Portugal");
		userATirapicos.setPassword("tirapicos");
		projectManager = myCompany.getUsersRepository().createUser("Sara Pereira", "spereira@gmail.com", "012",
				"Técnica de recursos humanos", "9333333", "Rua Torta", "4455-666", "Leca da Palmeira", "Porto",
				"Portugal");
		// addition of users to the company
		myCompany.getUsersRepository().addUserToUserRepository(userAdmin);
		myCompany.getUsersRepository().addUserToUserRepository(userDirector);
		myCompany.getUsersRepository().addUserToUserRepository(userJSilva);
		myCompany.getUsersRepository().addUserToUserRepository(userATirapicos);
		myCompany.getUsersRepository().addUserToUserRepository(projectManager);

		// set user as collaborator
		userDirector.setUserProfile(Profile.DIRECTOR);
		userJSilva.setUserProfile(Profile.COLLABORATOR);
		userATirapicos.setUserProfile(Profile.COLLABORATOR);
		projectManager.setUserProfile(Profile.COLLABORATOR);

		// Instantiates a project and add it to the company
		projectGP = myCompany.getProjectsRepository().createProject("Gestão de Projetos",
				"Aplicação para Gestão de Projetos", projectManager);
		projectApostas = myCompany.getProjectsRepository().createProject("Apostas Online",
				"Plataforma Web para Apostas", projectManager);
		projectHomeBanking = myCompany.getProjectsRepository().createProject("HomeBanking",
				"Aplicação iOS para HomeBanking", userJSilva);

		TaskCollaborator tWorkerJSilva;
		TaskCollaborator tWorkerATirapicos;

		// Add data to project1
		// add start date to project
		Calendar startDate = Calendar.getInstance();
		startDate.set(2017, Calendar.JANUARY, 2, 12, 31, 00);
		projectGP.setStartdate(startDate);

		// add finish date to project
		Calendar finishDate = Calendar.getInstance();
		finishDate.set(2017, Calendar.FEBRUARY, 2, 12, 31, 00);
		projectGP.setFinishdate(finishDate);

		// addition of projects to the company
		myCompany.getProjectsRepository().addProjectToProjectRepository(projectGP);
		myCompany.getProjectsRepository().addProjectToProjectRepository(projectApostas);
		myCompany.getProjectsRepository().addProjectToProjectRepository(projectHomeBanking);

		// Create Project collaborators
		projcollabJSilva = new ProjectCollaborator(userJSilva, 2);
		projcollabATirapicos = new ProjectCollaborator(userATirapicos, 2);
		projcollabManager = new ProjectCollaborator(projectManager, 2);

		// addition of ProjectCollaborators 1 and 2 to projectGP and projectApostas
		projectGP.addProjectCollaboratorToProjectTeam(projcollabJSilva);
		projectGP.addProjectCollaboratorToProjectTeam(projcollabATirapicos);
		projectApostas.addProjectCollaboratorToProjectTeam(projcollabJSilva);
		projectApostas.addProjectCollaboratorToProjectTeam(projcollabATirapicos);

		// addition of ProjectCollaborators 2 and 3(user projectManager) to
		// projectHomeBanking
		projectHomeBanking.addProjectCollaboratorToProjectTeam(projcollabATirapicos);
		projectHomeBanking.addProjectCollaboratorToProjectTeam(projcollabManager);

		// Instantiates a task
		taskGP1 = projectGP.getTaskRepository().createTask("Desenvolver código para responder à US399");
		projectGP.getTaskRepository().addProjectTask(taskGP1);
		// Creates a new taksCollaborator
		tWorkerJSilva = new TaskCollaborator(projcollabJSilva);
		// Adds the taskCollaborator to task1
		taskGP1.addTaskCollaboratorToTask(tWorkerJSilva);

		OnGoing onGoingState = new OnGoing(taskGP1);
		taskGP1.setTaskState(onGoingState);
		taskGP1.setStartDate(startDate);

		// create task deadline
		Calendar taskDeadlineDate = Calendar.getInstance();
		taskDeadlineDate.set(Calendar.YEAR, 2017);
		taskDeadlineDate.set(Calendar.MONTH, Calendar.FEBRUARY);
		taskGP1.setTaskDeadline(taskDeadlineDate);

		taskGP1.createReport(tWorkerJSilva);
		taskGP1.getReports().get(0).setReportedTime(20);

		taskGP2 = projectGP.getTaskRepository().createTask("Desenvolver código para responder à US122");
		projectGP.getTaskRepository().addProjectTask(taskGP2);

		// Instantiates a task
		taskGP3 = projectGP.getTaskRepository().createTask("Fazer refator");
		projectGP.getTaskRepository().addProjectTask(taskGP3);
		// Creates a new taksCollaborator
		tWorkerATirapicos = new TaskCollaborator(projcollabATirapicos);
		// necessary to pass from "Created" to "Planned"
		startDate = Calendar.getInstance();
		startDate.add(Calendar.MONTH, -1);
		taskGP3.setEstimatedTaskStartDate(startDate);
		finishDate = Calendar.getInstance();
		finishDate.add(Calendar.MONTH, 1);
		taskGP3.setTaskDeadline(finishDate);
		taskGP3.getTaskState().changeToPlanned();
		// necessary to pass from "Planned" to "Assigned"
		taskGP3.addProjectCollaboratorToTask(projcollabJSilva);
		taskGP3.getTaskState().changeToAssigned();
		// pass from "Assigned" to "Ready"
		taskGP3.getTaskState().changeToReady();
		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) startDate.clone();
		taskGP3.setStartDate(startDate);
		taskGP3.getTaskState().changeToOnGoing();
		// pass from "OnGoing" to "Finished"
		Calendar testDate = (Calendar) finishDate.clone();
		taskGP3.setFinishDate(testDate);
		taskGP3.getTaskState().changeToFinished();
		// Adds the taskCollaborator to taskGP3
		taskGP3.addTaskCollaboratorToTask(tWorkerATirapicos);

		// Creates a taskGP4 and sets it to cancelled
		taskGP4 = projectGP.getTaskRepository().createTask("Cancelled Task");
		projectGP.getTaskRepository().addProjectTask(taskGP4);
		Cancelled cancelledState = new Cancelled(taskGP4);
		taskGP4.setTaskState(cancelledState);

		// Create taskHB5 of projectHomeBanking
		taskHB5 = projectHomeBanking.getTaskRepository().createTask("Implementar sistema de segurança");
		projectHomeBanking.getTaskRepository().addProjectTask(taskHB5);
		// necessary to pass from "Created" to "Planned"
		startDate = Calendar.getInstance();
		startDate.add(Calendar.MONTH, -1);
		taskHB5.setEstimatedTaskStartDate(startDate);
		finishDate = Calendar.getInstance();
		finishDate.add(Calendar.MONTH, 1);
		taskHB5.setTaskDeadline(finishDate);
		taskHB5.getTaskState().changeToPlanned();
		// necessary to pass from "Planned" to "Assigned"
		taskHB5.addProjectCollaboratorToTask(projcollabManager);
		taskHB5.getTaskState().changeToAssigned();
		// pass from "Assigned" to "Ready"
		taskHB5.getTaskState().changeToReady();
		// necessary to pass from "Ready" to "OnGoing"
		taskHB5.setStartDate(startDate);
		taskHB5.getTaskState().changeToOnGoing();

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
			System.out.println("[3] Administrator");
			System.out.println("[4] Director");
			System.out.println("[5] Collaborator");
			System.out.println("______________________________________________");
			System.out.println("[E] Exit");
			System.out.println();
			System.out.println("Please select an option:");
			System.out.println();

			String choice = input.nextLine().toUpperCase();
			System.out.println();
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
				userJSilva.setUserProfile(Profile.COLLABORATOR);
				CollaboratorMainMenuUI collaboratorMenu = new CollaboratorMainMenuUI(userJSilva);
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