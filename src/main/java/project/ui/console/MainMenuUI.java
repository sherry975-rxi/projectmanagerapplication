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
	private static Task taskGP5;
	private static Task taskGP6;
	private static Task taskHB1;
	private static Task taskHB2;
	private static Task taskHB3;
	private static Task taskHB4;
	private static Task taskHB5;
	private static Task taskHB6;
	private static Task taskHB7;
	private static Task taskHB8;
	private static Task taskHB9;

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
		startDate.set(2018, Calendar.JANUARY, 23, 12, 31, 00);
		projectGP.setStartdate(startDate);

		// add finish date to project
		Calendar finishDate = Calendar.getInstance();
		finishDate.set(2018, Calendar.FEBRUARY, 2, 12, 31, 00);
		projectGP.setFinishdate(finishDate);

		// addition of projects to the company
		myCompany.getProjectsRepository().addProjectToProjectRepository(projectGP);
		myCompany.getProjectsRepository().addProjectToProjectRepository(projectApostas);
		myCompany.getProjectsRepository().addProjectToProjectRepository(projectHomeBanking);

		// set "EXECUTION" status of projects
		projectGP.setProjectStatus(2);
		projectApostas.setProjectStatus(2);
		projectHomeBanking.setProjectStatus(2);
		Calendar projstartdate = Calendar.getInstance();
		projstartdate.set(2017, 10, 05);
		projectHomeBanking.setStartdate(projstartdate);
		projectHomeBanking.setProjectBudget(5000);

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
		taskGP3 = projectGP.getTaskRepository().createTask("Fazer refatoração.");
		projectGP.getTaskRepository().addProjectTask(taskGP3);
		taskGP5 = projectGP.getTaskRepository().createTask("Adicionar colaboradores às tarefas planeadas.");
		projectGP.getTaskRepository().addProjectTask(taskGP5);
		taskGP6 = projectGP.getTaskRepository().createTask("Criar tarefas.");
		projectGP.getTaskRepository().addProjectTask(taskGP6);
		// Creates a new taksCollaborator
		tWorkerATirapicos = new TaskCollaborator(projcollabATirapicos);

		// taskGP#3 changes to "Finished"
		// changes from "Created" to "Planned"
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

		// taskGP#5 changes to finished
		// changes from "Created" to "Planned"
		Calendar estimatstartDate2 = Calendar.getInstance();
		estimatstartDate2.add(Calendar.YEAR, -1);
		estimatstartDate2.add(Calendar.MONTH, -3);
		estimatstartDate2.add(Calendar.DAY_OF_MONTH, 0);
		taskGP5.setEstimatedTaskStartDate(estimatstartDate2);
		Calendar finishDate2 = Calendar.getInstance();
		finishDate2.add(Calendar.YEAR, -1);
		finishDate2.add(Calendar.MONTH, -3);
		finishDate2.add(Calendar.DAY_OF_MONTH, 6);
		taskGP5.setTaskDeadline(finishDate2);
		taskGP5.getTaskState().changeToPlanned();
		// necessary to pass from "Planned" to "Assigned"
		taskGP5.addProjectCollaboratorToTask(projcollabJSilva);
		taskGP5.getTaskState().changeToAssigned();
		// pass from "Assigned" to "Ready"
		taskGP5.getTaskState().changeToReady();
		// necessary to pass from "Ready" to "OnGoing"
		Calendar startDate2 = Calendar.getInstance();
		startDate2.add(Calendar.YEAR, -1);
		startDate2.add(Calendar.MONTH, -3);
		startDate2.add(Calendar.DAY_OF_MONTH, 1);
		taskGP5.setStartDate(startDate2);
		taskGP5.getTaskState().changeToOnGoing();
		// pass from "OnGoing" to "Finished"
		Calendar testDate2 = Calendar.getInstance();
		testDate2.add(Calendar.YEAR, 0);
		testDate2.add(Calendar.MONTH, -1);
		testDate2.add(Calendar.DAY_OF_MONTH, 2);
		taskGP5.setFinishDate(testDate2);
		taskGP5.getTaskState().changeToFinished();

		// Creates a taskGP4 and sets it to cancelled
		taskGP4 = projectGP.getTaskRepository().createTask("Cancelled Task");
		projectGP.getTaskRepository().addProjectTask(taskGP4);
		Cancelled cancelledState = new Cancelled(taskGP4);
		taskGP4.setTaskState(cancelledState);

		// Create taskHB5 of projectHomeBanking
		taskHB1 = projectHomeBanking.getTaskRepository().createTask("Criar indicadores de acesso");
		projectHomeBanking.getTaskRepository().addProjectTask(taskHB1);
		taskHB2 = projectHomeBanking.getTaskRepository()
				.createTask("Permitir diferentes configurações para pagina pessoal");
		projectHomeBanking.getTaskRepository().addProjectTask(taskHB2);
		taskHB3 = projectHomeBanking.getTaskRepository().createTask("Permitir ligação a sites de noticias");
		projectHomeBanking.getTaskRepository().addProjectTask(taskHB3);
		taskHB4 = projectHomeBanking.getTaskRepository().createTask("Alterar configurações para acesso");
		projectHomeBanking.getTaskRepository().addProjectTask(taskHB4);
		taskHB5 = projectHomeBanking.getTaskRepository().createTask("Implementar sistema de segurança");
		projectHomeBanking.getTaskRepository().addProjectTask(taskHB5);
		taskHB6 = projectHomeBanking.getTaskRepository().createTask("Mostrar vista de administrador");
		projectHomeBanking.getTaskRepository().addProjectTask(taskHB6);
		taskHB7 = projectHomeBanking.getTaskRepository().createTask("Permitir alteração de dados de cliente");
		projectHomeBanking.getTaskRepository().addProjectTask(taskHB7);
		taskHB8 = projectHomeBanking.getTaskRepository().createTask("Gerar relatórios de investimentos");
		projectHomeBanking.getTaskRepository().addProjectTask(taskHB8);
		taskHB9 = projectHomeBanking.getTaskRepository().createTask("Criar botão personalizar");
		projectHomeBanking.getTaskRepository().addProjectTask(taskHB9);

		// Task 3.1 (taskHB1) is in state created

		// Task 3.2 (taskHB2) set to planned
		// necessary to pass from "Created" to "Planned"
		startDate = Calendar.getInstance();
		startDate.add(Calendar.MONTH, -1);
		taskHB2.setEstimatedTaskStartDate(startDate);
		finishDate = Calendar.getInstance();
		finishDate.add(Calendar.MONTH, 1);
		taskHB2.setTaskDeadline(finishDate);
		taskHB2.getTaskState().changeToPlanned();

		// Task 3.3 (taskHB3) set to assigned
		// necessary to pass from "Created" to "Planned"
		Calendar estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.add(Calendar.MONTH, -1);
		estimatedStartDate.add(Calendar.DAY_OF_MONTH, 5);
		taskHB3.setEstimatedTaskStartDate(estimatedStartDate);
		Calendar estimatedFinishDate1 = Calendar.getInstance();
		estimatedFinishDate1.add(Calendar.MONTH, -1);
		estimatedFinishDate1.add(Calendar.DAY_OF_MONTH, 6);
		taskHB3.setTaskDeadline(estimatedFinishDate1);
		taskHB3.getTaskState().changeToPlanned();
		// necessary to pass from "Planned" to "Assigned"
		taskHB3.addProjectCollaboratorToTask(projcollabManager);
		taskHB3.getTaskState().changeToAssigned();

		// Task 3.4 (taskHB4) set to ready
		// necessary to pass from "Created" to "Planned"
		Calendar estimatedStartDate2 = Calendar.getInstance();
		estimatedStartDate2.add(Calendar.MONTH, -1);
		estimatedStartDate2.add(Calendar.DAY_OF_MONTH, 6);
		taskHB4.setEstimatedTaskStartDate(estimatedStartDate2);
		Calendar estimatedFinishDate2 = Calendar.getInstance();
		estimatedFinishDate2.add(Calendar.MONTH, -1);
		estimatedFinishDate2.add(Calendar.DAY_OF_MONTH, 7);
		taskHB4.setTaskDeadline(estimatedFinishDate2);
		taskHB4.getTaskState().changeToPlanned();
		// necessary to pass from "Planned" to "Assigned"
		taskHB4.addProjectCollaboratorToTask(projcollabManager);
		taskHB4.getTaskState().changeToAssigned();
		// pass from "Assigned" to "Ready"
		taskHB4.getTaskState().changeToReady();

		// Task 3.5 (taskHB5) set to ongoing
		// necessary to pass from "Created" to "Planned"
		startDate = Calendar.getInstance();
		startDate.add(Calendar.MONTH, -1);
		taskHB5.setEstimatedTaskStartDate(startDate);
		Calendar estimatedFinishDate = Calendar.getInstance();
		estimatedFinishDate.add(Calendar.MONTH, 1);
		taskHB5.setTaskDeadline(finishDate);
		taskHB5.getTaskState().changeToPlanned();
		// necessary to pass from "Planned" to "Assigned"
		taskHB5.addProjectCollaboratorToTask(projcollabManager);
		taskHB5.addProjectCollaboratorToTask(projcollabATirapicos);
		taskHB5.getTaskState().changeToAssigned();
		// pass from "Assigned" to "Ready"
		taskHB5.getTaskState().changeToReady();
		// necessary to pass from "Ready" to "OnGoing"
		taskHB5.setStartDate(startDate);
		taskHB5.getTaskState().changeToOnGoing();

		// Task 3.6 (taskHB6) set to finished
		Calendar estimatedStartDate4 = Calendar.getInstance();
		estimatedStartDate4.add(Calendar.MONTH, -1);
		estimatedStartDate4.add(Calendar.DAY_OF_MONTH, 6);
		taskHB6.setEstimatedTaskStartDate(estimatedStartDate4);
		Calendar estimatedFinishDate4 = Calendar.getInstance();
		estimatedFinishDate4.add(Calendar.MONTH, -3);
		estimatedFinishDate4.add(Calendar.DAY_OF_MONTH, 17);
		taskHB6.setTaskDeadline(estimatedFinishDate4);
		taskHB6.getTaskState().changeToPlanned();
		// necessary to pass from "Planned" to "Assigned"
		taskHB6.addProjectCollaboratorToTask(projcollabManager);
		taskHB6.getTaskState().changeToAssigned();
		// pass from "Assigned" to "Ready"
		taskHB6.getTaskState().changeToReady();
		// necessary to pass from "Ready" to "OnGoing"
		taskHB6.setStartDate(startDate);
		taskHB6.getTaskState().changeToOnGoing();
		// necessary to pass from "OnGoing" to "Finished"
		Calendar finishDate1 = Calendar.getInstance();
		finishDate1.add(Calendar.MONTH, 0);
		finishDate1.add(Calendar.DAY_OF_MONTH, 13);
		taskHB6.setFinishDate(finishDate1);
		taskHB6.getTaskState().changeToFinished();

		// Task 3.7 (taskHB7) set to ongoing with expired deadline
		taskHB7.setEstimatedTaskStartDate(estimatedStartDate4);
		taskHB7.setTaskDeadline(estimatedFinishDate4);
		taskHB7.getTaskState().changeToPlanned();
		// necessary to pass from "Planned" to "Assigned"
		taskHB7.addProjectCollaboratorToTask(projcollabManager);
		taskHB7.getTaskState().changeToAssigned();
		// pass from "Assigned" to "Ready"
		taskHB7.getTaskState().changeToReady();
		// necessary to pass from "Ready" to "OnGoing"
		taskHB7.setStartDate(startDate);
		taskHB7.getTaskState().changeToOnGoing();

		// Task 3.8 (taskHB8) set to cancelled
		taskHB8.setEstimatedTaskStartDate(estimatedStartDate4);
		taskHB8.setTaskDeadline(estimatedFinishDate4);
		taskHB8.getTaskState().changeToPlanned();
		// necessary to pass from "Planned" to "Assigned"
		taskHB8.addProjectCollaboratorToTask(projcollabManager);
		taskHB8.getTaskState().changeToAssigned();
		// pass from "Assigned" to "Ready"
		taskHB8.getTaskState().changeToReady();
		// necessary to pass from "Ready" to "OnGoing"
		taskHB8.setStartDate(startDate);
		taskHB8.getTaskState().changeToOnGoing();
		// necessary to pass from "OnGoing" to "Cancelled"
		taskHB8.setCancelDate();
		taskHB8.getTaskState().changeToCancelled();

		// Task 3.9 (taskHB9) set to standby
		taskHB9.setEstimatedTaskStartDate(estimatedStartDate4);
		taskHB9.setTaskDeadline(estimatedFinishDate4);
		taskHB9.getTaskState().changeToPlanned();
		// necessary to pass from "Planned" to "Assigned"
		taskHB9.addProjectCollaboratorToTask(projcollabManager);
		taskHB9.getTaskState().changeToAssigned();
		// pass from "Assigned" to "Ready"
		taskHB9.getTaskState().changeToReady();
		// necessary to pass from "Ready" to "OnGoing"
		taskHB9.setStartDate(startDate);
		taskHB9.getTaskState().changeToOnGoing();
		// necessary to pass from "OnGoing" to "StandBy"
		taskHB9.removeAllCollaboratorsFromTaskTeam();
		taskHB9.getTaskState().changeToStandBy();

		// Request assignment of collaborator to task 3.2 (taskHB2)
		projectHomeBanking.createTaskAssignementRequest(projcollabATirapicos, taskHB2);

		// Request of removal of collaborator projcollabATirapicos from task 3.5
		// (taskHB5)
		projectHomeBanking.createTaskRemovalRequest(projcollabATirapicos, taskHB5);

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

		System.out.println();
		System.out.println("");
		System.out.println("");
		System.out.println("          `.----..  ...    ...`   `..` .:-            `.----.`  ...    `..`           ");
		System.out.println("         +dmddddhy  dmd`  +dddd`  /dd/ omd`  :++`    /hdmddddo `dmh    :mm/          ");
		System.out.println("         mNd-.....  sNN-  dN/mN/  yNm. -/: `.sNN/.. /NNh-....` `mNd    /NN+       ");
		System.out.println("         yNmyo:-`   :NN+ -Nm`yNh  mNy  smm`.ydNNhhs hNN:       `mNm++++yNN+        ");
		System.out.println("         `:shdmmds` `mNh oNy /Nm`-NN/  yNN`  oNN-   hNN-       `mNmyyyyhNN+           ");
		System.out.println("            `-sNN+  yNm`dN/ `mN/oNm`  yNN`  oNN-   sNN+        `mNd    /NN+       ");
		System.out.println("        /+++++hNN/  /NNsNm`  hNhhNy   yNN`  oNNs/- -dNms++++/  `mNd    /NN+         ");
		System.out.println("         oyhhhhhs/   `syyy+   -yyyy-   +yy`  `oyhy/  .+yhhhhyo `yys    -yy:           ");
		System.out.println(
				"                                                                                                   ");
		System.out.println("");
		System.out.println("");
	}
}
