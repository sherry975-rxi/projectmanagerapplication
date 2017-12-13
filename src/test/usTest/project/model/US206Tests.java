package test.usTest.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.project.model.Company;
import main.project.model.Project;
import main.project.model.ProjectCollaborator;
import main.project.model.ProjectRepository;
import main.project.model.Task;
import main.project.model.TaskRepository;
import main.project.model.TaskWorker;
import main.project.model.User;
import main.project.model.UserRepository;

class US206Tests {
	/**
	 * Tests US206
	 * 
	 * US206: Como colaborador, eu pretendo remover uma tarefa que consta na minha
	 * lista de tarefas
	 * 
	 */
	Company myCompany;
	UserRepository userRepository;
	ProjectRepository projectRepository;
	TaskRepository taskRepository;
	ProjectCollaborator projectUser1;
	ProjectCollaborator projectUser2;
	User newUser2;
	User newUser3;
	Project testProj;
	Task testTask;
	Task testTask2;
	int typeOfUser;
	TaskWorker taskWorker1;
	TaskWorker taskWorker2;
	List<TaskWorker> listToCompare;

	@BeforeEach
	void setUp() {
		myCompany = Company.getTheInstance();
		myCompany.getUsersRepository().getAllUsersFromRepository().clear();
		myCompany.getProjectsRepository().getAllProjects().clear();
		// creates an UserRepository
		userRepository = myCompany.getUsersRepository();

		// creates a ProjectRepository
		projectRepository = myCompany.getProjectsRepository();

		// create user
		newUser2 = userRepository.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// create user
		newUser3 = userRepository.createUser("Miguel", "miguel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// create project
		testProj = projectRepository.createProject("name3", "description4", newUser3);

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
		testProj = projectRepository.createProject("name3", "description4", newUser3);

		// create taskRepository
		taskRepository = testProj.getTaskRepository();

		// creates 2 Project Collaborators
		projectUser1 = testProj.createProjectCollaborator(newUser2, 10);
		projectUser2 = testProj.createProjectCollaborator(newUser3, 10);

		// creates 2 Tasks
		testTask = taskRepository.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask2 = taskRepository.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);

		// Creates 2 Task Workers
		taskWorker1 = testTask.createTaskWorker(projectUser1);
		taskWorker2 = testTask2.createTaskWorker(projectUser2);

		// Creates a new list to Compare with the list of taskWorkes that is obtained by
		// the method in Task Class
		listToCompare = new ArrayList<TaskWorker>();

		typeOfUser = 1;
	}

	@AfterEach
	void tearDown() {
		myCompany = null;
		taskRepository = null;
		newUser2 = null;
		newUser3 = null;
		testProj = null;
		testTask = null;
		testTask2 = null;
		projectUser1 = null;
		projectUser2 = null;
		typeOfUser = 0;
		listToCompare = null;
	}

	/**
	 * 
	 * This test adds two tasks workers to the task, created previously on the setUp
	 * One of the tasks is mask as finished. Then it asserts to see if the state of
	 * the isFinished field on the task is set to true.
	 * 
	 */
	@Test
	void testRemoveTaskFromUser() {

		// Adds users to the respective tasks
		testTask.addUserToTask(taskWorker1);
		testTask2.addUserToTask(taskWorker1);
		testTask.addUserToTask(taskWorker2);

		// Adds 2 users to the comparison list
		listToCompare.add(taskWorker1);
		listToCompare.add(taskWorker2);

		// Checks if the two lists are the same
		assertEquals(listToCompare, testTask.getTaskTeam());

		// AssertsTrue to see if the TaskTeam contains both users
		assertTrue(testTask.taskTeamContainsUser(newUser2));
		assertTrue(testTask.taskTeamContainsUser(newUser3));

		// AssertTrue to see if the TeamUser State is set to active
		assertTrue(testTask.taskTeamUserIsActive(newUser2));

		// sets the userState from newUser 2 to not Active
		testTask.removeUserFromTask(newUser2);

		// Checks if the active of newUser 2 is now set to false
		assertFalse(testTask.taskTeamUserIsActive(newUser2));

	}

}
