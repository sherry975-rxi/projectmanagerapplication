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

import project.model.taskStateInterface.Assigned;
import project.model.taskStateInterface.Cancelled;
import project.model.taskStateInterface.Created;
import project.model.taskStateInterface.Finished;
import project.model.taskStateInterface.OnGoing;
import project.model.taskStateInterface.Planned;
import project.model.taskStateInterface.Ready;
import project.model.taskStateInterface.StandBy;

public class TaskRepositoryTest {

	UserRepository userRepository;
	User user1;
	User userAdmin;
	User user2;
	User user3;

	TaskCollaborator taskWorker1;
	TaskCollaborator taskWorker2;
	TaskCollaborator taskWorker3;

	ProjectCollaborator collab1;
	ProjectCollaborator collab2;
	ProjectCollaborator collab3;

	Project project;
	Project project2;
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
	Calendar startDateTest;

	@Before
	public void setUp() {
		// creates an UserRepository
		userRepository = new UserRepository();

		// creates a ProjectRepository
		projectRepository = new ProjectRepository();

		// create user
		user1 = userRepository.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		user2 = userRepository.createUser("Joao", "joao@gmail.com", "002", "collaborator", "920000000", "Rua",
				"2402-00", "Test", "Testo", "Testistan");
		user3 = userRepository.createUser("Francisco", "francisco@gmail.com", "003", "collaborator", "420000000", "Rua",
				"2402-00", "Test", "Testo", "Testistan");
		// create user admin
		userAdmin = userRepository.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");

		// Creates one Project
		project = projectRepository.createProject("name3", "description4", userAdmin);
		project2 = projectRepository.createProject("Project2", "description2", userAdmin);

		// create project collaborators
		collab1 = new ProjectCollaborator(user1, 2);
		collab2 = new ProjectCollaborator(user2, 3);
		collab3 = project2.createProjectCollaborator(user3, 3);

		// create task workers
		taskWorker1 = new TaskCollaborator(collab1);
		taskWorker2 = new TaskCollaborator(collab2);
		taskWorker3 = new TaskCollaborator(collab3);

		// add user to user list
		userRepository.addUserToUserRepository(user1);
		userRepository.addUserToUserRepository(user2);
		userRepository.addUserToUserRepository(user3);

		userRepository.addUserToUserRepository(userAdmin);
		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);
		user3.setUserProfile(Profile.COLLABORATOR);

		userAdmin.setUserProfile(Profile.COLLABORATOR);
		// add user to project team
		project.addProjectCollaboratorToProjectTeam(collab1);
		project.addProjectCollaboratorToProjectTeam(collab2);
		// create taskRepository
		taskRepository = project.getTaskRepository();

		// create a estimated Task Start Date
		Calendar startDateTest = Calendar.getInstance();

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

	@After
	public void tearDown() {
		user1 = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
		testTask4 = null;
		project = null;
		project2 = null;
		projectRepository = null;
		taskRepository = null;
		userRepository = null;
		taskWorker1 = null;
		taskWorker2 = null;
		collab1 = null;
		collab2 = null;
		collab3 = null;

	}

	@Test
	public void testCreateTask() {

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
		assertEquals(taskRepository.getProjectTaskRepository(), taskListToCompare);

	}

	@Test
	public void testGetProjectTaskList() {

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
		assertEquals(taskRepository.getProjectTaskRepository(), taskListToCompare);

	}

	@Test
	public void testGetUnFinishedTasksFromUser() {
		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// add de user to the task
		testTask.addTaskCollaboratorToTask(taskWorker1);
		testTask2.addTaskCollaboratorToTask(taskWorker1);
		testTask3.addTaskCollaboratorToTask(taskWorker1);
		testTask4.addTaskCollaboratorToTask(taskWorker1);

		// create a list and add task to compare to unfinished task list
		List<Task> test = new ArrayList<Task>();
		test.add(testTask);
		test.add(testTask2);
		test.add(testTask3);
		test.add(testTask4);

		// verify if test list is the same as the user unfinished task list
		assertEquals(test, taskRepository.getUnfinishedTasksFromProjectCollaborator(collab1));
	}

