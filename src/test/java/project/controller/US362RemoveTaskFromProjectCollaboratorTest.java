package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class US362RemoveTaskFromProjectCollaboratorTest {

	Company myCompany;

	User user1;
	User userAdmin;

	TaskContainer taskContainer;

	TaskCollaborator taskWorker1;

	ProjectCollaborator collab1;

	Project project;
	Project project2;

	Task testTask;
	Task testTask2;
	Task testTask3;

	Calendar estimatedTaskStartDateTest;
	Calendar taskDeadlineDateTest;
	Calendar startDateTest;
	US362RemoveTaskFromProjectCollaborator controller;

	@Before
	public void setUp() {
		// create company
		myCompany = Company.getTheInstance();

		// create user
		user1 = myCompany.getUsersContainer().createUser("Daniel", "daniel@gmail.com", "001", "collaborator",
				"910000000", "Rua", "2401-00", "Test", "Testo", "Testistan");

		// create user admin
		userAdmin = myCompany.getUsersContainer().createUser("João", "joao@gmail.com", "001", "Admin", "920000000",
				"Rua", "2401-00", "Test", "Testo", "Testistan");

		// add user to user list
		myCompany.getUsersContainer().addUserToUserRepository(user1);
		myCompany.getUsersContainer().addUserToUserRepository(userAdmin);

		// Creates one Project
		project = myCompany.getProjectsContainer().createProject("name3", "description4", userAdmin);
		project2 = myCompany.getProjectsContainer().createProject("name1", "description4", userAdmin);

		// add project to project repository
		myCompany.getProjectsContainer().addProjectToProjectContainer(project);
		myCompany.getProjectsContainer().addProjectToProjectContainer(project2);

		// create project collaborators
		collab1 = new ProjectCollaborator(user1, 2);

		// create taskContainer

		taskContainer = project.getTaskRepository();

		// create 4 tasks
		testTask = taskContainer.createTask("Test dis agen pls");
		testTask2 = taskContainer.createTask("Test dis agen pls");
		testTask3 = taskContainer.createTask("Test moar yeh");

		// Adds 5 tasks to the TaskContainer
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);

		// create task workers
		taskWorker1 = new TaskCollaborator(collab1);

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);

		userAdmin.setUserProfile(Profile.COLLABORATOR);

		// add user to project team
		project.addProjectCollaboratorToProjectTeam(collab1);
		project2.addProjectCollaboratorToProjectTeam(collab1);

		// adds user to task
		testTask.addProjectCollaboratorToTask(collab1);

		// create a estimated Task Start Date
		Calendar startDateTest = Calendar.getInstance();

		// create a estimated Task Start Date
		Calendar estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 25);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a estimated Task Dead line Date
		Calendar taskDeadlineDateTest = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.JANUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a expired estimated Task Dead line Date
		Calendar taskExpiredDeadlineDateTest = Calendar.getInstance();
		taskExpiredDeadlineDateTest.set(Calendar.YEAR, 2017);
		taskExpiredDeadlineDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		taskExpiredDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskExpiredDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// Instantiates the controller
		controller = new US362RemoveTaskFromProjectCollaborator(project, testTask);

	}

	@After
	public void tearDown() {
		Company.clear();
		user1 = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
		project = null;
		taskContainer = null;
		taskWorker1 = null;
		collab1 = null;
		estimatedTaskStartDateTest = null;
		taskDeadlineDateTest = null;
		startDateTest = null;
	}

	/**
	 * Tests the getProjectCollaboratorsFromTask method
	 */
	@Test
	public void testGetProjectCollaboratorsFromTask() {

		String info = "Name: " + user1.getName() + "\n" + "Email: " + user1.getEmail() + "\n" + "Function: "
				+ user1.getFunction();

		List<String> expResult = new ArrayList<>();
		expResult.add(info);

		assertEquals(expResult, controller.getProjectCollaboratorsFromTask());
	}

	/**
	 * Tests the getProjectCollaborator and setProjectCollaborator methods
	 */
	@Test
	public void testSetandGetProjectCollaborator() {

		// Asserts that the project collaborator is null
		assertEquals(controller.getProjectCollaborator(), null);

		// sets the projectCollaborator
		controller.setProjectCollaborator(0);

		// Asserts that the project collaborator is not equal to collab1
		assertEquals(controller.getProjectCollaborator(), collab1);
	}

	@Test
	public void getRemoveProjectCollaboratorFromTask() {

		TaskCollaborator taskCollaborator = testTask.getTaskTeam().get(0);

		// Asserts that the task collaborator is active in the task
		assertTrue(taskCollaborator.isTaskCollaboratorActiveInTask());

		// Removes collaborator from the task
		controller.setProjectCollaborator(0);
		controller.removeCollaboratorFromTask();

		// Asserts that the task collaborator is not active in task
		assertFalse(taskCollaborator.isTaskCollaboratorActiveInTask());

	}

}
