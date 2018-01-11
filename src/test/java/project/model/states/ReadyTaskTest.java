/**
 * 
 */
package project.model.states;

import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;

public class ReadyTaskTest {

	User testUser;
	ProjectCollaborator testPCollab;
	Task mainTask;
	Task neededTask;
	Calendar estimatedTaskStartDate;
	Calendar taskDeadline;

	/**
	 * Sets up the necessary parameters to do the tests
	 */
	@Before
	public void setUp() throws Exception {

		// Creates the main task to be tested, as well as a task used solely to check
		// the dependencies
		// TODO mainTask = new Task(1, 1, "Task 1");
		// TODO needeTask = new Task(1, 1, "Task 1");

		// Creates the estimated Start Date and estimated Finish Date (Dealine)
		estimatedTaskStartDate = Calendar.getInstance();
		taskDeadline = (Calendar) estimatedTaskStartDate.clone();
		taskDeadline.add(Calendar.DAY_OF_MONTH, 30);

		// Sets up the estimated Start Dates for both tasks
		neededTask.setEstimatedTaskStartDate(estimatedTaskStartDate);
		mainTask.createTaskDependence(neededTask, 5);

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
		estimatedTaskStartDate = null;
		taskDeadline = null;

	}

	/**
	 * Test method for {@link project.model.taskStateInterface.Ready#isValid()}.
	 */
	@Test
	public final void testIsValid() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link project.model.taskStateInterface.Ready#changeToPlanned()}.
	 */
	@Test
	public final void testChangeToPlanned() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link project.model.taskStateInterface.Ready#changeToOnGoing()}.
	 */
	@Test
	public final void testChangeToOnGoing() {
		fail("Not yet implemented"); // TODO
	}

}
