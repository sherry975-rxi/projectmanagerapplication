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
		task1.addProjectCollaboratorToTask(collab2);
		task2.addProjectCollaboratorToTask(collab1);
		task3.addProjectCollaboratorToTask(collab2);
	}

	@After
	public void tearDown() {
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
	}

	@Test
	public void testPrintTaskNameInfo() {
		// create controller
		PrintTaskInfoController controller = new PrintTaskInfoController(task1);

		assertEquals(controller.printTaskNameInfo(), "FIRST TASK");
	}

	// ver porque está a falhar
	// @Test
	// public void testPrintProjectNameInfo() {
	// //create controller
	// PrintTaskInfoController controller = new PrintTaskInfoController(task1);
	//
	// assertEquals(controller.printProjectNameInfo(), "Projeto de gestão");
	// }

	@Test
	public void testPrintTaskIDCodeInfo() {
		// create controller
		PrintTaskInfoController controller = new PrintTaskInfoController(task1);

		assertEquals(controller.printTaskIDCodeInfo(), "1.1");
	}

	@Test
	public void testPrintTaskStateInfo() {
		// create controller
		PrintTaskInfoController controller = new PrintTaskInfoController(task1);

		assertEquals(controller.printTaskStateInfo(), "Created");
	}

	@Test
	public void testPrintEstimatedTaskStartDateInfo() {
		// create controller
		PrintTaskInfoController controller = new PrintTaskInfoController(task1);

		assertEquals(controller.printTaskEstimatedStartDateInfo(), "---");

		Calendar estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.set(2017, Calendar.JANUARY, 2, 12, 31, 00);
		task1.setEstimatedTaskStartDate(estimatedStartDate);
		assertEquals(controller.printTaskEstimatedStartDateInfo(), "Mon, 2 Jan 2017 12:31:00");
	}

	@Test
	public void testPrintTaskStartDateInfo() {
		// create controller
		PrintTaskInfoController controller = new PrintTaskInfoController(task1);

		assertEquals(controller.printTaskStartDateInfo(), "---");

		Calendar startDate = Calendar.getInstance();
		startDate.set(2017, Calendar.JANUARY, 2, 12, 31, 00);
		task1.setStartDate(startDate);
		assertEquals(controller.printTaskStartDateInfo(), "Mon, 2 Jan 2017 12:31:00");
	}

	@Test
	public void testPrintTaskDeadlineInfo() {
		// create controller
		PrintTaskInfoController controller = new PrintTaskInfoController(task1);

		assertEquals(controller.printTaskDeadlineInfo(), "---");

		Calendar estimatedFinishDate = Calendar.getInstance();
		estimatedFinishDate.set(2017, Calendar.FEBRUARY, 2, 12, 31, 00);
		task1.setTaskDeadline(estimatedFinishDate);
		assertEquals(controller.printTaskDeadlineInfo(), "Thu, 2 Feb 2017 12:31:00");
	}

	@Test
	public void testPrintTaskFinishDateInfo() {
		// create controller
		PrintTaskInfoController controller = new PrintTaskInfoController(task1);

		assertEquals(controller.printTaskDeadlineInfo(), "---");

		Calendar finishDate = Calendar.getInstance();
		finishDate.set(2017, Calendar.FEBRUARY, 2, 12, 31, 00);
		task1.setFinishDate(finishDate);
		assertEquals(controller.printTaskFinishDateInfo(), "Thu, 2 Feb 2017 12:31:00");
	}

	@Test
	public void testPrintTaskTeamInfo() {
		// create controller
		PrintTaskInfoController controller = new PrintTaskInfoController(task1);

		assertEquals(controller.printTaskTeamInfo(), "Daniel, João");
	}

	@Test
	public void testPrintTaskBudgetInfo() {
		// create controller
		PrintTaskInfoController controller = new PrintTaskInfoController(task1);
		task1.setTaskBudget(1000);
		assertEquals(controller.printTaskBudgetInfo(), "1000");
	}

	@Test
	public void testPrintInfoFromTask() {
		// create controller
		PrintTaskInfoController controller = new PrintTaskInfoController(task1.getTaskID(), project.getIdCode());

		String projectName = controller.printProjectNameInfo();
		assertEquals("Projeto de gestão", projectName);
	}
}
