package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import project.Repository.ProjectsRepository;
import project.Repository.TaskRepository;
import project.Repository.UserRepository;
import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.Profile;
import project.model.Project;
import project.model.Task;
import project.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class US340CreateTaskControllerTest {

	UserService userService;
	TaskService taskService;
	User user1;
	User userAdmin;
	Project project;
	ProjectService projectService;
	Task testTask;
	US340CreateTaskController testControl;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectsRepository projectRepository;

	@Before
	public void setUp() {
		// creates an UserContainer
		userService = new UserService();
		userService.setUserRepository(userRepository);

		projectService = new ProjectService();
		projectService.setProjectsRepository(projectRepository);

		taskService = new TaskService();
		taskService.setTaskRepository(taskRepository);

		userService.getAllUsersFromUserContainer().clear();

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

		// creates the Controller and asserts the list of unstarted tasks starts at 0
		testControl = new US340CreateTaskController(project);

		/*
		 * Sets a TaskService to the Controller
		 */
		testControl.taskService = taskService;

	}

	@Test
	public void testTaskController() {
		// asserts which user is the Project Manager
		assertFalse(project.isProjectManager(user1));
		assertTrue(project.isProjectManager(userAdmin));

		assertEquals(taskService.getProjectUnstartedTasks(project).size(), 0);

		// creates and adds a task using the controller and asserts a task was added
		assertTrue(testControl.addTask("Test dis agen pls"));
		assertEquals(taskService.getProjectUnstartedTasks(project).size(), 1);

		// asserts the added task matches the added task
		assertTrue(taskService.getProjectUnstartedTasks(project).get(0).getDescription().equals("Test dis agen pls"));

		assertEquals(this.taskService, testControl.getTaskService());

	}

}
