package test.java.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.User;

class US112Tests {

	/**
	 * Tests US112
	 * 
	 * US112: Como Administrador, pretendo atribuir o perfil de "Colaborador" a
	 * utilizadores registados.
	 * 
	 * uses methods createUser(), addUserToUserList(), setUserProfile(),
	 * getProfile(), getProfileInt()
	 * 
	 * @author Rita Machado
	 * @author João Bessa
	 * 
	 */
	Company Armis;
	User newUser2;
	User newUser3;
	int typeOfUser;

	@BeforeEach
	void setUp() {
		Armis = Company.getTheInstance();
		Armis.getUsersList().clear();
		newUser2 = Armis.createUser("Manel", "user2@gmail.com", "001", "Empregado", "930000000", "Testy Street",
				"2401-343", "Testburg", "Testo", "Testistan");
		newUser3 = Armis.createUser("Manelinho", "user3@gmail.com", "002", "Telefonista", "940000000", "Testy Street",
				"2401-343", "Testburg", "Testo", "Testistan");

		typeOfUser = 1;
	}

	@AfterEach
	void tearDown() {
		Armis = null;
		newUser2 = null;
		newUser3 = null;
		typeOfUser = 0;
	}

	/**
	 * Tests the setUserProfile method to check if the profile collaborator was
	 * correctly attributed. Then attempts to promote the User to Director, then
	 * demote back to Collaborator; Compares the User's profile int to Collaborator
	 * and Director ID's (must be equal)
	 */
	@Test
	void testSetUserAsCollaborator() {

		assertTrue(Armis.addUserToUserList(newUser2));
		assertTrue(Armis.addUserToUserList(newUser3));

		assertTrue(Armis.doesUserExist(newUser2));
		assertTrue(Armis.doesUserExist(newUser3));

		assertEquals(newUser2.getProfile().getProfileInt(), 0);

		assertTrue(newUser2.setUserProfile(typeOfUser));
		assertTrue(newUser3.setUserProfile(typeOfUser));

		assertEquals(newUser2.getProfile().getProfileInt(), typeOfUser);
		assertEquals(newUser3.getProfile().getProfileInt(), typeOfUser);

		// typeOfUser passa a ser 2 (Director), e newUser2 passa a Director
		typeOfUser = 2;
		assertTrue(newUser2.setUserProfile(typeOfUser));
		assertEquals(newUser2.getProfile().getProfileInt(), typeOfUser);

		// newUser2 volta a Colaborador
		typeOfUser = 1;
		assertTrue(newUser2.setUserProfile(typeOfUser));
		assertEquals(newUser2.getProfile().getProfileInt(), typeOfUser);

	}
}
