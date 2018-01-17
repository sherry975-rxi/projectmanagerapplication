package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

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
		testTask2 = null;
		project = null;
		projectRepository = null;
		userRepository = null;
	}

	@Test
	public void testDeleteTaskController() {
		// asserts which user is the Project Manager
		assertFalse(project.isProjectManager(user1));
		assertTrue(project.isProjectManager(userAdmin));

		// creates the Controller and asserts the list of unstrarted tasks starts at 0
		CreateTaskController testControl = new CreateTaskController(project);

		// creates and adds a task using the controller and asserts a task was added
		testTask = testControl.addTask("Test dis agen pls", 10, Calendar.getInstance(), Calendar.getInstance(), 10);
		testTask2 = testControl.addTask("Test dis agen pls", 10, Calendar.getInstance(), Calendar.getInstance(), 10);
		testTask3 = testControl.addTask("Test dis agen pls", 10, Calendar.getInstance(), Calendar.getInstance(), 10);
		testTask4 = testControl.addTask("Test dis agen pls", 10, Calendar.getInstance(), Calendar.getInstance(), 10);
		testTask5 = testControl.addTask("Test dis agen pls", 10, Calendar.getInstance(), Calendar.getInstance(), 10);
		testTask6 = testControl.addTask("Test dis agen pls", 10, Calendar.getInstance(), Calendar.getInstance(), 10);

		// creates different TaskStates variables
		taskStateStandBy = new StandBy(testTask3);
		taskStateReady = new Ready(testTask4);
		taskStateCancelled = new Cancelled(testTask5);
		taskStateFinished = new Finished(testTask5);
		taskStateOnGoing = new OnGoing(testTask5);
		taskStatePlanned = new Planned(testTask5);
		taskStateAssigned = new Assigned(testTask6);

		// Creates an int that olds the projectID Code
		int projectCode = project.getIdCode();

		// Creates a string that holds the value of tasks id
		String taskId = testTask.getTaskID();
		String taskId2 = testTask2.getTaskID();
		String taskId3 = testTask3.getTaskID();
		String taskId4 = testTask4.getTaskID();
		String taskId5 = testTask5.getTaskID();
		String taskId6 = testTask6.getTaskID();

		// creates the Controller and asserts the list of unstrarted tasks starts at 0
		DeleteTaskController controllerDelete = new DeleteTaskController(project);

		// Verifies that the testTask state is set to "Created"
		assertEquals(testTask.viewTaskStateName(), "Created");

		assertEquals(project.getTaskRepository().getTaskByID(taskId), testTask);

		// Checks if the task is successfully deleted
		assertTrue(controllerDelete.deleteTask(projectCode, taskId));

		// Deleted the task from the task repository
		assertEquals(project.getTaskRepository().getTaskByID(taskId), null);

		testTask3.setTaskState(taskStateStandBy);

		// Checks if the task is successfully deleted
		assertFalse(controllerDelete.deleteTask(projectCode, taskId3));

		/*
		 * Sets the state of the task4 to state "Ready". Task can be deleted
		 */
		testTask4.setTaskState(taskStateReady);

		// Verifies that the testTask state is set to "Ready"
		assertEquals(testTask4.viewTaskStateName(), "Ready");

		// Checks if the task is successfully deleted
		assertTrue(controllerDelete.deleteTask(projectCode, taskId4));

		testTask5.setTaskState(taskStateCancelled);

		// Verifies that the testTask state is set to "Cancelled"
		assertEquals(testTask5.viewTaskStateName(), "Cancelled");

		/*
		 * Checks if the task is deleted. It can't, because it's state is set to
		 * "Cancelled"
		 */
		assertFalse(controllerDelete.deleteTask(projectCode, taskId5));

		/*
		 * Sets the TaskState to finished
		 */
		testTask5.setTaskState(taskStateFinished);

		// Verifies that the testTask state is set to "Finished"
		assertEquals(testTask5.viewTaskStateName(), "Finished");

		/*
		 * Checks if the task is deleted. It can't, because it's state is set to
		 * "Finished"
		 */
		assertFalse(controllerDelete.deleteTask(projectCode, taskId5));

		/*
		 * Sets the TaskState to "OnGoing"
		 */
		testTask5.setTaskState(taskStateOnGoing);

		// Verifies that the testTask state is set to "OnGoing"
		assertEquals(testTask5.viewTaskStateName(), "OnGoing");

		/*
		 * Checks if the task is deleted. It can't, because it's state is set to
		 * "OnGoing"
		 */
		assertFalse(controllerDelete.deleteTask(projectCode, taskId5));

		/*
		 * Sets the TaskState to Planned
		 */
		testTask5.setTaskState(taskStatePlanned);

		/*
		 * Checks if the task is deleted.
		 */
		assertTrue(controllerDelete.deleteTask(projectCode, taskId5));

		/*
		 * Sets the TaskState to Assigned
		 */
		testTask6.setTaskState(taskStateAssigned);

		// Verifies that the testTask state is set to "Assigned"
		assertEquals(testTask6.viewTaskStateName(), "Assigned");

		/*
		 * Checks if the task is deleted.
		 */
		assertTrue(controllerDelete.deleteTask(projectCode, taskId6));

		/*
		 * The task doesn't exist in the repository anymore, so it will return false
		 */
		assertFalse(controllerDelete.deleteTask(projectCode, taskId6));
	}

}
