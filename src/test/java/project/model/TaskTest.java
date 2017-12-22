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

/**
 * @author Group 3
 *
 */
public class TaskTest {

	User user1, user2;
	Project myProject;
	Task testTask, testTask2, testTask3;
	ProjectCollaborator collab1, collab2, collab3;
	Calendar estimatedTaskStartDate, taskDeadline;
	TaskCollaborator tWorker1, tWorker2, tWorker3;
	double expectedCost;

	@Before
	public void setUp() {

		user1 = new User("pepe", "huehue@mail.com", "66", "debugger", "1234567");
		user2 = new User("doge", "suchmail@mail.com", "666", "debugger", "1234567");
		myProject = new Project(1, "Projecto 1", "Projecto Abcd", user1);

		collab1 = myProject.createProjectCollaborator(user1, 5);
		collab2 = myProject.createProjectCollaborator(user2, 5);

		tWorker1 = new TaskCollaborator(collab1);
		tWorker2 = new TaskCollaborator(collab2);

		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);

		testTask = new Task(1, 1, "Task 1", 1, estimatedTaskStartDate, taskDeadline, 0);
		testTask2 = new Task(2, 1, "Task 1", 1, estimatedTaskStartDate, taskDeadline, 0);
		testTask3 = new Task(3, 3, "Task Hue", 1, estimatedTaskStartDate, taskDeadline, 0);
	}

	@After
	public void breakDown() {
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
		assertFalse(testTask.isTaskFinished());
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

		assertTrue(testTask.isTaskTeamEmpty());
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
	 * Tests the dependence of Task2 from Task1. This method will create the dependence of testTask2 to testTask and increment 10 days to the estimatedStartDate of Task1. This new date corresponds to the estimatedStartDate of Task2.
	 */
	
	@Test
	public void testDependeceOfTasks() {
		
		//set testTask estimated start date
		Calendar dateTask1= Calendar.getInstance();
		dateTask1.set(2017, Calendar.DECEMBER, 02);
		testTask.setEstimatedTaskStartDate(dateTask1);
		
		//instantiate dependence of Task2 to Task1 in parameter taskDependence and sets the estimated task start date of testTask2 to the estimated task start date of testTask plus 10 days
		testTask2.createTaskDependence(testTask, 10);
		
		//set of the newEstimatedStartDateTestTask2 which corresponds to the estimated task start date of testTask plus 10 days
		Calendar newEstimatedStartDateTestTask2= Calendar.getInstance();
		newEstimatedStartDateTestTask2.set(2017, Calendar.DECEMBER, 12);
		
		assertEquals(newEstimatedStartDateTestTask2,testTask2.getEstimatedTaskStartDate());
		
	}
	
	/**
	 * Tests the dependence of Task2 from Task1. This method will create the dependence of testTask2 to testTask and increment 10 days to the estimatedStartDate of Task1. This new date corresponds to the estimatedStartDate of Task2. The turn of the Year is tested here.
	 */
	
	@Test
	public void testDependeceOfTasksUponChangeOfYear() {
		
		//set testTask estimated start date
		Calendar dateTask1= Calendar.getInstance();
		dateTask1.set(2017, Calendar.DECEMBER, 22);
		testTask.setEstimatedTaskStartDate(dateTask1);
		
		//instantiate dependence of Task2 to Task1 in parameter taskDependence and sets the estimated task start date of testTask2 to the estimated task start date of testTask plus 10 days
		testTask2.createTaskDependence(testTask, 10);
		
		//set of the newEstimatedStartDateTestTask2 which corresponds to the estimated task start date of testTask plus 10 days which corresponds to changing the year from 2017 to 2018
		Calendar newEstimatedStartDateTestTask2= Calendar.getInstance();
		newEstimatedStartDateTestTask2.set(2018, Calendar.JANUARY, 01);
		
		assertEquals(newEstimatedStartDateTestTask2,testTask2.getEstimatedTaskStartDate());
		
	}
	
	
	

}