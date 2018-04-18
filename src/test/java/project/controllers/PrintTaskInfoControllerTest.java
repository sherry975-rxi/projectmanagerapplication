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
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = { "project.services", "project.controllers", "project.model" })
public class PrintTaskInfoControllerTest {

	@Autowired
	ProjectService projContainer;

	@Autowired
	UserService userContainer;

	@Autowired
	TaskService taskService;

	User user1;
	private User joaoPM;
	private ProjectCollaborator collab1, collab2;
	Project project;
	private Calendar startDate, finishDate;
	Task task1, task2, task3;

	Integer projectID;

	@Autowired
	PrintTaskInfoController controller;

	@Before
	public void setUp() {

		// create user
		user1 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// create user admin
		joaoPM = userContainer.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		joaoPM.setUserProfile(Profile.COLLABORATOR);

		// add user to user list
		userContainer.addUserToUserRepositoryX(user1);
		userContainer.addUserToUserRepositoryX(joaoPM);

		// Creates one Project
		project = projContainer.createProject("Projeto de gestão", "Este projeto está focado na gestão.", joaoPM);
		project.setProjectBudget(3000);

		// add start date to project
		startDate = Calendar.getInstance();
		startDate.set(2017, Calendar.JANUARY, 2, 12, 31, 0);
		project.setStartdate(startDate);

		// add finish date to project
		finishDate = Calendar.getInstance();
		finishDate.set(2017, Calendar.FEBRUARY, 2, 12, 31, 0);
		project.setFinishdate(finishDate);

		// add project to project repository
		projContainer.updateProject(project);

		// create project collaborators
		collab1 = projContainer.createProjectCollaborator(user1, project, 2);
		collab2 = projContainer.createProjectCollaborator(joaoPM, project, 2);

		// create three tasks
		task1 = taskService.createTask("First task", project);
		task2 = taskService.createTask("Second task", project);
		task3 = taskService.createTask("Third task", project);

		// add project's collaborators to tasks
		task1.addProjectCollaboratorToTask(collab1);
		task3.addProjectCollaboratorToTask(collab2);
		task2.addProjectCollaboratorToTask(collab1);
		task2.addProjectCollaboratorToTask(collab2);

		projectID = project.getDbId();

		// Instantiates the controllers
		controller.setTask(task1);
		controller.setProject(project);

	}

	@After
	public void clear() {

		user1 = null;
		joaoPM = null;
		collab1 = null;
		collab2 = null;
		project = null;
		startDate = null;
		finishDate = null;
		task1 = null;
		projectID = null;
	}

	/**
	 * Tests if the method of controllers gets the task's name
	 */
	@Test
	public void testPrintTaskNameInfo() {

		assertEquals(controller.printTaskNameInfo(), "First task");
	}

	// @Test
	// public void testPrintProjectNameInfo() {
	// // create controllers
	// PrintTaskInfoController controllers = new PrintTaskInfoController(task1);
	//
	// assertEquals(controllers.printProjectNameInfo(), "Projeto de gestão");
	// }

	/**
	 * Tests if the method of controllers gets the task's ID
	 */
	@Test
	public void testPrintTaskIDCodeInfo() {
		assertEquals(controller.printTaskIDCodeInfo(), (projectID + ".1"));
	}

	/**
	 * Tests if the method of controllers gets the task's state
	 */
	@Test
	public void testPrintTaskStateInfo() {
		assertEquals(controller.printTaskStateInfo(), "PLANNED");
	}

	/**
	 * Tests if the method of controllers gets the task's estimated start date
	 */
	@Test
	public void testPrintEstimatedTaskStartDateInfo() {
		// create controllers
		controller = new PrintTaskInfoController(task1);

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
	 * Tests if the method of controllers gets the task's start date
	 */
	@Test
	public void testPrintTaskStartDateInfo() {
		// create controllers
		controller = new PrintTaskInfoController(task1);

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
	 * Tests if the method of controllers gets the task's deadline
	 */
	@Test
	public void testPrintTaskDeadlineInfo() {
		// create controllers
		controller = new PrintTaskInfoController(task1);

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
	 * Tests if the method of controllers gets the task's finish date
	 */
	@Test
	public void testPrintTaskFinishDateInfo() {
		// create controllers
		controller = new PrintTaskInfoController(task1);

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
	 * Tests if the method of controllers gets the task's team
	 */
	@Test
	public void testPrintTaskTeamInfo() {

		assertEquals(controller.printTaskTeamInfo(), "Daniel, João");
	}

	/**
	 * Tests if the method of controllers gets the project name where the task is
	 * associated
	 */
	@Test
	public void testPrintInfoFromTask() {

		String projectName = controller.printProjectNameInfo();
		assertEquals("Projeto de gestão", projectName);
	}

	/**
	 * Tests if the method of controllers gets the task's budget
	 */
	@Test
	public void testPrintTaskBudgetInfo() {
		task1.setTaskBudget(20.0);
		assertEquals(controller.printTaskBudgetInfo(), "20.0");
	}
	
	@Test
	public void testSettersAndGetters() {
		controller.setDateFormat(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss"));
		assertEquals(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss"), controller.getDateFormat());
		
		Integer projectIDExpected = project.getIdCode();
		controller.setProject(project);
		controller.setProjeID(projectIDExpected);
		assertEquals(projectIDExpected, controller.getProjeID());
		
		String taskIDExpected = task1.getTaskID();
		controller.setTask(task1);
		controller.setTaskID(taskIDExpected);
		assertEquals(taskIDExpected, controller.getTaskID());
		
		controller.setProjectAndTask();
		assertEquals(task1, controller.getTask());
		assertEquals(project, controller.getProject());
		
	}
}
