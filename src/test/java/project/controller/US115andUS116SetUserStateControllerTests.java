package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import project.HelloJpaApplication;
import project.Repository.UserRepository;
import project.Services.UserService;
import project.model.Profile;
import project.model.User;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class US115andUS116SetUserStateControllerTests {

	Profile director = Profile.DIRECTOR;
	Profile collaborator = Profile.COLLABORATOR;
	User newUser2;
	User newUser3;
	
	US115andUS116SetUserStateController testUserStateController;
	
	@Autowired
	private UserRepository userRepo;
	
	private UserService userService;

	@Before
	public void setUp() {
		
		userService = new UserService();
		userService.setUserRepository(userRepo);
		
		testUserStateController = new US115andUS116SetUserStateController();
		testUserStateController.setToChangeState(newUser2);
		testUserStateController.setUserContainer(userService);
		
		// creates the user and adds it to the dummy database, then refreshes the in memory User List
		newUser2 = userService.createUser("Manel", "user2@gmail.com", "001", "Empregado",
				"930000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");

		userService.addUserToUserRepositoryX(newUser2);
		userService.updateUserContainer();

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
		userService.updateUserContainer();
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
		userService.updateUserContainer();
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
