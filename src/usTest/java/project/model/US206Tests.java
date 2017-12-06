package usTest.java.project.model;

import static org.junit.Assert.assertTrue;

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

class US206Tests {
	/**
	 * Tests US206
	 * 
	 * US206: Como colaborador, eu pretendo remover uma tarefa que consta na minha
	 * lista de tarefas
	 * 
	 */
	Company c1;
	User u1;
	User u2;
	User u3;
	Project p1;
	Task testTask;
	Task testTask2;

	@BeforeEach
	void setUp() {
		// Company creation
		c1 = Company.getTheInstance();
		c1.getUsersList().clear();
		c1.getProjectsList().clear();

		// User creation
		u1 = c1.createUser("Daniel", "daniel@gmail.com", "01", "Director", "910000000", "StreetA", "ZipCodeA", "CityA",
				"DistrictA", "CountryA");
		u2 = c1.createUser("Rita", "rita@gmail.com", "02", "Gestora de Projeto", "920000000", "StreetB", "ZipCodeB",
				"CityB", "DistrictB", "CountryB");
		u3 = c1.createUser("Ana", "ana@gmail.com", "03", "Colaboradora", "930000000", "StreetC", "ZipCodeC", "CityC",
				"DistrictC", "CountryC");
		// create the project and set a user to Project manager
		p1 = c1.createProject("Teste", "blablabla", u2);

		// create task
		testTask = p1.getTaskRepository().createTask("sdfsdfdsfsdf");
		testTask2 = p1.getTaskRepository().createTask("sdfsdfdsfsdf");
	}

	@AfterEach
	void tearDown() {
		Company c1 = null;
		User u1 = null;
		User u2 = null;
		User u3 = null;
		Project p1 = null;
		Task testTask = null;
		Task testTask2 = null;
	}

	@Test
	void testAddThenRemoveUserFromTask() {

		// add users to company
		c1.addUserToUserList(u1);
		c1.addUserToUserList(u2);
		c1.addUserToUserList(u3);

		// set user as collaborator
		u2.setUserProfile(Profile.COLLABORATOR);
		u3.setUserProfile(Profile.COLLABORATOR);

		// set user as Director
		u1.setUserProfile(Profile.DIRECTOR);

		// add project to project list
		c1.addProjectToProjectList(p1);

		// set projects to active state
		p1.setProjectStatus(1);

		// add collaborator to project
		p1.addUserToProjectTeam(u2);
		p1.addUserToProjectTeam(u3);

		// add task to project
		p1.getTaskRepository().addProjectTask(testTask);
		p1.getTaskRepository().addProjectTask(testTask2);

		// add task to collaborator
		testTask.addUserToTask(u2);
		testTask.addUserToTask(u3);
		testTask2.addUserToTask(u3);

		// remove user from task
		testTask.removeUserFromTask(u2);
		testTask2.removeUserFromTask(u3);

		// Creates a new list and adds task to that list, to compare with
		// taskList
		List<User> test = new ArrayList<User>();
		test.add(u3);

		List<User> test1 = new ArrayList<User>();

		assertTrue(test.equals(testTask.getTaskTeam()));
		assertTrue(test1.equals(testTask2.getTaskTeam()));

	}

}
