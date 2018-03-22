package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import project.Services.ProjectService;
import project.Services.UserService;
import project.model.EffortUnit;
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
@ComponentScan({ "project.services", "project.model", "project.controller" })
public class US301CreateProjectControllerTest {
	@Autowired
	UserService userContainer;
	@Autowired
	ProjectService projectContainer;
	@Autowired
	US301CreateProjectController us301CreateProjectController;

	User u1;
	Project newProject;

	@Before
	public void setUp() {

		// User creation
		u1 = userContainer.createUser("Leonor", "leonor@gmail.com", "001", "Empregado", "930000000", "Rua Maria",
				"4444-444", "221234567", "Porto", "Portugal");

		// set user as Director
		u1.setUserProfile(Profile.DIRECTOR);

		// add users to company
		userContainer.addUserToUserRepositoryX(u1);
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
		assertEquals(emptyProjectList, projectContainer.getAllProjectsfromProjectsContainer());

		// Creates the project using the controller
		newProject = us301CreateProjectController.createProject("name", "description", u1);

		// List with a project to compare
		List<Project> projectList = new ArrayList<Project>();
		projectList.add(newProject);

		// Asserts if the project repository has the new project in it
		assertEquals(projectList, projectContainer.getAllProjectsfromProjectsContainer());

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
		// Creates the project using the controller
		newProject = us301CreateProjectController.createProject("name", "description", u1);

		// asserts the created project starts with default
		assertEquals(newProject.getProjectBudget(), 0);
		assertTrue(newProject.getEffortUnit().equals(EffortUnit.HOURS));

		// then calls both setBudget and changeEffortUnits methods
		// and asserts both values have been changed successfully
		us301CreateProjectController.changeEffortUnitToPersonMonth();
		us301CreateProjectController.changeBudget(123456);
		assertEquals(newProject.getProjectBudget(), 123456);
		assertTrue(newProject.getEffortUnit().equals(EffortUnit.PERSON_MONTH));

		// finally, attempts to call the changeEffortUnit() method again
		// and that the project remains measured in "Person_Month"
		us301CreateProjectController.changeEffortUnitToPersonMonth();
		assertTrue(newProject.getEffortUnit().equals(EffortUnit.PERSON_MONTH));

	}

	/**
	 * This test confirms the ListUsersController method is working correctly, and
	 * that it returns the Users as string of data and NOT an object
	 * 
	 */
	@Test
	public void testlistActiveCollaboratorsController() {

		// given u1 as director, asserts no ActiveCollaborators are listed
		assertTrue(u1.getUserProfile().equals(Profile.DIRECTOR));
		assertEquals(us301CreateProjectController.listActiveCollaborators().size(), 0);

		// given u1 as collaborator, asserts one ActiveCollaborators are listed
		u1.setUserProfile(Profile.COLLABORATOR);
		assertTrue(u1.getUserProfile().equals(Profile.COLLABORATOR));
		assertEquals(us301CreateProjectController.listActiveCollaborators().size(), 1);

		// then, asserts the index 0 of the actual list matches the first collaborator
		String user1String = "001: Leonor (leonor@gmail.com; 930000000) - Empregado";

		assertTrue(us301CreateProjectController.listActiveCollaborators().get(0).equals("[1] \n" + user1String));

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
		us301CreateProjectController.listActiveCollaborators();

		// selects user from "Collaborator number 1" (corresponding to the User List's
		// index 0
		// as no collaborators exist, index 0 must return null
		assertEquals(us301CreateProjectController.selectCollaborator(1), null);

		// sets u1 as collaborator and remakes the controller, and asserts the same
		// selection now returns u1
		u1.setUserProfile(Profile.COLLABORATOR);
		assertEquals(us301CreateProjectController.listActiveCollaborators().size(), 1);
		assertEquals(us301CreateProjectController.selectCollaborator(1), u1);

		// then, without resetting the controller, asserts that selecting an invalid
		// index number
		assertEquals(us301CreateProjectController.selectCollaborator(5), u1);
	}

	/**
	 * This test confirms the userDataToString method is working correctly, that it
	 * returns the User as string of data and NOT an object
	 * 
	 */
	@Test
	public void testUserDataToStringController() {

		// creates a string matching u1's data and asserts as true
		String user1String = "001: Leonor (leonor@gmail.com; 930000000) - Empregado";
		assertTrue(user1String.equals(us301CreateProjectController.userDataToString(u1)));
	}

}
