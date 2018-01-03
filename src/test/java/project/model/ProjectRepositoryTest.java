package project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;;

public class ProjectRepositoryTest {

	ProjectRepository projectRepository;
	User user1;
	User user2;
	User user3;
	User userNotCollaborator;
	ProjectCollaborator collab1;
	ProjectCollaborator collab2;
	ProjectCollaborator collab3;
	ProjectCollaborator collab4;

	TaskCollaborator taskWorker1;
	TaskCollaborator taskWorker2;
	TaskCollaborator taskWorker3;
	TaskCollaborator taskWorker4;

	Project project1;
	Project project2;
	Project project3;
	Project project4;
	Project project5;
	Project project6;

	Task task1;
	Task task2;
	Task task3;
	Task task4;
	Task task5;
	Task task6;

	List<Project> expResultProjectList;
	List<Task> expResultTaskList;
	Calendar estimatedStartDate;
	Calendar taskDeadline;
	Calendar taskDeadlineDateTest;
	Calendar taskDeadlineDateTest2;
	Calendar taskDeadlineDateTest3;
	Calendar taskDeadlineDateTest4;
	Calendar taskDeadlineDateTest5;
	Calendar taskDeadlineDateTest6;

	@Before
	public void setUp() {

		projectRepository = new ProjectRepository();
		user1 = new User("name", "email@gmail.com", "idNumber", "function", "123456789");
		user2 = new User("name2", "email2@gmail.com", "idNumber2", "function2", "987654321");
		user3 = new User("name6", "email6@gmail.com", "idNumber6", "function6", "987654271");
		userNotCollaborator = new User("name7", "notcollaborator@gmail.com", "idNumber7", "function6", "987654271");

		collab1 = new ProjectCollaborator(user1, 1);
		collab2 = new ProjectCollaborator(user2, 2);
		collab3 = new ProjectCollaborator(user3, 3);

		// Another collaborator in project 2
		collab4 = new ProjectCollaborator(user1, 4);

		taskWorker1 = new TaskCollaborator(collab1);
		taskWorker2 = new TaskCollaborator(collab2);
		taskWorker3 = new TaskCollaborator(collab3);

		// User 1 is collab4 in project 2
		taskWorker4 = new TaskCollaborator(collab4);

		project1 = new Project(1, "name3", "description3", user1);
		project2 = new Project(2, "name4", "description5", user2);
		project3 = new Project(3, "name5", "description5", user3);
		project4 = new Project(4, "project4", "description5", user3);
		project5 = new Project(5, "project5", "description5", user3);
		project6 = new Project(6, "project6", "description5", user3);

		// create a estimated Task Start Date
		Calendar estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 25);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);
		// create a estimated Task Dead line Date
		// last deadline
		Calendar taskDeadlineDateTest = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.JANUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);
		// first deadline
		Calendar taskDeadlineDateTest2 = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.FEBRUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 28);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 15);
		// second deadline
		Calendar taskDeadlineDateTest3 = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.FEBRUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 28);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 16);
		// third deadline
		Calendar taskDeadlineDateTest4 = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.FEBRUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 28);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 17);
		// fourth deadline
		Calendar taskDeadlineDateTest6 = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.FEBRUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 28);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 18);
		// fifth deadline
		Calendar taskDeadlineDateTest5 = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.FEBRUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 28);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 19);

		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.set(2017, Calendar.JANUARY, 14);
		taskDeadline = Calendar.getInstance();
		taskDeadline.set(2017, Calendar.NOVEMBER, 17);
		task1 = new Task(111, 222, "Task 1", 50, estimatedTaskStartDateTest, taskDeadlineDateTest, 2000);
		task2 = new Task(112, 223, "Task 1", 50, estimatedTaskStartDateTest, taskDeadlineDateTest2, 2000);
		task3 = new Task(113, 224, "Task 1", 50, estimatedTaskStartDateTest, taskDeadlineDateTest3, 2000);
		task4 = new Task(213, 224, "Task 4", 50, estimatedTaskStartDateTest, taskDeadlineDateTest4, 2000);
		task5 = new Task(213, 224, "Task 5", 50, estimatedTaskStartDateTest, taskDeadlineDateTest5, 2000);
		task6 = new Task(213, 224, "Task 5", 50, estimatedTaskStartDateTest, taskDeadlineDateTest6, 2000);

		expResultProjectList = new ArrayList<Project>();
		expResultTaskList = new ArrayList<Task>();
	}

	@After
	public void tearDown() {

		projectRepository = null;
		user1 = null;
		user2 = null;
		user3 = null;
		project1 = null;
		project2 = null;
		project3 = null;
		project4 = null;
		project5 = null;
		project6 = null;

		taskDeadlineDateTest = null;
		taskDeadlineDateTest2 = null;
		taskDeadlineDateTest3 = null;
		taskDeadlineDateTest4 = null;
		taskDeadlineDateTest5 = null;
		taskDeadlineDateTest6 = null;

		task1 = null;
		task2 = null;
		task3 = null;
		task4 = null;
		task5 = null;
		task6 = null;

		taskWorker1 = null;
		taskWorker2 = null;
		taskWorker3 = null;
		taskWorker4 = null;

	}

	/**
	 * Tests the ProjectRepository constructor.
	 */
	@Test
	public void test_Constructor() {

		assertEquals(expResultProjectList, projectRepository.getAllProjects());

	}

	/**
	 * Tests the CreateProject method by calling the method equals (project) to
	 * assert if the project created is equal to other project. If the equals
	 * returns TRUE means the two projects are equal, so the creatProject method
	 * worked.
	 */
	@Test
	public void testCreateProject() {

		assertTrue(project1.equals(projectRepository.createProject("name3", "description3", user1)));

	}

	/**
	 * Tests the getProjCounter and SetProjectCounter at the same time. First the
	 * project counter is set to 10 and then is asserted if the getProjCounter
	 * method outputs the value 10.
	 */
	@Test
	public void test_getProjCounter() {

		projectRepository.setProjCounter(10);

		assertEquals(10, projectRepository.getProjCounter());
	}

	/**
	 * Tests the addProjectToProjectRepository by asserting if the list within the
	 * projectRepository is equal to a new list of projects created, after adding
	 * the same project to both lists.
	 */
	@Test
	public void testAddProjectToProjectRepository() {

		projectRepository.addProjectToProjectRepository(project1);

		expResultProjectList.add(project1);

		assertEquals(expResultProjectList, projectRepository.getAllProjects());

		// Tried to add again the same project
		projectRepository.addProjectToProjectRepository(project1);

		/*
		 * The result will be the same as before, as the method doesn't allow to add
		 * projects that already exist in the projectRepository
		 */

		assertEquals(expResultProjectList, projectRepository.getAllProjects());

	}

	/**
	 * Tests the getAllProjects by asserting if the list within the
	 * projectRepository is equal to a new list of projects created.
	 */
	@Test
	public void testGetAllProjects() {

		projectRepository.addProjectToProjectRepository(project1);

		expResultProjectList.add(project1);

		assertEquals(expResultProjectList, projectRepository.getAllProjects());
	}

	/**
	 * This test compares a list (expResult) with only active projects to the list
	 * returned by the method getActiveProjects, which has to output only the active
	 * projects from the repository.
	 */
	@Test
	public void testGetActiveProjects() {

		// Adds the projects to the project repository
		projectRepository.addProjectToProjectRepository(project1);
		projectRepository.addProjectToProjectRepository(project2);
		projectRepository.addProjectToProjectRepository(project3);
		projectRepository.addProjectToProjectRepository(project4);
		projectRepository.addProjectToProjectRepository(project5);
		projectRepository.addProjectToProjectRepository(project6);

		// Sets the project status to Planning (0, Not active)
		project1.setProjectStatus(0);

		// Sets the project status to Initiation (1, Active)
		project2.setProjectStatus(1);

		// Sets the project status to Executing (2, Active)
		project3.setProjectStatus(2);

		// Sets the project status to Delivery (3, Active)
		project4.setProjectStatus(3);

		// Sets the project status to Review (4, Active)
		project5.setProjectStatus(4);

		// Sets the project status to Close (5, Active)
		project6.setProjectStatus(5);

		// Adds the projects to the expResult list to be compared to.
		expResultProjectList.add(project2);
		expResultProjectList.add(project3);
		expResultProjectList.add(project4);
		expResultProjectList.add(project5);

		assertEquals(expResultProjectList, projectRepository.getActiveProjects());
	}

	/**
	 * Tests the getUserTasks. The list returned has to be equal to the
	 * expResultTaskList created.
	 */
	@Test
	public void testGetUserTasks() {

		// Adds project to project repository
		projectRepository.addProjectToProjectRepository(project1);

		// Adds user to project team
		project1.addProjectCollaboratorToProjectTeam(collab1);
		project1.addProjectCollaboratorToProjectTeam(collab2);

		// Adds tasks to project repository.
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);
		project1.getTaskRepository().addProjectTask(task3);

		// Adds user to tasks.
		task1.addTaskCollaboratorToTask(taskWorker1);
		task3.addTaskCollaboratorToTask(taskWorker1);

		// Adds user to expResultTaskList
		expResultTaskList.add(task1);
		expResultTaskList.add(task3);

		assertEquals(expResultTaskList, projectRepository.getUserTasks(user1));

		// Clears list
		expResultTaskList.clear();

		// returns an empty list, as the user is not a collaborator
		assertEquals(expResultTaskList, projectRepository.getUserTasks(userNotCollaborator));

		/*
		 * returns an empty list, as the user is a collaborator but doesnt have any task
		 * associated to him
		 */
		assertEquals(expResultTaskList, projectRepository.getUserTasks(user2));

	}

	/**
	 * Tests the getFinishedUserTaskList method. Has to return the finished tasks
	 * from an user.
	 */
	@Test
	public void testGetFinishedUserTaskList() {

		// Adds project to project repository
		projectRepository.addProjectToProjectRepository(project1);

		// Adds user to project team
		project1.addProjectCollaboratorToProjectTeam(collab1);
		project1.addProjectCollaboratorToProjectTeam(collab2);

		// Adds tasks to project repository.
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);
		project1.getTaskRepository().addProjectTask(task3);

		// Adds user to tasks.
		task1.addTaskCollaboratorToTask(taskWorker1);
		task2.addTaskCollaboratorToTask(taskWorker1);
		task3.addTaskCollaboratorToTask(taskWorker1);

		// Marks tasks as finished
		task1.markTaskAsFinished();
		task1.setFinishDate();
		task3.markTaskAsFinished();
		task3.setFinishDate();

		// Adds user to expResultTaskList
		expResultTaskList.add(task1);
		expResultTaskList.add(task3);

		assertEquals(expResultTaskList, projectRepository.getAllFinishedTasksFromUser(user1));

		// Clears list
		expResultTaskList.clear();

		// returns an empty list, as the user is not a collaborator
		assertEquals(expResultTaskList, projectRepository.getAllFinishedTasksFromUser(userNotCollaborator));

	}

	/**
	 * Tests the getUnifinishedUserTaskList method. Has to return a list with the
	 * unfinished tasks from an user.
	 */
	@Test
	public void testGetUnfinishedUserTaskList() {

		// Adds project to project repository
		projectRepository.addProjectToProjectRepository(project1);

		// Adds user to project team
		project1.addProjectCollaboratorToProjectTeam(collab1);
		project1.addProjectCollaboratorToProjectTeam(collab2);

		// Adds tasks to project repository.
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);
		project1.getTaskRepository().addProjectTask(task3);

		// Adds user to tasks.
		task1.addTaskCollaboratorToTask(taskWorker1);
		task2.addTaskCollaboratorToTask(taskWorker1);
		task3.addTaskCollaboratorToTask(taskWorker1);

		// Marks tasks as finished
		task1.markTaskAsFinished();
		task1.setFinishDate();

		// Adds user to expResultTaskList
		expResultTaskList.add(task2);
		expResultTaskList.add(task3);

		assertEquals(expResultTaskList, projectRepository.getUnfinishedUserTaskList(user1));

		// Clears list
		expResultTaskList.clear();

		// returns an empty list, as the user is not a collaborator
		assertEquals(expResultTaskList, projectRepository.getUnfinishedUserTaskList(userNotCollaborator));

	}

	/**
	 * Tests the getLastMonthFinishedUserTaskList by comparing the output of that
	 * method with a list created with tasks finished last month.
	 */
	@Test
	public void testGetLastMonthFinishedUserTaskList() {

		// Adds project to project repository
		projectRepository.addProjectToProjectRepository(project1);

		// Adds user to project team
		project1.addProjectCollaboratorToProjectTeam(collab1);
		project1.addProjectCollaboratorToProjectTeam(collab2);

		// Adds tasks to project repository.
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);
		project1.getTaskRepository().addProjectTask(task3);

		// Adds user to tasks.
		task1.addTaskCollaboratorToTask(taskWorker1);
		task2.addTaskCollaboratorToTask(taskWorker1);
		task3.addTaskCollaboratorToTask(taskWorker1);

		// Marks tasks as finished
		Calendar calendar1 = Calendar.getInstance();
		calendar1.add(Calendar.MONTH, -1);
		task1.setFinishDate(calendar1);
		task1.markTaskAsFinished();

		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.MONTH, -1);
		task2.setFinishDate(calendar2);
		task2.markTaskAsFinished();

		Calendar calendar3 = Calendar.getInstance();
		calendar3.set(2017, Calendar.SEPTEMBER, 17);
		task3.setFinishDate(calendar3);
		task3.markTaskAsFinished();

		// Adds user to expResultTaskList
		expResultTaskList.add(task1);
		expResultTaskList.add(task2);

		assertEquals(expResultTaskList, projectRepository.getLastMonthFinishedUserTaskList(user1));

		// Clears list
		expResultTaskList.clear();

		// returns an empty list, as the user is not a collaborator
		assertEquals(expResultTaskList, projectRepository.getLastMonthFinishedUserTaskList(userNotCollaborator));

	}

	/**
	 * Tests the getTotalTimeLastMonthFinishedTasksByUser. The result has to be
	 * equal to the sum of time spent on every task by the user.
	 */
	@Test
	public void testGetTotalTimeLastMonthFinishedTasksByUser() {
		// Adds project to project repository
		projectRepository.addProjectToProjectRepository(project1);

		// Adds user to project team
		project1.addProjectCollaboratorToProjectTeam(collab1);

		// Adds tasks to project repository.
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);

		// Adds user to tasks.
		task1.addTaskCollaboratorToTask(taskWorker1);
		task2.addTaskCollaboratorToTask(taskWorker1);

		// Sets a startDate for the tasks

		Calendar startDate = Calendar.getInstance();
		startDate.add(Calendar.MONTH, -3);
		task1.setStartDate(startDate);
		task2.setStartDate(startDate);

		// Marks tasks as finished
		Calendar calendar1 = Calendar.getInstance();
		calendar1.add(Calendar.MONTH, -1);
		task1.setFinishDate(calendar1);
		task1.markTaskAsFinished();

		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.MONTH, -1);
		task2.setFinishDate(calendar2);
		task2.markTaskAsFinished();

		task1.createReport(taskWorker1);
		task2.createReport(taskWorker1);

		task1.getReports().get(0).setReportedTime(5);
		task2.getReports().get(0).setReportedTime(10);

		assertEquals(15.0, projectRepository.getTotalTimeOfFinishedTasksFromUserLastMonth(user1), 0.000000001);
	}

	/**
	 * Tests the GetAverageTimeLastMonthFinishedTasksUser. The result has to be
	 * equal to the sum of time spent on every task by the user divided by the
	 * number of tasks.
	 */
	@Test
	public void testGetAverageTimeLastMonthFinishedTasksUser() {

		// Adds project to project repository
		projectRepository.addProjectToProjectRepository(project1);

		// Adds user to project team
		project1.addProjectCollaboratorToProjectTeam(collab1);

		// Adds tasks to project repository.
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);

		// Adds user to tasks.
		task1.addTaskCollaboratorToTask(taskWorker1);
		task2.addTaskCollaboratorToTask(taskWorker1);

		// Sets a startDate for the tasks

		Calendar startDate = Calendar.getInstance();
		startDate.add(Calendar.MONTH, -3);
		task1.setStartDate(startDate);
		task2.setStartDate(startDate);

		// Marks tasks as finished
		Calendar calendar1 = Calendar.getInstance();
		calendar1.add(Calendar.MONTH, -1);
		task1.setFinishDate(calendar1);
		task1.markTaskAsFinished();

		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.MONTH, -1);
		task2.setFinishDate(calendar2);
		task2.markTaskAsFinished();

		task1.createReport(taskWorker1);
		task2.createReport(taskWorker1);

		task1.getReports().get(0).setReportedTime(5);
		task2.getReports().get(0).setReportedTime(10);

		assertEquals(7.5, projectRepository.getAverageTimeOfFinishedTasksFromUserLastMonth(user1), 0.000000001);
	}

	/**
	 * Tests the GetLastMonthFinishedUserTaskListDecreasingOrder. Compares the
	 * output of the method to a list with the projects added be decreasing order.
	 */
	@Test
	public void testGetLastMonthFinishedUserTaskListDecreasingOrder() {

		// Adds project to project repository
		projectRepository.addProjectToProjectRepository(project1);

		// Adds user to project team
		project1.addProjectCollaboratorToProjectTeam(collab1);
		project1.addProjectCollaboratorToProjectTeam(collab2);

		// Adds tasks to project repository.
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);
		project1.getTaskRepository().addProjectTask(task3);

		// Adds user to tasks.
		task1.addTaskCollaboratorToTask(taskWorker1);
		task2.addTaskCollaboratorToTask(taskWorker1);
		task3.addTaskCollaboratorToTask(taskWorker1);

		// Marks tasks as finished
		Calendar calendar1 = Calendar.getInstance();
		calendar1.add(Calendar.MONTH, -1);
		calendar1.set(Calendar.HOUR, 15);
		task1.setFinishDate(calendar1);
		task1.markTaskAsFinished();

		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.MONTH, -1);
		calendar2.set(Calendar.HOUR, 16);
		task2.setFinishDate(calendar2);
		task2.markTaskAsFinished();

		Calendar calendar3 = Calendar.getInstance();
		calendar3.set(2017, Calendar.SEPTEMBER, 17);
		task3.setFinishDate(calendar3);
		task3.markTaskAsFinished();

		// Adds user to expResultTaskList
		expResultTaskList.add(task2);
		expResultTaskList.add(task1);

		assertEquals(expResultTaskList, projectRepository.getFinishedUserTasksFromLastMonthInDecreasingOrder(user1));
	}

	/**
	 * Tests the GetFinishedTaskListByDecreasingOrder. Compares the output of the
	 * method to a list with the projects added be decreasing order.
	 */
	@Test
	public void testGetFinishedTaskListByDecreasingOrder() {

		// Adds project to project repository
		projectRepository.addProjectToProjectRepository(project1);

		// Adds user to project team
		project1.addProjectCollaboratorToProjectTeam(collab1);
		project1.addProjectCollaboratorToProjectTeam(collab2);

		// Adds tasks to project repository.
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);
		project1.getTaskRepository().addProjectTask(task3);

		// Adds user to tasks.
		task1.addTaskCollaboratorToTask(taskWorker1);
		task2.addTaskCollaboratorToTask(taskWorker1);
		task3.addTaskCollaboratorToTask(taskWorker1);

		// Marks tasks as finished
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(2017, Calendar.NOVEMBER, 14);
		task1.setFinishDate(calendar1);
		task1.markTaskAsFinished();

		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(2017, Calendar.NOVEMBER, 17);
		task2.setFinishDate(calendar2);
		task2.markTaskAsFinished();

		Calendar calendar3 = Calendar.getInstance();
		calendar3.set(2017, Calendar.SEPTEMBER, 17);
		task3.setFinishDate(calendar3);
		task3.markTaskAsFinished();

		// Adds user to expResultTaskList
		expResultTaskList.add(task2);
		expResultTaskList.add(task1);
		expResultTaskList.add(task3);

		assertEquals(expResultTaskList, projectRepository.getAllFinishedUserTasksInDecreasingOrder(user1));
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
		task1.setFinishDate(calendar1);
		task1.markTaskAsFinished();

		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(2017, Calendar.NOVEMBER, 17);
		task2.setFinishDate(calendar2);
		task2.markTaskAsFinished();

		Calendar calendar3 = Calendar.getInstance();
		calendar3.set(2017, Calendar.SEPTEMBER, 17);
		task3.setFinishDate(calendar3);
		task3.markTaskAsFinished();

		// List with tasks not sorted.
		List<Task> toBeSorted = new ArrayList<Task>();
		toBeSorted.add(task1);
		toBeSorted.add(task2);
		toBeSorted.add(task3);

		// List of sorted tasks.
		expResultTaskList.add(task2);
		expResultTaskList.add(task1);
		expResultTaskList.add(task3);

		assertEquals(expResultTaskList, projectRepository.sortTaskListDecreasingOrder(toBeSorted));
	}

	/**
	 * test to isProjectInProjectRepository.
	 */
	@Test
	public void test_isProjectInProjectRepository() {
		// before add the project, verify if the project1 is in project repository
		assertFalse(projectRepository.isProjectInProjectRepository(project1));

		// add project 1 to project repository
		projectRepository.addProjectToProjectRepository(project1);

		// verify if project1 it was add to project repository
		assertTrue(projectRepository.isProjectInProjectRepository(project1));
	}

	@Test
	public void getStartedNotFinishedUserTaskInIncreasingDeadlineOrder() {

		// Adds project to project repository
		projectRepository.addProjectToProjectRepository(project1);
		projectRepository.addProjectToProjectRepository(project2);

		// Adds user to project team
		project1.addProjectCollaboratorToProjectTeam(collab1);
		project1.addProjectCollaboratorToProjectTeam(collab2);

		// Adds user 1(collab4) - to project2
		project2.addProjectCollaboratorToProjectTeam(collab4);

		// Adds tasks to project repository 1
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);
		project1.getTaskRepository().addProjectTask(task3);

		// Adds tasks to project repository 2
		project2.getTaskRepository().addProjectTask(task4);
		project2.getTaskRepository().addProjectTask(task5);
		project2.getTaskRepository().addProjectTask(task6);

		// Adds user1 to tasks in Project 1
		task1.addTaskCollaboratorToTask(taskWorker1);
		task2.addTaskCollaboratorToTask(taskWorker1);
		task3.addTaskCollaboratorToTask(taskWorker1);

		Calendar estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 25);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);

		task1.setStartDate(estimatedTaskStartDateTest);
		task2.setStartDate(estimatedTaskStartDateTest);
		task3.setStartDate(estimatedTaskStartDateTest);
		task4.setStartDate(estimatedTaskStartDateTest);
		task5.setStartDate(estimatedTaskStartDateTest);
		task6.setStartDate(estimatedTaskStartDateTest);

		// Adds user1 to tasks in Project 2
		task4.addTaskCollaboratorToTask(taskWorker4);
		task5.addTaskCollaboratorToTask(taskWorker4);
		task6.addTaskCollaboratorToTask(taskWorker4);

		task2.markTaskAsFinished();
		task6.markTaskAsFinished();

		// creates a new list of tasks in increasingDeadLineOrder
		List<Task> startedNotFinishedTasksInOrder = new ArrayList<>();
		startedNotFinishedTasksInOrder.add(task3);
		startedNotFinishedTasksInOrder.add(task4);
		startedNotFinishedTasksInOrder.add(task5);
		startedNotFinishedTasksInOrder.add(task1);

		assertEquals(startedNotFinishedTasksInOrder,
				projectRepository.getStartedNotFinishedUserTasksInIncreasingDeadlineOrder(user1));

	}

}
