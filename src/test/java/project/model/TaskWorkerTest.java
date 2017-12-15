package project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TaskWorkerTest {

	User userTester;
	User userTester2;
	ProjectCollaborator collabTester;
	ProjectCollaborator collabTester2;
	TaskCollaborator workerTester;
	Task task1;
	Calendar finishDate;
	Calendar estimatedStartDate;
	Calendar taskDeadline;

	@Before public
	void setUp() {
		userTester = new User("myname", "myemail", "myidnumber", "myfunction", "myphone");
		userTester2 = new User("myname2", "myemail2", "myidnumber2", "myfunction2", "myphone2");
		collabTester = new ProjectCollaborator(userTester, 5);
		collabTester2 = new ProjectCollaborator(userTester2, 10);
		workerTester = new TaskCollaborator(collabTester);
		finishDate = Calendar.getInstance();
		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.set(2017, Calendar.JANUARY, 14);
		taskDeadline = Calendar.getInstance();
		taskDeadline.set(2017, Calendar.NOVEMBER, 17);
		task1 = new Task(111, 222, "Task 1", 50, estimatedStartDate, taskDeadline, 2000);

	}

	@After public
	void tearDown() {
		userTester = null;
		collabTester = null;
		workerTester = null;
	}

	/**
	 * Tests if the user info is correctly added to the task worker
	 */
	@Test public
	final void testUserInformationInTaskWorker() {
		assertEquals(userTester, workerTester.getTaskWorker());
	}

	/**
	 * Tests the method to get a user from a task worker
	 */
	@Test public
	final void testGetTaskWorker() {
		assertEquals(userTester, workerTester.getTaskWorker());
	}

	/**
	 * Tests the method to get a project collaborator from a taskworker
	 */
	@Test public
	final void testGeProjectCollaborator() {
		assertEquals(collabTester, workerTester.getProjectCollaboratorFromTaskWorker());
	}

	/**
	 * Tests if the worker is active in the task
	 */
	@Test public
	final void testIsCollaboratorInTask() {
		assertTrue(workerTester.isTaskWorkerActiveInTask());
	}

	/**
	 * Tests if the worker is NOT active in the task
	 */
	@Test public
	final void testIsCollaboratorNotInTask() {
		workerTester.addFinishDateForTaskWorker();
		assertFalse(workerTester.isTaskWorkerActiveInTask());
	}

	/**
	 * Tests the set and get finish date methods
	 */
	@Test public
	final void testGetFinishDate() {

		workerTester.addFinishDateForTaskWorker();
		finishDate = Calendar.getInstance();
		assertEquals(workerTester.getFinishDate(), finishDate);

	}

	/**
	 * Tests the isTaskWorkerActiveInTask. Must result true.
	 */
	@Test public
	final void isTaskWorkerActiveInTask_true() {

		assertTrue(workerTester.isTaskWorkerActiveInTask());
	}

	/**
	 * Tests the isTaskWorkerActiveInTask. Must result false.
	 */
	@Test public
	final void isTaskWorkerActiveInTask_false() {

		task1.getTaskTeam().add(workerTester);
		workerTester.addFinishDateForTaskWorker();

		assertFalse(workerTester.isTaskWorkerActiveInTask());
	}

	/**
	 * Tests the isProjectCollaboratorInTaskWorker method. The result must be true.
	 */
	@Test public
	final void isProjectCollaboratorInTaskWorker_true() {

		assertTrue(workerTester.isProjectCollaboratorInTaskWorker(collabTester));

	}

	/**
	 * Tests the isProjectCollaboratorInTaskWorker method. The result must be false;
	 */
	@Test public
	final void isProjectCollaboratorInTaskWorker_false() {

		assertFalse(workerTester.isProjectCollaboratorInTaskWorker(collabTester2));

	}

	/**
	 * Tests if changing email changes in all classes
	 */

	@Test public
	final void testChangeEmail() {

		// Creates a new email address
		String newEmail = new String("newEmail@gmail.com");

		// Sets the new email on userTester
		userTester.setEmail(newEmail);

		assertEquals(newEmail,
				workerTester.getProjectCollaboratorFromTaskWorker().getCollaboratorUserData().getEmail());

	}

}
