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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = {"project.services", "project.controllers", "project.model"})
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
		testTask.setStartDateAndState(estimatedTaskStartDateTest);
		testTask2.setStartDateAndState(estimatedTaskStartDateTest);
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

		double danielCost = 5 * taskWorkerDaniel.getProjectCollaboratorFromTaskCollaborator().getCostPerEffort();
		double jonnyCost = 10 * taskWorkerJonny.getProjectCollaboratorFromTaskCollaborator().getCostPerEffort();
		double mikeCost = 2 * taskWorkerMike.getProjectCollaboratorFromTaskCollaborator().getCostPerEffort();
		double anaCost = 3 * taskWorkerAna.getProjectCollaboratorFromTaskCollaborator().getCostPerEffort();

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

	/**
	 *
	 * This test confirms that the set calculation method for report cost works correctly
	 *
	 */
	@Test
	public void setReportCostCalculationMethod() {
		// given 6 start dates
		Calendar sixMonthsAgo = Calendar.getInstance();
		sixMonthsAgo.add(Calendar.MONTH, -6);
		Calendar fiveMonthsAgo = Calendar.getInstance();
		fiveMonthsAgo.add(Calendar.MONTH, -5);
		Calendar fourMonthsAgo = Calendar.getInstance();
		fourMonthsAgo.add(Calendar.MONTH, -4);
		Calendar threeMonthsAgo = Calendar.getInstance();
		threeMonthsAgo.add(Calendar.MONTH, -3);
		Calendar twoMonthsAgo = Calendar.getInstance();
		twoMonthsAgo.add(Calendar.MONTH, -2);
		Calendar oneMonthAgo = Calendar.getInstance();
		oneMonthAgo.add(Calendar.MONTH, -1);

		// and task worker Daniel with a cost per effort set to 10
		// creates a report with 3 hours, from 6 months ago
		projectUserDaniel.setStartDate(sixMonthsAgo);
		testTask.addTaskCollaboratorToTask(taskWorkerDaniel);
		testTask.createReport(taskWorkerDaniel, sixMonthsAgo, 3);

		// when the report is updated to five hours.
		testTask.getReports().get(0).updateReportedTime(5);

		taskContainer.saveTask(testTask);

		// then the report must contain 5 hours effort, and the day of update matching the current day
		assertEquals(5, testTask.getReports().get(0).getReportedTime(), 0.01);
		assertEquals(Calendar.getInstance().get(Calendar.DAY_OF_YEAR), testTask.getReports().get(0).getDateOfUpdate().get(Calendar.DAY_OF_YEAR));


		// when project Collaborator Daniel is readded to the project twice (with different costs)
		assertEquals(1, testTask.getTaskTeam().size());

		projectUserDaniel.setFinishDate(fourMonthsAgo);
		projectUserDaniel.setStatus(false);
		projContainer.updateProjectCollaborator(projectUserDaniel);

        ProjectCollaborator projectUserDaniel2 = projContainer.createProjectCollaborator(userDaniel, project, 25);
        projectUserDaniel2.setStartDate(fourMonthsAgo);
        projectUserDaniel2.setFinishDate(twoMonthsAgo);
        projectUserDaniel2.setStatus(false);
        projContainer.updateProjectCollaborator(projectUserDaniel2);

        ProjectCollaborator projectUserDaniel3 = projContainer.createProjectCollaborator(userDaniel, project, 100);
        projectUserDaniel3.setStartDate(twoMonthsAgo);
        projContainer.updateProjectCollaborator(projectUserDaniel3);


        // then the project team must contain 6 users (active or inactive), 3 instances from Daniel, and 3 from the others

        assertEquals(6, projContainer.getProjectTeam(project).size());

        // and the same collaborator Daniel contains 3 instances in the project during the report's time
        assertEquals(3, taskContainer.getAllCollaboratorInstancesFromReport(testTask.getReports().get(0)).size());

        // when the initial cost is calculated, tehn it must return 50: Daniel's inital cost(10) x the number of hours (5)
        assertEquals(50, controllerCost.calculateReportedProjectCostController(project), 0.01);

        // since the first calculation method (first collaborator instance) is the default one
        controllerCost.selectReportCostCalculation(project, CalculationMethod.CI.getCode());

        // when chose, the cost must remain unchanged
        assertEquals(50, controllerCost.calculateReportedProjectCostController(project), 0.01);


        // when cost is set to last collaborator instance
        controllerCost.selectReportCostCalculation(project, CalculationMethod.CF.getCode());

        // then the cost must be Daniel's last cost (100) x 5
        assertEquals(500, controllerCost.calculateReportedProjectCostController(project), 0.01);


        // when cost is set  to first/last average collaborator cost
        controllerCost.selectReportCostCalculation(project, CalculationMethod.CIFM.getCode());

        // then the cost must be the average of Daniel's fist and last cost, times hours: ((100+10) /2 ) x 5
        assertEquals(275, controllerCost.calculateReportedProjectCostController(project), 0.01);

        // when cost is set is to 4, then the result must be the average of all costs from the user x hours
        controllerCost.selectReportCostCalculation(project, CalculationMethod.CM.getCode());

        // then the total cost must be 225: ((10+25+100)/3)*5
        double average = 225;

        assertEquals(average, controllerCost.calculateReportedProjectCostController(project), 0.01);


    }


	@Test
	public void testGetTaskId() {

		Integer projectID = project.getProjectId();

		assertTrue((projectID+".1").equals(controllerCost.getTaskId(project).get(0)));
		assertTrue((projectID+".2").equals(controllerCost.getTaskId(project).get(1)));
	}

}