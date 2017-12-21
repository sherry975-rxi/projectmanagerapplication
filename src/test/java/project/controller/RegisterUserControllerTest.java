package project.controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.User;

public class RegisterUserControllerTest {

	Company Critical;
	User user1;

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

	}
	
	
	/**
	 * Given a visitor, this test attempts the creation of a User Registration controller
	 */
	@Test
	public void testUserRegistrationController() {
		// creates the controller and asserts the list of users starts at 0
		RegisterUserController testUserRegistrationController = new RegisterUserController();
		assertEquals(Critical.getUsersRepository().getAllUsersFromRepository().size(), 0);
		
		// uses the controller to both create and add the user
		testUserRegistrationController.addNewUser("Daniel", "daniel@gmail.com", "001", "Porteiro", "920000000", "Password",
		"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		assertEquals(Critical.getUsersRepository().getAllUsersFromRepository().size(), 1);
		assertTrue(Critical.getUsersRepository().getAllUsersFromRepository().get(0).equals(user1));
		
		// verifies if the addNewUser method returns null when user email already exists
		assertEquals(testUserRegistrationController.addNewUser("Daniel", "daniel@gmail.com", "001", "Porteiro", "920000000", "Password",
		"Testy Street", "2401-343", "Testburg", "Testo", "Testistan"), null );
		// verifies if the addNewUser method returns null when user email is invalid
		assertEquals(testUserRegistrationController.addNewUser("Daniel", "daniel?gmail.com", "001", "Porteiro", "920000000", "Password",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan"), null );
	}

}
