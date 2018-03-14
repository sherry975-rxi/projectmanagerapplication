package sprint.one;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import java.util.Calendar;

import static org.junit.Assert.*;

public class US205Tests {

	/**
	 * Tests US205
	 * 
	 * US205: Como colaborador, eu pretendo marcar uma tarefa que consta na minha
	 * lista de tarefas como concluida.
	 * 
	 * uses methods addTaskToProject, addUserToTask, markTaskAsCompleted(),
	 * setFinishDate(), isFinished()
	 * 
	 * 
	 */
	Company myCompany;
	UserContainer userContainer;
	ProjectContainer projectContainer;
	TaskContainer taskContainer;
	ProjectCollaborator projectUser1;
	ProjectCollaborator projectUser2;
	User newUser2;
	User newUser3;
	Project testProj;
	Task testTask;
	Task testTask2;
	int typeOfUser;
	TaskCollaborator taskWorker1;
	TaskCollaborator taskWorker2;
	Calendar estimatedTaskStartDate, taskDeadline;

	@Before
	public void setUp() {
		myCompany = Company.getTheInstance();

		// creates an UserContainer
		userContainer = myCompany.getUsersContainer();

		// creates a ProjectsRepository
		projectContainer = myCompany.getProjectsContainer();

		// create user
		newUser2 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// create user
		newUser3 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// create project
		testProj = projectContainer.createProject("name3", "description4", newUser3);

		// // create a estimated Task Start Date
		// Calendar estimatedTaskStartDateTest = Calendar.getInstance();
		// estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		// estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.DECEMBER);
		// estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 10);
		// estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);
		// // create a estimated Task Start Date
		// Calendar taskDeadlineDateTest = Calendar.getInstance();
		// taskDeadlineDateTest.set(Calendar.YEAR, 2017);
		// taskDeadlineDateTest.set(Calendar.MONTH, Calendar.DECEMBER);
		// taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 20);
		// taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create taskContainer
		taskContainer = testProj.getTaskRepository();

		// creates 2 Project Collaborators
		projectUser1 = testProj.createProjectCollaborator(newUser2, 10);
		projectUser2 = testProj.createProjectCollaborator(newUser3, 10);

		// creates 2 Tasks
		testTask = taskContainer.createTask("Test dis agen pls");
		testTask2 = taskContainer.createTask("Test dis agen pls");

		// Creates 2 Task Workers
		taskWorker1 = testTask.createTaskCollaborator(projectUser1);
		taskWorker2 = testTask2.createTaskCollaborator(projectUser2);

		typeOfUser = 1;
	}

	@After
	public void tearDown() {

		Company.clear();
		userContainer = null;
		projectContainer = null;
		taskContainer = null;
		projectUser1 = null;
		projectUser2 = null;
		newUser2 = null;
		newUser3 = null;
		testProj = null;
		testTask = null;
		testTask2 = null;
		typeOfUser = 0;
		taskWorker1 = null;
		taskWorker2 = null;
		estimatedTaskStartDate = null;
		taskDeadline = null;
	}

	/**
	 * 
	 * This test adds two tasks workers to the task, created previously on the setUp
	 * One of the tasks is mask as finished. Then it asserts to see if the state of
	 * the isFinished field on the task is set to true.
	 * 
	 */
	@Test
	public void testMarkTaskAsCompleted() {

		// testTask state set to Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask.setTaskDeadline(taskDeadline);

		testTask.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask.addProjectCollaboratorToTask(projectUser1);
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

		// Marks testTask as finished
		testTask.markTaskAsFinished();

		// testTask2 state set to Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask2.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask2.setTaskDeadline(taskDeadline);

		testTask2.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask2.addProjectCollaboratorToTask(projectUser1);
		testTask2.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask2.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		testTask2.setStartDate(projStartDate);
		testTask2.getTaskState().changeToOnGoing();

		// assures that the taskTest2 state is OnGoing
		assertEquals("OnGoing", testTask2.viewTaskStateName());

		// Asserts if testTask is cleared, and testTask2 isn't
		assertTrue(testTask.isTaskFinished());
		assertFalse(testTask2.isTaskFinished());

	}

}
