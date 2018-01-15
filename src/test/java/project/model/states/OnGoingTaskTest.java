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

public class OnGoingTaskTest {

	Company myCompany;
	ProjectRepository myProjectRepository;
	User user1, user2;
	Project myProject;
	Task task1, task2, task3;
	ProjectCollaborator collab1, collab2;
	Calendar estimatedTaskStartDate, taskDeadline;
	TaskCollaborator tWorker1, tWorker2;
	double expectedCost;
	OnGoing OnGoingTask1;
	OnGoing OnGoingTask2;
	String stateToCompare;
	Calendar taskStartDate;
	Calendar taskFinishDate;

	@Before
	public void setUp() {

		// Creates a Company and a project repository within the company
		myCompany = Company.getTheInstance();
		myProjectRepository = myCompany.getProjectsRepository();

		// Creates 3 users: User1 and User2
		user1 = new User("Ana", "ana@company.com", "66", "Dev", "1234567");
		user2 = new User("Nuno", "nuno@mail.com", "666", "Tester", "1234567");

		// Creates a project, being user1 the projectCollaborator
		myProject = new Project(1, "Projecto 1", "Projecto Abcd", user1);

		// Adds a project to the project repository
		myProjectRepository.addProjectToProjectRepository(myProject);

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

		// Creates 3 different Tasks instances
		task1 = new Task(1, 1, "Task 1", 1, estimatedTaskStartDate, taskDeadline, 0);
		task2 = new Task(2, 1, "Task 1", 1, estimatedTaskStartDate, taskDeadline, 0);
		task3 = new Task(3, 3, "Task Hue", 1, estimatedTaskStartDate, taskDeadline, 0);

	}

	@After
	public void tearDown() {
		Company.clear();
		myProjectRepository = null;
		user1 = null;
		user2 = null;
		myProject = null;
		task1 = null;
		task2 = null;
		task3 = null;
		collab1 = null;
		collab2 = null;
		tWorker1 = null;
		tWorker2 = null;
		expectedCost = 0;
		OnGoingTask1 = null;
		OnGoingTask2 = null;
		taskStartDate = null;

	}

	@Test
	public void testOnGoingTask() {

	}

	// Transition into "OnGoing" state is possible is the task has a startDate
	// defined and if the project
	// has active users

	/**
	 * Tests if the isValid method returns false when the task has non active users
	 * and true if the task has active users
	 * 
	 */

	@Test
	public void testIsValid() {

		// task 1 has 0 active users
		OnGoingTask1 = new OnGoing(task1);

		/*
		 * OnGoingTask1 has 0 active users, so the method will return false
		 */
		assertFalse(OnGoingTask1.isValid());

		// Added collaborators to task 1
		task1.addProjectCollaboratorToTask(collab1);
		task1.addProjectCollaboratorToTask(collab2);

		/*
		 * OnGoingTask1 still has 2 active users, but does not have a start date, so it
		 * will return false
		 *
		 */

		assertFalse(OnGoingTask1.isValid());

		/*
		 * OnGoingTask1 still has 2 active users, and has a start date, so it will
		 * return true
		 */
		task1.setStartDate(taskStartDate);

		assertTrue(OnGoingTask1.isValid());

		/*
		 * Sets a Finish Date for task1
		 */

		task1.setFinishDate();

		/*
		 * OnGoingTask1 has 2 active users, has a start date and now has a finish date
		 * so it will return false
		 */

		assertFalse(OnGoingTask1.isValid());

	}

	@Test
	public void testChangeToCreated() {

	}

	@Test
	public void testChangeToPlanned() {

	}

	@Test
	public void testChangeToAssigned() {

	}

	@Test
	public void testChangeToReady() {

	}

	@Test
	public void testChangeToOnGoing() {

	}

