package project.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

public class PrintProjectInfoControllerTest {

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

	/**
	 * Tests if the method of controller gets the project's name
	 */
	@Test
	public void testPrintProjectNameInfo() {
		// create controller
		PrintProjectInfoController controller = new PrintProjectInfoController(project);

		assertEquals(controller.printProjectNameInfo(), "Projeto de gestão");
	}

	/**
	 * Tests if the method of controller gets the project's ID
	 */
	@Test
	public void testPrintProjectIDCodeInfo() {
		// create controller
		PrintProjectInfoController controller = new PrintProjectInfoController(project);

		assertEquals(controller.printProjectIDCodeInfo(), "1");
	}

	/**
	 * Tests if the method of controller gets the project's status
	 */
	@Test
	public void testPrintProjectStatusInfo() {
		// create controller
		PrintProjectInfoController controller = new PrintProjectInfoController(project);
		// set project status to 1
		project.setProjectStatus(1);
		assertEquals(controller.printProjectStatusInfo(), "1");
	}

	/**
	 * Tests if the method of controller gets the project's description
	 */
	@Test
	public void testPrintProjectDescriptionInfo() {
		// create controller
		PrintProjectInfoController controller = new PrintProjectInfoController(project);

		assertEquals(controller.printProjectDescriptionInfo(), "Este projeto está focado na gestão.");
	}

	/**
	 * Tests if the method of controller gets the project's start date
	 */	
	@Test public void testPrintProjectStartDateInfo() {
		//create controller
		 PrintProjectInfoController controller = new PrintProjectInfoController(project);
		
		 assertEquals(controller.printProjectStartDateInfo(), "Mon, 2 Jan 2017 12:31:00"); }
		 
	/**
	 * Tests if the method of controller gets the project's finish date
	 */
	 @Test public void testPrintProjectFinishDateInfo() { 
		 //create controller
		 PrintProjectInfoController controller = new PrintProjectInfoController(project);
		 
		 assertEquals(controller.printProjectFinishDateInfo(), "Thu, 2 Feb 2017 12:31:00"); }
		 
	 /**
	 * Tests if the method of controller gets the project's manager
	 */
	@Test
	public void testPrintProjectManagerInfo() {
		// create controller
		PrintProjectInfoController controller = new PrintProjectInfoController(project);

		assertEquals(controller.printProjectManagerInfo(), "João");
	}

	/**
	 * Tests if the method of controller gets the project's team 
	 */
	@Test
	public void testPrintProjectTeamInfo() {
		// create controller
		PrintProjectInfoController controller = new PrintProjectInfoController(project);

		assertEquals(controller.printProjectTeamInfo(), "Daniel, João");
	}

	/**
	 * Tests if the method of controller gets the project's budget
	 */
	@Test
	public void testPrintProjectBudgetInfo() {
		// create controller
		PrintProjectInfoController controller = new PrintProjectInfoController(project);

		//set project budget to 1000
		project.setProjectBudget(1000);
		assertEquals(controller.printProjectBudgetInfo(), "1000");
	}
	
	/**
	 * Tests if the method of controller gets the project's task list
	 */
	@Test
	public void testGetProjectTaskList() {
		// create controller
		PrintProjectInfoController controller = new PrintProjectInfoController(project);

		//create a list of Strings with ID and description of task, to compare in assert
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
		// create controller
		PrintProjectInfoController controller = new PrintProjectInfoController(project);

		//create a list of Strings with ID of task, to compare in assert
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
		// create controller
		PrintProjectInfoController controller = new PrintProjectInfoController(project);
		
		//create a list of tasks, to compare in assert
		List<Task> toCompare = new ArrayList<>();
		toCompare.add(task1);
		toCompare.add(task2);
		toCompare.add(task3);

		assertEquals(controller.getTasks(), toCompare);
	}

}
