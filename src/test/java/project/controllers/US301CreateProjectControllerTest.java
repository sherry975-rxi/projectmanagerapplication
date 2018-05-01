package project.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.*;
import project.services.ProjectService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Group 3
 * 
 *         Tests the Create Project Controller.
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controllers" })
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

	@After
	public void clear() {

		u1 = null;
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
		assertEquals(emptyProjectList, projectContainer.getAllProjectsfromProjectsContainer());

		// Creates the project using the controllers
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
		// Creates the project using the controllers
		newProject = us301CreateProjectController.createProject("name", "description", u1);

		// asserts the created project starts with default
		assertEquals(newProject.getProjectBudget(), 0, 0.001);
		assertTrue(newProject.getEffortUnit().equals(EffortUnit.HOURS));

		// then calls both setBudget and changeEffortUnits methods
		// and asserts both values have been changed successfully
		us301CreateProjectController.changeEffortUnitToPersonMonth();
		us301CreateProjectController.changeBudget(123456);
		assertEquals(newProject.getProjectBudget(), 123456, 0.001);
		assertTrue(newProject.getEffortUnit().equals(EffortUnit.PM));

		// finally, attempts to call the changeEffortUnit() method again
		// and that the project remains measured in "Person_Month"
		us301CreateProjectController.changeEffortUnitToPersonMonth();
		assertTrue(newProject.getEffortUnit().equals(EffortUnit.PM));

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
	 * the first indexed User from the list. If the controllers is not reset,
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

		// sets u1 as collaborator and remakes the controllers, and asserts the same
		// selection now returns u1
		u1.setUserProfile(Profile.COLLABORATOR);
		assertEquals(us301CreateProjectController.listActiveCollaborators().size(), 1);
		assertEquals(us301CreateProjectController.selectCollaborator(1), u1);

		// then, without resetting the controllers, asserts that selecting an invalid
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

	/**
	 * This tests confirms if the method selectCalculationMethod() is working properly,
	 * by setting the defined cost calculation methods for the given project
	 */

	@Test
	public void testSetAndGetCalculationMethods(){

		//creation of the project (new Project, u1 is the project manager
		newProject = us301CreateProjectController.createProject("name", "description", u1);

		//creation of a list with the allowed calculation methods

		 ArrayList<Integer> allowedMethods = new ArrayList<>();
		 allowedMethods.add(1);
		 allowedMethods.add(2);

		 newProject.createAvailableCalculationMethodsString(allowedMethods);

		List<CalculationMethod> allowedMethodNames = new ArrayList<>();
		allowedMethodNames.add(CalculationMethod.CI);
		allowedMethodNames.add(CalculationMethod.CF);

		 assertEquals(allowedMethodNames, newProject.listAvaliableCalculationMethods());

	}

	/**
	 * 	 This tests confirms if the method selectCalculationMethod() is working properly,
	 * 	 by setting the defined cost calculation methods for the given project
	 */

	@Test
	public void testSelectCalculationMethods(){

		//creation of the project (new Project, u1 is the project manager
		newProject = us301CreateProjectController.createProject("name", "description", u1);

		ArrayList<Integer> allowedMethods = new ArrayList<>();
		allowedMethods.add(2);
		allowedMethods.add(3);

		us301CreateProjectController.selectCalculationMethods(allowedMethods);

		assertEquals(CalculationMethod.CF, newProject.getCalculationMethod());

		assertFalse(newProject.isCalculationMethodAllowed(CalculationMethod.CI.getCode()));

	}

	/**
	 * 	 This tests confirms if the method allowDisableCalculationMethod() is working properly,
	 * 	 by checking the size of the list after insertion or deletion of given method
	 */

	@Test
	public void testAllowDisableCalculationMethods(){

		//creation of the project (new Project, u1 is the project manager
		newProject = us301CreateProjectController.createProject("name", "description", u1);

		ArrayList<Integer> allowedMethods = new ArrayList<>();
		allowedMethods.add(1);
		allowedMethods.add(2);
		allowedMethods.add(3);

		//Checks if after insertion of method #2 (already in the list), the size of the returned list is of 2

		assertEquals(2, us301CreateProjectController.allowDisableCalculationMethods(allowedMethods, 2).size());

		//checks if after insertion of method #2 (removed from the list), the size of the returned list is of 3

		assertEquals(3, us301CreateProjectController.allowDisableCalculationMethods(allowedMethods, 2).size());

		allowedMethods = new ArrayList<>();
		allowedMethods.add(1);

		//after insertion of method #1 (the only method in the list), the list cannot be empty; so will return size 1

		assertEquals(1, us301CreateProjectController.allowDisableCalculationMethods(allowedMethods, 1). size());

		assertEquals(1, us301CreateProjectController.allowDisableCalculationMethods(allowedMethods, 1). size());

	}

	/**
	 * 	 This tests confirms if the method selectCalculationMethod() is working properly,
	 * 	 by setting the defined cost calculation methods for the given project
	 */

	@Test
	public void testSelectProjectAndUser(){

		// User creation
		User u2 = userContainer.createUser("Tiago", "tiago@gmail.com", "002", "Empregado", "930000000", "Rua Maria",
				"4444-444", "221234567", "Porto", "Portugal");
		u2.setUserProfile(Profile.COLLABORATOR);

		//creation of the project (new Project, u1 is the project manager
		newProject = us301CreateProjectController.createProject("Teste", "Testando", u1);
		us301CreateProjectController.setCreatedProject(newProject);

		us301CreateProjectController.setSelectedUser(u2);
		// creates a string matching u2's data and asserts as true
		String user2String = "002: Tiago (tiago@gmail.com; 930000000) - Empregado";
		assertTrue(user2String.equals(us301CreateProjectController.userDataToString(u2)));

		List<User> activeCollaboratorList = new ArrayList<>();
		activeCollaboratorList.add(u2);
		us301CreateProjectController.setActiveCollaboratorList(activeCollaboratorList);
		// then, asserts the index 0 of the actual list matches the first collaborator
		assertTrue(us301CreateProjectController.listActiveCollaborators().get(0).equals("[1] \n" + user2String));



	}

}
