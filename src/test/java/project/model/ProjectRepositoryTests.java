package test.java.project.model;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sun.javafx.tk.Toolkit.Task;

import main.java.project.model.Project;
import main.java.project.model.ProjectRepository;
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

	@BeforeEach
	public void setUp() {

		projectRepository = new ProjectRepository();
		user1 = new User("name", "email", "idNumber", "function", "123456789");
		user2 = new User("name2", "email2", "idNumber2", "function2", "987654321");
		user2 = new User("name6", "email6", "idNumber6", "function6", "987654271");
		project1 = new Project("name3", "description3", user1);
		project2 = new Project("name4", "description5", user2);
		project3 = new Project("name5", "description5", user3);
		task1 = new Task();

	}

	@Test
	void testCreateProject() {
		fail("Not yet implemented");
	}

	@Test
	void testAddProjectToProjectRepository() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllProjects() {
		fail("Not yet implemented");
	}

	@Test
	void testGetActiveProjects() {
		fail("Not yet implemented");
	}

	@Test
	void testGetUserTasks() {
		fail("Not yet implemented");
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
