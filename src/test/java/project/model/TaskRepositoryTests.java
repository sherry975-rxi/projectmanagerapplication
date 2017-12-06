package test.java.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Profile;
import main.java.project.model.Project;
import main.java.project.model.ProjectRepository;
import main.java.project.model.Task;
import main.java.project.model.TaskRepository;
import main.java.project.model.User;
import main.java.project.model.UserRepository;

class TaskRepositoryTests {

	Company myCompany;
	UserRepository userRepository;
	User user1;
	User userAdmin;
	Project project;
	ProjectRepository projectRepository;
	TaskRepository taskRepository;
	Task testTask;
	Task testTask2;
	Task testTask3;
	Task testTask4;

	@BeforeEach
	void setUp() {
		// create company
		myCompany = Company.getTheInstance();

		// creates an UserRepository
		userRepository = myCompany.getUsersRepository();

		// creattes a ProjectRepository
		projectRepository = myCompany.getProjectsRepository();

		userRepository.getAllUsersFromRepository().clear();
		// create user
		user1 = userRepository.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		// create user admin
		userAdmin = userRepository.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		// add user to user list
		userRepository.addUserToUserRepository(user1);
		userRepository.addUserToUserRepository(userAdmin);
		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		userAdmin.setUserProfile(Profile.COLLABORATOR);
		// create project
		project = projectRepository.createProject("name3", "description4", userAdmin);// !!!
		// create taskRepository
		taskRepository = project.getTaskRepository();
		// create 4 tasks
		testTask = taskRepository.createTask("Test dis agen pls");
		testTask2 = taskRepository.createTask("Test dis agen pls");
		testTask3 = taskRepository.createTask("Test moar yeh");
		testTask4 = taskRepository.createTask("TEST HARDER!");

	}

	@AfterEach
	void tearDown() {
		myCompany = null;
		user1 = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
		testTask4 = null;
		project = null;
		projectRepository = null;
		taskRepository = null;
		userRepository = null;
	}

	@Test
	void testTaskRepository() {

	}

	@Test
	void testCreateTask() {

		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);

		// Creates a new List of Tasks, to compare with the getProjectTaskList of the
		// getProjectTaskList method
		List<Task> taskListToCompare = new ArrayList<Task>();

		// adds tasks to the task list
		taskListToCompare.add(testTask);
		taskListToCompare.add(testTask2);
		taskListToCompare.add(testTask3);
		taskListToCompare.add(testTask4);

		// See if the two lists have the same tasks
		assertEquals(taskRepository.getProjectTaskList(), taskListToCompare);

	}

	@Test
	void testAddProjectTasks() {

	}

	@Test
	void testGetProjectTaskList() {

		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);

		// Creates a new List of Tasks, to compare with the getProjectTaskList of the
		// getProjectTaskList method
		List<Task> taskListToCompare = new ArrayList<Task>();

		// adds tasks to the task list
		taskListToCompare.add(testTask);
		taskListToCompare.add(testTask2);
		taskListToCompare.add(testTask3);
		taskListToCompare.add(testTask4);

		// See if the two lists have the same tasks
		assertEquals(taskRepository.getProjectTaskList(), taskListToCompare);

	}

	@Test
	void testGetUnFinishedTasks() {
		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// add de user to the task
		testTask.addUserToTask(user1);
		testTask2.addUserToTask(user1);
		testTask3.addUserToTask(user1);
		testTask4.addUserToTask(user1);

		// create a list and add task to compare to unfinished task list
		List<Task> test = new ArrayList<Task>();
		test.add(testTask);
		test.add(testTask2);
		test.add(testTask3);
		test.add(testTask4);

		// verify if test list is the same as the user unfinished task list
		assertEquals(test, taskRepository.getUnFinishedTasks(user1));
	}

	@Test
	void testFinishedTaskListOfUserInProject() {

		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// adds the user to the task
		testTask.addUserToTask(user1);
		testTask2.addUserToTask(user1);
		testTask3.addUserToTask(user1);
		testTask4.addUserToTask(user1);

		// Marks task and task3 as finished
		testTask.markTaskAsFinished();
		testTask3.markTaskAsFinished();

		// create a list and add task to compare to unfinished task list
		List<Task> test = new ArrayList<Task>();
		test.add(testTask);
		test.add(testTask3);

		// verify if test list is the same as the user finished task list
		assertEquals(test, taskRepository.getFinishedTaskListofUserInProject(user1));

	}

	@Test
	void testGetFinishedTaskGivenMonth() {
		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// add de user to the task
		testTask.addUserToTask(user1);
		testTask2.addUserToTask(user1);
		testTask3.addUserToTask(user1);
		testTask4.addUserToTask(user1);
		// create finished date to test
		Calendar finishDateTest = Calendar.getInstance();
		finishDateTest.set(Calendar.YEAR, 2017);
		finishDateTest.set(Calendar.MONTH, Calendar.NOVEMBER);
		finishDateTest.set(Calendar.DAY_OF_MONTH, 29);
		finishDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// finish tasks
		testTask.setFinishDate(finishDateTest);

		// create a list and add finished tasks to compare to finished task list
		List<Task> test = new ArrayList<Task>();
		test.add(testTask);
		test.add(testTask2);
		test.add(testTask3);
		test.add(testTask4);

		// verify if test list is the same as the user unfinished task list
		assertEquals(test, taskRepository.getFinishedTasksGivenMonth(user1, 1));
	}

	@Test
	void testContainsTask() {

		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);

		// See if the tasks are contained in the Task Repository
		assertTrue(taskRepository.containsTask(testTask));
		assertFalse(taskRepository.containsTask(testTask4));

		// adds Task4 to the Repository
		taskRepository.addProjectTask(testTask4);

		// See if task4 is contained in the Task Repository
		assertTrue(taskRepository.containsTask(testTask4));

	}

	@Test
	void testGetTimeOnLastMonthProjectUserTask() {
		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// add de user to the task
		testTask.addUserToTask(user1);
		testTask2.addUserToTask(user1);
		testTask3.addUserToTask(user1);
		testTask4.addUserToTask(user1);

	}

	@Test
	void testSetTaskCounter() {

		// sets the task counter as 0;
		taskRepository.setTaskCounter(0);
		// add task to task repository of the project
		taskRepository.createTask("New Task 1");
		taskRepository.createTask("New Task 2");
		taskRepository.createTask("New Task 3");

		// creates a variable with the value of the expected outcome of getTaskCounter
		// method in taskRepository class
		int expectedTaskCounter = 3;

		// Checks if the 2 values are equal
		assertEquals(expectedTaskCounter, taskRepository.getTaskCounter());

	}

	@Test
	void testGetTaskCounter() {

	}

	@Test
	void testGetProjectId() {

		// checks if the project id are the same;
		assertEquals(project.getIdCode(), taskRepository.getProjId());

		// creates a new project
		Project proj1 = projectRepository.createProject("Project", "My Description", user1);
		assertEquals(proj1.getIdCode(), 1);

	}

	@Test
	void testAllTasks() {

	}

}
