package project.model.states;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;
import project.model.taskStateInterface.Assigned;
import project.model.taskStateInterface.Finished;
import project.model.taskStateInterface.TaskStateInterface;

public class AssignedTaskTests {

	User tester;
	ProjectCollaborator testerCollab;
	Calendar randomStartDate, randomDeadline, earlierStartDate;
	Task assignedTask, dependency;
	TaskStateInterface assignedStateTester;

	@Before
	public void setUp() {

		// Creates a tester User, as well as a random date
		tester = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		// makes a project collaborator from the tester
		testerCollab = new ProjectCollaborator(tester, 10);

		// creates a random start date and deadline
		// uses it to create one task in the project
		randomStartDate = Calendar.getInstance();
		randomDeadline = Calendar.getInstance();
		randomDeadline.add(Calendar.DAY_OF_YEAR, 5);
		assignedTask = new Task(01, 01, "Assigned Test");
		assignedTask.setEstimatedTaskStartDate(randomStartDate);
		assignedTask.setTaskDeadline(randomDeadline);

		// creates a dependency with an earlier start date
		earlierStartDate = Calendar.getInstance();
		earlierStartDate.add(Calendar.DAY_OF_YEAR, -5);
		dependency = new Task(01, 02, "dependency");
		dependency.setEstimatedTaskStartDate(earlierStartDate);
	}

	@After
	public void tearDown() {
		tester = null;
		testerCollab = null;
		assignedTask = null;
		dependency = null;
		assignedStateTester = null;

	}

	// Given a new Task without dependencies:
	@Test
	public void testValidateAssignedState() {
		// creates the state tester for the task when it has 0 Team Members
		// then asserts its "Assigned" State is invalid
		assignedStateTester = new Assigned(assignedTask);
		assertFalse(assignedStateTester.isValid());

		// adds a team member to the task, giving it 1 active member
		// adds a dependency to the task, giving it one unresolved dependency
		// then asserts its "Assigned" State is valid
		assignedTask.addProjectCollaboratorToTask(testerCollab);
		assignedTask.createTaskDependence(dependency);
		assignedStateTester = new Assigned(assignedTask);
		assertTrue(assignedStateTester.isValid());

		// finally, removes the team member from the task, leaving it with 0 Team
		// members
		// then asserts its "Assigned" State is invalid
		assignedTask.removeProjectCollaboratorFromTask(testerCollab);
		assignedStateTester = new Assigned(assignedTask);
		assertFalse(assignedStateTester.isValid());
	}

	// Given a new Task without dependencies and valid Assigned State
	@Test
	public void testValidateStateChangingChecks() {
		assignedTask.addProjectCollaboratorToTask(testerCollab);
		assignedStateTester = new Assigned(assignedTask);

		// asserts state cannot change to itself
		assertFalse(assignedStateTester.isTransitionToAssignedPossible());

		// asserts state cannot change to any of the following
		assertFalse(assignedStateTester.isTransitionToCreatedPossible());
		assertFalse(assignedStateTester.isTransitionToOnGoingPossible());
		assertFalse(assignedStateTester.isTransitionToStandByPossible());
		assertFalse(assignedStateTester.isTransitionToCancelledPossible());
		assertFalse(assignedStateTester.isTransitionToFinishedPossible());

		// asserts it can only change to Ready or Planned
		assertTrue(assignedStateTester.isTransitionToPlannedPossible());
		assertTrue(assignedStateTester.isTransitionToReadyPossible());
	}

	// Given a new Task with active team (valid Assigned State) and a dependency...
	@Test
	public void testTransitionAssignedToReady() {
		assignedTask.addProjectCollaboratorToTask(testerCollab);
		assignedTask.createTaskDependence(dependency);
		assignedStateTester = new Assigned(assignedTask);
		assignedTask.setTaskState(assignedStateTester);

		// asserts a valid assigned state
		assertTrue(assignedTask.viewTaskStateName().equals("Assigned"));

		// while the task has a dependency, attempts to change its state to Ready
		// then asserts it's state is still Assigned
		assignedStateTester.changeToReady();
		assertTrue(assignedTask.viewTaskStateName().equals("Assigned"));

		// marks the dependent task as Finished before attempting change state
		// then asserts it's state is now Ready
		TaskStateInterface dependencyFinishedState = new Finished(dependency);
		dependency.setTaskState(dependencyFinishedState);
		assignedStateTester.changeToReady();
		assertTrue(assignedTask.viewTaskStateName().equals("Ready"));
	}

	// Given a new Task with active team (valid Assigned State)...
	@Test
	public void testTransitionAssignedToPlanned() {
		assignedTask.addProjectCollaboratorToTask(testerCollab);
		assignedTask.createTaskDependence(dependency);
		assignedStateTester = new Assigned(assignedTask);
		assignedTask.setTaskState(assignedStateTester);

		// asserts a valid assigned state
		assertTrue(assignedTask.viewTaskStateName().equals("Assigned"));

		// while the task still has an active team, attempts to return it to planned
		// then, asserts the state is still Assigned (transition invalid)
		assignedStateTester.changeToPlanned();
		assertTrue(assignedTask.viewTaskStateName().equals("Assigned"));

		// removes the Collaborator from the task, attempts to return it to planned
		// then, asserts the state has changed to planned (transition valid)

		assignedTask.removeProjectCollaboratorFromTask(testerCollab);
		assignedStateTester.changeToPlanned();
		assertTrue(assignedTask.viewTaskStateName().equals("Planned"));

	}
}
