package test.java.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
		project1 = new Project(2456321, "name3", "description3", user1);
		project2 = new Project(3424234, "name4", "description5", user2);
		project3 = new Project(2342565, "name5", "description5", user3);
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

		assertTrue(project1.equals(projectRepository.createProject("name3", "description3", user1)));

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

	@Test
	void testGetUserTasks() {

		project1.addUserToProjectTeam(user1);
		project1.addUserToProjectTeam(user2);

		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);
		project1.getTaskRepository().addProjectTask(task3);

		task1.addUserToTask(user1);
		task2.addUserToTask(user2);
		task3.addUserToTask(user1);

		expResultTaskList.add(task1);
		expResultTaskList.add(task3);

		assertEquals(expResultTaskList, projectRepository.getUserTasks(user1));
	}

	@Test
	void testGetFinishedUserTaskList() {
		fail("Not yet implemented");
	}

	@Test
	void testGetUnfinishedUserTaskList() {
		fail("Not yet implemented");
	}

	@Test
	void testGetLastMonthFinishedUserTaskList() {
		fail("Not yet implemented");
	}

	@Test
	void testGetTotalTimeLastMonthFinishedTasksByUser() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAverageTimeLastMonthFinishedTasksUser() {
		fail("Not yet implemented");
	}

	@Test
	void testGetLastMonthFinishedUserTaskListDecreasingOrder() {
		fail("Not yet implemented");
	}

	@Test
	void testGetFinishedTaskListByDecreasingOrder() {
		fail("Not yet implemented");
	}

	@Test
	void testSortTaskListDecreasingOrder() {
		fail("Not yet implemented");
	}

}
