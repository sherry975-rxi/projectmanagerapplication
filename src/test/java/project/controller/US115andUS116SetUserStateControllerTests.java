package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.Profile;
import project.model.User;
import project.services.UserService;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controller" })
public class US115andUS116SetUserStateControllerTests {

	@Autowired
	private UserService userService;

	Profile director = Profile.DIRECTOR;
	Profile collaborator = Profile.COLLABORATOR;
	User newUser2;
	User newUser3;

	@Autowired
	US115andUS116SetUserStateController testUserStateController;

	@Before
	public void setUp() {

		testUserStateController.setToChangeState(newUser2);

		// creates the user and adds it to the dummy database, then refreshes the in
		// memory User List
		newUser2 = userService.createUser("Manel", "user2@gmail.com", "001", "Empregado", "930000000", "Rua Bla",
				"BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");

		userService.getAllUsersFromUserContainer();

	}

	@After
	public void clear() {

		newUser2 = null;
		newUser3 = null;

	}

	@Test
	public void testSetUserAsInactiveController() {

		// given only 1 user in the database
		assertEquals(userService.getAllUsersFromUserContainer().size(), 1);

		// When the User starts as Active and creates the User State Controller
		assertTrue(newUser2.isSystemUserStateActive());
		testUserStateController.setToChangeState(newUser2);

		// Then Uses the controller to deactivate the user and confirms its state.
		testUserStateController.changeUserState();
		assertFalse(newUser2.isSystemUserStateActive());

		// when the userContainer is refreshed from the database info
		userService.getAllUsersFromUserContainer();
		newUser2 = userService.getAllUsersFromUserContainer().get(0);

		// then newUser2 must be Manel, and must be inactive
		assertTrue("Manel".equals(newUser2.getName()));
		assertFalse(newUser2.isSystemUserStateActive());
	}

	@Test
	public void testSetUserAsActiveController() {
		// Asserts the User starts as Active and creates the User State Controller
		assertTrue(newUser2.isSystemUserStateActive());
		testUserStateController.setToChangeState(newUser2);

		// Uses the controller to deactivate the user and confirms its state.
		testUserStateController.changeUserState();
		assertFalse(newUser2.isSystemUserStateActive());

		// Uses the controller to reactivate the user and confirms its state.
		testUserStateController.changeUserState();
		assertTrue(newUser2.isSystemUserStateActive());

		// when the userContainer is refreshed from the database info
		userService.getAllUsersFromUserContainer();
		newUser2 = userService.getAllUsersFromUserContainer().get(0);

		// then newUser2 must be Manel, and must be active
		assertTrue("Manel".equals(newUser2.getName()));
		assertTrue(newUser2.isSystemUserStateActive());
	}

	@Test
	public void userDataAsString() {
		testUserStateController.setToChangeState(newUser2);
		assertTrue("(ACTIVE)".equals(testUserStateController.userStateAsString()));
		testUserStateController.changeUserState();
		assertTrue("(DISABLED)".equals(testUserStateController.userStateAsString()));
	}

}
