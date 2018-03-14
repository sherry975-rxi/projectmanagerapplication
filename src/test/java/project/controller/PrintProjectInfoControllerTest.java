package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Services.ProjectService;
import project.Services.TaskContainerService;
import project.Services.UserContainerService;
import project.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PrintProjectInfoControllerTest {


	User user1;
	User joaoPM;
	ProjectCollaborator collab1, collab2;
	ProjectService projectContainer;
	UserContainerService userContainer;
	Project project, project1;
	Calendar startDate, finishDate;
	TaskContainerService taskContainer;
	Task task1, task2, task3;
	PrintProjectInfoController controller, controller1;

	@Before
	public void setUp() {
		// create company
		projectContainer = new ProjectService();
		projectContainer.setProjCounter(1);

		// create user
		userContainer = new UserContainerService();
		user1 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator",
				"910000000", "Rua", "2401-00", "Test", "Testo", "Testistan");

		// create user admin
		joaoPM = userContainer.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// add user to user list
		userContainer.addUserToUserRepository(user1);
		userContainer.addUserToUserRepository(joaoPM);

		// creates project repository
		//projectContainer = myCompany.getProjectsContainer();

		// Creates one Project
		project = projectContainer.createProject("Projeto de gestão",
				"Este projeto está focado na gestão.", joaoPM);

		project.setProjectBudget(1000);
		project.setProjectStatus(3);

		// add project to project repository
		projectContainer.addProjectToProjectContainer(project);

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

		// create taskContainer
		taskContainer = project.getTaskRepository();

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
		task2.addProjectCollaboratorToTask(collab1);
		task3.addProjectCollaboratorToTask(collab2);

		// Instantiates de controller
		controller = new PrintProjectInfoController(project.getIdCode());
		controller.setProject();

	}

	@After
	public void tearDown() {
		userContainer = null;
		projectContainer = null;
		user1 = null;
		joaoPM = null;
		project = null;
		startDate = null;
		finishDate = null;
		taskContainer = null;
		task1 = null;
		task2 = null;
		task3 = null;
		collab1 = null;
	}

	/**
	 * Tests if the method of controller gets the project's name
	 */
	@Test
	public void testPrintProjectNameInfo() {

		assertEquals(controller.printProjectNameInfo(), "Projeto de gestão");
	}

	/**
	 * Tests if the method of controller gets the project's ID
	 */
	@Test
	public void testPrintProjectIDCodeInfo() {

		assertEquals(controller.printProjectIDCodeInfo(), "1");
	}

	/**
	 * Tests if the method of controller gets the project's status
	 */
	@Test
	public void testPrintProjectStatusInfo() {

		assertEquals(controller.printProjectStatusInfo(), "3");
	}

	/**
	 * Tests if the method of controller gets the project's description
	 */
	@Test
	public void testPrintProjectDescriptionInfo() {

		assertEquals(controller.printProjectDescriptionInfo(), "Este projeto está focado na gestão.");
	}

	/**
	 * Tests if the method of controller gets the project's start date
	 */
	@Test
	public void testPrintProjectStartDateInfo() {

		assertEquals(controller.printProjectStartDateInfo(), "Mon, 2 Jan 2017 12:31:00");
	}

	/**
	 * Tests if the method of controller gets the project's finish date
	 */
	@Test
	public void testPrintProjectFinishDateInfo() {

		assertEquals(controller.printProjectFinishDateInfo(), "Thu, 2 Feb 2017 12:31:00");
	}

	/**
	 * Tests if the method of controller gets the project's manager
	 */
	@Test
	public void testPrintProjectManagerInfo() {

		assertEquals(controller.printProjectManagerInfo(), "João");
	}

	/**
	 * Tests if the method of controller gets the project's team
	 */
	@Test
	public void testPrintProjectTeamInfo() {

		assertEquals(controller.printProjectTeamInfo(), "Daniel [ACTIVE], João [ACTIVE]");
	}

	/**
	 * Tests if the method of controller gets the project's budget
	 */
	@Test
	public void testPrintProjectBudgetInfo() {

		assertEquals(controller.printProjectBudgetInfo(), "1000");
	}

	/**
	 * Tests if the method of controller gets the project's task list
	 */
	@Test
	public void testGetProjectTaskList() {

		// create a list of Strings with ID and description of task, to compare in
		// assert
		List<String> toCompare = new ArrayList<>();
		toCompare.add("[1.1] First task");
		toCompare.add("[1.2] Second task");
		toCompare.add("[1.3] Third task");

		assertEquals(controller.getProjectTaskList(), toCompare);
	}

	/**
	 * Tests if the method of controller gets the project's task list IDs
	 */
	@Test
	public void testGetTasksIDs() {

		// create a list of Strings with ID of task, to compare in assert
		List<String> toCompare = new ArrayList<>();
		toCompare.add("1.1");
		toCompare.add("1.2");
		toCompare.add("1.3");

		assertEquals(controller.getTasksIDs(), toCompare);
	}

	/**
	 * Tests if the method of controller gets the project's tasks
	 */
	@Test
	public void testGetTasks() {

		// create a list of tasks, to compare in assert
		List<Task> toCompare = new ArrayList<>();
		toCompare.add(task1);
		toCompare.add(task2);
		toCompare.add(task3);

		assertEquals(controller.getTasks(), toCompare);
	}

	@Test
	public void getProjectNameByID() {

		Integer projectID = project.getIdCode();

		// create controller
		PrintProjectInfoController controller = new PrintProjectInfoController(projectID);
		controller.setProject();

		String projectName = controller.printProjectNameInfo();

		assertEquals("Projeto de gestão", projectName);

	}

	@Test
	public void testPrintProjectInfoController(){

		// Creates one Project
		project1 = projectContainer.createProject("Projeto de voluntariado",
				"Este projeto está focado em solidariedade.", user1);

		// add project to project repository
		projectContainer.addProjectToProjectContainer(project1);

		// Instantiates de controller
		controller1 = new PrintProjectInfoController(project1);
		controller1.setProject();

		assertEquals(controller1.printProjectNameInfo(), "Projeto de voluntariado");

		// add user to project team
		project1.addProjectCollaboratorToProjectTeam(collab1);
		project1.addProjectCollaboratorToProjectTeam(collab2);

		//remove user from project team
		project1.removeProjectCollaboratorFromProjectTeam(joaoPM);

		assertEquals(controller1.printProjectTeamInfo(), "Daniel [ACTIVE], João [INACTIVE]");


	}

}
