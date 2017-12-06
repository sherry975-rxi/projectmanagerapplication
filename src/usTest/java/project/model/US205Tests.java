package test.java.project.model;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Profile;
import main.java.project.model.Project;
import main.java.project.model.Task;
import main.java.project.model.User;

class US205Tests {

	/**
	 * Tests US205
	 * 
	 * US205: Como colaborador, eu pretendo marcar uma tarefa que consta na minha
	 * lista de tarefas como concluida.
	 * 
	 * uses methods addProjectTask, addUserToTask, markTaskAsCompleted(),
	 * setFinishDate(), isFinished()
	 * 
	 * 
	 */
	Company myCompany;
	User newUser2;
	User newUser3;
	Project testProj;
	Task testTask;
	Task testTask2;
	int typeOfUser;

	@BeforeEach
	void setUp() {
		myCompany = Company.getTheInstance();
		myCompany.getUsersList().clear();

		newUser2 = myCompany.createUser("Joao", "Joao@gmail.com", "123", "Empregado", "930000000", "Rua Maria",
				"4444-444", "221234567", "Porto", "Portugal");

		newUser3 = myCompany.createUser("Leonor", "leonor@gmail.com", "123", "Empregado", "930000000", "Rua Maria",
				"4444-444", "221234567", "Porto", "Portugal");
		testProj = myCompany.createProject("name3", "description4", newUser2);

		testTask = testProj.getTaskRepository().createTask("Test dis pls");

		testTask2 = testProj.getTaskRepository().createTask("Test dis agen pls");

		typeOfUser = 1;
	}

	@AfterEach
	void tearDown() {
		myCompany = null;
		newUser2 = null;
		newUser3 = null;
		testProj = null;
		testTask = null;
		testTask2 = null;
		typeOfUser = 0;
	}

	/**
	 * 
	 * This test creates two users, one project, and two tasks. Both users are
	 * marked as collaborators, one of them the project's manager; The, tests the
	 * various steps of adding users to projects and tasks, and tasks to the
	 * project; Finally, confirms if tasks can be marked as completed
	 * 
	 */
	@Test
	void testMarkTaskAsCompleted() {
		assertTrue(myCompany.addUserToUserList(newUser2));
		assertTrue(myCompany.addUserToUserList(newUser3));
		newUser2.setUserProfile(Profile.COLLABORATOR);
		newUser3.setUserProfile(Profile.COLLABORATOR);

		myCompany.addProjectToProjectList(testProj);

		// Adds User 3 to team and both tasks to project
		testProj.addUserToProjectTeam(newUser3);
		testProj.getTaskRepository().addProjectTask(testTask);
		testProj.getTaskRepository().addProjectTask(testTask2);

		// Adds user 3 to both tasks and marks the first task as cleared
		testTask.addUserToTask(newUser3);
		testTask2.addUserToTask(newUser3);
		testTask.setFinishDate();
		testTask.markTaskAsFinished();

		// Confirms if User 3 is in the team, and User 2 (Project manager) isn't
		assertTrue(testTask.getTaskTeam().contains(newUser3));
		assertFalse(testTask.getTaskTeam().contains(newUser2));

		// Asserts if testTask is cleared, and testTask2 isn't
		assertTrue(testTask.isFinished());
		assertFalse(testTask2.isFinished());
	}

}
