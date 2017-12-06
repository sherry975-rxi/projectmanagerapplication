package test.java.project.model;

//
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Project;
import main.java.project.model.Task;
import main.java.project.model.User;

class ProjectTests {

	Company c1;
	User u1;
	User u2;
	Task t1;
	Task t2;
	Task t3;
	Project p1;
	Project p2;

	@BeforeEach
	public void setUp() {

		c1 = Company.getTheInstance();
		u1 = new User("name", "email", "idNumber", "function", "123456789");
		u2 = new User("name2", "email2", "idNumber2", "function2", "987654321");
		p1 = c1.getProjectsRepository().createProject("name3", "description4", u1);
		t1 = p1.getTaskRepository().createTask("description");
		p1.getTaskRepository().addProjectTask(t1);
		t2 = p1.getTaskRepository().createTask("description2");
		p1.getTaskRepository().addProjectTask(t2);
		t2.markTaskAsFinished();
		p2 = c1.getProjectsRepository().createProject("name4", "description5", u2);
		t3 = p1.getTaskRepository().createTask("description3");
		t3.markTaskAsFinished();

	}

	@Test
	void testAddTaskToProjectTaskList() {
		assertEquals(t1, p1.getTaskRepository().getProjectTaskList().get(0));
	}

	@Test
	void testCheckProjectManagerTrue() {
		assertTrue(p1.isProjectManager(u1));
	}

	@Test
	void testCheckProjectManagerFalse() {
		assertFalse(p1.isProjectManager(u2));
	}

	@Test
	void testAddUserToProjectTeam() {
		p1.addUserToProjectTeam(u2);
		assertEquals(u2, p1.getProjectTeam().get(0));
	}

	@Test
	void testTryToAddTheSameUserTwiceToProjectTeam() {
		p1.addUserToProjectTeam(u1);
		p1.addUserToProjectTeam(u1);
		assertEquals(1, p1.getProjectTeam().size());
	}

	/*
	 * @Test void testEqualsTrue() { Company c2 = Company.getTheInstance(); Project
	 * p3 = new Project("name5", "description5", u2);
	 * c2.addProjectToProjectList(p3); assertFalse(p3.equals(p1)); }
	 */
	@Test
	void testEqualsFalse() {
		assertFalse(p1.equals(p2));
	}

	@Test
	void testEqualsDifferentObject() {
		assertFalse(p1.equals(u1));
	}

	@Test
	void testProjectState() {
		p1.setProjectStatus(Project.EXECUTION);
		assertEquals(Project.EXECUTION, p1.getProjectStatus());
	}

	@Test
	void testGetUnfinishedTasks() {
		t1.addUserToTask(u1);
		assertEquals(t1, p1.getTaskRepository().getUnFinishedTasks(u1).get(0));
	}

	@Test
	void testGetFinishedTasks() {
		t2.addUserToTask(u2);
		assertEquals(t2, p1.getTaskRepository().getFinishedTaskListofUserInProject(u2).get(0));
	}

	@Test
	void testGetAllTasks() {// The order of tasks in the test list changes because the getAllTasks method
							// shows the finished tasks first.
		t1.addUserToTask(u1);
		t2.addUserToTask(u1);
		List<Task> test = new ArrayList<Task>();
		test.add(t1);
		test.add(t2);
		assertEquals(test, p1.getTaskRepository().getAllTasks(u1));
	}

	@Test
	void testGetFinishedTaskListLastMonth() {
		Calendar test = Calendar.getInstance();
		test.add(Calendar.MONTH, -1);
		t1.addUserToTask(u1);
		t2.addUserToTask(u1);
		t3.addUserToTask(u1);
		p1.getTaskRepository().addProjectTask(t1);
		p1.getTaskRepository().addProjectTask(t2);
		p1.getTaskRepository().addProjectTask(t3);
		t2.setFinishDate();
		t3.setFinishDate();
		t2.getFinishDate().set(Calendar.MONTH, test.get(Calendar.MONTH) - 1);
		t3.getFinishDate().set(Calendar.MONTH, test.get(Calendar.MONTH));
		assertEquals(t3, p1.getTaskRepository().getFinishedTasksGivenMonth(u1, 1).get(0));
	}

	@Test
	void testAddUserToTask() {
		p1.getTaskRepository().addProjectTask(t1);
		p1.getTaskRepository().addProjectTask(t2);
		p1.getTaskRepository().addProjectTask(t3);
		t2.addUserToTask(u1);
		assertTrue(t2.taskTeamContainsUser(u1));
	}

	@Test
	void testFailToAddUserToTask() {
		p1.getTaskRepository().addProjectTask(t1);
		p1.getTaskRepository().addProjectTask(t2);
		p1.getTaskRepository().addProjectTask(t3);
		t2.addUserToTask(u1);
		assertFalse(t1.taskTeamContainsUser(u1));
	}

	@Test
	void testProjectContainsTask() {
		assertTrue(p1.getTaskRepository().containsTask(t2));
	}

	@Test
	void testProjectContainsTaskFalse() {
		assertFalse(p1.getTaskRepository().containsTask(t3));
	}

}