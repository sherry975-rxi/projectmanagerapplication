package sprint.two;

import static org.junit.Assert.assertEquals;

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
import project.model.User;

public class US360 {

	/**
	 * Tests US360
	 * 
	 * US360: - Como Gestor de projeto, quero obter a lista de tarefas do projeto
	 * sem colaboradores ativos atribuídos.
	 * 
	 * uses method getListofTasksWithoutCollaboratorsAssigned
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
	Task testTask, testTask2, testTask3, testTask4;

	@Before
	public void setUp() {
		// create company and clear ProjectRepository and UsersRepository

		c1 = Company.getTheInstance();

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
		p1.getTaskRepository().getProjectTaskRepository().clear();

		// add project to the Company Project list
		c1.getProjectsRepository().addProjectToProjectRepository(p1);

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
		testTask2 = p1.getTaskRepository().createTask("Tests", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask3 = p1.getTaskRepository().createTask("Test1", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask4 = p1.getTaskRepository().createTask("Test2", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);

		// clear all tasks's team
		testTask.getTaskTeam().clear();
		testTask2.getTaskTeam().clear();
		testTask3.getTaskTeam().clear();
		testTask4.getTaskTeam().clear();

		// add tasks to project p1
		p1.getTaskRepository().addProjectTask(testTask);
		p1.getTaskRepository().addProjectTask(testTask2);
		p1.getTaskRepository().addProjectTask(testTask3);
		p1.getTaskRepository().addProjectTask(testTask4);

		// add taskworkers (collaborator1 and 2) to tasks 1 and 2
		testTask.addProjectCollaboratorToTask(collaborator1);
		testTask2.addProjectCollaboratorToTask(collaborator2);
	}

	@After
	public void tearDown() {
		c1.clear();
		u1 = null;
		u2 = null;
		u3 = null;
		u4 = null;
		u5 = null;
		p1 = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
		testTask4 = null;
		collaborator1 = null;
		collaborator2 = null;
		collaborator3 = null;
	}

	/**
	 * Tests that testTask3 and testTask4 don't have any collaborators assigned and
	 * are the tasks in the list
	 */
	@Test
	public void testGetTasksListWithoutActiveUsers() {
		// Creates a new list to compare and add the tasks that don't have collaborators

		List<Task> tasksWithoutUsersTest = new ArrayList<Task>();

		// Adds a new taskCollaborator
		testTask3.addProjectCollaboratorToTask(collaborator1);

		// Sets collaborator state to inactive
		testTask3.getTaskTeam().get(0).addFinishDateForTaskCollaborator();
		tasksWithoutUsersTest.add(testTask3);
		tasksWithoutUsersTest.add(testTask4);

		assertEquals(tasksWithoutUsersTest, p1.getTaskRepository().getAllTasksWithoutCollaboratorsAssigned());
	}
}
