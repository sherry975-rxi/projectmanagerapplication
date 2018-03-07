package sprint.one;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class US206Tests {
	/**
	 * Tests US206
	 * 
	 * US206: Como colaborador, eu pretendo remover uma tarefa que consta na minha
	 * lista de tarefas
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
	List<TaskCollaborator> listToCompare;

	@Before
	public void setUp() {
		myCompany = Company.getTheInstance();

		// creates an UserContainer
		userContainer = myCompany.getUsersRepository();

		// creates a ProjectsRepository
		projectContainer = myCompany.getProjectsRepository();

		// create user
		newUser2 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// create user
		newUser3 = userContainer.createUser("Miguel", "miguel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// create project
		testProj = projectContainer.createProject("name3", "description4", newUser3);

		// create a estimated Task Start Date
		Calendar estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.DECEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 10);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);
		// create a estimated Task Start Date
		Calendar taskDeadlineDateTest = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2017);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.DECEMBER);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 20);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// creates a Project
		testProj = projectContainer.createProject("name3", "description4", newUser3);

		// create taskContainer
		taskContainer = testProj.getTaskRepository();

		// creates 2 Project Collaborators
		projectUser1 = testProj.createProjectCollaborator(newUser2, 10);
		projectUser2 = testProj.createProjectCollaborator(newUser3, 10);

		// creates 2 Tasks
		testTask = taskContainer.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask2 = taskContainer.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);

		// Creates 2 Task Workers
		taskWorker1 = testTask.createTaskCollaborator(projectUser1);
		taskWorker2 = testTask2.createTaskCollaborator(projectUser2);

		// Creates a new list to Compare with the list of taskWorkes that is obtained by
		// the method in Task Class
		listToCompare = new ArrayList<TaskCollaborator>();

		typeOfUser = 1;
	}

	@After
	public void tearDown() {
		Company.clear();
		taskContainer = null;
		userContainer = null;
		projectContainer = null;
		newUser2 = null;
		newUser3 = null;
		testProj = null;
		testTask = null;
		testTask2 = null;
		projectUser1 = null;
		projectUser2 = null;
		typeOfUser = 0;
		listToCompare = null;
		taskWorker1 = null;
		taskWorker2 = null;
	}

	/**
	 * 
	 * This test adds two tasks workers to the task, created previously on the setUp
	 * One of the tasks is mask as finished. Then it asserts to see if the state of
	 * the isFinished field on the task is set to true.
	 * 
	 */
	@Test
	public void testRemoveTaskFromUser() {

		// Adds users to the respective tasks
		testTask.addTaskCollaboratorToTask(taskWorker1);
		testTask2.addTaskCollaboratorToTask(taskWorker1);
		testTask.addTaskCollaboratorToTask(taskWorker2);

		// Adds 2 users to the comparison list
		listToCompare.add(taskWorker1);
		listToCompare.add(taskWorker2);

		// Checks if the two lists are the same
		assertEquals(listToCompare, testTask.getTaskTeam());

		// AssertsTrue to see if the TaskTeam contains both users
		assertTrue(testTask.isProjectCollaboratorInTaskTeam(projectUser1));
		assertTrue(testTask.isProjectCollaboratorInTaskTeam(projectUser2));

		// AssertTrue to see if the TeamUser State is set to active
		assertTrue(testTask.isProjectCollaboratorActiveInTaskTeam(projectUser1));

		// sets the userState from newUser 2 to not Active
		testTask.removeProjectCollaboratorFromTask(projectUser1);

		// Checks if the active of newUser 2 is now set to false
		assertFalse(testTask.isProjectCollaboratorActiveInTaskTeam(projectUser1));

	}

}
