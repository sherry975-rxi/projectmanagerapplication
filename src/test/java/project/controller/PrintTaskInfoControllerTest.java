package project.controller;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskRepository;
import project.model.User;

public class PrintTaskInfoControllerTest {

	Company myCompany;

	User user1;
	User joaoPM;
	ProjectCollaborator collab1, collab2;
	ProjectRepository projectRepository;
	Project project;
	Calendar startDate, finishDate;
	TaskRepository taskRepository;
	Task task1, task2, task3;
	PrintTaskInfoController controller;

	@Before
	public void setUp() {
		// create company
		myCompany = Company.getTheInstance();
		myCompany.getProjectsRepository().setProjCounter(1);

		// create user
		user1 = myCompany.getUsersRepository().createUser("Daniel", "daniel@gmail.com", "001", "collaborator",
				"910000000", "Rua", "2401-00", "Test", "Testo", "Testistan");

		// create user admin
		joaoPM = myCompany.getUsersRepository().createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// add user to user list
		myCompany.getUsersRepository().addUserToUserRepository(user1);
		myCompany.getUsersRepository().addUserToUserRepository(joaoPM);

		// creates project repository
		projectRepository = myCompany.getProjectsRepository();

		// Creates one Project
		project = myCompany.getProjectsRepository().createProject("Projeto de gestão",
				"Este projeto está focado na gestão.", joaoPM);
		project.setProjectBudget(3000);

		// add project to project repository
		myCompany.getProjectsRepository().addProjectToProjectRepository(project);

		// add start date to project
		Calendar startDate = Calendar.getInstance();
		startDate.set(2017, Calendar.JANUARY, 2, 12, 31, 00);
		project.setStartdate(startDate);

		// add finish date to project
		Calendar finishDate = Calendar.getInstance();
		finishDate.set(2017, Calendar.FEBRUARY, 2, 12, 31, 00);
		project.setFinishdate(finishDate);

		// create project collaborators
		collab1 = new ProjectCollaborator(user1, 2);
		collab2 = new ProjectCollaborator(joaoPM, 3);

		// create taskRepository
		taskRepository = project.getTaskRepository();

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
		project.getTaskRepository().addProjectTask(task1);
		project.getTaskRepository().addProjectTask(task2);
		project.getTaskRepository().addProjectTask(task3);

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
		Company.clear();
		user1 = null;
		joaoPM = null;
		project = null;
		projectRepository = null;
		startDate = null;
		finishDate = null;
		taskRepository = null;
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
		estimatedStartDate.set(2017, Calendar.JANUARY, 2, 12, 31, 00);
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
		startDate.set(2017, Calendar.JANUARY, 2, 12, 31, 00);
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
		estimatedFinishDate.set(2017, Calendar.FEBRUARY, 2, 12, 31, 00);
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
		finishDate.set(2017, Calendar.FEBRUARY, 2, 12, 31, 00);
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

	@Test
	public void testPrintInfoFromTask() {

		String projectName = controller.printProjectNameInfo();
		assertEquals("Projeto de gestão", projectName);
	}
}
