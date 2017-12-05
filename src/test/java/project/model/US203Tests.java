package test.java.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Project;
import main.java.project.model.User;

class US203Tests {

	/**
	 * Tests US203
	 * 
	 * US203: Como colaborador, eu pretendo consultar a minha lista de tarefas
	 * pendentes de modo a saber o que tenho para fazer hoje.
	 * 
	 * uses methods addProjectTask, addUserToTask, markTaskAsFinished,
	 * setFinishDate, getUnfinishedTaskList()
	 * 
	 * 
	 */

	/**
	 * This creates two users, two tasks and a project, and tests if the User can
	 * see all unfinished tasks tagged with his name, then compares it against a
	 * test Task List; Finally, attempts to mark one task as completed and verify it
	 * no longer appears in Unfinished Task List
	 * 
	 */

	Company myCompany;
	User user2;
	User user3;
	Project project1;
	Task task1;
	Task task2;

	@BeforeEach
	void setUp() {

		myCompany = Company.getTheInstance();
		myCompany.getUsersList().clear();
		myCompany.getProjectsList().clear();

		user2 = myCompany.createUser("João", "user2@gmail.com", "Manager", "930000000", "rua doutor antónio",
				"7689-654", "porto", "porto", "portugal");
		user3 = myCompany.createUser("Juni", "user3@gmail.com", "Code Monkey", "930000000", "rua engenheiro joão",
				"789-654", "porto", "porto", "portugal");

		project1 = myCompany.createProject("name3", "description4", user2);

		task1 = project1.createTask("Task 1");
		task2 = project1.createTask("Task 2");

		user2.getProfile().setCollaborator();
		user3.getProfile().setCollaborator();

		myCompany.addProjectToProjectList(project1);

		project1.addUserToProjectTeam(user3);
		project1.addProjectTask(task1);
		project1.addProjectTask(task2);

		task1.addUserToTask(user3);
		task2.addUserToTask(user3);

		// sets testTask as Finished, removes it from the test List, then compares again
		task1.setFinishDate();
		task1.markTaskAsFinished();

	}

	@Test
	void testGetUserTaskList() {

		assertTrue(myCompany.addUserToUserList(user2));
		assertTrue(myCompany.addUserToUserList(user3));

		// Creates testList and compares it to the Unfinished task List
		List<Task> testList = new ArrayList<Task>();
		testList.add(task1);
		testList.add(task2);

		testList.remove(task1);

		assertEquals(myCompany.getUnfinishedUserTaskList(user3), testList);

		assertEquals(myCompany.getUnfinishedUserTaskList(user3), testList);

	}

}
