package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CalculateReportedProjectCostControllerTest {

	/**
	 * 
	 * This class tests the CalculateReportedProjectCostController class
	 *
	 */
	Company blip;
	User userDaniel;
	User userJonny;
	User userMike;
	User userAna;
	User projectManager;
	Project project;
	ProjectCollaborator projectUserDaniel;
	ProjectCollaborator projectUserJonny;
	ProjectCollaborator projectUserMike;
	ProjectCollaborator projectUserAna;
	ProjectContainer projectContainer;
	TaskContainer taskContainer;
	Task testTask;
	Task testTask2;
	TaskCollaborator taskWorkerDaniel;
	TaskCollaborator taskWorkerJonny;
	TaskCollaborator taskWorkerMike;
	TaskCollaborator taskWorkerAna;
	CalculateReportedProjectCostController controllerCost;

	double totalCost;

	@Before
	public void setUp() {

		blip = Company.getTheInstance();

		// create user
		userDaniel = new User("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000");
		// create user2
		userJonny = new User("João", "joao@gmail.com", "002", "Not Admin", "920000000");

		// create user3
		userMike = new User("Miguel", "miguel@gmail.com", "003", "Not Admin", "920000000");

		// create user4
		userAna = new User("Ana", "ana@gmail.com", "004", "Not Admin", "920000000");

		// create projectManager
		projectManager = new User("Manager boi", "manger@gmail.com", "005", "Kinda Admin", "920000000");

		// adds all users to user list
		blip.getUsersContainer().addUserToUserRepository(userDaniel);
		blip.getUsersContainer().addUserToUserRepository(userJonny);
		blip.getUsersContainer().addUserToUserRepository(userMike);
		blip.getUsersContainer().addUserToUserRepository(userAna);
		blip.getUsersContainer().addUserToUserRepository(projectManager);

		// set user as collaborator
		userDaniel.setUserProfile(Profile.COLLABORATOR);
		userJonny.setUserProfile(Profile.COLLABORATOR);
		userMike.setUserProfile(Profile.COLLABORATOR);
		userAna.setUserProfile(Profile.COLLABORATOR);

		// create project and add it to project list
		project = new Project(0, "Make things", "Test: CalculateReportedProjectCost", projectManager);
		blip.getProjectsContainer().addProjectToProjectContainer(project);

		// creates 4 Project Collaborators and adds them to the project
		projectUserDaniel = project.createProjectCollaborator(userDaniel, 10);
		projectUserJonny = project.createProjectCollaborator(userJonny, 20);
		projectUserMike = project.createProjectCollaborator(userMike, 5);
		projectUserAna = project.createProjectCollaborator(userAna, 3);

		project.addProjectCollaboratorToProjectTeam(projectUserDaniel);
		project.addProjectCollaboratorToProjectTeam(projectUserJonny);
		project.addProjectCollaboratorToProjectTeam(projectUserMike);
		project.addProjectCollaboratorToProjectTeam(projectUserAna);

		// create a estimated Task Start Date
		Calendar estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.DECEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 10);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);
		// create a estimated Task Start Date
		Calendar taskDeadlineDateTest = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2017);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.DECEMBER);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 20);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create taskContainer
		taskContainer = project.getTaskRepository();

		testTask = taskContainer.createTask("Testin once", 10, estimatedTaskStartDateTest, taskDeadlineDateTest, 10);
		testTask2 = taskContainer.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);

		// Adds Tasks to TaskContainer
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);

		// Creates 4 Task Workers
		taskWorkerDaniel = testTask.createTaskCollaborator(projectUserDaniel);
		taskWorkerJonny = testTask2.createTaskCollaborator(projectUserJonny);
		taskWorkerMike = testTask.createTaskCollaborator(projectUserMike);
		taskWorkerAna = testTask2.createTaskCollaborator(projectUserAna);

		// create variable to calculate total cost reported to project
		totalCost = 0.0;

	}

	@After
	public void tearDown() {

		Company.clear();
		blip = null;
		userDaniel = null;
		userJonny = null;
		userMike = null;
		userAna = null;
		projectUserDaniel = null;
		projectUserJonny = null;
		projectUserMike = null;
		projectUserAna = null;
		taskWorkerDaniel = null;
		taskWorkerJonny = null;
		taskWorkerMike = null;
		taskWorkerAna = null;
		testTask = null;
		testTask2 = null;
		project = null;
		projectContainer = null;
		taskContainer = null;
		totalCost = 0.0;
		controllerCost = null;

	}

	@Test
	public void calculateReportedProjectCostControllerTest() {

		// Adds users to the respective tasks
		testTask.addTaskCollaboratorToTask(taskWorkerDaniel);
		testTask.addTaskCollaboratorToTask(taskWorkerJonny);
		testTask2.addTaskCollaboratorToTask(taskWorkerMike);
		testTask2.addTaskCollaboratorToTask(taskWorkerAna);
		// Task worker sets the hours spent on the task
		testTask.createReport(taskWorkerDaniel, Calendar.getInstance(), 5);
		testTask.createReport(taskWorkerJonny, Calendar.getInstance(), 10);
		testTask2.createReport(taskWorkerMike, Calendar.getInstance(), 2);
		testTask2.createReport(taskWorkerAna, Calendar.getInstance(), 3);

		// Calculates the value of the project - Equals to to the sum of the total hours
		// spent times the cost of each TaskWorker

		int danielCost = 5 * taskWorkerDaniel.getProjectCollaboratorFromTaskCollaborator().getCollaboratorCost();
		int jonnyCost = 10 * taskWorkerJonny.getProjectCollaboratorFromTaskCollaborator().getCollaboratorCost();
		int mikeCost = 2 * taskWorkerMike.getProjectCollaboratorFromTaskCollaborator().getCollaboratorCost();
		int anaCost = 3 * taskWorkerAna.getProjectCollaboratorFromTaskCollaborator().getCollaboratorCost();

		totalCost = danielCost + jonnyCost + mikeCost + anaCost;

		// Creates a CalculateReportedProjectCostController
		controllerCost = new CalculateReportedProjectCostController();

		// Compares the 2 values
		assertEquals(totalCost, controllerCost.calculateReportedProjectCostController(project), 0.01);

	}

	@Test
	public void getReportedTimeOfAllTasks() {
		// Adds users to the respective tasks
		testTask.addTaskCollaboratorToTask(taskWorkerDaniel);
		testTask.addTaskCollaboratorToTask(taskWorkerJonny);
		testTask2.addTaskCollaboratorToTask(taskWorkerMike);
		testTask2.addTaskCollaboratorToTask(taskWorkerAna);
		// Task worker sets the hours spent on the task
		testTask.createReport(taskWorkerDaniel, Calendar.getInstance(), 5);
		testTask.getReports().get(0).setReportedTime(5);
		testTask.createReport(taskWorkerJonny, Calendar.getInstance(), 10);
		testTask.getReports().get(1).setReportedTime(10);

		// Creates a new list of ReporCcost
		List<String> reportedCost = new ArrayList<>();

		// Task1 has a report of 5*10 + 10*20
		reportedCost.add("250.0");
		// Task2 has no reports
		reportedCost.add("0.0");

		// Creates a CalculateReportedProjectCostController
		controllerCost = new CalculateReportedProjectCostController();
		assertEquals(reportedCost, controllerCost.calculeReportedCostOfEachTaskController(project));

	}

	@Test
	public void testGetTaskId() {
		// Creates a CalculateReportedProjectCostController
		controllerCost = new CalculateReportedProjectCostController();

		assertTrue("0.1".equals(controllerCost.getTaskId(project).get(0)));
		assertTrue("0.2".equals(controllerCost.getTaskId(project).get(1)));
	}

}
