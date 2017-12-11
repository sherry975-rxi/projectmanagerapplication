package test.java.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.ProjectCollaborator;
import main.java.project.model.TaskWorker;
import main.java.project.model.User;

class TaskWorkerTest {

	User userTester;
	ProjectCollaborator collabTester;
	TaskWorker workerTester;

	@BeforeEach
	void setUp() {
		userTester = new User("myname", "myemail", "myidnumber", "myfunction", "myphone");
		collabTester = new ProjectCollaborator(userTester, 5);
		workerTester = new TaskWorker(collabTester);
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
	 * Tests if a start date was added
	 */
	@Test
	final void testAddStartDate() {
		workerTester.addStartDateForTaskWorker();
		assertTrue(workerTester.isTaskWorkerActiveInTask());
	}

	/**
	 * Tests if a cost was added
	 */
	@Test
	final void testAddCost() {
		workerTester.addCostForTaskWorker(4);
		;
		assertEquals(4, workerTester.getCost(1));
	}

	/**
	 * Tests if a new hours spent was added, and if it is set correctly as well
	 */
	@Test
	final void testAddHoursSpent() {
		workerTester.addHoursSpentForTaskWorker();
		workerTester.setHoursSpent(5);
		assertEquals(0, workerTester.getHoursSpent(0));
		assertEquals(5, workerTester.getHoursSpent(1));
	}

}
