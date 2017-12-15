package project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TaskRepositoryTest {

	UserRepository userRepository;
	User user1;
	User userAdmin;
	User user2;
	TaskCollaborator taskWorker1;
	TaskCollaborator taskWorker2;
	ProjectCollaborator collab1;
	ProjectCollaborator collab2;
	Project project;
	ProjectRepository projectRepository;
	TaskRepository taskRepository;
	Task testTask;
	Task testTask2;
	Task testTask3;
	Task testTask4;
	Task testTask5;
	Task testTask6;
	Task testTask7;
	Calendar estimatedTaskStartDateTest;
	Calendar taskDeadlineDateTest;

	@Before public
	void setUp() {
		// creates an UserRepository
		userRepository = new UserRepository();

		// creates a ProjectRepository
		projectRepository = new ProjectRepository();

		// create user
		user1 = userRepository.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		user2 = userRepository.createUser("Joao", "joao@gmail.com", "002", "collaborator", "920000000", "Rua",
				"2402-00", "Test", "Testo", "Testistan");
		// create user admin
		userAdmin = userRepository.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		// create project collaborators
		collab1 = new ProjectCollaborator(user1, 2);
		collab2 = new ProjectCollaborator(user2, 3);
		// create task workers
		taskWorker1 = new TaskCollaborator(collab1);
		taskWorker2 = new TaskCollaborator(collab2);
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
		project.addUserToProjectTeam(collab1);
		project.addUserToProjectTeam(collab2);
		// create taskRepository
		taskRepository = project.getTaskRepository();
		// create a estimated Task Start Date
		Calendar estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 25);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);
		// create a estimated Task Dead line Date
		Calendar taskDeadlineDateTest = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.JANUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a expired estimated Task Dead line Date
		Calendar taskExpiredDeadlineDateTest = Calendar.getInstance();
		taskExpiredDeadlineDateTest.set(Calendar.YEAR, 2017);
		taskExpiredDeadlineDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		taskExpiredDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskExpiredDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create 4 tasks
		testTask = taskRepository.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask2 = taskRepository.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask3 = taskRepository.createTask("Test moar yeh", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask4 = taskRepository.createTask("TEST HARDER!", 10, estimatedTaskStartDateTest, taskDeadlineDateTest, 10);
		testTask5 = taskRepository.createTask("TEST HARDER!", 10, estimatedTaskStartDateTest,
				taskExpiredDeadlineDateTest, 10);
		testTask6 = taskRepository.createTask("TEST HARDER!", 10, estimatedTaskStartDateTest,
				taskExpiredDeadlineDateTest, 10);
		testTask7 = taskRepository.createTask("TEST HARDER!", 10, estimatedTaskStartDateTest,
				taskExpiredDeadlineDateTest, 10);

	}

	@After public
	void tearDown() {
		user1 = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
		testTask4 = null;
		project = null;
		projectRepository = null;
		taskRepository = null;
		userRepository = null;
		taskWorker1 = null;
		taskWorker2 = null;
		collab1 = null;
		collab2 = null;
	}

	@Test public
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

	@Test public
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

	@Test public
	void testGetUnFinishedTasksFromUser() {
		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// add de user to the task
		testTask.addUserToTask(taskWorker1);
		testTask2.addUserToTask(taskWorker1);
		testTask3.addUserToTask(taskWorker1);
		testTask4.addUserToTask(taskWorker1);

		// create a list and add task to compare to unfinished task list
		List<Task> test = new ArrayList<Task>();
		test.add(testTask);
		test.add(testTask2);
		test.add(testTask3);
		test.add(testTask4);

		// verify if test list is the same as the user unfinished task list
		assertEquals(test, taskRepository.getUnFinishedTasksFromUser(collab1));
	}

	@Test public
	void testFinishedTaskListOfUserInProject() {

		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// adds the user to the task
		testTask.addUserToTask(taskWorker1);
		testTask2.addUserToTask(taskWorker1);
		testTask3.addUserToTask(taskWorker1);
		testTask4.addUserToTask(taskWorker1);

		// Marks task and task3 as finished
		testTask.markTaskAsFinished();
		testTask3.markTaskAsFinished();

		// create a list and add task to compare to unfinished task list
		List<Task> test = new ArrayList<Task>();
		test.add(testTask);
		test.add(testTask3);

		// verify if test list is the same as the user finished task list
		assertEquals(test, taskRepository.getFinishedTaskListofUserInProject(collab1));

	}

	@Test public
	void testGetFinishedTaskGivenMonth() {
		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// add de user to the task
		testTask.addUserToTask(taskWorker1);
		testTask2.addUserToTask(taskWorker1);
		testTask3.addUserToTask(taskWorker1);
		testTask4.addUserToTask(taskWorker1);
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
		assertEquals(test, taskRepository.getFinishedTasksGivenMonth(collab1, 1));
	}

	@Test public
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

	@Test public
	void testGetTimeOnLastMonthProjectUserTask() {
		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);

		// add de user to the task
		testTask.addUserToTask(taskWorker1);

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

		testTask.addUserToTask(taskWorker1);
		testTask.createReport(taskWorker1);
		testTask.getReports().get(0).setReportedTime(5);

		// Checks if the 2 values are equal
		assertEquals(5.0, taskRepository.getTimeSpentOnLastMonthProjectUserTasks(collab1), 0.001);

	}

	@Test public
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

	@Test public
	void testGetProjectId() {
		// checks if the project id are the same;
		assertEquals(project.getIdCode(), taskRepository.getProjId());

		// creates a new project
		projectRepository.addProjectToProjectRepository(project);
		TaskRepository anotherTaskRepository = project.getTaskRepository();
		assertEquals(project.getIdCode(), anotherTaskRepository.getProjId());
	}

	@Test public
	void testGetAllTaskOfUser() {
		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// adds the user to the task
		testTask.addUserToTask(taskWorker1);
		testTask2.addUserToTask(taskWorker1);
		testTask3.addUserToTask(taskWorker1);
		testTask4.addUserToTask(taskWorker1);

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
		assertEquals(testList, taskRepository.getAllTasks(collab1));

	}

	@Test public
	void testIsThereUserWithoutTasks() {
		// adds task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);

		// Adds user to testTssk
		testTask.addUserToTask(taskWorker2);

		// Checks if the collab1 doesnt have any task assigned to him
		assertFalse(taskRepository.isCollaboratorActiveOnTasks(collab1));

		// Checks if the collab2 has tasks assigned to him
		assertTrue(taskRepository.isCollaboratorActiveOnTasks(collab2));
	}

	@Test public
	void testGetListofTasksWithoutCollaboratorsAssigned() {

		// adds task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);

		// adds User of index 1 to testTask and testTask4
		testTask.addUserToTask(taskWorker2);
		testTask4.addUserToTask(taskWorker2);

		// Creates a new list, and then added the tasks without any user assigned to
		// them
		List<Task> listTasksWithoutUser = new ArrayList<Task>();
		listTasksWithoutUser.add(testTask2);
		listTasksWithoutUser.add(testTask3);

		// Checks if both lists have the same tasks
		assertEquals(listTasksWithoutUser, taskRepository.getListofTasksWithoutCollaboratorsAssigned());

	}

	@Test public
	void testGetUnFinishedTasks() {

		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);

		// Adds user1 to the Task
		testTask.addUserToTask(taskWorker1);
		testTask2.addUserToTask(taskWorker1);

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

	@Test public
	void testGetFinishedTasks() {

		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);

		// Adds user1 to the Task
		testTask.addUserToTask(taskWorker1);
		testTask2.addUserToTask(taskWorker1);

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

	@Test public
	void testGetUnstartedTasks() {

		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);

		// Adds user1 to the Task
		testTask.addUserToTask(taskWorker1);
		testTask2.addUserToTask(taskWorker1);

		// Creates a new list, and then added the unfished task
		List<Task> listUnstartedTasks = new ArrayList<Task>();
		listUnstartedTasks.add(testTask);
		listUnstartedTasks.add(testTask2);

		// Checks if both lists have the same tasks
		assertEquals(listUnstartedTasks, taskRepository.getUnstartedTasks());

	}

	@Test public
	void testGetExpiredTasks() {
		// create star date to test
		Calendar startDateTest = Calendar.getInstance();
		startDateTest.set(Calendar.YEAR, 2017);
		startDateTest.set(Calendar.MONTH, Calendar.NOVEMBER);
		startDateTest.set(Calendar.DAY_OF_MONTH, 29);
		startDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask4);
		taskRepository.addProjectTask(testTask5);
		taskRepository.addProjectTask(testTask6);
		taskRepository.addProjectTask(testTask7);

		// Adds user1 to the Task
		testTask4.addUserToTask(taskWorker1);
		testTask5.addUserToTask(taskWorker1);
		testTask6.addUserToTask(taskWorker1);
		testTask7.addUserToTask(taskWorker1);

		// start tasks
		testTask4.setStartDate(startDateTest);
		testTask5.setStartDate(startDateTest);
		testTask6.setStartDate(startDateTest);
		testTask7.setStartDate(startDateTest);

		// Creates a new list, and then added the Expired tasks
		List<Task> listExpiredTasks = new ArrayList<Task>();
		listExpiredTasks.add(testTask5);
		listExpiredTasks.add(testTask6);
		listExpiredTasks.add(testTask7);

		// Checks if both lists have the same tasks
		assertEquals(listExpiredTasks, taskRepository.getExpiredTasks());

	}

}
