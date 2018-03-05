package project.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class TaskCollaboratorTest {

	User userTester;
	User userTester2;
	ProjectCollaborator collabTester;
	ProjectCollaborator collabTester2;
	TaskCollaborator workerTester;
	TaskCollaborator workerTester2;
	TaskCollaborator workerTester3;
	Task task1;
	Calendar finishDate;
	Calendar estimatedStartDate;
	Calendar taskDeadline;

	@Before
	public void setUp() {
		userTester = new User("myname", "myemail", "myidnumber", "myfunction", "myphone");
		userTester2 = new User("myname2", "myemail2", "myidnumber2", "myfunction2", "myphone2");
		collabTester = new ProjectCollaborator(userTester, 5);
		collabTester2 = new ProjectCollaborator(userTester2, 10);
		workerTester = new TaskCollaborator(collabTester);
		workerTester2 = new TaskCollaborator(collabTester2);
		workerTester3 = new TaskCollaborator(collabTester2);
		finishDate = Calendar.getInstance();
		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.set(2017, Calendar.JANUARY, 14);
		taskDeadline = Calendar.getInstance();
		taskDeadline.set(2017, Calendar.NOVEMBER, 17);
		task1 = new Task(111, 222, "Task 1", 50, estimatedStartDate, taskDeadline, 2000);

	}

	@After
	public void tearDown() {
		userTester = null;
		userTester2 = null;
		collabTester = null;
		collabTester2 = null;
		workerTester = null;
		workerTester2 = null;
		workerTester3 = null;
		finishDate = null;
		estimatedStartDate = null;
		taskDeadline = null;
		task1 = null;

	}

	/**
	 * Tests if the user info is correctly added to the task worker
	 */
	@Test
	public final void testUserInformationInTaskWorker() {
		assertEquals(userTester, workerTester.getTaskCollaborator());
	}

	/**
	 * Tests the method to get a user from a task worker
	 */
	@Test
	public final void testGetTaskWorker() {
		assertEquals(userTester, workerTester.getTaskCollaborator());
	}

	/**
	 * Tests the method to get a project collaborator from a taskworker
	 */
	@Test
	public final void testGeProjectCollaborator() {
		assertEquals(collabTester, workerTester.getProjectCollaboratorFromTaskCollaborator());
	}

	/**
	 * Tests if the worker is active in the task
	 */
	@Test
	public final void testIsCollaboratorInTask() {
		assertTrue(workerTester.isTaskCollaboratorActiveInTask());
	}

	/**
	 * Tests if the worker is NOT active in the task
	 */
	@Test
	public final void testIsCollaboratorNotInTask() {
		workerTester.addFinishDateForTaskCollaborator();
		assertFalse(workerTester.isTaskCollaboratorActiveInTask());
	}

	/**
	 * Tests the set and get finish date methods
	 */
	@Test
	public final void testGetFinishDate() {

		workerTester.addFinishDateForTaskCollaborator();
		finishDate = Calendar.getInstance();
		assertTrue(workerTester.getFinishDate().equals(finishDate));

	}

	/**
	 * Tests the isTaskWorkerActiveInTask. Must result true.
	 */
	@Test
	public final void isTaskWorkerActiveInTask_true() {

		assertTrue(workerTester.isTaskCollaboratorActiveInTask());
	}

	/**
	 * Tests the isTaskWorkerActiveInTask. Must result false.
	 */
	@Test
	public final void isTaskWorkerActiveInTask_false() {

		task1.getTaskTeam().add(workerTester);
		workerTester.addFinishDateForTaskCollaborator();

		assertFalse(workerTester.isTaskCollaboratorActiveInTask());
	}

	/**
	 * Tests the isProjectCollaboratorInTaskWorker method. The result must be true.
	 */
	@Test
	public final void isProjectCollaboratorInTaskWorker_true() {

		assertTrue(workerTester.isProjectCollaboratorInTaskCollaborator(collabTester));

	}

	/**
	 * Tests the isProjectCollaboratorInTaskWorker method. The result must be false;
	 */
	@Test
	public final void isProjectCollaboratorInTaskWorker_false() {

		assertFalse(workerTester.isProjectCollaboratorInTaskCollaborator(collabTester2));

	}

	/**
	 * Tests if changing email changes in all classes
	 */

	@Test
	public final void testChangeEmail() {

		// Creates a new email address
		String newEmail = new String("newEmail@gmail.com");

		// Sets the new email on userTester
		userTester.setEmail(newEmail);

		assertEquals(newEmail,
				workerTester.getProjectCollaboratorFromTaskCollaborator().getUserFromProjectCollaborator().getEmail());

	}

	/**
	 * Tests several combinations of the Equals override
	 * 
	 */
	@Test
	public final void testOverrideEquals() {
		assertTrue(workerTester.equals(workerTester));// same TaskCollaborator
		assertFalse(workerTester.equals(workerTester2));// different user
		workerTester3.addFinishDateForTaskCollaborator();
		assertFalse(workerTester2.equals(workerTester3));// same user, one finished and one active
		assertFalse(workerTester3.equals(workerTester2));// same user, one finished and one active
		assertTrue(workerTester3.equals(workerTester3));// same user with same finish date
		workerTester2.addFinishDateForTaskCollaborator();
		workerTester2.getFinishDate().add(Calendar.DAY_OF_MONTH, -5);
		assertFalse(workerTester3.equals(workerTester2));// same user with different finish date
		TaskCollaborator workerTester4 = null;
		assertFalse(workerTester.equals(workerTester4));// using a null object
		assertFalse(workerTester.equals(userTester));// using an object of a different class
		// sets the finishDate for workerTester3 with the same as workerTester2. They
		// are also the same user
		workerTester3.addFinishDateForTaskCollaborator();
		workerTester3.getFinishDate().add(Calendar.DAY_OF_MONTH, -5);
		// assertTrue(workerTester3.equals(workerTester2));
	}

	/**
	 * Tests Hashcode
	 */
	@Test
	public final void testHashCode() {
		try {
			assertEquals("", workerTester.hashCode());
			Assert.fail("Should have thrown an exception");
		} catch (Exception e) {
			Assert.assertEquals("Exception message must be correct", null, e.getMessage());
		}
	}

}
