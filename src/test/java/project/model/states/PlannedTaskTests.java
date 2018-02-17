package project.model.states;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;
import project.model.taskstateinterface.Planned;

public class PlannedTaskTests {

	Task testTask;
	Task testTask1;
	Task taskReadyToPlan;
	Task taskReadyToAssigned;
	Planned stateZero;
	Planned stateOne;
	Planned stateTwo;
	Planned stateThree;
	Calendar estimatedTaskStartDate, taskDeadline;
	
	User tester;
	ProjectCollaborator testerCollab;
	
	
	@Before
	public void setUp() {
		// create tasks
		testTask = new Task(1, 1, "Task 1");
		testTask1 = new Task(1, 1, null);
		taskReadyToPlan = new Task(1, 1, "Task 1");
		taskReadyToAssigned = new Task(1, 1, "Task 1");

		// create state Planned with task
		stateZero = new Planned(testTask);
		stateOne = new Planned(testTask1);
		stateTwo = new Planned(taskReadyToPlan);
		stateThree = new Planned(taskReadyToAssigned);
		
		// Sets the task state to planned 
		testTask.setTaskState(stateZero);
		testTask1.setTaskState(stateOne);
		taskReadyToPlan.setTaskState(stateTwo);
		taskReadyToAssigned.setTaskState(stateThree);
		

		// create a estimated task date
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);

		// create a dead line date
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);

		// set estimated task date and dead line date to task
		taskReadyToPlan.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskReadyToPlan.setTaskDeadline(taskDeadline);
		
		taskReadyToAssigned.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskReadyToAssigned.setTaskDeadline(taskDeadline);
		
		// Creates a tester User
		tester = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");
		
		// Add an effort to a User, creating a project collaborator from the tester
		testerCollab = new ProjectCollaborator(tester, 10);
		
		// adds a team member to the task, giving it 1 active member
		taskReadyToAssigned.addProjectCollaboratorToTask(testerCollab);

	}

	@After
	public void tearDown() {
		testTask = null;
		testTask1 = null;
		taskReadyToPlan = null;
		stateZero = null;
		stateOne = null;
		stateTwo = null;
		estimatedTaskStartDate = null;
		taskDeadline = null;
		tester= null;
		testerCollab=null;
		taskReadyToAssigned=null;
		//assignedStateTester=null;

	}
	
	/**
	 * this test verify if the State "Planned" requirements are fulfilled for a
	 * specific Task. The requirements are it has an estimated dates and no users 
	 * working on it.
	 */
	@Test
	public void testIsValid() {
		assertTrue(stateTwo.isValid());
		
	}
	
	/**
	 * this test verify if the State "Planned" requirements are not valid for a
	 * specific Task. The requirements are it has an estimated dates and no users 
	 * working on it.
	 */
	@Test
	public final void testIsNotValid() {

		assertFalse(stateOne.isValid());
	}

	

	/**
	 * This test verifies if the method changeToAssigned changes the state of a Task to
	 * the "Assigned" state using the method viewTaskStateName that returns the name
	 * of the task's current state.
	 */
	@Test
	public void testChangeToAssigned() {
		
		stateThree.changeToAssigned();
		

		assertEquals("Assigned", taskReadyToAssigned.viewTaskStateName());
	}
	
	/**
	 * this test verify if the method changeToAssigned cannot changes the state of a
	 * Task to the "Assigned" state using the method viewTaskStateName that returns
	 * the name of the task's current state.
	 */
	@Test
	public final void testCannotChangeToAssigned() {

		stateOne.changeToAssigned();

		assertEquals("Planned", testTask.viewTaskStateName());
	}

}
