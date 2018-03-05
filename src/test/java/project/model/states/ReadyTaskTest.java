/**
 * 
 */
package project.model.states;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;
import project.model.taskstateinterface.Finished;
import project.model.taskstateinterface.Planned;
import project.model.taskstateinterface.Ready;
import project.model.taskstateinterface.TaskStateInterface;

import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReadyTaskTest {

	User testUser;
	ProjectCollaborator testPCollab;
	Task mainTask;
	Task neededTask;
	Calendar estimatedTaskStartDateMainTask;
	Calendar estimatedTaskStartDateNeededTask;
	Calendar taskDeadline;
	Calendar taskDeadline2;

	TaskStateInterface readyState;
	TaskStateInterface plannedState;
	TaskStateInterface finishedState;

	/**
	 * Sets up the necessary parameters to do the tests
	 */
	@Before
	public void setUp() throws Exception {

		// Creates the main task to be tested, as well as a task used solely to check
		// the dependencies
		mainTask = new Task(1, 1, "Task 1");
		neededTask = new Task(2, 2, "Task 1");

		// Creates the estimated Start Dates and estimated Finish Date (Dealine)
		estimatedTaskStartDateMainTask = Calendar.getInstance();
		estimatedTaskStartDateNeededTask = (Calendar) estimatedTaskStartDateMainTask.clone();
		estimatedTaskStartDateNeededTask.add(Calendar.DAY_OF_MONTH, -5);
		taskDeadline = (Calendar) estimatedTaskStartDateMainTask.clone();
		taskDeadline.add(Calendar.DAY_OF_MONTH, 30);

		// Sets up the estimated Start Dates for both tasks
		mainTask.setEstimatedTaskStartDate(estimatedTaskStartDateMainTask);
		neededTask.setEstimatedTaskStartDate(estimatedTaskStartDateNeededTask);
		taskDeadline2 = Calendar.getInstance();
		taskDeadline2.add(Calendar.DAY_OF_YEAR, 10);
		neededTask.setTaskDeadline(taskDeadline2);

		mainTask.createTaskDependence(neededTask, 10);

		// Sets some of the conditions that should already be met by the task when it is
		// ready
		mainTask.setEstimatedTaskEffort(5);
		mainTask.setTaskBudget(100);
		mainTask.setTaskDeadline(taskDeadline);

		// Creates user and adds it to the task to complete the necessary conditions for
		// a task to be ready
		testUser = new User("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000");
		testPCollab = new ProjectCollaborator(testUser, 5);
		mainTask.addProjectCollaboratorToTask(testPCollab);

		// Creates the states
		readyState = new Ready(mainTask);
		plannedState = new Planned(neededTask);

		// Sets the states for the tasks
		mainTask.setTaskState(readyState);
		neededTask.setTaskState(plannedState);
	}

	/**
	 * All objects are nullified
	 */
	@After
	public void tearDown() throws Exception {
		testUser = null;
		testPCollab = null;
		mainTask = null;
		neededTask = null;
		estimatedTaskStartDateMainTask = null;
		estimatedTaskStartDateNeededTask = null;
		taskDeadline = null;
		readyState = null;
		plannedState = null;
		finishedState = null;

	}

	/**
	 * Tests if the isValid method returns false when the task still has ongoing
	 * dependencies
	 */

	@Test
	public final void testIsNotValid() {
		assertFalse(mainTask.getTaskState().isValid());
	}

	/**
	 * Tests if the isValid method returns true when the task doesn't have ongoing
	 * dependencies
	 */

	@Test
	public final void testIsValid() {
		finishedState = new Finished(neededTask);
		neededTask.setTaskState(finishedState);
		assertTrue(mainTask.getTaskState().isValid());
	}

	/**
	 * Tests if the task fails to change to created
	 */

	@Test
	public final void testChangeToCreatedFailed() {
		// Should fail as a ready task shouldn't change to created
		mainTask.getTaskState().changeToCreated();
		assertFalse(mainTask.viewTaskStateName().equals("Created"));
	}

	/**
	 * Tests if the task changes to planned
	 */

	@Test
	public final void testChangeToPlanned() {

		// Removes the user from the task team
		mainTask.removeProjectCollaboratorFromTask(testPCollab);
		// Changes the state to planned and tests it
		mainTask.getTaskState().changeToPlanned();
		assertTrue(mainTask.viewTaskStateName().equals("Planned"));
	}

	/**
	 * Tests if the task fails to change to planned
	 */

	@Test
	public final void testChangeToPlannedFailed() {
		// Should fail as there is still a user in the task team
		mainTask.getTaskState().changeToPlanned();
		assertFalse(mainTask.viewTaskStateName().equals("Planned"));
	}

	/**
	 * Tests if the task fails to change to assigned
	 */

	@Test
	public final void testChangeToAssignedFailed() {
		// Should fail as a ready task shouldn't change to assigned
		mainTask.getTaskState().changeToAssigned();
		assertFalse(mainTask.viewTaskStateName().equals("Assigned"));
	}

	/**
	 * Tests if the task "fails" to change to ready
	 */

	@Test
	public final void testChangeToReadyFailed() {
		// Should not fail nor succeed per se, as it's the same state as the one the
		// task is already in
		mainTask.getTaskState().changeToReady();
		assertTrue(mainTask.viewTaskStateName().equals("Ready"));
	}

	/**
	 * Tests if the task changes to ongoing
	 */

	@Test
	public final void testChangeToOnGoing() {
		// Sets a start date
		mainTask.setStartDate(Calendar.getInstance());
		// Finishes the needed task so the main task has no dependencies
		finishedState = new Finished(neededTask);
		neededTask.setTaskState(finishedState);
		// Changes the state to ongoing and tests it
		mainTask.getTaskState().changeToOnGoing();
		assertTrue(mainTask.viewTaskStateName().equals("OnGoing"));
	}

	/**
	 * Tests if the task fails to change to ongoing
	 */

	@Test
	public final void testChangeToOnGoingFailed() {
		// Should fail as the task doesn't have a start date and has active dependencies
		mainTask.getTaskState().changeToOnGoing();
		assertFalse(mainTask.viewTaskStateName().equals("OnGoing"));
	}

	/**
	 * Tests if the task fails to change to standby
	 */

	@Test
	public final void testChangeToStandByFailed() {
		// Should fail as a ready task shouldn't change to standby
		mainTask.getTaskState().changeToStandBy();
		assertFalse(mainTask.viewTaskStateName().equals("StandBy"));
	}

	/**
	 * Tests if the task fails to change to cancelled
	 */

	@Test
	public final void testChangeToCancelledFailed() {
		// Should fail as a ready task shouldn't change to cancelled
		mainTask.getTaskState().changeToCancelled();
		assertFalse(mainTask.viewTaskStateName().equals("Cancelled"));
	}

	/**
	 * Tests if the task fails to change to finished
	 */

	@Test
	public final void testChangeToFinishedFailed() {
		// Should fail as a ready task shouldn't change to finished
		mainTask.getTaskState().changeToAssigned();
		assertFalse(mainTask.viewTaskStateName().equals("Finished"));
	}
}