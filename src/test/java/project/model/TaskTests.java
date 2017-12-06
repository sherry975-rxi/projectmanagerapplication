/**
 * 
 */
package test.java.project.model;

//

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Project;
import main.java.project.model.Task;
import main.java.project.model.User;

/**
 * @author Group 3
 *
 */
class TaskTests {

	User user1, user2;
	Company myComp;
	Project myProject;
	Task testTask, testTask2, testTask3;

	@BeforeEach
	void setUp() {

		myComp = Company.getTheInstance();
		myComp.getUsersList().getAllUsersFromRepository().clear();

		user1 = new User("pepe", "huehue@mail.com", "66", "debugger", "1234567");
		user2 = new User("doge", "suchmail@mail.com", "666", "debugger", "1234567");
		myProject = new Project(1, "Projecto 1", "Projecto Abcd", user1);
		testTask = new Task(1, 2, "Task 1");
		testTask2 = new Task(1, 2, "Task 1");
		testTask3 = new Task(1, 3, "Task Hue");
	}

	@AfterEach
	void breakDown() {
		myComp = null;
		user1 = null;
		user2 = null;
		myProject = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;

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
		assertFalse(testTask.removeUserFromTask(user1));
		assertTrue(testTask.addUserToTask(user1));
		assertFalse(testTask.addUserToTask(user1));
		assertTrue(testTask.addUserToTask(user2));
		assertTrue(testTask.removeUserFromTask(user2));
		assertTrue(testTask.addUserToTask(user2));
		List<User> TestUsers = new ArrayList<User>();
		TestUsers.add(user1);
		TestUsers.add(user2);
		assertTrue(testTask.getTaskTeam().equals(TestUsers));
		assertTrue(testTask.copyListOfUsersInTask(new ArrayList<User>()).equals(TestUsers));
		TestUsers.remove(user1);
		assertFalse(testTask.getTaskTeam().equals(TestUsers));
	}

	/**
	 * Checks if clear date is different from a random date
	 */
	@Test
	void testGetFinishDate() {
		Task testTask = new Task(1, 01, "Task 1");
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
		Task testTask = new Task(1, 01, "Task 1");
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
		Task testTask = new Task(1, 01, "Task 1");
		Task testTask2 = new Task(2, 02, "Task 2");
		assertTrue(testTask.equals(testTask));
		assertFalse(testTask.equals(testTask2));
		assertFalse(testTask.equals(user2));
	}

	/**
	 * Tests the getTimeSpentOnTaskMethod First with a round number of hours - 5
	 * days; Then a decimal one - 5 days and 8 hours; And finally equal start and
	 * finish dates
	 */
	@Test
	void testGetTimeSpentOnTask() {
		Task testTask = new Task(1, 01, "Task 1");
		Calendar finishDate = Calendar.getInstance();
		finishDate.set(Calendar.YEAR, 2017);
		finishDate.set(Calendar.MONTH, Calendar.NOVEMBER);
		finishDate.set(Calendar.DAY_OF_MONTH, 29);
		finishDate.set(Calendar.HOUR_OF_DAY, 14);
		Calendar startDate = (Calendar) finishDate.clone();
		startDate.add(Calendar.DAY_OF_MONTH, -5);
		testTask.setStartDate(startDate);
		testTask.setFinishDate(finishDate);
		assertEquals((testTask.getTimeSpentOnTask()), 24, 0.01);
		startDate.add(Calendar.HOUR_OF_DAY, -5);
		testTask.setStartDate(startDate);
		assertEquals((testTask.getTimeSpentOnTask()), 28, 0.01);
		testTask.setFinishDate(startDate);
		assertEquals((testTask.getTimeSpentOnTask()), 0, 00.1);
	}

	/**
	 * Verifies if Second constructor correctly duplicates tasks; Afterwards, gives
	 * both duplicated tasks different finish dates and confirms difference; Also
	 * confirms if override equals still sees both tasks as the same
	 */
	@Test
	void testSecondConstructor() {
		Task testTask = new Task(1, 01, "Task 1");
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
}