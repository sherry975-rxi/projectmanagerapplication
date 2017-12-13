/**
 * 
 */
package test.project.model;

//

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.project.model.Project;
import main.project.model.ProjectCollaborator;
import main.project.model.Task;
import main.project.model.TaskWorker;
import main.project.model.User;

/**
 * @author Group 3
 *
 */
class TaskTests {

	User user1, user2;
	Project myProject;
	Task testTask, testTask2, testTask3;
	ProjectCollaborator Collab1, Collab2, Collab3;
	TaskWorker tWorker1, tWorker2, tWorker3;
	double expectedCost;

	@BeforeEach
	void setUp() {

		user1 = new User("pepe", "huehue@mail.com", "66", "debugger", "1234567");
		user2 = new User("doge", "suchmail@mail.com", "666", "debugger", "1234567");
		myProject = new Project(1, "Projecto 1", "Projecto Abcd", user1);

		Collab1 = myProject.createProjectCollaborator(user1, 5);
		Collab2 = myProject.createProjectCollaborator(user2, 5);

		tWorker1 = new TaskWorker(Collab1);
		tWorker2 = new TaskWorker(Collab2);

		Calendar estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		Calendar taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);

		testTask = new Task(1, 1, "Task 1", 1, estimatedTaskStartDate, taskDeadline, 0);
		testTask2 = new Task(2, 1, "Task 1", 1, estimatedTaskStartDate, taskDeadline, 0);
		testTask3 = new Task(3, 3, "Task Hue", 1, estimatedTaskStartDate, taskDeadline, 0);
	}

	@AfterEach
	void breakDown() {
		user1 = null;
		user2 = null;
		myProject = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
		Collab1 = null;
		Collab2 = null;
		Collab3 = null;
		tWorker1 = null;
		tWorker2 = null;
		expectedCost = 0;

	}

	/**
	 * Tests getDescription method by comparing it against a task with the same one
	 * And afterwards comparing it against a different one
	 */
	@Test
	void testTaskConstructor() {

		assertTrue(testTask.getDescription().equals(testTask2.getDescription()));
		assertFalse(testTask.getDescription().equals(testTask3.getDescription()));
	}

	/**
	 * Tests add and remove users to task team: verifying true or false depending on
	 * if they exist) Then compares the Team list with a test List
	 */
	@Test
	void testTaskTeam() {

		testTask.addUserToTask(tWorker1);
		testTask.addUserToTask(tWorker2);
		assertTrue(testTask.taskTeamContainsUser(Collab1) && testTask.taskTeamContainsUser(Collab2));
	}

	/**
	 * Checks if clear date is different from a random date
	 */
	@Test
	void testGetFinishDate() {
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
	void testIsFinished() {
		assertFalse(testTask.isFinished());
		testTask.markTaskAsFinished();
		assertTrue(testTask.isFinished());
	}

	/**
	 * Tests the task equals override, first by comparing it with itself(True); Then
	 * with a different one (False); Then compares it with user (false: Not Task)
	 */
	@Test
	void testTaskEquals() {
		assertTrue(testTask.equals(testTask));
		assertFalse(testTask.equals(testTask2));//
		assertFalse(testTask.equals(user2));
	}

	/**
	 * Tests the getTimeSpentOnTaskMethod First with 0 hours (confirming if report
	 * is created with time set to 0), then 15 hours;
	 */
	@Test
	void testGetTimeSpentOnTask() {
		testTask.addUserToTask(tWorker1);
		testTask.addUserToTask(tWorker2);

		testTask.createReport(tWorker1);
		testTask.createReport(tWorker2);

		testTask.getReports().get(1).setReportedTime(15);

		double i = 0;
		double j = 15;

		// checks getTimeSpentOnTask for individual Collaborators
		assertEquals(i, testTask.getTimeSpentOntask(Collab1), 0.01);
		assertEquals(j, testTask.getTimeSpentOntask(Collab2), 0.01);

		// then checks global getTimeSpentOnTask
		assertEquals(j, testTask.getTimeSpentOntask(), 0.01);

	}

	/**
	 * Verifies if Second constructor correctly duplicates tasks; Afterwards, gives
	 * both duplicated tasks different finish dates and confirms difference; Also
	 * confirms if override equals still sees both tasks as the same
	 */
	@Test
	void testSecondConstructor() {
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
	void isTaskTeamEmpty_true() {

		assertTrue(testTask.isTaskTeamEmpty());
	}

	/**
	 * Verifies if the task team is empty. Must be false (not empty).
	 */
	@Test
	void isTaskTeamEmpty_false() {

		testTask2.addUserToTask(tWorker1);

		assertFalse(testTask2.isTaskTeamEmpty());
	}

	/**
	 * A test to confirm that the addUserToTask method updates Task Worker info if
	 * the user already worked in a specific task
	 */
	@Test
	void updateTaskWorker() {
		testTask2.addUserToTask(tWorker1);
		testTask2.removeUserFromTask(tWorker1.getTaskWorker());
		testTask2.addUserToTask(tWorker1);
		tWorker1.setHoursSpent(15);
		assertEquals(5, tWorker1.getCost(1));
		assertEquals(15, tWorker1.getHoursSpent(1));
		assertTrue(tWorker1.getStartDate(1) != null);
		assertTrue(testTask2.taskTeamUserIsActive(tWorker1.getTaskWorker()));
	}

	/**
	 * tests if the method to get the reported budget to the task returns the
	 * expected value, according to the taskWorker value and the hours he spent on
	 * that task
	 * 
	 */
	@Test
	void testGetReportedBudgetToTheTask() {
		// Adds two users to the task
		testTask2.addUserToTask(tWorker1);
		testTask2.addUserToTask(tWorker2);

		// sets the hours spent on the task by each user
		tWorker1.setHoursSpent(10);
		tWorker2.setHoursSpent(5);

		// calculates the expected cost of the task
		expectedCost = 10 * tWorker1.getCost(0);
		expectedCost += 5 * tWorker2.getCost(0);

		// Checks if the two values are the smae
		assertEquals(expectedCost, testTask2.getTaskCost(), 0.001);

	}
}