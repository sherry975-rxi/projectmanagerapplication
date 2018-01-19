package project.ui;

import java.util.Scanner;

import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;

public class MainMenuUI {

	private static Company myCompany;
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
		myCompany.getUsersRepository().addUserToUserRepository(user1);
		myCompany.getUsersRepository().addUserToUserRepository(user2);
		myCompany.getUsersRepository().addUserToUserRepository(projectManager);

		// set user as collaborator
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
		task2 = project1.getTaskRepository().createTask("Desenvolver código para responder à US122");
		project1.getTaskRepository().addProjectTask(task2);

		mainMenu();
	}

	public static void mainMenu() {

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

			String choice = input.nextLine();
			switch (choice) {
			case "1":
				UserRegisterUI userRegister = new UserRegisterUI();
				userRegister.userRegister();
				break;
			case "2":
				LoginUI doLogin = new LoginUI();
				doLogin.doLogin();
				break;
			case "3":

				break;
			case "4":

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

}
