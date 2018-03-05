package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Group 3
 * 
 *         Tests the Create Project Controller.
 *
 */
public class US301CreateProjectControllerTest {

	Company c1;
	User u1;
	US301CreateProjectController createNewProject;
	Project newProject;

	@Before
	public void setUp() {

		// Company creation
		c1 = Company.getTheInstance();

		// User creation
		u1 = c1.getUsersRepository().createUser("Leonor", "leonor@gmail.com", "001", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");
		// add users to company
		c1.getUsersRepository().addUserToUserRepository(u1);

		// set user as Director
		u1.setUserProfile(Profile.DIRECTOR);
	}

	@After
	public void tearDown() {
		Company.clear();
		c1 = null;
		u1 = null;
		createNewProject = null;
		newProject = null;
	}

	/**
	 * This method asserts if the project is sucessfully created by asserting that
	 * the project list is empty and then asserting that the project list has a new
	 * project.
	 * 
	 * Also it checks if the user u1 is the project manager.
	 */
	@Test
	public void CreateProject() {

		// Empty List to compare to the project repository with no project inside
		List<Project> emptyProjectList = new ArrayList<Project>();

		// Asserts if the project list is empty
		assertEquals(emptyProjectList, c1.getProjectsRepository().getAllProjects());

		// Creates the controller to create a project
		createNewProject = new US301CreateProjectController();

		// Creates the project using the controller
		newProject = createNewProject.createProject("name", "description", u1);

		// List with a project to compare
		List<Project> projectList = new ArrayList<Project>();
		projectList.add(newProject);

		// Asserts if the project repository has the new project in it
		assertEquals(projectList, c1.getProjectsRepository().getAllProjects());

		// Asserts if the user u1 is the project manager
		assertTrue(newProject.isProjectManager(u1));

	}

	/**
	 * This test confirms that Budget and effort Units setters are working
	 * correctly.
	 * 
	 */
	@Test
	public void testSetBudgetAndEffortUnits() {
		// Creates the controller to create a project
		createNewProject = new US301CreateProjectController();

		// Creates the project using the controller
		newProject = createNewProject.createProject("name", "description", u1);

		// asserts the created project starts with default
		assertEquals(newProject.getProjectBudget(), 0);
		assertTrue(newProject.getEffortUnit().equals(EffortUnit.HOURS));

		// then calls both setBudget and changeEffortUnits methods
		// and asserts both values have been changed successfully
		createNewProject.changeEffortUnitToPersonMonth();
		createNewProject.changeBudget(123456);
		assertEquals(newProject.getProjectBudget(), 123456);
		assertTrue(newProject.getEffortUnit().equals(EffortUnit.PERSON_MONTH));

		// finally, attempts to call the changeEffortUnit() method again
		// and that the project remains measured in "Person_Month"
		createNewProject.changeEffortUnitToPersonMonth();
		assertTrue(newProject.getEffortUnit().equals(EffortUnit.PERSON_MONTH));

	}

	/**
	 * This test confirms the ListUsersController method is working correctly, and
	 * that it returns the Users as string of data and NOT an object
	 * 
	 */
	@Test
	public void testlistActiveCollaboratorsController() {
		createNewProject = new US301CreateProjectController();

		// given u1 as director, asserts no ActiveCollaborators are listed
		assertTrue(u1.getUserProfile().equals(Profile.DIRECTOR));
		assertEquals(createNewProject.listActiveCollaborators().size(), 0);

		// given u1 as collaborator, asserts one ActiveCollaborators are listed
		u1.setUserProfile(Profile.COLLABORATOR);
		assertTrue(u1.getUserProfile().equals(Profile.COLLABORATOR));
		assertEquals(createNewProject.listActiveCollaborators().size(), 1);

		// then, asserts the index 0 of the actual list matches the first collaborator
		String user1String = "001: Leonor (leonor@gmail.com; 930000000) - Empregado";

		assertTrue(createNewProject.listActiveCollaborators().get(0).equals("[1] \n" + user1String));

	}

	/**
	 * Asserts the user selection method is working correctly, attempting to select
	 * the first indexed User from the list. If the controller is not reset,
	 * 
	 */
	@Test
	public void testListUsersController_SelectUser() {
		// calls the List Active Collaborators method to generate a List and display it
		// to the user
		createNewProject = new US301CreateProjectController();
		createNewProject.listActiveCollaborators();

		// selects user from "Collaborator number 1" (corresponding to the User List's
		// index 0
		// as no collaborators exist, index 0 must return null
		assertEquals(createNewProject.selectCollaborator(1), null);

		// sets u1 as collaborator and remakes the controller, and asserts the same
		// selection now returns u1
		u1.setUserProfile(Profile.COLLABORATOR);
		createNewProject = new US301CreateProjectController();
		assertEquals(createNewProject.listActiveCollaborators().size(), 1);
		assertEquals(createNewProject.selectCollaborator(1), u1);

		// then, without resetting the controller, asserts that selecting an invalid
		// index number
		assertEquals(createNewProject.selectCollaborator(5), u1);
	}

	/**
	 * This test confirms the userDataToString method is working correctly, that it
	 * returns the User as string of data and NOT an object
	 * 
	 */
	@Test
	public void testUserDataToStringController() {

		createNewProject = new US301CreateProjectController();

		// creates a string matching u1's data and asserts as true
		String user1String = "001: Leonor (leonor@gmail.com; 930000000) - Empregado";
		assertTrue(user1String.equals(createNewProject.userDataToString(u1)));
	}

}
