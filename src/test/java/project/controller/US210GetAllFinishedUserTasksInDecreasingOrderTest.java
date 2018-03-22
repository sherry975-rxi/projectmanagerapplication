package project.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.*;
import project.model.taskstateinterface.Finished;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = { "project.Services", "project.controller", "project.model" })
public class US210GetAllFinishedUserTasksInDecreasingOrderTest {

	@Autowired
	ProjectService projectContainer;

	@Autowired
	UserService userContainer;

	@Autowired
	TaskService taskService;

	User user1, user2, user3;
	Project project1;
	ProjectCollaborator projCollab1, projCollab2, projCollab3;
	Task task1, task2, task3, task4, task5, task6;
	TaskCollaborator taskCollab1, taskCollab2, taskCollab3, taskCollab4, taskCollab5, taskCollab6;

	@Autowired
	US210GetAllFinishedUserTasksInDecreasingOrderController tasksFiltersController;

	@Before
	public void setUp() {

		// create users in company
		user2 = userContainer.createUser("João", "user2@gmail.com", "001", "Manager", "930025000", "rua doutor antónio",
				"7689-654", "porto", "porto", "portugal");
		user1 = userContainer.createUser("Juni", "user3@gmail.com", "002", "Code Monkey", "930000000",
				"rua engenheiro joão", "789-654", "porto", "porto", "portugal");

		// change profiles of users from VISITOR (default) to COLLABORATOR
		user2.setUserProfile(Profile.COLLABORATOR);
		user1.setUserProfile(Profile.COLLABORATOR);

		userContainer.addUserToUserRepositoryX(user2);
		userContainer.addUserToUserRepositoryX(user1);

		// create project 1 in company 1
		project1 = projectContainer.createProject("name3", "description4", user2);

		// add costPerEffort to users in project 1, resulting in a Project Collaborator
		// for each one
		projCollab1 = projectContainer.createProjectCollaborator(user1, project1, 250);
		projCollab2 = projectContainer.createProjectCollaborator(user2, project1, 120);

		// add project 1 to company 1
		projectContainer.addProjectToProjectContainer(project1);

		// create an estimated Task Start Date
		Calendar estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.add(Calendar.YEAR, -1);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 25);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create an estimated Task Dead line Date, from earliest to latest
		Calendar taskDeadlineDateTest1 = Calendar.getInstance();
		taskDeadlineDateTest1.add(Calendar.YEAR, 1);
		taskDeadlineDateTest1.set(Calendar.MONTH, Calendar.JANUARY);

		Calendar taskDeadlineDateTest2 = Calendar.getInstance();
		taskDeadlineDateTest2.add(Calendar.YEAR, 1);
		taskDeadlineDateTest2.set(Calendar.MONTH, Calendar.FEBRUARY);

		Calendar taskDeadlineDateTest3 = Calendar.getInstance();
		taskDeadlineDateTest3.add(Calendar.YEAR, 1);
		taskDeadlineDateTest3.set(Calendar.MONTH, Calendar.MARCH);

		Calendar taskDeadlineDateTest4 = Calendar.getInstance();
		taskDeadlineDateTest4.add(Calendar.YEAR, 1);
		taskDeadlineDateTest4.set(Calendar.MONTH, Calendar.APRIL);

		// create a Date before to the previous Dead line created in order to result in
		// an expired Task
		Calendar taskExpiredDeadlineDateTest = Calendar.getInstance();
		taskExpiredDeadlineDateTest.add(Calendar.YEAR, -1);
		taskExpiredDeadlineDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		taskExpiredDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskExpiredDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		int taskEffortAndBudget = 10;

		// create tasks in project 1
		task1 = taskService.createTask("Do this", project1);

		task2 = taskService.createTask("Do that", project1);
		task3 = taskService.createTask("Merge everything", project1);

		task4 = taskService.createTask("Do this", project1);
		task5 = taskService.createTask("Do this", project1);
		task6 = taskService.createTask("Do this", project1);

		// defines finish date to task1, and mark it as Finished7. This task has the
		// earliest deadline
		task1.setTaskBudget(taskEffortAndBudget);
		task1.setEstimatedTaskEffort(taskEffortAndBudget);
		task1.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task1.setTaskDeadline(taskDeadlineDateTest1);
		task1.addProjectCollaboratorToTask(projCollab1);

		Calendar startDateTask1 = estimatedTaskStartDateTest;
		startDateTask1.add(Calendar.DAY_OF_MONTH, 60);
		task1.setStartDate(startDateTask1);
		task1.markTaskAsFinished();
		task1.setTaskState(new Finished());
		task1.setCurrentState(StateEnum.FINISHED);

