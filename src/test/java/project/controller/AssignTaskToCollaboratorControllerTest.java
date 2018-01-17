package project.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.User;
import project.model.UserRepository;

public class AssignTaskToCollaboratorControllerTest {

	Company myCompany;
	UserRepository userRepository;
	User user1;
	User userAdmin;
	Project project;
	ProjectRepository projectRepository;
	Task testTask, testTask2, testTask3, testTask4, testTask5, testTask6, testTask7;
	ProjectCollaborator projCollaborator;

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

	@Test
	public void assignTaskToCollaboratorControllerTest() {

		// asserts which user is the Project Manager
		assertFalse(project.isProjectManager(user1));
		assertTrue(project.isProjectManager(userAdmin));

		// creates the Controller and asserts the list of unstarted tasks starts at 0
		CreateTaskController testControl = new CreateTaskController(project);

		// creates and adds a task using the controller and asserts a task was added
		testTask = testControl.addTask("Test dis agen pls", 10, Calendar.getInstance(), Calendar.getInstance(), 10);
		testTask2 = testControl.addTask("Test dis agen pls", 10, Calendar.getInstance(), Calendar.getInstance(), 10);
		testTask3 = testControl.addTask("Test dis agen pls", 10, Calendar.getInstance(), Calendar.getInstance(), 10);
		testTask4 = testControl.addTask("Test dis agen pls", 10, Calendar.getInstance(), Calendar.getInstance(), 10);
		testTask5 = testControl.addTask("Test dis agen pls", 10, Calendar.getInstance(), Calendar.getInstance(), 10);
		testTask6 = testControl.addTask("Test dis agen pls", 10, Calendar.getInstance(), Calendar.getInstance(), 10);

		// Creates an int that olds the projectID Code
		int projectCode = project.getIdCode();
		String taskId = testTask.getTaskID();

		// creates the Controller
		AssignTaskToCollaboratorController controllerAssignTaskToProjectCollaborator = new AssignTaskToCollaboratorController(
				projectCode);

		assertTrue(controllerAssignTaskToProjectCollaborator.assignTaskToProjectCollaboratorController(taskId,
				projCollaborator));

	}
}