	@Test
	public void testFinishedTaskListOfUserInProject() {

		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// adds the user to the task
		testTask.addTaskCollaboratorToTask(taskWorker1);
		testTask2.addTaskCollaboratorToTask(taskWorker1);
		testTask3.addTaskCollaboratorToTask(taskWorker1);
		testTask4.addTaskCollaboratorToTask(taskWorker1);

		// Marks task and task3 as finished
		testTask.markTaskAsFinished();
		testTask3.markTaskAsFinished();

		// create a list and add task to compare to unfinished task list
		List<Task> test = new ArrayList<Task>();
		test.add(testTask);
		test.add(testTask3);

		// verify if test list is the same as the user finished task list
		assertEquals(test, taskRepository.getFinishedTaskListofUserInProject(collab1));

		// clears list
		test.clear();

		// Expects empty list, as collab2 doesnt belong to Task team
		assertEquals(test, taskRepository.getFinishedTaskListofUserInProject(collab2));

	}

	@Test
	public void testGetFinishedTaskGivenMonth() {
		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// add de user to the task
		testTask.addTaskCollaboratorToTask(taskWorker1);
		testTask2.addTaskCollaboratorToTask(taskWorker1);
		testTask3.addTaskCollaboratorToTask(taskWorker1);
		testTask4.addTaskCollaboratorToTask(taskWorker1);
		// create finished date to test
		Calendar finishDateTest = Calendar.getInstance();
		finishDateTest.add(Calendar.MONTH, -1);

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
		assertEquals(test, taskRepository.getFinishedTasksFromProjectCollaboratorInGivenMonth(collab1, 1));
	}

	@Test
	public void testContainsTask() {

		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);

		// See if the tasks are contained in the Task Repository
		assertTrue(taskRepository.isTaskInRTaskRepository(testTask));
		assertFalse(taskRepository.isTaskInRTaskRepository(testTask4));

		// adds Task4 to the Repository
		taskRepository.addProjectTask(testTask4);

