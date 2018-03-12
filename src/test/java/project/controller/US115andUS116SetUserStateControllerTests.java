package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.Profile;
import project.model.User;
import project.model.UserContainer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class US115andUS116SetUserStateControllerTests {

	Profile director = Profile.DIRECTOR;
	Profile collaborator = Profile.COLLABORATOR;


	UserContainer userContainer;
	User newUser2;
	User newUser3;

	@Before
	public void setUp() {

		userContainer = new UserContainer();


		newUser2 = userContainer.createUser("Manel", "user2@gmail.com", "001", "Empregado",
				"930000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");

		userContainer.addUserToUserRepository(newUser2);

	}

	@After
	public void tearDown() {

		userContainer = null;
		newUser2 = null;
	}

	@Test
	public void testSetUserAsInactiveController() {
		// Asserts the User starts as Active and creates the User State Controller
		assertTrue(newUser2.isSystemUserStateActive());
		US115andUS116SetUserStateController testUserStateController = new US115andUS116SetUserStateController(newUser2);

		// Uses the controller to deactivate the user and confirms its state.
		testUserStateController.changeUserState();
		assertFalse(newUser2.isSystemUserStateActive());
	}

	@Test
	public void testSetUserAsActiveController() {
		// Asserts the User starts as Active and creates the User State Controller
		assertTrue(newUser2.isSystemUserStateActive());
		US115andUS116SetUserStateController testUserStateController = new US115andUS116SetUserStateController(newUser2);

		// Uses the controller to deactivate the user and confirms its state.
		testUserStateController.changeUserState();
		assertFalse(newUser2.isSystemUserStateActive());

		// Uses the controller to reactivate the user and confirms its state.
		testUserStateController.changeUserState();
		assertTrue(newUser2.isSystemUserStateActive());
	}

	@Test
	public void userDataAsString() {
		US115andUS116SetUserStateController testUserStateController = new US115andUS116SetUserStateController(newUser2);
		assertTrue("(ACTIVE)".equals(testUserStateController.userStateAsString()));
		testUserStateController.changeUserState();
		assertTrue("(DISABLED)".equals(testUserStateController.userStateAsString()));
	}

}
