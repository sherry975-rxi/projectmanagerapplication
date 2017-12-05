package test.java.project.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Profile;
import main.java.project.model.Project;
import main.java.project.model.ProjectRepository;
import main.java.project.model.Task;
import main.java.project.model.User;

class TaskRepositoryTests {

	Company myCompany;
	User user1;
	User userAdmin;
	Project project;
	ProjectRepository projectRepository;
	Task testTask;
	Task testTask2;
	Task testTask3;
	Task testTask4;

	@BeforeEach
	void setUp() {
		// create company
		myCompany = Company.getTheInstance();
		myCompany.getUsersList().clear();
		// create user
		user1 = myCompany.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		// create user admin
		userAdmin = myCompany.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		// add user to user list
		myCompany.addUserToUserList(user1);
		myCompany.addUserToUserList(userAdmin);
		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		userAdmin.setUserProfile(Profile.COLLABORATOR);
		// create project
		project = myCompany.getProjectRepository.createProject("name3", "description4", userAdmin);// !!!
		// create 4 tasks
		testTask = myCompany.getProjectRepository.getProject(project).getTaskRepository.createTask("Test dis agen pls");
		testTask2 = project.createTask("Test dis agen pls");
		testTask3 = project.createTask("Test moar yeh");
		testTask4 = project.createTask("TEST HARDER!");

	}

	@AfterEach
	void tearDown() {
		myCompany = null;
		user1 = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
		testTask4 = null;
		project = null;
		projectRepository = null;
	}

	@Test
	void testTaskRepository() {

	}

	@Test
	void testCreateTask() {

	}

	@Test
	void testAddProjectTasks() {

	}

	@Test
	void testGetProjectTaskList() {

	}

	@Test
	void testUnFinishedTasks() {

	}

	@Test
	void testFinishedTaskListOfUserInProject() {

	}

	@Test
	void testGetFinishedTaskGivenMonth() {

	}

	@Test
	void testContainsTask() {

	}

	@Test
	void testGetTimeOnLastMonthProjectUserTask() {

	}

	@Test
	void testSetTaskCounter() {

	}

	@Test
	void testGetTaskCounter() {

	}

	@Test
	void testProjectId() {

	}

	@Test
	void testAllTasks() {

	}

}