	@Test
	public void testChangeToStandBy() {
		// Initiates stateTestTask1
		OnGoingTask1 = new OnGoing(task1);
		task1.setStartDate(taskStartDate);
		task1.setTaskState(OnGoingTask1);

		/*
		 * Adds two ProjectCollaborators to the task,
		 * 
		 */
		task1.addProjectCollaboratorToTask(collab1);
		task2.addProjectCollaboratorToTask(collab2);

		/*
		 * Checks if its possible to change to state "StandBy" from state"OnGoing
		 */
		assertTrue(OnGoingTask1.isTransitionToStandByPossible());

		/*
		 * Creates a String that will be compared with the result of the method
		 * viewTaskStateName()
		 */
		String stateToCompare = "OnGoing";

		/*
		 * Tries to change to state "StandBy. State won't change because
		 * ProjectCollaborators are active in the project
		 */
		OnGoingTask1.changeToStandBy();

		/*
		 * Didn't change state, because projectCollaborators are still active
		 */
		assertEquals(stateToCompare, task1.viewTaskStateName());

		/*
		 * Removed project collaborators from Task
		 */
		task1.removeProjectCollaboratorFromTask(collab1);
		task1.removeProjectCollaboratorFromTask(collab2);

		/*
		 * Tries to change to state "StandBy. State will change because task doesnt have
		 * a finish date and doesn't have any active Project Collaborator in the task
		 */
		OnGoingTask1.changeToStandBy();
		/*
		 * Changes String values to "StandBy"
		 */
		stateToCompare = "StandBy";

		/*
		 * Tests if its possible to change to state "StandBy Expects True. State will
		 * change because project Collaborators are no longer project in the task
		 */
		assertEquals(stateToCompare, task1.viewTaskStateName());

	}

	@Test
	public void testChangeToCancelled() {

		// Initiates stateTestTask1
		OnGoingTask1 = new OnGoing(task1);
		task1.setStartDate(taskStartDate);
		task1.setTaskState(OnGoingTask1);

		/*
		 * Adds two ProjectCollaborators to the task,
		 * 
		 */
		task1.addProjectCollaboratorToTask(collab1);
		task1.addProjectCollaboratorToTask(collab2);

		/*
		 * Checks if its possible to change to state "Cancelled" from state "OnGoing"
		 */
		assertTrue(OnGoingTask1.isTransitionToCancelledPossible());

		/*
		 * Creates a String that will be compared with the result of the method
		 * viewTaskStateName()
		 */
		String stateToCompare = "Cancelled";

		/*
		 * Tries to change to state "Cancelled". State won't change because
		 * ProjectCollaborators are active in the project
		 */
		OnGoingTask1.changeToCancelled();

		/*
		 * The state changed sucessfully
		 */
		assertEquals(stateToCompare, task1.viewTaskStateName());
	}

	@Test
	public void testChangeToFinished() {

		// Initiates stateTestTask1
		OnGoingTask1 = new OnGoing(task1);
		task1.setStartDate(taskStartDate);
		task1.setTaskState(OnGoingTask1);

		/*
		 * Adds two ProjectCollaborators to the task,
		 * 
		 */
		task1.addProjectCollaboratorToTask(collab1);
		task1.addProjectCollaboratorToTask(collab2);

		/*
		 * Checks if its possible to change to state "Finished" from state "OnGoing"
		 */
		assertTrue(OnGoingTask1.isTransitionToFinishedPossible());

		/*
		 * Creates a String that will be compared with the result of the method
		 * viewTaskStateName()
		 */
		String stateToCompare = "OnGoing";

		/*
		 * Tries to change to state "Finished". State won't change because the Task
		 * doesn't have a finish date
		 */
		OnGoingTask1.changeToFinished();

		/*
		 * The state of the task didn't change
		 */
		assertEquals(stateToCompare, task1.viewTaskStateName());

		/*
		 * Sets a finish date for the task
		 */
		task1.setFinishDate();

		/*
		 * Changes string values to "Finished"
		 */
		stateToCompare = "Finished";

		/*
		 * Tries to change to state "Finished". State wll change because the Task has a
		 * finish date
		 */

		OnGoingTask1.changeToFinished();

		/*
		 * The state of the task changed sucessfully
		 */
		assertEquals(stateToCompare, task1.viewTaskStateName());

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
