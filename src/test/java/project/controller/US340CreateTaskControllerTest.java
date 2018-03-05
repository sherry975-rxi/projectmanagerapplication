package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import static org.junit.Assert.*;

public class US340CreateTaskControllerTest {

	Company myCompany;
	UserRepository userRepository;
	User user1;
	User userAdmin;
	Project project;
	ProjectRepository projectRepository;
	Task testTask;

	@Before
	public void setUp() {
		// create company

		myCompany = Company.getTheInstance();

		// creates an UserRepository
		userRepository = myCompany.getUsersRepository();

		// creates a ProjectsRepository
		projectRepository = myCompany.getProjectsRepository();

		userRepository.getAllUsersFromRepository().clear();

		// create user
		user1 = userRepository.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		// create user admin
		userAdmin = userRepository.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		// add user to user list
		userRepository.addUserToUserRepository(user1);
		userRepository.addUserToUserRepository(userAdmin);
		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		userAdmin.setUserProfile(Profile.COLLABORATOR);
		// create project
		project = projectRepository.createProject("name3", "description4", userAdmin);// !!!

	}

	@After
	public void tearDown() {
		Company.clear();
		user1 = null;
		testTask = null;
		project = null;
		projectRepository = null;
		userRepository = null;
	}

	@Test
	public void testTaskController() {
		// asserts which user is the Project Manager
		assertFalse(project.isProjectManager(user1));
		assertTrue(project.isProjectManager(userAdmin));

		// creates the Controller and asserts the list of unstarted tasks starts at 0
		US340CreateTaskController testControl = new US340CreateTaskController(project);
		assertEquals(project.getTaskRepository().getUnstartedTasks().size(), 0);

		// creates and adds a task using the controller and asserts a task was added
		testTask = testControl.addTask("Test dis agen pls");
		assertEquals(project.getTaskRepository().getUnstartedTasks().size(), 1);

		// asserts the added task matches the added task
		assertTrue(project.getTaskRepository().getUnstartedTasks().get(0).getDescription().equals("Test dis agen pls"));
		assertFalse(project.getTaskRepository().getUnstartedTasks().get(0).getDescription().equals(""));

		// Tests the get Task Repository
		assertEquals(testControl.getTaskRepository(), project.getTaskRepository());
	}

}
