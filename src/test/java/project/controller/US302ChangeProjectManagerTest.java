package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import project.Repository.ProjectsRepository;
import project.Repository.UserRepository;
import project.Services.ProjectService;
import project.Services.UserService;
import project.model.Profile;
import project.model.Project;
import project.model.User;

/**
 * @author Group 3
 * 
 *         Tests the Create Project Controller.
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class US302ChangeProjectManagerTest {

	UserService userService;
	ProjectService projectService;
	User userFirstManager;
	User userNewManager;
	Project newProject;
	US302ChangeProjectManagerController changeManagerController;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectsRepository projectRepository;

	@Before
	public void setUp() {

		// creates an UserContainer
		userService = new UserService();
		userService.setUserRepository(userRepository);

		// creates a Project Container
		projectService = new ProjectService();
		projectService.setProjectsRepository(projectRepository);

		// User creation
		userFirstManager = userService.createUser("Leonor", "leonor@gmail.com", "001", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");
		userNewManager = userService.createUser("Lenny", "lenny@gmail.com", "002", "Empregado", "940000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		// set user as Director
		userFirstManager.setUserProfile(Profile.COLLABORATOR);

		newProject = projectService.createProject("name", "description", userFirstManager);

		changeManagerController = new US302ChangeProjectManagerController(newProject);
		changeManagerController.userService = userService;

	}

	/**
	 * This test confirms the ListUsersController method is working correctly, and
	 * that it returns the Users as string of data and NOT an object
	 * 
	 */
	@Test
	public void testlistActiveCollaboratorsController() {

		// given userFirstManager as the only collaborator, asserts one
		// ActiveCollaborators are listed
		assertTrue(userFirstManager.getUserProfile().equals(Profile.COLLABORATOR));
		assertEquals(changeManagerController.listPossibleManagers().size(), 1);

		// then, asserts the index 0 of the actual list matches the first collaborator
		String user1String = "001: Leonor (leonor@gmail.com; 930000000) - Empregado";

		assertTrue(changeManagerController.listPossibleManagers().get(0).equals("[1] \n" + user1String));

	}

	/**
	 * Asserts the user selection method is working correctly, attempting to select
	 * the first indexed User from the list. If the controller is not reset,
	 * 
	 */
	@Test
	public void testListCollaborators_SelectUser() {

		/*
		 * calls the List Active Collaborators method to generate a List and display it
		 * to the user
		 */
		changeManagerController.listPossibleManagers();

		/*
		 * selects user from "Collaborator number 2" (corresponding to the User List's
		 * index 1 as only one collaborator exists, index 1 must return the original
		 * project manager
		 */
		assertEquals(changeManagerController.selectNewManager(1), userFirstManager);

		/*
		 * sets userNewManager as collaborator and remakes the controller, and asserts
		 * the same selection now returns userNewManager
		 */
		userNewManager.setUserProfile(Profile.COLLABORATOR);
		assertEquals(changeManagerController.listPossibleManagers().size(), 2);
		assertEquals(changeManagerController.selectNewManager(2), userNewManager);

		/*
		 * then, without resetting the controller, asserts that selecting an invalid
		 * index number will maintain the existing selection
		 */
		assertEquals(changeManagerController.selectNewManager(5), userNewManager);
	}

	/**
	 * Asserts the change manager method is working correctly, that it returns false
	 * when the user attempts to change to the current manager, and true when it
	 * changes to a new manager
	 * 
	 */
	@Test
	public void testChangeManagerController_IsManagerDifferent() {
		// sets userNewManager as collaborator
		// calls the List Active Collaborators method to generate a List and display it
		// to the user
		userNewManager.setUserProfile(Profile.COLLABORATOR);
		changeManagerController.listPossibleManagers();

		// selects user from invalid index entry (corresponding to the User List's
		// non existing index 10
		// this will cause the selectUser method to keep the default, existing manager
		// as selection
		assertFalse(changeManagerController.isNewManagerDifferentFromFirst(10));
		assertTrue(newProject.isProjectManager(userFirstManager));

		// then, given the selection of a different collaborator
		// asserts the new Manager is different from the first and that the change is
		// valid

		assertTrue(changeManagerController.isNewManagerDifferentFromFirst(2));
		assertTrue(newProject.isProjectManager(userNewManager));

	}

	/**
	 * This test confirms the userDataToString method is working correctly, that it
	 * returns the User as string of data and NOT an object
	 * 
	 */
	@Test
	public void testManagerDataToStringController() {

		// creates a string matching u1's data and asserts as true
		String firstManagerString = "001: Leonor (leonor@gmail.com; 930000000) - Empregado";
		assertTrue(firstManagerString.equals(changeManagerController.collaboratorInfoToString(userFirstManager)));
	}

}
