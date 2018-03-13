package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import project.Services.UserContainerService;
import project.model.User;

import static org.junit.Assert.*;

public class US101RegisterUserControllerTest {

	User user1, user2, user3;
	UserContainerService userContainer;

	@Before
	public void setUp() {

		// creates an UserContainer
		userContainer = new UserContainerService();

		// create user
		user1 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "Porteiro", "920000000",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		
		user3 = userContainer.createUser("João", "joão.gmail.com", "034", "Testes", "919876787",
				"Street", "2401-343", "Testburg", "Testo", "Testistan");

	}

	@After
	public void tearDown() {

		userContainer = null;
		user1 = null;
		user2 = null;
		user3 = null;

	}


	/**
	 * Given a visitor, this test attempts the creation of a User Registration
	 * controller
	 */
	@Ignore
	@Test
	public void testUserRegistrationController() {
		// creates the controller and asserts the list of users starts at 0
		US101RegisterUserController testUserRegistrationController = new US101RegisterUserController();
		assertEquals(userContainer.getAllUsersFromUserContainer().size(), 0);

		// Checks if the user1 is in the UserContainer
		assertEquals(testUserRegistrationController.isUserInUserRepository("daniel@gmail.com"), false);

		// uses the controller to both create and add the user
		testUserRegistrationController.addNewUser("Daniel", "daniel@gmail.com", "001", "Porteiro", "920000000",
				"Password", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		assertEquals(userContainer.getAllUsersFromUserContainer().size(), 1);
		assertTrue(userContainer.getAllUsersFromUserContainer().get(0).equals(user1));

		// verifies if the addNewUser method returns null when user email already exists
		testUserRegistrationController.addNewUser("Daniel", "daniel@gmail.com", "001", "Porteiro", "920000000",
				"Password", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		testUserRegistrationController.addNewUser("Daniel", "danicom", "001", "Porteiro", "920000000", "Password",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		user2 = userContainer.getAllUsersFromUserContainer().get(0);
		user3 = userContainer.getAllUsersFromUserContainer().get(1);

		// verifies if the addNewUser method returns true when the user email already
		// exists in the repository
		assertEquals(testUserRegistrationController.isUserInUserRepository(user1.getEmail()), true);
		// verifies if the addNewUser method returns false when user email is invalid
		assertEquals(testUserRegistrationController.isUserEmailValid(user3.getEmail()), false);
		// verifies if the addNewUser method returns false when user email is valid
		assertEquals(testUserRegistrationController.isUserEmailValid(user1.getEmail()), true);

	}
	


	@Ignore
	@Test
	public void wasUserAddedTest() {
	
		US101RegisterUserController testUserRegistrationController = new US101RegisterUserController();	
		
		testUserRegistrationController.addNewUser("Daniel", "danicom", "001", "Porteiro", "920000000", "Password",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
					
		assertTrue(testUserRegistrationController.wasUserAdded(true));
		
		
		//user 3 was created, added and email was set as invalid
		
		testUserRegistrationController.addNewUser("João", "joão@gmail.com", "034", "Testes", "919876787", "Password",
				"Street", "2401-343", "Testburg", "Testo", "Testistan");
			
		assertTrue(testUserRegistrationController.wasUserAdded(true));
		
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