package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.User;

public class US115andUS116SetUserStateControllerTests {

	Profile director = Profile.DIRECTOR;
	Profile collaborator = Profile.COLLABORATOR;

	Company myCompany;
	User newUser2;
	User newUser3;

	@Before
	public void setUp() {

		myCompany = Company.getTheInstance();

		newUser2 = myCompany.getUsersRepository().createUser("Manel", "user2@gmail.com", "001", "Empregado",
				"930000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");

		myCompany.getUsersRepository().addUserToUserRepository(newUser2);

	}

	@After
	public void tearDown() {
		Company.clear();
		newUser2 = null;
	}

	@Test
	public void testSetUserAsInactiveController() {
		// Asserts the User starts as Active and creates the User State Controller
		assertTrue(newUser2.isUserActive());
		US115andUS116SetUserStateController testUserStateController = new US115andUS116SetUserStateController(newUser2);

		// Uses the controller to deactivate the user and confirms its state.
		testUserStateController.changeUserState();
		assertFalse(newUser2.isUserActive());
	}

	@Test
	public void testSetUserAsActiveController() {
		// Asserts the User starts as Active and creates the User State Controller
		assertTrue(newUser2.isUserActive());
		US115andUS116SetUserStateController testUserStateController = new US115andUS116SetUserStateController(newUser2);

		// Uses the controller to deactivate the user and confirms its state.
		testUserStateController.changeUserState();
		assertFalse(newUser2.isUserActive());

		// Uses the controller to reactivate the user and confirms its state.
		testUserStateController.changeUserState();
		assertTrue(newUser2.isUserActive());
	}

	@Test
	public void userDataAsString() {
		US115andUS116SetUserStateController controller = new US115andUS116SetUserStateController(newUser2);
		assertEquals(controller.userDataAsString(), "001 (ACTIVE) - Unassigned: Manel");

		// Changes the user State
		newUser2.changeUserState();

		// Checks if the user state changed
		assertEquals(controller.userDataAsString(), "001 (DISABLED) - Unassigned: Manel");

		// Sets user profile to director
		newUser2.setUserProfile(director);

		// Checks if returns profile as director
		assertEquals(controller.userDataAsString(), "001 (DISABLED) - Director: Manel");

		// Changes the user state to collaborator
		newUser2.setUserProfile(collaborator);
		// Checks if returns profile as Collaborator
		assertEquals(controller.userDataAsString(), "001 (DISABLED) - Collaborator: Manel");

	}

}
