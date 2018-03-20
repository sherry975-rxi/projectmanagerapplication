package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.Repository.UserRepository;
import project.Services.UserService;
import project.model.Profile;
import project.model.User;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DoLoginControllerTests {

	@Autowired
	UserRepository userRepository;

	UserService userContainer;

	User user1;
	User userAdmin;

	DoLoginController doLoginController;


	@Before
	public void setUp() {

		userContainer = new UserService();
		userContainer.setUserRepository(userRepository);

		// create user
		user1 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// Sets a password for the user1
		user1.setPassword("123456");
		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);

		userContainer.addUserToUserRepositoryX(user1);

	}

	@After
	public void tearDown() {
		userRepository.deleteAll();
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
		doLoginController.userService=this.userContainer;

		assertTrue(doLoginController.doLogin(validEmail, validPassword));

	}

	// Test with an invalid Pasword
	@Test
	public void DoInvalidPasswordLoginControllerTest() {

		String validEmail = new String("daniel@gmail.com");
		String invalidPassword = new String("12345");

		doLoginController = new DoLoginController();
		doLoginController.userService=this.userContainer;

		assertFalse(doLoginController.doLogin(validEmail, invalidPassword));

	}

	// Test with an invalid Email
	@Test
	public void DoInvalidEmailLoginControllerTest() {

		String invalidEmail = new String("invalid@gmail.com");
		String validPassword = new String("123456");

		doLoginController = new DoLoginController();
		doLoginController.userService=this.userContainer;

		assertFalse(doLoginController.doLogin(invalidEmail, validPassword));

	}

	// Test with an invalid Email and invalid password
	@Test
	public void DoInvalidEmailAndPasswordLoginControllerTest() {

		String invalidEmail = new String("invalid@gmail.com");
		String invalidPassword = new String("12345");

		doLoginController = new DoLoginController();
		doLoginController.userService=this.userContainer;

		assertFalse(doLoginController.doLogin(invalidEmail, invalidPassword));

	}

}
