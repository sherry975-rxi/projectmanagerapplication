package project.model.states;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Task;
import project.model.taskstateinterface.Created;

public class CreatedTaskTests {

	Task testTask;
	Task testTask1;
	Task taskReadyToPlan;
	Created test;
	Created test1;
	Created test2;
	Calendar estimatedTaskStartDate, taskDeadline;

	@Before
	public void setUp() {
		// create tasks
		testTask = new Task(1, 1, "Task 1");
		testTask1 = new Task(1, 1, null);
		taskReadyToPlan = new Task(1, 1, "Task 1");

		// create Created with task
		test = new Created(testTask);
		test1 = new Created(testTask1);
		test2 = new Created(taskReadyToPlan);

		// create a estimated task date
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);

		// create a dead line date
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);

		// set estimated task date and dead line date to task
		taskReadyToPlan.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskReadyToPlan.setTaskDeadline(taskDeadline);

	}

	@After
	public void tearDown() {
		testTask = null;
		testTask1 = null;
		taskReadyToPlan = null;
		test = null;
		test1 = null;
		test2 = null;
		estimatedTaskStartDate = null;
		taskDeadline = null;

	}

	/**
	 * this test verify if the State "Created" requirements are fulfilled for a
	 * specific Task. The requirements are If the taskID and the description of the
	 * task is not null, the task is created.
	 */
	@Test
	public final void testIsValid() {

		assertTrue(test.isValid());
	}

	/**
	 * this test verify if the State "Created" requirements are not valid for a
	 * specific Task. The requirements are If the taskID and the description of the
	 * task is not null, the task is created.
	 */
	@Test
	public final void testIsNotValid() {

		assertFalse(test1.isValid());
	}

	/**
	 * this test verify if the method changeToPlanned changes the state of a Task to
	 * the "Planned" state using the method viewTaskStateName that returns the name
	 * of the task's current state.
	 */
	@Test
	public final void testChangeToPlanned() {

		test2.changeToPlanned();

		assertEquals("Planned", taskReadyToPlan.viewTaskStateName());
	}

	/**
	 * this test verify if the method changeToPlanned cannot changes the state of a
	 * Task to the "Planned" state using the method viewTaskStateName that returns
	 * the name of the task's current state.
	 */
	@Test
	public final void testCannotChangeToPlanned() {

		test1.changeToPlanned();

		assertEquals("Created", taskReadyToPlan.viewTaskStateName());
	}

	@Test
	public final void testChangesTo() {
		Created stateTask = new Created(testTask);
		assertFalse(stateTask.changeToCreated());
		assertFalse(stateTask.changeToAssigned());
		assertFalse(stateTask.changeToReady());
		assertFalse(stateTask.changeToOnGoing());
		assertFalse(stateTask.changeToStandBy());
		assertFalse(stateTask.changeToCancelled());
		assertFalse(stateTask.changeToFinished());
	}

}
