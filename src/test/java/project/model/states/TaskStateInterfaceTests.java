package project.model.states;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;
import project.model.taskstateinterface.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TaskStateInterfaceTests {


	User user;
	User userPm;
	Project project;
	Calendar estimatedTaskStartDate, taskDeadline;
	Task taskGetActions;
	Task taskCreated;
	Task taskPlanned;
	Task taskReady;
	Task taskOnGoing;
	Task taskStandBy;
	Task taskFinished;
	Task taskCancelled;
	ProjectCollaborator collaborator;
	ProjectCollaborator collaborator2;

	@Before
	public void setUp() {

		//Creates the users
		user = new User("Joao", "joao@gmail.com", "001", "Junior Programmer",
				"930000000");
		userPm = new User("Daniel", "daniel@gmail.com", "001", "Junior Programmer",
				"930000000");
		//Creates the project
		project = new Project("Teste", "teste", user);
		//Creates the Task
		taskCreated = project.createTask("Task de teste");
		taskPlanned = project.createTask("Task de teste");
		taskReady = project.createTask("Task de teste");
		taskOnGoing = project.createTask("Task de teste");
		taskStandBy = project.createTask("Task de teste");
		taskFinished = project.createTask("Task de teste");
		taskCancelled = project.createTask("Task de teste");
		taskGetActions = project.createTask("actions");
		// create a estimated task date
		estimatedTaskStartDate = Calendar.getInstance();
		// create a dead line date
		taskDeadline = Calendar.getInstance();
		collaborator = project.createProjectCollaborator(user, 10);
		collaborator2 = project.createProjectCollaborator(userPm, 20);

		taskPlanned.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskPlanned.setTaskDeadline(taskDeadline);
		taskPlanned.addProjectCollaboratorToTask(collaborator);
		taskPlanned.addProjectCollaboratorToTask(collaborator2);

		taskReady.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskReady.setTaskDeadline(taskDeadline);
		taskReady.addProjectCollaboratorToTask(collaborator);
		taskReady.setEstimatedTaskEffort(1000);
		taskReady.setTaskBudget(1999);

		taskOnGoing.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskOnGoing.setTaskDeadline(taskDeadline);
		taskOnGoing.addProjectCollaboratorToTask(collaborator);
		taskOnGoing.setEstimatedTaskEffort(1000);
		taskOnGoing.setTaskBudget(1999);
		taskOnGoing.setStartDateAndState(Calendar.getInstance());

		taskStandBy.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskStandBy.setTaskDeadline(taskDeadline);
		taskStandBy.addProjectCollaboratorToTask(collaborator);
		taskStandBy.setEstimatedTaskEffort(1000);
		taskStandBy.setTaskBudget(1999);
		taskStandBy.setStartDateAndState(Calendar.getInstance());
		taskStandBy.removeAllCollaboratorsFromTaskTeam();

		taskFinished.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskFinished.setTaskDeadline(taskDeadline);
		taskFinished.addProjectCollaboratorToTask(collaborator);
		taskFinished.setEstimatedTaskEffort(1000);
		taskFinished.setTaskBudget(1999);
		taskFinished.setStartDateAndState(Calendar.getInstance());
		taskFinished.markTaskAsFinished();

		taskCancelled.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskCancelled.setTaskDeadline(taskDeadline);
		taskCancelled.addProjectCollaboratorToTask(collaborator);
		taskCancelled.setEstimatedTaskEffort(1000);
		taskCancelled.setTaskBudget(1999);
		taskCancelled.setStartDateAndState(Calendar.getInstance());
		taskCancelled.cancelTask();

	}

	@After
	public void tearDown() {
		taskCreated = null;
		taskPlanned = null;
		taskReady = null;
		taskCancelled = null;
		taskFinished = null;
		taskStandBy = null;
		user = null;
		userPm = null;
		project = null;
		estimatedTaskStartDate = null;
		taskDeadline = null;


	}


	/**
	 * GIVEN a task in the created state
	 * WHEN we set the estimated start and finish date and add a collaborator to the team
	 * THEN the task as to change to Planned
	 */
	@Test
	public final void doActionCreated() {

		//GIVEN a task in the created state
		assertEquals("Created", taskCreated.viewTaskStateName());

		//WHEN we set the estimated start and finish date and add a collaborator to the team
		taskCreated.setEstimatedTaskStartDate(estimatedTaskStartDate);

		//THEN the task as to change to Planned
		assertEquals("Planned", taskCreated.viewTaskStateName());
		assertEquals(StateEnum.PLANNED, taskCreated.getCurrentState());

	}

	/**
	 * GIVEN a task in the Planned state
	 * WHEN we set the budget
	 * THEN the task as to change to Ready
	 */
	@Test
	public final void doActionPlannedToReady() {

		//GIVEN a task in the Planned state
		assertEquals("Planned", taskPlanned.viewTaskStateName());

		//WHEN we set the budget that state
		taskPlanned.setTaskBudget(10);
		taskPlanned.setEstimatedTaskEffort(10);

		//THEN the task as to change to Ready
		assertEquals("Ready", taskPlanned.viewTaskStateName());
		assertEquals(StateEnum.READY, taskPlanned.getCurrentState());

	}

	/**
	 * GIVEN a task in the Ready state
	 * WHEN we remove one collaborator
	 * THEN the task is still in the Ready state
	 * AND WHEN we remove all collaborators
	 * THEN the task state changes to Planned
	 */
	@Test
	public final void doActionReadyToPlanned() {

		//GIVEN a task in the Ready stateGIVEN a task in the Planned state
		assertEquals("Ready", taskReady.viewTaskStateName());

		//WHEN we remove all collaborators
		taskReady.removeProjectCollaboratorFromTask(collaborator2);
		assertEquals("Ready", taskReady.viewTaskStateName());


		taskReady.removeProjectCollaboratorFromTask(collaborator);

		//THEN the task as to change to Planned
		assertEquals("Planned", taskReady.viewTaskStateName());
		assertEquals(StateEnum.PLANNED, taskReady.getCurrentState());

	}

	/**
	 * GIVEN a task in the Ready state
	 * WHEN we set a start date
	 * THEN the task as to change to OnGoing
	 */
	@Test
	public final void doActionReadyToOnGoing() {

		//GIVEN a task in the Ready state
		assertEquals("Ready", taskReady.viewTaskStateName());

		//WHEN we set a start date
		taskReady.setStartDateAndState(Calendar.getInstance());

		// THEN the task as to change to OnGoing
		assertEquals("OnGoing", taskReady.viewTaskStateName());
		assertEquals(StateEnum.ONGOING, taskReady.getCurrentState());

	}

	/**
	 * GIVEN a task in the onGoing state
	 * WHEN we remove all collaborators
	 * THEN the task as to change to StandBy
	 */
	@Test
	public final void doActionOngoingToStandby() {

		//GIVEN a task in the onGoing state
		assertEquals("OnGoing", taskOnGoing.viewTaskStateName());

		//WHEN we remove all collaborators
		taskOnGoing.removeAllCollaboratorsFromTaskTeam();

		//THEN the task as to change to StandBy
		assertEquals("StandBy", taskOnGoing.viewTaskStateName());
		assertEquals(StateEnum.STANDBY, taskOnGoing.getCurrentState());

	}

	/**
	 * GIVEN a task in the OnGoing state
	 * WHEN we mark as finished
	 * THEN the task as to change to finished
	 */
	@Test
	public final void doActionOnGoingToFinished() {

		//GIVEN a task in the onGoing state
		assertEquals("OnGoing", taskOnGoing.viewTaskStateName());

		//WHEN we mark task as finished
		taskStandBy.markTaskAsFinished();

		//THEN the task as to change to StandBy
		assertEquals("Finished", taskStandBy.viewTaskStateName());
		assertEquals(StateEnum.FINISHED, taskStandBy.getCurrentState());
		assertTrue(taskStandBy.getFinishDate() != null);
	}


	/**
	 * GIVEN a task in the OnGoing state
	 * WHEN we mark as cancelled
	 * THEN the task as to change to cancelled
	 */
	@Test
	public final void doActionOnGoingToCancelled() {

		//GIVEN a task in the onGoing state
		assertEquals("OnGoing", taskOnGoing.viewTaskStateName());

		//WHEN we mark task as cancelled
		taskOnGoing.cancelTask();

		//THEN the task as to change to StandBy
		assertEquals("Cancelled", taskOnGoing.viewTaskStateName());
		assertEquals(StateEnum.CANCELLED, taskOnGoing.getCurrentState());
	}

	/**
	 * GIVEN a task in the StandBy state
	 * WHEN we mark as finished
	 * THEN the task as to change to finished
	 */
	@Test
	public final void doActionStandByToFinished() {

		//GIVEN a task in the onGoing state
		assertEquals("StandBy", taskStandBy.viewTaskStateName());

		//WHEN we mark task as finished
		taskStandBy.markTaskAsFinished();

		//THEN the task as to change to StandBy
		assertEquals("Finished", taskStandBy.viewTaskStateName());
		assertEquals(StateEnum.FINISHED, taskStandBy.getCurrentState());
		assertTrue(taskStandBy.getFinishDate() != null);
	}

	/**
	 * GIVEN a task in the StandBy state
	 * WHEN we mark as Cancelled
	 * THEN the task as to change to Cancelled
	 */
	@Test
	public final void doActionStandByToCancelled() {

		//GIVEN a task in the StandBy state
		assertEquals("StandBy", taskStandBy.viewTaskStateName());

		//WHEN we mark task as Cancelled
		taskStandBy.cancelTask();

		//THEN the task as to change to Cancelled
		assertEquals("Cancelled", taskStandBy.viewTaskStateName());
		assertEquals(StateEnum.CANCELLED, taskStandBy.getCurrentState());
	}

	/**
	 * GIVEN a task in the StandBy state
	 * WHEN we add collaborators to task
	 * THEN the task as to change to OnGoing
	 */
	@Test
	public final void doActionStandByToOnGoing() {

		//GIVEN a task in the StandBy state
		assertEquals("StandBy", taskStandBy.viewTaskStateName());

		//WHEN we mark task as Cancelled
		taskStandBy.addProjectCollaboratorToTask(collaborator);

		//THEN the task as to change to Cancelled
		assertEquals("OnGoing", taskStandBy.viewTaskStateName());
		assertEquals(StateEnum.ONGOING, taskStandBy.getCurrentState());
	}

	/**
	 * GIVEN a task in the Finished state
	 * WHEN we mark the task as unfinished
	 * THEN the task as to change to OnGoing
	 */
	@Test
	public final void doActionFinishedToOnGoing() {

		//GIVEN a task in the StandBy state
		assertEquals("Finished", taskFinished.viewTaskStateName());

		//WHEN we mark task as Cancelled
		taskFinished.addProjectCollaboratorToTask(collaborator);
		taskFinished.markAsOnGoing();

		//THEN the task as to change to Cancelled
		assertEquals("OnGoing", taskFinished.viewTaskStateName());
		assertEquals(StateEnum.ONGOING, taskFinished.getCurrentState());
	}

	/**
	 * GIVEN a task in the Finished state
	 * WHEN we mark the task as finished
	 * THEN the task as to change to Finished state
	 */
	@Test
	public final void doActionCancelledToFinished() {

		//GIVEN a task in the StandBy state
		assertEquals("Cancelled", taskCancelled.viewTaskStateName());
		assertTrue(taskCancelled.isProjectCollaboratorActiveInTaskTeam(collaborator));
		assertTrue(taskCancelled.getFinishDate() == null);

		//WHEN we mark task as Cancelled
		taskCancelled.markTaskAsFinished();


		//THEN the task as to change to Cancelled
		assertEquals("Finished", taskCancelled.viewTaskStateName());
		assertEquals(StateEnum.FINISHED, taskCancelled.getCurrentState());
		assertFalse(taskCancelled.isProjectCollaboratorActiveInTaskTeam(collaborator));
		assertTrue(taskCancelled.getFinishDate() != null);
	}

	/**
	 * Tests the state interface, by calling all the actions that may provoce a state change from created to planned.
	 */
	@Test
	public final void plannedStateValidation() {

		//GIVEN A NEW TASK
		//Asserts that the task state is created
		assertEquals("Created", taskCreated.viewTaskStateName());
		assertEquals(StateEnum.CREATED,  taskCreated.getCurrentState());

		// WHEN adding someone to the task team
		ProjectCollaborator collaborator = project.createProjectCollaborator(user, 10);
		taskCreated.addProjectCollaboratorToTask(collaborator);

		//THEN the state changes to Planned
		assertEquals("Planned", taskCreated.viewTaskStateName());
		assertEquals(StateEnum.PLANNED,  taskCreated.getCurrentState());

		//AND WHEN all collaborators are removed from the team and the task reset to created
		taskCreated.removeAllCollaboratorsFromTaskTeam();
		taskCreated.setTaskState(new project.model.taskstateinterface.Created());
		taskCreated.setCurrentState(StateEnum.CREATED);
		assertEquals("Created", taskCreated.viewTaskStateName());
		assertEquals(StateEnum.CREATED, taskCreated.getCurrentState());

		//THEN adding an expected start or finish date must move the task to planned as well
		taskCreated.setEstimatedTaskStartDate(estimatedTaskStartDate);
		assertEquals("Planned", taskCreated.viewTaskStateName());
		assertEquals(StateEnum.PLANNED,  taskCreated.getCurrentState());

		//AND WHEN the expected start date is removed and the task reset to created (again)
		taskCreated.setEstimatedTaskStartDate(null);
		taskCreated.setTaskState(new project.model.taskstateinterface.Created());
		taskCreated.setCurrentState(StateEnum.CREATED);
		assertEquals("Created", taskCreated.viewTaskStateName());
		assertEquals(StateEnum.CREATED,  taskCreated.getCurrentState());

		// THEN adding an expected finish date must move the task to planned once again
		taskCreated.setTaskDeadline(taskDeadline);
		assertEquals("Planned", taskCreated.viewTaskStateName());
		assertEquals(StateEnum.PLANNED,  taskCreated.getCurrentState());



	}



	/**
	 * GIVEN a task in the state created and a list of possible actions
	 * WHEN the method getActions from the task current state is called
	 * THEN a list of actions must be returned and must be equal to the list previously instantiated
	 */
	@Test
	public void getActionsCreatedStateTeste() {

		//GIVEN a task in the state created and a list of possible actions
		taskGetActions.setTaskState(new project.model.taskstateinterface.Created());
		List<String> actions = new ArrayList<>();
		actions.add("1");
		actions.add("2");
		actions.add("3");

		//WHEN the method getActions from its state is called
		taskGetActions.getTaskState().getActions();

		//THEN a list of actions must be returned
		assertEquals(actions, taskGetActions.getTaskState().getActions());
	}

	/**
	 * GIVEN a task in the state created and a list of possible actions
	 * WHEN the method getActions from the task current state is called
	 * THEN a list of actions must be returned and must be equal to the list previously instantiated
	 */
	@Test
	public void getActionsPlannedStateTeste() {

		//GIVEN a task in the state Planned and a list of possible actions
		taskGetActions.setTaskState(new Planned());
		List<String> actions = new ArrayList<>();
		actions.add("1");
		actions.add("2");
		actions.add("5");
		actions.add("6");
		actions.add("3");

		//WHEN the method getActions from its state is called
		taskGetActions.getTaskState().getActions();

		//THEN a list of actions must be returned
		assertEquals(actions, taskGetActions.getTaskState().getActions());
	}

	/**
	 * GIVEN a task in the state created and a list of possible actions
	 * WHEN the method getActions from the task current state is called
	 * THEN a list of actions must be returned and must be equal to the list previously instantiated
	 */
	@Test
	public void getActionsReadyStateTeste() {

		//GIVEN a task in the state Ready and a list of possible actions
		taskGetActions.setTaskState(new Ready());
		List<String> actions = new ArrayList<>();
		actions.add("7");
		actions.add("2");
		actions.add("8");
		actions.add("9");
		actions.add("5");
		actions.add("6");
		actions.add("3");

		//WHEN the method getActions from its state is called
		taskGetActions.getTaskState().getActions();

		//THEN a list of actions must be returned
		assertEquals(actions, taskGetActions.getTaskState().getActions());
	}

	/**
	 * GIVEN a task in the state created and a list of possible actions
	 * WHEN the method getActions from the task current state is called
	 * THEN a list of actions must be returned and must be equal to the list previously instantiated
	 */
	@Test
	public void getActionsOngoingStateTeste() {

		//GIVEN a task in the state OnGoing and a list of possible actions
		taskGetActions.setTaskState(new OnGoing());
		List<String> actions = new ArrayList<>();
		actions.add("2");
		actions.add("3");
		actions.add("5");
		actions.add("6");
		actions.add("9");
		actions.add("10");
		actions.add("11");
		actions.add("12");

		//WHEN the method getActions from its state is called
		taskGetActions.getTaskState().getActions();

		//THEN a list of actions must be returned
		assertEquals(actions, taskGetActions.getTaskState().getActions());
	}

	/**
	 * GIVEN a task in the state created and a list of possible actions
	 * WHEN the method getActions from the task current state is called
	 * THEN a list of actions must be returned and must be equal to the list previously instantiated
	 */
	@Test
	public void getActionsStandBYStateTeste() {

		//GIVEN a task in the state StandBy and a list of possible actions
		taskGetActions.setTaskState(new StandBy());
		List<String> actions = new ArrayList<>();
		actions.add("2");
		actions.add("3");
		actions.add("11");
		actions.add("12");

		//WHEN the method getActions from its state is called
		taskGetActions.getTaskState().getActions();

		//THEN a list of actions must be returned
		assertEquals(actions, taskGetActions.getTaskState().getActions());
	}


	/**
	 * GIVEN a task in the state Finished and a list of possible actions
	 * WHEN the method getActions from the task current state is called
	 * THEN a list of actions must be returned and must be equal to the list previously instantiated
	 */
	@Test
	public void getActionsFinishedStateTeste() {

		//GIVEN a task in the state created and a list of possible actions
		taskGetActions.setTaskState(new Finished());
		List<String> actions = new ArrayList<>();
		actions.add("3");
		actions.add("13");

		//WHEN the method getActions from its state is called
		taskGetActions.getTaskState().getActions();

		//THEN a list of actions must be returned
		assertEquals(actions, taskGetActions.getTaskState().getActions());
	}

	/**
	 * GIVEN a task in the state created and a list of possible actions
	 * WHEN the method getActions from the task current state is called
	 * THEN a list of actions must be returned and must be equal to the list previously instantiated
	 */
	@Test
	public void getActionsCancelledStateTeste() {

		//GIVEN a task in the state Cancelled and a list of possible actions
		taskGetActions.setTaskState(new Cancelled());
		List<String> actions = new ArrayList<>();
		actions.add("3");

		//WHEN the method getActions from its state is called
		taskGetActions.getTaskState().getActions();

		//THEN a list of actions must be returned
		assertEquals(actions, taskGetActions.getTaskState().getActions());
	}



}
