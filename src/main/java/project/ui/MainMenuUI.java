package project.ui;

import java.util.Scanner;

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

		// Instantiate a user, set its password and add it to the company
		user1 = myCompany.getUsersRepository().createUser("Aluno do Grupo 3", "aluno_3_@gmail.com", "010", "Estudante", "937653635",
				"Avenida dos Aliados", "4400-654", "Porto", "Porto", "Portugal");
		user1.setPassword("switch");
		myCompany.getUsersRepository().addUserToUserRepository(user1);

		// Instantiates a project and add it to the company
		project1 = myCompany.getProjectsRepository().createProject("Projeto GP", "Aplicação para Gestão de Projetos", user1);
		myCompany.getProjectsRepository().addProjectToProjectRepository(project1);
		
		project2 = myCompany.getProjectsRepository().createProject("Projeto Apostas", "Plataforma Web para Apostas", user1);
		myCompany.getProjectsRepository().addProjectToProjectRepository(project2);
		
		project3 = myCompany.getProjectsRepository().createProject("Projeto HomeBanking", "Aplicação iOS para HomeBanking", user1);
		myCompany.getProjectsRepository().addProjectToProjectRepository(project3);

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
			System.out.println("                 MAIN MENU");
			System.out.println("______________________________________________");
			System.out.println("[1] New Registration");
			System.out.println("[2] Login");
			System.out.println("[3] Administrator");
			System.out.println("[4] Director");
			System.out.println("[5] Collaborator");
			System.out.println("______________________________________________");
			System.out.println("[6] Exit");
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
				
				break;
			case "6":
				System.out.println();
				System.out.println("--YOU HAVE EXIT FROM MAIN MENU--");
				condition = false;
				
				break;
			
			}
		}
	}

}
