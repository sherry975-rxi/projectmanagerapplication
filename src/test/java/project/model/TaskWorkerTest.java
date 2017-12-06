package test.java.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.TaskWorker;
import main.java.project.model.User;

class TaskWorkerTest {

	User userTester;
	TaskWorker workerTester;

	@BeforeEach
	void setUp() {
		userTester = new User("myname", "myemail", "myidnumber", "myfunction", "myphone");
		workerTester = new TaskWorker(userTester);
	}

	@AfterEach
	void tearDown() {
		userTester = null;
		workerTester = null;
	}

	@Test
	final void testIsCollaboratorInTask() {
		assertTrue(workerTester.isCollaboratorInTask());
	}

	@Test
	final void testIsCollaboratorNotInTask() {
		workerTester.setCollaboratorStateInTask(false);
		assertFalse(workerTester.isCollaboratorInTask());
	}

	@Test
	final void testSetAndGetHoursSpent() {
		assertEquals(0, workerTester.getHoursSpent());
		workerTester.setHoursSpent(15);
		assertEquals(15, workerTester.getHoursSpent());
	}

}
