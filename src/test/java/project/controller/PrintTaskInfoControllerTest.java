package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Services.ProjectService;
import project.Services.UserContainerService;
import project.model.*;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class PrintTaskInfoControllerTest {

	private ProjectService projContainer = new ProjectService();
	UserContainerService userContainer = new UserContainerService();
	User user1;
	private User joaoPM;
	private ProjectCollaborator collab1, collab2;
	ProjectService projectContainer;
	Project project;
	private Calendar startDate, finishDate;
	Task task1, task2, task3;
	PrintTaskInfoController controller;

	@Before
	public void setUp() {

		projContainer.setProjCounter(1);

		// create user
		user1 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator",
				"910000000", "Rua", "2401-00", "Test", "Testo", "Testistan");

		// create user admin
		joaoPM = userContainer.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// add user to user list
		userContainer.addUserToUserRepository(user1);
		userContainer.addUserToUserRepository(joaoPM);

		// creates project repository
		projectContainer = projContainer;

		// Creates one Project
		project = projContainer.createProject("Projeto de gestão",
				"Este projeto está focado na gestão.", joaoPM);
		project.setProjectBudget(3000);

		// add project to project repository
		projContainer.addProjectToProjectContainer(project);

		// add start date to project
		startDate = Calendar.getInstance();
		startDate.set(2017, Calendar.JANUARY, 2, 12, 31, 0);
		project.setStartdate(startDate);

		// add finish date to project
		finishDate = Calendar.getInstance();
		finishDate.set(2017, Calendar.FEBRUARY, 2, 12, 31, 0);
		project.setFinishdate(finishDate);

		// create project collaborators
		collab1 = new ProjectCollaborator(user1, 2);
		collab2 = new ProjectCollaborator(joaoPM, 3);

		// create taskContainer

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		joaoPM.setUserProfile(Profile.COLLABORATOR);

		// add user to project team
		project.addProjectCollaboratorToProjectTeam(collab1);
		project.addProjectCollaboratorToProjectTeam(collab2);

		// create three tasks
		task1 = project.getTaskRepository().createTask("First task");
		task2 = project.getTaskRepository().createTask("Second task");
		task3 = project.getTaskRepository().createTask("Third task");

		// add task to project
		project.getTaskRepository().addTaskToProject(task1);
		project.getTaskRepository().addTaskToProject(task2);
		project.getTaskRepository().addTaskToProject(task3);

		// add project's collaborators to tasks
		task1.addProjectCollaboratorToTask(collab1);
		task3.addProjectCollaboratorToTask(collab2);
		task2.addProjectCollaboratorToTask(collab1);
		task2.addProjectCollaboratorToTask(collab2);

		// Instantiates the controller
		controller = new PrintTaskInfoController("1.1", 1);
		controller.setProjectAndTask();
	}

	@After
	public void tearDown() {
		projContainer = null;
		userContainer = null;
		user1 = null;
		joaoPM = null;
		project = null;
		projectContainer = null;
		startDate = null;
		finishDate = null;
		task1 = null;
		task2 = null;
		task3 = null;
		collab1 = null;
		collab2 = null;
		controller = null;
	}

	/**
	 * Tests if the method of controller gets the task's name
	 */
	@Test
	public void testPrintTaskNameInfo() {

		assertEquals(controller.printTaskNameInfo(), "First task");
	}

	// @Test
	// public void testPrintProjectNameInfo() {
	// // create controller
	// PrintTaskInfoController controller = new PrintTaskInfoController(task1);
	//
	// assertEquals(controller.printProjectNameInfo(), "Projeto de gestão");
	// }

	/**
	 * Tests if the method of controller gets the task's ID
	 */
	@Test
	public void testPrintTaskIDCodeInfo() {
		assertEquals(controller.printTaskIDCodeInfo(), "1.1");
	}

	/**
	 * Tests if the method of controller gets the task's state
	 */
	@Test
	public void testPrintTaskStateInfo() {
		assertEquals(controller.printTaskStateInfo(), "Created");
	}

	/**
	 * Tests if the method of controller gets the task's estimated start date
	 */
	@Test
	public void testPrintEstimatedTaskStartDateInfo() {
		// create controller
		PrintTaskInfoController controller = new PrintTaskInfoController(task1);

		assertEquals(controller.printTaskEstimatedStartDateInfo(), "---");

		// asserts that the task1 don't have a estimated start date
		assertEquals(controller.printTaskEstimatedStartDateInfo(), "---");

		// create a calendar date to set task's estimated start date
		Calendar estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.set(2017, Calendar.JANUARY, 2, 12, 31, 0);
		task1.setEstimatedTaskStartDate(estimatedStartDate);
		// asserts that the task1 have the estimated start date that are previous set
		assertEquals(controller.printTaskEstimatedStartDateInfo(), "Mon, 2 Jan 2017 12:31:00");
	}

	/**
	 * Tests if the method of controller gets the task's start date
	 */
	@Test
	public void testPrintTaskStartDateInfo() {
		// create controller
		PrintTaskInfoController controller = new PrintTaskInfoController(task1);

		assertEquals(controller.printTaskStartDateInfo(), "---");

		// asserts that the task1 don't have a start date
		assertEquals(controller.printTaskStartDateInfo(), "---");

		// create a calendar date to set task's start date
		Calendar startDate = Calendar.getInstance();
		startDate.set(2017, Calendar.JANUARY, 2, 12, 31, 0);
		task1.setStartDate(startDate);
		// asserts that the task1 have the start date that are previous set
		assertEquals(controller.printTaskStartDateInfo(), "Mon, 2 Jan 2017 12:31:00");
	}

	/**
	 * Tests if the method of controller gets the task's deadline
	 */
	@Test
	public void testPrintTaskDeadlineInfo() {
		// create controller
		PrintTaskInfoController controller = new PrintTaskInfoController(task1);

		// asserts that the task1 don't have a deadline
		assertEquals(controller.printTaskDeadlineInfo(), "---");

		// create a calendar date to set task's deadline
		Calendar estimatedFinishDate = Calendar.getInstance();
		estimatedFinishDate.set(2017, Calendar.FEBRUARY, 2, 12, 31, 0);
		task1.setTaskDeadline(estimatedFinishDate);
		// asserts that the task1 have the deadline that are previous set
		assertEquals(controller.printTaskDeadlineInfo(), "Thu, 2 Feb 2017 12:31:00");
	}

	/**
	 * Tests if the method of controller gets the task's finish date
	 */
	@Test
	public void testPrintTaskFinishDateInfo() {
		// create controller
		PrintTaskInfoController controller = new PrintTaskInfoController(task1);

		// asserts that the task1 don't have a finish date
		assertEquals(controller.printTaskDeadlineInfo(), "---");

		// create a calendar date to set task's finish date
		Calendar finishDate = Calendar.getInstance();
		finishDate.set(2017, Calendar.FEBRUARY, 2, 12, 31, 0);
		task1.setFinishDate(finishDate);
		// asserts that the task1 have the finish date that are previous set
		assertEquals(controller.printTaskFinishDateInfo(), "Thu, 2 Feb 2017 12:31:00");
	}

	/**
	 * Tests if the method of controller gets the task's team
	 */
	@Test
	public void testPrintTaskTeamInfo() {

		assertEquals(controller.printTaskTeamInfo(), "Daniel");
	}
	/**
	 * Tests if the method of controller gets the project name where the task is associated
	 */
	@Test
	public void testPrintInfoFromTask() {

		String projectName = controller.printProjectNameInfo();
		assertEquals("Projeto de gestão", projectName);
	}
	/**
	 * Tests if the method of controller gets the task's budget
	 */
	@Test
	public void testPrintTaskBudgetInfo(){
		task1.setTaskBudget(20);
		assertEquals(controller.printTaskBudgetInfo(), "20");

	}
}