		// See if task4 is contained in the Task Repository
		assertTrue(taskRepository.isTaskInRTaskRepository(testTask4));

	}

	@Test
	public void testGetTimeOnLastMonthProjectUserTask() {
		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);

		// add the user to the task
		testTask.addTaskCollaboratorToTask(taskWorker1);
		testTask.addTaskCollaboratorToTask(taskWorker2);
		testTask2.addTaskCollaboratorToTask(taskWorker2);
		testTask3.addTaskCollaboratorToTask(taskWorker1);

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
		finishDateTest.add(Calendar.MONTH, -1);

		// finish task
		testTask.setFinishDate(finishDateTest);

		// set work time in task

		testTask.addTaskCollaboratorToTask(taskWorker1);
		testTask.createReport(taskWorker1);
		testTask.getReports().get(0).setReportedTime(5);

		// Checks if the 2 values are equal
		assertEquals(5.0, taskRepository.getTimeSpentByProjectCollaboratorInAllTasksLastMonth(collab1), 0.001);
		// Checks if the 2 values are equal
		assertEquals(0.0, taskRepository.getTimeSpentByProjectCollaboratorInAllTasksLastMonth(collab2), 0.001);
		// Expects 0, as collab3 belongs to another Project
		assertEquals(0.0, taskRepository.getTimeSpentByProjectCollaboratorInAllTasksLastMonth(collab3), 0.001);

	}

	@Test
	public void testGetTaskCounter() {
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
	public void testGetProjectId() {
		// checks if the project id are the same;
		assertEquals(project.getIdCode(), taskRepository.getProjId());

		// creates a new project
		projectRepository.addProjectToProjectRepository(project);
		TaskRepository anotherTaskRepository = project.getTaskRepository();
		assertEquals(project.getIdCode(), anotherTaskRepository.getProjId());
	}

	@Test
	public void testGetAllTaskOfUser() {
		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// adds the user to the task
		testTask.addTaskCollaboratorToTask(taskWorker1);
		testTask2.addTaskCollaboratorToTask(taskWorker1);
		testTask3.addTaskCollaboratorToTask(taskWorker1);
		testTask4.addTaskCollaboratorToTask(taskWorker1);

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
		assertEquals(testList, taskRepository.getAllTasksFromProjectCollaborator(collab1));

	}

	@Test
	public void testIsThereUserWithoutTasks() {
		// adds task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);

		// Adds user to testTssk
		testTask.addTaskCollaboratorToTask(taskWorker2);

		// Checks if the collab1 doesnt have any task assigned to him
		assertFalse(taskRepository.isCollaboratorActiveOnAnyTask(collab1));

		// Checks if the collab2 has tasks assigned to him
		assertTrue(taskRepository.isCollaboratorActiveOnAnyTask(collab2));
	}

	@Test
	public void testGetListofTasksWithoutCollaboratorsAssigned() {

		// adds task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);

		// adds User of index 1 to testTask and testTask4
		testTask.addTaskCollaboratorToTask(taskWorker2);
		testTask4.addTaskCollaboratorToTask(taskWorker2);

		// Creates a new list, and then added the tasks without any user assigned to
		// them
		List<Task> listTasksWithoutUser = new ArrayList<Task>();
		listTasksWithoutUser.add(testTask2);
		listTasksWithoutUser.add(testTask3);
		// Checks if both lists have the same tasks
		assertEquals(listTasksWithoutUser, taskRepository.getAllTasksWithoutCollaboratorsAssigned());

		// adds TaskWorker 1 to testTask2
		testTask2.addTaskCollaboratorToTask(taskWorker1);

		// Removes task2 from taskList to compare
		listTasksWithoutUser.remove(testTask2);

		// See if the both lists have the same tasks.
		assertEquals(listTasksWithoutUser, taskRepository.getAllTasksWithoutCollaboratorsAssigned());

		// removes collab1 from testTask2
		testTask2.removeProjectCollaboratorFromTask(collab1);

		// clears the taskList
		listTasksWithoutUser.clear();

		// Adds testTask2 and testTask3 to the list
		listTasksWithoutUser.add(testTask2);
		listTasksWithoutUser.add(testTask3);

		// See if the both lists have the same tasks.
		assertEquals(listTasksWithoutUser, taskRepository.getAllTasksWithoutCollaboratorsAssigned());

	}

	@Test
	public void testGetUnFinishedTasks() {

		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);

		// Adds user1 to the Task
		testTask.addTaskCollaboratorToTask(taskWorker1);
		testTask2.addTaskCollaboratorToTask(taskWorker1);
		testTask3.addTaskCollaboratorToTask(taskWorker1);
		testTask4.addTaskCollaboratorToTask(taskWorker1);

		// create finished date to test
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
		testTask3.setStartDate(startDateTest);

		// Sets finish date to testTask3
		testTask3.setFinishDate(finishDateTest);

		// Marks testTask3 as finished
		testTask3.markTaskAsFinished();

		// Creates a new list, and then added the unfinished task
		List<Task> listUnfinishedTasks = new ArrayList<Task>();
		listUnfinishedTasks.add(testTask);
		listUnfinishedTasks.add(testTask2);

		// Checks if both lists have the same tasks
		assertEquals(listUnfinishedTasks, taskRepository.getUnFinishedTasks());

	}

	@Test
	public void testGetFinishedTasks() {

		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);

		// Adds user1 to the Task
		testTask.addTaskCollaboratorToTask(taskWorker1);
		testTask2.addTaskCollaboratorToTask(taskWorker1);
		testTask3.addTaskCollaboratorToTask(taskWorker1);

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
		testTask3.setStartDate(startDateTest);

		// set finished task
		testTask.setFinishDate(finishDateTest);
		testTask2.setFinishDate(finishDateTest);

		// mark task as Finished
		testTask.markTaskAsFinished();
		testTask2.markTaskAsFinished();

		// Creates a new list, and then added the unfished task
		List<Task> listFinishedTasks = new ArrayList<Task>();
		listFinishedTasks.add(testTask);
		listFinishedTasks.add(testTask2);

		// Checks if both lists have the same tasks
		assertEquals(listFinishedTasks, taskRepository.getFinishedTasks());

	}

	@Test
	public void testGetUnstartedTasks() {

		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);

		// Adds user1 to the Task
		testTask.addTaskCollaboratorToTask(taskWorker1);
		testTask2.addTaskCollaboratorToTask(taskWorker1);
		testTask3.addTaskCollaboratorToTask(taskWorker1);

		// create star date to test
		Calendar startDateTest = Calendar.getInstance();
		startDateTest.set(Calendar.YEAR, 2017);
		startDateTest.set(Calendar.MONTH, Calendar.NOVEMBER);
		startDateTest.set(Calendar.DAY_OF_MONTH, 29);
		startDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// Sets start date for testTask3
		testTask3.setStartDate(startDateTest);

		// Creates a new list, and then added the unfished task
		List<Task> listUnstartedTasks = new ArrayList<Task>();
		listUnstartedTasks.add(testTask);
		listUnstartedTasks.add(testTask2);

		// Checks if both lists have the same tasks
		assertEquals(listUnstartedTasks, taskRepository.getUnstartedTasks());

	}

	@Test
	public void testGetExpiredTasks() {

		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask4);
		taskRepository.addProjectTask(testTask5);
		taskRepository.addProjectTask(testTask6);
		taskRepository.addProjectTask(testTask7);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);

		// Marks testTask2 as finished
		testTask2.markTaskAsFinished();

		// Creates a new list, and then added the Expired tasks
		List<Task> listExpiredTasks = new ArrayList<Task>();
		listExpiredTasks.add(testTask5);
		listExpiredTasks.add(testTask6);
		listExpiredTasks.add(testTask7);

		// Checks if both lists have the same tasks
		assertEquals(listExpiredTasks, taskRepository.getExpiredTasks());

	}

	/**
	 * Tests the SortTaskListDecreasingOrder. Compares the output of the method to a
	 * list with the projects added be decreasing order.
	 */
	@Test
	public void testSortTaskListDecreasingOrder() {

		// Marks tasks as finished and sets a finish date
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(2017, Calendar.NOVEMBER, 14);
		testTask.setFinishDate(calendar1);
		testTask.markTaskAsFinished();

		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(2017, Calendar.NOVEMBER, 17);
		testTask2.setFinishDate(calendar2);
		testTask2.markTaskAsFinished();

		Calendar calendar3 = Calendar.getInstance();
		calendar3.set(2017, Calendar.SEPTEMBER, 17);
		testTask3.setFinishDate(calendar3);
		testTask3.markTaskAsFinished();

		// List with tasks not sorted.
		List<Task> toBeSorted = new ArrayList<>();
		toBeSorted.add(testTask);
		toBeSorted.add(testTask2);
		toBeSorted.add(testTask3);

		// creat list to compare
		List<Task> expResultTaskList = new ArrayList<>();

		// List of sorted tasks.
		expResultTaskList.add(testTask2);
		expResultTaskList.add(testTask);
		expResultTaskList.add(testTask3);

		assertEquals(expResultTaskList, projectRepository.sortTaskListDecreasingOrder(toBeSorted));
	}

	@Test
	public void testGetFinishedTasksInDecreasingOrder() {
		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);

		// Adds user1 to the Task
		testTask.addTaskCollaboratorToTask(taskWorker1);
		testTask2.addTaskCollaboratorToTask(taskWorker1);
		testTask3.addTaskCollaboratorToTask(taskWorker1);

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
		testTask3.setStartDate(startDateTest);

		// set finished task
		testTask.setFinishDate(finishDateTest);
		testTask2.setFinishDate(finishDateTest);

		// mark task as Finished
		testTask.markTaskAsFinished();
		testTask2.markTaskAsFinished();

		// Creates a new list, and then added the finished task
		List<Task> listFinishedTasks = new ArrayList<Task>();
		listFinishedTasks.add(testTask);
		listFinishedTasks.add(testTask2);

		// Checks if both lists have the same tasks
		assertEquals(listFinishedTasks, taskRepository.getFinishedTasks());

	}

	@Test
	public void testGetStartedNotFinishedTasksFromProjectCollaborator() {

		// create star date to test
		Calendar startDateTest = Calendar.getInstance();
		startDateTest.set(Calendar.YEAR, 2017);
		startDateTest.set(Calendar.MONTH, Calendar.NOVEMBER);
		startDateTest.set(Calendar.DAY_OF_MONTH, 29);
		startDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask4);
		taskRepository.addProjectTask(testTask5);
		taskRepository.addProjectTask(testTask6);
		taskRepository.addProjectTask(testTask7);

		// Adds user1 to the Task
		testTask.addTaskCollaboratorToTask(taskWorker1);
		testTask2.addTaskCollaboratorToTask(taskWorker1);
		testTask3.addTaskCollaboratorToTask(taskWorker1);
		testTask4.addTaskCollaboratorToTask(taskWorker1);
		testTask5.addTaskCollaboratorToTask(taskWorker1);
		testTask6.addTaskCollaboratorToTask(taskWorker1);
		testTask7.addTaskCollaboratorToTask(taskWorker1);

		// start tasks
		testTask.setStartDate(startDateTest);
		// testTask2 won't have a startDate
		// testTask2.setStartDate(startDateTest);
		testTask3.setStartDate(startDateTest);
		testTask4.setStartDate(startDateTest);
		testTask5.setStartDate(startDateTest);
		testTask6.setStartDate(startDateTest);

		// testTask7.setStartDate(startDateTest);

		// Marks testTask2 and testTask4 as finished
		testTask2.markTaskAsFinished();
		testTask4.markTaskAsFinished();

		/*
		 * Creates a new list, and then added the tasks that have a start date but
		 * weren't marked as finished
		 * 
		 */

		List<Task> getStartedNotFinishedTasks = new ArrayList<Task>();

		/*
		 * Adds the started and not finished tasks to that list
		 */
		getStartedNotFinishedTasks.add(testTask);
		getStartedNotFinishedTasks.add(testTask3);
		getStartedNotFinishedTasks.add(testTask5);
		getStartedNotFinishedTasks.add(testTask6);

		// Checks if both lists have the same tasks
		assertEquals(getStartedNotFinishedTasks,
				taskRepository.getStartedNotFinishedTasksFromProjectCollaborator(collab1));
		// Teste

	}

	/**
	 * Tests the getTaskByID method by creating 4 tasks that are equal to the tasks
	 * resulted from the getTaskByID, and asserting if they are equal to the
	 * original.
	 * 
	 */
	@Test
	public void getTaskByID() {

		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);

		// The TaskID is set automatically, so the the first task is 1.1, the second
		// task is 1.2 and so on.
		Task expResultTask = new Task(taskRepository.getTaskByID("1.1"));
		Task expResultTask2 = new Task(taskRepository.getTaskByID("1.2"));
		Task expResultTask3 = new Task(taskRepository.getTaskByID("1.3"));
		Task expResultTask4 = new Task(taskRepository.getTaskByID("1.4"));

		// Asserts if the task that resulted from the getTaskByID is equal to the
		// expected task
		assertEquals(testTask, expResultTask);
		assertEquals(testTask2, expResultTask2);
		assertEquals(testTask3, expResultTask3);
		assertEquals(testTask4, expResultTask4);
		assertEquals(taskRepository.getTaskByID("1.9"), null);
	}

	/**
	 * Tests the deleteTask method. If the State of the task is set to "Assigned",
	 * "Planned", "Created" or "Ready", the task can be deleted, else, the task
	 * won't be deleted
	 * 
	 */
	@Test
	public void deleteTaskTest() {

		// Creates 7 different State Objects

		OnGoing OnGoingTest = new OnGoing(testTask);
		Assigned AssignedTest = new Assigned(testTask);
		Created CreatedTest = new Created(testTask);
		Finished FinishedTest = new Finished(testTask);
		Planned PlannedTest = new Planned(testTask);
		Ready ReadyTest = new Ready(testTask);
		StandBy StandByTest = new StandBy(testTask);

		// Adds 5 tasks to the TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		taskRepository.addProjectTask(testTask5);

		/*
		 * States on which the task can be deleted - "ASSIGNED", "PLANNED" , "CREATED" ,
		 * "READY"
		 */

		// Sets the taskSate to "OnGoing"
		testTask.setTaskState(OnGoingTest);

		// Tries to delete the task
		taskRepository.deleteTask(testTask);

		// The task won't be deleted because the state of the Task is set to "OnGoing"
		assertTrue(taskRepository.getProjectTaskRepository().contains(testTask));

		// Sets the taskState to "ASSIGNED"
		testTask.setTaskState(AssignedTest);

		// Tries to delete the testTask
		taskRepository.deleteTask(testTask);

		// The task was deleted from the task repository
		assertFalse(taskRepository.getProjectTaskRepository().contains(testTask));

		// Verifies if task2 is in the TaskRepository
		assertTrue(taskRepository.getProjectTaskRepository().contains(testTask2));

		// sets the task State to "Created"
		testTask2.setTaskState(CreatedTest);

		// Tries to delete the testTask2
		taskRepository.deleteTask(testTask2);

		// Task2 was sucessfully deleted
		assertFalse(taskRepository.getProjectTaskRepository().contains(testTask2));

		// Verifies if task3 is in the TaskRepository
		assertTrue(taskRepository.getProjectTaskRepository().contains(testTask3));

		// sets the task State to "Finished"
		testTask3.setTaskState(FinishedTest);

		// Tries to delete the testTask3
		taskRepository.deleteTask(testTask3);

		// Task3 wasn't deleted from the repository
		assertTrue(taskRepository.getProjectTaskRepository().contains(testTask3));

		// Sets the state of testTask3 to "Planned"
		testTask3.setTaskState(PlannedTest);

		// Tries to delete the testTask3
		taskRepository.deleteTask(testTask3);

		// Task3 was sucessfully deleted from the TaskRepository
		assertFalse(taskRepository.getProjectTaskRepository().contains(testTask3));

		// Verifies if task4 is in the TaskRepository
		assertTrue(taskRepository.getProjectTaskRepository().contains(testTask4));

		// Sets the state of testTask4 to "Ready"
		testTask4.setTaskState(ReadyTest);

		// Tries to delete the testTask4
		taskRepository.deleteTask(testTask4);

		// Task4 was sucessfully deleted from the TaskRepository
		assertFalse(taskRepository.getProjectTaskRepository().contains(testTask4));

		// Verifies if task5 is in the TaskRepository
		assertTrue(taskRepository.getProjectTaskRepository().contains(testTask5));

		// Sets the state of testTask5 to "StandBy"
		testTask5.setTaskState(ReadyTest);

		// Tries to delete the testTask5
		taskRepository.deleteTask(testTask5);

		// Task5 was sucessfully deleted from the TaskRepository
		assertFalse(taskRepository.getProjectTaskRepository().contains(testTask5));

	}

	/**
	 * this test verify if the method getCancelledTasks return the correct list of
	 * cancelled tasks.
	 * 
	 */
	@Test
	public void getCancelledTasksTest() {

		// Adds 5 tasks to the TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);

		// Creates State Objects planned for task.
		Planned PlannedTestTask = new Planned(testTask);
		Planned PlannedTestTask2 = new Planned(testTask2);
		Planned PlannedTestTask3 = new Planned(testTask3);

		// set estimated task start date and task dead line to tasks
		testTask.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		testTask.setTaskDeadline(taskDeadlineDateTest);

		testTask2.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		testTask2.setTaskDeadline(taskDeadlineDateTest);

		testTask3.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		testTask3.setTaskDeadline(taskDeadlineDateTest);

		// set active user
		testTask.addTaskCollaboratorToTask(taskWorker1);
		testTask2.addTaskCollaboratorToTask(taskWorker1);
		testTask3.addTaskCollaboratorToTask(taskWorker1);

		// Sets the tasks to "Planned"
		testTask.setTaskState(PlannedTestTask);
		testTask2.setTaskState(PlannedTestTask2);
		testTask3.setTaskState(PlannedTestTask3);

		// Creates State Objects assigned for task.
		Assigned AssignedTestTask = new Assigned(testTask);
		Assigned AssignedTestTask2 = new Assigned(testTask2);
		Assigned AssignedTestTask3 = new Assigned(testTask3);

		// Sets the tasks to "Assigned"
		testTask.setTaskState(AssignedTestTask);
		testTask2.setTaskState(AssignedTestTask2);
		testTask3.setTaskState(AssignedTestTask3);

		// Creates State Objects Ready for task.
		Ready ReadyTestTask = new Ready(testTask);
		Ready ReadyTestTask2 = new Ready(testTask2);
		Ready ReadyTestTask3 = new Ready(testTask3);

		// set start date
		testTask.setStartDate(startDateTest);
		testTask2.setStartDate(startDateTest);
		testTask3.setStartDate(startDateTest);

		// Sets the tasks to "Ready"
		testTask.setTaskState(ReadyTestTask);
		testTask2.setTaskState(ReadyTestTask2);
		testTask3.setTaskState(ReadyTestTask3);

		// Creates State Objects OnGoing for task.
		OnGoing onGoingTestTask = new OnGoing(testTask);
		OnGoing onGoingTestTask2 = new OnGoing(testTask2);
		OnGoing onGoingTestTask3 = new OnGoing(testTask3);

		// Sets the tasks to "onGoing"
		testTask.setTaskState(onGoingTestTask);
		testTask2.setTaskState(onGoingTestTask2);
		testTask3.setTaskState(onGoingTestTask3);

		// Creates State Objects Cancelled for task.
		Cancelled cancelledTestTask = new Cancelled(testTask);
		Cancelled cancelledTestTask2 = new Cancelled(testTask2);

		// Sets the tasks to "cancelled"
		testTask.setTaskState(cancelledTestTask);
		testTask2.setTaskState(cancelledTestTask2);

		// create list with cancelled task to compare
		List<Task> cancelledTaskToCompare = new ArrayList<Task>();

		cancelledTaskToCompare.add(testTask);
		cancelledTaskToCompare.add(testTask2);

		assertEquals(cancelledTaskToCompare, taskRepository.getCancelledTasks());

	}
}
