package test.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.project.model.ProjectCollaborator;
import main.project.model.TaskWorker;
import main.project.model.User;

class TaskWorkerTest {

	User userTester;
	ProjectCollaborator collabTester;
	TaskWorker workerTester;
	Calendar finishDate;

	@BeforeEach
	void setUp() {
		userTester = new User("myname", "myemail", "myidnumber", "myfunction", "myphone");
		collabTester = new ProjectCollaborator(userTester, 5);
		workerTester = new TaskWorker(collabTester);
		finishDate = Calendar.getInstance();

	}

	@AfterEach
	void tearDown() {
		userTester = null;
		collabTester = null;
		workerTester = null;
	}

	/**
	 * Tests if the user info is correctly added to the task worker
	 */
	@Test
	final void testUserInformationInTaskWorker() {
		assertEquals(userTester, workerTester.getTaskWorker());
	}

	/**
	 * Tests the method to get a user from a task worker
	 */
	@Test
	final void testGetTaskWorker() {
		assertEquals(userTester, workerTester.getTaskWorker());
	}

	/**
	 * Tests the method to get a project collaborator from a taskworker
	 */
	@Test
	final void testGeProjectCollaborator() {
		assertEquals(collabTester, workerTester.getProjectCollaboratorFromTaskWorker());
	}

	/**
	 * Tests if the worker is active in the task
	 */
	@Test
	final void testIsCollaboratorInTask() {
		assertTrue(workerTester.isTaskWorkerActiveInTask());
	}

	/**
	 * Tests if the worker is NOT active in the task
	 */
	@Test
	final void testIsCollaboratorNotInTask() {
		workerTester.addFinishDateForTaskWorker();
		assertFalse(workerTester.isTaskWorkerActiveInTask());
	}

	/**
	 * Tests the set and get finish date methods
	 */
	@Test
	final void testGetFinishDate() {

		workerTester.addFinishDateForTaskWorker();
		finishDate = Calendar.getInstance();
		assertEquals(workerTester.getFinishDate(), finishDate);

	}
}
