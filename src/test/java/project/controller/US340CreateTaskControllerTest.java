package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import static org.junit.Assert.*;

public class US340CreateTaskControllerTest {

	Company myCompany;
	UserContainer userContainer;
	User user1;
	User userAdmin;
	Project project;
	ProjectContainer projectContainer;
	Task testTask;

	@Before
	public void setUp() {
		// create company

		myCompany = Company.getTheInstance();

		// creates an UserContainer
		userContainer = myCompany.getUsersContainer();

		// creates a ProjectsRepository
		projectContainer = myCompany.getProjectsContainer();

		userContainer.getAllUsersFromUserContainer().clear();

		// create user
		user1 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		// create user admin
		userAdmin = userContainer.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		// add user to user list
		userContainer.addUserToUserRepository(user1);
		userContainer.addUserToUserRepository(userAdmin);
		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		userAdmin.setUserProfile(Profile.COLLABORATOR);
		// create project
		project = projectContainer.createProject("name3", "description4", userAdmin);// !!!

	}

	@After
	public void tearDown() {
		Company.clear();
		user1 = null;
		testTask = null;
		project = null;
		projectContainer = null;
		userContainer = null;
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
