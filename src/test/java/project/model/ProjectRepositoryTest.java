package project.model;

import static org.junit.Assert.assertEquals;
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
	ProjectCollaborator collab1;
	ProjectCollaborator collab2;
	ProjectCollaborator collab3;
	TaskCollaborator taskWorker1;
	TaskCollaborator taskWorker2;
	TaskCollaborator taskWorker3;
	Project project1;
	Project project2;
	Project project3;
	Task task1;
	Task task2;
	Task task3;
	List<Project> expResultProjectList;
	List<Task> expResultTaskList;
	Calendar estimatedStartDate;
	Calendar taskDeadline;

	@Before
	public void setUp() {

		projectRepository = new ProjectRepository();
		user1 = new User("name", "email", "idNumber", "function", "123456789");
		user2 = new User("name2", "email2", "idNumber2", "function2", "987654321");
		user3 = new User("name6", "email6", "idNumber6", "function6", "987654271");
		collab1 = new ProjectCollaborator(user1, 1);
		collab2 = new ProjectCollaborator(user2, 2);
		collab3 = new ProjectCollaborator(user3, 3);
		taskWorker1 = new TaskCollaborator(collab1);
		taskWorker2 = new TaskCollaborator(collab2);
		taskWorker3 = new TaskCollaborator(collab3);
		project1 = new Project(1, "name3", "description3", user1);
		project2 = new Project(2, "name4", "description5", user2);
		project3 = new Project(3, "name5", "description5", user3);
		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.set(2017, Calendar.JANUARY, 14);
		taskDeadline = Calendar.getInstance();
		taskDeadline.set(2017, Calendar.NOVEMBER, 17);
		task1 = new Task(111, 222, "Task 1", 50, estimatedStartDate, taskDeadline, 2000);
		task2 = new Task(112, 223, "Task 1", 50, estimatedStartDate, taskDeadline, 2000);
		task3 = new Task(113, 224, "Task 1", 50, estimatedStartDate, taskDeadline, 2000);
		expResultProjectList = new ArrayList<Project>();
		expResultTaskList = new ArrayList<Task>();
	}

	@After
	public void tearDown() {

		projectRepository = null;
		user1 = null;
		user2 = null;
		user2 = null;
		project1 = null;
		project2 = null;
		project3 = null;
		task1 = null;
		task2 = null;
		task3 = null;
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

		// Sets the project status to review (4, active)
		project1.setProjectStatus(4);
		project2.setProjectStatus(4);

		// Adds the projects to the project repository
		projectRepository.addProjectToProjectRepository(project1);
		projectRepository.addProjectToProjectRepository(project2);
		projectRepository.addProjectToProjectRepository(project3);

		// Adds the projects to the expResult list to be compared to.
		expResultProjectList.add(project1);
		expResultProjectList.add(project2);

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
		calendar1.set(2017, Calendar.NOVEMBER, 14);
		task1.setFinishDate(calendar1);
		task1.markTaskAsFinished();

		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(2017, Calendar.NOVEMBER, 13);
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
		calendar1.set(2017, Calendar.NOVEMBER, 14);
		task1.setFinishDate(calendar1);
		task1.markTaskAsFinished();

		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(2017, Calendar.NOVEMBER, 13);
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
		calendar1.set(2017, Calendar.NOVEMBER, 14);
		task1.setFinishDate(calendar1);
		task1.markTaskAsFinished();

		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(2017, Calendar.NOVEMBER, 13);
		task2.setFinishDate(calendar2);
		task2.markTaskAsFinished();

		double expResult = (task1.getTimeSpentByProjectCollaboratorOntask(collab1)
				+ task2.getTimeSpentByProjectCollaboratorOntask(collab1)) / 2;

		assertEquals(expResult, projectRepository.getAverageTimeOfFinishedTasksFromUserLastMonth(user1), 0.000000001);
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

}
