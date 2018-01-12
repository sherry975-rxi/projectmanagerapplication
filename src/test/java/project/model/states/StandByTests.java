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
import project.model.taskStateInterface.OnGoing;
import project.model.taskStateInterface.StandBy;

public class StandByTests {

	Company myCompany;
	ProjectRepository myProjRep;
	User user1, user2;
	Project myProject;
	Task testTask, testTask2, testTask3;
	ProjectCollaborator collab1, collab2;
	Calendar estimatedTaskStartDate, taskDeadline;
	TaskCollaborator tWorker1, tWorker2;
	double expectedCost;
	StandBy stateTestTask1;
	StandBy stateTestTask2;
	OnGoing stateTestTask3;
	String stateToCompare;
	Calendar taskStartDate;
	Calendar taskFinishDate;

	@Before
	public void setUp() {

		// Creates a Company
		myCompany = Company.getTheInstance();

		// Creates a Project Repository
		myProjRep = myCompany.getProjectsRepository();

		// Creates a new User1
		user1 = new User("pepe", "huehue@mail.com", "66", "debugger", "1234567");

		// Creates a new User2
		user2 = new User("doge", "suchmail@mail.com", "666", "debugger", "1234567");

		// Creates a project, being user1 the projectCollaborator
		myProject = new Project(1, "Projecto 1", "Projecto Abcd", user1);

		// Adds a project to the project repository
		myProjRep.addProjectToProjectRepository(myProject);

		// Creates 2 project collaborators
		collab1 = myProject.createProjectCollaborator(user1, 5);
		collab2 = myProject.createProjectCollaborator(user2, 5);

		// Creates 2 Task Workers
		tWorker1 = new TaskCollaborator(collab1);
		tWorker2 = new TaskCollaborator(collab2);

		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);

		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();

		// Sets the start Date for the project
		myProject.setStartdate(projStartDate);

		// create a Task Start Date
		taskStartDate = Calendar.getInstance();
		taskStartDate.set(Calendar.YEAR, 2018);
		taskStartDate.set(Calendar.MONTH, Calendar.JANUARY);
		taskStartDate.set(Calendar.DAY_OF_MONTH, 04);
		taskStartDate.set(Calendar.HOUR_OF_DAY, 14);

		// creates a task finish date
		taskFinishDate = Calendar.getInstance();
		taskStartDate.set(Calendar.YEAR, 2018);
		taskStartDate.set(Calendar.MONTH, Calendar.FEBRUARY);
		taskStartDate.set(Calendar.DAY_OF_MONTH, 04);
		taskStartDate.set(Calendar.HOUR_OF_DAY, 14);

