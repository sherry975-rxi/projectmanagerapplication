package test.java.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.EffortUnit;
import main.java.project.model.Project;
import main.java.project.model.ProjectRepository;
import main.java.project.model.Task;
import main.java.project.model.User;

class ProjectRepositoryTests {

	ProjectRepository projectRepository;
	User user1;
	User user2;
	User user3;
	Project project1;
	Project project2;
	Project project3;
	Task task1;
	Task task2;
	Task task3;
	List<Project> expResultProjectList;
	List<Task> expResultTaskList;

	@BeforeEach
	public void setUp() {

		projectRepository = new ProjectRepository();
		user1 = new User("name", "email", "idNumber", "function", "123456789");
		user2 = new User("name2", "email2", "idNumber2", "function2", "987654321");
		user2 = new User("name6", "email6", "idNumber6", "function6", "987654271");
		project1 = new Project(0, "name3", "description3", user1, EffortUnit.HOURS, 2000);
		project2 = new Project(2, "name4", "description5", user2, EffortUnit.HOURS, 2000);
		project3 = new Project(3, "name5", "description5", user3, EffortUnit.HOURS, 2000);
		task1 = new Task(111, 222, "Task 1");
		task2 = new Task(112, 223, "Task 1");
		task3 = new Task(113, 224, "Task 1");
		expResultProjectList = new ArrayList<Project>();
		expResultTaskList = new ArrayList<Task>();
	}

	@AfterEach
	void tearDown() {

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
	 * Tests the CreateProject method by calling the method equals (project) to
	 * assert if the project created is equal to other project. If the equals
	 * returns TRUE means the two projects are equal, so the creatProject method
	 * worked.
	 */
	@Test
	void testCreateProject() {

		assertTrue(project1
				.equals(projectRepository.createProject("name3", "description3", user1, EffortUnit.HOURS, 2000)));

	}

	/**
	 * Tests the addProjectToProjectRepository by asserting if the list within the
	 * projectRepository is equal to a new list of projects created, after adding
	 * the same project to both lists.
	 */
	@Test
	void testAddProjectToProjectRepository() {

		projectRepository.addProjectToProjectRepository(project1);

		expResultProjectList.add(project1);

		assertEquals(expResultProjectList, projectRepository.getAllProjects());
	}

	/**
	 * Tests the getAllProjects by asserting if the list within the
	 * projectRepository is equal to a new list of projects created.
	 */
	@Test
	void testGetAllProjects() {

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
	void testGetActiveProjects() {

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
	void testGetUserTasks() {

		// Adds project to project repository
		projectRepository.addProjectToProjectRepository(project1);

		// Adds user to project team
		project1.addUserToProjectTeam(user1, 15);
		project1.addUserToProjectTeam(user2, 15);

		// Adds tasks to project repository.
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);
		project1.getTaskRepository().addProjectTask(task3);

		// Adds user to tasks.
		task1.addUserToTask(user1);
		task2.addUserToTask(user2);
		task3.addUserToTask(user1);

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
	void testGetFinishedUserTaskList() {

		// Adds project to project repository
		projectRepository.addProjectToProjectRepository(project1);

		// Adds user to project team
		project1.addUserToProjectTeam(user1, 15);
		project1.addUserToProjectTeam(user2, 15);

		// Adds tasks to project repository.
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);
		project1.getTaskRepository().addProjectTask(task3);

		// Adds user to tasks.
		task1.addUserToTask(user1);
		task2.addUserToTask(user1);
		task3.addUserToTask(user1);

		// Marks tasks as finished
		task1.markTaskAsFinished();
		task1.setFinishDate();
		task3.markTaskAsFinished();
		task3.setFinishDate();

		// Adds user to expResultTaskList
		expResultTaskList.add(task1);
		expResultTaskList.add(task3);

		assertEquals(expResultTaskList, projectRepository.getFinishedUserTaskList(user1));

	}

	/**
	 * Tests the getUnifinishedUserTaskList method. Has to return a list with the
	 * unfinished tasks from an user.
	 */
	@Test
	void testGetUnfinishedUserTaskList() {

		// Adds project to project repository
		projectRepository.addProjectToProjectRepository(project1);

		// Adds user to project team
		project1.addUserToProjectTeam(user1, 15);
		project1.addUserToProjectTeam(user2, 15);

		// Adds tasks to project repository.
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);
		project1.getTaskRepository().addProjectTask(task3);

		// Adds user to tasks.
		task1.addUserToTask(user1);
		task2.addUserToTask(user1);
		task3.addUserToTask(user1);

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
	void testGetLastMonthFinishedUserTaskList() {

		// Adds project to project repository
		projectRepository.addProjectToProjectRepository(project1);

		// Adds user to project team
		project1.addUserToProjectTeam(user1, 15);
		project1.addUserToProjectTeam(user2, 15);

		// Adds tasks to project repository.
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);
		project1.getTaskRepository().addProjectTask(task3);

		// Adds user to tasks.
		task1.addUserToTask(user1);
		task2.addUserToTask(user1);
		task3.addUserToTask(user1);

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
	void testGetTotalTimeLastMonthFinishedTasksByUser() {
		// Adds project to project repository
		projectRepository.addProjectToProjectRepository(project1);

		// Adds user to project team
		project1.addUserToProjectTeam(user1, 15);

		// Adds tasks to project repository.
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);

		// Adds user to tasks.
		task1.addUserToTask(user1);
		task2.addUserToTask(user1);

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

		Double expResult = (task1.getTimeSpentOnTask() + task2.getTimeSpentOnTask());

		assertEquals(expResult, projectRepository.getTotalTimeLastMonthFinishedTasksByUser(user1), 0.000000001);
	}

	/**
	 * Tests the GetAverageTimeLastMonthFinishedTasksUser. The result has to be
	 * equal to the sum of time spent on every task by the user divided by the
	 * number of tasks.
	 */
	@Test
	void testGetAverageTimeLastMonthFinishedTasksUser() {

		// Adds project to project repository
		projectRepository.addProjectToProjectRepository(project1);

		// Adds user to project team
		project1.addUserToProjectTeam(user1, 15);

		// Adds tasks to project repository.
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);

		// Adds user to tasks.
		task1.addUserToTask(user1);
		task2.addUserToTask(user1);

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

		Double expResult = (task1.getTimeSpentOnTask() + task2.getTimeSpentOnTask()) / 2;

		assertEquals(expResult, projectRepository.getAverageTimeLastMonthFinishedTasksUser(user1), 0.000000001);
	}

