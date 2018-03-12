package sprint.two;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests US340
 * 
 * US350: - Como Gestor de projeto quero poder criar uma tarefa.
 * 
 * Ao criar uma tarefa posso: - indicar a o esforço estimado (na unidade
 * definida para o projeto) - data de início prevista - data de conclusão
 * prevista - custo unitário orçamentado
 * 
 * uses method isProjectManager, createTask
 * 
 */

public class US340 {

	Company myCompany;
	UserContainer userContainer;
	User user1;
	User userAdmin;
	Project project;
	ProjectContainer projectContainer;
	TaskContainer taskContainer;
	Task testTask;
	Task testTask2;
	Task testTask3;
	Task testTask4;

	@Before
	public void setUp() {
		// create company

		myCompany = Company.getTheInstance();

		// creates an UserContainer
		userContainer = myCompany.getUsersContainer();

		// creates a ProjectsRepository
		projectContainer = myCompany.getProjectsContainer();

		userContainer.getAllUsersFromUserContainer().clear();

		// create user
		user1 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		// create user admin
		userAdmin = userContainer.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		// add user to user list
		userContainer.addUserToUserRepository(user1);
		userContainer.addUserToUserRepository(userAdmin);
		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		userAdmin.setUserProfile(Profile.COLLABORATOR);
		// create project
		project = projectContainer.createProject("name3", "description4", userAdmin);// !!!
		// create taskContainer
		taskContainer = project.getTaskRepository();

	}

	@After
	public void tearDown() {
		Company.clear();
		user1 = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
		testTask4 = null;
		project = null;
		projectContainer = null;
		taskContainer = null;
		userContainer = null;
	}

	@Test
	public void test340() {

		// verifies if userAdmin is Project Manager
		assertTrue(project.isProjectManager(userAdmin));
		assertFalse(project.isProjectManager(user1));

		// create a estimated Task Start Date
		Calendar estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.DECEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 29);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);
		// create a estimated Task Deadline
		Calendar taskDeadlineDateTest = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.JANUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create 1 task
		testTask = taskContainer.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask2 = taskContainer.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask3 = taskContainer.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask4 = taskContainer.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);

		// Adds Tasks to TaskContainer
		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);
		taskContainer.addTaskToProject(testTask4);

		// Creates a new List of Tasks, to compare with the getProjectTaskList of the
		// getProjectTaskList method
		List<Task> taskListToCompare = new ArrayList<Task>();

		// adds tasks to the task list
		taskListToCompare.add(testTask);
		taskListToCompare.add(testTask2);
		taskListToCompare.add(testTask3);
		taskListToCompare.add(testTask4);

		// See if the two lists have the same tasks
		assertEquals(taskContainer.getAllTasksfromProject(), taskListToCompare);

	}
	


}
