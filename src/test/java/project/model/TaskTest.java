/**
 * 
 */
package project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.taskStateInterface.Cancelled;
import project.model.taskStateInterface.Finished;
import project.model.taskStateInterface.StandBy;

/**
 * @author Group 3
 *
 */
public class TaskTest {

	Company myCompany;
	ProjectRepository myProjRep;
	User user1, user2;
	Project myProject;
	Task testTask, testTask2, testTask3;
	ProjectCollaborator collab1, collab2, collab3;
	Calendar estimatedTaskStartDate, taskDeadline;
	TaskCollaborator tWorker1, tWorker2, tWorker3;
	Finished finishedTaskState;
	StandBy standByTaskState;
	Cancelled cancelledTaskState;

	double expectedCost;

	@Before
	public void setUp() {

		myCompany = Company.getTheInstance();
		myProjRep = myCompany.getProjectsRepository();

		user1 = new User("pepe", "huehue@mail.com", "66", "debugger", "1234567");
		user2 = new User("doge", "suchmail@mail.com", "666", "debugger", "1234567");
		myProject = new Project(1, "Projecto 1", "Projecto Abcd", user1);

		myProjRep.addProjectToProjectRepository(myProject);

		collab1 = myProject.createProjectCollaborator(user1, 5);
		collab2 = myProject.createProjectCollaborator(user2, 5);

		myProject.addProjectCollaboratorToProjectTeam(collab1);
		myProject.addProjectCollaboratorToProjectTeam(collab2);

		tWorker1 = new TaskCollaborator(collab1);
		tWorker2 = new TaskCollaborator(collab2);

		Calendar projStartDate = Calendar.getInstance();
		myProject.setStartdate(projStartDate);

		testTask = new Task(1, 1, "Description of task");
		testTask2 = new Task(2, 2, "Description of task");
		testTask3 = new Task(3, 3, "Description of task 3");

	}

	@After
	public void tearDown() {
		Company.clear();
		myProjRep = null;
		user1 = null;
		user2 = null;
		myProject = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
		collab1 = null;
		collab2 = null;
		collab3 = null;
		tWorker1 = null;
		tWorker2 = null;
		expectedCost = 0;

	}

	/**
	 * Tests getDescription method by comparing it against a task with the same one
	 * And afterwards comparing it against a different one
	 */
	@Test
	public void testTaskConstructor() {
		testTask.addTaskCollaboratorToTask(tWorker1);

		assertTrue(testTask.getDescription().equals(testTask2.getDescription()));
		assertFalse(testTask.getDescription().equals(testTask3.getDescription()));
	}

	@Test
	public void testGettersAndSetters() {

		// Creates an int variable
		int valueToCompare = 10;

		// Tests the set and getEstimatedTaskEffort methods
		testTask.setEstimatedTaskEffort(valueToCompare);
		assertEquals(valueToCompare, testTask.getEstimatedTaskEffort());

		// Tests the set and getTaskBudget methods
		testTask.setTaskBudget(valueToCompare);
		assertEquals(valueToCompare, testTask.getTaskBudget());

		// Tests the set and getTaskStartDate methods
		testTask.setEstimatedTaskStartDate(estimatedTaskStartDate);
		assertEquals(estimatedTaskStartDate, testTask.getEstimatedTaskStartDate());

		/*
		 * Task is created in project MyProject, which is the first and only project to
		 * be created in this unitTest, so it starts with one Since testTask is the
		 * first task to be created, the number associated to it its also 1
		 */

		String taskIDToCompare = "1.1";

		assertEquals(testTask.getTaskID(), taskIDToCompare);
	}

	/**
	 * Tests add and remove users to task team: verifying true or false depending on
	 * if they exist) Then compares the Team list with a test List
	 */
	@Test
	public void testTaskTeam() {

		testTask.addTaskCollaboratorToTask(tWorker1);
		testTask.addTaskCollaboratorToTask(tWorker2);
		assertTrue(
				testTask.isProjectCollaboratorInTaskTeam(collab1) && testTask.isProjectCollaboratorInTaskTeam(collab2));
	}

