package test.java.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Profile;
import main.java.project.model.Project;
import main.java.project.model.Task;
import main.java.project.model.User;

class US204Tests {

	/**
	 * Tests US204
	 * 
	 * US204: Como colaborador, eu pretendo adicionar uma tarefa à minha lista de
	 * tarefas.
	 * 
	 * uses methods addProjectTask, addUserToTask, isProjectManager,
	 * getProjectTeam().contains()
	 * 
	 * 
	 */
	Company company;
	User newUserA;
	User newUserB;
	Project project;
	Task taskA;
	Task taskB;

	@BeforeEach
	void setUp() {
		company = Company.getTheInstance();
		company.getUsersList().clear();
		company.getProjectsList().clear();

		newUserA = company.createUser("João", "user2@gmail.com", "123", "Maneger", "940000000", "StreetA", "ZipCodeA",
				"CityA", "DistrictA", "CountryA");
		newUserB = company.createUser("Juni", "user3@gmail.com", "132", "Code Monkey", "930000000", "StreetB",
				"ZipCodeB", "CityB", "DistrictB", "CountryB");
		project = company.createProject("name3", "description4", newUserA);
		taskA = project.getTaskRepository().createTask("Test dis pls");
		taskB = project.getTaskRepository().createTask("Test dis agen pls");
	}

	@AfterEach
	void tearDown() {
		company = null;
		newUserA = null;
		newUserB = null;
		project = null;
		taskA = null;
		taskB = null;

	}

	/**
	 * This Test verifies if a given user is the project manager, then attempts to
	 * add two tasks. In the actual program, only the project manager would see the
	 * option to add a new task.
	 * 
	 */
	@Test
	void testCheckProjectManagerAndAddTaskToProject() {
		assertTrue(company.addUserToUserList(newUserA));
		assertTrue(company.addUserToUserList(newUserB));
		newUserA.setUserProfile(Profile.COLLABORATOR);
		newUserB.setUserProfile(Profile.COLLABORATOR);

		company.addProjectToProjectList(project);

		assertTrue(project.isProjectManager(newUserA));
		assertFalse(project.isProjectManager(newUserB));

		project.getTaskRepository().addProjectTask(taskA);
		project.getTaskRepository().addProjectTask(taskB);

	}

	/**
	 * 
	 * Create a project and two tasks, then attempt to add a user to those tasks.
	 * Any user in the Project could add themselves to the task and view it in the
	 * ToDo list
	 */
	@Test
	void testAddUserToTaskAndViewToDoList() {

		// assertTrue(company.addUserToUserList(newUserA));
		// assertTrue(company.addUserToUserList(newUserB));
		newUserA.setUserProfile(Profile.COLLABORATOR);
		newUserB.setUserProfile(Profile.COLLABORATOR);

		company.addProjectToProjectList(project);

		project.addUserToProjectTeam(newUserB);

		project.getTaskRepository().addProjectTask(taskA);
		project.getTaskRepository().addProjectTask(taskB);

		// verifies if project team contains User 3
		assertTrue(project.getProjectTeam().contains(newUserB));

		// assigns both tasks to User 3 then checks their unfinished task list
		taskA.addUserToTask(newUserB);
		taskB.addUserToTask(newUserB);

		List<Task> testList = new ArrayList<Task>();
		testList.add(taskA);
		testList.add(taskB);
		assertEquals(company.getUnfinishedUserTaskList(newUserB), testList);

		// tests taskTeamContainsUser method
		assertTrue(taskA.taskTeamContainsUser(newUserB));
		assertFalse(taskA.taskTeamContainsUser(newUserA));

	}

}