		// defines finish date to task1, finished second, forces its statte as finished
		task2.setTaskBudget(taskEffortAndBudget);
		task2.setEstimatedTaskEffort(taskEffortAndBudget);
		task2.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task2.setTaskDeadline(taskDeadlineDateTest1);
		task2.addProjectCollaboratorToTask(projCollab1);

		Calendar startDateTask2 = estimatedTaskStartDateTest;
		startDateTask2.add(Calendar.DAY_OF_MONTH, 60);
		task2.setStartDate(startDateTask1);
		task2.markTaskAsFinished();
		task2.setTaskState(new Finished());
		task2.setCurrentState(StateEnum.FINISHED);

		//
		task3.setTaskBudget(taskEffortAndBudget);
		task3.setEstimatedTaskEffort(taskEffortAndBudget);
		task3.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task3.setTaskDeadline(taskDeadlineDateTest1);
		task3.addProjectCollaboratorToTask(projCollab1);

		Calendar startDateTask3 = estimatedTaskStartDateTest;
		startDateTask3.add(Calendar.DAY_OF_MONTH, 60);
		task3.setStartDate(startDateTask1);
		task3.markTaskAsFinished();
		task3.setTaskState(new Finished());
		task3.setCurrentState(StateEnum.FINISHED);

		task4.setTaskBudget(taskEffortAndBudget);
		task4.setEstimatedTaskEffort(taskEffortAndBudget);
		task4.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task4.setTaskDeadline(taskDeadlineDateTest1);

		task5.setTaskBudget(taskEffortAndBudget);
		task5.setEstimatedTaskEffort(taskEffortAndBudget);
		task5.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task5.setTaskDeadline(taskDeadlineDateTest1);

		task6.setTaskBudget(taskEffortAndBudget);
		task6.setEstimatedTaskEffort(taskEffortAndBudget);
		task6.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task6.setTaskDeadline(taskDeadlineDateTest1);

		taskService.saveTask(task1);
		taskService.saveTask(task2);
		taskService.saveTask(task3);
		taskService.saveTask(task4);
		taskService.saveTask(task5);

		projectContainer.updateProjectCollaborator(projCollab1);
		projectContainer.updateProjectCollaborator(projCollab2);

		// creates the controller
		tasksFiltersController.setUser(user1);
	}

	/**
	 * US210 - Como colaborador, eu pretendo obter uma lista das tarefas que
	 * concluí, ordenadas por ordem temporal decrescente.
	 */
	@Test
	public void testGetAllFinishedUserTasksInDecreasingOrder() {

		// Change finish date in tasks
		Calendar finishOverwrite1 = Calendar.getInstance();

		finishOverwrite1.set(Calendar.YEAR, 2018);
		finishOverwrite1.set(Calendar.MONTH, 0);
		finishOverwrite1.set(Calendar.DAY_OF_MONTH, 16);

		task1.setFinishDate(finishOverwrite1);
		taskService.saveTask(task1);

		Calendar finishOverwrite2 = Calendar.getInstance();

		finishOverwrite2.set(Calendar.YEAR, 2018);
		finishOverwrite2.set(Calendar.MONTH, 0);
		finishOverwrite2.set(Calendar.DAY_OF_MONTH, 6);

		task2.setFinishDate(finishOverwrite2);
		taskService.saveTask(task2);

		Calendar finishOverwrite3 = Calendar.getInstance();

		finishOverwrite3.set(Calendar.YEAR, 2018);
		finishOverwrite3.set(Calendar.MONTH, 0);
		finishOverwrite3.set(Calendar.DAY_OF_MONTH, 11);

		task3.setFinishDate(finishOverwrite3);
		taskService.saveTask(task3);

		Integer projectID = project1.getId();

		// Create Strings representing the info shown of each task
		String one = "[" + projectID + ".1] 16/01/2018 - Do this";
		String two = "[" + projectID + ".2] 06/01/2018 - Do that";
		String three = "[" + projectID + ".3] 11/01/2018 - Merge everything";

		assertEquals(one, tasksFiltersController.getAllFinishedUserTasksInDecreasingOrder().get(0));

		assertEquals(three, tasksFiltersController.getAllFinishedUserTasksInDecreasingOrder().get(1));

		assertEquals(two, tasksFiltersController.getAllFinishedUserTasksInDecreasingOrder().get(2));

	}

	/**
	 * Tests the conversion of a date in calendar to a string
	 */
	@Test
	public void testConvertCalendarToString() {
		Calendar date = Calendar.getInstance();
		date.set(2018, 0, 21);

		assertEquals("21/01/2018", tasksFiltersController.convertCalendarToString(date));

	}

	/**
	 * Checks if the name of a user is the same used in the creator.
	 */
	@Test
	public void testPrintUserNameInfo() {
		assertEquals("Juni", tasksFiltersController.printUserNameInfo());
	}

}
