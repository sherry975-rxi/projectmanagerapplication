package project.model.states;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import project.model.taskStateInterface.TaskStateInterface;

public class CancelledTaskTests {
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
		testTask.addProjectCollaboratorToTask(collab2);
		/*
		 * testTask2 = new Task(2, 1, "Task 1", 1, estimatedTaskStartDate, taskDeadline,
		 * 0); testTask3 = new Task(3, 3, "Task Hue", 1, estimatedTaskStartDate,
		 * taskDeadline, 0);
		 */

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
	 * When a task is created is state is "Created". In this test, first we try to change the 
	 * state "Created" to "Cancelled", which is not possible. Then, the state of taskTest will
	 * not change (previousState is equal of newState - "Created").
	 */
	@Test
	public final void testIfCreatedTaskCanChangeToCancelled() {
		previousState = testTask.getTaskState();
		previousState.changeToCancelled();
		newState = testTask.getTaskState();
		assertTrue(previousState.equals(newState));
	}

	@Test
	public final void test2() {
		previousState = testTask.getTaskState();
		testTask.setFinishDate();
		testTask.getTaskState().changeToFinished();
		newState = testTask.getTaskState();
		assertTrue(previousState.equals(newState));
	}
	
	/**
	 * This test tests the isValid method of Cancelled state when a task change from OnGoing to Cancelled state.
	 */
	@Test
	public final void testIsValidOnGoingToCancelled() {
		previousState = testTask.getTaskState();
		
		//necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1); 
		taskDeadline = Calendar.getInstance(); 
		taskDeadline.add(Calendar.MONTH, 1);	
		
		previousState.changeToPlanned();
		previousState.changeToAssigned();
		previousState.changeToReady();
		
		//necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		myProject.setStartdate(projStartDate);
		previousState.changeToOnGoing();
		
		//I have a task in state "OnGoing" and I want to pass it to "Cancelled"		
		newState = testTask.getTaskState();
		newState.changeToCancelled();
		
		assertTrue(newState.isValid());
	}
	
	
	/**
	 * This test tests the isValid method of Cancelled state when a task change from StandBy to Cancelled state.
	 */
	@Test
	public final void testIsValidStandByToCancelled() {
		previousState = testTask.getTaskState();
		
		//necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1); 
		taskDeadline = Calendar.getInstance(); 
		taskDeadline.add(Calendar.MONTH, 1);	
		
		previousState.changeToPlanned();
		previousState.changeToAssigned();
		previousState.changeToReady();
		
		//necessary to pass from "Ready" to "StandBy"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		myProject.setStartdate(projStartDate);
		previousState.changeToOnGoing();
		previousState.changeToStandBy();
		
		//I have a task in state "StandBy" and I want to pass it to "Cancelled"		
		newState = testTask.getTaskState();
		newState.changeToCancelled();
		
		assertTrue(newState.isValid());
	}

	/**
	 * This test tests the isValid method of Cancelled state when a task change from StandBy to Cancelled state.
	 */
/*	@Test
	public final void testIfTransitionToFinishedIsPossible() {
		previousState = testTask.getTaskState();
		
		//necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1); 
		taskDeadline = Calendar.getInstance(); 
		taskDeadline.add(Calendar.MONTH, 1);	
		
		previousState.changeToPlanned();
		previousState.changeToAssigned();
		previousState.changeToReady();
		
		//necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		myProject.setStartdate(projStartDate);
		previousState.changeToOnGoing();
		
		//I have a task in state "OnGoing" and I want to pass it to "Cancelled"		
		newState = testTask.getTaskState();
		newState.changeToCancelled();
		
		Calendar projFinishDate = (Calendar) taskDeadline.clone();
		myProject.setStartdate(projFinishDate);
		testTask.setFinishDate();
		
		assertTrue(newState.isTransitionToFinishedPossible());
	}*/

	@Test
	public final void testIfTransitionToStandByIsPossible() {
		previousState = testTask.getTaskState();
		
		//necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1); 
		taskDeadline = Calendar.getInstance(); 
		taskDeadline.add(Calendar.MONTH, 1);	
		
		previousState.changeToPlanned();
		previousState.changeToAssigned();
		previousState.changeToReady();
		
		//necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		myProject.setStartdate(projStartDate);
		previousState.changeToOnGoing();
		
		//I have a task in state "OnGoing" and I want to pass it to "Cancelled"		
		newState = testTask.getTaskState();
		newState.changeToCancelled();
		
		assertFalse(newState.isTransitionToStandByPossible());
	}
	
/*	@Test
	public final void changeToCreatedTest() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void changeToPlannedTest() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void changeToAssignedTest() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void changeToReadyTest() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void changeToOnGoingTest() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void changeToStandByTest() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void changeToCancelledTest() {
		fail("Not yet implemented"); // TODO
	}
		
	@Test
	public final void changeToFinishedTest() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void isTransitionToCreatedPossibleTest() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void isTransitionToPlannedPossibleTest() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void isTransitionToAssignedPossibleTest() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void isTransitionToReadyPossibleTest() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void isTransitionToOnGoingPossibleTest() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void isTransitionToStandByPossibleTest() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void isTransitionToCancelledPossibleTest() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void isTransitionToFinishedPossibleTest() {
		fail("Not yet implemented"); // TODO
	}*/
}
