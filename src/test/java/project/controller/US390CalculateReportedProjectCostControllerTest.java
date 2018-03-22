package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;
import project.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = {"project.services", "project.controller", "project.model"})
public class US390CalculateReportedProjectCostControllerTest {

	/**
	 *
	 * This class tests the CalculateReportedProjectCostController class
	 *
	 */

	@Autowired
	private ProjectService projContainer;
	@Autowired
	private UserService userContainer;

	@Autowired
	private TaskService taskContainer;


	private User userDaniel;
	private User userJonny;
	private User userMike;
	private User userAna;
	private User projectManager;
	private Project project;
	private ProjectCollaborator projectUserDaniel;
	private ProjectCollaborator projectUserJonny;
	private ProjectCollaborator projectUserMike;
	private ProjectCollaborator projectUserAna;

	private Task testTask;
	private Task testTask2;
	private TaskCollaborator taskWorkerDaniel;
	private TaskCollaborator taskWorkerJonny;
	private TaskCollaborator taskWorkerMike;
	private TaskCollaborator taskWorkerAna;

	private double totalCost;

	@Autowired
	private US390CalculateReportedProjectCostController controllerCost;


	@Before
	public void setUp() {


		// create user
		userDaniel = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "test","test","test","test","test");
		// create user2
		userJonny = userContainer.createUser("João", "joao@gmail.com", "002", "Not Admin", "920000000", "test","test","test","test","test");

		// create user3
		userMike = userContainer.createUser("Miguel", "miguel@gmail.com", "003", "Not Admin", "920000000", "test","test","test","test","test");

		// create user4
		userAna = userContainer.createUser("Ana", "ana@gmail.com", "004", "Not Admin", "920000000", "test","test","test","test","test");

		// create projectManager
		projectManager = userContainer.createUser("Manager boi", "manger@gmail.com", "005", "Kinda Admin", "920000000", "test","test","test","test","test");

		// set user as collaborator
		userDaniel.setUserProfile(Profile.COLLABORATOR);
		userJonny.setUserProfile(Profile.COLLABORATOR);
		userMike.setUserProfile(Profile.COLLABORATOR);
		userAna.setUserProfile(Profile.COLLABORATOR);

		// adds all users to user list
		userContainer.addUserToUserRepositoryX(userDaniel);
		userContainer.addUserToUserRepositoryX(userJonny);
		userContainer.addUserToUserRepositoryX(userMike);
		userContainer.addUserToUserRepositoryX(userAna);
		userContainer.addUserToUserRepositoryX(projectManager);

		// create project and add it to project list
		project = projContainer.createProject("Make things", "Test: CalculateReportedProjectCost", projectManager);

		// creates 4 Project Collaborators and adds them to the project
		projectUserDaniel = projContainer.createProjectCollaborator(userDaniel, project, 10);
		projectUserJonny = projContainer.createProjectCollaborator(userJonny, project, 20);
		projectUserMike = projContainer.createProjectCollaborator(userMike, project, 5);
		projectUserAna = projContainer.createProjectCollaborator(userAna, project, 10);

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

		testTask = taskContainer.createTask("Testin once", project);
		testTask2 = taskContainer.createTask("Test dis agen pls", project);
		testTask.setStartDate(estimatedTaskStartDateTest);
		testTask2.setStartDate(estimatedTaskStartDateTest);
		testTask.setTaskDeadline(taskDeadlineDateTest);
		testTask2.setTaskDeadline(taskDeadlineDateTest);

		// Adds Tasks to TaskContainer
		taskContainer.saveTask(testTask);
		taskContainer.saveTask(testTask2);

		// Creates 4 Task Workers
		taskWorkerDaniel = testTask.createTaskCollaborator(projectUserDaniel);
		taskWorkerJonny = testTask2.createTaskCollaborator(projectUserJonny);
		taskWorkerMike = testTask.createTaskCollaborator(projectUserMike);
		taskWorkerAna = testTask2.createTaskCollaborator(projectUserAna);

		// create variable to calculate total cost reported to project
		totalCost = 0.0;

	}

	@After
	public void tearDown(){

		userDaniel = null;
		userJonny = null;
		userMike = null;
		userAna = null;
		projectManager = null;
		project = null;
		projectUserDaniel = null;
		projectUserJonny = null;
		projectUserMike = null;
		projectUserAna = null;

		testTask = null;
		testTask2 = null;
		taskWorkerDaniel = null;
		taskWorkerJonny = null;
		taskWorkerMike = null;
		taskWorkerAna = null;

		totalCost = 0;
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

		taskContainer.saveTask(testTask);
		taskContainer.saveTask(testTask2);

		// Calculates the value of the project - Equals to to the sum of the total hours
		// spent times the cost of each TaskWorker

		int danielCost = 5 * taskWorkerDaniel.getProjectCollaboratorFromTaskCollaborator().getCollaboratorCost();
		int jonnyCost = 10 * taskWorkerJonny.getProjectCollaboratorFromTaskCollaborator().getCollaboratorCost();
		int mikeCost = 2 * taskWorkerMike.getProjectCollaboratorFromTaskCollaborator().getCollaboratorCost();
		int anaCost = 3 * taskWorkerAna.getProjectCollaboratorFromTaskCollaborator().getCollaboratorCost();

		totalCost = danielCost + jonnyCost + mikeCost + anaCost;

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


		taskContainer.saveTask(testTask);
		taskContainer.saveTask(testTask2);


		// Creates a new list of ReporCcost
		List<String> reportedCost = new ArrayList<>();

		// Task1 has a report of 5*10 + 10*20
		reportedCost.add("250.0");
		// Task2 has no reports
		reportedCost.add("0.0");


		assertEquals(reportedCost, controllerCost.calculeReportedCostOfEachTaskController(project));

	}

	@Test
	public void testGetTaskId() {

		Integer projectID = project.getId();

		assertTrue((projectID+".1").equals(controllerCost.getTaskId(project).get(0)));
		assertTrue((projectID+".2").equals(controllerCost.getTaskId(project).get(1)));
	}

}