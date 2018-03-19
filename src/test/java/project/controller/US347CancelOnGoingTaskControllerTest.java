/**
 * 
 */
package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Services.ProjectService;
import project.Services.UserService;
import project.model.*;
import project.model.taskstateinterface.*;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the methods that are called in Controller to execute the
 * action of Canceling an OnGoing Task
 * 
 * @author Group3
 *
 */
public class US347CancelOnGoingTaskControllerTest {

	// TasksFiltersController tasksFiltersController;
	UserService userContainer;
	ProjectService projectContainer;
	User user1, user2, projectManager;
	Project project1;
	ProjectCollaborator projCollab1, projCollab2;
	Task task1, task2, task3;
	TaskCollaborator taskCollab1, taskCollab2;
	Calendar startDateTest;
	Calendar estimatedTaskStartDateTest;
	Calendar taskDeadlineDateTest;
	Calendar taskExpiredDeadlineDateTest;

	@Before
	public void setUp() {

		// creates an UserContainer
		userContainer = new UserService();

		// create users
		user1 = userContainer.createUser("Joe Smith", "jsmith@gmail.com", "001", "Junior Programmer",
				"930000000", "Rua da Caparica, 19", "7894-654", "Porto", "Porto", "Portugal");
		user2 = userContainer.createUser("John Smith", "johnsmith@gmail.com", "001", "General Manager",
				"930025000", "Rua Doutor Armando", "4455-654", "Rio Tinto", "Gondomar", "Portugal");
		projectManager = userContainer.createUser("Mary MacJohn", "mmacjohn@gmail.com", "003",
				"Product Manager", "930025000", "Rua Terceira, 44", "4455-122", "Leça da Palmeira", "Matosinhos",
				"Portugal");

		// add users to company
		userContainer.addUserToUserRepository(user1);
		userContainer.addUserToUserRepository(user2);
		userContainer.addUserToUserRepository(projectManager);

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);
		projectManager.setUserProfile(Profile.COLLABORATOR);
		
		// creates a Project Container
		projectContainer = new ProjectService();

		// create project and establishes collaborator projectManager as project manager
		// of project 1
		project1 = projectContainer.createProject("Project Management software",
				"This software main goals are ....", projectManager);

		// add project to company
		projectContainer.addProjectToProjectContainer(project1);

		// create project collaborators
		projCollab1 = new ProjectCollaborator(user1, 2);
		projCollab2 = new ProjectCollaborator(user2, 2);

		// add collaborators to Project
		project1.addProjectCollaboratorToProjectTeam(projCollab1);
		project1.addProjectCollaboratorToProjectTeam(projCollab2);

		// create tasks
		task1 = project1.getTaskRepository().createTask("Create class User");
		task2 = project1.getTaskRepository().createTask("Create class User");
		task3 = project1.getTaskRepository().createTask("create test for method set name in class user");

		// add tasks to task repository
		project1.getTaskRepository().addTaskToProject(task1);
		project1.getTaskRepository().addTaskToProject(task2);
		project1.getTaskRepository().addTaskToProject(task3);

		// create a estimated Task Start Date
		startDateTest = Calendar.getInstance();

		// create a estimated Task Start Date
		estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 25);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a estimated Task Dead line Date
		taskDeadlineDateTest = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.JANUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a expired estimated Task Dead line Date
		taskExpiredDeadlineDateTest = Calendar.getInstance();
		taskExpiredDeadlineDateTest.set(Calendar.YEAR, 2017);
		taskExpiredDeadlineDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		taskExpiredDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskExpiredDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// Creates State Objects planned for task.
		task1.setTaskState(new Planned());
        task2.setTaskState(new Planned());
        task3.setTaskState(new Planned());


		// set estimated task start date and task dead line to tasks
		task1.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task1.setTaskDeadline(taskDeadlineDateTest);

		task2.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task2.setTaskDeadline(taskDeadlineDateTest);

		task3.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task3.setTaskDeadline(taskDeadlineDateTest);

		// Sets the tasks to "Planned"
		task1.setTaskState(new Planned());
		task2.setTaskState(new Planned());
		task3.setTaskState(new Planned());

		// create task workers
		taskCollab1 = new TaskCollaborator(projCollab1);
		taskCollab2 = new TaskCollaborator(projCollab2);

		// set active user
		task1.addTaskCollaboratorToTask(taskCollab1);
		task2.addTaskCollaboratorToTask(taskCollab2);
		task3.addTaskCollaboratorToTask(taskCollab1);

        // Sets the tasks to "Ready"
        task1.setTaskState(new Ready());
        task2.setTaskState(new Ready());
        task3.setTaskState(new Ready());

		// Sets the tasks to "onGoing"
		task1.setTaskState(new OnGoing());
		task2.setTaskState(new OnGoing());
		task3.setTaskState(new OnGoing());
	}

	@After
	public void tearDown() {
		projectContainer = null;
		userContainer = null;
		user1 = null;
		user2 = null;
		projectManager = null;
		project1 = null;
		projCollab1 = null;
		projCollab2 = null;
		task1 = null;
		task2 = null;
		task3 = null;
		taskCollab1 = null;
		taskCollab2 = null;
		startDateTest = null;
		estimatedTaskStartDateTest = null;
		taskDeadlineDateTest = null;
		taskExpiredDeadlineDateTest = null;
	}

	/**
	 * Test method that views the task state and returns this information in a
	 * string format
	 */
	@Test
	public void testViewTaskState() {
		// create controller
		US347CancelOnGoingTaskController uS347CancelOnGoingTaskController = new US347CancelOnGoingTaskController(
				task1.getTaskID(), project1);

		assertEquals("OnGoing", uS347CancelOnGoingTaskController.viewTaskState());
	}

	/**
	 * This test verifies if an task in state OnGoing is changed to Cancelled using
	 * the controller.
	 */
	@Test
	public void testCancelOnGoingTask() {

		// create controller
		US347CancelOnGoingTaskController uS347CancelOnGoingTaskController = new US347CancelOnGoingTaskController(
				task1.getTaskID(), project1);

		// Sets task2 to "cancelled"
		task2.setTaskState(new Cancelled());

		// Sets a cancel date for the task1
		task1.setCancelDate();

		// use of control to set task1 to state cancelled
		uS347CancelOnGoingTaskController.cancelOnGoingTask();

		// asserts that task1 has state cancelled
		assertEquals("Cancelled", task1.viewTaskStateName());
	}


	/**
	 * This test verifies if task in state OnGoing and then marked with a finish date
	 * can not be changed to Cancelled using
	 * the controller.
	 */
	@Test
	public void testNotCancelOnGoingTask() {

		// create controller
		US347CancelOnGoingTaskController uS347NotCancelOnGoingTaskController = new US347CancelOnGoingTaskController(task1.getTaskID(), project1);


		// Sets a finish date for the task1
		task1.setFinishDate(Calendar.getInstance());

		// use of control to set task1 to state cancelled
		uS347NotCancelOnGoingTaskController.cancelOnGoingTask();

		// asserts that task1 has state cancelled
		assertEquals("OnGoing", task1.viewTaskStateName());
	}


}
