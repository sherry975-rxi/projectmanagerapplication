package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.Profile;
import project.model.User;
import project.model.UserContainer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DoLoginControllerTests {

	Company myCompany;
	UserContainer userContainer;
	User user1;
	User userAdmin;
	DoLoginController doLoginController;

	@Before
	public void setUp() {
		// create company

		myCompany = Company.getTheInstance();

		// creates an UserContainer
		userContainer = myCompany.getUsersContainer();

		userContainer.getAllUsersFromUserContainer().clear();

		// create user
		user1 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// Sets a password for the user1
		user1.setPassword("123456");

		// add user to user list
		userContainer.addUserToUserRepository(user1);
		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);

	}

	@After
	public void tearDown() {
		Company.clear();
		user1 = null;
		userContainer = null;
		doLoginController = null;
	}

	// Test with a valid password and valid email
	@Test
	public void DoValidLoginControllerTest() {

		String validEmail = new String("daniel@gmail.com");
		String validPassword = new String("123456");

		doLoginController = new DoLoginController();

		assertTrue(doLoginController.doLogin(validEmail, validPassword));

	}

	// Test with an invalid Pasword
	@Test
	public void DoInvalidPasswordLoginControllerTest() {

		String validEmail = new String("daniel@gmail.com");
		String invalidPassword = new String("12345");

		doLoginController = new DoLoginController();

		assertFalse(doLoginController.doLogin(validEmail, invalidPassword));

	}

	// Test with an invalid Email
	@Test
	public void DoInvalidEmailLoginControllerTest() {

		String invalidEmail = new String("invalid@gmail.com");
		String validPassword = new String("123456");

		doLoginController = new DoLoginController();

		assertFalse(doLoginController.doLogin(invalidEmail, validPassword));

	}

	// Test with an invalid Email and invalid password
	@Test
	public void DoInvalidEmailAndPasswordLoginControllerTest() {

		String invalidEmail = new String("invalid@gmail.com");
		String invalidPassword = new String("12345");

		doLoginController = new DoLoginController();

		assertFalse(doLoginController.doLogin(invalidEmail, invalidPassword));

	}

}
