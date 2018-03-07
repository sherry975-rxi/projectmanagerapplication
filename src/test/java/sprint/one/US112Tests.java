package sprint.one;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.Company;
import project.model.Profile;
import project.model.User;

import static org.junit.Assert.*;

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

		newUser2 = Armis.getUsersContainer().createUser("Manel", "user2@gmail.com", "001", "Empregado", "930000000",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		newUser3 = Armis.getUsersContainer().createUser("Manelinho", "user3@gmail.com", "002", "Telefonista",
				"940000000", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");

		Armis.getUsersContainer().addUserToUserRepository(newUser2);
		
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

		assertTrue(Armis.getUsersContainer().getAllUsersFromUserContainer().contains(newUser2));
		assertFalse(Armis.getUsersContainer().getAllUsersFromUserContainer().contains(newUser3));

		assertEquals(newUser2.getUserProfile(), Profile.UNASSIGNED);

		Armis.getUsersContainer().addUserToUserRepository(newUser3);

		newUser3.setUserProfile(Profile.COLLABORATOR);

		assertEquals(newUser2.getUserProfile(), Profile.UNASSIGNED);
		assertEquals(newUser3.getUserProfile(), Profile.COLLABORATOR);

		// newUser2 becomes Director

		newUser2.setUserProfile(Profile.DIRECTOR);
		assertEquals(newUser2.getUserProfile(), Profile.DIRECTOR);

		// newUser2 becomes Collaborador
		newUser2.setUserProfile(Profile.COLLABORATOR);
		assertEquals(newUser2.getUserProfile(), Profile.COLLABORATOR);

	}

	
}
