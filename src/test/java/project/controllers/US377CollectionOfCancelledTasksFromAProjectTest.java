package project.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.*;
import project.model.taskstateinterface.Cancelled;
import project.model.taskstateinterface.OnGoing;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = {"project.services", "project.controllers", "project.model"})
public class US377CollectionOfCancelledTasksFromAProjectTest {

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private TaskService taskService;

	User user1;
	User userAdmin;

	ProjectCollaborator collab1;

	Project project;
	Project project2;

	Task testTask;
	Task testTask2;
	Task testTask3;

	Calendar estimatedTaskStartDateTest;
	Calendar taskDeadlineDateTest;
	Calendar startDateTest;

	@Autowired
	US377CollectionOfCancelledTasksFromAProjectController controller;

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

		userService.addUserToUserRepositoryX(user1);
		userService.addUserToUserRepositoryX(userAdmin);


		// Creates one Project
		project = projectService.createProject("name3", "description4", userAdmin);
		project2 = projectService.createProject("name1", "description4", userAdmin);

		// create project collaborators
		collab1 = projectService.createProjectCollaborator(user1, project, 2);



		// create a estimated Task Start Date
		Calendar startDateTest = Calendar.getInstance();

		// create a estimated Task Start Date
		Calendar estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.add(Calendar.YEAR, -1);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 25);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a estimated Task Dead line Date
		Calendar taskDeadlineDateTest = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.JANUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a expired estimated Task Dead line Date
		Calendar taskExpiredDeadlineDateTest = Calendar.getInstance();
		taskExpiredDeadlineDateTest.add(Calendar.YEAR, -1);
		taskExpiredDeadlineDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		taskExpiredDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskExpiredDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create 3 tasks
		testTask = taskService.createTask("Test dis agen pls", project);
		testTask2 = taskService.createTask("Test dis agen pls", project);
		testTask3 = taskService.createTask("Test moar yeh", project);


		// set estimated task start date and task dead line to tasks
		testTask.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		testTask.setTaskDeadline(taskDeadlineDateTest);

		testTask2.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		testTask2.setTaskDeadline(taskDeadlineDateTest);

		testTask3.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		testTask3.setTaskDeadline(taskDeadlineDateTest);

		// set active user
		testTask.addProjectCollaboratorToTask(collab1);
		testTask.setStartDate(Calendar.getInstance());
		testTask.setCancelDate();
		testTask.setTaskState(new Cancelled());
		testTask.setCurrentState(StateEnum.CANCELLED);

		testTask2.addProjectCollaboratorToTask(collab1);
		testTask2.setStartDate(Calendar.getInstance());
		testTask2.setCancelDate();
		testTask2.setTaskState(new Cancelled());
		testTask2.setCurrentState(StateEnum.CANCELLED);


		testTask3.addProjectCollaboratorToTask(collab1);
		testTask3.setStartDate(Calendar.getInstance());
		testTask3.setTaskState(new OnGoing());
		testTask3.setCurrentState(StateEnum.ONGOING);

		// Creates the controllers to be tested
		controller.setProject(project);
	}

	@After
	public void tearDown(){

		user1 = null;
		userAdmin = null;
		collab1 = null;
		project = null;
		project2 = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
		estimatedTaskStartDateTest = null;
		taskDeadlineDateTest = null;
		startDateTest = null;
	}
	/**
	 * this test verify if the list of canceled projects is equals to the list
	 * created.
	 */
	@Test
	public final void testGetCancelledTasksFromAProject() {

		// create list with cancelled task to compare
		List<Task> cancelledTaskToCompare = new ArrayList<>();

		// add task to the list
		cancelledTaskToCompare.add(testTask);
		cancelledTaskToCompare.add(testTask2);

		assertEquals(cancelledTaskToCompare, controller.getCancelledTasksFromAProject());

	}

	@Test
	public final void testGetCancelledTaskListId() {

		String result = "["+testTask.getTaskID()+"] Test dis agen pls";
		assertTrue(result.equals(controller.getCancelledTaskListId(project).get(0)));
		result = "["+testTask2.getTaskID()+"] Test dis agen pls";
		assertTrue(result.equals(controller.getCancelledTaskListId(project).get(1)));
	}

	@Test
	public final void testSplitStringByFirstSpace() {
		String input = "Test me master!";
		assertTrue("Test".equals(controller.splitStringByFirstSpace(input)));
	}

	@Test
	public final void getProjectCancelledTasks() {
		assertEquals(2, controller.getCancelledTasksFromAProject().size());
	}

}
