package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;
import project.model.Profile;
import project.model.Project;
import project.model.Task;
import project.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controller" })

public class US340CreateTaskControllerTest {

	@Autowired
	UserService userService;
	@Autowired
	TaskService taskService;
	User user1;
	User userAdmin;
	Project project;
	@Autowired
	ProjectService projectService;
	Task testTask;
	@Autowired
	US340CreateTaskController testControl;

	@Before
	public void setUp() {


		// create user
		user1 = userService.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		// create user admin
		userAdmin = userService.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		userAdmin.setUserProfile(Profile.COLLABORATOR);
		// create project
		project = projectService.createProject("name3", "description4", userAdmin);// !!!

	}

	@After
	public void clear() {

		user1 = null;
		userAdmin = null;
		project = null;
		testTask = null;

	}

	@Test
	public void testTaskController() {
		// asserts which user is the Project Manager
		assertFalse(project.isProjectManager(user1));
		assertTrue(project.isProjectManager(userAdmin));

		assertEquals(taskService.getProjectUnstartedTasks(project).size(), 0);

		// creates and adds a task using the controller and asserts a task was added
		assertTrue(testControl.addTask("Test dis agen pls", project));
		assertEquals(taskService.getProjectUnstartedTasks(project).size(), 1);

		// asserts the added task matches the added task
		assertTrue(taskService.getProjectUnstartedTasks(project).get(0).getDescription().equals("Test dis agen pls"));

		assertEquals(taskService, testControl.getTaskService());

	}

}
