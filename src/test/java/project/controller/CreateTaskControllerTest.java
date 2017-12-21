package project.controller;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskRepository;
import project.model.User;
import project.model.UserRepository;

public class CreateTaskControllerTest {

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

		// creates a ProjectRepository
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
	public void test340Controller() {
		// asserts which user is the Project Manager
		assertFalse(project.isProjectManager(user1));
		assertTrue(project.isProjectManager(userAdmin));
		
		// creates the Controller and asserts the list of unstrarted tasks starts at 0
		CreateTaskController testControl = new CreateTaskController(project.getTaskRepository());
		assertEquals(project.getTaskRepository().getUnstartedTasks().size(), 0);
		
		// creates and adds a task using the controller and asserts a task was added
		testTask = testControl.addTask("Test dis agen pls", 10, Calendar.getInstance(), Calendar.getInstance(),
		10);
		assertEquals(project.getTaskRepository().getUnstartedTasks().size(), 1);
		
		// asserts the added task matches the added task
		assertTrue(project.getTaskRepository().getUnstartedTasks().get(0).getDescription().equals("Test dis agen pls"));
		assertFalse(project.getTaskRepository().getUnstartedTasks().get(0).getDescription().equals(""));
	}

}
