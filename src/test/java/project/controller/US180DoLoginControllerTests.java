package project.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import project.Repository.UserRepository;
import project.Services.UserService;
import project.model.Profile;
import project.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controller" })

public class US180DoLoginControllerTests {

	@Autowired
	UserRepository userRepository;

	UserService userContainer;

	User user1;
	User userAdmin;
	@Autowired
	US180DoLoginController doLoginController;

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

	// Test with a valid password and valid email
	@Test
	public void DoValidLoginControllerTest() {

		String validEmail = new String("daniel@gmail.com");
		String validPassword = new String("123456");

		doLoginController = new US180DoLoginController();
		doLoginController.userService = this.userContainer;

		assertTrue(doLoginController.doLogin(validEmail, validPassword));

	}

	// Test with an invalid Pasword
	@Test
	public void DoInvalidPasswordLoginControllerTest() {

		String validEmail = new String("daniel@gmail.com");
		String invalidPassword = new String("12345");

		doLoginController = new US180DoLoginController();
		doLoginController.userService = this.userContainer;

		assertFalse(doLoginController.doLogin(validEmail, invalidPassword));

	}

	// Test with an invalid Email
	@Test
	public void DoInvalidEmailLoginControllerTest() {

		String invalidEmail = new String("invalid@gmail.com");
		String validPassword = new String("123456");

		doLoginController = new US180DoLoginController();
		doLoginController.userService = this.userContainer;

		assertFalse(doLoginController.doLogin(invalidEmail, validPassword));

	}

	// Test with an invalid Email and invalid password
	@Test
	public void DoInvalidEmailAndPasswordLoginControllerTest() {

		String invalidEmail = new String("invalid@gmail.com");
		String invalidPassword = new String("12345");

		doLoginController = new US180DoLoginController();
		doLoginController.userService = this.userContainer;

		assertFalse(doLoginController.doLogin(invalidEmail, invalidPassword));

	}

}
