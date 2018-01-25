package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.User;
import project.model.UserRepository;

public class US361AssignTaskToCollaboratorControllerTest {

	Company myCompany;
	UserRepository userRepository;
	User user1;
	User userAdmin;
	Project project;
	ProjectRepository projectRepository;
	Task testTask, testTask2, testTask3, testTask4, testTask5, testTask6, testTask7;
	ProjectCollaborator projCollaborator;
	TaskCollaborator taskCollaborator;
	ProjectCollaborator nullProjectCollaborator;
	US361AssignTaskToCollaboratorsController controller;

	@Before
	public void setUp() {
		// create company

		myCompany = Company.getTheInstance();

		// creates an UserRepository
		userRepository = myCompany.getUsersRepository();

		// creates a ProjectRepository
		projectRepository = myCompany.getProjectsRepository();

		userRepository.getAllUsersFromRepository().clear();

		// create user
		user1 = userRepository.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		// create user admin
		userAdmin = userRepository.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		// add user to user list
		userRepository.addUserToUserRepository(user1);
		userRepository.addUserToUserRepository(userAdmin);
		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		userAdmin.setUserProfile(Profile.COLLABORATOR);
		// create project
		project = projectRepository.createProject("name3", "description4", userAdmin);// !!!
		projectRepository.addProjectToProjectRepository(project);
		projCollaborator = new ProjectCollaborator(user1, 0);
		project.addProjectCollaboratorToProjectTeam(projCollaborator);
		nullProjectCollaborator = null;

		// creates the task
		testTask = project.getTaskRepository().createTask("Task AAAA");
		controller = new US361AssignTaskToCollaboratorsController(project, testTask);

	}

	@After
	public void tearDown() {
		Company.clear();
		user1 = null;
		testTask = null;
		testTask2 = null;
		project = null;
		projectRepository = null;
		userRepository = null;
		projCollaborator = null;

	}

	/**
	 * Tests the assignCollaboratorToTask
	 */
	@Test
	public void assignTaskToCollaboratorControllerTest() {
		controller.setUserToAddToTask(0);
		assertTrue(controller.assignCollaboratorToTask());

		controller.setUserToAddToTask(0);
		assertFalse(controller.assignCollaboratorToTask());
	}

	/**
	 * Tests the setProjectCollaborator and getProjectCollaborator
	 */
	@Test
	public void setProjectCollaborator() {

		// asserts that the projectCollaborator is null
		assertEquals(controller.getUserToAddToTask(), null);

		// sets the projectCollaborator and then checks if is equal to the
		// projCollaborator
		controller.setUserToAddToTask(0);
		assertEquals(controller.getUserToAddToTask(), projCollaborator);
	}

	/**
	 * Tests the getProjectActiveTeam
	 */
	@Test
	public void getProjectActiveTeam() {

		String info = "Name: " + user1.getName() + "\n" + "Email: " + user1.getEmail() + "\n" + "Function: "
				+ user1.getFunction();

		List<String> expResult = new ArrayList<>();
		expResult.add(info);

		assertEquals(expResult, controller.getProjectActiveTeam());
	}
}
