package userStoryTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import code.Company;
import code.User;

class US110Tests {

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

	@BeforeEach
	void setUp() {
		Blip = Company.getTheInstance();
		Blip.getUsersList().clear();

		newUser2 = Blip.createUser("Manel", "user2@gmail.com", "Empregado", "930000000", "Testy Street", "2401-343",
				"Testburg", "Testo", "Testistan");
		newUser3 = Blip.createUser("Manelinho", "user3@gmail.com", "Telefonista", "940000000", "Testy Street",
				"2401-343", "Testburg", "Testo", "Testistan");

		typeOfUser = 2;
	}

	@AfterEach
	void breakDown() {
		Blip = null;

		newUser2 = null;
		newUser3 = null;
		typeOfUser = 0;

	}

	/**
	 * Tests the setUserProfile method to check if the profile director was
	 * correctly attributed. TypeofUser=3 (non-existent) will return false (not
	 * equal)
	 */
	@Test
	void testSetUserAsDirector() {

		assertTrue(Blip.addUserToUserList(newUser2));

		assertTrue(Blip.doesUserExist(newUser2.getEmail()));
		assertFalse(Blip.doesUserExist(newUser3.getEmail()));

		assertTrue(newUser2.setUserProfile(typeOfUser));

		assertEquals(newUser2.getProfile().getProfileInt(), typeOfUser);
		assertEquals(newUser3.getProfile().getProfileInt(), 0);

		typeOfUser = 3;

		assertFalse(newUser2.setUserProfile(typeOfUser));

	}

	/**
	 * Tests the ability to set multiple directors; Also attempts to switch from
	 * director to collaborator, then to director again.
	 */
	@Test
	void testSetMultipleDirectors() {

		assertTrue(Blip.addUserToUserList(newUser2));
		assertTrue(Blip.addUserToUserList(newUser3));

		assertTrue(Blip.doesUserExist(newUser2.getEmail()));
		assertTrue(Blip.doesUserExist(newUser3.getEmail()));

		// Sets both users as directors and confirms status
		assertTrue(newUser2.setUserProfile(typeOfUser));
		assertTrue(newUser3.setUserProfile(typeOfUser));

		assertEquals(newUser2.getProfile().getProfileInt(), typeOfUser);
		assertEquals(newUser3.getProfile().getProfileInt(), typeOfUser);

		// tests changing to visitor
		typeOfUser = 0;

		assertTrue(newUser2.setUserProfile(typeOfUser));
		assertEquals(newUser2.getProfile().getProfileInt(), typeOfUser);

		// tests changing back to director
		typeOfUser = 2;
		assertTrue(newUser2.setUserProfile(typeOfUser));
		assertEquals(newUser2.getProfile().getProfileInt(), typeOfUser);

	}

}
