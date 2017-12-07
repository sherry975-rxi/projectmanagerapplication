package usTest.java.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
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
		company.getUsersRepository().getAllUsersFromRepository().clear();
		company.getProjectsRepository().getAllProjects().clear();

		newUserA = company.getUsersRepository().createUser("João", "user2@gmail.com", "123", "Maneger", "940000000",
				"StreetA", "ZipCodeA", "CityA", "DistrictA", "CountryA");
		newUserB = company.getUsersRepository().createUser("Juni", "user3@gmail.com", "132", "Code Monkey", "930000000",
				"StreetB", "ZipCodeB", "CityB", "DistrictB", "CountryB");
		project = company.getProjectsRepository().createProject("name3", "description4", newUserA);
		Calendar startDateA = Calendar.getInstance();
		startDateA.clear();
		startDateA.set(2017, 05, 15);
		Calendar finishDateA = Calendar.getInstance();
		finishDateA.clear();
		finishDateA.set(2017, 05, 16);
		taskA = project.getTaskRepository().createTask("Test dis pls", 100, startDateA, finishDateA, 15000);
		taskB = project.getTaskRepository().createTask("Test dis agen pls", 100, startDateA, finishDateA, 15000);
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
		company.getUsersRepository().addUserToUserRepository(newUserA);
		company.getUsersRepository().addUserToUserRepository(newUserB);
		newUserA.setUserProfile(Profile.COLLABORATOR);
		newUserB.setUserProfile(Profile.COLLABORATOR);

		company.getProjectsRepository().addProjectToProjectRepository(project);

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

		company.getProjectsRepository().addProjectToProjectRepository(project);

		project.addUserToProjectTeam(newUserB, 5);

		project.getTaskRepository().addProjectTask(taskA);
		project.getTaskRepository().addProjectTask(taskB);

		// verifies if project team contains User 3
		assertTrue(project.containsUser(newUserB));

		// assigns both tasks to User 3 then checks their unfinished task list
		taskA.addUserToTask(newUserB);
		taskB.addUserToTask(newUserB);

		List<Task> testList = new ArrayList<Task>();
		testList.add(taskA);
		testList.add(taskB);
		assertEquals(company.getProjectsRepository().getUnfinishedUserTaskList(newUserB), testList);

		// tests taskTeamContainsUser method
		assertTrue(taskA.taskTeamContainsUser(newUserB));
		assertFalse(taskA.taskTeamContainsUser(newUserA));

	}

}
