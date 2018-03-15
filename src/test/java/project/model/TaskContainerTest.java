package project.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Services.ProjectService;
import project.Services.TaskContainerService;
import project.Services.UserContainerService;
import project.model.taskstateinterface.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class TaskContainerTest {

	UserContainerService userContainer;
	User userDan;
	User userAdmin;
	User userJoa;
	User userFra;

	TaskCollaborator taskWorkerDan;
	TaskCollaborator taskWorkerJoa;
	TaskCollaborator taskWorkerFra;

	ProjectCollaborator collabDan;
	ProjectCollaborator collabJoa;
	ProjectCollaborator collabFra;

	Project project;
	Project project2;
	ProjectService projectContainer;
	TaskContainerService taskContainer;
	Task testTask;
	Task testTask2;
	Task testTask3;
	Task testTask4;
	Task testTask5;
	Task testTask6;
	Task testTask7;
	Calendar estimatedTaskStartDate, taskDeadline;
	Calendar startDateTest;

	@Before
	public void setUp() {

		userContainer = new UserContainerService();

		projectContainer = new ProjectService();

		userDan = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		userJoa = userContainer.createUser("Joao", "joao@gmail.com", "002", "collaborator", "920000000", "Rua",
				"2402-00", "Test", "Testo", "Testistan");
		userFra = userContainer.createUser("Francisco", "francisco@gmail.com", "003", "collaborator", "420000000", "Rua",
				"2402-00", "Test", "Testo", "Testistan");

		userAdmin = userContainer.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");

		project = projectContainer.createProject("name3", "description4", userAdmin);
		project2 = projectContainer.createProject("Project2", "description2", userAdmin);

		collabDan = new ProjectCollaborator(userDan, 2);
		collabJoa = new ProjectCollaborator(userJoa, 3);
		collabFra = project2.createProjectCollaborator(userFra, 3);

		taskWorkerDan = new TaskCollaborator(collabDan);
		taskWorkerJoa = new TaskCollaborator(collabJoa);
		taskWorkerFra = new TaskCollaborator(collabFra);

		userContainer.addUserToUserRepository(userDan);
		userContainer.addUserToUserRepository(userJoa);
		userContainer.addUserToUserRepository(userFra);
		userContainer.addUserToUserRepository(userAdmin);

		userDan.setUserProfile(Profile.COLLABORATOR);
		userJoa.setUserProfile(Profile.COLLABORATOR);
		userFra.setUserProfile(Profile.COLLABORATOR);
		userAdmin.setUserProfile(Profile.COLLABORATOR);

		project.addProjectCollaboratorToProjectTeam(collabDan);
		project.addProjectCollaboratorToProjectTeam(collabJoa);

		taskContainer = project.getTaskRepository();

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
		taskDeadlineDateTest.set(Calendar.YEAR, 2028);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 27);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a expired estimated Task Dead line Date
		Calendar taskExpiredDeadlineDateTest = Calendar.getInstance();
		taskExpiredDeadlineDateTest.set(Calendar.YEAR, 2017);
		taskExpiredDeadlineDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		taskExpiredDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskExpiredDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);


		testTask = taskContainer.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask2 = taskContainer.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask3 = taskContainer.createTask("Test moar yeh", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask4 = taskContainer.createTask("TEST HARDER!", 10, estimatedTaskStartDateTest, taskDeadlineDateTest, 10);
		testTask5 = taskContainer.createTask("TEST HARDER!", 10, estimatedTaskStartDateTest,
				taskExpiredDeadlineDateTest, 10);
		testTask6 = taskContainer.createTask("TEST HARDER!", 10, estimatedTaskStartDateTest,
				taskExpiredDeadlineDateTest, 10);
		testTask7 = taskContainer.createTask("TEST HARDER!", 10, estimatedTaskStartDateTest,
				taskExpiredDeadlineDateTest, 10);

	}

	@After
	public void tearDown() {

		userDan = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
		testTask4 = null;
		project = null;
		project2 = null;
		projectContainer = null;
		taskContainer = null;
		userContainer = null;
		taskWorkerDan = null;
		taskWorkerJoa = null;
		collabDan = null;
		collabJoa = null;
		collabFra = null;

	}

	@Test
	public void testCreateTask() {

		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);
		taskContainer.addTaskToProject(testTask4);

		// Creates a new List of Tasks, to compare with the getProjectTaskList of the
		// getProjectTaskList method
		List<Task> taskListToCompare = new ArrayList<Task>();
		taskListToCompare.add(testTask);
		taskListToCompare.add(testTask2);
		taskListToCompare.add(testTask3);
		taskListToCompare.add(testTask4);

		assertEquals(taskContainer.getAllTasksfromProject(), taskListToCompare);

	}

	@Test
	public void testGetProjectTaskList() {
		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);
		taskContainer.addTaskToProject(testTask4);

		// Creates a new List of Tasks, to compare with the getProjectTaskList of the
		// getProjectTaskList method
		List<Task> taskListToCompare = new ArrayList<Task>();
		taskListToCompare.add(testTask);
		taskListToCompare.add(testTask2);
		taskListToCompare.add(testTask3);
		taskListToCompare.add(testTask4);

		assertEquals(taskContainer.getAllTasksfromProject(), taskListToCompare);

	}

	@Test
	public void testGetUnFinishedTasksFromUser() {
		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);
		taskContainer.addTaskToProject(testTask4);

		testTask.addTaskCollaboratorToTask(taskWorkerDan);
		testTask2.addTaskCollaboratorToTask(taskWorkerDan);
		testTask3.addTaskCollaboratorToTask(taskWorkerDan);
		testTask4.addTaskCollaboratorToTask(taskWorkerDan);

		// create a list and add task to compare to unfinished task list
		List<Task> test = new ArrayList<Task>();
		test.add(testTask);
		test.add(testTask2);
		test.add(testTask3);
		test.add(testTask4);

		assertEquals(test, taskContainer.getUnfinishedTasksFromProjectCollaborator(collabDan));
	}

	@Test
	public void testFinishedTaskListOfUserInProject() {

		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);
		taskContainer.addTaskToProject(testTask4);

		testTask2.addTaskCollaboratorToTask(taskWorkerDan);
		testTask4.addTaskCollaboratorToTask(taskWorkerDan);

		// testTask - set state as Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask.setTaskDeadline(taskDeadline);

		testTask.setTaskState(new Planned());

		// necessary to pass from "Planned" to "Assigned"
		testTask.addProjectCollaboratorToTask(collabDan);


		// pass from "Assigned" to "Ready"
		testTask.setTaskState(new Ready());

		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setStartDate(projStartDate);
		testTask.setTaskState(new OnGoing());

		// pass from "OnGoing" to "Finished"
		Calendar testDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setFinishDate(testDate);
		testTask.setTaskState(new Finished());

		// testTask3 - set state as Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask3.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask3.setTaskDeadline(taskDeadline);

		testTask3.setTaskState(new Planned());

		// necessary to pass from "Planned" to "Assigned"
		testTask3.addProjectCollaboratorToTask(collabDan);


		// pass from "Assigned" to "Ready"
		testTask3.setTaskState(new Ready());

		// necessary to pass from "Ready" to "OnGoing"
		testTask3.setStartDate(projStartDate);
		testTask3.setTaskState(new OnGoing());

		// pass from "OnGoing" to "Finished"
		testTask3.setFinishDate(testDate);
		testTask3.setTaskState(new Finished());

		testTask.markTaskAsFinished();
		testTask3.markTaskAsFinished();

		assertEquals("Finished", testTask.viewTaskStateName());
		assertEquals("Finished", testTask3.viewTaskStateName());

		// create a list and add task to compare to unfinished task list
		List<Task> test = new ArrayList<Task>();
		test.add(testTask);
		test.add(testTask3);

		// verify if test list is the same as the user finished task list
		assertEquals(test, taskContainer.getFinishedTaskListOfUserInProject(collabDan));

		// clears list
		test.clear();

		assertEquals(test, taskContainer.getFinishedTaskListOfUserInProject(collabJoa));
	}

	@Test
	public void testGetFinishedTaskGivenMonth() {

		Calendar startDateTest = Calendar.getInstance();
		startDateTest.set(Calendar.YEAR, 2017);
		startDateTest.set(Calendar.MONTH, Calendar.NOVEMBER);
		startDateTest.set(Calendar.DAY_OF_MONTH, 29);
		startDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create finish dates to test ("last month", and "this month")
		Calendar finishDateLastMonth = Calendar.getInstance();
		finishDateLastMonth.add(Calendar.MONTH, -1);
		Calendar finishDateThisMonth = Calendar.getInstance();

		testTask.setTaskState(new Planned());
		testTask.addProjectCollaboratorToTask(collabDan);
		testTask.addProjectCollaboratorToTask(collabJoa);

		testTask.setTaskState(new Ready());
		Calendar startDatetestTask = testTask.getEstimatedTaskStartDate();
		startDatetestTask.add(Calendar.DAY_OF_MONTH, 60);
		testTask.setStartDate(startDatetestTask);
		testTask.setTaskState(new OnGoing());
		testTask.createReport(testTask.getTaskTeam().get(0), Calendar.getInstance(), 0);
		testTask.getReports().get(0).setReportedTime(5);
		testTask.setFinishDate(finishDateLastMonth);
		testTask.setTaskState(new Finished());

		testTask2.setTaskState(new Planned());
		testTask2.addProjectCollaboratorToTask(collabJoa);
		testTask2.addProjectCollaboratorToTask(collabDan);

		testTask2.setTaskState(new Ready());
		Calendar startDateTask2 = testTask2.getEstimatedTaskStartDate();
		startDateTask2.add(Calendar.DAY_OF_MONTH, 60);
		testTask2.setStartDate(startDatetestTask);
		testTask2.setTaskState(new OnGoing());
		testTask2.setFinishDate(finishDateLastMonth);
		testTask2.setTaskState(new Finished());

		testTask3.setTaskState(new Planned());
		testTask3.addProjectCollaboratorToTask(collabDan);

		testTask3.setTaskState(new Ready());
		Calendar startDateTask3 = testTask3.getEstimatedTaskStartDate();
		startDateTask3.add(Calendar.DAY_OF_MONTH, 60);
		testTask3.setStartDate(startDatetestTask);
		testTask3.setTaskState(new OnGoing());
		testTask3.setFinishDate(finishDateLastMonth);
		testTask3.setTaskState(new Finished());

		testTask4.setTaskState(new Planned());
		testTask4.addProjectCollaboratorToTask(collabDan);

		testTask4.setTaskState(new Ready());
		Calendar startDateTask4 = testTask4.getEstimatedTaskStartDate();
		startDateTask4.add(Calendar.DAY_OF_MONTH, 60);
		testTask4.setStartDate(startDatetestTask);
		testTask4.setTaskState(new OnGoing());
		testTask4.setFinishDate(finishDateThisMonth);
		testTask4.setTaskState(new Finished());

		// add task to task repository of the project
		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);
		taskContainer.addTaskToProject(testTask4);

		// given four finished tasks from Collaborator Daniel (collabDan)

		// when "Months ago" is set to "1" (last month)
		// then the expected list must contain 3 tasks
		assertEquals(3, taskContainer.getFinishedTasksFromProjectCollaboratorInGivenMonth(collabDan, 1).size());

		// when "Months ago" is set to "0" (this month)
		// then the expected list must contain 1 task
		assertEquals(1, taskContainer.getFinishedTasksFromProjectCollaboratorInGivenMonth(collabDan, 0).size());

		// when "Months ago" is set to "-1" (All months)
		// then the expected list must contain all 4 tasks
		assertEquals(4, taskContainer.getFinishedTasksFromProjectCollaboratorInGivenMonth(collabDan, -1).size());
	}

	@Test
	public void testContainsTask() {

		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);

		// See if the tasks are contained in the Task Repository
		assertTrue(taskContainer.isTaskInTaskContainer(testTask));
		assertFalse(taskContainer.isTaskInTaskContainer(testTask4));

		// adds Task4 to the Repository
		taskContainer.addTaskToProject(testTask4);

		// See if task4 is contained in the Task Repository
		assertTrue(taskContainer.isTaskInTaskContainer(testTask4));

	}

	@Test
	public void testGetTimeOnLastMonthProjectUserTask() {
		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);

		Calendar startDateTest = Calendar.getInstance();
		startDateTest.set(Calendar.YEAR, 2017);
		startDateTest.set(Calendar.MONTH, Calendar.NOVEMBER);
		startDateTest.set(Calendar.DAY_OF_MONTH, 29);
		startDateTest.set(Calendar.HOUR_OF_DAY, 14);

		Calendar finishDateTest = Calendar.getInstance();
		finishDateTest.add(Calendar.MONTH, -1);
		Calendar finishDateTest2 = Calendar.getInstance();
		finishDateTest2.add(Calendar.MONTH, -2);

		testTask.setTaskState(new Planned());
		testTask.addProjectCollaboratorToTask(collabDan);
		testTask.addProjectCollaboratorToTask(collabJoa);

		testTask.setTaskState(new Ready());
		Calendar startDatetestTask = testTask.getEstimatedTaskStartDate();
		startDatetestTask.add(Calendar.DAY_OF_MONTH, 60);
		testTask.setStartDate(startDatetestTask);
		testTask.setTaskState(new OnGoing());
		testTask.createReport(testTask.getTaskTeam().get(0), Calendar.getInstance(), 0);
		testTask.getReports().get(0).setReportedTime(5);
		testTask.setFinishDate(finishDateTest);
		testTask.setTaskState(new Finished());

		testTask2.setTaskState(new Planned());
		testTask2.addProjectCollaboratorToTask(collabJoa);

		testTask2.setTaskState(new Ready());
		Calendar startDateTask2 = testTask2.getEstimatedTaskStartDate();
		startDateTask2.add(Calendar.DAY_OF_MONTH, 60);
		testTask2.setStartDate(startDatetestTask);
		testTask2.setTaskState(new OnGoing());
		testTask2.setFinishDate(finishDateTest2);
		testTask2.setTaskState(new Finished());

		testTask3.setTaskState(new Planned());
		testTask3.addProjectCollaboratorToTask(collabDan);

		testTask3.setTaskState(new Ready());
		Calendar startDateTask3 = testTask3.getEstimatedTaskStartDate();
		startDateTask3.add(Calendar.DAY_OF_MONTH, 60);
		testTask3.setStartDate(startDatetestTask);
		testTask3.setTaskState(new OnGoing());
		testTask3.setFinishDate(finishDateTest2);
		testTask3.setTaskState(new Finished());

		// Checks if the 2 values are equal
		assertEquals(5.0, taskContainer.getTimeSpentByProjectCollaboratorInAllTasksLastMonth(collabDan), 0.001);
		// Checks if the 2 values are equal
		assertEquals(0.0, taskContainer.getTimeSpentByProjectCollaboratorInAllTasksLastMonth(collabJoa), 0.001);
		// Expects 0, as collabFra belongs to another Project
		assertEquals(0.0, taskContainer.getTimeSpentByProjectCollaboratorInAllTasksLastMonth(collabFra), 0.001);

	}

	@Test
	public void testGetTaskCounter() {
		// sets the task counter as 0;
		taskContainer.setTaskCounter(0);
		// add task to task repository of the project
		taskContainer.addTaskToProject(testTask);
		taskContainer.setTaskCounter(1);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.setTaskCounter(2);
		taskContainer.addTaskToProject(testTask3);
		taskContainer.setTaskCounter(3);

		// creates a variable with the value of the expected outcome of getTaskCounter
		// method in taskContainer class
		int expectedTaskCounter = 3;

		// Checks if the 2 values are equal
		assertEquals(expectedTaskCounter, taskContainer.getTaskCounter());
	}

	@Test
	public void testGetProjectId() {
		// checks if the project id are the same;
		assertEquals(project.getIdCode(), taskContainer.getProjectId());

		// creates a new project
		projectContainer.addProjectToProjectContainer(project);
		TaskContainerService anotherTaskContainer = project.getTaskRepository();
		assertEquals(project.getIdCode(), anotherTaskContainer.getProjectId());
	}

	@Test
	public void testGetAllTaskOfUser() {
		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);
		taskContainer.addTaskToProject(testTask4);

		testTask.addTaskCollaboratorToTask(taskWorkerDan);
		testTask2.addTaskCollaboratorToTask(taskWorkerDan);
		testTask3.addTaskCollaboratorToTask(taskWorkerDan);
		testTask4.addTaskCollaboratorToTask(taskWorkerDan);

		// Marks task and task3 as finished
		testTask.markTaskAsFinished();
		testTask3.markTaskAsFinished();

		// create a list and add tasks to compare with user task list
		List<Task> testList = new ArrayList<Task>();
		testList.add(testTask);
		testList.add(testTask2);
		testList.add(testTask3);
		testList.add(testTask4);

		assertEquals(testList, taskContainer.getAllTasksFromProjectCollaborator(collabDan));
	}

	@Test
	public void testIsThereUserWithoutTasks() {
		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);
		taskContainer.addTaskToProject(testTask4);

		testTask.addTaskCollaboratorToTask(taskWorkerJoa);

		assertFalse(taskContainer.isCollaboratorActiveOnAnyTask(collabDan));

		assertTrue(taskContainer.isCollaboratorActiveOnAnyTask(collabJoa));
	}

	@Test
	public void testGetListofTasksWithoutCollaboratorsAssigned() {
		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);
		taskContainer.addTaskToProject(testTask4);

		testTask.addTaskCollaboratorToTask(taskWorkerJoa);
		testTask4.addTaskCollaboratorToTask(taskWorkerJoa);

		// Creates a new list, and then added the tasks without any user assigned to
		// them
		List<Task> listTasksWithoutUser = new ArrayList<Task>();
		listTasksWithoutUser.add(testTask2);
		listTasksWithoutUser.add(testTask3);

		assertEquals(listTasksWithoutUser, taskContainer.getProjectTasksWithoutCollaboratorsAssigned());

		testTask2.addTaskCollaboratorToTask(taskWorkerDan);

		listTasksWithoutUser.remove(testTask2);

		assertEquals(listTasksWithoutUser, taskContainer.getProjectTasksWithoutCollaboratorsAssigned());

		testTask2.removeProjectCollaboratorFromTask(collabDan);

		// clears the taskList
		listTasksWithoutUser.clear();
		listTasksWithoutUser.add(testTask2);
		listTasksWithoutUser.add(testTask3);

		assertEquals(listTasksWithoutUser, taskContainer.getProjectTasksWithoutCollaboratorsAssigned());

	}

	@Test
	public void testGetUnFinishedTasks() {

		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);
		taskContainer.addTaskToProject(testTask4);

		testTask4.addTaskCollaboratorToTask(taskWorkerDan);

		// testTask - set state as OnGoing
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask.setTaskDeadline(taskDeadline);

		testTask.setTaskState(new Planned());

		// necessary to pass from "Planned" to "Assigned"
		testTask.addProjectCollaboratorToTask(collabDan);


		// pass from "Assigned" to "Ready"
		testTask.setTaskState(new Ready());

		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setStartDate(projStartDate);
		testTask.setTaskState(new OnGoing());

		// testTask2 - set state as OnGoing
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask2.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask2.setTaskDeadline(taskDeadline);

		testTask2.setTaskState(new Planned());

		// necessary to pass from "Planned" to "Assigned"
		testTask2.addProjectCollaboratorToTask(collabDan);


		// pass from "Assigned" to "Ready"
		testTask2.setTaskState(new Ready());

		// necessary to pass from "Ready" to "OnGoing"
		testTask2.setStartDate(projStartDate);
		testTask2.setTaskState(new OnGoing());

		// testTask3 - set state as Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask3.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask3.setTaskDeadline(taskDeadline);

		testTask3.setTaskState(new Planned());

		// necessary to pass from "Planned" to "Assigned"
		testTask3.addProjectCollaboratorToTask(collabDan);


		// pass from "Assigned" to "Ready"
		testTask3.setTaskState(new Ready());

		// necessary to pass from "Ready" to "OnGoing"
		testTask3.setStartDate(projStartDate);
		testTask3.setTaskState(new OnGoing());

		// pass from "OnGoing" to "Finished"
		Calendar testDate = (Calendar) estimatedTaskStartDate.clone();
		testTask3.setFinishDate(testDate);
		testTask3.setTaskState(new Finished());

		// Marks testTask3 as finished
		testTask3.markTaskAsFinished();

		// Creates a new list, and then added the unfinished task
		List<Task> listUnfinishedTasks = new ArrayList<Task>();
		listUnfinishedTasks.add(testTask);
		listUnfinishedTasks.add(testTask2);

		assertEquals(listUnfinishedTasks, taskContainer.getUnFinishedTasks());

	}

	@Test
	public void testGetFinishedTasks() {

		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);

		testTask3.addTaskCollaboratorToTask(taskWorkerDan);

		// testTask - set state as Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask.setTaskDeadline(taskDeadline);

		testTask.setTaskState(new Planned());

		// necessary to pass from "Planned" to "Assigned"
		testTask.addProjectCollaboratorToTask(collabDan);


		// pass from "Assigned" to "Ready"
		testTask.setTaskState(new Ready());

		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setStartDate(projStartDate);
		testTask.setTaskState(new OnGoing());

		// pass from "OnGoing" to "Finished"
		Calendar testDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setFinishDate(testDate);
		testTask.setTaskState(new Finished());

		// testTask2 - set state as Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask2.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask2.setTaskDeadline(taskDeadline);

		testTask2.setTaskState(new Planned());

		// necessary to pass from "Planned" to "Assigned"
		testTask2.addProjectCollaboratorToTask(collabDan);


		// pass from "Assigned" to "Ready"
		testTask2.setTaskState(new Ready());

		// necessary to pass from "Ready" to "OnGoing"
		testTask2.setStartDate(projStartDate);
		testTask2.setTaskState(new OnGoing());

		// pass from "OnGoing" to "Finished"
		testTask2.setFinishDate(testDate);
		testTask2.setTaskState(new Finished());

		// mark task as Finished
		testTask.markTaskAsFinished();
		testTask2.markTaskAsFinished();

		// Creates a new list, and then added the finished tasks
		List<Task> listFinishedTasks = new ArrayList<Task>();
		listFinishedTasks.add(testTask);
		listFinishedTasks.add(testTask2);

		assertEquals(listFinishedTasks, taskContainer.getFinishedTasks());
	}

	@Test
	public void testGetUnstartedTasks() {

		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);

		testTask.addTaskCollaboratorToTask(taskWorkerDan);
		testTask2.addTaskCollaboratorToTask(taskWorkerDan);
		testTask3.addTaskCollaboratorToTask(taskWorkerDan);

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

		assertEquals(listUnstartedTasks, taskContainer.getProjectUnstartedTasks());

	}

	@Test
	public void testGetExpiredTasks() {

		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask4);
		taskContainer.addTaskToProject(testTask5);
		taskContainer.addTaskToProject(testTask6);
		taskContainer.addTaskToProject(testTask7);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);

		testTask2.markTaskAsFinished();

		// Creates a new list, and then added the Expired tasks
		List<Task> listExpiredTasks = new ArrayList<Task>();
		listExpiredTasks.add(testTask5);
		listExpiredTasks.add(testTask6);
		listExpiredTasks.add(testTask7);

		assertEquals(listExpiredTasks, taskContainer.getProjectExpiredTasks());

	}


	@Test
	public void testSortTaskListDecreasingOrder() {

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
		expResultTaskList.add(testTask2);
		expResultTaskList.add(testTask);
		expResultTaskList.add(testTask3);

		assertEquals(expResultTaskList, projectContainer.sortTaskListDecreasingOrder(toBeSorted));
	}

	@Test
	public void testGetFinishedTasksInDecreasingOrder() {
		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);

		testTask3.addTaskCollaboratorToTask(taskWorkerDan);

		// testTask - set state as Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask.setTaskDeadline(taskDeadline);

		testTask.setTaskState(new Planned());

		// necessary to pass from "Planned" to "Assigned"
		testTask.addTaskCollaboratorToTask(taskWorkerDan);


		// pass from "Assigned" to "Ready"
		testTask.setTaskState(new Ready());

		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setStartDate(projStartDate);
		testTask.setTaskState(new OnGoing());

		// pass from "OnGoing" to "Finished"
		Calendar testDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setFinishDate(testDate);
		testTask.setTaskState(new Finished());

		// testTask2 - set state as Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask2.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask2.setTaskDeadline(taskDeadline);

		testTask2.setTaskState(new Planned());

		// necessary to pass from "Planned" to "Assigned"
		testTask2.addTaskCollaboratorToTask(taskWorkerDan);


		// pass from "Assigned" to "Ready"
		testTask2.setTaskState(new Ready());

		// necessary to pass from "Ready" to "OnGoing"
		testTask2.setStartDate(projStartDate);
		testTask2.setTaskState(new OnGoing());

		// pass from "OnGoing" to "Finished"
		testTask2.setFinishDate(testDate);
		testTask2.setTaskState(new Finished());

		// assures that the taskTest state is Finished
		assertEquals("Finished", testTask2.viewTaskStateName());

		// mark task as Finished
		testTask.markTaskAsFinished();
		testTask2.markTaskAsFinished();

		// Creates a new list, and then added the finished task
		List<Task> listFinishedTasks = new ArrayList<Task>();
		listFinishedTasks.add(testTask);
		listFinishedTasks.add(testTask2);

		assertEquals(listFinishedTasks, taskContainer.getFinishedTasks());

	}

	@Test
	public void testGetStartedNotFinishedTasksFromProjectCollaborator() {

		Calendar startDateTest = Calendar.getInstance();
		startDateTest.set(Calendar.YEAR, 2017);
		startDateTest.set(Calendar.MONTH, Calendar.NOVEMBER);
		startDateTest.set(Calendar.DAY_OF_MONTH, 29);
		startDateTest.set(Calendar.HOUR_OF_DAY, 14);

		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask3);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask4);
		taskContainer.addTaskToProject(testTask5);
		taskContainer.addTaskToProject(testTask6);
		taskContainer.addTaskToProject(testTask7);

		testTask7.addTaskCollaboratorToTask(taskWorkerDan);

		// testTask - set state as OnGoing
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask.setTaskDeadline(taskDeadline);

		testTask.setTaskState(new Planned());

		// necessary to pass from "Planned" to "Assigned"
		testTask.addProjectCollaboratorToTask(collabDan);


		// pass from "Assigned" to "Ready"
		testTask.setTaskState(new Ready());

		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setStartDate(projStartDate);
		testTask.setTaskState(new OnGoing());

		// testTask3 - set state as OnGoing
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask3.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask3.setTaskDeadline(taskDeadline);

		testTask3.setTaskState(new Planned());

		// necessary to pass from "Planned" to "Assigned"
		testTask3.addProjectCollaboratorToTask(collabDan);


		// pass from "Assigned" to "Ready"
		testTask3.setTaskState(new Ready());

		// necessary to pass from "Ready" to "OnGoing"
		testTask3.setStartDate(projStartDate);
		testTask3.setTaskState(new OnGoing());

		// testTask5 - set state as OnGoing
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask5.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask5.setTaskDeadline(taskDeadline);

		testTask5.setTaskState(new Planned());

		// necessary to pass from "Planned" to "Assigned"
		testTask5.addProjectCollaboratorToTask(collabDan);


		// pass from "Assigned" to "Ready"
		testTask5.setTaskState(new Ready());

		// necessary to pass from "Ready" to "OnGoing"
		testTask5.setStartDate(projStartDate);
		testTask5.setTaskState(new OnGoing());

		// testTask6 - set state as OnGoing
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask6.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask6.setTaskDeadline(taskDeadline);

		testTask6.setTaskState(new Planned());

		// necessary to pass from "Planned" to "Assigned"
		testTask6.addProjectCollaboratorToTask(collabDan);


		// pass from "Assigned" to "Ready"
		testTask6.setTaskState(new Ready());

		// necessary to pass from "Ready" to "OnGoing"
		testTask6.setStartDate(projStartDate);
		testTask6.setTaskState(new OnGoing());

		// testTask2 - set state as Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask2.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask2.setTaskDeadline(taskDeadline);

		testTask2.setTaskState(new Planned());

		// necessary to pass from "Planned" to "Assigned"
		testTask2.addProjectCollaboratorToTask(collabDan);


		// pass from "Assigned" to "Ready"
		testTask2.setTaskState(new Ready());

		// necessary to pass from "Ready" to "OnGoing"
		testTask2.setStartDate(projStartDate);
		testTask2.setTaskState(new OnGoing());

		// pass from "OnGoing" to "Finished"
		Calendar testDate = (Calendar) estimatedTaskStartDate.clone();
		testTask2.setFinishDate(testDate);
		testTask2.setTaskState(new Finished());

		// testTask4 - set state as Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask4.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask4.setTaskDeadline(taskDeadline);

		testTask4.setTaskState(new Planned());

		// necessary to pass from "Planned" to "Assigned"
		testTask4.addProjectCollaboratorToTask(collabDan);


		// pass from "Assigned" to "Ready"
		testTask4.setTaskState(new Ready());

		// necessary to pass from "Ready" to "OnGoing"
		testTask4.setStartDate(projStartDate);
		testTask4.setTaskState(new OnGoing());

		// pass from "OnGoing" to "Finished"
		testTask4.setFinishDate(testDate);
		testTask4.setTaskState(new Finished());

		// Marks testTask2 and testTask4 as finished
		testTask2.markTaskAsFinished();
		testTask4.markTaskAsFinished();

		// Creates a new list, and then added the tasks that have a start date but
		// weren't marked as finished
		List<Task> getStartedNotFinishedTasks = new ArrayList<Task>();
		getStartedNotFinishedTasks.add(testTask);
		getStartedNotFinishedTasks.add(testTask3);
		getStartedNotFinishedTasks.add(testTask5);
		getStartedNotFinishedTasks.add(testTask6);

		assertEquals(getStartedNotFinishedTasks,
				taskContainer.getStartedNotFinishedTasksFromProjectCollaborator(collabDan));

	}


	@Test
	public void getTaskByID() {

		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);
		taskContainer.addTaskToProject(testTask4);

		// The TaskID is set automatically, so the the first task is 1.1, the second
		// task is 1.2 and so on.
		Task expResultTask = new Task(taskContainer.getTaskByID("1.1"));
		Task expResultTask2 = new Task(taskContainer.getTaskByID("1.2"));
		Task expResultTask3 = new Task(taskContainer.getTaskByID("1.3"));
		Task expResultTask4 = new Task(taskContainer.getTaskByID("1.4"));

		assertEquals(testTask, expResultTask);
		assertEquals(testTask2, expResultTask2);
		assertEquals(testTask3, expResultTask3);
		assertEquals(testTask4, expResultTask4);
		assertEquals(taskContainer.getTaskByID("1.9"), null);
	}


	@Test
	public void deleteTaskTest() {

		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);
		taskContainer.addTaskToProject(testTask4);
		taskContainer.addTaskToProject(testTask5);
		taskContainer.addTaskToProject(testTask6);

		/*
		 * States on which the task can be deleted - "ASSIGNED", "PLANNED" , "CREATED" ,
		 * "READY"
		 */


		testTask.setTaskState(new OnGoing());
		assertFalse(taskContainer.deleteTask(testTask));

		// The task won't be deleted because the state of the Task is set to "OnGoing"
		assertTrue(taskContainer.getAllTasksfromProject().contains(testTask));



		assertTrue(taskContainer.deleteTask(testTask));
		assertFalse(taskContainer.getAllTasksfromProject().contains(testTask));
		assertTrue(taskContainer.getAllTasksfromProject().contains(testTask2));


		testTask2.setTaskState(new Created());


		assertTrue(taskContainer.deleteTask(testTask2));
		assertFalse(taskContainer.getAllTasksfromProject().contains(testTask2));
		assertTrue(taskContainer.getAllTasksfromProject().contains(testTask3));

		testTask3.setTaskState(new Finished());

		assertFalse(taskContainer.deleteTask(testTask3));
		assertTrue(taskContainer.getAllTasksfromProject().contains(testTask3));

		testTask3.setTaskState(new Planned());
		taskContainer.deleteTask(testTask3);

		assertFalse(taskContainer.getAllTasksfromProject().contains(testTask3));
		assertTrue(taskContainer.getAllTasksfromProject().contains(testTask4));

		testTask4.setTaskState(new Ready());

		assertTrue(taskContainer.deleteTask(testTask4));
		assertFalse(taskContainer.getAllTasksfromProject().contains(testTask4));
		assertTrue(taskContainer.getAllTasksfromProject().contains(testTask5));

		testTask5.setTaskState(new Ready());

		assertTrue(taskContainer.deleteTask(testTask5));
		assertFalse(taskContainer.getAllTasksfromProject().contains(testTask5));

		testTask6.setTaskState(new StandBy());
		assertTrue(taskContainer.getAllTasksfromProject().contains(testTask6));
		assertFalse(taskContainer.deleteTask(testTask6));
		assertTrue(taskContainer.getAllTasksfromProject().contains(testTask6));


		testTask6.setTaskState(new Cancelled());

		assertFalse(taskContainer.deleteTask(testTask6));
		assertTrue(taskContainer.getAllTasksfromProject().contains(testTask6));

	}


	@Test
	public void getCancelledTasksTest() {

		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);


		testTask.setEstimatedTaskStartDate(estimatedTaskStartDate);
		testTask.setTaskDeadline(taskDeadline);
		testTask2.setEstimatedTaskStartDate(estimatedTaskStartDate);
		testTask2.setTaskDeadline(taskDeadline);
		testTask3.setEstimatedTaskStartDate(estimatedTaskStartDate);
		testTask3.setTaskDeadline(taskDeadline);

		testTask.addTaskCollaboratorToTask(taskWorkerDan);
		testTask2.addTaskCollaboratorToTask(taskWorkerDan);
		testTask3.addTaskCollaboratorToTask(taskWorkerDan);

		testTask.setTaskState(new Planned());
		testTask2.setTaskState(new Planned());
		testTask3.setTaskState(new Planned());


		testTask.setStartDate(startDateTest);
		testTask2.setStartDate(startDateTest);
		testTask3.setStartDate(startDateTest);

		testTask.setTaskState(new Ready());
		testTask2.setTaskState(new Ready());
		testTask3.setTaskState(new Ready());


		testTask.setTaskState(new OnGoing());
		testTask2.setTaskState(new OnGoing());
		testTask3.setTaskState(new OnGoing());


		testTask.setTaskState(new Cancelled());
		testTask2.setTaskState(new Cancelled());

		// create list with cancelled task to compare
		List<Task> cancelledTaskToCompare = new ArrayList<Task>();
		cancelledTaskToCompare.add(testTask);
		cancelledTaskToCompare.add(testTask2);

		assertEquals(cancelledTaskToCompare, taskContainer.getCancelledTasksFromProject());

	}


	@Test
	public void getTaskCostsTest() {

		taskContainer.addTaskToProject(testTask);
		taskContainer.addTaskToProject(testTask2);
		taskContainer.addTaskToProject(testTask3);

		testTask.addTaskCollaboratorToTask(taskWorkerDan);
		testTask.createReport(taskWorkerDan, Calendar.getInstance(), 2);

		testTask.updateReportedTime(30, taskWorkerDan, 0);

		// Creates a new list of ReportCcost
		List<String> reportedCost = new ArrayList<>();
		double reportCost = 60;
		reportedCost.add((String.valueOf(reportCost)));
		reportedCost.add("0.0");
		reportedCost.add("0.0");

		assertEquals(project.getTaskRepository().getReportedCostOfEachTask(), reportedCost);
	}

	@Test
	public void setAndGetProjectTest(){
		testTask.setProject(project);

		assertEquals(project, testTask.getProject());
	}

	@Test
	public void sortTaskListDecreasingOrderTest(){
		Calendar finishDate1 = Calendar.getInstance();
		finishDate1.set(21, 02, 2017);
		testTask.setFinishDate(finishDate1);
		Calendar finishDate2 = Calendar.getInstance();
		finishDate1.set(21, 03, 2017);
		testTask2.setFinishDate(finishDate1);
		Calendar finishDate3 = Calendar.getInstance();
		finishDate1.set(21, 04, 2017);
		testTask3.setFinishDate(finishDate1);
		Calendar finishDate4 = Calendar.getInstance();
		finishDate1.set(21, 05, 2017);
		testTask4.setFinishDate(finishDate1);


		List<Task> listToSort = new ArrayList<>();
		listToSort.add(testTask4);
		listToSort.add(testTask2);
		listToSort.add(testTask);
		listToSort.add(testTask3);

		List<Task> expectedlist = new ArrayList<>();
		listToSort.add(testTask);
		listToSort.add(testTask2);
		listToSort.add(testTask3);
		listToSort.add(testTask4);

		assertEquals(expectedlist, taskContainer.sortTaskListDecreasingOrder(listToSort));
	}

	@Test
	public void getTaskListOfWhichDependenciesCanBeCreatedTest(){

	}
}
