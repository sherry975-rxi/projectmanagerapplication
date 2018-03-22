package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controller" })
public class US362RemoveTaskFromProjectCollaboratorTest {

	@Autowired
	UserService userContainer;
	@Autowired
	ProjectService projectContainer;
	@Autowired
	TaskService taskContainer;
	@Autowired
	US362RemoveTaskFromProjectCollaborator controller;

	User user1;
	User userAdmin;

	TaskCollaborator taskWorker1;

	ProjectCollaborator collab1;
	ProjectCollaborator collab2;

	Project project;
	Project project2;

	Task testTask;
	Task testTask2;
	Task testTask3;

	Calendar estimatedTaskStartDateTest;
	Calendar taskDeadlineDateTest;
	Calendar startDateTest;

	@Before
	public void setUp() {

		// create user
		user1 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// create user admin
		userAdmin = userContainer.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");

		// add user to user list
		userContainer.addUserToUserRepositoryX(user1);
		userContainer.addUserToUserRepositoryX(userAdmin);

		// Creates one Project
		project = projectContainer.createProject("name3", "description4", userAdmin);
		project2 = projectContainer.createProject("name1", "description4", userAdmin);

		// add project to project repository
		projectContainer.addProjectToProjectContainer(project);
		projectContainer.addProjectToProjectContainer(project2);

		// create project collaborators
		collab1 = projectContainer.createProjectCollaborator(user1, project, 0);
		projectContainer.addProjectCollaborator(collab1);

		// create 3 tasks
		testTask = taskContainer.createTask("Test dis agen pls", project);
		testTask2 = taskContainer.createTask("Test dis agen pls", project);
		testTask3 = taskContainer.createTask("Test moar yeh", project);

		// create task workers
		testTask.addProjectCollaboratorToTask(collab1);
		testTask2.addProjectCollaboratorToTask(collab1);
		testTask3.addProjectCollaboratorToTask(collab1);

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);

		userAdmin.setUserProfile(Profile.COLLABORATOR);

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

		controller.setProject(project);
		controller.setTask(testTask);

	}

	@After
	public void tearDown() {
		user1 = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
		project = null;
		taskWorker1 = null;
		collab1 = null;
		collab2 = null;
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

		controller.setProjectCollaborator(collab2);
		// Asserts that the project collaborator is null
		assertEquals(controller.getProjectCollaborator(), null);

		// sets the projectCollaborator
		controller.setProjectCollaborator(collab1);

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
