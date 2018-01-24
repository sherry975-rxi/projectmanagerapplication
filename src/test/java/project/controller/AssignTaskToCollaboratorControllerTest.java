package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

public class AssignTaskToCollaboratorControllerTest {

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

		// creates and adds a task using the controller and asserts a task was added
		testTask = project.getTaskRepository().createTask("Test dis agen pls");
		project.getTaskRepository().addProjectTask(testTask);

		// Creates an int that holds the projectID Code
		int projectCode = project.getIdCode();
		String taskId = testTask.getTaskID();

		// creates the Controller
		AssignTaskToCollaboratorsController controllerAssignTaskToProjectCollaborator = new AssignTaskToCollaboratorsController(
				projectCode);

		/*
		 * Checks that its possible to add a Task to the ProjectCollaborator
		 */
		assertTrue(controllerAssignTaskToProjectCollaborator.assignTaskToProjectCollaboratorController(taskId,
				projCollaborator));

		/*
		 * It's not possible to add a ProjectCollaborator to the task, because he
		 * already belongs to the task team
		 */
		assertFalse(controllerAssignTaskToProjectCollaborator.assignTaskToProjectCollaboratorController(taskId,
				projCollaborator));

		/*
		 * Deactives the user from the Task
		 */
		testTask.removeProjectCollaboratorFromTask(projCollaborator);

		/*
		 * Checks that its possible to add the ProjectCollaborator again to the task
		 */
		assertTrue(controllerAssignTaskToProjectCollaborator.assignTaskToProjectCollaboratorController(taskId,
				projCollaborator));

		/*
		 * Tries to assign a task to a ProjectCollaborator to a Task that doesn't exist
		 * Returns false
		 */
		assertFalse(controllerAssignTaskToProjectCollaborator.assignTaskToProjectCollaboratorController("NullTask",
				projCollaborator));

		/*
		 * Tries to assign a task to a ProjectCollaborator that doesn't exist Returns
		 * false
		 */
		assertFalse(controllerAssignTaskToProjectCollaborator.assignTaskToProjectCollaboratorController(taskId,
				nullProjectCollaborator));

		/*
		 * Tries to assign a task to a ProjectCollaborator that doesn't exist to a task
		 * that doesn't exist false
		 */
		assertFalse(controllerAssignTaskToProjectCollaborator.assignTaskToProjectCollaboratorController("NullTask",
				nullProjectCollaborator));

		/*
		 * Gets a project collaborator from a user using the controller
		 */
		assertEquals(projCollaborator, controllerAssignTaskToProjectCollaborator.getProjectCollaboratorFromUser(user1));

		/*
		 * Gets the ID of a Project using the controller
		 */
		controllerAssignTaskToProjectCollaborator.setProjectID(1);
		Integer um = 1;
		assertEquals(um, controllerAssignTaskToProjectCollaborator.getProjectID());

	}

	@Test
	public void getTaskByTaskId() {

		// creates and adds a task using the controller and asserts a task was added
		testTask = project.getTaskRepository().createTask("Test dis agen pls");
		project.getTaskRepository().addProjectTask(testTask);

		// Creates an int that holds the projectID Code
		int projectCode = project.getIdCode();
		String taskId = testTask.getTaskID();

		// Creates an assignTasktOProjectCollaborator controller
		AssignTaskToCollaboratorsController controllerAssignTaskToProjectCollaborator = new AssignTaskToCollaboratorsController(
				projectCode);

		// checks if the method returns a task by it's ID
		assertEquals(controllerAssignTaskToProjectCollaborator.getTaskByTaskID("1.1"), testTask);

	}

	@Test
	public void setProjectIDFromTaskID() {

		// creates and adds a task using the controller and asserts a task was added
		testTask = project.getTaskRepository().createTask("Test dis agen pls");
		project.getTaskRepository().addProjectTask(testTask);
		Integer projId = 1;

		// Creates an int that holds the projectID Code
		int projectCode = project.getIdCode();
		String taskId = testTask.getTaskID();

		// Creates an assignTasktOProjectCollaborator controller
		AssignTaskToCollaboratorsController controllerAssignTaskToProjectCollaborator = new AssignTaskToCollaboratorsController(
				projectCode);

		controllerAssignTaskToProjectCollaborator.setProjectIDFromTaskID(taskId);
		assertEquals(controllerAssignTaskToProjectCollaborator.getProjectID(), projId);

	}

}