	/**
	 * Tests the GetLastMonthFinishedUserTaskListDecreasingOrder. Compares the
	 * output of the method to a list with the projects added be decreasing order.
	 */
	@Test
	void testGetLastMonthFinishedUserTaskListDecreasingOrder() {

		// Adds project to project repository
		projectRepository.addProjectToProjectRepository(project1);

		// Adds user to project team
		project1.addUserToProjectTeam(user1, 15);
		project1.addUserToProjectTeam(user2, 15);

		// Adds tasks to project repository.
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);
		project1.getTaskRepository().addProjectTask(task3);

		// Adds user to tasks.
		task1.addUserToTask(user1);
		task2.addUserToTask(user1);
		task3.addUserToTask(user1);

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

		assertEquals(expResultTaskList, projectRepository.getLastMonthFinishedUserTaskListDecreasingOrder(user1));
	}

	/**
	 * Tests the GetFinishedTaskListByDecreasingOrder. Compares the output of the
	 * method to a list with the projects added be decreasing order.
	 */
	@Test
	void testGetFinishedTaskListByDecreasingOrder() {

		// Adds project to project repository
		projectRepository.addProjectToProjectRepository(project1);

		// Adds user to project team
		project1.addUserToProjectTeam(user1, 15);
		project1.addUserToProjectTeam(user2, 15);

		// Adds tasks to project repository.
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);
		project1.getTaskRepository().addProjectTask(task3);

		// Adds user to tasks.
		task1.addUserToTask(user1);
		task2.addUserToTask(user1);
		task3.addUserToTask(user1);

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

		assertEquals(expResultTaskList, projectRepository.getFinishedTaskListByDecreasingOrder(user1));
	}

	/**
	 * Tests the SortTaskListDecreasingOrder. Compares the output of the method to a
	 * list with the projects added be decreasing order.
	 */
	@Test
	void testSortTaskListDecreasingOrder() {

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
