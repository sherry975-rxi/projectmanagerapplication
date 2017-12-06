package usTest.java.project.model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Profile;
import main.java.project.model.User;

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

		newUser2 = Blip.createUser("Manel", "user2@gmail.com", "001", "Empregado", "930000000", "Testy Street",
				"2401-343", "Testburg", "Testo", "Testistan");
		newUser3 = Blip.createUser("Manelinho", "user3@gmail.com", "002", "Telefonista", "940000000", "Testy Street",
				"2401-343", "Testburg", "Testo", "Testistan");

		Blip.addUserToUserList(newUser2);
	}

	@AfterEach
	void breakDown() {
		Blip = null;

		newUser2 = null;
		newUser3 = null;

	}

	/**
	 * Tests the setUserProfile method to check if the profile director was
	 * correctly attributed. Then tests the ability to set multiple directors; Also
	 * attempts to switch from director to collaborator, then to director again.
	 */
	@Test
	void testSetUserAsDirector() {

		newUser2.setUserProfile(Profile.DIRECTOR);

		assertEquals(newUser2.getProfile(), Profile.DIRECTOR);
		assertEquals(newUser3.getProfile(), Profile.VISITOR);

		// Sets another users as directors and confirms status
		newUser3.setUserProfile(Profile.DIRECTOR);

		assertEquals(newUser2.getProfile(), Profile.DIRECTOR);
		assertEquals(newUser3.getProfile(), Profile.DIRECTOR);

		// tests changing to visitor

		newUser3.setUserProfile(Profile.VISITOR);
		assertEquals(newUser3.getProfile(), Profile.VISITOR);

		// tests changing back to director
		newUser3.setUserProfile(Profile.DIRECTOR);
		assertEquals(newUser3.getProfile(), Profile.DIRECTOR);

	}

}
