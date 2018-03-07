package project.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.taskstateinterface.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class TaskContainerTest {

	UserContainer userContainer;
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
	ProjectContainer projectContainer;
	TaskContainer taskContainer;
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
		// creates an UserContainer
		userContainer = new UserContainer();

		// creates a ProjectsRepository
		projectContainer = new ProjectContainer();

		// create user
		userDan = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		userJoa = userContainer.createUser("Joao", "joao@gmail.com", "002", "collaborator", "920000000", "Rua",
				"2402-00", "Test", "Testo", "Testistan");
		userFra = userContainer.createUser("Francisco", "francisco@gmail.com", "003", "collaborator", "420000000", "Rua",
				"2402-00", "Test", "Testo", "Testistan");
		// create user admin
		userAdmin = userContainer.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");

		// Creates one Project
		project = projectContainer.createProject("name3", "description4", userAdmin);
		project2 = projectContainer.createProject("Project2", "description2", userAdmin);

		// create project collaborators
		collabDan = new ProjectCollaborator(userDan, 2);
		collabJoa = new ProjectCollaborator(userJoa, 3);
		collabFra = project2.createProjectCollaborator(userFra, 3);

		// create task workers
		taskWorkerDan = new TaskCollaborator(collabDan);
		taskWorkerJoa = new TaskCollaborator(collabJoa);
		taskWorkerFra = new TaskCollaborator(collabFra);

		// add user to user list
		userContainer.addUserToUserRepository(userDan);
		userContainer.addUserToUserRepository(userJoa);
		userContainer.addUserToUserRepository(userFra);

		userContainer.addUserToUserRepository(userAdmin);
		// set user as collaborator
		userDan.setUserProfile(Profile.COLLABORATOR);
		userJoa.setUserProfile(Profile.COLLABORATOR);
		userFra.setUserProfile(Profile.COLLABORATOR);

		userAdmin.setUserProfile(Profile.COLLABORATOR);
		// add user to project team
		project.addProjectCollaboratorToProjectTeam(collabDan);
		project.addProjectCollaboratorToProjectTeam(collabJoa);
		// create taskContainer
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

		// create 4 tasks
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

		// Adds Tasks to TaskContainer
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);
		taskContainer.addProjectTask(testTask4);

		// Creates a new List of Tasks, to compare with the getProjectTaskList of the
		// getProjectTaskList method
		List<Task> taskListToCompare = new ArrayList<Task>();

		// adds tasks to the task list
		taskListToCompare.add(testTask);
		taskListToCompare.add(testTask2);
		taskListToCompare.add(testTask3);
		taskListToCompare.add(testTask4);

		// See if the two lists have the same tasks
		assertEquals(taskContainer.getProjectTaskRepository(), taskListToCompare);

	}

	@Test
	public void testGetProjectTaskList() {

		// Adds Tasks to TaskContainer
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);
		taskContainer.addProjectTask(testTask4);

		// Creates a new List of Tasks, to compare with the getProjectTaskList of the
		// getProjectTaskList method
		List<Task> taskListToCompare = new ArrayList<Task>();

		// adds tasks to the task list
		taskListToCompare.add(testTask);
		taskListToCompare.add(testTask2);
		taskListToCompare.add(testTask3);
		taskListToCompare.add(testTask4);

		// See if the two lists have the same tasks
		assertEquals(taskContainer.getProjectTaskRepository(), taskListToCompare);

	}

	@Test
	public void testGetUnFinishedTasksFromUser() {
		// add task to task repository of the project
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);
		taskContainer.addProjectTask(testTask4);
		// add de user to the task
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

		// verify if test list is the same as the user unfinished task list
		assertEquals(test, taskContainer.getUnfinishedTasksFromProjectCollaborator(collabDan));
	}

	@Test
	public void testFinishedTaskListOfUserInProject() {

		// add task to task repository of the project
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);
		taskContainer.addProjectTask(testTask4);
		// adds the user to the task
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

		testTask.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask.addProjectCollaboratorToTask(collabDan);
		testTask.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setStartDate(projStartDate);
		testTask.getTaskState().changeToOnGoing();

		// pass from "OnGoing" to "Finished"
		Calendar testDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setFinishDate(testDate);
		testTask.getTaskState().changeToFinished();

		// testTask3 - set state as Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask3.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask3.setTaskDeadline(taskDeadline);

		testTask3.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask3.addProjectCollaboratorToTask(collabDan);
		testTask3.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask3.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		testTask3.setStartDate(projStartDate);
		testTask3.getTaskState().changeToOnGoing();

		// pass from "OnGoing" to "Finished"
		testTask3.setFinishDate(testDate);
		testTask3.getTaskState().changeToFinished();

		// Marks task and task3 as finished
		testTask.markTaskAsFinished();
		testTask3.markTaskAsFinished();

		// assures that the taskTest state is Finished
		assertEquals("Finished", testTask.viewTaskStateName());
		// assures that the taskTest3 state is Finished
		assertEquals("Finished", testTask3.viewTaskStateName());

		// create a list and add task to compare to unfinished task list
		List<Task> test = new ArrayList<Task>();
		test.add(testTask);
		test.add(testTask3);

		// verify if test list is the same as the user finished task list
		assertEquals(test, taskContainer.getFinishedTaskListofUserInProject(collabDan));

		// clears list
		test.clear();

		// Expects empty list, as collabJoa doesnt belong to Task team
		assertEquals(test, taskContainer.getFinishedTaskListofUserInProject(collabJoa));

	}

	@Test
	public void testGetFinishedTaskGivenMonth() {

		// create finished date to test
		Calendar startDateTest = Calendar.getInstance();
		startDateTest.set(Calendar.YEAR, 2017);
		startDateTest.set(Calendar.MONTH, Calendar.NOVEMBER);
		startDateTest.set(Calendar.DAY_OF_MONTH, 29);
		startDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create finish dates to test ("last month", and "this month")
		Calendar finishDateLastMonth = Calendar.getInstance();
		finishDateLastMonth.add(Calendar.MONTH, -1);
		Calendar finishDateThisMonth = Calendar.getInstance();

		// prepare the tasks
		testTask.getTaskState().changeToPlanned();
		testTask.addProjectCollaboratorToTask(collabDan);
		testTask.addProjectCollaboratorToTask(collabJoa);
		testTask.getTaskState().changeToAssigned();
		testTask.getTaskState().changeToReady();
		Calendar startDatetestTask = testTask.getEstimatedTaskStartDate();
		startDatetestTask.add(Calendar.DAY_OF_MONTH, 60);
		testTask.setStartDate(startDatetestTask);
		testTask.getTaskState().changeToOnGoing();
		testTask.createReport(testTask.getTaskTeam().get(0), Calendar.getInstance(), 0);
		testTask.getReports().get(0).setReportedTime(5);
		testTask.setFinishDate(finishDateLastMonth);
		testTask.getTaskState().changeToFinished();

		testTask2.getTaskState().changeToPlanned();
		testTask2.addProjectCollaboratorToTask(collabJoa);
		testTask2.addProjectCollaboratorToTask(collabDan);
		testTask2.getTaskState().changeToAssigned();
		testTask2.getTaskState().changeToReady();
		Calendar startDateTask2 = testTask2.getEstimatedTaskStartDate();
		startDateTask2.add(Calendar.DAY_OF_MONTH, 60);
		testTask2.setStartDate(startDatetestTask);
		testTask2.getTaskState().changeToOnGoing();
		testTask2.setFinishDate(finishDateLastMonth);
		testTask2.getTaskState().changeToFinished();

		testTask3.getTaskState().changeToPlanned();
		testTask3.addProjectCollaboratorToTask(collabDan);
		testTask3.getTaskState().changeToAssigned();
		testTask3.getTaskState().changeToReady();
		Calendar startDateTask3 = testTask3.getEstimatedTaskStartDate();
		startDateTask3.add(Calendar.DAY_OF_MONTH, 60);
		testTask3.setStartDate(startDatetestTask);
		testTask3.getTaskState().changeToOnGoing();
		testTask3.setFinishDate(finishDateLastMonth);
		testTask3.getTaskState().changeToFinished();

		testTask4.getTaskState().changeToPlanned();
		testTask4.addProjectCollaboratorToTask(collabDan);
		testTask4.getTaskState().changeToAssigned();
		testTask4.getTaskState().changeToReady();
		Calendar startDateTask4 = testTask4.getEstimatedTaskStartDate();
		startDateTask4.add(Calendar.DAY_OF_MONTH, 60);
		testTask4.setStartDate(startDatetestTask);
		testTask4.getTaskState().changeToOnGoing();
		testTask4.setFinishDate(finishDateThisMonth);
		testTask4.getTaskState().changeToFinished();

		// add task to task repository of the project
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);
		taskContainer.addProjectTask(testTask4);

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

		// add task to task repository of the project
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);

		// See if the tasks are contained in the Task Repository
		assertTrue(taskContainer.isTaskInRTaskRepository(testTask));
		assertFalse(taskContainer.isTaskInRTaskRepository(testTask4));

		// adds Task4 to the Repository
		taskContainer.addProjectTask(testTask4);

		// See if task4 is contained in the Task Repository
		assertTrue(taskContainer.isTaskInRTaskRepository(testTask4));

	}

	@Test
	public void testGetTimeOnLastMonthProjectUserTask() {
		// add task to task repository of the project
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);

		// create finished date to test
		Calendar startDateTest = Calendar.getInstance();
		startDateTest.set(Calendar.YEAR, 2017);
		startDateTest.set(Calendar.MONTH, Calendar.NOVEMBER);
		startDateTest.set(Calendar.DAY_OF_MONTH, 29);
		startDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create finished date to test
		Calendar finishDateTest = Calendar.getInstance();
		finishDateTest.add(Calendar.MONTH, -1);
		Calendar finishDateTest2 = Calendar.getInstance();
		finishDateTest2.add(Calendar.MONTH, -2);

		// Prepare the tasks

		testTask.getTaskState().changeToPlanned();
		testTask.addProjectCollaboratorToTask(collabDan);
		testTask.addProjectCollaboratorToTask(collabJoa);
		testTask.getTaskState().changeToAssigned();
		testTask.getTaskState().changeToReady();
		Calendar startDatetestTask = testTask.getEstimatedTaskStartDate();
		startDatetestTask.add(Calendar.DAY_OF_MONTH, 60);
		testTask.setStartDate(startDatetestTask);
		testTask.getTaskState().changeToOnGoing();
		testTask.createReport(testTask.getTaskTeam().get(0), Calendar.getInstance(), 0);
		testTask.getReports().get(0).setReportedTime(5);
		testTask.setFinishDate(finishDateTest);
		testTask.getTaskState().changeToFinished();

		testTask2.getTaskState().changeToPlanned();
		testTask2.addProjectCollaboratorToTask(collabJoa);
		testTask2.getTaskState().changeToAssigned();
		testTask2.getTaskState().changeToReady();
		Calendar startDateTask2 = testTask2.getEstimatedTaskStartDate();
		startDateTask2.add(Calendar.DAY_OF_MONTH, 60);
		testTask2.setStartDate(startDatetestTask);
		testTask2.getTaskState().changeToOnGoing();
		testTask2.setFinishDate(finishDateTest2);
		testTask2.getTaskState().changeToFinished();

		testTask3.getTaskState().changeToPlanned();
		testTask3.addProjectCollaboratorToTask(collabDan);
		testTask3.getTaskState().changeToAssigned();
		testTask3.getTaskState().changeToReady();
		Calendar startDateTask3 = testTask3.getEstimatedTaskStartDate();
		startDateTask3.add(Calendar.DAY_OF_MONTH, 60);
		testTask3.setStartDate(startDatetestTask);
		testTask3.getTaskState().changeToOnGoing();
		testTask3.setFinishDate(finishDateTest2);
		testTask3.getTaskState().changeToFinished();

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
		taskContainer.addProjectTask(testTask);
		taskContainer.setTaskCounter(1);
		taskContainer.addProjectTask(testTask2);
		taskContainer.setTaskCounter(2);
		taskContainer.addProjectTask(testTask3);
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
		assertEquals(project.getIdCode(), taskContainer.getProjId());

		// creates a new project
		projectContainer.addProjectToProjectContainer(project);
		TaskContainer anotherTaskContainer = project.getTaskRepository();
		assertEquals(project.getIdCode(), anotherTaskContainer.getProjId());
	}

	@Test
	public void testGetAllTaskOfUser() {
		// add task to task repository of the project
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);
		taskContainer.addProjectTask(testTask4);
		// adds the user to the task
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

		// See if the two taskLists have the same tasks
		assertEquals(testList, taskContainer.getAllTasksFromProjectCollaborator(collabDan));

	}

	@Test
	public void testIsThereUserWithoutTasks() {
		// adds task to task repository of the project
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);
		taskContainer.addProjectTask(testTask4);

		// Adds user to testTssk
		testTask.addTaskCollaboratorToTask(taskWorkerJoa);

		// Checks if the collabDan doesnt have any task assigned to him
		assertFalse(taskContainer.isCollaboratorActiveOnAnyTask(collabDan));

		// Checks if the collabJoa has tasks assigned to him
		assertTrue(taskContainer.isCollaboratorActiveOnAnyTask(collabJoa));
	}

	@Test
	public void testGetListofTasksWithoutCollaboratorsAssigned() {

		// adds task to task repository of the project
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);
		taskContainer.addProjectTask(testTask4);

		// adds User of index 1 to testTask and testTask4
		testTask.addTaskCollaboratorToTask(taskWorkerJoa);
		testTask4.addTaskCollaboratorToTask(taskWorkerJoa);

		// Creates a new list, and then added the tasks without any user assigned to
		// them
		List<Task> listTasksWithoutUser = new ArrayList<Task>();
		listTasksWithoutUser.add(testTask2);
		listTasksWithoutUser.add(testTask3);
		// Checks if both lists have the same tasks
		assertEquals(listTasksWithoutUser, taskContainer.getAllTasksWithoutCollaboratorsAssigned());

		// adds TaskWorker 1 to testTask2
		testTask2.addTaskCollaboratorToTask(taskWorkerDan);

		// Removes task2 from taskList to compare
		listTasksWithoutUser.remove(testTask2);

		// See if the both lists have the same tasks.
		assertEquals(listTasksWithoutUser, taskContainer.getAllTasksWithoutCollaboratorsAssigned());

		// removes collabDan from testTask2
		testTask2.removeProjectCollaboratorFromTask(collabDan);

		// clears the taskList
		listTasksWithoutUser.clear();

		// Adds testTask2 and testTask3 to the list
		listTasksWithoutUser.add(testTask2);
		listTasksWithoutUser.add(testTask3);

		// See if the both lists have the same tasks.
		assertEquals(listTasksWithoutUser, taskContainer.getAllTasksWithoutCollaboratorsAssigned());

	}

	@Test
	public void testGetUnFinishedTasks() {

		// Adds Tasks to TaskContainer
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);
		taskContainer.addProjectTask(testTask4);

		// Adds userDan to the Task
		testTask4.addTaskCollaboratorToTask(taskWorkerDan);

		// testTask - set state as OnGoing
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask.setTaskDeadline(taskDeadline);

		testTask.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask.addProjectCollaboratorToTask(collabDan);
		testTask.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setStartDate(projStartDate);
		testTask.getTaskState().changeToOnGoing();

		// testTask2 - set state as OnGoing
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask2.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask2.setTaskDeadline(taskDeadline);

		testTask2.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask2.addProjectCollaboratorToTask(collabDan);
		testTask2.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask2.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		testTask2.setStartDate(projStartDate);
		testTask2.getTaskState().changeToOnGoing();

		// testTask3 - set state as Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask3.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask3.setTaskDeadline(taskDeadline);

		testTask3.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask3.addProjectCollaboratorToTask(collabDan);
		testTask3.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask3.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		testTask3.setStartDate(projStartDate);
		testTask3.getTaskState().changeToOnGoing();

		// pass from "OnGoing" to "Finished"
		Calendar testDate = (Calendar) estimatedTaskStartDate.clone();
		testTask3.setFinishDate(testDate);
		testTask3.getTaskState().changeToFinished();

		// Marks testTask3 as finished
		testTask3.markTaskAsFinished();

		// Creates a new list, and then added the unfinished task
		List<Task> listUnfinishedTasks = new ArrayList<Task>();
		listUnfinishedTasks.add(testTask);
		listUnfinishedTasks.add(testTask2);

		// Checks if both lists have the same tasks
		assertEquals(listUnfinishedTasks, taskContainer.getUnFinishedTasks());

	}

	@Test
	public void testGetFinishedTasks() {

		// Adds Tasks to TaskContainer
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);

		// Adds userDan to the Task
		testTask3.addTaskCollaboratorToTask(taskWorkerDan);

		// testTask - set state as Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask.setTaskDeadline(taskDeadline);

		testTask.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask.addProjectCollaboratorToTask(collabDan);
		testTask.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setStartDate(projStartDate);
		testTask.getTaskState().changeToOnGoing();

		// pass from "OnGoing" to "Finished"
		Calendar testDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setFinishDate(testDate);
		testTask.getTaskState().changeToFinished();

		// testTask2 - set state as Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask2.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask2.setTaskDeadline(taskDeadline);

		testTask2.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask2.addProjectCollaboratorToTask(collabDan);
		testTask2.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask2.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		testTask2.setStartDate(projStartDate);
		testTask2.getTaskState().changeToOnGoing();

		// pass from "OnGoing" to "Finished"
		testTask2.setFinishDate(testDate);
		testTask2.getTaskState().changeToFinished();

		// mark task as Finished
		testTask.markTaskAsFinished();
		testTask2.markTaskAsFinished();

		// Creates a new list, and then added the finished tasks
		List<Task> listFinishedTasks = new ArrayList<Task>();
		listFinishedTasks.add(testTask);
		listFinishedTasks.add(testTask2);

		// Checks if both lists have the same tasks
		assertEquals(listFinishedTasks, taskContainer.getFinishedTasks());

	}

	@Test
	public void testGetUnstartedTasks() {

		// Adds Tasks to TaskContainer
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);

		// Adds userDan to the Task
		testTask.addTaskCollaboratorToTask(taskWorkerDan);
		testTask2.addTaskCollaboratorToTask(taskWorkerDan);
		testTask3.addTaskCollaboratorToTask(taskWorkerDan);

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
		assertEquals(listUnstartedTasks, taskContainer.getUnstartedTasks());

	}

	@Test
	public void testGetExpiredTasks() {

		// Adds Tasks to TaskContainer
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask4);
		taskContainer.addProjectTask(testTask5);
		taskContainer.addProjectTask(testTask6);
		taskContainer.addProjectTask(testTask7);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);

		// Marks testTask2 as finished
		testTask2.markTaskAsFinished();

		// Creates a new list, and then added the Expired tasks
		List<Task> listExpiredTasks = new ArrayList<Task>();
		listExpiredTasks.add(testTask5);
		listExpiredTasks.add(testTask6);
		listExpiredTasks.add(testTask7);

		// Checks if both lists have the same tasks
		assertEquals(listExpiredTasks, taskContainer.getExpiredTasks());

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

		assertEquals(expResultTaskList, projectContainer.sortTaskListDecreasingOrder(toBeSorted));
	}

	@Test
	public void testGetFinishedTasksInDecreasingOrder() {
		// Adds Tasks to TaskContainer
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);

		testTask3.addTaskCollaboratorToTask(taskWorkerDan);

		// testTask - set state as Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask.setTaskDeadline(taskDeadline);

		testTask.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask.addTaskCollaboratorToTask(taskWorkerDan);
		testTask.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setStartDate(projStartDate);
		testTask.getTaskState().changeToOnGoing();

		// pass from "OnGoing" to "Finished"
		Calendar testDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setFinishDate(testDate);
		testTask.getTaskState().changeToFinished();

		// testTask2 - set state as Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask2.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask2.setTaskDeadline(taskDeadline);

		testTask2.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask2.addTaskCollaboratorToTask(taskWorkerDan);
		testTask2.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask2.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		testTask2.setStartDate(projStartDate);
		testTask2.getTaskState().changeToOnGoing();

		// pass from "OnGoing" to "Finished"
		testTask2.setFinishDate(testDate);
		testTask2.getTaskState().changeToFinished();

		// assures that the taskTest state is Finished
		assertEquals("Finished", testTask2.viewTaskStateName());

		// mark task as Finished
		testTask.markTaskAsFinished();
		testTask2.markTaskAsFinished();

		// Creates a new list, and then added the finished task
		List<Task> listFinishedTasks = new ArrayList<Task>();
		listFinishedTasks.add(testTask);
		listFinishedTasks.add(testTask2);

		// Checks if both lists have the same tasks
		assertEquals(listFinishedTasks, taskContainer.getFinishedTasks());

	}

	@Test
	public void testGetStartedNotFinishedTasksFromProjectCollaborator() {

		// create start date to test
		Calendar startDateTest = Calendar.getInstance();
		startDateTest.set(Calendar.YEAR, 2017);
		startDateTest.set(Calendar.MONTH, Calendar.NOVEMBER);
		startDateTest.set(Calendar.DAY_OF_MONTH, 29);
		startDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// Adds Tasks to TaskContainer
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask3);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask4);
		taskContainer.addProjectTask(testTask5);
		taskContainer.addProjectTask(testTask6);
		taskContainer.addProjectTask(testTask7);

		// Adds userDan to the Task7
		testTask7.addTaskCollaboratorToTask(taskWorkerDan);

		// testTask - set state as OnGoing
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask.setTaskDeadline(taskDeadline);

		testTask.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask.addProjectCollaboratorToTask(collabDan);
		testTask.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setStartDate(projStartDate);
		testTask.getTaskState().changeToOnGoing();

		// testTask3 - set state as OnGoing
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask3.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask3.setTaskDeadline(taskDeadline);

		testTask3.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask3.addProjectCollaboratorToTask(collabDan);
		testTask3.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask3.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		testTask3.setStartDate(projStartDate);
		testTask3.getTaskState().changeToOnGoing();

		// testTask5 - set state as OnGoing
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask5.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask5.setTaskDeadline(taskDeadline);

		testTask5.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask5.addProjectCollaboratorToTask(collabDan);
		testTask5.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask5.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		testTask5.setStartDate(projStartDate);
		testTask5.getTaskState().changeToOnGoing();

		// testTask6 - set state as OnGoing
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask6.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask6.setTaskDeadline(taskDeadline);

		testTask6.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask6.addProjectCollaboratorToTask(collabDan);
		testTask6.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask6.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		testTask6.setStartDate(projStartDate);
		testTask6.getTaskState().changeToOnGoing();

		// testTask2 - set state as Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask2.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask2.setTaskDeadline(taskDeadline);

		testTask2.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask2.addProjectCollaboratorToTask(collabDan);
		testTask2.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask2.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		testTask2.setStartDate(projStartDate);
		testTask2.getTaskState().changeToOnGoing();

		// pass from "OnGoing" to "Finished"
		Calendar testDate = (Calendar) estimatedTaskStartDate.clone();
		testTask2.setFinishDate(testDate);
		testTask2.getTaskState().changeToFinished();

		// testTask4 - set state as Finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask4.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask4.setTaskDeadline(taskDeadline);

		testTask4.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask4.addProjectCollaboratorToTask(collabDan);
		testTask4.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask4.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		testTask4.setStartDate(projStartDate);
		testTask4.getTaskState().changeToOnGoing();

		// pass from "OnGoing" to "Finished"
		testTask4.setFinishDate(testDate);
		testTask4.getTaskState().changeToFinished();

		// Marks testTask2 and testTask4 as finished
		testTask2.markTaskAsFinished();
		testTask4.markTaskAsFinished();

		// Creates a new list, and then added the tasks that have a start date but
		// weren't marked as finished
		List<Task> getStartedNotFinishedTasks = new ArrayList<Task>();

		// Adds the started and not finished tasks to that list

		getStartedNotFinishedTasks.add(testTask);
		getStartedNotFinishedTasks.add(testTask3);
		getStartedNotFinishedTasks.add(testTask5);
		getStartedNotFinishedTasks.add(testTask6);

		// Checks if both lists have the same tasks
		assertEquals(getStartedNotFinishedTasks,
				taskContainer.getStartedNotFinishedTasksFromProjectCollaborator(collabDan));

	}

	/**
	 * Tests the getTaskByID method by creating 4 tasks that are equal to the tasks
	 * resulted from the getTaskByID, and asserting if they are equal to the
	 * original.
	 * 
	 */
	@Test
	public void getTaskByID() {

		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);
		taskContainer.addProjectTask(testTask4);

		// The TaskID is set automatically, so the the first task is 1.1, the second
		// task is 1.2 and so on.
		Task expResultTask = new Task(taskContainer.getTaskByID("1.1"));
		Task expResultTask2 = new Task(taskContainer.getTaskByID("1.2"));
		Task expResultTask3 = new Task(taskContainer.getTaskByID("1.3"));
		Task expResultTask4 = new Task(taskContainer.getTaskByID("1.4"));

		// Asserts if the task that resulted from the getTaskByID is equal to the
		// expected task
		assertEquals(testTask, expResultTask);
		assertEquals(testTask2, expResultTask2);
		assertEquals(testTask3, expResultTask3);
		assertEquals(testTask4, expResultTask4);
		assertEquals(taskContainer.getTaskByID("1.9"), null);
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
		StandBy StandByTest = new StandBy(testTask6);
		Cancelled CancelledTest = new Cancelled(testTask6);

		// Adds 5 tasks to the TaskContainer
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);
		taskContainer.addProjectTask(testTask4);
		taskContainer.addProjectTask(testTask5);
		taskContainer.addProjectTask(testTask6);

		/*
		 * States on which the task can be deleted - "ASSIGNED", "PLANNED" , "CREATED" ,
		 * "READY"
		 */

		// Sets the taskSate to "OnGoing"
		testTask.setTaskState(OnGoingTest);

		// Tries to delete the task
		assertFalse(taskContainer.deleteTask(testTask));

		// The task won't be deleted because the state of the Task is set to "OnGoing"
		assertTrue(taskContainer.getProjectTaskRepository().contains(testTask));

		// Sets the taskState to "ASSIGNED"
		testTask.setTaskState(AssignedTest);

		// Tries to delete the testTask
		assertTrue(taskContainer.deleteTask(testTask));

		// The task was deleted from the task repository
		assertFalse(taskContainer.getProjectTaskRepository().contains(testTask));

		// Verifies if task2 is in the TaskContainer
		assertTrue(taskContainer.getProjectTaskRepository().contains(testTask2));

		// sets the task State to "Created"
		testTask2.setTaskState(CreatedTest);

		// Tries to delete the testTask2
		assertTrue(taskContainer.deleteTask(testTask2));

		// Task2 was sucessfully deleted
		assertFalse(taskContainer.getProjectTaskRepository().contains(testTask2));

		// Verifies if task3 is in the TaskContainer
		assertTrue(taskContainer.getProjectTaskRepository().contains(testTask3));

		// sets the task State to "Finished"
		testTask3.setTaskState(FinishedTest);

		// Tries to delete the testTask3
		assertFalse(taskContainer.deleteTask(testTask3));

		// Task3 wasn't deleted from the repository
		assertTrue(taskContainer.getProjectTaskRepository().contains(testTask3));

		// Sets the state of testTask3 to "Planned"
		testTask3.setTaskState(PlannedTest);

		// Tries to delete the testTask3
		taskContainer.deleteTask(testTask3);

		// Task3 was sucessfully deleted from the TaskContainer
		assertFalse(taskContainer.getProjectTaskRepository().contains(testTask3));

		// Verifies if task4 is in the TaskContainer
		assertTrue(taskContainer.getProjectTaskRepository().contains(testTask4));

		// Sets the state of testTask4 to "Ready"
		testTask4.setTaskState(ReadyTest);

		// Tries to delete the testTask4
		assertTrue(taskContainer.deleteTask(testTask4));

		// Task4 was sucessfully deleted from the TaskContainer
		assertFalse(taskContainer.getProjectTaskRepository().contains(testTask4));

		// Verifies if task5 is in the TaskContainer
		assertTrue(taskContainer.getProjectTaskRepository().contains(testTask5));

		// Sets the state of testTask5 to "StandBy"
		testTask5.setTaskState(ReadyTest);

		// Tries to delete the testTask5
		assertTrue(taskContainer.deleteTask(testTask5));

		// Task5 was sucessfully deleted from the TaskContainer
		assertFalse(taskContainer.getProjectTaskRepository().contains(testTask5));

		// Sets testTak6 to state "StandBy"
		testTask6.setTaskState(StandByTest);
		// Verifies if task6 is in the TaskContainer
		assertTrue(taskContainer.getProjectTaskRepository().contains(testTask6));

		// Tries to delete the testTask6
		assertFalse(taskContainer.deleteTask(testTask6));

		// Task6 couldn't deleted from the TaskContainer because its set to state
		// "StandBy2
		assertTrue(taskContainer.getProjectTaskRepository().contains(testTask6));

		// Sets testTak6 to state "Cancelled"
		testTask6.setTaskState(CancelledTest);

		// Tries to delete the testTask6
		assertFalse(taskContainer.deleteTask(testTask6));

		// Task6 couldn't deleted from the TaskContainer because it's set to state
		// "Cancelled"
		assertTrue(taskContainer.getProjectTaskRepository().contains(testTask6));

	}

	/**
	 * this test verify if the method getCancelledTasks return the correct list of
	 * cancelled tasks.
	 * 
	 */
	@Test
	public void getCancelledTasksTest() {

		// Adds 5 tasks to the TaskContainer
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);

		// Creates State Objects planned for task.
		Planned PlannedTestTask = new Planned(testTask);
		Planned PlannedTestTask2 = new Planned(testTask2);
		Planned PlannedTestTask3 = new Planned(testTask3);

		// set estimated task start date and task dead line to tasks
		testTask.setEstimatedTaskStartDate(estimatedTaskStartDate);
		testTask.setTaskDeadline(taskDeadline);

		testTask2.setEstimatedTaskStartDate(estimatedTaskStartDate);
		testTask2.setTaskDeadline(taskDeadline);

		testTask3.setEstimatedTaskStartDate(estimatedTaskStartDate);
		testTask3.setTaskDeadline(taskDeadline);

		// set active user
		testTask.addTaskCollaboratorToTask(taskWorkerDan);
		testTask2.addTaskCollaboratorToTask(taskWorkerDan);
		testTask3.addTaskCollaboratorToTask(taskWorkerDan);

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

		assertEquals(cancelledTaskToCompare, taskContainer.getCancelledTasks());

	}

	/**
	 * this test verify if the method getTaskCost
	 * 
	 */
	@Test
	public void getTaskCostsTest() {

		// Adds 3 tasks to the TaskContainer
		taskContainer.addProjectTask(testTask);
		taskContainer.addProjectTask(testTask2);
		taskContainer.addProjectTask(testTask3);

		// creates a TaskReport (TaskWorker as an indexWork of 2)
		testTask.addTaskCollaboratorToTask(taskWorkerDan);
		testTask.createReport(taskWorkerDan, Calendar.getInstance(), 2);

		// Updates report Time to 30
		testTask.updateReportedTime(30, taskWorkerDan, 0);

		// Creates a new list of ReportCcost
		List<String> reportedCost = new ArrayList<>();
		double reportCost = 60;

		/*
		 * Adds the value of TaskWorker*Time spend (60) Other tasks have no reports
		 */

		reportedCost.add((String.valueOf(reportCost)));
		reportedCost.add("0.0");
		reportedCost.add("0.0");

		assertEquals(project.getTaskRepository().getReportedCostOfEachTask(), reportedCost);

	}
}