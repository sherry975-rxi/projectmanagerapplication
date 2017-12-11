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
	User user2;
	Project project;
	ProjectRepository projectRepository;
	TaskRepository taskRepository;
	Task testTask;
	Task testTask2;
	Task testTask3;
	Task testTask4;
	Calendar estimatedTaskStartDateTest;
	Calendar taskDeadlineDateTest;

	@BeforeEach
	void setUp() {
		// create company
		myCompany = Company.getTheInstance();

		// creates an UserRepository
		userRepository = myCompany.getUsersRepository();

		// creates a ProjectRepository
		projectRepository = myCompany.getProjectsRepository();

		userRepository.getAllUsersFromRepository().clear();
		// create user
		user1 = userRepository.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		user2 = userRepository.createUser("Joao", "joao@gmail.com", "002", "collaborator", "920000000", "Rua",
				"2402-00", "Test", "Testo", "Testistan");
		// create user admin
		userAdmin = userRepository.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		// add user to user list
		userRepository.addUserToUserRepository(user1);
		userRepository.addUserToUserRepository(user2);
		userRepository.addUserToUserRepository(userAdmin);
		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);
		userAdmin.setUserProfile(Profile.COLLABORATOR);
		// create project
		project = projectRepository.createProject("name3", "description4", userAdmin);
		// add user to project team
		project.addUserToProjectTeam(user1, 2);
		project.addUserToProjectTeam(user2, 3);
		// create taskRepository
		taskRepository = project.getTaskRepository();
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
		// create 4 tasks
		testTask = taskRepository.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask2 = taskRepository.createTask("Test dis agen pls", 10, taskDeadlineDateTest, taskDeadlineDateTest, 10);
		testTask3 = taskRepository.createTask("Test moar yeh", 10, taskDeadlineDateTest, taskDeadlineDateTest, 10);
		testTask4 = taskRepository.createTask("TEST HARDER!", 10, taskDeadlineDateTest, taskDeadlineDateTest, 10);

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
	void testGetUnFinishedTasksFromUser() {
		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// add de user to the task
		testTask.addUserToTask(project.getProjectTeam().get(0));
		testTask2.addUserToTask(project.getProjectTeam().get(0));
		testTask3.addUserToTask(project.getProjectTeam().get(0));
		testTask4.addUserToTask(project.getProjectTeam().get(0));

		// create a list and add task to compare to unfinished task list
		List<Task> test = new ArrayList<Task>();
		test.add(testTask);
		test.add(testTask2);
		test.add(testTask3);
		test.add(testTask4);

		// verify if test list is the same as the user unfinished task list
		assertEquals(test, taskRepository.getUnFinishedTasksFromUser(project.getProjectTeam().get(0)));
	}

	@Test
	void testFinishedTaskListOfUserInProject() {

		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// adds the user to the task
		testTask.addUserToTask(project.getProjectTeam().get(0));
		testTask2.addUserToTask(project.getProjectTeam().get(0));
		testTask3.addUserToTask(project.getProjectTeam().get(0));
		testTask4.addUserToTask(project.getProjectTeam().get(0));

		// Marks task and task3 as finished
		testTask.markTaskAsFinished();
		testTask3.markTaskAsFinished();

		// create a list and add task to compare to unfinished task list
		List<Task> test = new ArrayList<Task>();
		test.add(testTask);
		test.add(testTask3);

		// verify if test list is the same as the user finished task list
		assertEquals(test, taskRepository.getFinishedTaskListofUserInProject(project.getProjectTeam().get(0)));

	}

	@Test
	void testGetFinishedTaskGivenMonth() {
		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// add de user to the task
		testTask.addUserToTask(project.getProjectTeam().get(0));
		testTask2.addUserToTask(project.getProjectTeam().get(0));
		testTask3.addUserToTask(project.getProjectTeam().get(0));
		testTask4.addUserToTask(project.getProjectTeam().get(0));
		// create finished date to test
		Calendar finishDateTest = Calendar.getInstance();
		finishDateTest.set(Calendar.YEAR, 2017);
		finishDateTest.set(Calendar.MONTH, Calendar.NOVEMBER);
		finishDateTest.set(Calendar.DAY_OF_MONTH, 29);
		finishDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// finish tasks
		testTask.setFinishDate(finishDateTest);
		testTask2.setFinishDate(finishDateTest);
		testTask3.setFinishDate(finishDateTest);
		testTask4.setFinishDate(finishDateTest);

		// create a list and add finished tasks to compare to finished task list
		List<Task> test = new ArrayList<Task>();
		test.add(testTask);
		test.add(testTask2);
		test.add(testTask3);
		test.add(testTask4);

		// verify if test list is the same as the user unfinished task list
		assertEquals(test, taskRepository.getFinishedTasksGivenMonth(project.getProjectTeam().get(0), 1));
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

		// add de user to the task
		testTask.addUserToTask(project.getProjectTeam().get(0));

		// create finished date to test
		Calendar startDateTest = Calendar.getInstance();
		startDateTest.set(Calendar.YEAR, 2017);
		startDateTest.set(Calendar.MONTH, Calendar.NOVEMBER);
		startDateTest.set(Calendar.DAY_OF_MONTH, 29);
		startDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// start task
		testTask.setStartDate(startDateTest);

		// create finished date to test
		Calendar finishDateTest = Calendar.getInstance();
		finishDateTest.set(Calendar.YEAR, 2017);
		finishDateTest.set(Calendar.MONTH, Calendar.NOVEMBER);
		finishDateTest.set(Calendar.DAY_OF_MONTH, 29);
		finishDateTest.set(Calendar.HOUR_OF_DAY, 15);

		// finish task
		testTask.setFinishDate(finishDateTest);

		// set work time in task

		testTask.getTaskTeam().get(0).setHoursSpent(5);

		// Checks if the 2 values are equal
		assertEquals(5.0, taskRepository.getTimeSpentOnLastMonthProjectUserTasks(project.getProjectTeam().get(0)),
				0.001);

	}

	@Test
	void testGetTaskCounter() {
		// sets the task counter as 0;
		taskRepository.setTaskCounter(0);
		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.setTaskCounter(1);
		taskRepository.addProjectTask(testTask2);
		taskRepository.setTaskCounter(2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.setTaskCounter(3);

		// creates a variable with the value of the expected outcome of getTaskCounter
		// method in taskRepository class
		int expectedTaskCounter = 3;

		// Checks if the 2 values are equal
		assertEquals(expectedTaskCounter, taskRepository.getTaskCounter());
	}

	@Test
	void testGetProjectId() {
		// checks if the project id are the same;
		assertEquals(project.getIdCode(), taskRepository.getProjId());

		// creates a new project
		projectRepository.addProjectToProjectRepository(project);
		TaskRepository anotherTaskRepository = project.getTaskRepository();
		assertEquals(project.getIdCode(), anotherTaskRepository.getProjId());
	}

	@Test
	void testGetAllTaskOfUser() {
		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// adds the user to the task
		testTask.addUserToTask(project.getProjectTeam().get(0));
		testTask2.addUserToTask(project.getProjectTeam().get(0));
		testTask3.addUserToTask(project.getProjectTeam().get(0));
		testTask4.addUserToTask(project.getProjectTeam().get(0));

		// Marks task and task3 as finished
		testTask.markTaskAsFinished();
		testTask3.markTaskAsFinished();

		// create a list and add tasks to compare with user task list
		List<Task> testList = new ArrayList<Task>();
		testList.add(testTask);
		testList.add(testTask2);
		testList.add(testTask3);
		testList.add(testTask4);

		// See if the two taskLists have the same tasks
		assertEquals(testList, taskRepository.getAllTasks(project.getProjectTeam().get(0)));

	}

	@Test
	void testIsThereUserWithoutTasks() {
		// adds task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);

		// Adds user to testTssk
		testTask.addUserToTask(project.getProjectTeam().get(1));

		// Checks if the user of index.0 doesnt have any task assigned to him
		assertFalse(taskRepository.isCollaboratorActiveOnTasks(project.getProjectTeam().get(0)));

		// Checks if the user of index.1 has tasks assigned to him
		assertTrue(taskRepository.isCollaboratorActiveOnTasks(project.getProjectTeam().get(1)));
	}

	@Test
	void testGetListofTasksWithoutCollaboratorsAssigned() {

		// adds task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);

		// adds User of index 1 to testTask and testTask4
		testTask.addUserToTask(project.getProjectTeam().get(1));
		testTask4.addUserToTask(project.getProjectTeam().get(1));

		// Creates a new list, and then added the tasks without any user assigned to
		// them
		List<Task> listTasksWithoutUser = new ArrayList<Task>();
		listTasksWithoutUser.add(testTask2);
		listTasksWithoutUser.add(testTask3);

		// Checks if both lists have the same tasks
		assertEquals(listTasksWithoutUser, taskRepository.getListofTasksWithoutCollaboratorsAssigned());

	}

	@Test
	void testGetUnFinishedTasks() {

		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);

		// Adds user1 to the Task
		testTask.addUserToTask(project.getProjectTeam().get(0));
		testTask2.addUserToTask(project.getProjectTeam().get(0));

		// create finished date to test
		Calendar startDateTest = Calendar.getInstance();
		startDateTest.set(Calendar.YEAR, 2017);
		startDateTest.set(Calendar.MONTH, Calendar.NOVEMBER);
		startDateTest.set(Calendar.DAY_OF_MONTH, 29);
		startDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// start task
		testTask.setStartDate(startDateTest);
		testTask2.setStartDate(startDateTest);

		// Creates a new list, and then added the unfished task
		List<Task> listUnfinishedTasks = new ArrayList<Task>();
		listUnfinishedTasks.add(testTask);
		listUnfinishedTasks.add(testTask2);

		// Checks if both lists have the same tasks
		assertEquals(listUnfinishedTasks, taskRepository.getUnFinishedTasks());

	}

	@Test
	void testGetFinishedTasks() {

		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);

		// Adds user1 to the Task
		testTask.addUserToTask(project.getProjectTeam().get(0));
		testTask2.addUserToTask(project.getProjectTeam().get(0));

		// create star date to test
		Calendar startDateTest = Calendar.getInstance();
		startDateTest.set(Calendar.YEAR, 2017);
		startDateTest.set(Calendar.MONTH, Calendar.NOVEMBER);
		startDateTest.set(Calendar.DAY_OF_MONTH, 29);
		startDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create finished date to test
		Calendar finishDateTest = Calendar.getInstance();
		finishDateTest.set(Calendar.YEAR, 2017);
		finishDateTest.set(Calendar.MONTH, Calendar.NOVEMBER);
		finishDateTest.set(Calendar.DAY_OF_MONTH, 29);
		finishDateTest.set(Calendar.HOUR_OF_DAY, 15);

		// start task
		testTask.setStartDate(startDateTest);
		testTask2.setStartDate(startDateTest);

		// set finished task
		testTask.setFinishDate(finishDateTest);
		testTask2.setFinishDate(finishDateTest);

		// mark task as Finished
		testTask.markTaskAsFinished();
		testTask2.markTaskAsFinished();

		// Creates a new list, and then added the unfished task
		List<Task> listUnfinishedTasks = new ArrayList<Task>();
		listUnfinishedTasks.add(testTask);
		listUnfinishedTasks.add(testTask2);

		// Checks if both lists have the same tasks
		assertEquals(listUnfinishedTasks, taskRepository.getFinishedTasks());

	}

	@Test
	void testGetUnstartedTasks() {

		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);

		// Adds user1 to the Task
		testTask.addUserToTask(project.getProjectTeam().get(0));
		testTask2.addUserToTask(project.getProjectTeam().get(0));

		// Creates a new list, and then added the unfished task
		List<Task> listUnstartedTasks = new ArrayList<Task>();
		listUnstartedTasks.add(testTask);
		listUnstartedTasks.add(testTask2);

		// Checks if both lists have the same tasks
		assertEquals(listUnstartedTasks, taskRepository.getUnstartedTasks());

	}

}
