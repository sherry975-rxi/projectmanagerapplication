package test.java.project.model;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Profile;
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

	@BeforeEach
	void setUp() {
		Armis = Company.getTheInstance();
		Armis.getUsersRepository().getAllUsersFromRepository().clear();
		Armis.getProjectsRepository().getAllProjects().clear();
		newUser2 = Armis.getUsersRepository().createUser("Manel", "user2@gmail.com", "001", "Empregado", "930000000",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		newUser3 = Armis.getUsersRepository().createUser("Manelinho", "user3@gmail.com", "002", "Telefonista",
				"940000000", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");

		Armis.getUsersRepository().addUserToUserRepository(newUser2);
	}

	@AfterEach
	void tearDown() {
		Armis = null;
		newUser2 = null;
		newUser3 = null;

	}

	/**
	 * Tests the setUserProfile method to check if the profile collaborator was
	 * correctly attributed. Then attempts to promote the User to Director, then
	 * demote back to Collaborator; Compares the User's profile to Collaborator and
	 * Director ID's (must be equal)
	 */
	@Test
	void testSetUserAsCollaborator() {

		assertTrue(Armis.getUsersRepository().getAllUsersFromRepository().contains(newUser2));
		assertFalse(Armis.getUsersRepository().getAllUsersFromRepository().contains(newUser3));

		assertEquals(newUser2.getUserProfile(), Profile.VISITOR);

		Armis.getUsersRepository().addUserToUserRepository(newUser3);

		newUser3.setUserProfile(Profile.COLLABORATOR);

		assertEquals(newUser2.getUserProfile(), Profile.VISITOR);
		assertEquals(newUser3.getUserProfile(), Profile.COLLABORATOR);

		// newUser2 becomes Director

		newUser2.setUserProfile(Profile.DIRECTOR);
		assertEquals(newUser2.getUserProfile(), Profile.DIRECTOR);

		// newUser2 becomes Collaborador
		newUser2.setUserProfile(Profile.COLLABORATOR);
		assertEquals(newUser2.getUserProfile(), Profile.COLLABORATOR);

	}
}
