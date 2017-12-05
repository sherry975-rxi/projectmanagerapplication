package test.java.project.model;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.User;

class US116Tests {

	/**
	 * Tests US115
	 * 
	 * US115: Como Administrador, pretendo reactivar utilizadores do sistema.
	 * 
	 * uses methods setSystemUserState
	 * 
	 */
	Company myCompany;
	User newUser2;
	User newUser3;

	@BeforeEach
	void setUp() {

		myCompany = Company.getTheInstance();
		myCompany.getUsersList().clear();

		newUser2 = myCompany.createUser("Manel", "user2@gmail.com", "001", "Empregado", "930000000", "Rua Bla",
				"BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");
		newUser3 = myCompany.createUser("Manelinho", "user3@gmail.com", "002", "Telefonista", "940000000", "Rua Bla",
				"BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");

		int typeOfUser = 1;
	}

	@AfterEach
	void tearDown() {
		Company myCompany = null;
		User newUser2 = null;
		User newUser3 = null;
	}

	/**
	 * Tests the setSystemUserState method to check if the active or deactivate
	 * state was correctly attributed to the user; Also Attempts to change UserState
	 * of non existent user (False)
	 */
	@Test
	void testSetSystemUserState() {

		myCompany.addUserToUserList(newUser2);
		myCompany.addUserToUserList(newUser3);

		newUser2.setSystemUserState(false);
		newUser3.setSystemUserState(true);

		assertFalse(newUser2.isSystemUserStateActive());
		assertTrue(newUser3.isSystemUserStateActive());

	}

	/**
	 * Asserts if UserStateActive is True; Finally, Asserts if UserStateActive if
	 * False, after setting it as such;
	 */
	@Test
	void testAssertSystemUserState() {

		newUser2.setSystemUserState(true);
		assertTrue(newUser2.isSystemUserStateActive());

		newUser3.setSystemUserState(false);
		assertFalse(newUser3.isSystemUserStateActive());
	}

}
