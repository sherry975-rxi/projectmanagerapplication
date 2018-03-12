package sprint.two;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class US355 {

	/**
	 * Tests US355
	 * 
	 * US355: - Como Gestor de Projeto, quero obter a lista de colaboradores do
	 * projeto sem tarefas atribuídas.
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
	ProjectCollaborator collaborator1, collaborator2, collaborator3;
	Task testTask, testTask2;

	@Before
	public void setUp() {
		// create company and clear ProjectsRepository and UsersRepository

		c1 = Company.getTheInstance();

		// create users
		u1 = c1.getUsersContainer().createUser("Daniel", "user2@gmail.com", "123", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");
		u2 = c1.getUsersContainer().createUser("Joaquim", "joaquim@gmail.com", "126", "Empregado", "940000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");
		u3 = c1.getUsersContainer().createUser("Maria", "maria@gmail.com", "127", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		u4 = c1.getUsersContainer().createUser("Leonor", "leonor@gmail.com", "128", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		u5 = c1.getUsersContainer().createUser("Raquel", "raquel@gmail.com", "129", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		// add users to company
		c1.getUsersContainer().addUserToUserRepository(u1);
		c1.getUsersContainer().addUserToUserRepository(u2);
		c1.getUsersContainer().addUserToUserRepository(u3);
		c1.getUsersContainer().addUserToUserRepository(u4);
		c1.getUsersContainer().addUserToUserRepository(u5);

		// set user as Director
		u1.setUserProfile(Profile.DIRECTOR);

		// set user to collaborator
		u2.setUserProfile(Profile.COLLABORATOR);
		u3.setUserProfile(Profile.COLLABORATOR);
		u4.setUserProfile(Profile.COLLABORATOR);
		u5.setUserProfile(Profile.COLLABORATOR);

		// create the project and set a user to Project manager
		p1 = c1.getProjectsContainer().createProject("Teste", "blablabla", u2);
		p1.getProjectTeam().clear();
		p1.getTaskRepository().getAllTasksfromProject().clear();

		// add project to the Company Project list
		c1.getProjectsContainer().addProjectToProjectContainer(p1);

		// create project collabotors with u3, u4 and u5 users
		collaborator1 = p1.createProjectCollaborator(u3, 120);
		collaborator2 = p1.createProjectCollaborator(u4, 130);
		collaborator3 = p1.createProjectCollaborator(u5, 150);

		// add collaborators to project
		p1.addProjectCollaboratorToProjectTeam(collaborator1);
		p1.addProjectCollaboratorToProjectTeam(collaborator2);
		p1.addProjectCollaboratorToProjectTeam(collaborator3);

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

		p1.getTaskRepository().addTaskToProject(testTask);
		p1.getTaskRepository().addTaskToProject(testTask2);

	}

	@After
	public void tearDown() {
		Company.clear();
		u1 = null;
		u2 = null;
		u3 = null;
		u4 = null;
		u5 = null;
		p1 = null;
		testTask = null;
		testTask2 = null;
		collaborator1 = null;
		collaborator2 = null;
		collaborator3 = null;
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

		// adds collaborator 1 and 2 to Tasks
		TaskCollaborator testTaskCollaborator1 = testTask.createTaskCollaborator(collaborator1);
		TaskCollaborator testTask2Collaborator2 = testTask.createTaskCollaborator(collaborator2);
		testTask.addTaskCollaboratorToTask(testTaskCollaborator1);
		testTask2.addTaskCollaboratorToTask(testTask2Collaborator2);

		// Creates a new list and adds user to that list, to compare with userList
		// inside ProjectTeam
		List<ProjectCollaborator> testTwoUsersOnTasks = new ArrayList<ProjectCollaborator>();

		testTwoUsersOnTasks.add(collaborator3);

		assertEquals(testTwoUsersOnTasks, p1.getCollaboratorsWithoutTasks());
	}

	/**
	 * Adds two users to the Project and two of them to tasks, then removes one from
	 * the task. The test List with the output of getCollaboratorsWithoutTasks Must
	 * contain one user
	 */
	@Test
	public void US355_UserRemovedFromTask() {

		// adds collaborator 1 and 2 to Tasks
		TaskCollaborator testTaskCollaborator1 = testTask.createTaskCollaborator(collaborator1);
		TaskCollaborator testTask2Collaborator2 = testTask.createTaskCollaborator(collaborator2);
		testTask.addTaskCollaboratorToTask(testTaskCollaborator1);
		testTask2.addTaskCollaboratorToTask(testTask2Collaborator2);
		testTask2.removeProjectCollaboratorFromTask(collaborator2);

		// Creates a new list and adds user to that list, to compare with userList
		// inside ProjectTeam
		List<ProjectCollaborator> testOneUserOnTask = new ArrayList<ProjectCollaborator>();

		testOneUserOnTask.add(collaborator2);
		testOneUserOnTask.add(collaborator3);

		assertEquals(testOneUserOnTask, p1.getCollaboratorsWithoutTasks());
	}

}