		// Creates 3 different Tasks instances
		testTask = new Task(1, 1, "Task 1", 1, estimatedTaskStartDate, taskDeadline, 0);
		testTask2 = new Task(2, 1, "Task 1", 1, estimatedTaskStartDate, taskDeadline, 0);
		testTask3 = new Task(3, 3, "Task Hue", 1, estimatedTaskStartDate, taskDeadline, 0);

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
		tWorker1 = null;
		tWorker2 = null;
		expectedCost = 0;
		stateTestTask1 = null;
		stateTestTask2 = null;
		taskStartDate = null;
		taskFinishDate = null;

	}

	@Test
	public void testStandBy() {
	}

	// Tests if the transition to the StandBy state is valid
	@Test
	public void testIsValid() {
		// Initiates stateTestTask1
		stateTestTask1 = new StandBy(testTask);

		/*
		 * stateTask1 doesn't have any active user, so the method isValid will return
		 * true;
		 */

		assertTrue(stateTestTask1.isValid());

		// Two Project Collaborator are added to the task

		testTask.addProjectCollaboratorToTask(collab1);
		testTask.addProjectCollaboratorToTask(collab2);

		/*
		 * stateTask1 has two active users, so the method will return true
		 */
		assertFalse(stateTestTask1.isValid());

		/*
		 * stateTask1 has now one active user
		 */
		testTask.removeProjectCollaboratorFromTask(collab1);

		/*
		 * stateTask1 still has an active user, so the method will return false
		 */
		assertFalse(stateTestTask1.isValid());

		/*
		 * stateTask1 removed the last active user from Task
		 */
		testTask.removeProjectCollaboratorFromTask(collab2);

		/*
		 * 
		 * stateTask1 has no longer active users, so the method will return true
		 */

		assertTrue(stateTestTask1.isValid());

	}

	public void testChangeToCreated() {
	}

	public void testChangeToPlanned() {
	}

	public void testChangeToAssigned() {
	}

	public void testChangeToReady() {
	}

	//
	@Test
	public void testChangeToOnGoing() {

		// Initiates stateTestTask1
		stateTestTask1 = new StandBy(testTask);
		stateTestTask3 = new OnGoing(testTask);

		testTask.setStartDate(taskStartDate);
		testTask.setTaskState(stateTestTask1);

		/*
		 * Tests if its possible to change to state OnGoing. Expects true
		 */
		assertTrue(stateTestTask1.isTransitionToOnGoingPossible());
		String stateToCompare = "StandBy";

		/*
		 * State won't change, because task doesn't have active users
		 */
		stateTestTask1.changeToOnGoing();
		assertEquals(stateToCompare, testTask.viewTaskStateName());

		/*
		 * Adds two ProjectCollaborators to the task,
		 * 
		 */
		testTask.addProjectCollaboratorToTask(collab1);
		testTask.addProjectCollaboratorToTask(collab2);

		/*
		 * Remove ProjectCollaborators from task,
		 * 
		 */
		testTask.removeProjectCollaboratorFromTask(collab1);
		testTask.removeProjectCollaboratorFromTask(collab2);

		/*
		 * Tries to change the state of the Task to OnGoing
		 */
		stateTestTask1.changeToOnGoing();

		/*
		 * Task state will still be standby, because users are not active in the project
		 */
		assertEquals(stateToCompare, testTask.viewTaskStateName());

		/*
		 * Adds again two ProjectCollaborators to the task
		 * 
		 */
		testTask.addProjectCollaboratorToTask(collab1);
		testTask.addProjectCollaboratorToTask(collab2);
		/*
		 * Changes String value to "OnGoing"
		 */
		stateToCompare = "OnGoing";

		/*
		 * State will change, because task has now two project collaborators
		 */
		stateTestTask1.changeToOnGoing();
		assertEquals(stateToCompare, testTask.viewTaskStateName());

	}

	@Test
	public void testChangeToStandBy() {
	}

	@Test
	public void testChangeToCancelled() {
		// Initiates stateTestTask1
		stateTestTask1 = new StandBy(testTask);
		testTask.setStartDate(taskStartDate);
		testTask.setTaskState(stateTestTask1);

		/*
		 * Adds two ProjectCollaborators to the task,
		 * 
		 */
		testTask.addProjectCollaboratorToTask(collab1);
		testTask.addProjectCollaboratorToTask(collab2);

		/*
		 * Tests if its possible to change to state Cancelled Expects true
		 */
		assertTrue(stateTestTask1.isTransitionToCancelledPossible());
		String stateToCompare = "Cancelled";

		/*
		 * Tests if its possible to change to state Cancelled
		 */
		testTask.setTaskState(stateTestTask1);
		stateTestTask1.changeToCancelled();
		assertEquals(stateToCompare, testTask.viewTaskStateName());
		// TODO Cancelled Class is still returning false

		// Sets a finish Date to testTask
		testTask.setFinishDate();
		/*
		 * Tests if its possible to change to state Cancelled. Expects False because
		 * TestTask has a finish date
		 */
		assertTrue(stateTestTask1.isTransitionToCancelledPossible());

	}

	@Test
	public void testChangeToFinished() {

		// Initiates stateTestTask1
		stateTestTask1 = new StandBy(testTask);
		stateTestTask1 = new StandBy(testTask);

		testTask.setStartDate(taskStartDate);
		testTask2.setTaskState(stateTestTask1);

		/*
		 * Adds two ProjectCollaborators to the testTask1,
		 * 
		 */
		testTask.addProjectCollaboratorToTask(collab1);
		testTask.addProjectCollaboratorToTask(collab2);

		/*
		 * Adds two ProjectCollaborators to the testTask2
		 * 
		 */
		testTask2.addProjectCollaboratorToTask(collab1);
		testTask2.addProjectCollaboratorToTask(collab2);

		/*
		 * Tests if its possible to change to state Cancelled. Expects true
		 */
		assertTrue(stateTestTask1.isTransitionToFinishedPossible());

		// Creates a new string to compare with result by viewTaskStateName
		String stateToCompare = "StandBy";

		// Sets TaskState to "StandBy"
		testTask.setTaskState(stateTestTask1);

		// Expects the result of the taskState to be "StandBy"
		assertEquals(stateToCompare, testTask.viewTaskStateName());

		// Changes the string values to "Finished"
		stateToCompare = "Finished";

		// Sets a finish date to the task, so it's state can change to finished
		testTask.setFinishDate();

		// Changes the state of the task to finished
		stateTestTask1.changeToFinished();

		// Checks if the task status changed to finished
		assertEquals(stateToCompare, testTask.viewTaskStateName());

	}

	@Test
	public void testIsTransitionToCreatedPossible() {
	}

	@Test
	public void testIsTransitionToPlannedPossible() {
	}

	@Test
	public void testIsTransitionToAssignedPossible() {
	}

	@Test
	public void testIsTransitionToReadyPossible() {
	}

	@Test
	public void testIsTransitionToOnGoingPossible() {
	}

	@Test
	public void testIsTransitionToStandByPossible() {
	}

	@Test
	public void testIsTransitionToCancelledPossible() {
	}

	@Test
	public void testIsTransitionToFinishedPossible() {
	}

}
