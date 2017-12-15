package sprint.two;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskRepository;
import project.model.User;
import project.model.UserRepository;

public class US372 {

	/**
	 * @author Group 3
	 * 
	 *         Test to US372
	 * 
	 *         US372 - As Project Manager, I want to get a list of completed tasks.
	 */

	Company myCompany;
	UserRepository userRepository;
	User user1;
	User user2;
	Project project;
	ProjectRepository projectRepository;
	TaskRepository taskRepository;
	Task testTask;
	Task testTask2;
	Task testTask3;
	Task testTask4;

	@Before
	public void setUp() {
		// create company

		myCompany = Company.getTheInstance();

		// creates an UserRepository
		userRepository = myCompany.getUsersRepository();

		// creates a ProjectRepository
		projectRepository = myCompany.getProjectsRepository();

		// creates a UserRepository
		userRepository.getAllUsersFromRepository().clear();

		// create user
		user1 = userRepository.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// create user admin
		user2 = userRepository.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");

		// add user to user list
		userRepository.addUserToUserRepository(user1);
		userRepository.addUserToUserRepository(user2);

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);

		// create project collabotors with u3, u4 and u5 users
		ProjectCollaborator collaborator1 = new ProjectCollaborator(user1, 1200);

		// create project
		project = projectRepository.createProject("name3", "description4", user2);

		// add user to project team
		project.addProjectCollaboratorToProjectTeam(collaborator1);

		// create taskRepository
		taskRepository = project.getTaskRepository();

	}

	@After
	public void tearDown() {
		myCompany.clear();
		user1 = null;
		testTask = null;
		project = null;
		projectRepository = null;
		taskRepository = null;
		userRepository = null;
	}

	@Test
	public void us372() {

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

		// create a start date

		Calendar startDate = Calendar.getInstance();
		startDate.set(Calendar.YEAR, 2017);
		startDate.set(Calendar.MONTH, Calendar.DECEMBER);
		startDate.set(Calendar.DAY_OF_MONTH, 11);
		startDate.set(Calendar.HOUR_OF_DAY, 14);

		// create 4 tasks
		testTask = taskRepository.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask2 = taskRepository.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask3 = taskRepository.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask4 = taskRepository.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);

		// Marks 2 tasks as finished
		testTask3.markTaskAsFinished();
		testTask4.markTaskAsFinished();

		// Sets start date for testTask and testTask3
		testTask.setStartDate(startDate);
		testTask3.setStartDate(startDate);

		ArrayList<Task> listOfUnfinishedTasks = new ArrayList<Task>();

		// Sees if the two lists are the same
		assertEquals(listOfUnfinishedTasks, taskRepository.getUnFinishedTasks());

	}

}
