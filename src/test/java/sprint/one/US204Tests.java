package sprint.one;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.User;

public class US204Tests {

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
	ProjectCollaborator projCollab2;
	TaskCollaborator taskWorker1;

	@Before
	public void setUp() {

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

		projCollab1 = project1.createProjectCollaborator(newUserA, 250);
		projCollab2 = project1.createProjectCollaborator(newUserB, 250);

		taskWorker1 = taskA.createTaskCollaborator(projCollab1);

	}

	@After
	public void tearDown() {
		Company.clear();
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
	public void testCheckProjectManagerAndAddTaskToProject() {
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
	public void testAddUserToTaskAndViewToDoList() {

		// assertTrue(company.addUserToUserList(newUserA));
		// assertTrue(company.addUserToUserList(newUserB));
		newUserA.setUserProfile(Profile.COLLABORATOR);
		newUserB.setUserProfile(Profile.COLLABORATOR);

		myCompany.getProjectsRepository().addProjectToProjectRepository(project1);

		project1.addUserToProjectTeam(newUserB, 10);
		project1.addUserToProjectTeam(newUserA, 10);

		project1.getTaskRepository().addProjectTask(taskA);
		project1.getTaskRepository().addProjectTask(taskB);

		// verifies if project team contains newUserB
		assertTrue(project1.isUserInProjectTeam(newUserB));

		// assigns both tasks to User 3 then checks their unfinished task list
		taskA.addTaskCollaboratorToTask(taskWorker1);
		taskB.addTaskCollaboratorToTask(taskWorker1);

		List<Task> testList = new ArrayList<Task>();
		testList.add(taskA);
		testList.add(taskB);
		assertEquals(myCompany.getProjectsRepository().getUnfinishedUserTaskList(newUserA), testList);

		// tests taskTeamContainsUser method
		assertTrue(taskA.isProjectCollaboratorInTaskTeam(projCollab1));
		assertFalse(taskA.isProjectCollaboratorInTaskTeam(projCollab2));

	}

}
