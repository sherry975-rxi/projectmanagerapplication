package sprint.two;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * 
 * @author Group 3
 * 
 *         test to US375
 * 
 *         US375 - As Project Manager, I want to get a list of unstarted tasks.
 *
 */
public class US375 {
	Company myCompany;
	UserContainer userContainer;
	User user1;
	User user2;
	Project project;
	ProjectContainer projectContainer;
	TaskContainer taskContainer;
	Task testTask;
	Task testTask2;
	ProjectCollaborator projectCollaborator;
	TaskCollaborator taskWorker;
	TaskCollaborator taskWorker1;

	@Before
	public void setUp() {
		// create company

		myCompany = Company.getTheInstance();

		// creates an UserContainer
		userContainer = myCompany.getUsersRepository();

		// creates a ProjectsRepository
		projectContainer = myCompany.getProjectsRepository();

		// creates a UserContainer
		userContainer.getAllUsersFromRepository().clear();

		// create user
		user1 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// create user admin
		user2 = userContainer.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");

		// add user to user list
		userContainer.addUserToUserRepository(user1);
		userContainer.addUserToUserRepository(user2);

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);

		// create project
		project = projectContainer.createProject("name3", "description4", user2);

		// create project collaborator
		projectCollaborator = project.createProjectCollaborator(user1, 2);

		// add user to project team
		project.addProjectCollaboratorToProjectTeam(projectCollaborator);

		// create taskContainer
		taskContainer = project.getTaskRepository();

	}

	@After
	public void tearDown() {
		Company.clear();
		user1 = null;
		testTask = null;
		project = null;
		projectContainer = null;
		taskContainer = null;
		userContainer = null;
	}

	@Test
	public void testUS375() {
		// create a estimated Task Start Date
		Calendar estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.DECEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 29);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);
		// create a estimated Task Start Date
		Calendar taskDeadlineDateTest = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.JANUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create 2 task
		testTask = taskContainer.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask2 = taskContainer.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);

		// create task Worker
		taskWorker = testTask.createTaskCollaborator(projectCollaborator);
		taskWorker1 = testTask2.createTaskCollaborator(projectCollaborator);

		// Adds Tasks to TaskContainer
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);

		// Adds user1 to the Task
		testTask.addTaskCollaboratorToTask(taskWorker);
		testTask2.addTaskCollaboratorToTask(taskWorker1);

		// Creates a new list, and then added the tasks without any user assigned to
		// them
		List<Task> listunstartedTasks = new ArrayList<Task>();
		listunstartedTasks.add(testTask);
		listunstartedTasks.add(testTask2);

		assertEquals(listunstartedTasks, taskContainer.getUnstartedTasks());

	}
}
