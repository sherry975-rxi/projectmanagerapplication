package test.newUSTest.project.model;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.project.model.Company;
import main.project.model.Project;
import main.project.model.ProjectCollaborator;
import main.project.model.ProjectRepository;
import main.project.model.Task;
import main.project.model.TaskRepository;
import main.project.model.TaskWorker;
import main.project.model.User;
import main.project.model.UserRepository;

/**
 * @author Group 3
 * 
 *         US380
 * 
 *         Como Gestor de projeto, quero obter uma lista de tarefas não
 *         concluídas e com data de conclusão vencida.
 *
 */
class US380 {

	Company myCompany;
	UserRepository userRepository;
	ProjectRepository projectRepository;
	User user1;
	User user2;
	User user3;
	Project project;
	ProjectCollaborator projectCollaborator;
	ProjectCollaborator projectCollaborator2;
	TaskRepository taskRepository;
	Task testTask;
	Task testTask2;
	Task testTask3;
	TaskWorker taskWorker;
	TaskWorker taskWorker1;
	TaskWorker taskWorker2;

	@BeforeEach
	void setUp() {

		// Creates the Company
		myCompany = Company.getTheInstance();

		// Creates the user repository
		userRepository = myCompany.getUsersRepository();

		// Creates the project repository
		projectRepository = myCompany.getProjectsRepository();

		// Creates three users
		user1 = myCompany.getUsersRepository().createUser("Daniel", "daniel@gmail.com", "001", "collaborator",
				"910000000", "Rua", "2101-00", "Test", "Testo", "Testistan");
		user2 = myCompany.getUsersRepository().createUser("Joao", "joaoo@gmail.com", "001", "collaborator", "920000000",
				"Rua", "2301-00", "Test", "Testo", "Testistan");
		user3 = myCompany.getUsersRepository().createUser("Rita", "rita@gmail.com", "001", "collaborator", "930000000",
				"Rua", "2401-00", "Test", "Testo", "Testistan");

		// Creates a project
		project = myCompany.getProjectsRepository().createProject("Project A", "Project AA", user1);

		// Creates Project collaborators
		projectCollaborator = project.addUserToProjectTeam(user2);

	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
