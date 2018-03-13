package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Services.ProjectContainerService;
import project.Services.UserContainerService;
import project.model.*;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class US203GetUserStartedNotFinishedTaskListInIncreasingOrderTest {

	US203GetUserStartedNotFinishedTaskListInIncreasingOrderController tasksFiltersController;
	UserContainerService userContainer;
	ProjectContainerService projectContainer;
	User user1, user2, user3;
	Project project1;
	ProjectCollaborator projCollab1, projCollab2, projCollab3;
	Task task1, task2, task3, task4, task5, task6;
	TaskCollaborator taskCollab1, taskCollab2, taskCollab3, taskCollab4, taskCollab5, taskCollab6;

	@Before
	public void setUp() {

		//Creates an UserContainer
		userContainer = new UserContainerService();

		//Creates a ProjectContainer
		projectContainer = new ProjectContainerService();

		// create users in UserContainer
		user2 = userContainer.createUser("João", "user2@gmail.com", "001", "Manager", "930025000",
				"rua doutor antónio", "7689-654", "porto", "porto", "portugal");
		user1 = userContainer.createUser("Juni", "user3@gmail.com", "002", "Code Monkey", "930000000",
				"rua engenheiro joão", "789-654", "porto", "porto", "portugal");

		// change profiles of users from VISITOR (default) to COLLABORATOR
		user2.setUserProfile(Profile.COLLABORATOR);
		user1.setUserProfile(Profile.COLLABORATOR);

		// create project 1 in company 1
		project1 = projectContainer.createProject("name3", "description4", user2);

		// add project 1 to company 1
		projectContainer.addProjectToProjectContainer(project1);

		// create an estimated Task Start Date
		Calendar estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 25);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create an estimated Task Dead line Date
		Calendar taskDeadlineDateTest1 = Calendar.getInstance();
		taskDeadlineDateTest1.set(Calendar.YEAR, 2019);
		taskDeadlineDateTest1.set(Calendar.MONTH, Calendar.JANUARY);
		Calendar taskDeadlineDateTest2 = Calendar.getInstance();
		taskDeadlineDateTest2.set(Calendar.YEAR, 2019);
		taskDeadlineDateTest2.set(Calendar.MONTH, Calendar.FEBRUARY);
		Calendar taskDeadlineDateTest3 = Calendar.getInstance();
		taskDeadlineDateTest3.set(Calendar.YEAR, 2019);
		taskDeadlineDateTest3.set(Calendar.MONTH, Calendar.MARCH);
		Calendar taskDeadlineDateTest4 = Calendar.getInstance();
		taskDeadlineDateTest4.set(Calendar.YEAR, 2019);
		taskDeadlineDateTest4.set(Calendar.MONTH, Calendar.APRIL);
		Calendar taskDeadlineDateTest6 = Calendar.getInstance();
		taskDeadlineDateTest6.set(Calendar.YEAR, 2019);
		taskDeadlineDateTest6.set(Calendar.MONTH, Calendar.MAY);

		// create a Date before to the previous Dead line created in order to result in
		// an expired Task
		Calendar taskExpiredDeadlineDateTest = Calendar.getInstance();
		taskExpiredDeadlineDateTest.set(Calendar.YEAR, 2017);
		taskExpiredDeadlineDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		taskExpiredDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskExpiredDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create tasks in project 1
		task1 = project1.getTaskRepository().createTask("Do this", 10, estimatedTaskStartDateTest,
				taskDeadlineDateTest1, 10);
		task2 = project1.getTaskRepository().createTask("Do that", 10, estimatedTaskStartDateTest,
				taskDeadlineDateTest2, 10);
		task3 = project1.getTaskRepository().createTask("Merge everything", 10, estimatedTaskStartDateTest,
				taskExpiredDeadlineDateTest, 10);
		task4 = project1.getTaskRepository().createTask("Do this", 10, estimatedTaskStartDateTest,
				taskDeadlineDateTest3, 10);
		task5 = project1.getTaskRepository().createTask("Testing a task with deadlinenull");
		task6 = project1.getTaskRepository().createTask("Do this", 10, estimatedTaskStartDateTest,
				taskExpiredDeadlineDateTest, 10);

		// add tasks to task repository of project 1
		project1.getTaskRepository().addTaskToProject(task1);
		project1.getTaskRepository().addTaskToProject(task2);
		project1.getTaskRepository().addTaskToProject(task3);
		project1.getTaskRepository().addTaskToProject(task4);
		project1.getTaskRepository().addTaskToProject(task5);
		project1.getTaskRepository().addTaskToProject(task6);

		// add costPerEffort to users in project 1, resulting in a Project Collaborator
		// for each one
		projCollab1 = project1.createProjectCollaborator(user1, 250);
		projCollab2 = project1.createProjectCollaborator(user2, 120);
		projCollab3 = project1.createProjectCollaborator(user2, 200);

		// associate Project Collaborators to project 1 (info user + costPerEffort)
		project1.addProjectCollaboratorToProjectTeam(projCollab1);
		project1.addProjectCollaboratorToProjectTeam(projCollab2);

		// set the state of the tasks
		task1.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task1.setTaskDeadline(taskDeadlineDateTest1);

		task2.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task2.setTaskDeadline(taskDeadlineDateTest4);
		task2.addProjectCollaboratorToTask(projCollab1);

		Calendar startDateTask2 = estimatedTaskStartDateTest;
		startDateTask2.add(Calendar.DAY_OF_MONTH, 60);
		task2.setStartDate(startDateTask2);

		task3.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task3.setTaskDeadline(taskDeadlineDateTest1);
		task3.addProjectCollaboratorToTask(projCollab1);

		Calendar startDateTask3 = estimatedTaskStartDateTest;
		startDateTask3.add(Calendar.DAY_OF_MONTH, 60);
		task3.setStartDate(startDateTask3);

		task4.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task4.setTaskDeadline(taskDeadlineDateTest1);
		task4.addProjectCollaboratorToTask(projCollab1);

		task5.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task5.setTaskDeadline(taskDeadlineDateTest1);
		task5.addProjectCollaboratorToTask(projCollab1);
		Calendar startDateTask5 = estimatedTaskStartDateTest;
		startDateTask5.add(Calendar.DAY_OF_MONTH, 60);
		task5.setStartDate(startDateTask5);
		task5.markTaskAsFinished();

		task6.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task6.setTaskDeadline(taskDeadlineDateTest2);
		task6.addProjectCollaboratorToTask(projCollab1);
		Calendar startDateTask6 = estimatedTaskStartDateTest;
		startDateTask6.add(Calendar.DAY_OF_MONTH, 60);
		task6.setStartDate(startDateTask6);

		// creates the controller
		tasksFiltersController = new US203GetUserStartedNotFinishedTaskListInIncreasingOrderController();
	}

	@After
	public void tearDown() {

		userContainer = null;
		projectContainer = null;
		user1 = null;
		user2 = null;
		user3 = null;
		project1 = null;
		projCollab1 = null;
		projCollab2 = null;
		projCollab3 = null;
		task1 = null;
		task2 = null;
		task3 = null;
		task4 = null;
		task5 = null;
		task6 = null;
		taskCollab1 = null;
		taskCollab2 = null;
		taskCollab3 = null;
		taskCollab4 = null;
		taskCollab5 = null;
		taskCollab6 = null;
		tasksFiltersController = null;
	}

	/**
	 * 
	 * US203v02 - Como colaborador, eu pretendo consultar a minha lista de tarefas
	 * iniciadas mas não concluidas de modo a saber o que tenho para fazer hoje. Se
	 * tiverem data limite quero-as ordenadas por ordem crescente de data limite
	 */

	@Test
	public void testGetUserStartedNotFinishedTaskList() {

		// asserts the list contains five tasks, and the first two are the ones with the
		// earliest deadline
		assertEquals(3, tasksFiltersController.getUserStartedNotFinishedTaskListInIncreasingOrder(user1).size());
		assertEquals(tasksFiltersController.getUserStartedNotFinishedTaskListInIncreasingOrder(user1).get(0), task3);
		assertEquals(tasksFiltersController.getUserStartedNotFinishedTaskListInIncreasingOrder(user1).get(1), task6);
		assertEquals(tasksFiltersController.getUserStartedNotFinishedTaskListInIncreasingOrder(user1).get(2), task2);
	}
}