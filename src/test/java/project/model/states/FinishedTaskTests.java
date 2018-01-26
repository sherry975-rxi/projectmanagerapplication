package project.model.states;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.User;
import project.model.taskStateInterface.Finished;
import project.model.taskStateInterface.TaskStateInterface;

public class FinishedTaskTests {
	Company myCompany;
	ProjectRepository myProjRep;
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

		myProjRep.addProjectToProjectRepository(myProject);

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

		// pass from "OnGoing" to "Finished"
		Calendar testDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setFinishDate(testDate);
		testTask.getTaskState().changeToFinished();

		// assures that the taskTest state is Finished
		assertEquals("Finished", testTask.viewTaskStateName());

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
	 * This tests if a Finished task is valid
	 */
	@Test
	public final void testIsValid() {
		Calendar testDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setFinishDate(testDate);
		assertTrue(testTask.getTaskState().isValid());
	}

	/**
	 * This tests that a Finished task with finish date can't change to OnGoing
	 * state (stands Finished)
	 */
	@Test
	public final void testCantChangeToOngoing() {
		testTask.getTaskState().changeToOnGoing();
		assertEquals("Finished", testTask.viewTaskStateName());
	}

	/**
	 * This tests if a Finished task without finish date is invalid
	 */
	@Test
	public final void testIsNotValid() {
		testTask.removeFinishDate();
		assertEquals("OnGoing", testTask.viewTaskStateName());
		assertTrue(testTask.getTaskState().isValid());
	}

	/**
	 * This tests that a Finished task without finish date can change to OnGoing
	 * state
	 */
	@Test
	public final void testchangeToOngoing() {
		testTask.removeFinishDate();
		testTask.getTaskState().changeToOnGoing();
		assertEquals("OnGoing", testTask.viewTaskStateName());
	}

	@Test
	public final void testChangesTo() {
		Finished stateTask = new Finished(testTask);
		assertFalse(stateTask.changeToCreated());
		assertFalse(stateTask.changeToPlanned());
		assertFalse(stateTask.changeToAssigned());
		assertFalse(stateTask.changeToReady());
		assertFalse(stateTask.changeToCancelled());
		assertFalse(stateTask.changeToFinished());
		assertFalse(stateTask.isTransitionToCreatedPossible());
		assertFalse(stateTask.isTransitionToPlannedPossible());
		assertFalse(stateTask.isTransitionToAssignedPossible());
		assertFalse(stateTask.isTransitionToStandByPossible());
		assertFalse(stateTask.isTransitionToCancelledPossible());

	}
}
