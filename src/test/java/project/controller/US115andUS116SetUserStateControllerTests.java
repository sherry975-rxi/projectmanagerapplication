package project.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.User;

public class US115andUS116SetUserStateControllerTests {

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
		US115andUS116SetUserStateController testUserStateController = new US115andUS116SetUserStateController(newUser2);
		assertTrue("(ACTIVE)".equals(testUserStateController.userStateAsString()));
		testUserStateController.changeUserState();
		assertTrue("(DISABLED)".equals(testUserStateController.userStateAsString()));
	}

}
