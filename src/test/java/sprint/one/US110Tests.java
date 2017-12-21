package sprint.one;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.controller.SetUserProfileController;
import project.model.Company;
import project.model.Profile;
import project.model.User;

public class US110Tests {

	/**
	 * Tests US110
	 * 
	 * US110: Como Administrador, pretendo atribuir o perfil de "Diretor" a
	 * utilizadores registados.
	 * 
	 * uses methods createUser(), addUserToUserList(), setUserProfile(),
	 * getProfile(), getProfileInt()
	 * 
	 * @author Rita Machado
	 * @author João Bessa
	 * 
	 */

	Company Blip;
	User newUser2, newUser3;
	int typeOfUser;

	@Before
	public void setUp() {

		Blip = Company.getTheInstance();

		newUser2 = Blip.getUsersRepository().createUser("Manel", "user2@gmail.com", "001", "Empregado", "930000000",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		newUser3 = Blip.getUsersRepository().createUser("Manelinho", "user3@gmail.com", "002", "Telefonista",
				"940000000", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");

		Blip.getUsersRepository().addUserToUserRepository(newUser2);
	}

	@After
	public void breakDown() {
		Company.clear();
		newUser2 = null;
		newUser3 = null;

	}

	/**
	 * Tests the setUserProfile method to check if the profile director was
	 * correctly attributed. Then tests the ability to set multiple directors; Also
	 * attempts to switch from director to collaborator, then to director again.
	 */
	@Test
	public void testSetUserAsDirector() {

		newUser2.setUserProfile(Profile.DIRECTOR);

		assertEquals(newUser2.getUserProfile(), Profile.DIRECTOR);
		assertEquals(newUser3.getUserProfile(), Profile.VISITOR);

		// Sets another users as directors and confirms status
		newUser3.setUserProfile(Profile.DIRECTOR);

		assertEquals(newUser2.getUserProfile(), Profile.DIRECTOR);
		assertEquals(newUser3.getUserProfile(), Profile.DIRECTOR);

		// tests changing to visitor

		newUser3.setUserProfile(Profile.VISITOR);
		assertEquals(newUser3.getUserProfile(), Profile.VISITOR);

		// tests changing back to director
		newUser3.setUserProfile(Profile.DIRECTOR);
		assertEquals(newUser3.getUserProfile(), Profile.DIRECTOR);

	}
	
	@Test
	public void testSetUserAsDirectorController() {
		// given a User - newUser2 - asserts they start as Visitor when created
		// and then creates the controller
		assertEquals(newUser2.getUserProfile(), Profile.VISITOR);
		SetUserProfileController testProfileController = new SetUserProfileController(newUser2);
		
		// sets newUser2 as Director and asserts its profile
		testProfileController.setUserAsDirector();
		assertEquals(newUser2.getUserProfile(), Profile.DIRECTOR);
		
		//finally, asserts that no other Users were changed
		// and that user repository contains the new director
		assertTrue(Blip.getUsersRepository().searchUsersByProfile(Profile.DIRECTOR).contains(newUser2));
		assertEquals(newUser3.getUserProfile(), Profile.VISITOR);
	}

}
