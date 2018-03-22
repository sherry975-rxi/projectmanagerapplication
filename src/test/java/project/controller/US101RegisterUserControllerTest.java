package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import project.Services.UserService;
import project.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controller" })
public class US101RegisterUserControllerTest {

	@Autowired
	US101RegisterUserController testUserRegistrationController;

	@Autowired
	UserService userService;

	User user1;
	User user2;
	User user3;

	@Before
	public void setUp() {

		// create user
		user1 = userService.createUser("Daniel", "daniel@gmail.com", "001", "Porteiro", "920000000", "Testy Street",
				"2401-343", "Testburg", "Testo", "Testistan");

		testUserRegistrationController.addNewUser("João", "joão.gmail.com", "034", "Testes", "919876787", "Street",
				"2401-343", "Testburg", "Testo", "Testistan", "Portugal");

	}

	@After
	public void clear() {

		user1 = null;
		user2 = null;
		user3 = null;
	}

	/**
	 * Given a visitor, this test attempts the creation of a User Registration
	 * controller
	 */

	@Test
	public void testUserRegistrationController() {
		// creates the controller and asserts the list of users starts at 0
		assertEquals(userService.getAllUsersFromUserContainer().size(), 2);

		// Checks if the user1 is in the UserContainer
		assertEquals(testUserRegistrationController.isUserInUserRepository("daniel@gmail.com"), true);

		// uses the controller to both create and add the user
		testUserRegistrationController.addNewUser("Fabio", "fabio@gmail.com", "003", "worker", "919997775", "Password",
				"Tasty streets", "4450-150", "Hellcity", "HellsBurg", "HellMam");
		assertEquals(3, userService.getAllUsersFromUserContainer().size());
		assertTrue(userService.getAllUsersFromUserContainer().get(0).equals(user1));

		// verifies if the addNewUser method returns null when user email already exists
		testUserRegistrationController.addNewUser("Daniel", "danielq@gmail.com", "001", "Porteiro", "920000000",
				"Password", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		testUserRegistrationController.addNewUser("Daniel", "danicom", "001", "Porteiro", "920000000", "Password",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		user2 = userService.getAllUsersFromUserContainer().get(0);
		user3 = userService.getAllUsersFromUserContainer().get(1);

		// verifies if the addNewUser method returns true when the user email already
		// exists in the repository
		assertEquals(testUserRegistrationController.isUserInUserRepository(user1.getEmail()), true);
		// verifies if the addNewUser method returns false when user email is invalid
		assertEquals(testUserRegistrationController.isUserEmailValid(user3.getEmail()), false);
		// verifies if the addNewUser method returns false when user email is valid
		assertEquals(testUserRegistrationController.isUserEmailValid(user1.getEmail()), true);

	}

	@Test
	public void wasUserAddedTest() {

		testUserRegistrationController.addNewUser("Daniel", "danicom", "001", "Porteiro", "920000000", "Password",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");

		assertTrue(testUserRegistrationController.wasUserAdded(true));

		// user 3 was created, added and email was set as invalid

		testUserRegistrationController.addNewUser("João", "joão@gmail.com", "034", "Testes", "919876787", "Password",
				"Street", "2401-343", "Testburg", "Testo", "Testistan");

		assertTrue(testUserRegistrationController.wasUserAdded(true));

	}

	@Test
	public void isUserEmailValidRegistrationController() {

		// Creates a valid email
		String email = new String("validEmail@gmail.com");

		// creates the controller and asserts the list of users starts at 0

		assertTrue(testUserRegistrationController.isEmailValidController(email));

		// Changes string value to invalid email
		email = "invalid";
		assertFalse(testUserRegistrationController.isEmailValidController(email));

	}

}