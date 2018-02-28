package sprint.one;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.User;

public class US116Tests {

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

	@Before
	public void setUp() {

		myCompany = Company.getTheInstance();

		newUser2 = myCompany.getUsersRepository().createUser("Manel", "user2@gmail.com", "001", "Empregado",
				"930000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");
		newUser3 = myCompany.getUsersRepository().createUser("Manelinho", "user3@gmail.com", "002", "Telefonista",
				"940000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");

		myCompany.getUsersRepository().addUserToUserRepository(newUser2);
		myCompany.getUsersRepository().addUserToUserRepository(newUser3);

		newUser2.changeUserState();
	}

	@After
	public void tearDown() {
		Company.clear();
		newUser2 = null;
		newUser3 = null;
	}

	/**
	 * Tests the setSystemUserState method to check if the active or deactivate
	 * state was correctly attributed to the user
	 */
	@Test
	public void testSetSystemUserState() {
		assertFalse(newUser2.isSystemUserStateActive());
		assertTrue(newUser3.isSystemUserStateActive());

		newUser2.changeUserState();

		assertTrue(newUser2.isSystemUserStateActive());
	}

}
