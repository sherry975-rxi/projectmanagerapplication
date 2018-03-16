package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import project.Repository.UserRepository;
import project.model.Company;
import project.model.Profile;
import project.model.User;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations="classpath:application.properties")
public class US115andUS116SetUserStateControllerTests {

	Profile director = Profile.DIRECTOR;
	Profile collaborator = Profile.COLLABORATOR;

	Company myCompany;
	User newUser2;
	User newUser3;

	@Autowired
    private UserRepository userRepository;

	@Before
	public void setUp() {

	    // gets the Company instance and links it to the dummy H2 database
		myCompany = Company.getTheInstance();
		myCompany.getUsersContainer().setUserRepository(userRepository);

		// creates the user and adds it to the dummy database, then refreshes the in memory User List
		newUser2 = myCompany.getUsersContainer().createUser("Manel", "user2@gmail.com", "001", "Empregado",
				"930000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");

		myCompany.getUsersContainer().addUserToUserRepositoryX(newUser2);
		myCompany.getUsersContainer().updateUserContainer();

	}

	@After
	public void tearDown() {
		Company.clear();
		newUser2 = null;
	}

	@Test
	public void testSetUserAsInactiveController() {
	    // given only 1 user in the database
        assertEquals(myCompany.getUsersContainer().getAllUsersFromUserContainer().size(), 1);

		// When the User starts as Active and creates the User State Controller
		assertTrue(newUser2.isSystemUserStateActive());
		US115andUS116SetUserStateController testUserStateController = new US115andUS116SetUserStateController(newUser2);

		// Then Uses the controller to deactivate the user and confirms its state.
		testUserStateController.changeUserState();
		assertFalse(newUser2.isSystemUserStateActive());

		// when the userContainer is refreshed from the database info
		myCompany.getUsersContainer().updateUserContainer();
		newUser2 = myCompany.getUsersContainer().getAllUsersFromUserContainer().get(0);

		// then newUser2 must be Manel, and must be inactive
        assertTrue("Manel".equals(newUser2.getName()));
        assertFalse(newUser2.isSystemUserStateActive());
	}

	@Test
	public void testSetUserAsActiveController() {
		// Asserts the User starts as Active and creates the User State Controller
		assertTrue(newUser2.isSystemUserStateActive());
		US115andUS116SetUserStateController testUserStateController = new US115andUS116SetUserStateController(newUser2);

		// Uses the controller to deactivate the user and confirms its state.
		testUserStateController.changeUserState();
		assertFalse(newUser2.isSystemUserStateActive());

		// Uses the controller to reactivate the user and confirms its state.
		testUserStateController.changeUserState();
		assertTrue(newUser2.isSystemUserStateActive());


        // when the userContainer is refreshed from the database info
        myCompany.getUsersContainer().updateUserContainer();
        newUser2 = myCompany.getUsersContainer().getAllUsersFromUserContainer().get(0);

        // then newUser2 must be Manel, and must be active
        assertTrue("Manel".equals(newUser2.getName()));
        assertTrue(newUser2.isSystemUserStateActive());
	}

	@Test
	public void userDataAsString() {
		US115andUS116SetUserStateController testUserStateController = new US115andUS116SetUserStateController(newUser2);
		assertTrue("(ACTIVE)".equals(testUserStateController.userStateAsString()));
		testUserStateController.changeUserState();
		assertTrue("(DISABLED)".equals(testUserStateController.userStateAsString()));
	}

}
