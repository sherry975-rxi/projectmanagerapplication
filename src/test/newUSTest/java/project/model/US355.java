package test.newUSTest.java.project.model;

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
import main.java.project.model.ProjectCollaborator;
import main.java.project.model.Task;
import main.java.project.model.User;

class US355 {

	/**
	 * Tests US355
	 * 
	 * US355: - Como Gestor de projeto,
	 * 
	 * uses method isProjectManager, getProjectTeam, getCollaboratorsWithoutTasks
	 * 
	 */

	Company c1;
	User u1;
	User u2;
	User u3;
	User u4;
	User u5;
	Project p1;
	Task testTask, testTask2;

	@BeforeEach
	void setUp() {
		// create company and clear ProjectRepository and UsersRepository
		c1 = Company.getTheInstance();
		c1.getProjectsRepository().getAllProjects().clear();
		c1.getUsersRepository().getAllUsersFromRepository().clear();

		// create users
		u1 = c1.getUsersRepository().createUser("Daniel", "user2@gmail.com", "123", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");
		u2 = c1.getUsersRepository().createUser("Joaquim", "joaquim@gmail.com", "126", "Empregado", "940000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");
		u3 = c1.getUsersRepository().createUser("Maria", "maria@gmail.com", "127", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		u4 = c1.getUsersRepository().createUser("Leonor", "leonor@gmail.com", "128", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		u5 = c1.getUsersRepository().createUser("Raquel", "raquel@gmail.com", "129", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		// add users to company
		c1.getUsersRepository().addUserToUserRepository(u1);
		c1.getUsersRepository().addUserToUserRepository(u2);
		c1.getUsersRepository().addUserToUserRepository(u3);
		c1.getUsersRepository().addUserToUserRepository(u4);
		c1.getUsersRepository().addUserToUserRepository(u5);

		// set user as Director
		u1.setUserProfile(Profile.DIRECTOR);

		// set user to collaborator
		u2.setUserProfile(Profile.COLLABORATOR);
		u3.setUserProfile(Profile.COLLABORATOR);
		u4.setUserProfile(Profile.COLLABORATOR);
		u5.setUserProfile(Profile.COLLABORATOR);

		// create the project and set a user to Project manager
		p1 = c1.getProjectsRepository().createProject("Teste", "blablabla", u2);
		p1.getProjectTeam().clear();
		p1.getTaskRepository().getProjectTaskList().clear();

		// add project to the Company Project list
		c1.getProjectsRepository().addProjectToProjectRepository(p1);

		// add collaborators to project
		p1.addUserToProjectTeam(u3, 120);
		p1.addUserToProjectTeam(u4, 130);
		p1.addUserToProjectTeam(u5, 150);

		// create a estimated Task Start Date
		Calendar estimatedTaskStartDateTest = Calendar.getInstance();

		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.DECEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 29);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a estimated Task Deadline
		Calendar taskDeadlineDateTest = Calendar.getInstance();

		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.JANUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create two Tasks and add to Repository
		testTask = p1.getTaskRepository().createTask("Test dis agen pls", 10, estimatedTaskStartDateTest,
				taskDeadlineDateTest, 10);
		testTask2 = p1.getTaskRepository().createTask("Test dis agen pls", 10, estimatedTaskStartDateTest,
				taskDeadlineDateTest, 10);
		testTask.getTaskTeam().clear();
		testTask2.getTaskTeam().clear();

		p1.getTaskRepository().addProjectTask(testTask);
		p1.getTaskRepository().addProjectTask(testTask2);

	}

	@AfterEach
	void tearDown() {
		c1 = null;
		u1 = null;
		u2 = null;
		u3 = null;
		u4 = null;
		u5 = null;
		p1 = null;
		testTask = null;
		testTask2 = null;
	}

	/**
	 * Adds three users to the Project but not to any task, then compares the test
	 * List with the output of getCollaboratorsWithoutTasks
	 * 
	 */
	@Test
	public void US355NoUsersOnTasks() {

		// tests that project manager is u2 and not other user (for example u4)
		assertTrue(p1.isProjectManager(u2));
		assertFalse(p1.isProjectManager(u4));

		// create project collabotors with u3, u4 and u5 users
		ProjectCollaborator collaborator1 = new ProjectCollaborator(u3, 120);
		ProjectCollaborator collaborator2 = new ProjectCollaborator(u4, 130);
		ProjectCollaborator collaborator3 = new ProjectCollaborator(u5, 150);

		// Creates a new list and adds user to that list, to compare with userList
		// inside ProjectTeam
		List<ProjectCollaborator> testNoUsersOnTasks = new ArrayList<ProjectCollaborator>();

		testNoUsersOnTasks.add(collaborator1);
		testNoUsersOnTasks.add(collaborator2);
		testNoUsersOnTasks.add(collaborator3);

		assertEquals(testNoUsersOnTasks, p1.getCollaboratorsWithoutTasks());
	}

	/**
	 * Adds two users to the Project and two of them to tasks, then compares the
	 * test List with the output of getCollaboratorsWithoutTasks (Must contain two
	 * user)
	 */
	@Test
	public void US355_TwoUsersOnTasks() {

		// create project collabotors with u3, u4 and u5 users
		ProjectCollaborator collaborator1 = new ProjectCollaborator(u3, 120);
		ProjectCollaborator collaborator2 = new ProjectCollaborator(u4, 130);
		ProjectCollaborator collaborator3 = new ProjectCollaborator(u5, 150);

		// adds collaborator 1 and 2 to Tasks
		testTask.addUserToTask(collaborator1);
		testTask2.addUserToTask(collaborator2);

		// Creates a new list and adds user to that list, to compare with userList
		// inside ProjectTeam
		List<ProjectCollaborator> testNoUsersOnTasks = new ArrayList<ProjectCollaborator>();

		testNoUsersOnTasks.add(collaborator3);

		assertEquals(testNoUsersOnTasks, p1.getCollaboratorsWithoutTasks());
	}

	/**
	 * Adds two users to the Project and two of them to tasks, then removes one from
	 * the task. The test List with the output of getCollaboratorsWithoutTasks Must
	 * contain one user
	 */
	@Test
	public void US355_UserRemovedFromTask() {

		// create project collabotors with u3, u4 and u5 users
		ProjectCollaborator collaborator1 = new ProjectCollaborator(u3, 120);
		ProjectCollaborator collaborator2 = new ProjectCollaborator(u4, 130);
		ProjectCollaborator collaborator3 = new ProjectCollaborator(u5, 150);

		// adds collaborator 1 and 2 to Tasks
		testTask.addUserToTask(collaborator1);
		testTask2.addUserToTask(collaborator2);
		testTask2.removeUserFromTask(collaborator2);

		// Creates a new list and adds user to that list, to compare with userList
		// inside ProjectTeam
		List<ProjectCollaborator> testNoUsersOnTasks = new ArrayList<ProjectCollaborator>();

		testNoUsersOnTasks.add(collaborator2);
		testNoUsersOnTasks.add(collaborator3);

		assertEquals(testNoUsersOnTasks, p1.getCollaboratorsWithoutTasks());
	}

}