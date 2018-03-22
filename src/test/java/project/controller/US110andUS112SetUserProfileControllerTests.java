package project.controller;

import static org.junit.Assert.assertEquals;
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
import project.model.Profile;
import project.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controller" })
public class US110andUS112SetUserProfileControllerTests {
	@Autowired
	UserService userContainer;
	@Autowired
	US110andUS112SetUserProfileController us110andUS112SetUserProfileController;

	User newUser2;
	User newUser3;

	@Before
	public void setUp() {

		newUser2 = us110andUS112SetUserProfileController.getUserContainer().createUser("Manel", "user2@gmail.com",
				"001", "Empregado", "930000000", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");

		newUser3 = us110andUS112SetUserProfileController.getUserContainer().createUser("Manelinho", "user3@gmail.com",
				"002", "Telefonista", "940000000", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");

	}

	@After
	public void clear() {

		newUser2 = null;
		newUser3 = null;

	}

	@Test
	public void testSetUserAsDirectorController() {
		// given a User - newUser2 - asserts they start as Visitor when created
		// and then creates the controller
		assertEquals(newUser2.getUserProfile(), Profile.UNASSIGNED);

		// sets newUser2 as Director and asserts its profile
		us110andUS112SetUserProfileController.setUserAsDirector(newUser2);
		assertEquals(newUser2.getUserProfile(), Profile.DIRECTOR);

		// finally, asserts that no other Users were changed
		// and that user repository contains the new director
		assertTrue(us110andUS112SetUserProfileController.getUserContainer().searchUsersByProfile(Profile.DIRECTOR)
				.contains(newUser2));
		assertEquals(newUser3.getUserProfile(), Profile.UNASSIGNED);
	}

	@Test
	public void testSetUserAsCollaboratorController() {
		// given a User - newUser2 - asserts they start as Visitor when created
		// and then creates the controller
		assertEquals(newUser2.getUserProfile(), Profile.UNASSIGNED);

		// sets newUser2 as Collaborator and asserts its profile
		us110andUS112SetUserProfileController.setUserAsCollaborator(newUser2);
		assertEquals(newUser2.getUserProfile(), Profile.COLLABORATOR);

		// finally, asserts that no other Users were changed
		// and that user repository contains the new director
		assertTrue(us110andUS112SetUserProfileController.getUserContainer().searchUsersByProfile(Profile.COLLABORATOR)
				.contains(newUser2));
		assertEquals(newUser3.getUserProfile(), Profile.UNASSIGNED);
	}

	@Test
	public void testUserProfileAsString() {
		assertTrue("Unassigned".equals(us110andUS112SetUserProfileController.userProfileAsString(newUser2)));
		us110andUS112SetUserProfileController.setUserAsCollaborator(newUser2);

		assertTrue("Collaborator".equals(us110andUS112SetUserProfileController.userProfileAsString(newUser2)));
		us110andUS112SetUserProfileController.setUserAsDirector(newUser2);

		assertTrue("Director".equals(us110andUS112SetUserProfileController.userProfileAsString(newUser2)));
	}

}
