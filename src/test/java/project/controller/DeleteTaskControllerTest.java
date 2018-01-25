package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.User;
import project.model.UserRepository;
import project.model.taskStateInterface.Assigned;
import project.model.taskStateInterface.Cancelled;
import project.model.taskStateInterface.Finished;
import project.model.taskStateInterface.OnGoing;
import project.model.taskStateInterface.Planned;
import project.model.taskStateInterface.Ready;
import project.model.taskStateInterface.StandBy;

public class DeleteTaskControllerTest {

	Company myCompany;
	UserRepository userRepository;
	User user1;
	User userAdmin;
	Project project;
	ProjectRepository projectRepository;
	Task testTask, testTask2, testTask3, testTask4, testTask5, testTask6, testTask7;
	Assigned taskStateAssigned;
	Cancelled taskStateCancelled;
	Finished taskStateFinished;
	OnGoing taskStateOnGoing;
	Planned taskStatePlanned;
	Ready taskStateReady;
	StandBy taskStateStandBy;
	US340CreateTaskController testControl;

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
		projectRepository.addProjectToProjectRepository(project);

	}

	@After
	public void tearDown() {
		Company.clear();
		user1 = null;
		testTask = null;
		testTask2 = null;
		project = null;
		projectRepository = null;
		userRepository = null;
		testControl = null;
	}

	@Test
	public void testDeleteTaskSetToCreatedStateControllerTest() {
		// asserts which user is the Project Manager
		assertFalse(project.isProjectManager(user1));
		assertTrue(project.isProjectManager(userAdmin));

		// creates and adds a task using the controller and asserts a task was added
		testTask = project.getTaskRepository().createTask("Test dis agen pls");
		project.getTaskRepository().addProjectTask(testTask);

		taskStateAssigned = new Assigned(testTask6);

		// Creates an int that olds the projectID Code
		int projectCode = project.getIdCode();

		// Creates a string that holds the value of tasks id
		String taskId = testTask.getTaskID();

		// creates the Controller
		DeleteTaskController controllerDelete = new DeleteTaskController(projectCode);

		// Verifies that the testTask state is set to "Created"
		assertEquals(testTask.viewTaskStateName(), "Created");

		assertEquals(project.getTaskRepository().getTaskByID(taskId), testTask);

		// Checks if the task is successfully deleted
		assertTrue(controllerDelete.deleteTask(projectCode, taskId));

	}

	@Test
	public void testDeleteTaskSetToPlannedStateControllerTest() {

		// asserts which user is the Project Manager
		assertFalse(project.isProjectManager(user1));
		assertTrue(project.isProjectManager(userAdmin));

		// creates and adds a task using the controller and asserts a task was added
		testTask = project.getTaskRepository().createTask("Test dis agen pls");
		project.getTaskRepository().addProjectTask(testTask);

		taskStatePlanned = new Planned(testTask);

		// Creates an int that holds the projectID Code
		int projectCode = project.getIdCode();

		// Creates a string that holds the value of tasks id
		String taskId = testTask.getTaskID();

		// creates the Controller
		DeleteTaskController controllerDelete = new DeleteTaskController(projectCode);

		testTask.setTaskState(taskStatePlanned);

		// Verifies that the testTask state is set to "Planned"
		assertEquals(testTask.viewTaskStateName(), "Planned");

		assertEquals(project.getTaskRepository().getTaskByID(taskId), testTask);

		// Checks if the task is successfully deleted
		assertTrue(controllerDelete.deleteTask(projectCode, taskId));

	}

	@Test
	public void testDeleteTaskSetToAssignedStateControllerTest() {

		// asserts which user is the Project Manager
		assertFalse(project.isProjectManager(user1));
		assertTrue(project.isProjectManager(userAdmin));

		// creates and adds a task using the controller and asserts a task was added
		testTask = project.getTaskRepository().createTask("Test dis agen pls");
		project.getTaskRepository().addProjectTask(testTask);

		taskStateAssigned = new Assigned(testTask);

		// Creates an int that holds the projectID Code
		int projectCode = project.getIdCode();

		// Creates a string that holds the value of tasks id
		String taskId = testTask.getTaskID();

		// creates the Controller
		DeleteTaskController controllerDelete = new DeleteTaskController(projectCode);

		testTask.setTaskState(taskStateAssigned);

		// Verifies that the testTask state is set to "Assigned"
		assertEquals(testTask.viewTaskStateName(), "Assigned");

		assertEquals(project.getTaskRepository().getTaskByID(taskId), testTask);

		// Checks if the task is successfully deleted
		assertTrue(controllerDelete.deleteTask(projectCode, taskId));

	}

	@Test
	public void testDeleteTaskSetToCancelledStateControllerTest() {

		// asserts which user is the Project Manager
		assertFalse(project.isProjectManager(user1));
		assertTrue(project.isProjectManager(userAdmin));

		// creates and adds a task using the controller and asserts a task was added
		testTask = project.getTaskRepository().createTask("Test dis agen pls");
		project.getTaskRepository().addProjectTask(testTask);

		taskStateCancelled = new Cancelled(testTask);

		// Creates an int that holds the projectID Code
		int projectCode = project.getIdCode();

		// Creates a string that holds the value of tasks id
		String taskId = testTask.getTaskID();

		// creates the Controller
		DeleteTaskController controllerDelete = new DeleteTaskController(projectCode);

		testTask.setTaskState(taskStateCancelled);

		// Verifies that the testTask state is set to "Cancelled"
		assertEquals(testTask.viewTaskStateName(), "Cancelled");

		assertEquals(project.getTaskRepository().getTaskByID(taskId), testTask);

		// Checks if the task wasn't deleted
		assertFalse(controllerDelete.deleteTask(projectCode, taskId));

	}

	@Test
	public void testDeleteTaskSetToFinishedStateControllerTest() {

		// asserts which user is the Project Manager
		assertFalse(project.isProjectManager(user1));
		assertTrue(project.isProjectManager(userAdmin));

		// creates and adds a task using the controller and asserts a task was added
		testTask = project.getTaskRepository().createTask("Test dis agen pls");
		project.getTaskRepository().addProjectTask(testTask);

		taskStateFinished = new Finished(testTask);

		// Creates an int that holds the projectID Code
		int projectCode = project.getIdCode();

		// Creates a string that holds the value of tasks id
		String taskId = testTask.getTaskID();

		// creates the Controller
		DeleteTaskController controllerDelete = new DeleteTaskController(projectCode);

		testTask.setTaskState(taskStateFinished);

		// Verifies that the testTask state is set to "Finished"
		assertEquals(testTask.viewTaskStateName(), "Finished");

		assertEquals(project.getTaskRepository().getTaskByID(taskId), testTask);

		// Checks if the task wasn't deleted
		assertFalse(controllerDelete.deleteTask(projectCode, taskId));

	}

	@Test
	public void testDeleteTaskSetToOnGoingStateControllerTest() {

		// asserts which user is the Project Manager
		assertFalse(project.isProjectManager(user1));
		assertTrue(project.isProjectManager(userAdmin));

		// creates and adds a task using the controller and asserts a task was added
		testTask = project.getTaskRepository().createTask("Test dis agen pls");
		project.getTaskRepository().addProjectTask(testTask);

		taskStateOnGoing = new OnGoing(testTask);

		// Creates an int that holds the projectID Code
		int projectCode = project.getIdCode();

		// Creates a string that holds the value of tasks id
		String taskId = testTask.getTaskID();

		// creates the Controller
		DeleteTaskController controllerDelete = new DeleteTaskController(projectCode);

		testTask.setTaskState(taskStateOnGoing);

		// Verifies that the testTask state is set to "OnGoing"
		assertEquals(testTask.viewTaskStateName(), "OnGoing");

		assertEquals(project.getTaskRepository().getTaskByID(taskId), testTask);

		// Checks if the task wasn't deleted
		assertFalse(controllerDelete.deleteTask(projectCode, taskId));

	}

	@Test
	public void testDeleteTaskSetToReadyControllerTest() {

		// asserts which user is the Project Manager
		assertFalse(project.isProjectManager(user1));
		assertTrue(project.isProjectManager(userAdmin));

		// creates and adds a task using the controller and asserts a task was added
		testTask = project.getTaskRepository().createTask("Test dis agen pls");
		project.getTaskRepository().addProjectTask(testTask);

		taskStateReady = new Ready(testTask);

		// Creates an int that holds the projectID Code
		int projectCode = project.getIdCode();

		// Creates a string that holds the value of tasks id
		String taskId = testTask.getTaskID();

		// creates the Controller
		DeleteTaskController controllerDelete = new DeleteTaskController(projectCode);

		testTask.setTaskState(taskStateReady);

		// Verifies that the testTask state is set to "OnGoing"
		assertEquals(testTask.viewTaskStateName(), "Ready");

		assertEquals(project.getTaskRepository().getTaskByID(taskId), testTask);

		// Checks if the task was deleted
		assertTrue(controllerDelete.deleteTask(projectCode, taskId));

	}

	@Test
	public void testDeleteTaskSetToStandByControllerTest() {

		// asserts which user is the Project Manager
		assertFalse(project.isProjectManager(user1));
		assertTrue(project.isProjectManager(userAdmin));

		// creates and adds a task using the controller and asserts a task was added
		testTask = project.getTaskRepository().createTask("Test dis agen pls");
		project.getTaskRepository().addProjectTask(testTask);

		taskStateStandBy = new StandBy(testTask);

		// Creates an int that holds the projectID Code
		int projectCode = project.getIdCode();

		// Creates a string that holds the value of tasks id
		String taskId = testTask.getTaskID();

		// creates the Controller
		DeleteTaskController controllerDelete = new DeleteTaskController(projectCode);

		testTask.setTaskState(taskStateStandBy);

		// Verifies that the testTask state is set to "StandBy"
		assertEquals(testTask.viewTaskStateName(), "StandBy");

		assertEquals(project.getTaskRepository().getTaskByID(taskId), testTask);

		// Checks if the task wasn't deleted
		assertFalse(controllerDelete.deleteTask(projectCode, taskId));

	}

	@Test
	public void testDeleteNullTaskControllerTest() {

		// asserts which user is the Project Manager
		assertFalse(project.isProjectManager(user1));
		assertTrue(project.isProjectManager(userAdmin));

		// creates and adds a task using the controller and asserts a task was added
		testTask = project.getTaskRepository().createTask("Test dis agen pls");
		project.getTaskRepository().addProjectTask(testTask);

		taskStateReady = new Ready(testTask);

		// Creates an int that holds the projectID Code
		int projectCode = project.getIdCode();

		// Creates a string that holds the value of tasks id
		String taskId = testTask.getTaskID();

		// creates the Controller
		DeleteTaskController controllerDelete = new DeleteTaskController(projectCode);

		testTask.setTaskState(taskStateReady);

		// Verifies that the testTask state is set to "OnGoing"
		assertEquals(testTask.viewTaskStateName(), "Ready");

		assertEquals(project.getTaskRepository().getTaskByID(taskId), testTask);

		// Checks if the task was deleted
		assertTrue(controllerDelete.deleteTask(projectCode, taskId));

		/*
		 * Checks if the task was deleted. Task doesn't exist anymore in the repository,
		 * so it wasn't deleted
		 */
		assertFalse(controllerDelete.deleteTask(projectCode, taskId));

	}
}
