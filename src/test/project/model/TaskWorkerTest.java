package test.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.project.model.ProjectCollaborator;
import main.project.model.Task;
import main.project.model.TaskWorker;
import main.project.model.User;

class TaskWorkerTest {

	User userTester;
	User userTester2;
	ProjectCollaborator collabTester;
	ProjectCollaborator collabTester2;
	TaskWorker workerTester;
	Task task1;
	Calendar finishDate;
	Calendar estimatedStartDate;
	Calendar taskDeadline;

	@BeforeEach
	void setUp() {
		userTester = new User("myname", "myemail", "myidnumber", "myfunction", "myphone");
		userTester2 = new User("myname2", "myemail2", "myidnumber2", "myfunction2", "myphone2");
		collabTester = new ProjectCollaborator(userTester, 5);
		collabTester2 = new ProjectCollaborator(userTester2, 10);
		workerTester = new TaskWorker(collabTester);
		finishDate = Calendar.getInstance();
		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.set(2017, Calendar.JANUARY, 14);
		taskDeadline = Calendar.getInstance();
		taskDeadline.set(2017, Calendar.NOVEMBER, 17);
		task1 = new Task(111, 222, "Task 1", 50, estimatedStartDate, taskDeadline, 2000);

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

	/**
	 * Tests the isTaskWorkerActiveInTask. Must result true.
	 */
	@Test
	final void isTaskWorkerActiveInTask_true() {

		assertTrue(workerTester.isTaskWorkerActiveInTask());
	}

	/**
	 * Tests the isTaskWorkerActiveInTask. Must result false.
	 */
	@Test
	final void isTaskWorkerActiveInTask_false() {

		task1.getTaskTeam().add(workerTester);
		workerTester.addFinishDateForTaskWorker();

		assertFalse(workerTester.isTaskWorkerActiveInTask());
	}

	/**
	 * Tests the isProjectCollaboratorInTaskWorker method. The result must be true.
	 */
	@Test
	final void isProjectCollaboratorInTaskWorker_true() {

		assertTrue(workerTester.isProjectCollaboratorInTaskWorker(collabTester));

	}

	/**
	 * Tests the isProjectCollaboratorInTaskWorker method. The result must be false;
	 */
	@Test
	final void isProjectCollaboratorInTaskWorker_false() {

		assertFalse(workerTester.isProjectCollaboratorInTaskWorker(collabTester2));

	}

	/**
	 * Tests if changing email changes in all classes
	 */

	@Test
	final void testChangeEmail() {

		// Creates a new email address
		String newEmail = new String("newEmail@gmail.com");

		// Sets the new email on userTester
		userTester.setEmail(newEmail);

		assertEquals(newEmail,
				workerTester.getProjectCollaboratorFromTaskWorker().getCollaboratorUserData().getEmail());

	}

}
