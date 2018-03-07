package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;
import project.model.taskstateinterface.OnGoing;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class US372GetProjectUnfinishedTaskListTest {

	US372GetProjectUnfinishedTaskListController tasksFiltersController;
	Company company1;
	User user1, user2, user3;
	Project project1;
	ProjectCollaborator projCollab1, projCollab2, projCollab3;
	Task task1, task2, task3, task4, task5, task6;
	TaskCollaborator taskCollab1, taskCollab2, taskCollab3, taskCollab4, taskCollab5, taskCollab6;

	@Before
	public void setUp() {
		// create company 1
		company1 = Company.getTheInstance();

		// create users in company
		user2 = company1.getUsersRepository().createUser("João", "user2@gmail.com", "001", "Manager", "930025000",
				"rua doutor antónio", "7689-654", "porto", "porto", "portugal");
		user1 = company1.getUsersRepository().createUser("Juni", "user3@gmail.com", "002", "Code Monkey", "930000000",
				"rua engenheiro joão", "789-654", "porto", "porto", "portugal");

		// change profiles of users from VISITOR (default) to COLLABORATOR
		user2.setUserProfile(Profile.COLLABORATOR);
		user1.setUserProfile(Profile.COLLABORATOR);

		// create project 1 in company 1
		project1 = company1.getProjectsRepository().createProject("name3", "description4", user2);

		// add project 1 to company 1
		company1.getProjectsRepository().addProjectToProjectContainer(project1);

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
		task5 = project1.getTaskRepository().createTask("Do this", 10, estimatedTaskStartDateTest,
				taskDeadlineDateTest4, 10);
		task6 = project1.getTaskRepository().createTask("Do this", 10, estimatedTaskStartDateTest,
				taskExpiredDeadlineDateTest, 10);

		// add tasks to task repository of project 1
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);
		project1.getTaskRepository().addProjectTask(task3);
		project1.getTaskRepository().addProjectTask(task4);
		project1.getTaskRepository().addProjectTask(task5);
		project1.getTaskRepository().addProjectTask(task6);

		// add costPerEffort to users in project 1, resulting in a Project Collaborator
		// for each one
		projCollab1 = project1.createProjectCollaborator(user1, 250);
		projCollab2 = project1.createProjectCollaborator(user2, 120);
		projCollab3 = project1.createProjectCollaborator(user2, 200);

		// associate Project Collaborators to project 1 (info user + costPerEffort)
		project1.addProjectCollaboratorToProjectTeam(projCollab1);
		project1.addProjectCollaboratorToProjectTeam(projCollab2);

		// defines finish date to task, and mark it as Finished7
		task1.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task1.setTaskDeadline(taskDeadlineDateTest1);
		task1.getTaskState().changeToPlanned();
		task1.addProjectCollaboratorToTask(projCollab1);
		task1.getTaskState().changeToAssigned();
		task1.getTaskState().changeToReady();
		Calendar startDateTask1 = estimatedTaskStartDateTest;
		startDateTask1.add(Calendar.DAY_OF_MONTH, 60);
		task1.setStartDate(startDateTask1);
		task1.getTaskState().changeToOnGoing();
		task1.markTaskAsFinished();

		task2.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task2.setTaskDeadline(taskDeadlineDateTest1);
		task2.getTaskState().changeToPlanned();
		task2.addProjectCollaboratorToTask(projCollab1);
		task2.getTaskState().changeToAssigned();
		task2.getTaskState().changeToReady();
		Calendar startDateTask2 = estimatedTaskStartDateTest;
		startDateTask2.add(Calendar.DAY_OF_MONTH, 60);
		task2.setStartDate(startDateTask2);
		task2.getTaskState().changeToOnGoing();
		task2.markTaskAsFinished();

		task3.setStartDate(Calendar.getInstance());
		OnGoing stateTask3 = new OnGoing(task3);
		task3.setTaskState(stateTask3);
		task4.setStartDate(Calendar.getInstance());
		OnGoing stateTask4 = new OnGoing(task4);
		task4.setTaskState(stateTask4);
		task5.setStartDate(Calendar.getInstance());
		OnGoing stateTask5 = new OnGoing(task5);
		task5.setTaskState(stateTask5);
		task6.setStartDate(Calendar.getInstance());
		OnGoing stateTask6 = new OnGoing(task6);
		task6.setTaskState(stateTask6);

		// creates the controller
		tasksFiltersController = new US372GetProjectUnfinishedTaskListController();
	}

	@After
	public void tearDown() {
		Company.clear();
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

	@Test
	public final void testGetProjectUnfinishedTaskList() {
		assertEquals(4, tasksFiltersController.getProjectUnfinishedTaskList(project1).size());
	}

	@Test
	public final void testGetOnGoingTaskListId() {
		String result = "[1.3] Merge everything";
		assertTrue(result.equals(tasksFiltersController.getUnfinishedTaskListId(project1).get(0)));
		result = "[1.4] Do this";
		assertTrue(result.equals(tasksFiltersController.getUnfinishedTaskListId(project1).get(1)));
		result = "[1.5] Do this";
		assertTrue(result.equals(tasksFiltersController.getUnfinishedTaskListId(project1).get(2)));
		result = "[1.6] Do this";
		assertTrue(result.equals(tasksFiltersController.getUnfinishedTaskListId(project1).get(3)));
	}

	@Test
	public final void testSplitStringByFirstSpace() {
		String input = "Test me master!";
		assertTrue("Test".equals(tasksFiltersController.splitStringByFirstSpace(input)));
	}

}
