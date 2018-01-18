package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.User;

public class US101RegisterUserControllerTest {

	Company Critical;
	User user1, user2, user3;

	@Before
	public void setUp() {

		// create company
		Critical = Company.getTheInstance();

		// create user
		user1 = Critical.getUsersRepository().createUser("Daniel", "daniel@gmail.com", "001", "Porteiro", "920000000",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");

	}

	@After
	public void tearDown() {

		Company.clear();
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
		US101RegisterUserController testUserRegistrationController = new US101RegisterUserController();
		assertEquals(Critical.getUsersRepository().getAllUsersFromRepository().size(), 0);

		// Checks if the user1 is in the UserRepository
		assertEquals(testUserRegistrationController.isUserInUserRepository("daniel@gmail.com"), false);

		// uses the controller to both create and add the user
		testUserRegistrationController.addNewUser("Daniel", "daniel@gmail.com", "001", "Porteiro", "920000000",
				"Password", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		assertEquals(Critical.getUsersRepository().getAllUsersFromRepository().size(), 1);
		assertTrue(Critical.getUsersRepository().getAllUsersFromRepository().get(0).equals(user1));

		// verifies if the addNewUser method returns null when user email already exists
		testUserRegistrationController.addNewUser("Daniel", "daniel@gmail.com", "001", "Porteiro", "920000000",
				"Password", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		testUserRegistrationController.addNewUser("Daniel", "danicom", "001", "Porteiro", "920000000", "Password",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		user2 = Critical.getUsersRepository().getAllUsersFromRepository().get(0);
		user3 = Critical.getUsersRepository().getAllUsersFromRepository().get(1);

		// verifies if the addNewUser method returns true when the user email already
		// exists in the repository
		assertEquals(testUserRegistrationController.isUserInUserRepository(user1.getEmail()), true);
		// verifies if the addNewUser method returns false when user email is invalid
		assertEquals(testUserRegistrationController.isUserEmailValid(user3.getEmail()), false);
		// verifies if the addNewUser method returns false when user email is valid
		assertEquals(testUserRegistrationController.isUserEmailValid(user1.getEmail()), true);

	}

	@Test
	public void isUserEmailValidRegistrationController() {

		// Creates a valid email
		String email = new String("validEmail@gmail.com");

		// creates the controller and asserts the list of users starts at 0
		US101RegisterUserController testUserRegistrationController = new US101RegisterUserController();

		assertTrue(testUserRegistrationController.isEmailValidController(email));

		// Changes string value to invalid email
		email = "invalid";
		assertFalse(testUserRegistrationController.isEmailValidController(email));

	}

}