	/**
	 * Checks if clear date is different from a random date
	 */
	@Test
	public void testGetFinishDate() {
		testTask.setFinishDate();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 5);
		assertFalse(testTask.getFinishDate().equals(c));
	}

	/**
	 * First checks if new task is not finished After setting it as finished,
	 * confirms it's indeed finished
	 */
	@Test
	public void testIsFinished() {
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask.setTaskDeadline(taskDeadline);

		testTask.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask.addProjectCollaboratorToTask(collab2);
		testTask.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setStartDate(projStartDate);
		testTask.getTaskState().changeToOnGoing();

		assertFalse(testTask.isTaskFinished());
		testTask.markTaskAsFinished();

		// pass from "OnGoing" to "Finished"
		Calendar testDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setFinishDate(testDate);
		testTask.markTaskAsFinished();

		assertTrue(testTask.isTaskFinished());
	}

	/**
	 * Tests the task equals override
	 */
	@Test
	public void testTaskEquals() {
		assertTrue(testTask.equals(testTask));// same object
		Task testTask4 = null;
		assertFalse(testTask.equals(testTask4));// null object
		assertFalse(testTask.equals(user1));// different classes
		assertFalse(testTask.equals(testTask2));// different counter
		testTask4 = new Task(1, 1, "Task 1", 1, estimatedTaskStartDate, taskDeadline, 0);
		assertTrue(testTask.equals(testTask4));// same counter
	}

	/**
	 * Tests the getTimeSpentOnTaskMethod First with 0 hours (confirming if report
	 * is created with time set to 0), then 15 hours;
	 */
	@Test
	public void testGetTimeSpentOnTask() {
		testTask.addTaskCollaboratorToTask(tWorker1);
		testTask.addTaskCollaboratorToTask(tWorker2);

		testTask.createReport(tWorker1);
		testTask.createReport(tWorker2);

		testTask.getReports().get(1).setReportedTime(15);

		double i = 0;
		double j = 15;

		// checks getTimeSpentOnTask for individual Collaborators
		assertEquals(i, testTask.getTimeSpentByProjectCollaboratorOntask(collab1), 0.01);
		assertEquals(j, testTask.getTimeSpentByProjectCollaboratorOntask(collab2), 0.01);

		// Collab3 doesnt belong to task, so he doesnt have any task assigned, and
		// expected result is 0
		assertEquals(0, testTask.getTimeSpentByProjectCollaboratorOntask(collab3), 0.01);

		// then checks global getTimeSpentOnTask
		assertEquals(j, testTask.getTimeSpentOntask(), 0.01);

	}

	/**
	 * Verifies if Second constructor correctly duplicates tasks; Afterwards, gives
	 * both duplicated tasks different finish dates and confirms difference; Also
	 * confirms if override equals still sees both tasks as the same
	 */
	@Test
	public void testSecondConstructor() {
		// first, clone and compare a task without start date and deadline intervals
		Task testDupe = new Task(testTask);
		assertTrue(testTask.equals(testDupe));

		// second, clone and compare a task with start date and deadline intervals
		testTask.setStartDateInterval(5);
		testTask.setDeadlineInterval(10);
		testDupe = new Task(testTask);
		assertTrue(testTask.equals(testDupe));

		// third, compare that two tasks are the same despite having different start
		// dates
		Calendar startDate1 = Calendar.getInstance();
		Calendar startDateDupe = Calendar.getInstance();
		startDateDupe.add(Calendar.DAY_OF_MONTH, -5);
		testTask.setStartDate(startDate1);
		testDupe.setStartDate(startDateDupe);
		assertTrue(testTask.equals(testDupe));
		assertFalse(testTask.getStartDate().equals(testDupe.getStartDate()));
	}

	@Test
	public void testSecondConstructorNullIntervals() {
		Task testDupe = new Task(testTask);
		assertTrue(testTask.equals(testDupe));
		Calendar startDate1 = Calendar.getInstance();
		Calendar startDateDupe = Calendar.getInstance();
		startDateDupe.add(Calendar.DAY_OF_MONTH, -5);
		testTask.setStartDate(startDate1);
		testDupe.setStartDate(startDateDupe);
		assertTrue(testTask.equals(testDupe));
		assertFalse(testTask.getStartDate().equals(testDupe.getStartDate()));
	}

	/**
	 * Verifies if the task team is empty. Must be true (is empty).
	 */
	@Test
	public void isTaskTeamEmpty_true() {

		assertTrue(testTask2.isTaskTeamEmpty());
	}

	/**
	 * Verifies if the task team is empty. Must be false (not empty).
	 */
	@Test
	public void isTaskTeamEmpty_false() {

		testTask2.addTaskCollaboratorToTask(tWorker1);

		assertFalse(testTask2.isTaskTeamEmpty());
	}

	/**
	 * A test to confirm that the addUserToTask method updates Task Worker info if
	 * the user already worked in a specific task
	 */
	@Test
	public void updateTaskWorker() {
		testTask2.addProjectCollaboratorToTask(collab1);
		testTask2.removeProjectCollaboratorFromTask(collab1);
		testTask2.addProjectCollaboratorToTask(collab1);
		testTask2.createReport(tWorker1);
		testTask2.getReports().get(0).setReportedTime(15);

		assertEquals(5, collab1.getCollaboratorCost());
		assertEquals(15, testTask2.getReports().get(0).getReportedTime());
		assertTrue(tWorker1.getStartDate() != null);
		assertTrue(testTask2.isProjectCollaboratorActiveInTaskTeam(collab1));
	}

	/**
	 * tests if the method to get the reported budget to the task returns the
	 * expected value, according to the taskWorker value and the hours he spent on
	 * that task
	 * 
	 */
	@Test
	public void testGetReportedBudgetToTheTask() {
		// Adds two users to the task
		testTask2.addTaskCollaboratorToTask(tWorker1);
		testTask2.addTaskCollaboratorToTask(tWorker2);

		// sets the hours spent on the task by each user
		testTask2.createReport(tWorker1);
		testTask2.getReports().get(0).setReportedTime(10);
		testTask2.createReport(tWorker2);
		testTask2.getReports().get(1).setReportedTime(5);

		// calculates the expected cost of the task
		expectedCost = 10 * collab1.getCollaboratorCost();
		expectedCost += 5 * collab2.getCollaboratorCost();

		// Checks if the two values are the smae
		assertEquals(expectedCost, testTask2.getTaskCost(), 0.001);

		finishedTaskState = new Finished(testTask2);
		testTask2.setTaskState(finishedTaskState);
		assertFalse(testTask2.createReport(tWorker1));

		assertEquals(testTask2.viewTaskStateName(), "Finished");

	}

	/**
	 * tests if the method to get the reported budget to the task returns the
	 * expected value, according to the taskWorker value and the hours he spent on
	 * that task
	 * 
	 */
	@Test
	public void testTaskTeamHasActiveUsers() {
		// Adds two users to the task
		// Adds two users to the task
		testTask2.addTaskCollaboratorToTask(tWorker1);

		// Checks if task has active users
		assertTrue(testTask2.doesTaskTeamHaveActiveUsers());
		// Set finish date for taskWorker - this method also changes is state to false
		// in the task
		tWorker1.addFinishDateForTaskCollaborator();
		// Checks if the task doesnt have any active user
		assertFalse(testTask2.doesTaskTeamHaveActiveUsers());

	}

	/**
	 * tests the removeProjectCollaboratorFromTask method
	 */

	@Test
	public void testRemoveProjectCollaboratorFromTask() {
		testTask2.addTaskCollaboratorToTask(tWorker1);
		testTask2.removeProjectCollaboratorFromTask(collab1);
		assertFalse(testTask2.doesTaskTeamHaveActiveUsers());

		/*
		 * tries to remove collab1 from testTask. Wont do anything because collab1 was
		 * already deactivated from task
		 */
		testTask2.removeProjectCollaboratorFromTask(collab1);
		assertFalse(testTask2.doesTaskTeamHaveActiveUsers());

		/*
		 * tries to remove collab2 from testTask. Wont do anything because collab2 is
		 * not in task
		 */
		testTask2.removeProjectCollaboratorFromTask(collab2);
		assertFalse(testTask2.doesTaskTeamHaveActiveUsers());
	}

	/**
	 * Tests the dependence of Task2 from Task1. This method will create the
	 * dependence of testTask2 to testTask and increment 10 days to the
	 * estimatedStartDate of Task1. This new date corresponds to the
	 * estimatedStartDate of Task2.
	 */

	@Test
	public void testDependeceOfTasks() {

		// set testTask estimated start date
		Calendar dateTask1 = Calendar.getInstance();
		dateTask1.set(2017, Calendar.DECEMBER, 02);
		dateTask1.set(Calendar.HOUR, 16);
		dateTask1.set(Calendar.MINUTE, 10);
		dateTask1.set(Calendar.SECOND, 10);
		dateTask1.set(Calendar.MILLISECOND, 100);
		testTask.setEstimatedTaskStartDate(dateTask1);

		// Confirms that the task has no dependencies
		assertFalse(testTask2.hasDependencies());

		// instantiate dependence of Task2 to Task1 in parameter taskDependence and sets
		// the estimated task start date of testTask2 to the estimated task start date
		// of testTask plus 10 days

		testTask2.setEstimatedTaskStartDate(Calendar.getInstance());
		testTask2.createTaskDependence(testTask);

		// Confirms that the task has active dependencies
		assertTrue(testTask2.hasActiveDependencies());

		// Task dependencies no longer update Start Date!!!

		// // set of the newEstimatedStartDateTestTask2 which corresponds to the
		// estimated
		// // task start date of testTask plus 10 days
		// Calendar newEstimatedStartDateTestTask2 = Calendar.getInstance();
		// newEstimatedStartDateTestTask2.set(2017, Calendar.DECEMBER, 12);
		// newEstimatedStartDateTestTask2.set(Calendar.HOUR, 16);
		// newEstimatedStartDateTestTask2.set(Calendar.MINUTE, 10);
		// newEstimatedStartDateTestTask2.set(Calendar.SECOND, 10);
		// newEstimatedStartDateTestTask2.set(Calendar.MILLISECOND, 100);
		//
		//
		//
		// assertEquals(newEstimatedStartDateTestTask2.DAY_OF_YEAR,
		// testTask2.getEstimatedTaskStartDate().DAY_OF_YEAR);

	}

	/**
	 * Checks if the estimated task start date is the same that was introduced.
	 */
	@Test
	public void testGetEstimatedStartDateWithoutInterval() {
		assertEquals(estimatedTaskStartDate, testTask.getEstimatedTaskStartDate());
	}

	/**
	 * This test checks if the estimated star date of task was changed because of
	 * the interval that was introduced between the start date of the project and
	 * the task estimated start date.
	 */
	@Test
	public void testGetEstimatedStartDateWithInterval() {

		// adds interval of time between the start date of the project and the task
		// estimated start date.
		testTask.setStartDateInterval(10);

		// simulates the new estimated start date for the task
		Calendar expectedStartDate = (Calendar) myProject.getStartdate().clone();
		expectedStartDate.add(Calendar.DAY_OF_YEAR, 10);

		// compares the simulated date to the one that the method returns
		assertEquals(expectedStartDate.get(Calendar.DAY_OF_YEAR),
				testTask.getEstimatedTaskStartDate().get(Calendar.DAY_OF_YEAR));
	}

	/**
	 * Checks if the estimated task start date is the same that was introduced in
	 * the beginning.
	 */
	@Test
	public void testGetDeadlineWithoutInterval() {
		assertEquals(taskDeadline, testTask.getTaskDeadline());
	}

	/**
	 * This test checks if the estimated deadline date of task was changed because
	 * of the interval that was introduced between the start date of the project and
	 * the task estimated deadline.
	 */
	@Test
	public void testGetDeadlineWithInterval() {

		// adds interval of time between the start date of the project and the task
		// estimated deadline.
		testTask.setDeadlineInterval(15);

		// simulates the new estimated deadline for the task
		Calendar expectedDeadline = (Calendar) myProject.getStartdate().clone();
		expectedDeadline.add(Calendar.DAY_OF_YEAR, 15);

		// compares the simulated date to the one that the method returns
		assertEquals(expectedDeadline.get(Calendar.DAY_OF_YEAR), testTask.getTaskDeadline().get(Calendar.DAY_OF_YEAR));
	}

	/**
	 * Checks if the task deadline interval is the same that was introduced.
	 */
	@Test
	public void testGetDeadlineInterval() {
		testTask.setDeadlineInterval(15);
		assertEquals(15, (int) testTask.getDeadlineInterval());
	}

	/**
	 * Checks if the task deadline date is the same that was introduced.
	 */
	@Test
	public void testTaskDeadline() {
		Calendar newTaskDeadline = Calendar.getInstance();
		newTaskDeadline.add(Calendar.DAY_OF_MONTH, 5);
		testTask2.setTaskDeadline(newTaskDeadline);
		assertEquals(newTaskDeadline, testTask2.getTaskDeadline());
	}

	/**
	 * This test changes the task finish date, and checks the new finish date.
	 */
	@Test
	public void testSetFinishDate() {
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);

		taskDeadline.add(Calendar.DAY_OF_YEAR, -5);
		testTask.setFinishDate(taskDeadline);
		assertEquals(taskDeadline, testTask.getFinishDate());
	}

	/**
	 * Checks if the task start date interval is the same that was introduced.
	 * 
	 */
	@Test
	public void testGetStartDateInterval() {

		testTask.setStartDateInterval(10);
		assertEquals(10, (int) testTask.getStartDateInterval());
	}

	/**
	 * Tests the HashCodes of each Task
	 * 
	 */
	@Test
	public void testHashcode() {

		assertTrue(testTask.hashCode() == testTask.hashCode());
		assertFalse(testTask.hashCode() == testTask2.hashCode());

	}

	/**
	 * Tests the removal of the finish date of testTask
	 */
	@Test
	public void testRemoveFinishDate() {
		testTask.removeFinishDate();
		assertEquals(null, testTask.getFinishDate());
	}

	/**
	 * Tests the change of reported time
	 */
	@Test
	public void testChangeReportedTime() {
		// add three reports to reports's list
		testTask.createReport(tWorker1);
		testTask.createReport(tWorker1);
		testTask.createReport(tWorker1);

		// change reportedTime to 10 of report with index 0
		assertTrue(testTask.changeReportedTime(10, 0));

		// change reportedTime to 20 of report with index 1
		assertTrue(testTask.changeReportedTime(20, 1));

		// asserts that cannot change a reported time if the index is outside of the
		// limit (bigger than list size)
		assertFalse(testTask.changeReportedTime(30, 3));

		// asserts that cannot change a reported time if the index is outside of the
		// limit (less than zero)
		assertFalse(testTask.changeReportedTime(30, -1));

	}

	/**
	 * Tests the get and set of cancel date
	 */
	@Test
	public void testGetCancelDate() {
		// asserts that the testTask don't have a cancel date defined
		assertEquals(testTask.getCancelDate(), null);

		// set the testTask cancel date and then assert that this date exists
		testTask.setCancelDate();
		assertTrue(testTask.getCancelDate() != null);
	}

	/**
	 * Tests if the this method actually empties the task team
	 */
	@Test
	public void testRemoveAllCollaboratorsFromTaskTeam() {
		testTask.addProjectCollaboratorToTask(collab2);
		testTask.removeAllCollaboratorsFromTaskTeam();
		assertFalse(testTask.doesTaskTeamHaveActiveUsers());
	}

	/**
	 * Tests if the this method actually empties the task team
	 */
	@Test
	public void testCreateReportNotPossible() {
		// Adds two users to the task
		testTask2.addTaskCollaboratorToTask(tWorker1);

		finishedTaskState = new Finished(testTask2);
		testTask2.setTaskState(finishedTaskState);
		assertFalse(testTask2.createReport(tWorker1));

		/*
		 * Checks that the tastState is set to Finished
		 */
		assertEquals(testTask2.viewTaskStateName(), "Finished");

		/*
		 * Creates a StandBy State object
		 */
		standByTaskState = new StandBy(testTask2);

		/*
		 * sets testTask2 to StandBy State
		 */
		testTask2.setTaskState(standByTaskState);

		/*
		 * Checks that its not possible to add a report to a task set to "StandBy"
		 */
		assertFalse(testTask2.createReport(tWorker1));

		cancelledTaskState = new Cancelled(testTask2);

		/*
		 * sets testTask2 to Cancelled State
		 */
		testTask2.setTaskState(cancelledTaskState);

		/*
		 * Checks that its not possible to add a report to a task set to "Cancelled"
		 */
		assertFalse(testTask2.createReport(tWorker1));

	}
}