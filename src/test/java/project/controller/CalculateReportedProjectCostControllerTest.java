package project.controller;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.TaskRepository;
import project.model.User;

public class CalculateReportedProjectCostControllerTest {

	/**
	 * 
	 * This class tests the CalculateReportedProjectCostController class
	 *
	 */
	User user1;
	User user2;
	User user3;
	User user4;
	User projectManager;
	Project project;
	ProjectCollaborator projectUser1;
	ProjectCollaborator projectUser2;
	ProjectCollaborator projectUser3;
	ProjectCollaborator projectUser4;
	ProjectRepository projectRepository;
	TaskRepository taskRepository;
	Task testTask;
	Task testTask2;
	TaskCollaborator taskWorker1;
	TaskCollaborator taskWorker2;
	TaskCollaborator taskWorker3;
	TaskCollaborator taskWorker4;
	CalculateReportedProjectCostController controllerCost;

	double totalCost;

	@Before
	public void setUp() {

		// Creates a CalculateReportedProjectCostController
		controllerCost = new CalculateReportedProjectCostController();

		// create user
		user1 = new User("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000");
		// create user2
		user2 = new User("João", "joao@gmail.com", "001", "Admin", "920000000");

		// create user3
		user3 = new User("Miguel", "miguel@gmail.com", "001", "Admin", "920000000");

		// create user4
		user4 = new User("Ana", "ana@gmail.com", "001", "Admin", "920000000");

		// create projectManager
		projectManager = new User("João", "joao@gmail.com", "001", "Admin", "920000000");

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);
		user3.setUserProfile(Profile.COLLABORATOR);
		user4.setUserProfile(Profile.COLLABORATOR);

		// create project
		project = new Project(0, "name3", "description4", projectManager);

		// creates 4 Project Collaborators and adds them to the project
		projectUser1 = project.createProjectCollaborator(user1, 10);
		projectUser2 = project.createProjectCollaborator(user2, 20);
		projectUser3 = project.createProjectCollaborator(user3, 5);
		projectUser4 = project.createProjectCollaborator(user4, 3);

		project.addProjectCollaboratorToProjectTeam(projectUser1);
		project.addProjectCollaboratorToProjectTeam(projectUser2);
		project.addProjectCollaboratorToProjectTeam(projectUser3);
		project.addProjectCollaboratorToProjectTeam(projectUser4);

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

		// create taskRepository
		taskRepository = project.getTaskRepository();

		testTask = taskRepository.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask2 = taskRepository.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);

		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);

		// Creates 4 Task Workers
		taskWorker1 = testTask.createTaskCollaborator(projectUser1);
		taskWorker2 = testTask2.createTaskCollaborator(projectUser2);
		taskWorker3 = testTask.createTaskCollaborator(projectUser3);
		taskWorker4 = testTask2.createTaskCollaborator(projectUser4);

		// create variable to calculate total cost reported to project
		totalCost = 0.0;

	}

	@After
	public void tearDown() {

		user1 = null;
		user2 = null;
		user3 = null;
		user4 = null;
		projectUser1 = null;
		projectUser2 = null;
		projectUser3 = null;
		projectUser4 = null;
		taskWorker1 = null;
		taskWorker2 = null;
		taskWorker3 = null;
		taskWorker4 = null;
		testTask = null;
		testTask2 = null;
		project = null;
		projectRepository = null;
		taskRepository = null;
		totalCost = 0.0;
		controllerCost = null;

	}

	@Test
	public void calculateReportedProjectCostControllerTest() {

		// Adds users to the respective tasks
		testTask.addTaskCollaboratorToTask(taskWorker1);
		testTask.addTaskCollaboratorToTask(taskWorker2);
		testTask2.addTaskCollaboratorToTask(taskWorker3);
		testTask2.addTaskCollaboratorToTask(taskWorker4);
		// Task worker sets the hours spent on the task
		testTask.createReport(taskWorker1);
		testTask.getReports().get(0).setReportedTime(5);
		testTask.createReport(taskWorker2);
		testTask.getReports().get(0).setReportedTime(10);
		testTask2.createReport(taskWorker3);
		testTask2.getReports().get(0).setReportedTime(2);
		testTask2.createReport(taskWorker4);
		testTask2.getReports().get(0).setReportedTime(3);

		// Calculates the value of the project - Equals to to the sum of the total hours
		// spent times the cost of the TaskWorker

		totalCost += testTask.getReports().get(0).getReportedTime()
				* taskWorker1.getProjectCollaboratorFromTaskCollaborator().getCollaboratorCost();
		totalCost += testTask.getReports().get(1).getReportedTime()
				* taskWorker2.getProjectCollaboratorFromTaskCollaborator().getCollaboratorCost();
		totalCost += testTask2.getReports().get(0).getReportedTime()
				* taskWorker3.getProjectCollaboratorFromTaskCollaborator().getCollaboratorCost();
		totalCost += testTask2.getReports().get(1).getReportedTime()
				* taskWorker4.getProjectCollaboratorFromTaskCollaborator().getCollaboratorCost();

		// Compares the 2 values
		assertEquals(totalCost, controllerCost.calculateReportedProjectCostController(project), 0.01);

	}

}
