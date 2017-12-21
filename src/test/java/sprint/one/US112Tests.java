package sprint.one;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.User;

public class US112Tests {

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

	@Before
	public void setUp() {

		Armis = Company.getTheInstance();

		newUser2 = Armis.getUsersRepository().createUser("Manel", "user2@gmail.com", "001", "Empregado", "930000000",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		newUser3 = Armis.getUsersRepository().createUser("Manelinho", "user3@gmail.com", "002", "Telefonista",
				"940000000", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");

		Armis.getUsersRepository().addUserToUserRepository(newUser2);
		
	}

	@After
	public void tearDown() {
		Company.clear();
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
	public void testSetUserAsCollaborator() {

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
