package project.model.states;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;
import project.model.taskstateinterface.*;

import java.util.Calendar;

import static org.junit.Assert.*;

public class CancelledTaskTests {
	Company myCompany;
	ProjectContainer myProjRep;
	User user1, user2;
	Project myProject;
	Task testTask, testTask2, testTask3;
	ProjectCollaborator collab1, collab2, collab3;
	Calendar estimatedTaskStartDate, taskDeadline;
	TaskCollaborator tWorker1, tWorker2, tWorker3;
	double expectedCost;
	TaskStateInterface previousState;
	TaskStateInterface newState;

	@Before
	public void setUp() {

		myCompany = Company.getTheInstance();
		myProjRep = myCompany.getProjectsRepository();

		user1 = new User("pepe", "huehue@mail.com", "66", "debugger", "1234567");
		user2 = new User("doge", "suchmail@mail.com", "666", "debugger", "1234567");
		myProject = new Project(1, "Projecto 1", "Projecto Abcd", user1);

		myProjRep.addProjectToProjectContainer(myProject);

		collab1 = myProject.createProjectCollaborator(user1, 5);
		collab2 = myProject.createProjectCollaborator(user2, 5);

		tWorker1 = new TaskCollaborator(collab1);
		tWorker2 = new TaskCollaborator(collab2);

		testTask = new Task(1, 1, "Tarefa para teste de cancelled state");

		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask.setTaskDeadline(taskDeadline);

		testTask.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask.addProjectCollaboratorToTask(collab2);
		testTask.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setStartDate(projStartDate);
		testTask.getTaskState().changeToOnGoing();

		// Sets a cancel date for testTask
		testTask.setCancelDate();
		// pass from "OnGoing" to "Cancelled"
		testTask.getTaskState().changeToCancelled();

		// assures that the taskTest state is Cancelled
		assertEquals("Cancelled", testTask.viewTaskStateName());

	}

	@After
	public void tearDown() {
		Company.clear();
		myProjRep = null;
		user1 = null;
		user2 = null;
		myProject = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
		collab1 = null;
		collab2 = null;
		collab3 = null;
		tWorker1 = null;
		tWorker2 = null;
		expectedCost = 0;

	}

	/**
	 * This tests if a cancelled task is valid
	 */
	@Test
	public final void testIsValid() {
		assertTrue(testTask.getTaskState().isValid());
	}

	/**
	 * This tests if a cancelled task with finish date is invalid
	 */
	@Test
	public final void testIsNotValid() {
		Calendar testDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setFinishDate(testDate);
		assertEquals("Cancelled", testTask.viewTaskStateName());
		assertFalse(testTask.getTaskState().isValid());
	}

	/**
	 * This tests that a Cancelled task with finish date can change to Finished
	 * state
	 */
	@Test
	public final void testchangeToFinished() {
		Calendar testDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setFinishDate(testDate);
		testTask.getTaskState().changeToFinished();
		assertEquals("Finished", testTask.viewTaskStateName());
	}

	/**
	 * This tests that a Cancelled task without finish date can't change to Finished
	 * state (stands Cancelled)
	 */
	@Test
	public final void testCantChangeToFinished() {
		testTask.getTaskState().changeToFinished();
		Finished taskState = new Finished(testTask);

		assertEquals("Cancelled", testTask.viewTaskStateName());
	}

	@Test
	public final void testChangeToCreated() {
		Cancelled taskState = new Cancelled(testTask);
		assertFalse(taskState.changeToCreated());
	}

	@Test
	public final void testChangeToPlanned() {
		Planned taskState = new Planned(testTask);
		assertFalse(taskState.changeToCreated());
	}

	@Test
	public final void testChangeToAssigned() {
		Assigned taskState = new Assigned(testTask);
		assertFalse(taskState.changeToAssigned());
	}

	@Test
	public final void testChangeToReady() {
		Ready taskState = new Ready(testTask);
		assertFalse(taskState.changeToReady());
	}

	@Test
	public final void testChangeToCancelled() {
		Cancelled taskState = new Cancelled(testTask);
		assertFalse(taskState.changeToReady());
	}
	
	/**
	 * Tests the ability of the task to change state
	 */

	@Test
	public final void testPossibleChanges() {
		// Tests the impossible transitions
		assertFalse(testTask.getTaskState().changeToCreated());
		assertFalse(testTask.getTaskState().changeToPlanned());
		assertFalse(testTask.getTaskState().changeToAssigned());

		assertFalse(testTask.getTaskState().changeToReady());
		assertFalse(testTask.getTaskState().changeToOnGoing());
		assertFalse(testTask.getTaskState().changeToStandBy());
		assertFalse(testTask.getTaskState().changeToCancelled());

	}

}
