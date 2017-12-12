/**
 * 
 */
package test.java.project.model;

//

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Project;
import main.java.project.model.ProjectCollaborator;
import main.java.project.model.Task;
import main.java.project.model.TaskWorker;
import main.java.project.model.User;

/**
 * @author Group 3
 *
 */
class TaskTests {

	User user1, user2;
	TaskWorker Worker1, Worker2;
	Company myComp;
	Project myProject;
	Task testTask, testTask2, testTask3;
	ProjectCollaborator Collab1, Collab2;
	TaskWorker tWorker1, tWorker2;

	@BeforeEach
	void setUp() {

		myComp = Company.getTheInstance();
		myComp.getUsersRepository();

		user1 = new User("pepe", "huehue@mail.com", "66", "debugger", "1234567");
		user2 = new User("doge", "suchmail@mail.com", "666", "debugger", "1234567");
		Collab1 = new ProjectCollaborator(user1, 5);
		Collab2 = new ProjectCollaborator(user2, 5);
		tWorker1 = new TaskWorker(Collab1);
		tWorker2 = new TaskWorker(Collab2);
		myProject = new Project(1, "Projecto 1", "Projecto Abcd", user1);
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
		myComp = null;
		user1 = null;
		user2 = null;
		myProject = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
		Collab1 = null;
		Collab2 = null;
		tWorker1 = null;
		tWorker2 = null;

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
		assertTrue(testTask.taskTeamContainsUser(Collab1.getCollaboratorUserData())
				&& testTask.taskTeamContainsUser(Collab2.getCollaboratorUserData()));
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
	 * Tests the getTimeSpentOnTaskMethod First with a round number of hours - 5
	 * days; Then a decimal one - 5 days and 8 hours; And finally equal start and
	 * finish dates
	 */
	@Test
	void testGetTimeSpentOnTask() {
		testTask.addUserToTask(tWorker1);
		testTask.addUserToTask(tWorker2);
		testTask.getTaskTeam().get(0).setHoursSpent(20);
		testTask.getTaskTeam().get(1).setHoursSpent(15);

		int i = 20;
		int j = 15;

		assertEquals(i, testTask.getTimeSpentOntask(Collab1.getCollaboratorUserData()));
		assertEquals(j, testTask.getTimeSpentOntask(Collab2.getCollaboratorUserData()));

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
}