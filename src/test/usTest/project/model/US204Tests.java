package test.usTest.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.project.model.Company;
import main.project.model.Profile;
import main.project.model.Project;
import main.project.model.ProjectCollaborator;
import main.project.model.Task;
import main.project.model.TaskWorker;
import main.project.model.User;

class US204Tests {

	/**
	 * Tests US204
	 * 
	 * US204: Como colaborador, eu pretendo adicionar uma tarefa à minha lista de
	 * tarefas.
	 * 
	 * uses methods addProjectTask, addUserToTask, isProjectManager,
	 * getProjectTeam().contains()
	 */

	Company myCompany;
	User newUserA;
	User newUserB;
	Project project1;
	Task taskA;
	Task taskB;
	ProjectCollaborator projCollab1;
	TaskWorker taskWorker1;

	@BeforeEach
	void setUp() {

		myCompany = Company.getTheInstance();

		// Creation of two users: newUserA and newUserB
		newUserA = myCompany.getUsersRepository().createUser("João", "user2@gmail.com", "123", "Maneger", "940000000",
				"StreetA", "ZipCodeA", "CityA", "DistrictA", "CountryA");

		newUserB = myCompany.getUsersRepository().createUser("Juni", "user3@gmail.com", "132", "Code Monkey",
				"930000000", "StreetB", "ZipCodeB", "CityB", "DistrictB", "CountryB");

		// Creation of one project and newUser4 set as the project manager
		project1 = myCompany.getProjectsRepository().createProject("name3", "description4", newUserA);

		// Creation of two tasks: taskA and taskB
		Calendar startDateA = Calendar.getInstance();
		startDateA.clear();
		startDateA.set(2017, 05, 15);
		Calendar finishDateA = Calendar.getInstance();
		finishDateA.clear();
		finishDateA.set(2017, 05, 16);
		taskA = project1.getTaskRepository().createTask("Test dis pls", 100, startDateA, finishDateA, 15000);
		taskB = project1.getTaskRepository().createTask("Test dis agen pls", 100, startDateA, finishDateA, 15000);

		projCollab1 = project1.createProjectCollaborator(newUserB, 250);

		taskWorker1 = taskA.createTaskWorker(projCollab1);

	}

	@AfterEach
	void tearDown() {
		myCompany.clear();
		newUserA = null;
		newUserB = null;
		project1 = null;
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
		myCompany.getUsersRepository().addUserToUserRepository(newUserA);
		myCompany.getUsersRepository().addUserToUserRepository(newUserB);
		newUserA.setUserProfile(Profile.COLLABORATOR);
		newUserB.setUserProfile(Profile.COLLABORATOR);

		myCompany.getProjectsRepository().addProjectToProjectRepository(project1);

		assertTrue(project1.isProjectManager(newUserA));
		assertFalse(project1.isProjectManager(newUserB));

		project1.getTaskRepository().addProjectTask(taskA);
		project1.getTaskRepository().addProjectTask(taskB);

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

		myCompany.getProjectsRepository().addProjectToProjectRepository(project1);

		project1.addUserToProjectTeam(projCollab1);

		project1.getTaskRepository().addProjectTask(taskA);
		project1.getTaskRepository().addProjectTask(taskB);

		// verifies if project team contains User 3
		assertTrue(project1.containsUser(newUserB));

		// assigns both tasks to User 3 then checks their unfinished task list
		taskA.addUserToTask(taskWorker1);
		taskB.addUserToTask(taskWorker1);

		List<Task> testList = new ArrayList<Task>();
		testList.add(taskA);
		testList.add(taskB);
		assertEquals(myCompany.getProjectsRepository().getUnfinishedUserTaskList(newUserB), testList);

		// tests taskTeamContainsUser method
		assertTrue(taskA.taskTeamContainsUser(newUserB));
		assertFalse(taskA.taskTeamContainsUser(newUserA));

	}

}
